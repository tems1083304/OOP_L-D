package it.univpm.Progetto.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;



// Classe astratta che implementa i metodi per filtrare i campi
@Service
public abstract class filtri {
	

	public static boolean check(Object valore, String operatore, Object riferimento) { //creo un metodo static dato che non ha bisogno di creare oggetti
		if (riferimento instanceof Number && valore instanceof Number) {	//se riferimento e valore sono due numeri
			Double rif = ((Number)riferimento).doubleValue();              //converto il riferimento in double
			Double val = ((Number)valore).doubleValue();                  //converto il valore in double
		 if (operatore.equals("&gte"))
			return val >= rif;
			else if (operatore.equals("&gt"))
				return val > rif;
				else if (operatore.equals("&lte"))
					return val <= rif;
				else if ( operatore.equals("$lt"))
					return val < rif;
				else throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"INVALID OPERATOR");
			        // È una RuntimeException per le eccezioni utilizzate per applicare un codice di stato a una risposta HTTP
			       // BAD_REQUEST è la costante di enumerazione
				
		} else if (riferimento instanceof String && valore instanceof String) { //se riferimento e valore sono due stringhe
			 String strRif = ((String)riferimento);                            //le converto
			 String strVal = ((String)valore);
			 if (operatore.equals("$not"))
				 return !strVal.equals(strRif);
			 else if (operatore.contentEquals("$eq"))
				 return strVal.equals(strRif);
			 else throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"INVALID OPERATOR");
            
		}else throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"REFERENCE NOT COMPATIBLE WITH THE VALUE");}
	

			
	    //METODO CHE FILTRA LA LISTA DEI VALORI DI UN CAMPO
	   // in "values" c'è la lista da controllare
	  // in "ref" il valore di riferimento
		
	
		public static List<Integer> selection(List values, String operator, Object ref) {
	        List<Integer> indice = new ArrayList<>();              //creo una lista di interi
	        for (int i = 0; i < values.size(); i++) {
	            if (check(values.get(i), operator, ref))         // per ogni elemento della lista, se soddisfa il controllo (check)
	                indice.add(i);                              // aggiungo il suo indice alla lista
	        }
	        return indice;} 
	}                                                        //restituisco la lista degli indici
	    
