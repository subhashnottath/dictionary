package sn.fun.dictionary.data;

import lombok.Data;
import sn.fun.dictionary.repo.Meaning;

import java.util.List;

@Data
public class Response {
    private String word = "Not found";
    private List<Meaning> meanings;
    private List<String> suggestions;
}
