import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Screenshots;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeOptions;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
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
        System.setProperty("chromeoptions.args", "--headless");
        Configuration.browser = "chrome";
        open("https://www.ltu.se");
        getWebDriver().manage().window().maximize();
        if ($(By.id("CybotCookiebotDialogBodyLevelButtonLevelOptinAllowAll")).isDisplayed()) {
            $(By.id("CybotCookiebotDialogBodyLevelButtonLevelOptinAllowAll")).click();
        }


        $(By.cssSelector("[onclick*=\"gaClickEvent('First page Extra links', 'Click', '/student')\"]")).click();


        $(byAttribute("href", "https://www.student.ladok.se/student/#/intyg")).click();


        $(byText("Inloggning via ditt lärosäte")).click();


        $(byId("searchinput")).val("ltu").pressEnter();
        $(byClassName("search-results"));

        $(byText("Lulea University of Technology")).click();

        $(By.name("username")).val(email);
        $(By.name("password")).val(password);

        // Click on the login button
        $(By.name("submit")).click();

        $(byCssSelector("span.ms-2.fw-bold")).click();

        String text = $("ladok-card").getText();
        String[] lines = text.split("\n");

        File directory = new File("target/screenshots");
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Take screenshot of the webpage
        File screenshot = Screenshots.takeScreenShotAsFile();

        // Save screenshot to the directory
        File destination = new File(directory, "screenshot.png");
        Files.copy(screenshot.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
        FileUtils.copyFile(screenshot, new File("final_examination.jpeg"));

        System.out.println(lines[1]);
        return lines[1];


    }

}
