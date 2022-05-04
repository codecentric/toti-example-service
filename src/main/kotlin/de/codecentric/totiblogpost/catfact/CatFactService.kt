package de.codecentric.totiblogpost.catfact

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

@Service
class CatFactService(builder: WebClient.Builder) {
    @Value("\${catfact.url}")
    private lateinit var uri: String
    private val webClient = builder.build()

    fun randomCatFact(): CatFact =
        try {
            webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(CatFact::class.java)
                .block()
        } catch (ex: Exception) {
            throw CatFactNotAvailableException("CatFact Service is not available.")
        } ?: throw CatFactNotAvailableException("CatFact Service returned an empty response.")

    fun getCatFact(count: Int): List<CatFact> =
        IntRange(1, count).map { randomCatFact() }.toList()
}
