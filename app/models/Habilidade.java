/*
 * Habilidade.java
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
public class Habilidade extends Model {
	/** 
	 * A Finder object used to fetch Skills from the database.
	 */
	public static Finder<String,Habilidade> find = 
			new Finder<String,Habilidade>(String.class, Habilidade.class); 
	/** 
	* The id is used by the database to reference each Skill individually.
	*/
	@Id
	public String id;	
	
	/** 
	 * The minimum value required of the monster in its Dexerity 
	 * attribute to be able to purchase this Skill.
	 * @see minWis
	 * @see minStr 
	 */
	private int minDex;
	/** 
	 * The minimum value required of the monster in its Wisdom 
	 * attribute to be able to purchase this Skill.
	 * @see minDex
	 * @see minStr
	 */
	private int minWis;
	/** 
	 * The minimum value required of the monster in its Strength 
	 * attribute to be able to purchase this Skill.
	 * @see minDex
	 * @see minWis
	 */
	private int minStr;

	/** 
	 * The damage the Skill can do on an opposing monster.
	 */
	private int dano;
	/** 
	 * The cost of the Skill in the shop.
	 */
	private int custo;
	/** 
	 * The full name of the Skill.
	 */
	private String nome;
	
	/* Constructor */
	public Habilidade(String nome, int dano, int custo) {
		super();
		this.minDex = this.minStr = this.minWis = 0;
		this.nome = nome;
		this.dano = dano;
		this.custo = custo;
	}
	
	/* Getters and Setters */
	public String getId() {
		return this.id;
	}

	public int getMinDex() {
		return minDex;
	}
	public void setMinDex(int minDex) {
		this.minDex = minDex;
	}
	public int getMinWis() {
		return minWis;
	}
	public void setMinWis(int minWis) {
		this.minWis = minWis;
	}
	public int getMinStr() {
		return minStr;
	}
	public void setMinStr(int minStr) {
		this.minStr = minStr;
	}

	public int getCusto() {
		return custo;
	}
	public void setCusto(int custo) {
		this.custo = custo;
	}

	public int getDano() {
		return dano;
	}
	public void setDano(int dano) {
		this.dano = dano;
	}

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
}
