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

}
