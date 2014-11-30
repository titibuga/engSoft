package models;


import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Monstro extends Model {

    public enum Atributo {
        STR, DEX, WIS
    }
    
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
	
	public void treinaAtributo(Atributo atributo)
	{
        int custo = this.custoAtributo(atributo);
    
		if(this.energia >= custo)
		{
			this.energia -= custo;
			this.aumentaAtributo(atributo);
		}
	}
    
	public int custoAtributo(Atributo atributo)
	{
		return this.getAtributo(atributo) * this.getAtributo(atributo);
	}
	
    public int incrementaAtributo(Atributo atributo)
    {
        int valorAntigo = this.getAtributo(atributo);
        int valorNovo = valorAntigo + 1;
        this.setAtributo(atributo, valorNovo);
    }

    public int getAtributo(Atributo atributo)
    {
        switch (atributo) {
            case STR:
                return this.getStr();
                break;
            case DEX:
                return this.getDex();
                break;
            case WIS:
                return this.getWis();
                break;
            default:
                throw new IllegalArgumentException("O atributo passado para o getAtributo não foi reconhecido.");
                break;
        }
    }
    
    public int setAtributo(Atributo atributo, int valor)
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
                break;
        }
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
        int novaEnergia = this.getEnergia() + energia;
        this.setEnergia(novaEnergia);
	}

}
