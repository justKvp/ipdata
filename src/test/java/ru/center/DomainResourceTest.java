package ru.center;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class DomainResourceTest {
    @Test
    void testHelloEndpoint() {
        given()
          .when().post("/api/v1/domain/getDomain")
          .then()
             .statusCode(415);
    }

}