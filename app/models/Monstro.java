package models;


import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Monstro extends Model {

	/**
	 
	private static final long serialVersionUID = 3799161690136050003L;
	* 
	 */

	@Id
	public String id;
	
	public String nome;
	public int energia;
	
	
	public static Finder<String,Monstro> find = new Finder<String,Monstro>(
			    String.class, Monstro.class
			  ); 

	
	public String getId()
	{
		return this.id;
	}
	
	
	
	public String getNome()
	{
		return this.nome;
	}
	public void setNome(String nome)
	{
		this.nome = nome;
	}
	
	public int getEnergia()
	{
		return this.energia;
	}
	
	public void setEnergia(int energia)
	{
		this.energia = energia;
	}
	
	public void somaEnergia(int energia)
	{
		this.energia += energia;
	}

}
