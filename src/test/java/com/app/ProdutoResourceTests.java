package com.app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import org.hamcrest.Matchers;


@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class ProdutoResourceTests {

    private Produto produtoInserido;
    
    private String locationResponse;
    
    
    @BeforeEach
    void steup() {
        produtoInserido = new Produto("Nome A", "Descrição B", 55.5d);
    }


    @Test
    @Order(1)
    void testSalvarProdutoEsperadoStatus201() {

        Response response = RestAssured.with()
            .body(produtoInserido)
            .when()
            .post("/produtos");
        
        locationResponse = response.getHeader("LOCATION");    
        
        response
            .then()
            .statusCode(201);
    }


    @Test
    @Order(2)
    void testRecuperarPeloHashIDProdutoInseridoEsperadoStatus200() {
        
        RestAssured
            .when()
                .get(locationResponse)
            .then()
                .statusCode(200)
                .body("nome", Matchers.equalTo("produtoInserido.nome"));

    }
}
