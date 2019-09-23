package it.univpm.Progetto.service;



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
import org.springframework.stereotype.Service;

import it.univpm.Progetto.model.Tabella;


//classe  che esegue il parsing del dataset in formato JSON includendo nel programma librerie esterne per utilizzare le classi preimpostate JSON,
//effettua il download del file in csv e lo salva,genere poi metadati da conservare e restituire.
@Service
public class download {

	private List<Tabella> Ta = new ArrayList<Tabella>(); //creo le liste
	private List<Map> metadata = new ArrayList<Map>();
	String data = "";
	String line = "";
	String url = "http://data.europa.eu/euodp/data/api/3/action/package_show?id=eu-wide-stress-test-results-2018-asset-quality";
	String Nome = "CSV_I.csv";

	public download() {

		File NomeFile = new File (Nome); //trasformo da string ad oggetto per poi dare il nome al file con FileUtils.copyURLToFile
		if ( !NomeFile.exists()){
			try {	

				URLConnection openConnection = new URL(url).openConnection();		 //apro la connessione all'url
				openConnection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:64.0) Gecko/20100101 Firefox/64.0");	
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
							if(format.equals("http://publications.europa.eu/resource/authority/file-type/CSV")) {			    //Se il formato di uno di quei url è cvs
								FileUtils.copyURLToFile(tempurl, NomeFile);// scarica il file dall'url e lo mette nella cartella
								System.out.println( "Il file è stato scaricato" );
							}
						}

					} 
					catch (Exception e) { e.printStackTrace(); }// url non adatto


				}
			} catch (Exception e) { e.printStackTrace(); }
		}

		


		try (BufferedReader br = new BufferedReader(new FileReader(new File (Nome)))) {  //in questo modo br viene chiuso automaticamente alla fine

			br.readLine();                  //salto la prima riga
			while ((line = br.readLine()) != null)  {                                       // leggo il file riga per riga fino alla fine
				// trim elimina i caratteri non visibili, split divide la riga in corrispondenza del separatore
				String[] csvLineSplitted = line.trim().split("\",\"");
				// vado a estrarre i valori dei singoli campi dalla riga 
				String country_code = csvLineSplitted[0].trim().toUpperCase().substring(1);  //uso substring per eliminare il primo carattere cioe la "
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
				String temp = csvLineSplitted[12].trim(); //creo una variabile di appoggio per eliminare l'ultimo carattere del campo
				temp = temp.substring(0,temp.length()-1); 
				float amount;
				if (temp.equals(".")) amount = -1;  //nel caso in cui il dato è mancate verrà assegnato il valore -1
				else amount = Float.parseFloat(temp);

				Tabella nuova = new Tabella( country_code, lei_code, bank_name, period, item, scenario, portfolio, country, c_rank, exp, perf_status, status, amount );
				// aggiunge l'oggetto appena creato alla lista
				Ta.add(nuova);


			}

		} catch (Exception e) {e.printStackTrace(); }

		Field[] fields = Tabella.class.getDeclaredFields(); //estrae gli attributi della classe Tabella e il mette nel campo

		for (Field f : fields) {                                 //scorro il campo e
			Map<String, String> map = new HashMap<String,String>(); //andiamo ad inserire le coppie chiave valore

			map.put("alias", f.getName()); //"getName" restituisce il nome della classe
			map.put("sourceField", f.getName().toUpperCase());//nome del campo in csv
			map.put("type", f.getType().getSimpleName());
			metadata.add(map);  //aggiungo il tutto all'array metadata
		}
	}
	

	
	public List<Tabella> PrendiTa() {
		// TODO Auto-generated method stub
		return Ta;
	}



	public List<Map> PrendiMeta() {
		// TODO Auto-generated method stub
		return metadata;
	}



	



	
	
  

}  


