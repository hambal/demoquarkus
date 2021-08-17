package org.my.group.json;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static io.restassured.RestAssured.given;
import static javax.ws.rs.core.HttpHeaders.ACCEPT;
import static javax.ws.rs.core.HttpHeaders.CONTENT_TYPE;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.*;
import static org.hamcrest.CoreMatchers.is;


@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TagResourceTest {

    @Test
    @Order(1)
    public void get() {
        given()
          .when().get("/tags")
          .then()
             .statusCode(200)
             .body(is("[{\"id\":1,\"label\":\"Tag1\"},{\"id\":2,\"label\":\"Tag2\"}]"));
    }

    @Test
    @Order(1)
    public void getOne() {
        given()
                .when().get("/tags/2")
                .then()
                .statusCode(200)
                .body(is("{\"id\":2,\"label\":\"Tag2\"}"));
    }

    @Test
    @Order(200)
    public void delete() {
        given()
                .when().delete("/tags/1")
                .then()
                .statusCode(204);
    }

    @Test
    @Order(3)
    void create() {
        Tag tag = new Tag(5, "tag5");

        given()
                .body(tag)
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .header(ACCEPT, APPLICATION_JSON)
                .when()
                .post("/tags")
                .then()
                .statusCode(CREATED.getStatusCode());
    }

    @Test
    @Order(4)
    public void update() {
        Tag tag = new Tag();
        tag.id = 2;
        tag.label = "Tag2x";

        given()
                .body(tag)
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .header(ACCEPT, APPLICATION_JSON)
                .when()
                .put("/tags/2")
                .then()
                .statusCode(200)
                .body(is("{\"id\":2,\"label\":\"Tag2x\"}"));
    }

    @Test
    @Order(1)
    public void tagGetPosts() {
        given()
                .when().get("/tags/1/posts")
                .then()
                .statusCode(200)
                .body(is("[{\"id\":1,\"title\":\"Abc\",\"content\":\"Content Abc\",\"tags\":[{\"id\":1,\"label\":\"Tag1\"}]}]"));
    }

    @Test
    @Order(1)
    public void tagAddPosts() {
        given()
                .when().put("/tags/1/posts/2")
                .then()
                .statusCode(OK.getStatusCode());
    }

    @Test
    @Order(100)
    public void tagDeletePosts() {
        given()
                .when().delete("/tags/1/posts/1")
                .then()
                .statusCode(NO_CONTENT.getStatusCode());
    }

}