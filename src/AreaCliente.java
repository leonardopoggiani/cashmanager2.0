import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;

class AreaCliente {

    private final HBox contenitore;
    private final DatePicker data;
    private final TextField nomeCliente;
    private final Label nomeOrdine;
    private final Label dataOrdine;
    private final FileDiConfigurazioneXML config;
    
    AreaCliente(InterfacciaOrdine interfacciaOrdine,FileDiConfigurazioneXML config){
        this.config = config;
        data = new DatePicker();
        nomeCliente = new TextField();
        nomeCliente.setPromptText("Nome cliente");
        nomeOrdine = new Label("Nome Cliente");
        dataOrdine = new Label("Data");
        contenitore = new HBox(config.spaziatura);
        contenitore.getChildren().addAll(nomeOrdine,nomeCliente,dataOrdine,data);
        contenitore.setAlignment(Pos.CENTER);
        
        impostaStile();
    }
    
    private void impostaStile(){
        nomeOrdine.setFont(Font.font(config.font,config.dimensioneFont + config.dimensioneLabel));
        dataOrdine.setFont(Font.font(config.font,config.dimensioneFont + config.dimensioneLabel));
        nomeCliente.setFont(Font.font(config.font,config.dimensioneFont + config.dimensioneLabel));
        data.setStyle("-fx-font-size:" + config.dimensioneFont + "px");
    }
    
    
    public HBox getContenitore(){
        return contenitore;
    }
    
    public TextField getNomeOrdine(){
        return nomeCliente;
    }
    
    public DatePicker getDate(){
        return data;
    }
    
    public void setNomeCliente(String nomeCliente){
        this.nomeCliente.replaceSelection(nomeCliente);
    }
}
