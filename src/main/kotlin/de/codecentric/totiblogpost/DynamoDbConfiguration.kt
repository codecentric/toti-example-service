package de.codecentric.totiblogpost

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import java.net.URI

@Configuration
class DynamoDbConfiguration {

    @Bean
    fun enhancedClient(client: DynamoDbClient): DynamoDbEnhancedClient =
        DynamoDbEnhancedClient.builder()
            .dynamoDbClient(client)
            .build()


    @Bean
    fun createClient(
        @Value("\${dynamo.region:us-east-1}") region: String,
        @Value("\${dynamo.endpoint:#{null}}") endpoint: String?
    ): DynamoDbClient {
        val clientBuilder = DynamoDbClient.builder()
            .region(Region.of(region))
            .credentialsProvider(DefaultCredentialsProvider.create())

        // endpoint is only set for "test" profile by testcontainers
        endpoint?.let {
            log.warn("configure dynamodb client in test mode with endpoint override")
            clientBuilder.endpointOverride(URI.create(it))
        }

        return clientBuilder.build()
    }
}
