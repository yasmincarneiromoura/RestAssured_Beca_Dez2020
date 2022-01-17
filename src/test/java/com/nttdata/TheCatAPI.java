package com.nttdata;

import io.restassured.response.Response;
import org.junit.Test;
import org.junit.BeforeClass;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;



public class TheCatAPI {

    String id_votacao;

    @BeforeClass
    public static void base(){
        baseURI= "https://api.thecatapi.com/v1";
    }

    @Test
    public void criarCadastro(){
        given()
                .contentType("application/json")
                .body("{\n" +
                        "        \"email\": \"yasmin.carneiromoura@nttdata.com\",\n" +
                        "            \"appDescription\": \"Teste Beca\",\n" +
                        "            \"opted_into_mailing_list\": false,\n" +
                        "            \"details\": {\n" +
                        "        \"user_type\": \"personal\"\n" +
                        "    }\n" +
                        "    }")

        .when()
                .post("https://api.thecatapi.com/v1/user/passwordlesssignup")

        .then()
                .log().all()

        ;
    }

    @Test
    public void cadastroCampoObrigatorio(){
        given()
                .contentType("application/json")
                .body("\"appDescription\": \"Teste Beca\"")


        .when()
                .post("https://api.thecatapi.com/v1/user/passwordlesssignup")


        .then()
                .log().all()
                .body("message", containsString("\"email\" is required"))
                .statusCode(400)
        ;
    }
//65c8ac8d-d1a1-4127-980e-afca6eabf358
    @Test
    public void efetuarVotacao(){
        given()
                .contentType("application/json")
                .body("{\n" +
                        "  \"image_id\": \"39m\",\n" +
                        "  \"value\": true,\n" +
                        "  \"sub_id\": \"demo-b4529f\"\n" +
                        "}")
                .header("x-api-key", "65c8ac8d-d1a1-4127-980e-afca6eabf358")

        .when()
                .post("https://api.thecatapi.com/v1/votes/")

        .then()
                .statusCode(200)
                .body("message", is("SUCCESS"))
                .log().all()
        ;
    }

    @Test
    public void pegarVotacao(){
        Response response =

        given()
                .header("x-api-key", "65c8ac8d-d1a1-4127-980e-afca6eabf358")

        .when()
                .get("https://api.thecatapi.com/v1/votes/");

        response.then()
                .log().all()
                .body("image_id", hasItem("39m"));

       String id_votacao = response.jsonPath().getString("image_id");
       System.out.println("ID RETORNANDO" + id_votacao);


    }

    @Test
    public void deletarVotacao(){
       efetuarVotacao();
       pegarVotacao();

       given()
               .contentType("application/json")
               .header("x-api-key", "65c8ac8d-d1a1-4127-980e-afca6eabf358")
               .pathParams("vote_id", id_votacao)

       .when()
               .delete("https://api.thecatapi.com/v1/votes/{vote_id}")



       .then()
               .log().all()
               ;

    }

    }

