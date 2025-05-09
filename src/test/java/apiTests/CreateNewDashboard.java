package apiTests;
import io.github.cdimascio.dotenv.Dotenv;
import io.restassured.path.json.JsonPath;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import io.restassured.RestAssured;
import java.util.List;
import java.util.Locale;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;

import com.github.javafaker.Faker;

public class CreateNewDashboard {
    private static String TOKEN;

    @Test
    public void addNewWidgetAndCheck() {
        Faker faker = new Faker(new Locale("en"));

        String dashboardName = faker.name().title();
        System.out.println("Название Dashboard: " + dashboardName);
        RestAssured.baseURI = "https://demo.reportportal.io";
        String body = "{\n" +
                "  \"name\": \"" + dashboardName + "\",\n" +
                "  \"description\": \"string\"\n" +
                "}";

        Dotenv dotenv = Dotenv.load();
        TOKEN = dotenv.get("TOKEN");

        given()
                .header("Authorization", "Bearer " + TOKEN)
                .contentType("application/json")
                .body(body)
                .when()
                .post("/api/v1/default_personal/dashboard")
                .then()
                .log().all()
                .statusCode(201);

        given()
                .header("Authorization", "Bearer " + TOKEN)
                .contentType("application/json")
                .when()
                .get("/api/v1/default_personal/dashboard?page.size=10000")
                .then()
                .log().all()
                .statusCode(200)
                .body("content.name", hasItem(dashboardName));
    }
}

