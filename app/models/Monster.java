/*
 * Monster.java
 * 
 * Version 1.0
 *
 * 02/12/2014
 * 
 *           DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE 
 *                    Version 2, December 2004 
 *
 * Copyright (C) 2004 Sam Hocevar <sam@hocevar.net> 
 *
 * Everyone is permitted to copy and distribute verbatim or modified
 * copies of this license document, and changing it is allowed as long 
 * as the name is changed. 
 *
 *            DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE 
 *   TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION 
 *
 *  0. You just DO WHAT THE FUCK YOU WANT TO.
 */

package models;

import play.db.ebean.Model;

import javax.persistence.*;

import models.Skill;
import java.util.*;


/** 
 * The Monster class represents a Monster that a player may use, which is 
 * the main form of interaction of the player with the game world.
 */
@Entity
public class Monster extends Model {
    /** 
     * The Attribute enum is used to execute operations regarding the Monster's
     * individual attributes with ease.
     */
    public static enum Attribute {
        STRENGTH, DEXTERITY, WISDOM
    }

    /** 
     * A Finder object used to fetch Skills from the database.
     */
    public static Finder<String,Monster> find = 
            new Finder<String,Monster>(String.class, Monster.class); 
    /** 
     * The id is used by the database to reference each Monster individually.
     */
	@Id
	public String id;

    /** 
     * The Inventory contains all Skills that have been purchased for a specific
     * Monster and therefore may be used by that Monster.
     */
	@ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	public List<Skill> inventory = new ArrayList<Skill>();
    /** 
     * The list of Generators contains all the Generators that have been 
     * purchased andare currently active, generating more energy for the player.
     */
    @OneToMany(mappedBy = "mon", cascade = CascadeType.ALL)
    public List<MonsterGeneratorLink> generators;
    
    /** 
     * The Monster's name, which may be displayed at the top of the screen.
     */
	public String name;
    /** 
     * The Monster's current stored energy points.
     */
	public int storedEnergy;
    /** 
     * The Monster's Dexterity attribute.
     */
	public int dexterity;
    /** 
     * The Monster's Strength attribute.
     */
    public int strength;
    /** 
     * The Monster's Wisdom attribute.
     */
    public int wisdom;
    
	
	// Constructor
	public Monster(String name) {
		super();
		this.name = name;
		this.storedEnergy = 0;
		this.dexterity = 1;
		this.strength = 1;
		this.wisdom = 1;
	}
	
    // Skills
    /** 
     * Checks if the Monster is able to buy the given Skill. If it is, 
     * the Skill is purchased (the cost is subtracted from the Monster's
     * energy pool) and the Skill is added to the inventory.
     * @param skill The desired Skill to purchase.
     * @return @p true if the purchase was successfully completed, @p false
     * otherwise.
     */
	public boolean purchase(Skill skill) {
		if (this.mayPurchase(skill)) {
			this.setEnergy(this.getEnergy() - skill.getCost());
			this.inventory.add(skill);
			return true;
		}
		return false;
	}
	
    /** 
     * Check if the Monster is able to buy the Skill (if it has the necessary
     * attributes and energy).
     * @param skill The desired Skill to purchase.
     * @return @p true if the purchase is possible, @p false otherwise.
     */
	public boolean mayPurchase(Skill skill) {
		if (this.getDexterity() < skill.getRequiredDexterity()
                || this.getStrength() < skill.getRequiredStrength()
                || this.getWisdom() < skill.getRequiredWisdom() 
                || this.getEnergy() < skill.getCost()) {
			return false;
        }
		if (this.hasSkill(skill)) {
			return false;
        }
		return true;
	}
	
    /** 
     * Check if the Monster has the given skill in its inventory.
     * @param skill The skill to check.
     * @return @p true if the skill is present, @p false otherwise.
     */
	public boolean hasSkill(Skill skill) {
	    for (Skill eachSkill : this.getInventory()) {
	        if (eachSkill.getId() == skill.getId()) {
                return true;
            }
        }
	    return false;
	}
	
    
    // Generators
    /** 
     * Returns a list of all MonsterGeneratorLinks linked to this Monster. This
     * list refers only to purchased Generators, not all Generators.
     */
    public List<MonsterGeneratorLink> getGeneratorLinks() {
        return this.generators;
    }
    
    /** 
     * Returns a list of all Generators linked to this Monster via a 
     * MonsterGeneratorLink. The list contains one generator of each type, and
     * therefore ignores their quantities. Also, the list refers only to
     * purchased Generators, not all Generators.
     */
    public List<Generator> getGenerators() {
        List<Generator> generators = new ArrayList<Generator>();
        
        for (MonsterGeneratorLink link : this.generators) {
            Generator generator = link.generator;
            if (generator != null) {
                generators.add(generator);
            }
        }
        return generators;
    }
    
