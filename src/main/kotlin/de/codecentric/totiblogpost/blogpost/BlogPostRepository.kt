package de.codecentric.totiblogpost.blogpost

import org.springframework.stereotype.Repository
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable
import software.amazon.awssdk.enhanced.dynamodb.Key
import software.amazon.awssdk.enhanced.dynamodb.TableSchema
import software.amazon.awssdk.enhanced.dynamodb.mapper.BeanTableSchema
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey

@Repository
class BlogPostRepository(private val enhancedClient: DynamoDbEnhancedClient) {
    private val blogPostTableName = "table_name"

    fun findById(id: Long): BlogPost =
       BlogPost.from(blogPostTable().getItem(Key.builder().partitionValue(id).build())) ?: throw BlogPostNotFoundException()


    fun findAll(): List<BlogPostEntry> =
        blogPostTable().scan().items().stream().toList()

    fun put(blogPost: BlogPostEntry) = blogPostTable().putItem(blogPost)

    fun delete(siteId: String): BlogPostEntry? =
        blogPostTable().deleteItem(Key.builder().partitionValue(siteId).build())

    fun blogPostTable(): DynamoDbTable<BlogPostEntry> =
        enhancedClient.table(blogPostTableName, getTableSchema())

    private fun getTableSchema(): BeanTableSchema<BlogPostEntry> = TableSchema.fromBean(BlogPostEntry::class.java)

    @DynamoDbBean
    data class BlogPostEntry(
        @get: DynamoDbPartitionKey var id: Long? = null,
        var title: String? = null,
        var content: String? = null,
    )
}