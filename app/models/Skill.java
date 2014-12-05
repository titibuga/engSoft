/*
 * Skill.java
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
import play.db.ebean.Model.Finder;

import javax.persistence.Entity;
import javax.persistence.Id;

/** 
 * The Skill class represents a skill that a mosnter can have. It contains all
 * actions and information regarding skills and their attributes.
 */
@Entity
public class Skill extends Model {
    /** 
     * A Finder object used to fetch Skills from the database.
     */
    public static Finder<String,Skill> find = 
	new Finder<String,Skill>(String.class, Skill.class); 
    /** 
     * The id is used by the database to reference each Skill individually.
     */
    @Id
    public String id;	
	
    /** 
     * The minimum value required of the monster in its Dexterity 
     * attribute to be able to purchase this Skill.
     * @see requiredWisdom
     * @see requiredStrength 
     */
    private int requiredDexterity;
    /** 
     * The minimum value required of the monster in its Wisdom 
     * attribute to be able to purchase this Skill.
     * @see requiredDexterity
     * @see requiredStrength
     */
    private int requiredWisdom;
    /** 
     * The minimum value required of the monster in its Strength 
     * attribute to be able to purchase this Skill.
     * @see requiredDexterity
     * @see requiredWisdom
     */
    private int requiredStrength;

    /** 
     * The damage the Skill can do on an opposing monster.
     */
    private int damage;
    /** 
     * The cost of the Skill in the shop.
     */
    private int cost;
    /** 
     * The full name of the Skill.
     */
    private String name;
	
    // Constructor
    public Skill(String name, int damage, int cost) {
	super();
	this.requiredDexterity = 0;
	this.requiredStrength = 0;
	this.requiredWisdom = 0;
	this.name = name;
	this.damage = damage;
	this.cost = cost;
    }
	
    // Getters and Setters
    public String getId() {
	return this.id;
    }

    public int getRequiredDexterity() {
	return requiredDexterity;
    }
    public void setRequiredDexterity(int requiredDexterity) {
	this.requiredDexterity = requiredDexterity;
    }
    public int getRequiredWisdom() {
	return this.requiredWisdom;
    }
    public void setRequiredWisdom(int requiredWisdom) {
	this.requiredWisdom = requiredWisdom;
    }
    public int getRequiredStrength() {
	return this.requiredStrength;
    }
    public void setRequiredStrength(int requiredStrength) {
	this.requiredStrength = requiredStrength;
    }

    public int getCost() {
	return this.cost;
    }
    public void setCost(int cost) {
	this.cost = cost;
    }

    public int getDamage() {
	return this.damage;
    }
    public void setDamage(int damage) {
	this.damage = damage;
    }

    public String getName() {
	return this.name;
    }
    public void setName(String name) {
	this.name = name;
    }
}
