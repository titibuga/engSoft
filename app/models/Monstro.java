package models;


import play.db.ebean.Model;

import javax.persistence.*;

import models.Habilidade;
import java.util.*;

@Entity
public class Monstro extends Model {

    public static enum Atributo {
        STR, DEX, WIS
    }
    
	/**
	 
	private static final long serialVersionUID = 3799161690136050003L;
	* 
	 */

	@Id
	public String id;
	
	@ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	public List<Habilidade> inventario = new ArrayList<Habilidade>();
	
	public String nome;
	public int energia;
	public int dex, str, wis;
	
	
	@OneToMany(mappedBy = "mon", cascade = CascadeType.ALL)
	public List<GeradorMonstro> geradores;
	
	
	
	public Monstro(String nome)
	{
		super();
		this.nome = nome;
		this.energia = 0;
		this.dex = 1;
		this.str = 1;
		this.wis = 1;
	}

	public static Finder<String,Monstro> find = new Finder<String,Monstro>(String.class, Monstro.class); 

	
	
	public boolean compra(Habilidade h)
	{
		if(this.podeComprar(h))
		{
			this.setEnergia(this.getEnergia() - h.getCusto());
			this.inventario.add(h);
			return true;
		}
		return false;
	}
	
	
	public List<GeradorMonstro> getListaGeradores()
	{
		return this.geradores;
	}
	
	public List<Gerador> getGeradores()
	{
		
		List<Gerador> geradores = new ArrayList<Gerador>();
		
		for(GeradorMonstro gm : this.geradores)
		{
			Gerador g = gm.ger;
			if(g != null)
				geradores.add(g);
		}
		return geradores;
	}
	
	
	public List<Integer> getQtdGeradores()
	{
		List<Integer> geradores = new ArrayList<Integer>();
		
		for(GeradorMonstro gm : this.geradores)
		{
			geradores.add(gm.getQtd());
		}
		return geradores;
	}
	
	
	public int getEnergiaPorUni()
	{
		int count = 0;
		
		for(GeradorMonstro gm : this.geradores)
			count += gm.getEnergiaPorUni();
		return count;
		
	}
	
	
	public boolean compra(Gerador g)
	{
		if(!this.podeComprar(g)) return false;
		
		this.setEnergia(this.getEnergia() - g.getCusto());
		
		for(GeradorMonstro gm : this.geradores)
		{
			if(gm.ger.getId().equals(g.getId()))
			{
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
	
	
	public boolean podeComprar(Gerador g)
	{
		if(g.getCusto() <= this.getEnergia()) return true;
		else return false;
	}
	
	
	public boolean podeComprar(Habilidade h)
	{
		if(this.getDex() < h.getMinDex() || this.getStr() < h.getMinStr() || this.getWis() < h.getMinWis() || this.getEnergia() < h.getCusto())
			return false;
		if(temHabilidade(h))
			return false;
		return true;
	}
	
	public boolean temHabilidade(Habilidade h)
	{
	    for(Habilidade h2 : this.getInventario())
	        if(h2.getId() == h.getId()) return true;
	    return false;
	}
	
	public List<Habilidade> getInventario()
	{
		return inventario;
	}
	
	
	public String getId()
	{
		return this.id;
	}
	
	public int getDex() {
		return this.dex;
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
			this.incrementaAtributo(atributo);
		}
	}
    
	public int custoAtributo(Atributo atributo)
	{
		return this.getAtributo(atributo) * this.getAtributo(atributo);
	}
	
    public void incrementaAtributo(Atributo atributo)
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
            case DEX:
                return this.getDex();
            case WIS:
                return this.getWis();
            default:
                throw new IllegalArgumentException("O atributo passado para o getAtributo não foi reconhecido.");
        }
    }
    
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
	
	/*public void setNome(String nome)
	{
		this.nome = nome;
	}*/
	
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
