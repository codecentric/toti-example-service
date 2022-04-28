package de.codecentric.totiblogpost.blogpost

import org.springframework.stereotype.Service

@Service
class BlogPostService(var blogPostRepository: BlogPostRepository) {

    fun allBlogPosts(): List<BlogPost> =
        blogPostRepository.findAll()

    fun blogPostById(id: Long): BlogPost =
        blogPostRepository.findById(id = id)

    fun putBlogPost(id: Long, title: String, content: String?): BlogPost =
        blogPostRepository.put(id = id, title = title, content = content)

    fun deleteBlogPost(id: Long) =
        blogPostRepository.delete(id = id)

}
