package de.codecentric.totiblogpost.catfact

import de.codecentric.totiblogpost.log
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class CatFactController(val catFactService: CatFactService) {
    @GetMapping("/catfacts")
    fun getCatFact(@RequestParam(defaultValue = "1") count: Int): CatFactDto =
        CatFactDto(catFacts = catFactService.getCatFact(count).map { CatFactResponse.from(it) }.toList())

    @ExceptionHandler
    fun handleException(exception: Exception): ResponseEntity<ErrorDto> {
        log.warn("Exception occured: $exception")
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .body(ErrorDto(message = exception.message ?: "Empty message", status = HttpStatus.INTERNAL_SERVER_ERROR.value()))
    }
}
