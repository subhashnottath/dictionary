package sn.fun.dictionary.repo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Meaning {
    private final String def;
    @JsonProperty("speech_part")
    private final String speechPart;
    private final String[] synonyms;
}
