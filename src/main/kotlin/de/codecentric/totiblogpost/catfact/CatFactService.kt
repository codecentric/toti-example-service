package de.codecentric.totiblogpost.catfact

import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

@Service
class CatFactService(private val builder: WebClient.Builder) {
    private val uri = "https://catfact.ninja/fact"
    private val webClient = builder.build();

    fun randomCatFact(): CatFact? =
        webClient.get()
            .uri(uri)
            .retrieve()
            .bodyToMono(CatFact::class.java)
            .block() ?: throw CatFactNotAvailableException()
}