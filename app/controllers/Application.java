package controllers;

import java.util.List;
import play.*;
import play.data.Form;
import play.data.DynamicForm;
import play.mvc.*;

import views.html.*;
import models.*;


public class Application extends Controller 
{

    public static Result index()
    {
        return ok(index.render(""));
    }
    
    public static Result criaHabilidadesIniciais()
    {
    	
    	List<Habilidade> habilidades = Habilidade.find.all();
    	if(habilidades.size() != 0) return ok(index.render("Já existem habilidades no BD"));
    	
    	Habilidade h = new Habilidade("Patada", 10, 10);
    	h.save();
    	
    	
    	h = new Habilidade("Patada inteligente", 10, 20);
    	h.setMinWis(10);
    	h.save();
    	
    	h = new Habilidade("VEM MONSTRO", 666, 20);
    	h.setMinStr(50);
    	h.save();
    	
    	
    	return ok(index.render("Habilidades criadas"));
    }
    
    public static Result criaGeradoresIniciais()
    {
    	List<Gerador> geradores = Gerador.find.all();
    	if(geradores.size() != 0) return ok(index.render("Já existem geradores no BD"));
    	
    	Gerador g = new Gerador("Gerador básico", 10, 2);
    	g.save();
    	
    	g = new Gerador("Gerador não-tão-básico", 20, 5);
    	g.save();
    	
    	g = new Gerador("Super Gerador básico", 100, 40);
    	g.save();
    	
    	return ok(index.render("Geradores criados"));
    }
    
    
    public static Result shop2(String id)
    {
    	Monstro mon;
    	mon = Monstro.find.byId(id);
    	List<Gerador> geradores = Gerador.find.all();
    	return ok(loja2.render(mon, geradores));
    	
    }
    
    public static Result shop(String id)
    {
    	Monstro mon;
    	mon = Monstro.find.byId(id);
    	List<Habilidade> habilidades = Habilidade.find.all();
    	return ok(loja.render(mon, habilidades));
    	
    }
    
    public static Result compraDeHabilidade()
    {
    	DynamicForm data = Form.form().bindFromRequest();
    	Monstro	mon = Monstro.find.byId(data.get("mId"));
    	Habilidade h = Habilidade.find.byId(data.get("habId"));
    	mon.compra(h);
    	mon.save();
    	return redirect("/monstro/"+data.get("mId")+"/shop");
    }
    
    public static Result compraDeGerador()
    {
    	DynamicForm data = Form.form().bindFromRequest();
    	Monstro	mon = Monstro.find.byId(data.get("mId"));
    	Gerador g = Gerador.find.byId(data.get("gerId"));
    	mon.compra(g);
    	mon.save();
    	return redirect("/monstro/"+data.get("mId")+"/shop2");
    }
    
    
    public static Result novo()
    {
    	Monstro jubs = new Monstro("Monstro Genérico");
    	jubs.save();
    	return ok(teste.render(jubs));
    }
    
    public static Result falha()
    {
    	 return ok(index.render("Monstro com o Id passado não existe"));
    }
    
    
    public static Result login()
    {
    	DynamicForm data = Form.form().bindFromRequest();
    	try  
		 {  
    		int mid = Integer.parseInt(data.get("mId")); 
		 }  
		 catch(NumberFormatException nfe)  
		 {  
			 return redirect("/falha");  
		 } 
    	
    	
    	return redirect("/monstro/"+data.get("mId"));
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
    	if(mon == null)
    		return redirect("/falha");
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
