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
        assertThat("Dashboard должен присутствовать в списке", allDashboardNames, hasItem(dashboardName));
    }

}
