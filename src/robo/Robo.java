/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robo;

import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aluno
 */
public class Robo {
    
    private static ParseXML xml;
    private static ParserHTML web;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        web = new ParserHTML();
        xml = new ParseXML();
        List<Seed> seeds = xml.getSeeds();
        for (int k = 0; k < seeds.size(); k++) {
            Seed get = seeds.get(k);
            if (get.VISITED.equals("")) {
                System.out.println(get.URL);
                try {                
                    xml.gravarCentroidesXML(web.visitar(get));
                } catch (IOException ex) {
                    Logger.getLogger(Robo.class.getName()).log(Level.SEVERE, null, ex);
                }
                xml.addSeed(web.getLinks());
            xml.gravarXML();
            }
        }
        //walkingIn(Jsoup.connect(getNextLink()).get());
    }
}
