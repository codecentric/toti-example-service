package de.codecentric.totiblogpost.catfact

data class CatFactResponse(val fact: String, val length: Long) {
    companion object {
        fun from(catFact: CatFact): CatFactResponse = CatFactResponse(catFact.fact, catFact.length)
    }
}
