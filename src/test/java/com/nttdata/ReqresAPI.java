package com.nttdata;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import org.json.JSONObject;

import org.junit.Test;

public class ReqresAPI {

    private static final String url = "https://reqres.in/";
    private String path;

    @Test
    public void lista_De_Usuarios_Retorna_Code_200(){
        path = "api/users?page=2";

        Response response = RestAssured.get(url+path);

        assertEquals(200, response.statusCode());

    }

    @Test
    public void quantidade_De_Usuarios_Igual_Doze(){
        path = "api/users?page=2";

        Response response = RestAssured.get(url+path);

        assertEquals(Integer.valueOf(12), response.path("total"));

    }

    @Test
    public void id_Inexistente(){
        path = "api/users/23";
        Response response = RestAssured.get(url+path);

        assertEquals(404, response.statusCode() );

    }

    @Test
    public void verificarEmailDeUsuarioEspecifico(){

        path = "api/users/2";
        Response response = RestAssured.get(url+path);
        assertEquals("janet.weaver@reqres.in", response.path("data.email"));
    }

    @Test
    public void verificar_Id_De_Usuario3(){

        path = "api/users?page=2";

       given().
                queryParam("page", 2)
        .when()
               .get(url+path)

      .then()

            .body("data[2]", hasEntry("id", 9));

    }

    @Test
    public void verifica_Retorno_De_Usuario_Cadastrado(){

        path = "api/users";

        JSONObject requestBody = new JSONObject();
        requestBody.put("name", "morpheus");
        requestBody.put("job", "leader");

        given().
                header("Content-Type","application/json").
                body(requestBody.toString()).
                when().
                post(url+path).
                then().
                body("name", is("morpheus") ).
                and().
                body("job", is("leader"));
    }


}
