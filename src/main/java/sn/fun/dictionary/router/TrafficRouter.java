package sn.fun.dictionary.router;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import sn.fun.dictionary.handler.SearchHandler;

@Slf4j
@Configuration
public class TrafficRouter {
    @Bean
    public RouterFunction<ServerResponse> route(SearchHandler searchHandler) {
        return RouterFunctions.route(RequestPredicates.POST("/lookup")
                .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), searchHandler::lookup);
    }
}
