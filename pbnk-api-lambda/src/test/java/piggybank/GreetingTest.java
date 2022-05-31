package piggybank;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;

@QuarkusTest
public class GreetingTest
{
    @Test
    public void testJaxrs() {
        RestAssured.when().get("/api/greetings").then()
                .body(equalTo("hello jaxrs"));
    }

    @Test
    public void testServlet() {
        RestAssured.when().get("/servlet/hello").then()
                .body(equalTo("hello servlet"));
    }

    @Test
    public void testVertx() {
        RestAssured.when().get("/vertx/hello").then()
                .body(equalTo("hello vertx"));
    }

    @Test
    public void testFunqy() {
        RestAssured.when().get("/funqyHello").then()
                .contentType("application/json")
                .body(equalTo("\"hello funqy\""));
    }

}
