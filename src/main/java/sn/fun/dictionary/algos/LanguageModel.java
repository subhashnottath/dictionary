package sn.fun.dictionary.algos;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class LanguageModel {
    private final Map<String, Integer> wordCounts;
    private final double nonExistentWordCount = 0.01;

    public LanguageModel(@Value("${language.model.dataFile}") String languageModelDataFile) throws IOException {
        wordCounts = new HashMap<>();
        Path path = Paths.get(languageModelDataFile);
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line = reader.readLine();
            while (line != null) {
                for (String word : line.split("\\s+")) {
                    word = word.toLowerCase();
                    wordCounts.put(word, wordCounts.getOrDefault(word, 0) + 1);
                }
                line = reader.readLine();
            }
        } catch (IOException e) {
            log.error("Error creating language model", e);
            throw e;
        }
    }

    public double probability(String word) {
        Integer wordCount = wordCounts.get(word);
        if (wordCount != null) {
            return wordCount / (double) wordCounts.size();
        } else {
            return nonExistentWordCount / wordCounts.size();
        }
    }
}
