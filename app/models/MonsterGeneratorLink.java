/*
 * MonsterGeneratorLink.java
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

import java.io.Serializable;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;
import models.*;

import javax.persistence.*;


/** 
 * The MonsterGeneratorLink class represents a link between a Monster and a 
 * Generator. Its existence means that the Monster currently posesses at least
 * one of that Generator type. The class is ideal for supplying information 
 * pertaining to that specific relationship but not particularly to the Monster 
 * or the Generator - for instance, how many Generators of that kind the Monster
 * has purchased. It is also used to enable such an attributed relationship to
 * exist in the database.
 */
@Entity
public class MonsterGeneratorLink extends Model{
	
    /** 
     * The key used to store the information about the relationship. The 
     * attributes (such as the amount of Generators the Monster has) are stored
     * and handled by the MonsterGeneratorLink itself; the information about
     * the relationship pertaining the database is stored in the key.
     */
    @EmbeddedId
    public MonsterGeneratorKey key;
	
    /** 
     * The Monster this link refers to.
     */
    @ManyToOne
    @JoinColumn(name = "monster_id", referencedColumnName = "id", 
		nullable = false, insertable=false, updatable=false) 
		public Monster mon;

    /** 
     * The Generator this link refers to.
     */
    @ManyToOne
    @JoinColumn(name = "generator_id", referencedColumnName = "id",
		nullable = false, insertable=false, updatable=false) 
		public Generator generator;
	
    /** 
     * The amount of Generators of this Link's type the Monster has purchased.
     */
    private int amount;
	

    // Constructors
    public MonsterGeneratorLink(Generator generator, Monster mon) {
	super();
	this.key = new MonsterGeneratorKey(generator.getId(), mon.getId());
	this.amount = 1;	
    }
	
    // Setters and Getters
    public int getAmount() {
	return this.amount;
    }

    public void setAmount(int amount) {
	this.amount = amount;
    }
	
    public String getGeneratorId() {
	return this.key.generatorId;
    }
	
    public String getMonsterId() {
	return this.key.monsterId;
    }

    public int totalEnergyPerInstant() {
	return this.generator.getEnergyPerInstant()*this.getAmount();
    }
	
    public Generator getGenerator() {
	return this.generator;
    }



    /** 
     * The MonsterGeneratorKey is used privately to encapsulate the information
     * pertaining the database such as the IDs and Keys.
     */
    @Embeddable
    public class MonsterGeneratorKey implements Serializable{

	private static final long serialVersionUID = 1L;

	/** 
	 * The Id of the Generator to which this Key refers.
	 */
	@Column(name = "generator_id")
	public String generatorId;
	/** 
	 * The Id of the Monster to which this Key refers.
	 */
	@Column(name = "monster_id")
	public String monsterId;
		
	public MonsterGeneratorKey(String generatorId, String monsterId) {
	    super();
	    this.generatorId = generatorId;
	    this.monsterId = monsterId;
	}
		
	/** 
	 * Overrides the equals method to enable usage of both Generator and
	 * Monster IDs as the primary key.
	 */
	@Override
	public boolean equals(Object obj) {
	    if (obj == null) {
		return false;
	    }
	    if (getClass() != obj.getClass()) {
		return false;
	    }
	    final MonsterGeneratorKey other = (MonsterGeneratorKey) obj;
	    if ((this.generatorId == null) ?
		(other.generatorId != null) :
		!this.generatorId.equals(other.generatorId)) {
		return false;
	    }
	    if ((this.monsterId == null) ?
		(other.monsterId != null) :
		!this.monsterId.equals(other.monsterId)) {
		return false;
	    }
	    return true;
	}
		
	/** 
	 * Overrides the hashCode method to enable usage of both Generator and
	 * Monster IDs as the primary key.
	 */
	@Override
	public int hashCode() {
	    int hash = 3;
	    hash = 89 * hash + (this.generatorId != null ? 
				this.generatorId.hashCode() : 0);
	    hash = 89 * hash + (this.monsterId != null ? 
				this.monsterId.hashCode() : 0);
	    return hash;
	}
     }
}
	
	

	



