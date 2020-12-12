import java.util.*;
import javafx.collections.*;
import javafx.geometry.*;
import javafx.scene.chart.*;
import javafx.scene.layout.*;


class AreaStatistiche {
    private final VBox contenitore;
    private final PieChart grafico;
    private final int numeroGiorni;
    private final int numeroProdotti;
    private final FileDiConfigurazioneXML config;
    
    AreaStatistiche(InterfacciaOrdine interfacciaOrdine, FileDiConfigurazioneXML config){
        this.config = config;
        numeroGiorni = config.numeroGiorni;
        numeroProdotti = config.numeroProdotti;
        
        ArrayList<Prodotto> prodottiDB = ArchivioOrdini.ottieniProdottiDB(numeroProdotti);
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        for(int i = 0; i != prodottiDB.size(); i++){ // 01
            data.add(new PieChart.Data(prodottiDB.get(i).getNome(),Double.parseDouble(prodottiDB.get(i).getQuantita())));        
        }
        
        grafico = new PieChart(data);
        grafico.setTitle("Piatti più ordinati negli ultimi " + numeroGiorni + " giorni");
        grafico.setMaxHeight(350);
        grafico.setMaxWidth(350);
        contenitore = new VBox(config.spaziatura);
        contenitore.getChildren().addAll(grafico);
        impostaStile();
    }
    
    private void impostaStile(){
        grafico.setStyle("-fx-background-color: " + config.coloreSfondo);
        grafico.setLegendSide(Side.BOTTOM);
        grafico.setStyle("-fx-font-size:" + config.dimensioneFont + "px");
        grafico.setStyle("-fx-font-weight:bold");
    }
    
    public VBox getContenitore(){
        return contenitore;
    }
    
    void aggiornaPieChart() {
        
        ArrayList<Prodotto> prodottiDB = ArchivioOrdini.ottieniProdottiDB(numeroProdotti);
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        for(int i = 0; i != prodottiDB.size(); i++){
            data.add(new PieChart.Data(prodottiDB.get(i).getNome(),Double.parseDouble(prodottiDB.get(i).getQuantita())));        
        }
        
        grafico.setData(FXCollections.observableArrayList(data));
    }
}


/* Commenti

 01) Aggiunge i prodotti da mostrare nel grafico

*/