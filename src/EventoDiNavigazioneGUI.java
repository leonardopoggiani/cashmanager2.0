import com.thoughtworks.xstream.*;
import java.io.*;
import java.text.*;
import java.util.*;

public class EventoDiNavigazioneGUI implements Serializable { // 01)
    private final String nomeApplicazione = "Cashmanager";
    private final String nomeEvento, indirizzoIpClient;
    private final String dataOraCorrente;
    
    public EventoDiNavigazioneGUI(String nomeEvento, String indirizzoIpClient) {
        this.nomeEvento = nomeEvento;
        this.indirizzoIpClient = indirizzoIpClient;
        this.dataOraCorrente = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss").format(new Date());
    }
    
    public String toString() { 
        XStream xs = new XStream();
        xs.useAttributeFor(EventoDiNavigazioneGUI.class,"nomeApplicazione");
        xs.useAttributeFor(EventoDiNavigazioneGUI.class, "nomeEvento"); 
        xs.useAttributeFor(EventoDiNavigazioneGUI.class, "indirizzoIpClient"); 
        xs.useAttributeFor(EventoDiNavigazioneGUI.class, "dataOraCorrente"); 
        return xs.toXML(this) + "\n"; 
    }
}

/* Commenti: 

 01) Classe che rappresenta l'oggetto che si scambiano l'applicativo e il server di log

*/