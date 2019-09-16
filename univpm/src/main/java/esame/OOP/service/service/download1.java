package esame.OOP.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import java.io.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.expression.ParseException;

import model.Tabella;


public class download1 {
	
	    private List<Tabella> Ta = new ArrayList<Tabella>();
	    private List<Map> metadata = new ArrayList<Map>();
		String data = "";//inizializza variabili
		String line = "";
		
		
		public download1(String url,String Nome) {
			
			File NomeFile = new File (Nome); //trasformo da string ad oggetto per poi dare il nome al file con FileUtils.copyURLToFile
			
			try {
				
				URLConnection openConnection = new URL(url).openConnection();		 //apro la connessione all'url
				openConnection.addRequestProperty("User_DAVIDE", "Google Chrome");	//Con l'utente "User-Agent" e "Google Chrome"
				InputStream PrendiDati = openConnection.getInputStream();		   //Raccoglie il flusso dei dati
				
				 try {
				  	 InputStreamReader LeggiDati = new InputStreamReader( PrendiDati ); //legge il flusso dei dati
					 BufferedReader Buffer = new BufferedReader( LeggiDati );	
				   														
					 while ( ( line = Buffer.readLine() ) != null ) {	              //legge i dati una linea alla volta e li mette in data
						 data+= line;
					 }
				 } finally {
					 PrendiDati.close();		                                 //dopo aver raccolto tutti i dati chiude il flusso
				 }
				 
				JSONObject oggetto = (JSONObject) JSONValue.parseWithException(data);	 //Inizia il parsing dei dati come JSON Object
				JSONObject temp = (JSONObject) (oggetto.get("result"));			        //Cerca nel JSON il "result" e lo apre in "temp"
				JSONArray temp2 = (JSONArray) (temp.get("resources"));		           //Poi cerca tutti i resources tra le parentesi {} del result
				  
				for( Object x : temp2){
				try {	                                               //Con questo ciclo for controlla ogni resources
				
				    if ( x instanceof JSONObject ) {					 
				        JSONObject y = (JSONObject)x; 				
				        String format = (String)y.get("format");		      //controlla i formati e li mette dentro format
				        URL tempurl = new URL ((String)y.get("url"));	     //idem per url, appena ne vede uno lo mette in url
				        if(format.equals("csv")) {						    //Se il formato di uno di quei url è cvs (ovvero il file che cerchiamo noi)
				        	FileUtils.copyURLToFile(tempurl, NomeFile);	   //metodo che scarica il file dall'url e lo mette nella cartella
				            System.out.println( "Il file è stato scaricato" );
				        }
				       }
				    
				} 
				catch (Exception e) { e.printStackTrace(); }// url non adatto
			
		}
				String lines = "";
		        
		        String csvFile = "TRA_CR.csv";
		        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {  //in questo modo br viene chiuso automaticamente alla fine

		        lines = br.readLine();
	           do  {                             // leggo il file riga per riga fino alla fine
	                                                       // trim elimina i caratteri non visibili, split divide la riga in corrispondenza del separatore
	                String[] csvLineSplitted = lines.trim().split(";");
	                                                                    // vado a estrarre i valori dei singoli campi dalla riga effettuando eventuali conversioni
	                String country_code = csvLineSplitted[0].trim().toUpperCase(); 
	                String lei_code = csvLineSplitted[1].trim().toUpperCase();
	                String bank_name = csvLineSplitted[2].trim().toUpperCase();
	                int period = Integer.parseInt(csvLineSplitted[3].trim());  //converte in intero
	                int item = Integer.parseInt(csvLineSplitted[4].trim());
	                int scenario = Integer.parseInt(csvLineSplitted[5].trim());
	                int portfolio = Integer.parseInt(csvLineSplitted[6].trim());
	                int country = Integer.parseInt(csvLineSplitted[7].trim());
	                int c_rank = Integer.parseInt(csvLineSplitted[8].trim());
	                int exp = Integer.parseInt(csvLineSplitted[9].trim());
	                int perf_status = Integer.parseInt(csvLineSplitted[10].trim());
	                int status = Integer.parseInt(csvLineSplitted[11].trim());
	                float amount = Float.parseFloat(csvLineSplitted[12].trim());
	                
	               Tabella nuova = new Tabella( country_code, lei_code, bank_name, period, item, scenario, portfolio, country, c_rank, exp, perf_status, status, amount );
	                // aggiunge l'oggetto appena creato alla lista
	                Ta.add(nuova);
			
		
	} while ((line = br.readLine()) != null);
	 br.close(); // chiudo il buffer
} catch (Exception e) {e.printStackTrace(); }

		Field[] fields = Tabella.class.getDeclaredFields(); //estrae gli attributi della classe TAbella

        for (Field f : fields) {
            Map<String, String> map = new HashMap<String,String>();
            //andiamo ad inserire le coppie chiave valore
            map.put("alias", f.getName());
            map.put("sourceField", f.getName().toUpperCase());//nome del campo in csv
            map.put("type", f.getType().getSimpleName());
            metadata.add(map);
        }
}catch (IOException | ParseException e) { e.printStackTrace(); }//Eccezioni in caso di parse sbagliato
catch (Exception e) { e.printStackTrace(); }//o di url non adatto
	// TODO: handle exception
}
         
        
            
		}  




	



