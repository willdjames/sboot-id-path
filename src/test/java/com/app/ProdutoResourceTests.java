package com.app;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
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


@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class ProdutoResourceTests {

    private String locationResponse;
    
    private final String nomeProdutoInserido = "Nome produto AbC";
    
    
    void steup() {
    }


    @Test
    @Order(1)
    void test_salva_produto_esperado_status_201() {

        Response response = RestAssured
            .given()
                .contentType(ContentType.APPLICATION_JSON.toString())
                .body( new Produto(nomeProdutoInserido, "Descrição B", 55.5d) )
            .when()
                .post("/produtos");
        
        locationResponse = response.getHeader("Location");
        
        response
            .then()
                .log().all()
                .statusCode(201);
    }


    @Test
    @Order(2)
    void tes_recupera_todos_produtos_esperado_status_200() {
        
        RestAssured
            .when()
                .get("/produtos")
            .then()
                .log().all()
                .statusCode(200);
    }


    @Test
    @Order(3)
    void test_recupera_pelo_hashID_produto_inserido_esperado_status_200() {
        
        RestAssured
            .when()
                .get(locationResponse)
            .then()
                .log().all()
                .statusCode(200)
            .assertThat()
                .body("nome", Matchers.equalTo(nomeProdutoInserido));
    }
}
