package uiTests.addNewWidget.Pages;
import com.github.javafaker.Faker;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Allure;
import java.util.Locale;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class RegistrationPage {
    Faker faker = new Faker(new Locale("en"));
    private SelenideElement
            loginButton = $(".bigButton__color-organish--gj0Mz"),
            dashboardsListClick = $(".sidebar__sidebar-btn--DE02C"),
            dashboardSelect = $("#app > div > div > div > div > div.layout__content--y1ANI > div.scrollWrapper__scroll-component--L3JSO > div.scrollWrapper__scrolling-content--FGvAS.scrollWrapper__with-footer--lyezV > div > div.layout__page-container--O1S09 > div > div.pageLayout__page-content--DCfUt > div.grid__grid--W4yQA.dashboardTable__dashboard-table--oK3Zi > div:nth-child(2) > div > a"),
            addWidgetClick = $(".dashboardItemPage__buttons-block--QoL50"),
            selectTypeWidget = $(".widgetTypeItem__widget-type-item--pkO3L"),
            goToStepSelectFilter = $(".wizardControlsSection__buttons-block--HD8zo"),
            selectFilter = $(".inputRadio__input-radio--EMMTx"),
            addFilterButton = $("button[title='Add filter']"),
            selectFilterName = $(".input__input--iYEmM"),
            selectLaunchName = $ (".inputConditional__input--esNOk"),
            submitClick = $(".bigButton__color-booger--EpRlL"),
            goFinalStep = $$(".wizardControlsSection__button--PtN2R").findBy(text("Next step")),
            widgetName = $(".input__input--iYEmM"),
            createWidgetClick = $(".bigButton__big-button--BmG4Q"),
            widgetHaveCheck = $(".widget__widget-container--EfepB");

    public RegistrationPage openPage() {
        Allure.step("Открытие сайта", () -> {
            open("https://demo.reportportal.io/");
        });
        return this;
    }

    public RegistrationPage authorization() {
        Allure.step("Авторизация", () -> {
            $(loginButton).click();
        });
        return this;
    }

    public RegistrationPage goToDashboardsList() {
        Allure.step("Переход в раздел Dashboards", () -> {
            $(dashboardsListClick).click();
        });
        return this;
    }

    public RegistrationPage goToDashboard() {
        Allure.step("Переход в существующий Dashboard", () -> {
            $(dashboardSelect).click();
        });
        return this;
    }

    public RegistrationPage addWidget() {
        Allure.step("Нажатие по кнопке 'Add new widget'", () -> {
            $(addWidgetClick).click();
        });
        return this;
    }

    public RegistrationPage selectTypeWidget() {
        Allure.step("Выбор нужного типа виджета", () -> {
            $(selectTypeWidget).click();
            $(goToStepSelectFilter).click();
        });
        return this;
    }

    public RegistrationPage selectFilter() {
        Allure.step("Создание фильтра и его выбор фильтра", () -> {
            $(addFilterButton).click();
            $(selectFilterName).setValue(faker.name().title());
            $(selectLaunchName).setValue(faker.name().title());
            $(submitClick).click();
            $(selectFilter).click();
            $(goFinalStep).click();
        });
        return this;
    }

    public RegistrationPage finishAndCheck() {
        Allure.step("Завершение создания виджета и проверка на его наличие", () -> {
            String value = $(widgetName).getAttribute("value");
            System.out.println("Название виджета: " + value);
            $(createWidgetClick).click();
            $(widgetHaveCheck).should(text(value));
        });
        return this;
    }
}
