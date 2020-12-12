import java.io.*;
import java.nio.file.*;

public class CaricaFile { 
    private final static String PERCORSO_RELATIVO = "..\\..\\"; 
    
    public static void salva(Object o, String file, boolean append) { 
        try {
            Files.write( 
                Paths.get(PERCORSO_RELATIVO + file), 
                o.toString().getBytes(), 
                (append) ? StandardOpenOption.APPEND : StandardOpenOption.TRUNCATE_EXISTING 
            );
        } catch(IOException e) {
            System.err.println(e.getMessage());
        }
    }
    
    public static void svuota(String file) { 
        if(file.compareTo("cache.bin") == 0){
            try {
                Files.delete(Paths.get(file));
            } catch (IOException ex) {System.err.println(ex.getMessage());}
        }
        salva("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!-- " + file + " -->\n", file, false); 
    }
    
    public static String carica(String file) { 
        try {
            return new String(Files.readAllBytes(Paths.get(file))); 
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return null; 
    }
}
