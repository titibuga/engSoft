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
	public int dex, str, wis;
	
	
	public static Finder<String,Monstro> find = new Finder<String,Monstro>(
			    String.class, Monstro.class
			  ); 

	
	public String getId()
	{
		return this.id;
	}
	
	
	
	
	
	public int getDex() {
		return dex;
	}
	public void setDex(int dex) {
		this.dex = dex;
	}
	
	public void treinaDex()
	{
		int custo = this.custoDex();
		if(this.energia >= custo)
		{
			this.energia -= custo;
			this.dex++;
		}
	}
	
	public int custoDex()
	{
		return this.dex*this.dex;
	}
	
	
	





	public int getStr() {
		return str;
	}
	public void setStr(int str) {
		this.str = str;
	}





	public int getWis() {
		return wis;
	}
	public void setWis(int wis) {
		this.wis = wis;
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
