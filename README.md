Using HATEOAS with JAX-RS 2.1 and OpenAPI v3.0.0 specification
==============

- Build and run

      mvn clean package 
      java -jar target/jax-rs-2.1-hateaos-0.0.1-SNAPSHOT.jar


- Play with API using `Swagger UI`

      http://localhost:8080/api/api-docs

- Play with API using `curl`

      curl -i http://localhost:8080/api/companies

      curl -i http://localhost:8080/api/companies/1

      curl -i http://localhost:8080/api/companies/1/staff

 - More links
 
    - https://swagger.io/docs/specification/links/
    - https://tools.ietf.org/html/rfc5988
