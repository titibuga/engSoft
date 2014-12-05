/*
 * Generator.java
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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/** 
 * The Generator class represents an in-game item that generates energy for the
 * Monster at regular (externally defined) intervals.
 */
@Entity
public class Generator extends Model {
		
    /** 
     * The ID used in the database.
     */
    @Id
    private String id;
		
    /** 
     * The name of this Generator to be displayed.
     */
    private String name;

    /** 
     * The cost of this Generator in the Generator shop.
     */
    private int cost;

    /** 
     * The amount of energy generated at each interval.
     */
    private int energyPerInstant;
		
    /** 
     * The Finder used by the database.
     */
    public static Finder<String,Generator> find =
	new Finder<String,Generator>(String.class, Generator.class);
		

    // Constructors
    public Generator() {
	this.setCost(1);
	this.setEnergyPerInstant(1);
    }
		
    public Generator(String name, int cost, int energy) {
	this.setName(name);			
	this.setCost(cost);
	this.setEnergyPerInstant(energy);
    }
		
    // Setters and Getters
    public String getName() {
	return this.name;
    }

    public void setName(String name) {
	this.name = name;
    }		

    public String getId() {
	return this.d;
    }

    public int getCost() {
	return this.cost;
    }
    public void setCost(int cost) {
	this.cost = cost;
    }
    public int getEnergyPerInstant() {
	return this.energyPerInstant;
    }
    public void setEnergyPerInstant(int energyPerInstant) {
	this.energyPerInstant = energyPerInstant;
    }
}
