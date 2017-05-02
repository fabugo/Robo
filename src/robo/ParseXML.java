/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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

/**
 *
 * @author NOTEBOOK
 */
public class ParseXML {

    private static String ENDERECO_SEEDS = "src\\robo\\seeds.xml";
    private static String ENDERECO_CENTROIDES = "src\\robo\\centroides.xml";
    private List<Seed> seeds;

    public List<Seed> getSeeds() {
        return seeds;
    }

    public void addSeed(Elements links) {
        
        for (org.jsoup.nodes.Element link : links) {
            //System.out.println("\nlink : " + link.attr("href")); // link
            //System.out.println("text : " + link.text()); // texto PESO DE TEXTO DE LINK
            if (!seeds.contains(link)) {
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
                seeds.add(link.);
            }

            System.out.println("QUANTIDADE DE SEMENTES: " + seeds.size());

            if (seeds.size() % 500 == 0) {
                this.gravarXML();
            }
        }

    }

    public ParseXML() {
        seeds = parseSeedsXML();
    }

    public void gravarXML() {

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

                Element url_semente = doc.createElement("url");
                url_semente.setTextContent(sem.URL);
                sement.appendChild(url_semente);

                Element dt_visita = doc.createElement("visited");
                dt_visita.setTextContent(sem.VISITED);
                sement.appendChild(dt_visita);

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

    void gravarCentroidesXML(Iterator<Centroide> iterator) {
        OutputStream streamOut = null;
        try {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            //root elements
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("centroides");
            doc.appendChild(rootElement);

            Iterator<Centroide> i = iterator;
            while (i.hasNext()) {
                Centroide cent = i.next();
                Element centroide = doc.createElement("centroide");

                Element termo_centroide = doc.createElement("termo");
                termo_centroide.setTextContent(cent.getTermo());
                centroide.appendChild(termo_centroide);

                Element peso = doc.createElement("peso");
                peso.setTextContent(Integer.toString(cent.getPeso()));
                centroide.appendChild(peso);

                Element quantidade = doc.createElement("quantidade");
                quantidade.setTextContent(Integer.toString(cent.getQtd()));
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
