package esame.OOP.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//CLASSE ASTRATTA PER IMPLEMENTARE METODI PER IL CALCOLO DI VALORI STATISTICI

public abstract class statistiche {
	
	
	 //METODO CHE CONTA GLI ELEMENTI DI UNA LISTE USANDO size()
	 public static int conta(List list) {
	        return list.size();
	    }
	 
	
         
    //SOMMA TUTTI I NUMERI DELLA LIST
		 public static double somma(List<Number> list) {
		        double i= 0;
		        for (Number n : list) {
		            i += n.doubleValue();    //restituisce un double   
		        }
		        return i;
		    }
	 
   //CALCOLA LA MEDIA DI UNA LISTA DI NUMERI (utilizzando gli altri metodi)
		 public static double avg(List<Number> list) {
			 double sum = somma(list);
		        double mean = 0;
		        mean = sum / (list.size());
		        return mean;   
		    }
	 
	
   //TROVA IL MAX IN UNA LISTA DI NUMERI
		 public static double max(List<Number> list) {
			 double max = list.get(0).doubleValue(); //doubleValue() mi restituisci un double,quindi subisce arrotondamenti
			 for ( Number i : list)
			 {
				 double n = i.doubleValue(); //assegno a n il valore di i
				 if ( n > max) max = n;
			 }
			 return max;
			 
		 }
		 
	//TROVA IL MIN IN UNA LISTA DI NUMERI
		 public static double min(List<Number> list) {
			 double min = list.get(0).doubleValue();
			 for ( Number i : list)
			 {
				 double n = i.doubleValue();
				 if (n < min) min = n;
			 }
			 return min;
		 }
		 
	//CALCOLO DELLA DEVIAZIONE STANDARD,FORMULA = sqrt{(Σ(xi-m)^2)/(n-1)}
		 public static double std (List<Number> a){
		        int sum = 0;
		       double mean = avg(a);
		 
		        for (Number i : a)
		            sum += Math.pow((i.doubleValue() - mean), 2);
		        return Math.sqrt( sum / ( a.size() - 1 ) );
		    }
		 
		 //METODO CHE CONTA LE OCCORRENZE IN UNA LISTA 
		 public static Map<Object, Integer> contau (List list) {
		        Map<Object, Integer> mappa = new HashMap<>();
		        for (Object ele : list) {
		            if (mappa.containsKey(ele)) {        //se la mappa contiene già la chiave (elemento già trovato precedentemente)
		                mappa.replace(ele, mappa.get(ele) +1);   //incrementa il valore, ovvero il contatore, di uno
		            } else {
		                mappa.put(ele, 1);       // altrimenti inserisce nella mappa la nuova chiave trovata con contatore inizializzato a uno
		            }
		        }
		        return mappa;
		    }
		 
		 
		 
	// METODO CHE RESTITUISCE TUTTE LE STATISTICHE DI UN CERTO CAMPO DEL DATASET
	     
	     //fieldName  nome del campo dal quale si è estratta la lista di valori
	     // list      lista dei valori del campo (eventualmente  già filtrata)
	    // Map        che ha come chiavi i nomi delle statistiche sul campo e associati i rispettivi valori


       public static Map<String, Object> prendiStatistiche(String fieldName, List list) {
		Map<String, Object> map = new HashMap<>();        
		map.put("field", fieldName);                      //metti chiave-valore       
		if (!list.isEmpty()) {                           //se la lista non è vuota	            if (list.get(0) instanceof Number) {        // calcola le statistiche numeriche
	                
	                List<Number> listNum = new ArrayList<>(); //creo una lista di numeri
	                for (Object elem : list){
	                    listNum.add(((Number) elem));
	                }
	                map.put("avg", avg(listNum));
	                map.put("min", min(listNum));
	                map.put("max", max(listNum));
	                map.put("std", std(listNum));
	                map.put("somma", somma(listNum));
	                map.put("conta", conta(listNum));
	                return map;
	            } else {                              // calcola le statistiche non numeriche
	                map.put("contau", contau(list));
	                map.put("conta", conta(list));
	            }
		        return map;
	        }
	       






		


	
		
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 

