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
import static org.hamcrest.Matchers.not;

public class CreateDashboardNegativeTest {

    String dashboardName = "ADemoTestDashboard1234";
    private static String TOKEN;

    @Test
    public void addNewWidgetAndCheck() {
        RestAssured.baseURI = "https://demo.reportportal.io";
        String body = "{\n" +
                "  \"name\": \"" + dashboardName + "\",\n" +
                "  \"description\": \"string\"\n" +
                "}";

        Dotenv dotenv = Dotenv.load();
        TOKEN = dotenv.get("TOKEN");

        given()
                .contentType("application/json")
                .body(body)
                .when()
                .post("/api/v1/default_personal/dashboard")
                .then()
                .log().all()
                .statusCode(401);

        List<String> allDashboardNames = new ArrayList<>();
        int page = 1;
        int size = 20;
        int totalPages;

        do {
            String response = given()
                    .header("Authorization", "Bearer " + TOKEN)
                    .contentType("application/json")
                    .queryParam("page", page)
                    .queryParam("size", size)
                    .when()
                    .get("/api/v1/default_personal/dashboard?page.size=10000")
                    .then()
                    .log().all()
                    .statusCode(200)
                    .extract().asString();

            JsonPath jsonPath = new JsonPath(response);
            List<String> dashboardNames = jsonPath.getList("content.name");
            if (dashboardNames != null) {
                allDashboardNames.addAll(dashboardNames);
            }
            totalPages = jsonPath.getInt("page.totalPages");
            page++;
        } while (page <= totalPages);

        System.out.println("Все имена Dashboard'ов: " + allDashboardNames);
        assertThat(allDashboardNames, not(hasItem(dashboardName)));
    }
}
