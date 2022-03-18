package de.codecentric.totiblogpost

import de.codecentric.totiblogpost.extensions.DynamoDbExtension
import de.codecentric.totiblogpost.extensions.WebTestClientExtention
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.RegisterExtension
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(DynamoDbExtension::class)
class BlogPostIntegrationTest {
    companion object {
        @JvmStatic
        @RegisterExtension
        val rest = WebTestClientExtention()
    }

    @Test
    internal fun `should work`() {
        rest.webTestClient.get()
            .uri { uriBuilder ->
                uriBuilder
                    .path("/blogpost/{id}")
                    .build(1L)
            }
            .exchange()
    }
}
