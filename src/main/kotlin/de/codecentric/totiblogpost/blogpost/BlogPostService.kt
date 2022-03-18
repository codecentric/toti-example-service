package de.codecentric.totiblogpost.blogpost

import de.codecentric.totiblogpost.catfact.CatFactService
import org.springframework.stereotype.Service

@Service
class BlogPostService(val catFactService: CatFactService) {


    fun allBlogPosts() : List<BlogPost> {

    }

    fun blogPostById(id: Long) : BlogPost {
        val randomFact= catFactService.randomCatFact()

    }

}