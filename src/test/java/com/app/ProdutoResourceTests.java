package com.app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import org.apache.http.entity.ContentType;
import org.hamcrest.Matchers;
import org.hamcrest.core.Is;


@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class ProdutoResourceTests {

    private String locationResponse;
    
    
    void steup() {
    }


    @Test
    @Order(1)
    void testSalvarProdutoEsperadoStatus201() {

        Response response = RestAssured.given()
        .contentType(ContentType.APPLICATION_JSON.toString())
        .body(new Produto("Nome A", "Descrição B", 55.5d))
        .when()
        .post("/produtos");
        
        locationResponse = response.getHeader("Location");
        
        System.out.println(locationResponse);
        
        response
            .then()
            .log().all()
            .statusCode(201);
    }


    @Test
    @Order(2)
    void testRecuperarPeloHashIDProdutoInseridoEsperadoStatus200() {
        String x = locationResponse;
        System.out.println(x);
        
        RestAssured
            .when()
                .get(x)
            .then()
                .log().all()
                .statusCode(200)
                .assertThat()
                .body("nome", Is.is(Matchers.notNullValue()));

    }
}
