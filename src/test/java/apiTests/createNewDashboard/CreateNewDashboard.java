package apiTests.createNewDashboard;
import apiTests.TestBaseAPI;
import io.github.cdimascio.dotenv.Dotenv;
import io.qameta.allure.Allure;
import model.BodyModel;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;

public class CreateNewDashboard extends TestBaseAPI {
    private static String TOKEN;

    @Test
    public void addNewWidgetAndCheck() {
        BodyModel bodyModel = new BodyModel();
        bodyModel.setName(dashboardName);
        bodyModel.setDescription("String");

        Dotenv dotenv = Dotenv.load();
        TOKEN = dotenv.get("TOKEN");

        Allure.step("Выполнение API запроса создания Dashboard", () -> {
            given()
                    .log().all()
                    .header("Authorization", "Bearer " + TOKEN)
                    .contentType("application/json")
                    .body(bodyModel)
                    .when()
                    .post("/api/v1/default_personal/dashboard")
                    .then()
                    .log().all()
                    .statusCode(201);
        });

        Allure.step("Проверить, что Dashboard успешно создан и есть в списке", () -> {
            given()
                    .log().all()
                    .header("Authorization", "Bearer " + TOKEN)
                    .contentType("application/json")
                    .when()
                    .get("/api/v1/default_personal/dashboard?page.size=10000")
                    .then()
                    .log().all()
                    .statusCode(200)
                    .body("content.name", hasItem(dashboardName));
            System.out.println("Dashboard " + dashboardName + " успешно создан :)");
        });
    }
}

