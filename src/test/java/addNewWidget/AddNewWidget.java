package addNewWidget;
import addNewWidget.Pages.RegistrationPage;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AddNewWidget extends TestBase {
    RegistrationPage registrationPage = new RegistrationPage();

    @Test
    @DisplayName("Создание нового Widget")
    @Feature("add")
    @Story("test")
    @Owner("Quthonn")
    @Severity(SeverityLevel.NORMAL)
    @Link(value = "testing", url = "https://demo.reportportal.io/")
    public void addNewWidget() {
        registrationPage.openPage()
                .authorization()
                .goToDashboardsList()
                .goToDashboard()
                .addWidget()
                .selectTypeWidget()
                .selectFilter()
                .finishAndCheck();
    }
}
