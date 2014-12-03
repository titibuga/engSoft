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
     * Creates a set of default initial Generators to display at the shop.
     */
    public static Result criaGeradoresIniciais() {
        List<Gerador> geradores = Gerador.find.all();
        Gerador g;
        
        if (geradores.size() != 0) {
            return ok(index.render("Já existem geradores no BD"));
        }
        
        g = new Gerador("Gerador básico", 10, 2);
        g.save();
        
        g = new Gerador("Gerador não-tão-básico", 20, 5);
        g.save();
        
        g = new Gerador("Super Gerador básico", 100, 40);
        g.save();
        
        return ok(index.render("Geradores criados"));
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
     * The method used to render the Skills shop.
     */
    public static Result shop(String id) {
    	Monstro mon = Monstro.find.byId(id);
        List<Habilidade> habilidades = Habilidade.find.all();
    	return ok(loja.render(mon, habilidades));
    }

    /**
     * The method used to render the Generators shop.
     */
    public static Result shop2(String id) {
        Monstro mon = Monstro.find.byId(id);
        List<Gerador> geradores = Gerador.find.all();
        
        return ok(loja2.render(mon, geradores));
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
     * Handles the request to purchase a Skill.
     */
    public static Result compraDeHabilidade() {
        DynamicForm data = Form.form().bindFromRequest();
        Monstro	mon = Monstro.find.byId(data.get("mId"));
        Habilidade h = Habilidade.find.byId(data.get("habId"));
        
        mon.compra(h);
        mon.save();
        
        return redirect("/monstro/"+data.get("mId")+"/shop");
    }

    /**
     * Handles the request to purchase a Generator.
     */
    public static Result compraDeGerador() {
        DynamicForm data = Form.form().bindFromRequest();
        Monstro	mon = Monstro.find.byId(data.get("mId"));
        Gerador g = Gerador.find.byId(data.get("gerId"));
        
        mon.compra(g);
        mon.save();
        
        return redirect("/monstro/"+data.get("mId")+"/shop2");
    }
    
    /**
     * Creates a new Monster.
     */
    public static Result novo() {
        Monstro mon = new Monstro("Monstro Genérico");
        
        mon.save();
        
        return ok(teste.render(mon));
    }
    
    /**
     * Default scenario for when the rendering of another screen fails for some
     * reason.
     */
    public static Result falha() {
        return ok(index.render("Monstro com o Id passado não existe"));
    }
    
    /** 
     * Handles a login request, ie the request to "open" a Monster.
     */
    public static Result login() {
        DynamicForm data = Form.form().bindFromRequest();
        
        try {
            int mid = Integer.parseInt(data.get("mId"));
        } catch(NumberFormatException nfe) {
            return redirect("/falha");
        } 
        
        return redirect("/monstro/"+data.get("mId"));
    }
    
    
    /** 
     * Renders the monster.
     */
    public static Result mostraMonstro(String id) {
    	Monstro mon = Monstro.find.byId(id);;
    	if(mon == null)
    		return redirect("/falha");
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
