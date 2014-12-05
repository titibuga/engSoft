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
        return ok(index.render("PREPARE FOR ADVENTUUUUUUUURE!!"));
    }

    /** 
     * The method used to render the Skills shop.
     */
    public static Result skillShop(String id) {
    	Monster monster = Monster.find.byId(id);
        List<Skill> skills;

        createInitialSkills();

        skills = Skill.find.all();

    	return ok(skillShop.render(monster, skills));
    }

    /**
     * The method used to render the Generators shop.
     */
    public static Result generatorShop(String id) {
        Monster monster = Monster.find.byId(id);
        List<Generator> generators;

        createInitialGenerators();
        
        generators = Generator.find.all();

        return ok(generatorShop.render(monster, generators));
    }

    /** 
     * Executes the absorption of energy from the pool to the monster.
     */
    public static Result absorb() {
    	DynamicForm dynamicForm = Form.form().bindFromRequest();
    	Monster monster = Monster.find.byId(dynamicForm.get("mId"));

    	monster.addEnergy(Integer.parseInt(dynamicForm.get("pontos")));
    	monster.save();

    	return redirect("/monster/"+dynamicForm.get("mId"));
    }
    
    /** 
     * Handles the request to purchase a Skill.
     */
    public static Result purchaseSkill() {
        DynamicForm data = Form.form().bindFromRequest();
        Monster	monster = Monster.find.byId(data.get("mId"));
        Skill skill = Skill.find.byId(data.get("habId"));
        
        monster.purchase(skill);
        monster.save();
        
        return redirect("/monster/"+data.get("mId")+"/skillShop");
    }

    /**
     * Handles the request to purchase a Generator.
     */
    public static Result purchaseGenerator() {
        DynamicForm data = Form.form().bindFromRequest();
        Monster	monster = Monster.find.byId(data.get("mId"));
        Generator generator = Generator.find.byId(data.get("gerId"));
        
        monster.purchase(generator);
        monster.save();
        
        return redirect("/monster/"+data.get("mId")+"/generatorShop");
    }
    
    /**
     * Creates a new Monster.
     */
    public static Result newMonster() {
        Monster monster = new Monster("Skull Greymon");
        
        monster.save();
        
        return ok(init.render(monster));
    }
    
    /**
     * Default scenario for when the rendering of another screen fails.
     */
    public static Result fail() {
        return ok(index.render("There is no monster with the given id."));
    }
    
    /** 
     * Handles a login request, ie the request to "open" a Monster.
     */
    public static Result login() {
        DynamicForm data = Form.form().bindFromRequest();
        
        try {
            int mid = Integer.parseInt(data.get("mId"));
        } catch(NumberFormatException nfe) {
            return redirect("/fail");
        } 
        
        return redirect("/monster/"+data.get("mId"));
    }
    
    
    /** 
     * Renders the monster.
     */
    public static Result renderMonster(String id) {
    	Monster monster = Monster.find.byId(id);;
    	if(monster == null)
    		return redirect("/fail");
    	return ok(init.render(monster));
    }
    
    /** 
     * Attempts to train the Monster'skill given attribute (must check if the
     * Monster is able to train that attribute first).
     */
    public static Result train(String type) {
    	DynamicForm data = Form.form().bindFromRequest();
    	Monster	monster = Monster.find.byId(data.get("mId"));

    	if (type.equals("str")) {
    		monster.trainAttribute(Monster.Attribute.STRENGTH);
        } else if (type.equals("dex")) {
    		monster.trainAttribute(Monster.Attribute.DEXTERITY);
    	} else if (type.equals("wis")) {
    		monster.trainAttribute(Monster.Attribute.WISDOM);
        }

    	monster.save();
    	
    	return redirect("/monster/"+data.get("mId"));
    }

    /** 
     * Creates a set of default initial Skills to display at the shop.
     */
    private static void createInitialSkills() {
        List<Skill> skills = Skill.find.all();
        Skill skill;

        if (skills.size() == 0) {
            skill = new Skill("Tackle", 10, 10);
            skill.save();
            
            skill = new Skill("Quick Attack", 10, 20);
            skill.setRequiredDexterity(10);
            skill.save();
            
            skill = new Skill("Hyper Beam", 666, 20);
            skill.setRequiredWisdom(50);
            skill.save();
        }
    }
    
    /**
     * Creates a set of default initial Generators to display at the shop.
     */
    private static void createInitialGenerators() {
        List<Generator> generators = Generator.find.all();
        Generator generator;
        
        if (generators.size() == 0) {
            generator = new Generator("Wind Turbine", 10, 2);
            generator.save();
            
            generator = new Generator("Solar Panel", 20, 5);
            generator.save();
            
            generator = new Generator("Nuclear Station", 100, 40);
            generator.save();
        }
    }
}
