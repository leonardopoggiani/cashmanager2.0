import java.io.*;
import java.time.*;
import javafx.collections.*;

public class CacheOrdine implements Serializable { 
    private final LocalDate dataOrdine; 
    private final String nomeCliente; 
    private final int voceSelezionata; 
    private final int[] quantita;
    
    public CacheOrdine(InterfacciaOrdine interfacciaOrdine) { // 01) 
        dataOrdine = interfacciaOrdine.getAreaCliente().getDate().getValue();
        nomeCliente = interfacciaOrdine.getAreaCliente().getNomeOrdine().getText();
        voceSelezionata = interfacciaOrdine.getAreaOrdine().getTabella().getSelectionModel().getSelectedIndex();
        quantita = new int[interfacciaOrdine.getAreaOrdine().getTabella().getItems().size()];
        ObservableList<Prodotto> ol = FXCollections.observableArrayList();
        ol = interfacciaOrdine.getAreaOrdine().getTabella().getItems();
        
        for(int i = 0; i < interfacciaOrdine.getAreaOrdine().getTabella().getItems().size();i++){
            String x =  ol.get(i).getQuantita();
            quantita[i] = Integer.parseInt("".equals(x) ? "0" : x);
            System.out.println("Salvo: <" + ol.get(i).getNome() + " / " + ol.get(i).getQuantita() + ">");
        }
    }
    
    public static void triggerCache(InterfacciaOrdine interfacciaOrdine, String fileCache){ // 02)
        CacheOrdine cache = carica(fileCache);
        if(cache != null){
            interfacciaOrdine.getAreaCliente().getNomeOrdine().setText(cache.getCliente());
            interfacciaOrdine.getAreaCliente().getDate().setValue(cache.getDataOrdine());
            interfacciaOrdine.getAreaOrdine().getTabella().getSelectionModel().select(cache.getVoceSelezionata());
            for(int i = 0; i < cache.getQuantita().length ;i++){
                int[] quantita = cache.getQuantita();
                ObservableList<Prodotto> list = FXCollections.observableArrayList();
                list = interfacciaOrdine.getAreaOrdine().getTabella().getItems();
                list.get(i).setQuantita(Integer.toString(quantita[i]));
                list.get(i).setSubtotale();
                interfacciaOrdine.getAreaOrdine().getTabella().getItems().set(i, list.get(i));
                System.out.println("Carico: <" + list.get(i).getNome() + " / " + list.get(i).getQuantita() + ">");
            }
        }    
    }
    
    public final static void salva(InterfacciaOrdine interfacciaOrdine, String file) { 
        try(ObjectOutputStream oout = new ObjectOutputStream(new FileOutputStream(file))) { 
            oout.writeObject(new CacheOrdine(interfacciaOrdine)); 
        } catch(IOException e) {
            System.err.println(e.getMessage());
        }
    }
    
    public final static CacheOrdine carica(String file) {
        try(ObjectInputStream oin = new ObjectInputStream(new FileInputStream(file))) { 
            return (CacheOrdine)oin.readObject(); 
        } catch(IOException | ClassNotFoundException e) {
            if(file.equals("cache.bin")){ // 03)
                System.out.println("Primo avvio, cache non presente");
            }else{      
                System.err.println(e.getMessage());
            }
        }
        return null; 
    }
    
    public LocalDate getDataOrdine() { return dataOrdine; }
    public String getCliente() { return nomeCliente; }
    public int getVoceSelezionata() { return voceSelezionata; }
    public int[] getQuantita() { return quantita; }
}

/* Commenti

 01) Crea un oggetto di tipo CacheOrdine con i dati dell'interfaccia grafica che sono stati passati come parametro

 02) Trigger che carica il file di cache salvato

03) La prima volta che avvio l'applicazione il file di cache non esiste

*/