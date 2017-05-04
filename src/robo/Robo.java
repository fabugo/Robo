/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robo;

import java.util.*;

public class Robo {
    
    private static ParseXML xml;
    private static ParserHTML web;

    public static void main(String[] args) {
        web = new ParserHTML();
        xml = new ParseXML();
        List<Seed> seeds = xml.getSeeds();
        for (int k = 0; k < seeds.size(); k++) {
            Seed get = seeds.get(k);
            Iterator i = web.visit(get);
            if (get.VISITED.equals("") & i!=null) {
                System.out.println(get.URL);
                xml.saveCentroidsXML(i);
                xml.addSeed(web.getLinks());
            xml.saveXML();
            }
        }
    }
}
