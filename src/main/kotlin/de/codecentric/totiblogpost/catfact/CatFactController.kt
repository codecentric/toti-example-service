package de.codecentric.totiblogpost.catfact

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class CatFactController(val catFactService: CatFactService) {
    @GetMapping("/catfacts")
    fun getCatFact(@RequestParam(defaultValue = "1") count: Int): CatFactDto =
        CatFactDto(catFacts = catFactService.getCatFact(count).map { CatFactResponse.from(it) }.toList())
}
