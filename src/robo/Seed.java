/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robo;

/**
 *
 * @author NOTEBOOK
 */
class Seed {
    
    String URL;
    String VISITED;
    
    public Seed(String attr, String vis) {
       URL = attr; VISITED = vis;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Seed)) {
            return false;
        }
        final Seed other = (Seed) obj;
        return this.URL.equals(other.URL);
    }
    
}
