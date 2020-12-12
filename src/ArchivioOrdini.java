import java.sql.*;
import java.time.*;
import java.util.*;

public class ArchivioOrdini{
    
     public static void aggiungiProdottoDB(Prodotto p,int giorno) {
        try( Connection co = DriverManager.getConnection("jdbc:mysql://localhost:3306/cashmanager","root","");  
                PreparedStatement ps = co.prepareStatement("INSERT INTO prodotti(nomeprodotto,quantita,giorni) VALUES (?,?,?)");    // 01)
            ) {
            if(Integer.parseInt(p.getQuantita()) != 0){ // 02)
                ps.setInt(3, giorno);
                System.out.println("Aggiungo: " + p.getNome() + " ,quantita: " + p.getQuantita());
                ps.setString(1, p.getNome()); ps.setInt(2,Integer.parseInt(p.getQuantita()));
                ps.execute();
                aggiornaProdottiDB();
            }
        }    
    catch (SQLException ex){System.err.println(ex.getMessage());}
    }
    
    private static void aggiornaProdottiDB() {
        try( Connection co = DriverManager.getConnection("jdbc:mysql://localhost:3306/cashmanager","root","");
                PreparedStatement ps = co.prepareStatement("DELETE FROM prodotti WHERE giorni IS NULL OR ? - giorni > 3  ");  // 03)
            ) { 
            int now = LocalDate.now().getDayOfYear();
            ps.setInt(1, now);
            System.out.println("rows affected: " + ps.executeUpdate());
        }
        catch (SQLException ex){System.err.println(ex.getMessage());}
    }
    
    public static void registraOrdineDB(String nomeCliente, LocalDate dataOrdine,double totale) {
        try( Connection co = DriverManager.getConnection("jdbc:mysql://localhost:3306/cashmanager","root","");
                PreparedStatement ps = co.prepareStatement("INSERT INTO ordini(nomecliente,totale,data) VALUES (?,?,?)");
                PreparedStatement ps1 = co.prepareStatement("UPDATE prodotti SET NomeCliente = ? WHERE NomeCliente IS NULL");  // 04)
            ) {
            ps.setString(1, nomeCliente); ps.setDouble(2, totale); ps.setObject(3, dataOrdine.toString());
            System.out.println("rows affected: " + ps.executeUpdate());
            ps1.setString(1,nomeCliente);
            System.out.println("rows affected: " + ps1.executeUpdate());
        }
       catch (SQLException ex){System.err.println(ex.getMessage());}
    }

    public static void eliminaProdotto(Prodotto prodottoDaEliminare, String nomeCliente,LocalDate data) {
        try( Connection co = DriverManager.getConnection("jdbc:mysql://localhost:3306/cashmanager","root","");
                PreparedStatement ps = co.prepareStatement("SELECT MAX(idprodotti) AS target FROM prodotti  WHERE nomeprodotto = ? AND nomecliente = ? AND giorni = ? AND quantita = ?;");  // 05)
                PreparedStatement ps1 = co.prepareStatement("DELETE FROM prodotti WHERE idprodotti = ?");
            ) {
            ps.setString(1, prodottoDaEliminare.getNome()); ps.setString(2, nomeCliente); ps.setInt(3,data.getDayOfYear() ); ps.setInt(4, Integer.parseInt(prodottoDaEliminare.getQuantita()));
            ResultSet rs = ps.executeQuery();
            int prodottoTarget = -1;
            if(rs.next()){
                System.out.println("Target" + rs.getInt("target"));
                prodottoTarget = rs.getInt("target");
            }
            ps1.setInt(1, prodottoTarget);
            System.out.println("rows affected: " + ps1.executeUpdate() + " elimino: " + prodottoTarget);           
        }
       catch (SQLException ex){System.err.println(ex.getMessage());}
    }

    public static ArrayList caricaProdottiDB() {
        ArrayList<Prodotto> prodotti = new ArrayList();
        try( Connection co = DriverManager.getConnection("jdbc:mysql://localhost:3306/cashmanager","root","");
             PreparedStatement ps = co.prepareStatement("SELECT * FROM prodotti WHERE nomeCliente IS NULL");
            ){
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                prodotti.add(new Prodotto(rs.getString("nomeprodotto"),rs.getString("quantita"),AreaOrdine.ottieniPrezzo(rs.getString("nomeprodotto"))*rs.getInt("quantita")));  // 06)
            }
            
        } catch (SQLException e) { System.err.println(e.getMessage());}
        return prodotti;
    }
    
    public static ArrayList ottieniProdottiDB(int numeroProdotti) {
        ArrayList<Prodotto> prodotti = new ArrayList();
        
        try( Connection co = DriverManager.getConnection("jdbc:mysql://localhost:3306/cashmanager","root",""); 
                PreparedStatement ps = co.prepareStatement("SELECT nomeprodotto,SUM(quantita) AS Quantita FROM cashmanager.prodotti WHERE nomeprodotto != 'Coperto' AND nomeprodotto != 'Birra' AND nomeprodotto != 'Acqua' GROUP BY nomeprodotto ORDER BY Quantita DESC"))  // 07)
        {
            ResultSet rs = ps.executeQuery();
            int i = 0;
            while(rs.next()&& i != numeroProdotti){
                i++;
                System.out.println("Aggiungo al grafico: " + rs.getString("nomeprodotto") + " , in quantita: " + rs.getDouble("quantita"));
                double prezzo = AreaOrdine.ottieniPrezzo(rs.getString("nomeprodotto"));
                prodotti.add(new Prodotto(rs.getString("nomeprodotto"), rs.getString("quantita"),prezzo*rs.getInt("quantita")));           
            }       
    }   catch (SQLException ex){System.err.println(ex.getMessage());}
        
        
    return prodotti;
    }
    
    
}

/* Commenti

 01) Query per l'inserimento dei prodotti

 02) Se la quantità è 0 non devo inserire

 03) Cancello i prodotti più vecchi di 3 giorni
    
 04) Setta il nome di ordini precedentemente lasciati a metà e ricaricati con la cache

 05) Rottura della parità per l'eventualità in cui ho prodotti uguali nello stesso ordine

 06) Aggiunge i prodotti dell'ordine che prima non avevo ultimato

 07) Aggiunge come prodotti da mostrare nel grafico 

*/