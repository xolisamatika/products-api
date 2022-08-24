package com.example.productsapi;

import com.example.productsapi.model.Product;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.List;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SpringBootBootstrapLiveTest {

    private static final String API_ROOT
            = "http://localhost:8081/products";

    private Product createRandomProduct() {
        Product product = new Product();
        product.setTitle(randomAlphabetic(10));
        product.setDescription(randomAlphabetic(15));
        return product;
    }

    private String createProductAsUri(Product product) {
        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(product)
                .post(API_ROOT);
        return API_ROOT + "/" + response.jsonPath().get("id");
    }

    @Test
    public void whenGetAllProducts_thenOK() {
        Response response = RestAssured.get(API_ROOT);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
    }

    @Test
    public void whenGetProductsByTitle_thenOK() {
        Product product = createRandomProduct();
        createProductAsUri(product);
        Response response = RestAssured.get(
                API_ROOT + "/title/" + product.getTitle());

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertTrue(response.as(List.class)
                .size() > 0);
    }
    @Test
    public void whenGetCreatedProductById_thenOK() {
        Product product = createRandomProduct();
        String location = createProductAsUri(product);
        Response response = RestAssured.get(location);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertEquals(product.getTitle(), response.jsonPath()
                .get("title"));
    }

    @Test
    public void whenGetNotExistProductById_thenNotFound() {
        Response response = RestAssured.get(API_ROOT + "/" + randomNumeric(4));

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
    }

    @Test
    public void whenCreateNewProduct_thenCreated() {
        Product book = createRandomProduct();
        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(book)
                .post(API_ROOT);

        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
    }

//    @Test
//    public void whenInvalidProduct_thenError() {
//        Product book = createRandomProduct();
//        book.setDescription(null);
//        Response response = RestAssured.given()
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .body(book)
//                .post(API_ROOT);
//
//        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
//    }

    @Test
    public void whenUpdateCreatedProduct_thenUpdated() {
        Product product = createRandomProduct();
        String location = createProductAsUri(product);
        product.setId(Long.parseLong(location.split("/products/")[1]));
        product.setDescription("newDescription");
        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(product)
                .put(location);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        response = RestAssured.get(location);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertEquals("newDescription", response.jsonPath()
                .get("description"));
    }

    @Test
    public void whenDeleteCreatedProduct_thenOk() {
        Product product = createRandomProduct();
        String location = createProductAsUri(product);
        Response response = RestAssured.delete(location);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        response = RestAssured.get(location);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
    }
}
