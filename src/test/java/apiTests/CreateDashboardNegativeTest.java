package apiTests;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;
import io.restassured.RestAssured;
import static org.hamcrest.Matchers.not;

public class CreateDashboardNegativeTest {
    String dashboardName = "ADemoTestDashboard1234";
    private static String TOKEN;

    Dotenv dotenv = Dotenv.load();

    @Test
    public void addNewWidgetAndCheck() {
        RestAssured.baseURI = "https://demo.reportportal.io";
        String body = "{\n" +
                "  \"name\": \"" + dashboardName + "\",\n" +
                "  \"description\": \"string\"\n" +
                "}";

        TOKEN = dotenv.get("TOKEN");

        given()
                .contentType("application/json")
                .body(body)
                .when()
                .post("/api/v1/default_personal/dashboard")
                .then()
                .log().all()
                .statusCode(401);

        given()
                .header("Authorization", "Bearer " + TOKEN)
                .contentType("application/json")
                .when()
                .get("https://demo.reportportal.io/api/v1/default_personal/dashboard?page.size=10000")
                .then()
                .log().all()
                .statusCode(200)
                .body("content.name", not(hasItem(dashboardName)));
    }
}
