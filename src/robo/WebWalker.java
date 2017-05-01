/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robo;

import java.net.URL;
import java.util.Iterator;
import java.util.LinkedList;

/*
    Caminha pela web
*/
public class WebWalker {
    private LinkedList<Centroid> cents;
    private LinkedList<URL> links;
    
    public WebWalker() {
        
    }
    
    /*
    Caminha no link e constroi as centroids e novos links
    */
    public boolean walkIn(URL link){
        
        return false;
    }
    
    /*
    Retorna o iterador da lista de centroids capturadas na ultima caminhada
    */
    public Iterator<Centroid> getCents() {
        return cents.iterator();
    }
    
    /*
    Retorna o iterador da lista de links capturadas na ultima caminhada
    */
    public Iterator<URL> getLinks() {
        return links.iterator();
    }
    
    
}
