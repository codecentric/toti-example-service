package de.codecentric.totiblogpost.blogpost

import org.springframework.stereotype.Service

@Service
class BlogPostService(var blogPostRepository: BlogPostRepository) {

    fun allBlogPosts(): List<BlogPost> {
        return blogPostRepository.findAll()
    }

    fun blogPostById(id: Long): BlogPost {
        return blogPostRepository.findById(id)
    }

}
