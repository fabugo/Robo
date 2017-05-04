package robo;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.jsoup.select.Elements;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ParseXML {

    private static String ENDERECO_SEEDS = "src\\robo\\seeds.xml",
                                ENDERECO_CENTROIDES = "src\\robo\\centroides.xml";
    private final List<Seed> seeds;
    
    /**
     * 
     * @return lista de sementes
     */
    public List<Seed> getSeeds() {
        return seeds;
    }

    /**
     * 
     * @param links a serem adicionados nas sementes
     */
    public void addSeed(Elements links) {
        for (org.jsoup.nodes.Element link : links) {
            //System.out.println("\nlink : " + link.attr("href")); // link
            //System.out.println("text : " + link.text()); // texto PESO DE TEXTO DE LINK
            Seed s = new Seed(link.attr("href"), "");
            if (!seeds.contains(s)) {
                try {
                    java.net.URL z;
                    z = new java.net.URL(s.URL);
                    java.net.URLConnection conn = z.openConnection();
                    conn.connect();
                } catch (MalformedURLException ex) {
                    System.out.println("URL Inválida");
                    return;
                } catch (IOException ex) {
                    System.out.println("Não foi possível conectar");
                }
                seeds.add(s);
            }

            System.out.println("QUANTIDADE DE SEMENTES: " + seeds.size());

            if (seeds.size() % 500 == 0) {
                this.saveXML();
            }
        }

    }

    /**
     * Ler XML e cria lista de links
     */
    public ParseXML() {
        seeds = parseSeedsXML();
    }

    /**
     * Escreve XML
     */
    public void saveXML() {

        OutputStream streamOut = null;
        try {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            //root elements
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("seeds");
            doc.appendChild(rootElement);

            Iterator<Seed> i = seeds.iterator();
            while (i.hasNext()) {
                Seed sem = i.next();
                Element sement = doc.createElement("seed");

                Element url_seed = doc.createElement("url");
                url_seed.setTextContent(sem.URL);
                sement.appendChild(url_seed);

                Element dt_visit = doc.createElement("visited");
                dt_visit.setTextContent(sem.VISITED);
                sement.appendChild(dt_visit);

                rootElement.appendChild(sement);

            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);

            StringWriter stringWriter = new StringWriter();
            StreamResult result = new StreamResult(stringWriter);
            // Output to console for testing
            // StreamResult result = new StreamResult(System.out);
            transformer.transform(source, result);
            System.out.println(stringWriter.toString());

            File xmlMap = new File(ENDERECO_SEEDS);
            streamOut = new FileOutputStream(xmlMap);

            XMLSerializer serializer = new XMLSerializer(streamOut, new OutputFormat(doc, "utf-8", true));
            serializer.serialize(doc);

        } catch (ParserConfigurationException ex) {
            Logger.getLogger(ParseXML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(ParseXML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(ParseXML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ParseXML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ParseXML.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * 
     * @return lista de sementes
     */
    private List<Seed> parseSeedsXML() {
        List<Seed> s = new ArrayList<Seed>();
        try {
            //Initialize a list

            Seed seed = null;

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(ENDERECO_SEEDS));
            document.getDocumentElement().normalize();
            NodeList nList = document.getElementsByTagName("seed");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node node = nList.item(temp);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) node;
                    //Create Object
                    seed = new Seed(eElement.getElementsByTagName("url").item(0).getTextContent(), eElement.getElementsByTagName("visited").item(0).getTextContent());
                    //Add to list
                    s.add(seed);
                }
            }

        } catch (ParserConfigurationException ex) {
            Logger.getLogger(ParseXML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(ParseXML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ParseXML.class.getName()).log(Level.SEVERE, null, ex);
        }
        return s;
    }

    /**
     * 
     * @param iterator de centroides a serem gravados no XML
     */
    void saveCentroidsXML(Iterator<Centroid> iterator) {
        OutputStream streamOut = null;
        try {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            //root elements
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("centroides");
            doc.appendChild(rootElement);

            Iterator<Centroid> i = iterator;
            while (i.hasNext()) {
                Centroid cent = i.next();
                Element centroide = doc.createElement("centroide");

                Element termo_centroide = doc.createElement("termo");
                termo_centroide.setTextContent(cent.getData());
                centroide.appendChild(termo_centroide);

                Element peso = doc.createElement("peso");
                peso.setTextContent(Integer.toString(cent.getWeight()));
                centroide.appendChild(peso);

                Element quantidade = doc.createElement("quantidade");
                quantidade.setTextContent(Integer.toString(cent.getFrequency()));
                centroide.appendChild(quantidade);

                rootElement.appendChild(centroide);

            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);

            StringWriter stringWriter = new StringWriter();
            StreamResult result = new StreamResult(stringWriter);
            // Output to console for testing
            // StreamResult result = new StreamResult(System.out);
            transformer.transform(source, result);
            System.out.println(stringWriter.toString());

            File xmlMap = new File(ENDERECO_CENTROIDES);
            streamOut = new FileOutputStream(xmlMap);

            XMLSerializer serializer = new XMLSerializer(streamOut, new OutputFormat(doc, "utf-8", true));
            serializer.serialize(doc);

        } catch (ParserConfigurationException ex) {
            Logger.getLogger(ParseXML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(ParseXML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(ParseXML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ParseXML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ParseXML.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
