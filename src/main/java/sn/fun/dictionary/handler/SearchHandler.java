package sn.fun.dictionary.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import sn.fun.dictionary.algos.WordSuggester;
import sn.fun.dictionary.data.InputData;
import sn.fun.dictionary.data.Response;
import sn.fun.dictionary.repo.DictionaryRepo;
import sn.fun.dictionary.repo.Meanings;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class SearchHandler {
    private final DictionaryRepo dictionaryRepo;
    private final WordSuggester wordSuggester;

    public Mono<ServerResponse> lookup(ServerRequest request) {
        Mono<InputData> reqMono = request.bodyToMono(InputData.class);
        return reqMono.flatMap(inputData -> {
            log.debug("InputData {}", inputData);
            String word = inputData.getWord().trim().toLowerCase();
            Response response = new Response();
            Meanings meanings = dictionaryRepo.lookup(word);
            if (meanings != null) {
                response.setWord(meanings.getWord());
                response.setMeanings(meanings.getMeanings());
            }
            List<String> suggestions = wordSuggester.suggestions(word);
            response.setSuggestions(suggestions);
            return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(response));
        });
    }
}
