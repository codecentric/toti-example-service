package de.codecentric.totiblogpost.blogpost

import de.codecentric.totiblogpost.blogpost.dto.BlogPostRequest
import de.codecentric.totiblogpost.blogpost.dto.BlogPostResponse
import de.codecentric.totiblogpost.catfact.CatFactService
import org.springframework.web.bind.annotation.*

@RestController
class BlogPostController(val blogPostService: BlogPostService, val catFactService: CatFactService) {

    @GetMapping("/blogposts")
    fun allBlogPosts(): BlogPostResponse {
        TODO()
    }

    @GetMapping("/blogpost/{id}")
    fun blogPost(@PathVariable id: Long): BlogPostResponse {
        val blogPost = blogPostService.blogPostById(id)
        //  val catFact = catFactService.randomCatFact()

        return BlogPostResponse.from(blogPost = blogPost) // , listOfNotNull(catFact)
    }

    @PutMapping("/blogpost/{id}")
    fun createOrReplaceBlogPost(@PathVariable id: Long, @RequestBody blogPostRequest: BlogPostRequest): BlogPost {
        return blogPostService.putBlogPost(id = id, title = blogPostRequest.title, content = blogPostRequest.content)
    }
}
