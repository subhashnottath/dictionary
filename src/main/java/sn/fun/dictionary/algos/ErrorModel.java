package sn.fun.dictionary.algos;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import sn.fun.dictionary.repo.DictionaryRepo;

import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class ErrorModel {
    private static final double edit1Prob = 0.9999;
    private static final double edit2Prob = 1 - edit1Prob;
    private final DictionaryRepo dictionaryRepo;

    private Set<String> edit1(String word) {
        Set<String> res = new HashSet<>();
        // insert
        for (int i = 0; i <= word.length(); i++) {
            for (char c = 'a'; c <= 'z'; c++) {
                String newWord = word.substring(0, i) + c + word.substring(i);
                if (dictionaryRepo.isPresent(newWord)) {
                    res.add(newWord);
                }
            }
        }
        // delete
        for (int i = 0; i < word.length(); i++) {
            String newWord = word.substring(0, i) + word.substring(i + 1);
            if (dictionaryRepo.isPresent(newWord)) {
                res.add(newWord);
            }
        }
        // replace
        for (int i = 0; i < word.length(); i++) {
            char cur = word.charAt(i);
            for (char c = 'a'; c <= 'z'; c++) {
                if (c != cur) {
                    String newWord = word.substring(0, i) + c + word.substring(i + 1);
                    if (dictionaryRepo.isPresent(newWord)) {
                        res.add(newWord);
                    }
                }
            }
        }
        // transpose
        for (int i = 0; i < word.length() - 1; i++) {
            char cur = word.charAt(i);
            char next = word.charAt(i+1);
            if (cur != next) {
                String newWord = word.substring(0, i) + next + cur + word.substring(i + 2);
                if (dictionaryRepo.isPresent(newWord)) {
                    res.add(newWord);
                }
            }
        }
        return res;
    }

    private Set<String> edit2(Set<String> edit1Words) {
        Set<String> res = new HashSet<>();
        for (String w : edit1Words) {
            res.addAll(edit1(w));
        }
        return res;
    }

    public List<Candidate> candidates(String word) {
        Set<String> edit1 = edit1(word);
        Set<String> edit2 = edit2(edit1);
        Map<String, Candidate> result = new HashMap<>();
        for (String w : edit1) {
            if (!result.containsKey(w)) {
                result.put(w, new Candidate(w, edit1Prob));
            }
        }
        for (String w : edit2) {
            if (!result.containsKey(w)) {
                result.put(w, new Candidate(w, edit2Prob));
            }
        }
        return new ArrayList<>(result.values());
    }
}
