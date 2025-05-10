package apiTests;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

import java.util.Locale;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
public class TestBaseAPI {
    public String dashboardName;

    @BeforeAll
    public void beforeAllAPI() {
        RestAssured.baseURI = "https://demo.reportportal.io";

        Faker faker = new Faker(new Locale("en"));
        dashboardName = faker.name().title();
        System.out.println("Название Dashboard: " + dashboardName);
    }
}
