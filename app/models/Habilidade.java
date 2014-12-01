package models;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class Habilidade extends Model {
	
	@Id
	public String id;
	
	private int minDex, minWis, minStr;
	private int dano;
	private int custo;
	private String nome;
	
	
	public static Finder<String,Habilidade> find = new Finder<String,Habilidade>(String.class, Habilidade.class); 
	
	
	public Habilidade(String nome, int dano, int custo)
	{
		this.minDex = this.minStr = this.minWis = 0;
		this.nome = nome;
		this.dano = dano;
		this.custo = custo;
	}
	
	
	public int getMinDex() {
		return minDex;
	}
	public int getCusto() {
		return custo;
	}
	public void setCusto(int custo) {
		this.custo = custo;
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
