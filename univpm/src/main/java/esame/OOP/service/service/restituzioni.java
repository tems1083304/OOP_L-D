package esame.OOP.service;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import esame.OOP.service.statistiche;
import model.Tabella;

public class restituzioni  {
	
	private List<Map> metadata = new ArrayList<>();
	private List<Tabella> Ta = new ArrayList<>();
	
	
	//METODO CHE MI RESTITUISCE LA LISTA DEGLI OGGETTI
	public List prendiDati() {
        return Ta;}
	
	
    //METODO CHE MI RESTITUISCE LA LISTA DEI METADATI
        public List prendiMetaDati() {
            return metadata;}
    
        
        
    //METODO CHE MI RESTITUISCE L'OGGETTO RELATIVO ALL'INDICE PASSATO
      public Tabella prendiTa(int indice) {
          if (indice < Ta.size()) return Ta.get(indice);
          else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Idd: '" + indice + "' not exist");
      }
          
          
      //METODO CHE RESTITUISCE LE STATISTICHE A TUTTI I CAMPI 
        public List<Map> prendiStats() throws IllegalArgumentException, InvocationTargetException, ReflectiveOperationException {
		
             Field[] field = Tabella.class.getDeclaredFields();      //Questo metodo restituisce la matrice di oggetti Field che rappresentano tutti i campi dichiarati di questa classe.
             List<Map> list = new ArrayList<>();
             for (Field f : field) {
                 String fieldName = f.getName();                  // estrae il nome del campo corrente
                 list.add(prendiStat(fieldName));                //va ad aggiungere alla lista  la mappa che contiene le statistiche del campo fieldName
             }
             return list;
         }

         
       //METODO PRIVATO PER L'ESTRAZIONE DALLA LISTA DEL DATASET DEI VALORI RELATIVI AD UN CAMPO
         private List prendiValori(String fieldName) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        	 //IllegalAccessException interviene se vogliono accedere a un metodo privato .
        	 //IllegalArgumentException interviene se passiamo come parametro uno non valido
        	 //se il metodo invocato con API genera un'eccezione, l'API di riflessione inserirà l'eccezione in un InvocationTargetException
        	    List<Object> valori = new ArrayList<>();
        	    try {
        	        
        	        for (Tabella t: Ta) {                         //scorro la lista per cercare i valori del campo
        	            Method m = Tabella.class.getMethod("get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1)); //creo il metodo per l'estrazione
        	            Object value = m.invoke(t);             //invoco il metodo sopra creato
        	            valori.add(value);
        	        }
        	    } catch (NoSuchMethodException e) {          //eccezzione quando non esiste il metodo 
        	        e.printStackTrace();                    // Questo metodo stampa una traccia dello stack sul flusso di output degli errori standard
        	        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Field '" + fieldName + "' does not exist");
        	                                              // la classe base per le eccezioni utilizzate per applicare un codice di stato a una risposta HTTP.
        	    }
        	    
        	    return valori;                        //mi restituisci la lista dei valori
        	}
        			
        	
         
         //METODO CHE MI RESTITUISCE LE STATISTICHE RELATIVE AD UN CERTO CAMPO UTILIZZANDO IL METODO "PRENDIVALORI"
         public Map prendiStat (String fieldName) throws ReflectiveOperationException, IllegalArgumentException, InvocationTargetException {
            
				return statistiche.prendiStatistiche(fieldName, prendiValori(fieldName)); }
         
         
         //METODO CHE MI RESTITUISCE LA LISTA DI OGGETTI CHE SODDIFANO IL FILTRO
         public List<Tabella> prendiFiltri(String fieldName, String operator, Object ref) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
             List<Integer> l = filtri.selection(prendiValori(fieldName), operator, ref);    //esegue il filtraggio ricavando la lista degli indici 
             List<Tabella> out = new ArrayList<>();                                        //costruisce la lista di oggetti 
             for (int i : l) {
                 out.add(Ta.get(i));                                                      //alla nuova lista out gli aggiungo gli oggetti in "Ta" con il loro indice
             }
             return out;
         }

        		
	

}
