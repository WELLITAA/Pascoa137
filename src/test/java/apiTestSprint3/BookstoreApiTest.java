package apiTestSprint3;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class BookstoreApiTest {

    private CSVReader reader;

    @BeforeMethod
    public void setUpMethod() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        RestAssured.basePath = "/booking";
    }

    @AfterMethod
    public void tearDown() throws IOException {
        if (reader != null) {
            reader.close();
        }
    }

    @Test
    public void testGetPosts() {
        Response response = RestAssured.given()
                .when()
                .get();

        int statusCode = response.getStatusCode();
        String responseBody = response.getBody().asString();

        Assert.assertEquals(statusCode, 200);
        Assert.assertTrue(responseBody.contains("userId"));
        Assert.assertTrue(responseBody.contains("title"));
        Assert.assertTrue(responseBody.contains("body"));
    }

    @DataProvider(name = "bookingsData")
    public Object[][] getBookingsData() throws IOException, CsvException {
        try (CSVReader reader = new CSVReader(new FileReader("C:\\Users\\Raquel\\Pascoa137\\src\\test\\resources\\user4\\bookings.csv"))) {
            List<String[]> rows = reader.readAll();
            return rows.toArray(new Object[rows.size()][6]);
        }
    }

    @Test(dataProvider = "bookingsData")
    public void testCreateBooking(String firstname, String lastname, String totalprice, String depositpaid, String checkin, String checkout) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate checkinDate = LocalDate.parse(checkin, formatter);
        LocalDate checkoutDate = LocalDate.parse(checkout, formatter);

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body("{ \"firstname\": \"" + firstname + "\", \"lastname\": \"" + lastname + "\", \"totalprice\": " + Double.parseDouble(totalprice) + ", \"depositpaid\": " + Boolean.parseBoolean(depositpaid) + ", \"bookingdates\": { \"checkin\": \"" + checkin + "\", \"checkout\": \"" + checkout + "\" } }")
                .when()
                .post();

        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200);

        String responseBody = response.getBody().asString();
        Assert.assertTrue(responseBody.contains("bookingid"));

        int bookingId = response.jsonPath().getInt("bookingid");

        Response getBookingResponse = RestAssured.get("/" + bookingId);
        String getBookingResponseBody = getBookingResponse.getBody().asString();
        Assert.assertTrue(getBookingResponseBody.contains(firstname));
        Assert.assertTrue(getBookingResponseBody.contains(lastname));
        Assert.assertTrue(getBookingResponseBody.contains(totalprice));
        Assert.assertTrue(getBookingResponseBody.contains(depositpaid));
        Assert.assertTrue(getBookingResponseBody.contains(checkin));
        Assert.assertTrue(getBookingResponseBody.contains(checkout));
    }
}