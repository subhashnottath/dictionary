package sn.fun.dictionary.algos;

import lombok.Data;

@Data
public class Candidate implements Comparable<Candidate>{
    private final String word;
    private double error;
    private double probability;

    public Candidate(String word, double error) {
        this.word = word;
        this.error = error;
    }

    @Override
    public int compareTo(Candidate o) {
        return Double.compare(o.probability, probability);
    }
}