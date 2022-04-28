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

    fun findById(id: Long): BlogPost = BlogPost.from(blogPostTable().getItem(Key.builder().partitionValue(id).build()))
        ?: throw BlogPostNotFoundException()

    fun findAll(): List<BlogPost> = blogPostTable().scan().items().mapNotNull {
        BlogPost.from(it)
    }.toList()

    fun put(id: Long, title: String, content: String?): BlogPost {
        blogPostTable().putItem(BlogPostEntry(id = id, title = title, content = content))

        return findById(id = id)
    }

    fun delete(id: Long): BlogPostEntry? =
        blogPostTable().deleteItem(Key.builder().partitionValue(id).build())

    fun blogPostTable(): DynamoDbTable<BlogPostEntry> = enhancedClient.table(blogPostTableName, getTableSchema())

    private fun getTableSchema(): BeanTableSchema<BlogPostEntry> = TableSchema.fromBean(BlogPostEntry::class.java)

    @DynamoDbBean
    data class BlogPostEntry(
        @get: DynamoDbPartitionKey var id: Long? = null,
        var title: String? = null,
        var content: String? = null,
    )
}
