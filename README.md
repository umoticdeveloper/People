Il client genera real time la posizione dell'utente. 
Il client chiama un "thread" temporizzato (da decidere il tempo di invio dei dati) che a sua volta invia una richiesta al server (GET o POST).
Il "thread" può inviare 2 richieste: 
   - Richiesta di scrittura/update dei dati relativi all'utente.
   - Richiesta di lettura dei dati di tutti gli altri utenti.
La scrittura su db non deve essere real time. Una scrittura real time andrebbe a gravare sulle prestazioni e sarebbe in più inutile. Le posizioni vanno scritte sul db in maniera temporizzata (poichè non serve sapere per ogni 
secondo di quanto si spostano gli utenti, ma basta anche sapere dei loro spostamenti ogni 5/10 minuti).

Il server conterrà:
   - Database: contenente una sola tabella che fornisca info sulla posizione, età, sesso, utenza VIP, data dell'ultima rilevazione (potrebbe servire per una gestione più logica che applicativa) ed infine un ID che possa identificare
     univocamente un utente, per poter eseguire le update sulla precisa tupla.
   - Script PHP: 
     - Connessione al DB;
	 - RetrieveUserData.php
	 - UpdateUserData.php
	 - RegisterUser.php     ?
	 - Login.php            ?
	 - DeleteUser.php
	 
Il server riceve dal client una richiesta HTTP. Questa richiesta conterrà delle parole chiave per instradare la stessa verso uno degli script appena elencati. Ciascuno script restituirà una risposta sotto forma di Json.


Il Client legge il file Json e gestisce a sua volta i dati.
