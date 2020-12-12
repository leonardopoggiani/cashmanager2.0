import com.thoughtworks.xstream.*;
import javafx.beans.property.*;

 //Classe Bean

public class Prodotto {
    private final SimpleStringProperty nome;
    private final SimpleStringProperty quantita;
    private final SimpleDoubleProperty prezzo;
    
    Prodotto(String nome, String quantita, double prezzo){
        this.nome = new SimpleStringProperty(nome); 
        this.quantita = new SimpleStringProperty(quantita); 
        this.prezzo = new SimpleDoubleProperty(prezzo);
    }
    
    public final static XStream creaXStream(){
        XStream xs = new XStream();
        xs.useAttributeFor(Prodotto.class,"nome");
        xs.useAttributeFor(Prodotto.class,"quantita");
        xs.useAttributeFor(Prodotto.class,"prezzo");
        return xs;
    }

    public String getNome(){
        return nome.get();
    }
    
    public String getQuantita(){
        return quantita.get();
    }
    
    public double getPrezzo(){
        return prezzo.get();
    }
    
    public void setNome(String nome){
        this.nome.set(nome);
    }
    
    public void setQuantita(String quantita){
        this.quantita.set(quantita);
    }
    
    public void setSubtotale(){
        if(Integer.parseInt(this.getQuantita()) != 0)
            this.prezzo.set(Integer.parseInt(this.getQuantita())*AreaOrdine.ottieniPrezzo(this.getNome()));
        else 
            this.prezzo.set(AreaOrdine.ottieniPrezzo(this.getNome()));
        
        System.out.print("Prezzo : " + Integer.parseInt(this.getQuantita())*AreaOrdine.ottieniPrezzo(this.getNome()) + "\n");
    }
    
}
