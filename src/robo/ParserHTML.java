/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Fabugo
 */
public class ParserHTML {

    private static final String ENDERECO_TXT_PT = "src\\robo\\stoplist.txt";
    private static final String ENDERECO_TXT_EN = "src\\robo\\stoplistEn.txt";
    private static final int PESO_TITULO = 10,
            PESO_H1 = 7,
            PESO_H2 = 6,
            PESO_H3 = 5,
            PESO_H4 = 4,
            PESO_H5 = 4,
            PESO_H6 = 4,
            PESO_A = 5,
            PESO_BIG = 3,
            PESO_B = 3,
            PESO_EM = 3,
            PESO_I = 3,
            PESO_U = 3,
            PESO_STRONG = 3,
            PESO_STRIKE = 3,
            PESO_CENTER = 3,
            PESO_SMALL = 2,
            PESO_SUB = 2,
            PESO_SUP = 2,
            PESO_FONT = 2,
            PESO_ADDRESS = 2,
            PESO_META = 2,
            PESO_OUTROS = 1;
    private static List<Centroide> centroides = new ArrayList<Centroide>();
    private org.jsoup.nodes.Document site;

    public Iterator<Centroide> visitar(Seed next) throws IOException {

        System.out.println("Iniciando download… " + next.URL);

        site = Jsoup.connect(next.URL).get();

        // marca visitado
        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println(formatador.format(new Date()));
        next.VISITED = formatador.format(new Date());

        // pega titulo
        String titulo = site.title();
        String[] titulosep = titulo.split(" ");
        for (int j = 0; j < titulosep.length; j++) {
            // verifica se contem nos arquivos txt's
            boolean vTermo = verificaTermo(titulosep[j]);
            if (!vTermo) {
                System.out.println("termo centroide titulo: " + titulosep[j]);
                // verifica se existe centroide com esse termo
                if (centroides.contains(new Centroide(titulosep[j], PESO_TITULO))) {
                    System.out.println("TEM");
                    int indexOf = centroides.indexOf(new Centroide(titulosep[j], PESO_TITULO));
                    Centroide get = centroides.get(indexOf);
                    get.setPeso(get.getPeso() + PESO_TITULO);
                    get.setQtd();
                } else {
                    System.out.println("NÃO TEM");
                    centroides.add(new Centroide(titulosep[j], PESO_TITULO));
                }
            }
        }

        // pega elementos tag meta
        Elements metaTags = site.getElementsByTag("meta");
        for (org.jsoup.nodes.Element metaTag : metaTags) {
            String[] metasep = metaTag.attr("content").split(" ");
            for (int w = 0; w < metasep.length; w++) {
                // retira virgula
                metasep[w] = metasep[w].replace(",", "");
                boolean vTermo = verificaTermo(metasep[w]);
                if (!vTermo) {
                    System.out.println("termo centroide meta: " + metasep[w]);
                    // verifica se existe centroide com esse termo
                    if (centroides.contains(new Centroide(metasep[w], PESO_META))) {
                        int indexOf = centroides.indexOf(new Centroide(metasep[w], PESO_META));
                        Centroide get = centroides.get(indexOf);
                        get.setPeso(get.getPeso() + PESO_META);
                        get.setQtd();
                    } else {
                        centroides.add(new Centroide(metasep[w], PESO_META));
                    }
                }
            }
        }

        //pega cabeçalhos
        //Elements cab1s = site.select("h1");
        Elements cab1s = site.body().select("*");
        for (org.jsoup.nodes.Element cab1 : cab1s) {
            //System.out.println("\nlink : " + link.attr("href")); // link
            System.out.println("TAG: " + cab1.tagName());
            System.out.println("TEXTO: " + cab1.ownText()); // texto

            String[] tagtexto = cab1.ownText().split(" ");
            for (int h = 0; h < tagtexto.length; h++) {

                // verifica se contem nos arquivos txt's
                boolean vTermo = verificaTermo(tagtexto[h]);
                if (!vTermo) {
                    System.out.println("termo centroide body: " + tagtexto[h]);
                    // verifica se existe centroide com esse termo
                    if (centroides.contains(new Centroide(tagtexto[h], PESO_TITULO))) {
                        System.out.println("TEM");
                        int indexOf = centroides.indexOf(new Centroide(tagtexto[h], PESO_TITULO));
                        Centroide get = centroides.get(indexOf);

                        if (cab1.tagName().equalsIgnoreCase("h1")) {
                            get.setPeso(get.getPeso() + PESO_H1);
                        } else if (cab1.tagName().equalsIgnoreCase("h2")) {
                            get.setPeso(get.getPeso() + PESO_H2);
                        } else if (cab1.tagName().equalsIgnoreCase("h3")) {
                            get.setPeso(get.getPeso() + PESO_H3);
                        } else if (cab1.tagName().equalsIgnoreCase("h4")) {
                            get.setPeso(get.getPeso() + PESO_H4);
                        } else if (cab1.tagName().equalsIgnoreCase("h5")) {
                            get.setPeso(get.getPeso() + PESO_H5);
                        } else if (cab1.tagName().equalsIgnoreCase("h6")) {
                            get.setPeso(get.getPeso() + PESO_H6);
                        } else if (cab1.tagName().equalsIgnoreCase("a")) {
                            get.setPeso(get.getPeso() + PESO_A);
                        } else if (cab1.tagName().equalsIgnoreCase("big")) {
                            get.setPeso(get.getPeso() + PESO_BIG);
                        } else if (cab1.tagName().equalsIgnoreCase("b")) {
                            get.setPeso(get.getPeso() + PESO_B);
                        } else if (cab1.tagName().equalsIgnoreCase("em")) {
                            get.setPeso(get.getPeso() + PESO_EM);
                        } else if (cab1.tagName().equalsIgnoreCase("i")) {
                            get.setPeso(get.getPeso() + PESO_I);
                        } else if (cab1.tagName().equalsIgnoreCase("u")) {
                            get.setPeso(get.getPeso() + PESO_U);
                        } else if (cab1.tagName().equalsIgnoreCase("strong")) {
                            get.setPeso(get.getPeso() + PESO_STRONG);
                        } else if (cab1.tagName().equalsIgnoreCase("strike")) {
                            get.setPeso(get.getPeso() + PESO_STRIKE);
                        } else if (cab1.tagName().equalsIgnoreCase("center")) {
                            get.setPeso(get.getPeso() + PESO_CENTER);
                        } else if (cab1.tagName().equalsIgnoreCase("small")) {
                            get.setPeso(get.getPeso() + PESO_SMALL);
                        } else if (cab1.tagName().equalsIgnoreCase("sub")) {
                            get.setPeso(get.getPeso() + PESO_SUB);
                        } else if (cab1.tagName().equalsIgnoreCase("sup")) {
                            get.setPeso(get.getPeso() + PESO_SUP);
                        } else if (cab1.tagName().equalsIgnoreCase("font")) {
                            get.setPeso(get.getPeso() + PESO_FONT);
                        } else if (cab1.tagName().equalsIgnoreCase("address")) {
                            get.setPeso(get.getPeso() + PESO_ADDRESS);
                        } else {
                            get.setPeso(get.getPeso() + PESO_OUTROS);
                        }
                        get.setQtd();
                    } else {
                        System.out.println("NÃO TEM");
                        int peso_parcial = 0;
                        if (cab1.tagName().equalsIgnoreCase("h1")) {
                            peso_parcial = PESO_H1;
                        } else if (cab1.tagName().equalsIgnoreCase("h2")) {
                            peso_parcial = PESO_H2;
                        } else if (cab1.tagName().equalsIgnoreCase("h3")) {
                            peso_parcial = PESO_H3;
                        } else if (cab1.tagName().equalsIgnoreCase("h4")) {
                            peso_parcial = PESO_H4;
                        } else if (cab1.tagName().equalsIgnoreCase("h5")) {
                            peso_parcial = PESO_H5;
                        } else if (cab1.tagName().equalsIgnoreCase("h6")) {
                            peso_parcial = PESO_H6;
                        } else if (cab1.tagName().equalsIgnoreCase("a")) {
                            peso_parcial = PESO_A;
                        } else if (cab1.tagName().equalsIgnoreCase("big")) {
                            peso_parcial = PESO_BIG;
                        } else if (cab1.tagName().equalsIgnoreCase("b")) {
                            peso_parcial = PESO_B;
                        } else if (cab1.tagName().equalsIgnoreCase("em")) {
                            peso_parcial = PESO_EM;
                        } else if (cab1.tagName().equalsIgnoreCase("i")) {
                            peso_parcial = PESO_I;
                        } else if (cab1.tagName().equalsIgnoreCase("u")) {
                            peso_parcial = PESO_U;
                        } else if (cab1.tagName().equalsIgnoreCase("strong")) {
                            peso_parcial = PESO_STRONG;
                        } else if (cab1.tagName().equalsIgnoreCase("strike")) {
                            peso_parcial = PESO_STRIKE;
                        } else if (cab1.tagName().equalsIgnoreCase("center")) {
                            peso_parcial = PESO_CENTER;
                        } else if (cab1.tagName().equalsIgnoreCase("small")) {
                            peso_parcial = PESO_SMALL;
                        } else if (cab1.tagName().equalsIgnoreCase("sub")) {
                            peso_parcial = PESO_SUB;
                        } else if (cab1.tagName().equalsIgnoreCase("sup")) {
                            peso_parcial = PESO_SUP;
                        } else if (cab1.tagName().equalsIgnoreCase("font")) {
                            peso_parcial = PESO_FONT;
                        } else if (cab1.tagName().equalsIgnoreCase("address")) {
                            peso_parcial = PESO_ADDRESS;
                        } else {
                            peso_parcial = PESO_OUTROS;
                        }
                        centroides.add(new Centroide(tagtexto[h], peso_parcial));
                    }
                }
            }

        }

        /*
       System.out.println("- - CENTROIDES - -");
       for(int p=0;p<centroides.size();p++){
           System.out.println("TERMO "+p+": "+centroides.get(p).getTermo()+"   -   PESO: "+centroides.get(p).getPeso()+"   -   QUANTIDADE: "+centroides.get(p).getQtd());
       }
         */
        return centroides.iterator();

    }

    public Elements getLinks() {
        // pega todos os links
        return site.select("a[href]");
    }

    private boolean verificaTermo(String titulo) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(ENDERECO_TXT_PT)));
            BufferedReader reader2 = new BufferedReader(new FileReader(new File(ENDERECO_TXT_EN)));
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (linha.contains(titulo)) {
                    return true;
                }
            }
            while ((linha = reader2.readLine()) != null) {
                if (linha.contains(titulo)) {
                    return true;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Robo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
