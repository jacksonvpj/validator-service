package info.jk.validator.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.networknt.schema.JsonSchemaFactory
import com.networknt.schema.ValidationMessage
import info.jk.validator.model.RequestValidation
import info.jk.validator.model.ValidatorException
import io.smallrye.mutiny.Uni
import io.smallrye.mutiny.infrastructure.Infrastructure
import javax.enterprise.context.ApplicationScoped
import javax.transaction.Transactional
import javax.validation.Validator

@ApplicationScoped
class ValidatorService(
    val jsonSchemaService: JSONSchemaService,
    val objectMapper: ObjectMapper,
    val validator: Validator
    ) {

    fun validate(jsonBody: String): Uni<Void> {
        return Uni.createFrom().voidItem()
            .emitOn(Infrastructure.getDefaultWorkerPool())
            .call { e ->
                Uni.createFrom().item(getConstraints(jsonBody))
                    .onItem().transformToUni { set ->
                        if (set.isEmpty()) {
                            return@transformToUni Uni.createFrom().voidItem()
                        }
                        return@transformToUni Uni.createFrom().failure { (ValidatorException(set)) }
                    }
            }

    }

    fun selfValidate(jsonBody: String): Set<ValidationMessage> {
        val jsonSchemeString = """
            {
              "${'$'}schema": "http://json-schema.org/draft-04/schema#",
              "type": "object",
              "properties": {
                "code": {
                  "type": "string"
                },
                "jsonBody": {
                  "type": "string"
                }
              },
              "additionalProperties": false,
              "required": [
                "code",
                "jsonBody"
              ]
            }
        """
        return callValidate(jsonSchemeString, jsonBody)
    }

    fun getConstraints(jsonBody: String): Set<ValidationMessage> {

        val constraints = selfValidate(jsonBody)
        if (constraints.isNotEmpty()) return constraints

        val request = objectMapper.readValue(jsonBody, RequestValidation::class.java)
        val jsonSchema = jsonSchemaService.findByCode(request.code!!) ?: return emptySet()

        val schema = jsonSchema.schema!!
        return callValidate(schema, request.jsonBody!!)
    }

    fun callValidate(jsonSchema: String, jsonBody: String): MutableSet<ValidationMessage> {
        val jsonNode = objectMapper.readTree(jsonBody)
        val jsonSchema = JsonSchemaFactory.getInstance().getSchema(jsonSchema)
        return jsonSchema.validate(jsonNode)
    }
}