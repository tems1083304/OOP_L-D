# OOP_L-D
Repository for exam project

Progetto d'esame per il corso di Programmazione ad Oggetti. Ingegneria Informatica e dell’Automazione (Università Politecnica delle Marche).
Realizzato da Davide Olivieri e Lorenzo Tribuiani.

# Introduzione
l progetto assegnato ha lo scopo di utilizzare un dataset su cui vengono gestite diverse query attraverso le API GET REST E POST,dell'applicazione
SPRING,Spring è un framework che si basa sul concetto dell' IoC ( inversion of controll ) ciò significa che diversamente da altri framework java 
è SPRING stesso ad avere il controllo del flusso applicativo e non il programmatore.
I dati richiesti vengono estratti in formato JSON a partire da un file CSV estratto da un url fornito dal professore,tutte le specifiche sono descritte nel file pdf.
Verrà poi utilizzato POSTMAN per la visualizzazione delle richieste.

# COSA PUO' FARE IL SOFTWARE?
1)Restituisce i metadata del dataset : "Rotta: /metadata".

2)Restituisce i dati (eventualmente filtrati) : "Rotta: /data".

3)Restituisce statistiche sui dati (eventualmente filtrati) : "Rotta: /stats".

# COME INSERIRE UN FILTRO?
Il filtro va inserito nel body della richiesta POST come stringa RAW e deve avere il seguente formato:

{"field" : {"operator" : refvalue}}

dove:

-"FIELD" = Specifica il campo sul quale deve essere applicato il filtro. I campi validi sono specificati nei metadati.

-"OPERATOR" = l'operatore che specifica il tipo di filtro richiesto. 
"$eq" (uguaglianza),

"$not" (disuguaglianza),
                                                                      
"$gt" ( maggiore ,solo per campi numerici) ,

"$gte" (maggiore o uguale ,solo per campi numerici),

"$lt" (minore (solo per campi numerici) ,

"$lte" (minore o uguale,solo per campi numerici),

"$bt" (compreso ,solo per campi numerici),

-"REFVALUE" = Valore di riferimento per il controllo.


# REALIZZAZIONE

Il progetto è diviso in tre package:

MODEL: contiene la classe Tabella modella il singolo record del dataset.

SERVICE: contiene le classi: 

-download1: effettua il download del data-set che contiene dati in formati CSV partendo dall’indirizzo fornito dopo opportuna decodifica del JSON che contiene la URL utile per scaricare il file;

-restituzioni: contiene metodi per la restituzione di dati e metadati filtrati o non;

-filtri: classe che implementa i metodi per filtrare i campi;

-statistiche: classe per il calcolo dei valori statistici;

CONTROLLER: contiene la classe Control gestisce le richieste del client e converte le risposte da oggetti Java a stringhe in formato JSON.


# ARGOMENTO TRATTATO NEL SOFTWARE

La Tabella scaricata in formato csv è lo STRESS TEST delle banche o il programma di valutazione del capitale di vigilanza è una valutazione della riserva di capitale in corso da parte del Cebs (Committee of European Banking Supervisors) in collaborazione con la Banca centrale europea e la commissione Europa su le principali banche europee. Il risultato ha evidenziato una buona capacità delle banche europee a reggere un eventuale peggioramento dell'economia reale nel prossimo biennio.L'obiettivo dello stress test a livello dell'UE è valutare la resilienza delle banche dell'UE a una serie comune di sviluppi economici sfavorevoli al fine di identificare potenziali rischi, informare le decisioni di vigilanza e aumentare la disciplina di mercato.
Tutte le statistiche e i dati posso essere visualizzati nella pagina "http://data.europa.eu/".



