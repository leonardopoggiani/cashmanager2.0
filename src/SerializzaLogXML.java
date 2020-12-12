import java.io.*;
import java.net.*;

public class SerializzaLogXML { // 01)
    private static void invia(EventoDiNavigazioneGUI log, String ipServerLog, int portaServerLog) { 
        try(Socket socket = new Socket(ipServerLog, portaServerLog); 
            ObjectOutputStream oout = new ObjectOutputStream(socket.getOutputStream()); 
        ) {
            oout.writeObject(log.toString());
        } catch(IOException e) {
            System.err.println(e.getMessage());
        }
    }
    
    public static void creaLog(String tipoDiLog, FileDiConfigurazioneXML config) { 
        invia(
            new EventoDiNavigazioneGUI(tipoDiLog, config.ipClient), 
            config.ipServerLog, 
            config.portaServerLog
        );
    }
}

/* Commenti

 01) Classe che si occupa di serializzare l'oggetto EventoDiNavigazioneGUI e inviarlo al server di log

*/