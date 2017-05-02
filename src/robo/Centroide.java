/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robo;

import java.text.Normalizer;

/**
 *
 * @author NOTEBOOK
 */
public class Centroide {
    
    private String termo;
    private int peso;
    private int qtd;

    public Centroide(String termo, int peso) {
        this.termo = termo;
        this.peso = peso;
        this.qtd = 1;
    }

    public String getTermo() {
        return termo;
    }

    public int getPeso() {
        return peso;
    }

    public int getQtd() {
        return qtd;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public void setQtd() {
        this.qtd++;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Centroide)) {
            return false;
        }
        final Centroide other = (Centroide) obj;
        boolean equalsIgnoreCase = Normalizer.normalize(this.termo, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "").equalsIgnoreCase(Normalizer.normalize(other.termo, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", ""));
        return equalsIgnoreCase;
        
    }
    
    
    
}
