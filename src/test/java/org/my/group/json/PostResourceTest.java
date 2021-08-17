package org.my.group.json;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static javax.ws.rs.core.HttpHeaders.ACCEPT;
import static javax.ws.rs.core.HttpHeaders.CONTENT_TYPE;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.*;


@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PostResourceTest {

    @Test
    @Order(1)
    public void get() {
        given()
          .when().get("/posts")
          .then()
             .statusCode(200)
             .body(is("[{\"id\":1,\"title\":\"Abc\",\"content\":\"Content Abc\",\"tags\":[{\"id\":1,\"label\":\"Tag1\"}]},{\"id\":2,\"title\":\"Bce\",\"content\":\"Bcd...\",\"tags\":[]}]"));
    }

    @Test
    @Order(1)
    public void getOne() {
        given()
                .when().get("/posts/2")
                .then()
                .statusCode(200)
                .body(is("{\"id\":2,\"title\":\"Bce\",\"content\":\"Bcd...\",\"tags\":[]}"));
    }

    @Test
    @Order(2)
    public void delete() {
        given()
                .when().delete("/posts/1")
                .then()
                .statusCode(204);
    }

    @Test
    @Order(3)
    void create() {
        Post post = new Post();
        post.id = 5;
        post.title = "Efg";
        post.content = "E....";

//        String newPost = "{\"id\": 10,\"title\": \"Jkl\",\"content\": \"Jj Jj Jj\"}";

        given()
                .body(post)
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .header(ACCEPT, APPLICATION_JSON)
                .when()
                .post("/posts")
                .then()
                .statusCode(CREATED.getStatusCode());
    }

    @Test
    @Order(4)
    public void update() {
        Post post = new Post();
        post.id = 2;
        post.title = "Bcl";
        post.content = "BBB ...";

        given()
                .body(post)
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .header(ACCEPT, APPLICATION_JSON)
                .when()
                .put("/posts/2")
                .then()
                .statusCode(200)
                .body(is("{\"id\":2,\"title\":\"Bcl\",\"content\":\"BBB ...\",\"tags\":[]}"));
    }
}