import java.time.*;
import javafx.collections.*;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.*;
import javafx.scene.control.cell.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;

class AreaOrdine{ 

    private final VBox contenitore;
    private final Label titolo;
    private final TableView<Prodotto> tb = new TableView<>();
    private final ObservableList<Prodotto> ol;
    private final TableColumn nomeProdottoCol;
    private final TableColumn quantitaCol;
    private final TableColumn subtotaleCol;
    private final HBox pulsanti;
    private final Button invia;
    private final Button rimuovi;
    private final TextField totale;
    private final InterfacciaOrdine interfacciaOrdine;
    private final FileDiConfigurazioneXML config;
    
    private double tot = 0;

    AreaOrdine(InterfacciaOrdine interfacciaOrdine,FileDiConfigurazioneXML config){
        this.config = config;
        this.interfacciaOrdine = interfacciaOrdine;
        
        ol = FXCollections.observableArrayList(new Prodotto("Coperto","",1),new Prodotto("Pici al ragù","",6),new Prodotto("Salsiccia","",4),new Prodotto("Braciola","",6.5), new Prodotto("Pizza","",5), new Prodotto("Acqua","",1),new Prodotto("Birra","",3.5)); // 01)
        tb.setItems(ol);
        
        titolo = new Label("Ordine");
        
        subtotaleCol = new TableColumn("Subtotale");
        nomeProdottoCol = new TableColumn("Prodotto");
        quantitaCol = new TableColumn("Quantita");
        
        impostaStileTabella();
        
        invia = new Button("Invia");
        invia.setOnAction((inviaHandler)->{inviaOrdine();});
        
        rimuovi = new Button("Rimuovi");
        rimuovi.setOnAction((rimuoviHandler)->{rimuoviProdotto();});
        
        totale = new TextField();
        totale.setPromptText("Totale da calcolare");
        totale.maxWidth(10);
        totale.autosize();
        
        pulsanti = new HBox(config.spaziatura);
        pulsanti.setAlignment(Pos.CENTER_LEFT);
        pulsanti.getChildren().addAll(invia,rimuovi);
                
        
        contenitore = new VBox(config.spaziatura);
        contenitore.getChildren().addAll(titolo,tb,pulsanti,totale);
        contenitore.setAlignment(Pos.CENTER_LEFT);
        
        impostaStile();
    }
    
    public VBox getContenitore(){
        return contenitore;
    }

    private void impostaStile(){
        titolo.setFont(Font.font(config.font,config.dimensioneFont + config.dimensioneTitolo));
        titolo.setStyle("-fx-font-weight:bold");
        tb.setFixedCellSize(config.altezzaRiga);
        tb.prefHeightProperty().set((config.numeroRighe + 1)*config.altezzaRiga);
        invia.setFont(Font.font(config.font , config.dimensioneFont));
        rimuovi.setFont(Font.font(config.font,config.dimensioneFont));
    }
    
    private void inviaOrdine() { // 02)
        SerializzaLogXML.creaLog("Pressione tasto invio", config); // 03)
        String nomeCliente = interfacciaOrdine.getAreaCliente().getNomeOrdine().getText();
        LocalDate dataOrdine = interfacciaOrdine.getAreaCliente().getDate().getValue();
        ObservableList<Prodotto> items = tb.getItems();
        
        items.stream().map((item) -> {
            tot += item.getPrezzo();
            return item;
        }).forEach((item) -> {
            ArchivioOrdini.aggiungiProdottoDB(item, interfacciaOrdine.getAreaCliente().getDate().getValue().getDayOfYear());
        });
        
        totale.setText(Double.toString(tot));
        ArchivioOrdini.registraOrdineDB(nomeCliente,dataOrdine,tot);     
        ripristinaInterfacciaOrdine();
    }

    private void ripristinaInterfacciaOrdine(){ // 04)
        ol.clear();
        ol.addAll(new Prodotto("Coperto","0",1),new Prodotto("Pici al ragù","0",6),new Prodotto("Salsiccia","0",4),new Prodotto("Braciola","0",6.5), new Prodotto("Pizza","0",5), new Prodotto("Acqua","0",1),new Prodotto("Birra","0",3.5)); // 05)
        tot = 0;
        interfacciaOrdine.getAreaStatistiche().aggiornaPieChart();
        interfacciaOrdine.getAreaCliente().getNomeOrdine().clear();
        interfacciaOrdine.getAreaCliente().getDate().setValue(LocalDate.now());
    }
    
    private void rimuoviProdotto() { // 06)
        SerializzaLogXML.creaLog("Pressione tasto rimuovi", config);
        Prodotto prodottoSelezionato = tb.getSelectionModel().getSelectedItem();
        int i = tb.getItems().indexOf(prodottoSelezionato);
        prodottoSelezionato.setQuantita(Integer.toString(Integer.parseInt(prodottoSelezionato.getQuantita())-1));
        prodottoSelezionato.setSubtotale();
        tb.getItems().set(i, prodottoSelezionato);
        ArchivioOrdini.eliminaProdotto(prodottoSelezionato,interfacciaOrdine.getAreaCliente().getNomeOrdine().getText(),interfacciaOrdine.getAreaCliente().getDate().getValue());
    }
    
    public TableView getTabella(){
        return tb;
    }

    public double getTot(){
        return tot;
    }
    
    public ObservableList getOl(){
        return ol;
    }
    
    private void impostaStileTabella() { // 07)
        tb.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tb.setMaxHeight(180);
        tb.setMaxWidth(250);    
        
        tb.setEditable(true);
        
        subtotaleCol.setCellValueFactory(new PropertyValueFactory<>("prezzo"));
            
        quantitaCol.setCellValueFactory(new PropertyValueFactory<>("quantita"));
        quantitaCol.setCellFactory(TextFieldTableCell.forTableColumn());
        quantitaCol.setOnEditCommit(
                new EventHandler<CellEditEvent<Prodotto,String>>(){
                    
                    @Override
                    public void handle(CellEditEvent<Prodotto,String> t){
                        ((Prodotto) tb.getItems().get(t.getTablePosition().getRow())).setQuantita(t.getNewValue());
                        tb.getSelectionModel().getSelectedItem().setSubtotale();
                        tb.getItems().set(tb.getSelectionModel().getSelectedIndex(), tb.getItems().set(tb.getSelectionModel().getSelectedIndex(), tb.getSelectionModel().getSelectedItem()));
                    }
                }
        );
        
        
        nomeProdottoCol.setCellValueFactory(new PropertyValueFactory<>("nome"));
        nomeProdottoCol.setCellFactory(TextFieldTableCell.forTableColumn());
        tb.getColumns().addAll(nomeProdottoCol,quantitaCol,subtotaleCol);

        tb.setTableMenuButtonVisible(true);
    }
    
    public static double ottieniPrezzo(String prodotto) {
        switch(prodotto){
            case "Pizza":
                return 5;
            case "Birra":
                return 3.5;
            case "Braciola":
                return 6.5;
            case "Salsiccia":
                return 4;
            case "Pici al ragù":
                return 6;
            case "Coperto":
                return 1;
            case "Acqua":
                return 1;
            default:
                return 0;
        }
    }
}


/* Commenti 
    
 01) Lista dei prodotti di default da mostrare

 02) Handler del pulsante "Invia"

 03) Crea il relativo messaggio di log

 04) Riporta l'interfaccia grafica allo stato iniziale dopo aver premuto "Invia"

 05) Ricarica la lista dei prodotti di default

 06) Handler del pulsante "Rimuovi"

 07) Inizializza e imposta lo stile della tabella degli ordini

*/