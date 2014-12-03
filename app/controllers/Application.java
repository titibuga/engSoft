/*
 * Application.java
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

package controllers;

import java.util.List;
import play.*;
import play.data.Form;
import play.data.DynamicForm;
import play.mvc.*;

import views.html.*;
import models.*;

/** 
 * The Application class is the main Controller. It is used to control the main 
 * flow of the application and to haldle callbacks from buttons.
 */
public class Application extends Controller  {

    /** 
     * The main method called when the application starts.
     */
    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }
    
    /** 
     * Creates a set of default initial Skills to display at the shop.
     */
    public static Result criaHabilidadesIniciais() {
    	List<Habilidade> habilidades = Habilidade.find.all();
        Habilidade h;

    	if (habilidades.size() != 0) {
            return ok(index.render("Já existem habilidades no BD"));
        }
    	
    	h = new Habilidade("Patada", 10, 10);
    	h.save();
    	
    	h = new Habilidade("Patada inteligente", 10, 20);
    	h.setMinWis(10);
    	h.save();
    	
    	h = new Habilidade("VEM MONSTRO", 666, 20);
    	h.setMinStr(50);
    	h.save();
    	
    	return ok(index.render("Habilidades criadas"));
    }
    
    /** 
     * Executes the purchase of a skill, updates the database and the local 
     * objects.
     */
    public static Result compraDeHabilidade() {
        DynamicForm data = Form.form().bindFromRequest();
        Habilidade h = Habilidade.find.byId(data.get("habId"));
        Monstro mon = Monstro.find.byId(data.get("mId"));

        mon.compraHabilidade(h);
        mon.save();

        return redirect("/monstro/"+data.get("mId")+"/shop");
    }

    /** 
     * The method used to render the shop.
     */
    public static Result shop(String id) {
    	Monstro mon = Monstro.find.byId(id);
        List<Habilidade> habilidades = Habilidade.find.all();

    	return ok(loja.render(mon, habilidades));
    }
    
    /** 
     * Renders a test screen.
     */
    public static Result teste1() {
    	Monstro mon = new Monstro("Leo Stronda");

    	mon.save();

    	return ok(teste.render(mon));
    }

    /** 
     * Executes the absorption of energy from the pool to the monster.
     */
    public static Result absorve() {
    	DynamicForm dynamicForm = Form.form().bindFromRequest();
    	Monstro mon = Monstro.find.byId(dynamicForm.get("mId"));

    	mon.somaEnergia(Integer.parseInt(dynamicForm.get("pontos")));
    	mon.save();

    	return redirect("/monstro/"+dynamicForm.get("mId"));
    }
    
    /** 
     * Renders the monster.
     */
    public static Result mostraMonstro(String id) {
    	Monstro mon = Monstro.find.byId(id);;

    	return ok(teste.render(mon));
    }
    
    /** 
     * Attempts to train the Monster's given attribute (must check if the
     * Monster is able to train that attribute first).
     */
    public static Result treina(String tipo) {
    	DynamicForm data = Form.form().bindFromRequest();
    	Monstro	mon = Monstro.find.byId(data.get("mId"));

    	if (tipo.equals("str")) {
    		mon.treinaAtributo(Monstro.Atributo.STR);
        } else if (tipo.equals("dex")) {
    		mon.treinaAtributo(Monstro.Atributo.DEX);
    	} else if (tipo.equals("wis")) {
    		mon.treinaAtributo(Monstro.Atributo.WIS);
        }

    	mon.save();
    	
    	return redirect("/monstro/"+data.get("mId"));
    }
}
