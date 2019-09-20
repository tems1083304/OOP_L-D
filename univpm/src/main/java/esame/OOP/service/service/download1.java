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

import org.json.simple.*;
import org.springframework.expression.ParseException;

import model.Tabella;

//classe  che esegue il parsing del dataset in formato JSON includendo nel programma librerie esterne per utilizzare le classi preimpostate JSON,
//effettua il download del file in csv e lo salva,genere poi metadati da conservare e restituire.
public class download1 {
	
	    private List<Tabella> Ta = new ArrayList<Tabella>();
	    private List<Map> metadata = new ArrayList<Map>();
		String data = "";
		String line = "";
		String url = "http://data.europa.eu/euodp/data/api/3/action/package_show?id=eu-wide-stress-test-results-2018-asset-quality";
		
		public download1(String url,String Nome) {
			
			File NomeFile = new File (Nome); //trasformo da string ad oggetto per poi dare il nome al file con FileUtils.copyURLToFile
			this.url = url;
			try {
				
				URLConnection openConnection = new URL(url).openConnection();		 //apro la connessione all'url
				openConnection.addRequestProperty("User-DAVIDE", "Google Chrome");	
				InputStream PrendiDati = openConnection.getInputStream();		
				
				 try {
				  	 InputStreamReader LeggiDati = new InputStreamReader( PrendiDati ); //Raccoglie il flusso dei dati
					 BufferedReader Buffer = new BufferedReader( LeggiDati );	   //Legge il flusso dei dati
				   														
					 while ( ( line = Buffer.readLine() ) != null ) {	          //legge i dati una linea alla volta e li mette in data
						 data+= line;
					 }
				 } finally {
					 PrendiDati.close();		                                 //dopo aver raccolto tutti i dati chiude il flusso
				 }
				 
				JSONObject oggetto = (JSONObject) JSONValue.parseWithException(data);	 //Inizia il parsing dei dati come JSON Object includendo le librerie esterne json_simple
				JSONObject temp = (JSONObject) (oggetto.get("result"));			//Cerca nel JSON il "result" e lo apre in "temp"
				JSONArray temp2 = (JSONArray) (temp.get("resources"));		       //Poi cerca tutti i resources tra le parentesi {} del result
				  
				for( Object x : temp2){
				try {	                                                           //Con questo ciclo for controlla ogni resources
				
				    if ( x instanceof JSONObject ) {					 
				        JSONObject y = (JSONObject)x; 				
				        String format = (String)y.get("format");              //controlla i formati e li mette dentro format
				        URL tempurl = new URL ((String)y.get("url"));	     //idem per url, appena ne vede uno lo mette in url
				        if(format.equals("csv")) {			    //Se il formato di uno di quei url è cvs
				        	FileUtils.copyURLToFile(tempurl, NomeFile);// scarica il file dall'url e lo mette nella cartella
				            System.out.println( "Il file è stato scaricato" );
				        }
				       }
				    
				} 
				catch (Exception e) { e.printStackTrace(); }// url non adatto
			
		}
				String lines = "";
		        
		        String csvFile = "TRA_CR.csv";
		        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {  //in questo modo br viene chiuso automaticamente alla fine

		        lines = br.readLine();                  //salto la prima riga
	           do  {                                       // leggo il file riga per riga fino alla fine
	                                                      // trim elimina i caratteri non visibili, split divide la riga in corrispondenza del separatore
	                String[] csvLineSplitted = lines.trim().split(";");
	                                                                    // vado a estrarre i valori dei singoli campi dalla riga 
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
	 
} catch (Exception e) {e.printStackTrace(); }

		Field[] fields = Tabella.class.getDeclaredFields(); //estrae gli attributi della classe Tabella e il mette nel campo

            for (Field f : fields) {                                 //scorro il campo e
            Map<String, String> map = new HashMap<String,String>(); //andiamo ad inserire le coppie chiave valore
                                            
            map.put("alias", f.getName()); //"getName" restituisce il nome della classe
            map.put("sourceField", f.getName().toUpperCase());//nome del campo in csv
            map.put("type", f.getType().getSimpleName());
            metadata.add(map);  //aggiungo il tutto all'array metadata
        }
}catch (IOException | ParseException e) { e.printStackTrace(); }//Eccezioni in caso di parse sbagliato
catch (Exception e) { e.printStackTrace(); }//o di url non adatto
	// TODO: handle exception
}
         
        
            
		}  




	



