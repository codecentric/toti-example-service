package de.codecentric.totiblogpost.extensions

import de.codecentric.totiblogpost.blogpost.BlogPostRepository
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.testcontainers.containers.GenericContainer


class DynamoDbLocalExtension : BeforeAllCallback {

    companion object {
        const val port = 8000

        @JvmStatic
        var container = DynamoDbLocalContainer()

        @JvmStatic
        var initialized = false
    }

    override fun beforeAll(context: ExtensionContext) {
        synchronized(DynamoDbLocalExtension::class) {
            if (!initialized) {
                container.withExposedPorts(port)
                    .start()
            }

            System.setProperty(
                "dynamo.endpoint",
                "http://${container.containerIpAddress}:${container.getMappedPort(port)}"
            )

            createTables(context)
        }
    }

    private fun createTables(context: ExtensionContext) {
        val blogPostRepository = SpringExtension.getApplicationContext(context).getBean(BlogPostRepository::class.java)
        blogPostRepository.blogPostTable().createTable()
    }

    // TODO overwrite
    class DynamoDbLocalContainer : GenericContainer<DynamoDbLocalContainer>("amazon/dynamodb-local:latest")
}
