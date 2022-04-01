package de.codecentric.totiblogpost.blogpost.dto

import de.codecentric.totiblogpost.blogpost.BlogPost

data class BlogPostResponse(val id: Long, val title: String, val content: String?) {
    companion object {
        fun from(blogPost: BlogPost): BlogPostResponse {
            return BlogPostResponse(id = blogPost.id, title = blogPost.title, content = blogPost.content)
        }
    }
}
