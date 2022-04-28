package de.codecentric.totiblogpost.blogpost

import de.codecentric.totiblogpost.blogpost.dto.BlogPostRequest
import de.codecentric.totiblogpost.blogpost.dto.BlogPostResponse
import org.springframework.web.bind.annotation.*

@RestController
class BlogPostController(val blogPostService: BlogPostService) {

    // TODO is created http status code ?
    @GetMapping("/blogposts")
    fun allBlogPosts(): List<BlogPostResponse> =
        blogPostService.allBlogPosts().map { BlogPostResponse.from(it) }.toList()

    @GetMapping("/blogposts/{id}")
    fun blogPost(@PathVariable id: Long): BlogPostResponse =
        blogPostService.blogPostById(id).let { BlogPostResponse.from(blogPost = it) }

    @PutMapping("/blogposts/{id}")
    fun createOrReplaceBlogPost(@PathVariable id: Long, @RequestBody blogPostRequest: BlogPostRequest): BlogPost =
        blogPostService.putBlogPost(id = id, title = blogPostRequest.title, content = blogPostRequest.content)

    @DeleteMapping("/blogposts/{id}")
    fun deleteBlogPost(@PathVariable id: Long) = blogPostService.deleteBlogPost(id)
}
