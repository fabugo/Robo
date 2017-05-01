/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robo;

import java.io.FileInputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

/**
 *
 * @author Fabugo
 */
public class XMLer {
    
    private final XMLStreamReader parserCents;
    private final XMLStreamReader parserSeeds;

    public XMLer() throws Exception {
        this.parserCents = XMLInputFactory.newInstance().
                createXMLStreamReader(new FileInputStream("src/robo/centroide.xml"));
        
        this.parserSeeds = XMLInputFactory.newInstance().
                createXMLStreamReader(new FileInputStream("src/robo/seeds.xml"));
    }
    
    
    public void readSeed(){
        
    }
    
    /*
    Retorna o porximo link ainda nao visitado das seeds
    */
    public URL getNextLink(){
        try {
            return new URL("www.uefs.br");
        } catch (MalformedURLException ex) {
            Logger.getLogger(XMLer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    /*
    Escreve as centroides no xml
    */
    public void writeCentroids(Iterator<Centroid> cents){
        
    }
    
    /*
    Escreve os novos links achados em seeds xml
    deve verificar se já existe
    atualiza seeds ja visitados
    */
    public void writeSeeds(Iterator<URL> links) {
        
    }
}
