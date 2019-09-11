package model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Tabella implements Serializable {
	final static int time = 6;
	private String freq;     
	private String geo;
	private String unit;
	private String help;
	private int period[] = new int  [time];
	
	
	
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
	
	public Tabella ( String freq, String geo, String unit , String help ,int[] period ) {
		this.freq = freq;
		this.geo = geo;
		this.unit = unit;
		this.help = help;
		this.period = period;
		
		
	}



	public String getFreq() {
		return freq;
	}



	public void setFreq(String freq) {
		this.freq = freq;
	}



	public String getGeo() {
		return geo;
	}



	public void setGeo(String geo) {
		this.geo = geo;
	}



	public String getUnit() {
		return unit;
	}



	public void setUnit(String unit) {
		this.unit = unit;
	}



	public String getHelp() {
		return help;
	}



	public void setHelp(String help) {
		this.help = help;
	}



	public int[] getPeriod() {
		return period;
	}



	public void setPeriod(int[] period) {
		this.period = period;
	}

	@Override     //Indica che una dichiarazione di un metodo ha lo scopo di sovrascrivere la dichiarazione di un metodo in un supertipo.
    public String toString() {  //metodo per la stampa,restituisci una stringa contenente il valore dei vari campi
        return "Tabella{" +
                "frequenza='" +  freq  + '\'' +
                ", entità geopolitica='" + geo + '\'' +
                ", unità di misura ='" + unit + '\'' +
                ", aiuto='" + help + '\'' +
                ", periodo='" + period + 
               '}';
    }

	
}
