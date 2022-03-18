package de.codecentric.totiblogpost.blogpost

import de.codecentric.totiblogpost.catfact.CatFactService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class BlogPostController(val blogPostService: BlogPostService, val catFactService: CatFactService) {

    @GetMapping("/blogposts")
    fun getAllBlogPosts(): BlogPostDto {
        TODO()
    }

    @GetMapping("/blogpost/{id}")
    fun getAllBlogPosts(@PathVariable id: Long): BlogPostDto {
        val blogPost = blogPostService.blogPostById(id)
        val catFact = catFactService.randomCatFact()

        return BlogPostDto.from(blogPost, listOfNotNull(catFact))
    }
}
