package de.codecentric.totiblogpost.blogpost

import de.codecentric.totiblogpost.catfact.CatFact

data class BlogPostDto(val id: Long, val title: String, val content: String?, val catFacts: List<CatFactDto>) {
    companion object {
        fun from(blogPost: BlogPost, catFacts: List<CatFact>): BlogPostDto {
            val catFactsDto = catFacts.map { CatFactDto(it.fact, it.length) }
            return BlogPostDto(id = blogPost.id, title = blogPost.title, content = blogPost.content, catFacts = catFactsDto)
        }
    }
}
