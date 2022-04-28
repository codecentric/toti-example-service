package de.codecentric.totiblogpost

import de.codecentric.totiblogpost.test_fixtures.BlogPostTestFixture
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

        val blogPostTestFixture = BlogPostTestFixture(rest)
    }

    // setup (given)
    // execution (when)
    // verify (then)

    @Test
    internal fun `should return blog post`() {
        val id = IdGenerator.nextAsLong()
        blogPostTestFixture.givenSomeBlogPost(id, "some title 1", "the content 1")
        rest.webTestClient.get()
            .uri { uriBuilder ->
                uriBuilder.path("/blogposts/{id}")
                    .build(id)
            }
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody().json("""
                {
                   "id": $id,
                   "title": "some title 1",
                   "content": "the content 1"
                }
            """.trimIndent())
    }
    @Test
    internal fun `should create blog post and return it`() {
        val id = IdGenerator.nextAsLong()
        rest.webTestClient.put()
            .uri { uriBuilder ->
                uriBuilder.path("/blogposts/{id}")
                    .build(id)
            }.bodyValue(
                """
                {
                   "id": $id,
                   "title": "some title 2",
                   "content": "the content 2"
                }
            """.trimIndent()
            )
            .headers { headers -> headers.contentType = MediaType.APPLICATION_JSON }
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody().json("""
                {
                   "id": $id,
                   "title": "some title 2",
                   "content": "the content 2"
                }
            """.trimIndent())
    }

    @Test
    internal fun `should update blog post and return it`() {
        val id = IdGenerator.nextAsLong()
        blogPostTestFixture.givenSomeBlogPost(id, "some title 1", "the content 1")

        rest.webTestClient.put()
            .uri { uriBuilder ->
                uriBuilder.path("/blogposts/{id}")
                    .build(id)
            }.bodyValue(
                """
                {
                   "id": $id,
                   "title": "some title 2",
                   "content": "the content 2"
                }
            """.trimIndent()
            )
            .headers { headers -> headers.contentType = MediaType.APPLICATION_JSON }
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody().json("""
                {
                   "id": $id,
                   "title": "some title 2",
                   "content": "the content 2"
                }
            """.trimIndent())
    }
}
