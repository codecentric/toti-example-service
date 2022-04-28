package de.codecentric.totiblogpost.test_fixtures

import de.codecentric.totiblogpost.extensions.WebTestClientExtention
import org.springframework.http.MediaType

class BlogPostTestFixture(private val rest: WebTestClientExtention) {
    fun givenSomeBlogPost(id: Long, title: String, content: String)  =
        rest.webTestClient.put().uri("/blogposts/$id").bodyValue(
            """
                {
                   "title": "$title",
                   "content": "$content"
                }
            """.trimIndent()
        )
            .headers { headers -> headers.contentType = MediaType.APPLICATION_JSON }
            .exchange()
            .expectStatus().is2xxSuccessful

}
