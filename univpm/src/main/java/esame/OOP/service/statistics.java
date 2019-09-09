package esame.OOP.service;

import java.util.List;

//CLASSE ASTRATTA PER IMPLEMENTARE METODI PER IL CALCOLO DI VALORI STATISTICI

public abstract class statistics {
	
	
	 //METODO CHE CONTA GLI ELEMENTI DI UNA LISTE USANDO size()
	 public static int conta(List list) {
	        return list.size();
	    }
	 
	
         
    //SOMMA TUTTI I NUMERI DELLA LISTA
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
		}
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 

