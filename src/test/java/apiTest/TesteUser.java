// nome do pacote
package apiTest;

// Bibliotecas
import io.restassured.response.ExtractableResponse;

import io.restassured.response.Response;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


// Classe

public class TesteUser {    // inicio da classe
    // Atributos

    static String ct = "application/json"; // content type

    static String uriUser = "https://petstore.swagger.io/v2/user/";

    // Funções e Métodos
    // funções de apoio
    public static String lerArquivoJson(String arquivoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(arquivoJson)));
    }


    // funções de teste

    @Test
    public void testarIncluirUser() throws IOException {
       // carregar os dados do nosso json
       String jsonBody = lerArquivoJson("src/test/resources/json/user1.json");

       String userId = "1373660044";

       // realizar o teste
                                                    // dado que
                                                    // o tipo do conteudo
                                                    // mostre as tudo
                                                    // corpo da requisição
        given()
               .contentType("application/json")
               .log().all()
               .body(jsonBody)
        .when()
                .post(uriUser)  //endpoint / onde
        .then()                                                 // mostre tudo na volta
                .log().all()                                   // comunicaçao ida e volta ok
                .statusCode(200)                            // tag code 200
                .body("code",is(200))                 // tag type é unknown
                .body("type", is("unknown"))          //message e o userId
                .body("message", is(userId));
    } //fim do post
    @Test
    public void testarConsultarUser(){ // inicio get user
        String username = "marcus";
         // resultados esperado

        int userId = 1373660044;
        String email = "mvnsv@hotmail.com";
        String senha = "123456";
        String telefone = "1155556666";


        given()
                .contentType(ct)
                .log().all()
        .when()
                .get(uriUser + username)
        .then()
                .log().all()
                .statusCode(200)
                .body("id", is(userId))
                .body("email", is(email))
                .body("password", is(senha))
                .body("phone", is(telefone));


    }// fim do get user
    @Test
    public void testarAlterarUser() throws IOException { // inicio do Put User
        String jsonBody = lerArquivoJson("src/test/resources/json/user2.json");

        String userId = "1373660044";
        String username = "marcus";

        given()
                .contentType(ct)
                .log().all()
                .body(jsonBody)
        .when()
                .put(uriUser + username)
        .then()
                .log().all()
                .statusCode(200)
                .body("code", is(200))
                .body("type", is("unknown"))
                .body("message", is(userId))
            ;


    } // fim do Put User
    @Test
    public void testarExcluirUser(){ // inicio do delete user
        String username = "marcus";

        given()
                .contentType(ct)
                .log().all()
        .when()
                .delete(uriUser + username)
        .then()
                .statusCode(200)
                .body("code", is(200))
                .body("type", is( "unknown"))
                .body("message", is(username))
                ;

    } // fim do delete user

    @Test
    public void testarLogin() { // inicio do login
        String username = "marcus";
        String password = "abcdef";

        Response resp = (Response) given()
                .contentType(ct)
                .log().all()
        .when()
                .get(uriUser + "login?username=" + username + "&password=" + password)
        .then()
                .log().all()
                .statusCode(200)
                .body("code", is(200))
                .body("type", is("unknown"))
                .body("message", containsString("logged in user session:"))
                .body("message", hasLength(36))
        .extract();

        // Extração do token da resposta
        String token = resp.jsonPath().getString("message").substring(23);
        System.out.println("Conteudo do Token: " + token);
    } // fim do login
    @ParameterizedTest
    @CsvFileSource(resources = "csv/massaUser.csv", numLinesToSkip = 1, delimiter = ',')
    public void testarIncluirUserCSV(
            String id,
            String username,
            String firstName,
            String lastName,
            String email,
            String password,
            String phone,
            String userStatus)
    { // inicio Incluir CSV
        // carregar os dados do nosso json

        /*
        StringBuilder jsonBody = new StringBuilder("{");
        jsonBody.append("'id': " + id + ",");
        jsonBody.append("'username': " + username + ",");
        jsonBody.append("'firstName': " + firstName + ",");
        jsonBody.append("'lastName': " + lastName + ",");
        jsonBody.append("'email': " + email + ",");
        jsonBody.append("'password': " + password+ ",");
        jsonBody.append("'phone': " + phone + ",");
        jsonBody.append("'userStatus': " + userStatus);
        jsonBody.append("}");
        */

        // realizar o teste
        String jsonBody = new String();
        given()                                         // Dado que
                .contentType(ct)                        // o tipo do conteúdo
                .log().all()                            // mostre tudo
                .body(jsonBody)                         // corpo da requisição
                .when()                                         // Quando
                .post(uriUser) // Endpoint / Onde
                .then()                                         // Então
                .log().all()                            // mostre tudo na volta
                .statusCode(200)                      // comunic. ida e volta ok
                .body("code", is(200))          // tag code é 200
                .body("type", is("unknown"))    // tag type é "unknown"
                .body("message", is(id))         // message é o userId
        ;
    } // fim incluir CSV
} // fim da classe