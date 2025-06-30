//package com.server.markmyreads.service.impl;
//
//import com.server.markmyreads.domain.dto.BookcoverDto;
//import com.server.markmyreads.domain.dto.ClippingsContext;
//import com.server.markmyreads.domain.dto.GoogleResponse;
//import com.server.markmyreads.domain.enumeration.NoteSortType;
//import com.server.markmyreads.domain.model.KindleNote;
//import com.server.markmyreads.service.BookcoverService;
//import com.server.markmyreads.service.ClippingsExtractorService;
//import com.server.markmyreads.service.KindleNoteProviderService;
//import jakarta.validation.constraints.NotNull;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatusCode;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.reactive.function.client.WebClient;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class BookcoverServiceImpl implements BookcoverService {
//
//    private final WebClient webClient;
//    private final ClippingsExtractorService extractor;
//    private final KindleNoteProviderService provider;
//
//    //["image-links"]["thumbnail"] -> coverUrl
//
//    @Override
//    public List<BookcoverDto> fetchBookcovers(@NotNull final MultipartFile file) {
//        // O argumento 'file' é ignorado por enquanto, apenas para o teste.
//
//        log.info("Iniciando requisição de teste para a API do Google Books...");
//
//        // 1. Faz a requisição GET para um livro específico como exemplo
//        Mono<GoogleResponse> responseMono = this.webClient
//                .get()
//                .uri("https://www.googleapis.com/books/v1/volumes/zyTCAlFPjgYC") // Exemplo de URI da API do Google
//                .retrieve() // Executa a requisição
//                .bodyToMono(GoogleResponse.class); // Mapeia o corpo da resposta para o seu DTO
//
//
//
//
//        // 2. Bloqueia a execução para obter o resultado (APENAS PARA TESTE)
//        // O método .block() transforma a chamada reativa (assíncrona) em síncrona.
//        GoogleResponse googleResponse = responseMono.block();
//
//        // 3. Imprime o resultado para você ver no console
//        if (googleResponse != null) {
//            log.info("Resposta recebida com sucesso!");
//            log.info("ID: {}", googleResponse.getId());
//            log.info("Self Link: {}", googleResponse.getSelfLink());
//        } else {
//            log.warn("Nenhuma resposta recebida do servidor.");
//        }
//
//        // 4. Retorna uma lista vazia para satisfazer o contrato da interface
//        // A lógica final deverá retornar uma List<BookcoverDto>
//        return Collections.emptyList();
//    }
//
//}

package com.server.markmyreads.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.markmyreads.domain.dto.BookcoverDto;
import com.server.markmyreads.domain.dto.GoogleResponse;
import com.server.markmyreads.service.BookcoverService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookcoverServiceImpl implements BookcoverService {

    private final WebClient webClient;
    private final ObjectMapper objectMapper; // Bean padrão do Spring, útil para converter objetos em JSON

    @Override
    public List<BookcoverDto> fetchBookcovers(@NotNull final MultipartFile file) {
        // Simulação: Aqui você leria o 'file' e extrairia os termos de busca.
        // Por agora, vamos usar uma lista fixa como exemplo.
        List<String> searchQueries = List.of("O Hobbit", "Duna", "Fundação");

        log.info("Iniciando buscas para {} termos...", searchQueries.size());

        // 1. Converte a lista de buscas em um Flux (um stream de dados reativo)
        List<GoogleResponse> results = Flux.fromIterable(searchQueries)
                // 2. Para cada termo de busca, faz uma chamada à API.
                // O flatMap é essencial para lidar com chamadas assíncronas em paralelo.
                .flatMap(query -> this.findBookByQuery(query))
                // 3. Coleta todos os resultados em uma lista.
                .collectList()
                // 4. Bloqueia para obter a lista final (APENAS PARA TESTE/DEPURAÇÃO)
                .block();

        // 5. Agora que temos a lista de resultados, usamos o seu 'for' para exibir no log.
        if (results != null && !results.isEmpty()) {
            log.info("--- Exibindo resultados individualmente ---");
            for (GoogleResponse response : results) {
                log.info("Busca encontrada -> ID: {}, Link: {}", response.getId(), response.getSelfLink());
            }

            log.info("--- Exibindo resultados em formato .json ---");
            try {
                // Usa o ObjectMapper para converter a lista de objetos em uma string JSON bonita
                String jsonResults = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(results);
                log.info(jsonResults);
            } catch (JsonProcessingException e) {
                log.error("Erro ao converter resultados para JSON", e);
            }
        } else {
            log.warn("Nenhum resultado encontrado para as buscas.");
        }

        return Collections.emptyList();
    }

    private Mono<GoogleResponse> findBookByQuery(String query) {
        return this.webClient
                .get()
                .uri("/volumes?q={query}&maxResults=1", query)
                .retrieve()
                .bodyToMono(String.class)
                .map(this::extractFirstItemAsGoogleResponse)
                .onErrorResume(error -> {
                    log.error("Falha ao buscar pelo termo '{}': {}", query, error.getMessage());
                    return Mono.empty();
                });
    }

    private GoogleResponse extractFirstItemAsGoogleResponse(String jsonBody) {
        try {
            // A resposta do Google vem dentro de um objeto com um campo "items" que é um array.
            // Aqui, pegamos o primeiro objeto desse array.
            return objectMapper.readTree(jsonBody).get("items").get(0).traverse(objectMapper).readValueAs(GoogleResponse.class);
        } catch (Exception e) {
            return null; // Retorna nulo se não conseguir fazer o parse
        }
    }
}