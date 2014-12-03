/*
 * Monstro.java
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

import models.Habilidade;
import java.util.*;


/** 
 * The Monster class represents a Monster that a player may use, which is 
 * the main form of interaction of the player with the game world.
 */
@Entity
public class Monstro extends Model {
    /** 
     * The Attribute enum is used to execute operations regarding the Monster's
     * individual attributes with ease. The three values (STR, DEX and WIS) 
     * represent Strength, Dexerity and Wisdom respectively.
     */
    public static enum Atributo {
        STR, DEX, WIS
    }

    /** 
     * A Finder object used to fetch Skills from the database.
     */
    public static Finder<String,Monstro> find = 
            new Finder<String,Monstro>(String.class, Monstro.class); 
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
	public List<Habilidade> inventario = new ArrayList<Habilidade>();
    /** 
     * The list of Generators contains all the Generators that have been 
     * purchased andare currently active, generating more energy for the player.
     */
    @OneToMany(mappedBy = "mon", cascade = CascadeType.ALL)
    public List<GeradorMonstro> geradores;
    
    /** 
     * The Monster's name, which may be displayed at the top of the screen.
     */
	public String nome;
    /** 
     * The Monster's current stored energy points.
     */
	public int energia;
    /** 
     * The Dexerity attribute.
     */
	public int dex;
    /** 
     * The Strength attribute.
     */
    public int str;
    /** 
     * The Wisdom attribute.
     */
    public int wis;
    
	
	// Constructor
	public Monstro(String nome) {
		super();
		this.nome = nome;
		this.energia = 0;
		this.dex = 1;
		this.str = 1;
		this.wis = 1;
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
	public boolean compraHabilidade(Habilidade h) {
		if (this.podeComprar(h)) {
			this.setEnergia(this.getEnergia() - h.getCusto());
			this.inventario.add(h);
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
	public boolean podeComprar(Habilidade h) {
		if (this.getDex() < h.getMinDex()
                || this.getStr() < h.getMinStr()
                || this.getWis() < h.getMinWis() 
                || this.getEnergia() < h.getCusto()) {
			return false;
        }
		if (temHabilidade(h)) {
			return false;
        }
		return true;
	}
	
    /** 
     * Check if the Monster has the given skill in its inventory.
     * @param skill The skill to check.
     * @return @p true if the skill is present, @p false otherwise.
     */
	public boolean temHabilidade(Habilidade h) {
	    for (Habilidade h2 : this.getInventario()) {
	        if (h2.getId() == h.getId()) {
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
    public List<GeradorMonstro> getListaGeradores() {
        return this.geradores;
    }
    
    /** 
     * Returns a list of all Generators linked to this Monster via a 
     * MonsterGeneratorLink. The list contains one generator of each type, and
     * therefore ignores their quantities. Also, the list refers only to
     * purchased Generators, not all Generators.
     */
    public List<Gerador> getGeradores() {
        List<Gerador> geradores = new ArrayList<Gerador>();
        
        for (GeradorMonstro gm : this.geradores) {
            Gerador g = gm.ger;
            if (g != null) {
                geradores.add(g);
            }
        }
        return geradores;
    }
    
    /** 
     * Returns a list containing numbers that represent how many Generators of
     * each type the Monster has. This list is indexed respectively with the
     * list obtained by calling this Monster's @p getListaGeradores method, so 
     * its nth item corresponds to the getListaGeradores' list nth item.
     *
     * @see getListaGeradores
     */
    public List<Integer> getQtdGeradores() {
        List<Integer> geradores = new ArrayList<Integer>();
        
        for (GeradorMonstro gm : this.geradores) {
            geradores.add(gm.getQtd());
        }
        return geradores;
    }
        
    /** 
     * Returns the amount of Energy the Generators should give the Monster at 
     * each time interval, taking into account which Generators have been 
     * purchased, how many and how much energy each one generates.
     */
    public int getEnergiaPorUni() {
        int count = 0;
        
        for (GeradorMonstro gm : this.geradores) {
            count += gm.getEnergiaPorUni();
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
    public boolean compra(Gerador g) {
        if (!this.podeComprar(g)){
            return false;
        }
        
        this.setEnergia(this.getEnergia() - g.getCusto());
        
        for (GeradorMonstro gm : this.geradores) {
            if (gm.ger.getId().equals(g.getId())) {
                gm.setQtd(gm.getQtd() + 1);
                return true;
            }
        }
        //Não tem esse gerador ainda
        GeradorMonstro gm = new GeradorMonstro(g, this);
        gm.save();
        geradores.add(gm);
        
        
        return true;
    }
        
    /** 
     * Checks whether or not the Monster is able to buy the given Generator
     * based on the Monster's available energy and the Generator's cost.
     */
    public boolean podeComprar(Gerador g) {
        if (g.getCusto() <= this.getEnergia()) {
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
    public void treinaAtributo(Atributo atributo) {
        int custo = this.custoAtributo(atributo);
    
        if(this.energia >= custo) {
            this.energia -= custo;
            this.incrementaAtributo(atributo);
        }
    }
    
    /** 
     * Returns the cost of increasing the Monster's given attribute.
     * @param attribute A value of the @p Attribute enum representing the 
     * attribute whose cost is desired.
     */
    public int custoAtributo(Atributo atributo) {
        return this.getAtributo(atributo) * this.getAtributo(atributo);
    }
    /** 
     * Increments the Monster's given @p attribute by one. This method assumes
     * the Monster is allowed to increase the attribute, but does not require 
     * it.
     */
    private void incrementaAtributo(Atributo atributo) {
        this.setAtributo(atributo, this.getAtributo(atributo) + 1);
    }

    // Energy
    /** 
     * Takes all the energy available in the energy pool and transfers it to the
     * Monster's own storage.
     */
    public void somaEnergia(int energia) {
        this.setEnergia(this.getEnergia() + energia);
    }

    // Getters and Setters
	public List<Habilidade> getInventario() {
		return inventario;
	}
	
	public String getId() {
		return this.id;
	}

    public String getNome() {
        return this.nome;
    }
    
    public int getEnergia() {
        return this.energia;
    }
    
    public void setEnergia(int energia) {
        this.energia = energia;
    }

    /** 
     * @param attribute A value of the @p Attribute enum representing the 
     * attribute to get.
     * @return The value of the Monster's appropriate @p attribute.
     * This method throws an exception if the attribute is unknown.
     */
    public int getAtributo(Atributo atributo) {
        switch (atributo) {
            case STR:
                return this.getStr();
            case DEX:
                return this.getDex();
            case WIS:
                return this.getWis();
            default:
                throw new IllegalArgumentException("O atributo passado para o getAtributo não foi reconhecido.");
        }
    }
    
    /** 
     * @param attribute A value of the @p Attribute enum representing the 
     * attribute to set.
     * @param value The new value that the Monster's attribute should have.
     * This method throws an exception if the attribute is unknown.
     */
    public void setAtributo(Atributo atributo, int valor)
    {
        switch (atributo) {
            case STR:
                this.setStr(valor);
                break;
            case DEX:
                this.setDex(valor);
                break;
            case WIS:
                this.setWis(valor);
                break;
            default:
                throw new IllegalArgumentException("O atributo passado para o setAtributo não foi reconhecido.");
                
        }
    }

    private int getDex() {
        return dex;
    }
    private void setDex(int dex) {
        this.dex = dex;
    }

    private int getStr() {
        return str;
    }
    private void setStr(int str) {
        this.str = str;
    }

    private int getWis() {
        return wis;
    }
    private void setWis(int wis) {
        this.wis = wis;
    }

}
