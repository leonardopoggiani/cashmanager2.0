# Progetto programmazione avanzata

## Cash manager 2.0

### Caso d'uso:

1.	L'Utente inserisce il NOME CLIENTE
2.	L'Utente inserisce la DATA
3.	L’Utente seleziona il PIATTO da inserire nell’ORDINE
4.	L’Utente seleziona la QUANTITA’ desiderata
5.	IF l’Utente preme INSERISCI 
  5.1.	Il Sistema inserisce il PRODOTTO e la relativa quantità nella tabella ORDINE calcolando il SUBTOTALE
  5.2.	Il Sistema aggiorna il TOTALE
6.	IF l’Utente seleziona una riga della tabella ORDINI  e preme RIMUOVI
  6.1.	Il Sistema rimuove la riga selezionata dalla tabella ORDINI
7.	IF l’Utente preme INVIA
  7.1.	Il Sistema salva l’ordine effettuato
  7.2.	Il Sistema aggiorna il grafico dei PIATTI PIU’ ORDINATI
  7.3.	Il Sistema svuota la tabella ORDINI, il campo NOME CLIENTE e il TOTALE

### File di configurazione locale in XML:
Il sistema all’avvio legge dal file di configurazione i seguenti dati:
-	Il numero di righe della tabella ORDINE  da mostrare.
-	Il font, la dimensione del testo e il colore del background
-	L’indirizzo IP del client, l’indirizzo IP e la porta del server del log
-	Il PREZZO dei prodotti
-	Il numero di giorni da considerare nel grafico
-	Il numero di PRODOTTI da considerare nel grafico

### Cache locale degli input:
Alla chiusura il sistema salva su un file binario:
-	Il NOME CLIENTE
-	Il contenuto della tabella ORDINE
-	La DATA dell’ordine
-	La riga selezionata della tabella ORDINE
-	Il TOTALE 

### Archivio:
Il sistema archivia i seguenti dati:
-	Il NOME CLIENTE
-	Il TOTALE da pagare per l’ordine
-	La DATA in cui è stato effettuato l’ordine
-	Le QUANTITA’ dei PRODOTTI ordinate

### File di log remoto in XML:
Il sistema invia una riga di log ad ogni evento del tipo:
-	Avvio dell’applicazione (“AVVIO”)
-	Pressione dei pulsati “INSERISCI”, “INVIA”, “RIMUOVI”
-	Termine dell’applicazione (“TERMINE”)
La riga di log è composta da: [ nome dell’applicazione, indirizzo IP del client, timestamp, etichetta dell’evento ]


