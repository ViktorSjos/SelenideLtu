import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Screenshots;
import com.codeborne.selenide.ex.ElementNotFound;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selectors.byCssSelector;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class FinalDate {

    public static String finalDate() throws IOException {


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
        try {
            $( "#CybotCookiebotDialogBodyLevelButtonLevelOptinAllowAll").shouldBe( visible ).click();

        } catch (ElementNotFound e) {
            System.out.println("Count not find cookies");
        }


        try {
            $("[onclick*=\"gaClickEvent('First page Extra links', 'Click', '/student')\"]").click();

            $(byAttribute("href", "https://www.student.ladok.se/student/#/intyg")).click();


            $(byText("Inloggning via ditt lärosäte")).click();


            $(byId("searchinput")).val("ltu").pressEnter();
            $(byClassName("search-results"));

            $(byText("Lulea University of Technology")).click();

            $("[name='username']").val(email);
            $("[name='password']").val(password);

            // Click on the login button
            $("[name='submit']").click();


            $(byCssSelector("span.ms-2.fw-bold")).click();



        } catch (ElementNotFound e) {
            System.out.println("Count not find element");
        }

        String text = $("ladok-card").getText();

        String[] lines = text.split("\n");

        File directory = new File("target/screenshots");
        if (!directory.exists()) {
            directory.mkdirs();
        }
        try{
            // Take screenshot of the webpage
            File screenshot = Screenshots.takeScreenShotAsFile();

            // Save screenshot to the directory
            File destination = new File(directory, "final_examination.png");
            Files.copy(screenshot.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
            FileUtils.copyFile(screenshot, new File("final_examination.jpeg"));

            $(byText("Logga ut")).click();
            return lines[1];
        } catch (FileNotFoundException e) {
            System.out.println("Screenshot file not found");;
            return null;
        }


    }

}
