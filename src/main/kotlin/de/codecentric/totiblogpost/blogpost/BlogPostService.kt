package de.codecentric.totiblogpost.blogpost

import org.springframework.stereotype.Service

@Service
class BlogPostService(var blogPostRepository: BlogPostRepository) {

    fun allBlogPosts(): List<BlogPost> {
        return blogPostRepository.findAll()
    }

    fun blogPostById(id: Long): BlogPost {
        return blogPostRepository.findById(id = id)
    }

    fun putBlogPost(id: Long, title: String, content: String?): BlogPost? {
        val blogPostEntry = blogPostRepository.put(BlogPostRepository.BlogPostEntry(id = id, title = title, content = content))
        return BlogPost.from(blogPostEntry)
    }

}
