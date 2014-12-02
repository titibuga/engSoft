package models;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Gerador extends Model {
		
		@Id
		private String id;
		
		private String nome;
		private int custo;
		private int energiaPorUni;
		
		public static Finder<String,Gerador> find = new Finder<String,Gerador>(String.class, Gerador.class);
		
		
		
		
		
		
	

		public Gerador()
		{
			// Valores padrão
			this.setCusto(1);
			this.setEnergiaPorUni(1);
			
		}
		
	

		public Gerador(String nome, int custo, int energ)
		{
			this.setNome(nome);			
			this.setCusto(custo);
			this.setEnergiaPorUni(energ);
		}
		
		
		public String getNome() {
			return nome;
		}

		public void setNome(String nome) {
			this.nome = nome;
		}		
		public String getId() {
			return id;
		}
		public int getCusto() {
			return custo;
		}
		public void setCusto(int custo) {
			this.custo = custo;
		}
		public int getEnergiaPorUni() {
			return energiaPorUni;
		}
		public void setEnergiaPorUni(int energiaPorUni) {
			this.energiaPorUni = energiaPorUni;
		}
	
	

}
