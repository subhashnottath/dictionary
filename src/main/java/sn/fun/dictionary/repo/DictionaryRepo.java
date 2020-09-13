package sn.fun.dictionary.repo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class DictionaryRepo {
    private final Map<String, Meanings> dict = new HashMap<>();

    public DictionaryRepo(@Value("${dictionary.path}") String path, ObjectMapper mapper) throws IOException {
        Path folder = Paths.get(path);
        if (!Files.exists(folder)) {
            log.error("Invalid dictionary base path, exiting {}", path);
            throw new IOException("Invalid dictionary base path");
        }
        Files.walk(folder)
                .filter(Files::isRegularFile)
                .forEach((filePath) -> {
                            try {
                                String content = Files.readString(filePath, StandardCharsets.UTF_8).toLowerCase();
                                Map<String, Meanings> letterDict = mapper.readValue(content, new TypeReference<>() {});
                                dict.putAll(letterDict);
                                log.debug("Added {} with size {} to dictionary (current size is {})", filePath,
                                        letterDict.size(), dict.size());
                            } catch (IOException e) {
                                log.error("Failed to read {}", filePath, e);
                            }
                        }
                );
    }

    public Meanings lookup(String word) {
        return dict.get(word);
    }

    public boolean isPresent(String word) {
        return dict.containsKey(word);
    }
}
