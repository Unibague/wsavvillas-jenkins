package controller;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;

/**
 * Tests unitarios para el controlador BillController
 */
@QuarkusTest
public class BillControllerTest {

    @Test
    public void testSaludar() {
        String cuerpoSoap = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:onl=\"http://biller.com/onlinebilling\">\n" +
                "   <soapenv:Header/>\n" +
                "   <soapenv:Body>\n" +
                "      <onl:saludar>\n" +
                "      </onl:saludar>\n" +
                "   </soapenv:Body>\n" +
                "</soapenv:Envelope>";

        given()
                .body(cuerpoSoap)
                .when().post("/soap/bill")
                .then().statusCode(200) // Espera un código de estado 200
                .body(containsString("¡Hola, Quarkus"));
    }
}
