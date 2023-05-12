import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Screenshots;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.io.IOException;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selectors.byCssSelector;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class FinalDate {

    public static String finalDate() throws IOException {

        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);
        Configuration.browserCapabilities = options;

        File jsonFile = new File("C:\\Users\\vikto\\Documents\\Facebook.json");

        String email = null;
        String password = null;

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonFile);

            email = jsonNode.get("facebookCredentials").get("email").asText();
            password = jsonNode.get("facebookCredentials").get("password").asText();

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.setProperty("selenide.holdBrowserOpen", "true");
        Configuration.browser = "chrome";
        open("https://www.ltu.se");
        getWebDriver().manage().window().maximize();
        sleep(1000);
        if ($(By.id("CybotCookiebotDialogBodyLevelButtonLevelOptinAllowAll")).isDisplayed()) {
            $(By.id("CybotCookiebotDialogBodyLevelButtonLevelOptinAllowAll")).click();
        }

        sleep(500);

        $(By.cssSelector("[onclick*=\"gaClickEvent('First page Extra links', 'Click', '/student')\"]")).click();

        sleep(500);

        $(byAttribute("href", "https://www.student.ladok.se/student/#/intyg")).click();

        sleep(500);

        $(byText("Inloggning via ditt lärosäte")).click();

        sleep(500);

        $(byId("searchinput")).val("ltu").pressEnter();
        $(byClassName("search-results"));

        sleep(500);

        $(byText("Lulea University of Technology")).click();

        sleep(500);

        $(By.name("username")).val(email);
        $(By.name("password")).val(password);

        // Click on the login button
        $(By.name("submit")).click();

        sleep(500);

        $(byCssSelector("span.ms-2.fw-bold")).click();

        sleep(500);

        String text = $("ladok-card").getText();
        String[] lines = text.split("\n");

        sleep(500);
        File screenshot = Screenshots.takeScreenShotAsFile();

        FileUtils.copyFile(screenshot, new File("screenshot.png"));

        return lines[1];

    }

}
