import com.sun.prism.paint.Paint;
import com.thoughtworks.xstream.*;

public class FileDiConfigurazioneXML { 
    final static double LARGHEZZA_DEFAULT_FINESTRA = 970.0, 
                        ALTEZZA_DEFAULT_FINESTRA = 650.0, 
                        ALTEZZA_RIGA_DEFAULT = 30.0,
                        SPAZIATURA_DEFAULT = 10.0,
                        DIMENSIONE_LABEL_DEFAULT = 0.0,
                        DIMENSIONE_TITOLO_DEFAULT = 0.0;                           
    final static int    NUMERO_RIGHE_DEFAULT = 16,  
                        PORTA_SERVER_LOG_DEFAULT = 6789, 
                        DIMENSIONE_FONT_DEFAULT = 11,  
                        NUMERO_GIORNI_DEFAULT = 5,
                        NUMERO_PRODOTTI_DEFAULT = 5;
    final static String FONT_DEFAULT = "Arial",  
                        COLORE_SFONDO_DEFAULT = "WHITE";  
    final String font; 
    final double dimensioneFont; 
    final double[] dimensioni; 
    final double altezzaRiga;
    final double spaziatura;
    final double dimensioneLabel;
    final double dimensioneTitolo;
    final String coloreSfondo; 
    final int numeroRighe; 
    final String ipClient; 
    final String ipServerLog; 
    final int portaServerLog; 
    final int numeroProdotti;
    final int numeroGiorni;
     
    public FileDiConfigurazioneXML(String xml) {
        dimensioni = new double[2];
        
        if(xml == null || xml.compareTo("") == 0) { // 10)
            font = FONT_DEFAULT;
            dimensioneFont = DIMENSIONE_FONT_DEFAULT;
            dimensioni[0] = LARGHEZZA_DEFAULT_FINESTRA;
            dimensioni[1] = ALTEZZA_DEFAULT_FINESTRA;
            coloreSfondo = COLORE_SFONDO_DEFAULT;
            numeroRighe = NUMERO_RIGHE_DEFAULT;
            ipClient = "127.0.0.1";
            ipServerLog = "127.0.0.1";
            portaServerLog = PORTA_SERVER_LOG_DEFAULT;
            numeroGiorni = NUMERO_GIORNI_DEFAULT;
            numeroProdotti = NUMERO_PRODOTTI_DEFAULT;
            altezzaRiga = ALTEZZA_RIGA_DEFAULT;
            dimensioneTitolo = DIMENSIONE_TITOLO_DEFAULT;
            dimensioneLabel = DIMENSIONE_LABEL_DEFAULT;
            spaziatura = SPAZIATURA_DEFAULT;
            
        } else {
            FileDiConfigurazioneXML p = (FileDiConfigurazioneXML)creaXStream().fromXML(xml); // 11)
            font = p.font;
            dimensioneFont = p.dimensioneFont;
            dimensioni[0] = p.dimensioni[0];
            dimensioni[1] = p.dimensioni[1];
            coloreSfondo = p.coloreSfondo;
            numeroRighe = p.numeroRighe;
            ipClient = p.ipClient;
            ipServerLog = p.ipServerLog;
            portaServerLog = p.portaServerLog;
            numeroGiorni = p.numeroGiorni;
            numeroProdotti = p.numeroProdotti;
            altezzaRiga = p.altezzaRiga;
            dimensioneTitolo= p.dimensioneTitolo;
            dimensioneLabel = p.dimensioneLabel;
            spaziatura = p.spaziatura;
        }
    }

    public final XStream creaXStream() {
        XStream xs = new XStream();
        xs.useAttributeFor(FileDiConfigurazioneXML.class, "numeroRighe"); 
        xs.useAttributeFor(FileDiConfigurazioneXML.class, "ipClient"); 
        xs.useAttributeFor(FileDiConfigurazioneXML.class, "ipServerLog");
        xs.useAttributeFor(FileDiConfigurazioneXML.class, "portaServerLog"); 
        xs.useAttributeFor(FileDiConfigurazioneXML.class, "numeroProdotti");  
        xs.useAttributeFor(FileDiConfigurazioneXML.class, "numeroGiorni"); 
        return xs;
    }
    
    public String toString() { 
        return creaXStream().toXML(this);
    }
}
