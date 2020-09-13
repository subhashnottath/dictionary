package sn.fun.dictionary.repo;

import lombok.Data;

import java.util.List;

@Data
public class Meanings {
    private final String word;
    private final List<Meaning> meanings;
}
