package de.codecentric.totiblogpost.catfact

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

@Service
class CatFactService(builder: WebClient.Builder) {
    @Value("\${catfact.url}")
    private lateinit var  uri : String
    private val webClient = builder.build();

    fun randomCatFact(): CatFact =
        webClient.get()
            .uri(uri)
            .retrieve()
            .bodyToMono(CatFact::class.java)
            .block() ?: throw CatFactNotAvailableException()

    fun getCatFact(count: Int): List<CatFact> =
        IntRange(0,count).map { randomCatFact() }.toList()
}
