import javafx.geometry.*;
import javafx.scene.layout.*;

class InterfacciaOrdine {
    private final AreaOrdine areaOrdine;
    private final AreaCliente areaCliente;
    private final AreaStatistiche areaStatistiche;
    VBox contenitore;
    
    InterfacciaOrdine(FileDiConfigurazioneXML config, String fileCache){
        
        areaCliente = new AreaCliente(this,config);
        areaOrdine = new AreaOrdine(this,config);
        areaStatistiche = new AreaStatistiche(this,config);
        
        CacheOrdine.triggerCache(this,fileCache);
        
        VBox allineamento = new VBox(config.spaziatura);
        allineamento.getChildren().addAll(areaOrdine.getContenitore());
        
        HBox allinea = new HBox(config.spaziatura);
        allinea.setPadding(new Insets(10,10,10,10));
        allinea.getChildren().addAll(allineamento,areaStatistiche.getContenitore());
        contenitore = new VBox(config.spaziatura);
        contenitore.getChildren().addAll(areaCliente.getContenitore(),allinea);    
    
    }
    
    public AreaOrdine getAreaOrdine(){
        return areaOrdine;
    }
    
    public AreaCliente getAreaCliente(){
        return areaCliente;
    }
    
    public AreaStatistiche getAreaStatistiche(){
        return areaStatistiche;
    }
    
    public VBox getContenitore(){
        return contenitore;
    }
}
