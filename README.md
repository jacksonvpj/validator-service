# validator-service project

This microservice centralizes validation's payload by specification of jsonSchema drafts  
registered by CRUD into own database.
- [Draft-07](https://json-schema.org/specification-links.html#draft-7)
- [Draft-06](https://json-schema.org/specification-links.html#draft-6)
- [Draft-04](https://json-schema.org/specification-links.html#draft-4)

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

 

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
./gradlew quarkusDev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application

The application can be packaged using:

```shell script
./gradlew build
```

It produces the `quarkus-run.jar` file in the `build/quarkus-app/` directory. Be aware that it’s not an _über-jar_ as
the dependencies are copied into the `build/quarkus-app/lib/` directory.

If you want to build an _über-jar_, execute the following command:

```shell script
./gradlew build -Dquarkus.package.type=uber-jar
```

The application is now runnable using `java -jar build/quarkus-app/quarkus-run.jar`.

## Creating a native executable

You can create a native executable using:

```shell script
./gradlew build -Dquarkus.package.type=native
```


Or, if you don't have GraalVM installed, you can run the native executable build in a container using:

```shell script
./gradlew build -Dquarkus.package.type=native -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./build/validator-service-1.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/gradle-tooling.

## Execution

After running, call http://localhost:8080/validade

valid payload 
```
{
    "code": "teste",
    "jsonBody": "{ \"data\":\"teste\"}"
}
```
returning 204 http status 

invalid payload 
```
{
    "code": "teste",
    "jsonBody": "{ \"data\":\"teste\", \"data1\":\"teste1\"}"
}
```
returning 422 http status and
```
{
    "protocol": "fb9f6d31-7f38-492e-ae82-81c679b9f861",
    "createdAt": "2021-04-18T19:08:51-03:00",
    "errors": [
        {
            "code": "1001",
            "field": "data1",
            "message": "$.data1: is not defined in the schema and the schema does not allow additional properties"
        }
    ]
}
```
## Related guides

- Kotlin ([guide](https://quarkus.io/guides/kotlin)): Write your services in Kotlin

- json-schema ([guide](https://json-schema.org/)): Specification of json-schema

- json-schema generator ([guide](https://www.jsonschema.net/)): Generator of json-schema from json payload