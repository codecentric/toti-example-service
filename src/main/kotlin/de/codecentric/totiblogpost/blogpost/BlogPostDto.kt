package de.codecentric.totiblogpost.blogpost

import de.codecentric.totiblogpost.catfact.CatFact

data class BlogPostDto(val id: Long, val title: String, val content: String, val catFacts: List<CatFactDto>) {
    companion object {
        fun from(blogPost: BlogPost, catFacts : List<CatFact>) {
            val catFactsDto = catFacts.
            return BlogPostDto()
        }
    }
}