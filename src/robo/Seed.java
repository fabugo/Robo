package robo;

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
