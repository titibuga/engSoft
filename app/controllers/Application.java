package controllers;

import play.*;
import play.data.Form;
import play.data.DynamicForm;
import play.mvc.*;

import views.html.*;
import models.Monstro;


public class Application extends Controller {

    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }
    
    public static Result teste1()
    {
    	Monstro jubs = new Monstro();
    	jubs.setNome("Jubileu");
    	jubs.save();
    	return ok(teste.render(jubs));
    }
    
    public static Result absorve()
    {
    	DynamicForm dynamicForm = Form.form().bindFromRequest();
    	Monstro mon;
    	mon = Monstro.find.byId(dynamicForm.get("mId"));
    	mon.somaEnergia(Integer.parseInt(dynamicForm.get("pontos")));
    	mon.save();
    	 return redirect("/monstro/"+dynamicForm.get("mId"));
    }
    
    public static Result mostraMonstro(String id)
    {
    	Monstro mon;
    	mon = Monstro.find.byId(id);
    	return ok(teste.render(mon));
    }
    
    public static Result treina(String tipo)
    {
    	DynamicForm data = Form.form().bindFromRequest();
    	Monstro	mon = Monstro.find.byId(data.get("mId"));
    	if(tipo.equals("str"))
    		mon.treinaAtributo(Monstro.Atributo.STR);
    	else if(tipo.equals("dex"))
    		mon.treinaAtributo(Monstro.Atributo.DEX);
    	else if(tipo.equals("wis"))
    		mon.treinaAtributo(Monstro.Atributo.WIS);
    	mon.save();
    	
    	return redirect("/monstro/"+data.get("mId"));
    }

}
