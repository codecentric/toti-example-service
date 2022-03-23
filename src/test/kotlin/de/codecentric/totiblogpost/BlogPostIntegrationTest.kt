package de.codecentric.totiblogpost

import de.codecentric.totiblogpost.extensions.DynamoDbLocalExtension
import de.codecentric.totiblogpost.extensions.WebTestClientExtention
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.RegisterExtension
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(DynamoDbLocalExtension::class)
class BlogPostIntegrationTest {
    companion object {
        @JvmStatic
        @RegisterExtension
        val rest = WebTestClientExtention()
    }

    @Test
    internal fun `should work`() {
        val id = 1L
        rest.webTestClient.put()
            .uri { uriBuilder ->
                uriBuilder.path("/blogpost/{id}")
                    .build(id)
            }.bodyValue(
                """
                {
                   "id": $id,
                   "title": "some title",
                   "content": "the content"
                }
            """.trimIndent()
            )
            .headers { headers -> headers.contentType = MediaType.APPLICATION_JSON }
            .exchange()

        rest.webTestClient.get()
            .uri { uriBuilder ->
                uriBuilder
                    .path("/blogpost/{id}")
                    .build(id)
            }
            .exchange()
    }
}
