/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robo;


/**
 *
 * @author Fabugo
 */
class Centroid {
    private String word;
    private int weight;

    public Centroid(String word) {
        this.word = word;
        this.weight = 0;
    }
    
    public String getWord() {
        return word;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = this.weight + weight;
    }
    
}
