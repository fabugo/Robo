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
import org.jsoup.select.Elements;

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
    private static List<Centroid> centroids = new ArrayList<Centroid>();
    private org.jsoup.nodes.Document page;

    /**
     * 
     * @param next link a ser visitado
     * @return lista de centroides construidas
     */
    public Iterator<Centroid> visit(Seed next){

        System.out.println("Iniciando download… " + next.URL);

        try {
            page = Jsoup.connect(next.URL).get();
        } catch (IOException ex) {
            Logger.getLogger(ParserHTML.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

        // marca visitado
        SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println(formater.format(new Date()));
        next.VISITED = formater.format(new Date());

        // pega titulo
        String titulo = page.title();
        String[] titlesep = titulo.split(" ");
        for (int j = 0; j < titlesep.length; j++) {
            // verifica se contem nos arquivos txt's
            boolean vTermo = verifyTerm(titlesep[j]);
            if (!vTermo) {
                System.out.println("termo centroide titulo: " + titlesep[j]);
                // verifica se existe centroide com esse termo
                if (centroids.contains(new Centroid(titlesep[j], PESO_TITULO))) {
                    System.out.println("TEM");
                    int indexOf = centroids.indexOf(new Centroid(titlesep[j], PESO_TITULO));
                    Centroid get = centroids.get(indexOf);
                    get.setWeight(get.getWeight() + PESO_TITULO);
                    get.setQtd();
                } else {
                    System.out.println("NÃO TEM");
                    centroids.add(new Centroid(titlesep[j], PESO_TITULO));
                }
            }
        }

        // pega elementos tag meta
        Elements metaTags = page.getElementsByTag("meta");
        for (org.jsoup.nodes.Element metaTag : metaTags) {
            String[] metasep = metaTag.attr("content").split(" ");
            for (int w = 0; w < metasep.length; w++) {
                // retira virgula
                metasep[w] = metasep[w].replace(",", "");
                boolean vTermo = verifyTerm(metasep[w]);
                if (!vTermo) {
                    System.out.println("termo centroide meta: " + metasep[w]);
                    // verifica se existe centroide com esse termo
                    if (centroids.contains(new Centroid(metasep[w], PESO_META))) {
                        int indexOf = centroids.indexOf(new Centroid(metasep[w], PESO_META));
                        Centroid get = centroids.get(indexOf);
                        get.setWeight(get.getWeight() + PESO_META);
                        get.setQtd();
                    } else {
                        centroids.add(new Centroid(metasep[w], PESO_META));
                    }
                }
            }
        }

        //pega cabeçalhos
        //Elements cab1s = site.select("h1");
        Elements cab1s = page.body().select("*");
        for (org.jsoup.nodes.Element cab1 : cab1s) {
            //System.out.println("\nlink : " + link.attr("href")); // link
            System.out.println("TAG: " + cab1.tagName());
            System.out.println("TEXTO: " + cab1.ownText()); // texto

            String[] tagtexto = cab1.ownText().split(" ");
            for (int h = 0; h < tagtexto.length; h++) {

                // verifica se contem nos arquivos txt's
                boolean vTermo = verifyTerm(tagtexto[h]);
                if (!vTermo) {
                    System.out.println("termo centroide body: " + tagtexto[h]);
                    // verifica se existe centroide com esse termo
                    if (centroids.contains(new Centroid(tagtexto[h], PESO_TITULO))) {
                        System.out.println("TEM");
                        int indexOf = centroids.indexOf(new Centroid(tagtexto[h], PESO_TITULO));
                        Centroid get = centroids.get(indexOf);

                        if (cab1.tagName().equalsIgnoreCase("h1")) {
                            get.setWeight(get.getWeight() + PESO_H1);
                        } else if (cab1.tagName().equalsIgnoreCase("h2")) {
                            get.setWeight(get.getWeight() + PESO_H2);
                        } else if (cab1.tagName().equalsIgnoreCase("h3")) {
                            get.setWeight(get.getWeight() + PESO_H3);
                        } else if (cab1.tagName().equalsIgnoreCase("h4")) {
                            get.setWeight(get.getWeight() + PESO_H4);
                        } else if (cab1.tagName().equalsIgnoreCase("h5")) {
                            get.setWeight(get.getWeight() + PESO_H5);
                        } else if (cab1.tagName().equalsIgnoreCase("h6")) {
                            get.setWeight(get.getWeight() + PESO_H6);
                        } else if (cab1.tagName().equalsIgnoreCase("a")) {
                            get.setWeight(get.getWeight() + PESO_A);
                        } else if (cab1.tagName().equalsIgnoreCase("big")) {
                            get.setWeight(get.getWeight() + PESO_BIG);
                        } else if (cab1.tagName().equalsIgnoreCase("b")) {
                            get.setWeight(get.getWeight() + PESO_B);
                        } else if (cab1.tagName().equalsIgnoreCase("em")) {
                            get.setWeight(get.getWeight() + PESO_EM);
                        } else if (cab1.tagName().equalsIgnoreCase("i")) {
                            get.setWeight(get.getWeight() + PESO_I);
                        } else if (cab1.tagName().equalsIgnoreCase("u")) {
                            get.setWeight(get.getWeight() + PESO_U);
                        } else if (cab1.tagName().equalsIgnoreCase("strong")) {
                            get.setWeight(get.getWeight() + PESO_STRONG);
                        } else if (cab1.tagName().equalsIgnoreCase("strike")) {
                            get.setWeight(get.getWeight() + PESO_STRIKE);
                        } else if (cab1.tagName().equalsIgnoreCase("center")) {
                            get.setWeight(get.getWeight() + PESO_CENTER);
                        } else if (cab1.tagName().equalsIgnoreCase("small")) {
                            get.setWeight(get.getWeight() + PESO_SMALL);
                        } else if (cab1.tagName().equalsIgnoreCase("sub")) {
                            get.setWeight(get.getWeight() + PESO_SUB);
                        } else if (cab1.tagName().equalsIgnoreCase("sup")) {
                            get.setWeight(get.getWeight() + PESO_SUP);
                        } else if (cab1.tagName().equalsIgnoreCase("font")) {
                            get.setWeight(get.getWeight() + PESO_FONT);
                        } else if (cab1.tagName().equalsIgnoreCase("address")) {
                            get.setWeight(get.getWeight() + PESO_ADDRESS);
                        } else {
                            get.setWeight(get.getWeight() + PESO_OUTROS);
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
                        centroids.add(new Centroid(tagtexto[h], peso_parcial));
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
        return centroids.iterator();

    }

    /**
     * 
     * @return lista de links da pagina visitada
     */
    public Elements getLinks() {
        // pega todos os links
        return page.select("a[href]");
    }

    /**
     * 
     * @param term a ser verificado sua existencia
     * @return verdadeiro ou falso
     */
    private boolean verifyTerm(String term) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(ENDERECO_TXT_PT)));
            BufferedReader reader2 = new BufferedReader(new FileReader(new File(ENDERECO_TXT_EN)));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(term)) {
                    return true;
                }
            }
            while ((line = reader2.readLine()) != null) {
                if (line.contains(term)) {
                    return true;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Robo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
