// Classe Bean
import com.thoughtworks.xstream.*;
import java.time.*;
import javafx.beans.property.*;

public class Ordine {
    private final SimpleStringProperty nomeCliente;
    private final SimpleDoubleProperty totale;
    private final ObjectProperty<LocalDate> data;
    
    public Ordine(String nomeCliente, LocalDate data, double totale) {
        this.nomeCliente = new SimpleStringProperty(nomeCliente); this.totale = new SimpleDoubleProperty(totale); this.data = new SimpleObjectProperty<>(data);
    }
    
    public final static XStream creaXStream(){
        XStream xs = new XStream();
        xs.useAttributeFor(Ordine.class,"nomeCliente");
        xs.useAttributeFor(Ordine.class,"totale");
        xs.useAttributeFor(Ordine.class,"data");
        return xs;
    }
    
    public String toString(){
        return creaXStream().toXML(this);
    }
    
    public String getString(){
        return nomeCliente.get();
    }
    
    public double getTotale(){
        return totale.get();
    }
    
    public LocalDate getData(){
        return data.get();
    }
}
