package addNewWidget;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.chrome.ChromeOptions;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class TestBase {
    @BeforeAll
    public static void BeforeAll() throws Exception {
        Configuration.holdBrowserOpen = true;
        // Создаём временную папку для чистого профиля Chrome
        Path tempProfile = Files.createTempDirectory("chrome-profile-");
        ChromeOptions options = new ChromeOptions();

        // Указываем путь к чистому профилю
        options.addArguments("user-data-dir=" + tempProfile.toAbsolutePath().toString());

        // Отключаем менеджер паролей и проверку утечки
        options.addArguments("--disable-features=PasswordLeakDetection,PasswordManagerOnboarding,PasswordManagerEnabled");
        options.setExperimentalOption("prefs", Map.of(
                "credentials_enable_service", false,
                "profile.password_manager_enabled", false,
                "profile.password_manager_leak_detection", false
        ));

        // Передаём опции в Selenide
        Configuration.browserCapabilities = options;
        Configuration.browserSize = System.getProperty("browserSize", "1920x1080");
    }
}
