package info.jk.validator.lifecycle

import info.jk.validator.entity.JSONSchema
import info.jk.validator.repository.JSONSchemaRepository
import io.quarkus.runtime.ShutdownEvent
import io.quarkus.runtime.StartupEvent
import org.jboss.logging.Logger
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.event.Observes
import javax.transaction.Transactional


@ApplicationScoped
class AppLifecycleBean(val jsonSchemaRepository: JSONSchemaRepository) {
    private val log: Logger = Logger.getLogger("ListenerBean")

    @Transactional
    fun onStart(@Observes ev: StartupEvent?) {
        log.info("The application is starting...")
        val jsonSchema = JSONSchema()
        jsonSchema.code = "teste"
        jsonSchema.schema = "{\"${'$'}schema\": \"http://json-schema.org/draft-04/schema#\", \"type\": \"object\", \"properties\": {\"data\": {\"type\": \"string\"}},\"additionalProperties\": false,\"required\": [\"data\"]}"
        jsonSchema.persist()
//
//        jsonSchemaRepository.persist(jsonSchema)
    }

    fun onStop(@Observes ev: ShutdownEvent?) {
        log.info("The application is stopping...")
    }
}