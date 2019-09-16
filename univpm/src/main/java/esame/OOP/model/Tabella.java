package model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Tabella implements Serializable {
	
	private String country_code;     
	private String lei_code;
	private String bank_name;
	private int period;
	private int item;    //articolo
	private int scenario;
	private int portfolio;  
	private int country; 
	private int c_rank; //rango del paese
	private int exp;   //esposizione
	private int perf_status;
	private int status;
	private float amount; //importo
	
	
	/**  LEGGENDA DEI PARAMETRI DELLA TABELLA
	 * 
	 * 1) Frequenza [FREQ]	
	 * fisso Annuale [A]
	
	   2) Entità geopolitica [GEO]
	      Unione Europea (28 paesi) [EU28]
	      Belgio [BE]
	      Bulgaria [BG]
	      Repubblica Ceca [CZ]
	      Danimarca [DK]
	      Germania  [DE]
	      Estonia [EE]
	      Irlanda [IE]
	      Grecia [EL]
	      Spagna [ES]
	      Francia [FR]
	     
	  3)unità di misura [UNIT]	
	      Milioni di euro (ai prezzi dell'anno precedente) [MEUR_KP_PRE]
	      Percentuale del totale [PC_TOT]
	      
	  4)strumenti d'aiuto [AID_INSTR]
          Partecipazione azionaria [EQUIT]
	      Concedi [GRANT]
	      Garanzia [GUAR]
	      Prestito agevolato [SOFTL]
	      Differimento fiscale [TAXD]
	      Esenzione fiscale [TAXE]
	      Altro [OTH]
	     
	    5) periodo
	*/
	
	public Tabella (  String country_code,String lei_code,String bank_name,int period,int item, int scenario,int portfolio,int country,int c_rank,int exp,int perf_status,int status,float amount ) {
		this.country_code = country_code;
		this.lei_code = lei_code;
		this.bank_name = bank_name;
		this.period = period;
		this.item= item;
		this.scenario = scenario;
		this.portfolio = portfolio;
		this.country = country;
		this.c_rank = c_rank;
		this.exp = exp;
		this.perf_status = perf_status;
		this.status = status;
		this.amount = amount;
		
}
	



	

	public String getCountry_code() {
		return country_code;
	}






	public void setCountry_code(String country_code) {
		this.country_code = country_code;
	}






	public String getLei_code() {
		return lei_code;
	}






	public void setLei_code(String lei_code) {
		this.lei_code = lei_code;
	}






	public String getBank_name() {
		return bank_name;
	}






	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}






	public int getPeriod() {
		return period;
	}






	public void setPeriod(int period) {
		this.period = period;
	}






	public int getItem() {
		return item;
	}






	public void setItem(int item) {
		this.item = item;
	}






	public int getScenario() {
		return scenario;
	}






	public void setScenario(int scenario) {
		this.scenario = scenario;
	}






	public int getPortfolio() {
		return portfolio;
	}






	public void setPortfolio(int portfolio) {
		this.portfolio = portfolio;
	}






	public int getCountry() {
		return country;
	}






	public void setCountry(int country) {
		this.country = country;
	}






	public int getC_rank() {
		return c_rank;
	}






	public void setC_rank(int c_rank) {
		this.c_rank = c_rank;
	}






	public int getExp() {
		return exp;
	}






	public void setExp(int exp) {
		this.exp = exp;
	}






	public int getPerf_status() {
		return perf_status;
	}






	public void setPerf_status(int perf_status) {
		this.perf_status = perf_status;
	}






	public int getStatus() {
		return status;
	}






	public void setStatus(int status) {
		this.status = status;
	}






	public float getAmount() {
		return amount;
	}






	public void setAmount(float amount) {
		this.amount = amount;
	}






	@Override                    //Indica che una dichiarazione di un metodo ha lo scopo di sovrascrivere la dichiarazione di un metodo in un supertipo.
    public String toString() {  //metodo per la stampa,restituisci una stringa contenente il valore dei vari campi
		return "Tabella{" +
        "nome paese='" + country_code + '\'' +
        ", codice='" + lei_code + '\'' +
        ",nome banca='" + bank_name + '\'' +
        ", periodo='" + period + '\'' +
        ", articolo='" + item + '\'' +
        ", scenario=" + scenario +
        ",portafoglio='" + portfolio + '\'' +
        ",paese ='" + country + '\'' +
        ",rango paese='" + c_rank + '\'' +
        ", esposizione='" + exp + '\'' +
        ", Status='" + status + '\'' +
        ", ammontare =" + amount +
        '}';
    }

	
}
