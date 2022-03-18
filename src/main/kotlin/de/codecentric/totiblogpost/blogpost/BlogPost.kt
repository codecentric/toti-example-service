package de.codecentric.totiblogpost.blogpost

data class BlogPost(val id: Long, val title: String, val content: String?) {
    companion object {
        fun from(blogPostEntry: BlogPostRepository.BlogPostEntry?): BlogPost? {
            if (blogPostEntry == null) {
                return null;
            }

            val id = blogPostEntry.id
            val content = blogPostEntry.content
            val title = blogPostEntry.title

            if (id == null || title == null) {
                return null;
            }

            return BlogPost(id = id, title = title, content = content)
        }
    }
}