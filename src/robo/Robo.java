/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robo;


/*
 Controlador
*/
public class Robo {
    public static void main(String[] args) throws Exception {
        WebWalker walker = new WebWalker();
        XMLer xmler = new XMLer();
        while(true){
            walker.walkIn(xmler.getNextLink());
            xmler.writeCentroids(walker.getCents());
            xmler.writeSeeds(walker.getLinks());
        }
    }
}
