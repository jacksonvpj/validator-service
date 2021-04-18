package info.jk.validator.controller

import com.fasterxml.jackson.databind.ObjectMapper
import info.jk.validator.model.RequestValidation
import info.jk.validator.model.ResultError
import info.jk.validator.model.ValidatorException
import info.jk.validator.service.ValidatorService
import io.quarkus.vertx.web.Route
import io.smallrye.mutiny.Uni
import io.smallrye.mutiny.infrastructure.Infrastructure
import io.vertx.core.http.HttpMethod
import io.vertx.core.http.HttpServerResponse
import io.vertx.ext.web.RoutingContext
import java.util.concurrent.Executors
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class ValidatorController(val validatorService: ValidatorService, val objectMapper: ObjectMapper) {

    @Route(path = "/validate", methods = [HttpMethod.POST], produces = ["application/json"], order = 0)
    fun validate(rc: RoutingContext): Uni<Void> {
        return validatorService.validate(rc.bodyAsString)
    }

    @Route(type = Route.HandlerType.FAILURE, produces = ["application/json"], order = 1)
    fun doValidationException(e: ValidatorException, response: HttpServerResponse) {
        val resultError = ResultError()
        e.constraints.parallelStream().forEach {
            val error = ResultError.Error(it.code, it.arguments[0], it.message)
            resultError.errors.add(error)
        }
        val message = objectMapper.writeValueAsString(resultError)
        response.setStatusCode(422).end(message)
    }

    @Route(type = Route.HandlerType.FAILURE, produces = ["application/json"], order = 1)
    fun doException(e: Exception, response: HttpServerResponse) {
        response.setStatusCode(422).end(e.message)
    }
}