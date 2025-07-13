package com.server.markmyreads.domain.component;

import com.server.markmyreads.domain.dto.ParsedClippingDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class TestRunner implements CommandLineRunner {

    private final ClippingBlockParser parser; // Injeta o seu componente

    @Override
    public void run(String... args) throws Exception {
        log.info("--- INICIANDO TESTE DO PARSER ---");

        String blocoDeTeste =
                """
                        Dopamina: a molécula do desejo (Daniel Z. Lieberman;Michael E. Long)
                        - Seu destaque ou posição 253-254 | Adicionado: segunda-feira, 23 de outubro de 2023 21:10:00
                                        
                        Mas não foram o café e o croissant que mudaram; foram suas expectativas.
                """;

        ParsedClippingDto resultado = parser.parse(blocoDeTeste);

        if (resultado != null) {
            log.info("Parse bem-sucedido!");
            log.info("Título: {}", resultado.title());
            log.info("Autor: {}", resultado.author());
            log.info("Conteúdo: {}", resultado.content());
            log.info("Data: {}", resultado.date());
        } else {
            log.error("Falha no parse do bloco de teste.");
        }
    }

}
