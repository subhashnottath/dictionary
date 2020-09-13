package sn.fun.dictionary.algos;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class WordSuggester {
    private final LanguageModel languageModel;
    private final ErrorModel errorModel;
    private static final int maxSuggestions = 6;

    @Data
    static class WordProb implements Comparable<WordProb>{
        private final String word;
        private final double probability;

        @Override
        public int compareTo(WordProb o) {
            return Double.compare(o.probability, probability);
        }
    }

    public List<String> suggestions(String word) {
        List<String> result = new ArrayList<>();
        List<Candidate> candidates = errorModel.candidates(word);
        for (Candidate candidate : candidates) {
            double probCandidate = languageModel.probability(candidate.getWord());
            candidate.setProbability(probCandidate * candidate.getError());
        }
        Collections.sort(candidates);
        int resultCount = Math.min(maxSuggestions, candidates.size());
        for (int i = 0; i < resultCount; i++) {
            result.add(candidates.get(i).getWord());
        }
        return result;
    }
}
