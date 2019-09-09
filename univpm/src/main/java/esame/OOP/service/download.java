package esame.OOP.service;

import java.io.*;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.boot.json.BasicJsonParser;
import esame.OOP.model.Codici;
import model.Tabella;



public class download<getDeclaredFields> {
	private List<Map> metadata = new ArrayList<>();
	String data;
	String line;
	String url = "http://data.europa.eu/euodp/data/api/3/action/package_show?id=eu-wide-stress-test-results-2018-asset-quality";
    
	public download ( String url, String Nome) {
	this.url = url;
    File file = new File(Nome);
try {
			URLConnection Connection = new URL(url).openConnection();		//Si connette all'url
			Connection.addRequestProperty("User-Agent", "Google Chrome");	//Con l'utente "User-Agent" e "Google Chrome"
			  
		  //evita errore java.io.IOException: Server returned HTTP response code: 403 for UR                                                     
			InputStream PrendiDati = Connection.getInputStream();					//Raccoglie il flusso dei dati
			
			 try {
			  	 InputStreamReader LeggiDati = new InputStreamReader( PrendiDati ); //legge il flusso dei dati
				 BufferedReader Buffer = new BufferedReader( LeggiDati );
				  String json = Buffer.readLine(); //leggo il json e lo salvo
				  Map map = new BasicJsonParser().parseMap(json); // passo la stringa del json al parser di Spring che mi restituisce la mappa chiave-valore associata
		                                                         // navigo nella mappa fino all'URL del file csv
		            Map result = (Map) map.get("result");   // il metodo get della classe Map mi restituisce un generico Object -> Devo fare il casting
		            List resources = (List) result.get("resources");
		            String linkcsv = "";
		            // Scorro tutte le risorse cercando quella con formato csv -> a quel punto estraggo l'URL
		            for (Object r : resources) {
		                Map m = (Map) r;
		                if (m.get("format").equals("csv")) { linkcsv = (String) m.get("url"); FileUtils.copyURLToFile(linkcsv, file);} //copio l'url nel file nome
		            }
		            System.out.println(linkcsv);    // stampo in console l'URL per debug
		            URL csvurl = new URL(linkcsv);  // apro la connessione all'URL
			   														
				 while ( ( line = Buffer.readLine() ) != null ) {	//legge i dati una linea alla volta e li mette in csvline
					 String[] csvLine = line.trim().split(";");
		                // vado a estrarre i valori dei singoli campi dalla riga effettuando eventuali conversioni
		                String album = csvLine[0].trim().replaceAll("\"", "").toUpperCase();
		                int anno = Integer.parseInt(csvLine[1].trim());
		                String post = csvLine[1].trim();
		                // creo l'oggetto StrutturaAlberghiera sulla base dei valori parsati
		                Codici nuova = new Codici(album, anno, post);
		                // aggiunge l'oggetto appena creato alla lista
		                Codici.add(nuova);
		                // stampa l'oggetto in console per debug
		                // System.out.println(nuova);
				 }
			 }catch (MalformedURLException e) } 
		            System.err.println("URL Errato");
		            e.printStackTrace();
		        } catch (IOException e) {
		            e.printStackTrace();
				 buffer.close();} //dopo aver raccolto tutti i dati chiude il flusso
		        


		
 }
Field[] fields = Tabella.class.getDeclaredFields();//estrae gli attributi della classe 

for (Field f : fields) {
    Map<String, String> map = new HashMap<>();
    //andiamo ad inserire le coppie chiave valore
    map.put("alias", f.getName());
    map.put("sourceField", f.getName().toUpperCase());//nome del campo in csv
    //touppercase serve per convertire il nome in maiuscolo
    map.put("type", f.getType().getSimpleName());
    metadata.add(map);

}
