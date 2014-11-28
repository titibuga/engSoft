package controllers;

import play.*;
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
    	return ok(teste.render(jubs));
    }

}