    /** 
     * Returns a list containing numbers that represent how many Generators of
     * each type the Monster has. This list is indexed respectively with the
     * list obtained by calling this Monster's @p getGeneratorLinks method, so 
     * its nth item corresponds to the getGeneratorLinks' list nth item.
     *
     * @see getGeneratorLinks
     */
    public List<Integer> getGeneratorsAmounts() {
        List<Integer> generators = new ArrayList<Integer>();
        
        for (MonsterGeneratorLink link : this.generators) {
            generators.add(link.getAmount());
        }
        return generators;
    }
      
    /** 
     * Returns the amount of Energy the Generators should give the Monster at 
     * each time interval, taking into account which Generators have been 
     * purchased, how many and how much energy each one generates.
     */
    public int totalEnergyPerInstant() {
        int count = 0;
        
        for (MonsterGeneratorLink link : this.generators) {
            count += link.totalEnergyPerInstant();
        }
        return count;
    }
     
    /** 
     * Checks if the Monster is able to buy the given Generator. If it is, 
     * subtracts the appropriate value from the Monster's energy and adds one 
     * more of that Generator to the Monster's stash, taking into account the 
     * appropriate actions if the Monster has never purchased a Generator of
     * that kind before.
     */
    public boolean purchase(Generator generator) {
        if (!this.mayPurchase(generator)){
            return false;
        }
        
        this.setEnergy(this.getEnergy() - generator.getCost());
        
        for (MonsterGeneratorLink link : this.generators) {
            if (link.generator.getId().equals(generator.getId())) {
                link.setAmount(link.getAmount() + 1);
                return true;
            }
        }
        //Não tem esse Generator ainda
        MonsterGeneratorLink link = new MonsterGeneratorLink(generator, this);
        link.save();
        generators.add(link);
        
        
        return true;
    }
        
    /** 
     * Checks whether or not the Monster is able to buy the given Generator
     * based on the Monster's available energy and the Generator's cost.
     */
    public boolean mayPurchase(Generator generator) {
        if (generator.getCost() <= this.getEnergy()) {
            return true;
        } else {
            return false;
        }
    }

        
    // Attributes
    /** 
     * Checks if the Mosnter has enough energy to increase the desired 
     * attribute. If it does, the attribute is increased.
     * @param attribute A value of the @p Attribute enum representing the 
     * attribute to increase.
     */
    public void trainAttribute(Attribute attribute) {
        int cost = this.attributeCost(attribute);
    
        if(this.storedEnergy >= cost) {
            this.storedEnergy -= cost;
            this.incrementAttribute(attribute);
        }
    }
    
    /** 
     * Returns the cost of increasing the Monster's given attribute.
     * @param attribute A value of the @p Attribute enum representing the 
     * attribute whose cost is desired.
     */
    public int attributeCost(Attribute attribute) {
        return this.getAttribute(attribute) * this.getAttribute(attribute);
    }
    /** 
     * Increments the Monster's given @p attribute by one. This method assumes
     * the Monster is allowed to increase the attribute, but does not require 
     * it.
     */
    private void incrementAttribute(Attribute attribute) {
        this.setAttribute(attribute, this.getAttribute(attribute) + 1);
    }

    // Energy
    /** 
     * Takes all the energy available in the energy pool and transfers it to the
     * Monster's own storage.
     */
    public void addEnergy(int energy) {
        this.setEnergy(this.getEnergy() + energy);
    }

    // Getters and Setters
	public List<Skill> getInventory() {
		return inventory;
	}
	
	public String getId() {
		return this.id;
	}

    public String getName() {
        return this.name;
    }
    
    public int getEnergy() {
        return this.storedEnergy;
    }
    
    public void setEnergy(int energy) {
        this.storedEnergy = energy;
    }

    /** 
     * @param attribute A value of the @p Attribute enum representing the 
     * attribute to get.
     * @return The value of the Monster's appropriate @p attribute.
     * This method throws an exception if the attribute is unknown.
     */
    public int getAttribute(Attribute attribute) {
        switch (attribute) {
            case STRENGTH:
                return this.getStrength();
            case DEXTERITY:
                return this.getDexterity();
            case WISDOM:
                return this.getWisdom();
            default:
                throw new IllegalArgumentException("The given attribute was not recognized.");
        }
    }
    
    /** 
     * @param attribute A value of the @p Attribute enum representing the 
     * attribute to set.
     * @param value The new value that the Monster's attribute should have.
     * This method throws an exception if the attribute is unknown.
     */
    public void setAttribute(Attribute Attribute, int valor)
    {
        switch (Attribute) {
            case STRENGTH:
                this.setStrength(valor);
                break;
            case DEXTERITY:
                this.setDexterity(valor);
                break;
            case WISDOM:
                this.setWisdom(valor);
                break;
            default:
                throw new IllegalArgumentException("The given attribute was not recognized.");
                
        }
    }

    private int getDexterity() {
        return dexterity;
    }
    private void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    private int getStrength() {
        return strength;
    }
    private void setStrength(int strength) {
        this.strength = strength;
    }

    private int getWisdom() {
        return wisdom;
    }
    private void setWisdom(int wisdom) {
        this.wisdom = wisdom;
    }

}
