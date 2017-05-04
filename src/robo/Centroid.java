package robo;

import java.text.Normalizer;

public class Centroid {
    
    private String data;
    private int weight;
    private int frequency;

    public Centroid(String termo, int peso) {
        this.data = termo;
        this.weight = peso;
        this.frequency = 1;
    }

    public String getData() {
        return data;
    }

    public int getWeight() {
        return weight;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setQtd() {
        this.frequency++;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Centroid)) {
            return false;
        }
        final Centroid other = (Centroid) obj;
        boolean equalsIgnoreCase = Normalizer.normalize(this.data, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "").equalsIgnoreCase(Normalizer.normalize(other.data, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", ""));
        return equalsIgnoreCase;
        
    }
    
    
    
}
