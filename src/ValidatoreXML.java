import java.io.*;
import javax.xml.*;
import javax.xml.parsers.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import javax.xml.validation.*;
import org.w3c.dom.*;
import org.xml.sax.*;

public class ValidatoreXML { 
    private final static String PERCORSO_RELATIVO = "..\\";
    
    public static boolean valida(String xml, String fileSchema, boolean file) { 
        try {
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI); 
            Document d; 
            if(file) 
                d = db.parse(new File(xml)); 
            else 
                d = db.parse(new InputSource(new StringReader(xml))); 
            Schema s = sf.newSchema(new StreamSource(new File(((fileSchema.compareTo("log.xsd") == 0) ? PERCORSO_RELATIVO : "") + fileSchema)));
            s.newValidator().validate(new DOMSource(d)); 
        } catch(ParserConfigurationException | SAXException | IOException e) {
            if(e instanceof SAXException) 
                System.err.println("Errore di validazione"); 
            
            System.err.println(e.getMessage());
            return false; 
        }
        return true; 
    }
}