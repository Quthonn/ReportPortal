package apiTests.createNewDashboardNegativeTest;
import apiTests.TestBaseAPI;
import io.github.cdimascio.dotenv.Dotenv;
import io.qameta.allure.Allure;
import model.BodyModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;

public class CreateDashboardNegativeTest extends TestBaseAPI {
    private static String TOKEN;

    Dotenv dotenv = Dotenv.load();

    @Test
    @DisplayName("Создание Dashboard и проверка его наличия (Negative Test)")
    public void addNewWidgetAndCheck() {
        BodyModel bodyModel = new BodyModel();
        bodyModel.setName(dashboardName);
        bodyModel.setDescription("String");

        TOKEN = dotenv.get("TOKEN");

        Allure.step("Выполнение API запроса создания Dashboard с пропущенными обязательными" +
                " параметрами", () -> {
            given()
                    .contentType("application/json")
                    .body(bodyModel)
                    .when()
                    .post("/api/v1/default_personal/dashboard")
                    .then()
                    .log().all()
                    .statusCode(401);
        });

        Allure.step("Проверка отсутствия Dashboard", () -> {
            given()
                    .header("Authorization", "Bearer " + TOKEN)
                    .contentType("application/json")
                    .when()
                    .get("api/v1/default_personal/dashboard?page.size=10000")
                    .then()
                    .log().all()
                    .statusCode(200)
                    .body("content.name", not(hasItem(dashboardName)));
            System.out.println("Dashboard " + dashboardName + " не был создан, т. к. не все обязательные параметры были переданы в запрос :(");
        });
    }
}
