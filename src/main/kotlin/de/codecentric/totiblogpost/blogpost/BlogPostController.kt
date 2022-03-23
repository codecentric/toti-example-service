package de.codecentric.totiblogpost.blogpost

import de.codecentric.totiblogpost.catfact.CatFactService
import org.springframework.web.bind.annotation.*

@RestController
class BlogPostController(val blogPostService: BlogPostService, val catFactService: CatFactService) {

    @GetMapping("/blogposts")
    fun allBlogPosts(): BlogPostDto {
        TODO()
    }

    @GetMapping("/blogpost/{id}")
    fun blogPost(@PathVariable id: Long): BlogPostDto {
        val blogPost = blogPostService.blogPostById(id)
        val catFact = catFactService.randomCatFact()

        return BlogPostDto.from(blogPost, listOfNotNull(catFact))
    }

    @PutMapping("/blogpost/{id}")
    fun createOrReplaceBlogPost(@PathVariable id: Long, @RequestBody blogPostDto: BlogPostDto): BlogPost? {
        return blogPostService.putBlogPost(id = id, title = blogPostDto.title, content = blogPostDto.content)
    }
}
