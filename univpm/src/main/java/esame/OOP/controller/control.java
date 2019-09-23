package it.univpm.Progetto.controller;



import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.BasicJsonParser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.univpm.Progetto.model.Tabella;
import it.univpm.Progetto.service.restituzioni;

//CONTROLLER PER GESTIRE LE RICHIESTE DEL CLIENTE

//@RestController //REST è un modello architetturale che permette la comunicazione tra sistemi remoti connesi ad internet
               //è un Web Service stateless (privo di memoria);attraverso degli oggetti di tipo CONTROLLER,Spring permette di gestire 
              //le richieste inviate al server. 
@RestController
public class controller {
	
	
	
	private restituzioni ServRes;
	
//@Autowired                      //viene lanciato automaticamente all'avvio da Spring e esegue il collegamento al Service
                               //stiamo dichiarando che il controllore dipende dal Service
	@Autowired
public controller (restituzioni ServRes)
{ 
  this.ServRes = ServRes;
}

	@GetMapping("/hello")    //mappa una richiesta HTTP su un metodo
	public String home() {  
		System.out.println("hello world"); 
		return "hello world";//viene visualizzato in localhost:8080 sul browser
	}
	
	
//REST PERMETTE LO SCAMBIO DI MESSAGGI TRAMITE LE RICHIESTE "GET" e "POST"
@GetMapping(value="/dati") //mappa le richieste HTTP GET su metodi di gestione specifici
                          //la rotta è la parte dell'url dopo il dominio, es.: localhost:8080/dati

public List prendiDati() {
	return ServRes.prendiDati();
}

//Metodo che gestisce la richiesta GET alla rotta "/data/{indice}", restituisce il record del dataset corrispondente a {indice}
@GetMapping(value="/data/{indice}")
public Tabella prendiId(@PathVariable int indice) { //@PathVariable ci permette di recuperare valori inclusi nell’URI associato alla richiesta;
    return ServRes.prendiTa(indice);               //mentre @RequestParam recupera valori dalla stringa della query
}

//Metodo che gestisce la richiesta GET alla rotta "/metadati", restituisce la lista dei metadati
@GetMapping(value="/metadati")
public List prendiMetaDati() {
    return ServRes.prendiMetaDati();
}


//Metodo che gestisce la richiesta GET alla rotta "/stats", restituisce le statistiche di un campo
@GetMapping(value="/stats")
public List prendiStat(@RequestParam(value = "field", defaultValue = "") String field) throws IllegalArgumentException, InvocationTargetException, ReflectiveOperationException { //@RequestParam a differenza di @pathVariable 
	                                                                   //recupera valori dalla stringa della query(richiesta per accedere ad un database)
	    List<Map> list = new ArrayList<>();
        list.add(ServRes.prendiStat(field));
        return list;                                          //lista contenente le statistiche richieste
    }

//METODO PRIVATO CHE ESEGUE IL PARSING DEL FILTRO PASSATO TRAMITE BODY
private static Map<String, Object> parseFiltro(String body) {
    Map<String, Object> parsedBody = new BasicJsonParser().parseMap(body); //mi analizza la stringa di JSON e mi mappa il body
    String fieldName = parsedBody.keySet().toArray(new String[0])[0];  //converte le chiavi in string e li mette in matrice
    Object rawValue = parsedBody.get(fieldName); //"parseBody"per mappare il flusso del corpo a un oggetto in memoria,
    Object refValue;
    String operator;
    if (rawValue instanceof Map) {    //se la chiave è di tipo Map
        Map filter = (Map) rawValue; //assegno a filter il valore della chiave
        System.out.println(filter);
        operator = ((String) filter.keySet().toArray()[0]).toLowerCase();  //gli assegno l'operatore e converte tutti i caratteri di questa stringa in lettere minuscole
        refValue = filter.get(operator); //converte la chiave in stringa minuscola
    } else {
        operator = "$eq";
        refValue = rawValue;
    }
    Map<String, Object> filter = new HashMap<>();
    filter.put("operator", operator); //metti chiavi-valore
    filter.put("field", fieldName);
    filter.put("ref", refValue);
    return filter;
}

//METODO CHE RESTITUISCE LA LISTA FILTRATA TRAMITE UNA RICHIESTA POST
@PostMapping("/data")
public List prendiFiltri(@RequestBody String body) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
    Map<String, Object> filter = parseFiltro(body);
    String fieldName = (String) filter.get("field");
    String operator = (String) filter.get("operator");
    Object refValue = filter.get("ref");
    return ServRes.prendiFiltri(fieldName, operator, refValue); //lista degli oggetti che soddisfano il filtro
}
}









