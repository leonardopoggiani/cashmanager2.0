import javafx.application.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.stage.*;

public class CashManager extends Application {
    
    private final static String FILE_CONFIGURAZIONE = "config.xml",
                                SCHEMA_CONFIGURAZIONE = "config.xsd",
                                FILE_CACHE = "cache.bin"; 
    private VBox contenitore;
    private InterfacciaOrdine interfacciaOrdine;
    
    public void start(Stage stage) {
        
         FileDiConfigurazioneXML config = 
            new FileDiConfigurazioneXML(
                ValidatoreXML.valida(CaricaFile.carica(FILE_CONFIGURAZIONE), SCHEMA_CONFIGURAZIONE, false) ?
                    CaricaFile.carica(FILE_CONFIGURAZIONE) : null 
            );
         
        SerializzaLogXML.creaLog("Apertura applicazione", config); 
        
        interfacciaOrdine = new InterfacciaOrdine(config,FILE_CACHE); 
      
        stage.setOnCloseRequest((WindowEvent we) -> { 
            SerializzaLogXML.creaLog("Chiusura applicazione", config); 
            CacheOrdine.salva(interfacciaOrdine, FILE_CACHE);
        });
       
        contenitore = new VBox(config.spaziatura);
        contenitore.getChildren().add(interfacciaOrdine.getContenitore());
        contenitore.setStyle("-fx-background-color: " + config.coloreSfondo);
        Group root = new Group(contenitore);
        root.setStyle("-fx-background-color: " + config.coloreSfondo);
        root.setStyle("-fx-background-size: 800 500");
        Scene scene = new Scene(root,650,400);
        stage.setTitle("Cash Manager");
        stage.setScene(scene);
        stage.show();
    }
}
