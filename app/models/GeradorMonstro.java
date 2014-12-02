package models;

import java.io.Serializable;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;
import models.*;

import javax.persistence.*;



@Entity
public class GeradorMonstro extends Model{
	
	@EmbeddedId
	public GerMonKey key;
	private int qtd;
	
	@ManyToOne
	@JoinColumn(name = "mon_id", referencedColumnName = "id", nullable = false,insertable=false, updatable=false) 
	public Monstro mon;
	@ManyToOne
	@JoinColumn(name = "ger_id", referencedColumnName = "id", nullable = false,insertable=false, updatable=false) 
	public Gerador ger;
	
		
	
	
	public GeradorMonstro(Gerador ger, Monstro mon)
	{
		super();
		this.key = new GerMonKey(ger.getId(), mon.getId());
		this.qtd = 1; // Quantidade padrão quando criado
		
	}
	
	
	//Classe que define a chave primaria dessa relação

	public int getQtd() {
		return qtd;
	}


	public void setQtd(int qtd) {
		this.qtd = qtd;
	}
	
	public String getIdGer()
	{
		return this.key.gerId;
	}
	
	public String getIdMon()
	{
		return this.key.monId;
	}
	public int getEnergiaPorUni()
	{
		return this.ger.getEnergiaPorUni()*this.getQtd();
	}
	


	public Gerador getGerador() {
		return ger;
	}




	@Embeddable
	public class GerMonKey implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		@Column(name = "ger_id")
		public String gerId;
		@Column(name = "mon_id")
		public String monId;
		
		public GerMonKey(String gerId, String monId)
		{
			super();
			this.gerId = gerId;
			this.monId = monId;
		}
		
		
		@Override
		public boolean equals(Object obj) {
	        if (obj == null) {
	            return false;
	        }
	        if (getClass() != obj.getClass()) {
	            return false;
	        }
	        final GerMonKey other = (GerMonKey) obj;
	        if ((this.gerId == null) ? (other.gerId != null) : !this.gerId.equals(other.gerId)) {
	                return false;
	            }
	        if ((this.monId == null) ? (other.monId != null) : !this.monId.equals(other.monId)) {
	            return false;
	        }
	        return true;
	    }
		
		@Override
	    public int hashCode() {
	        int hash = 3;
	        hash = 89 * hash + (this.gerId != null ? this.gerId.hashCode() : 0);
	        hash = 89 * hash + (this.monId != null ? this.monId.hashCode() : 0);
	        return hash;
	    }
	
		
	}
}
	
	

	



