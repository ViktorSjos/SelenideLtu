import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class IntygDownload {

    public static void getIntyg() throws InterruptedException {

        String targetPath = System.getProperty("user.dir") + "/target";
        Path path = Paths.get(targetPath);


        ChromeOptions options = new ChromeOptions();
        options.addArguments("download.default_directory", targetPath);

        // Create a new ChromeDriver instance with the configured options
        WebDriver driver = new ChromeDriver(options);

        Configuration.downloadsFolder = targetPath;


        System.setProperty("selenide.holdBrowserOpen", "true");


        Configuration.browser = "chrome";
        // open LTU website
        open("https://www.ltu.se");
        getWebDriver().manage().window().maximize();

        sleep(1000);

        // Click on the element by ID
        Selenide.$(By.id("CybotCookiebotDialogBodyLevelButtonLevelOptinAllowAll")).click();

        // Click on the Student link
        $(By.xpath("//a[text()='Student']")).click();

        // Click on the "Studera" link
        Selenide.$("a[href='#'][onclick*='Studera']").click();

        // Click on the "Registerutdrag" link
        Selenide.$(byAttribute("href", "/student/Studera/Resultat/Registerutdrag-1.160158")).click();

        // Wait for the page to load
        Selenide.sleep(1000);


        // Click on the "Registerutdrag" link
        Selenide.$("a[href='https://student.ladok.se']").click();

        // Wait for the page to load
        Selenide.sleep(1000);

        String originalHandle = getWebDriver().getWindowHandle();
        for(String handle : getWebDriver().getWindowHandles()) {
            if (!handle.equals(originalHandle)) {
                getWebDriver().switchTo().window(handle);
                break;
            }
        }

        // Click on the "Inloggning via ditt lärosäte" element
        Selenide.$(byText("Inloggning via ditt lärosäte")).click();

        // Wait for the page to load
        Selenide.sleep(1000);

        // Click on the "searchinput" element
        Selenide.$(byId("searchinput")).sendKeys("luleå tekniska universitet");

        // Wait for the page to load
        Selenide.sleep(1000);

        // locate the element using its CSS selector
        SelenideElement institutionElement = $(By.cssSelector("div.institution-text"));

        // click on the element
        institutionElement.click();



        // Create a new File object pointing to the location of the facebook.json file
        File jsonFile = new File("C:\\Users\\vikto\\Documents\\Facebook.json");

        // Declare variables to store email and password values
        String email = null;
        String password = null;

        // Use the ObjectMapper class to parse the JSON data from the file into a JsonNode object
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonFile);

            // Retrieve the email and password values from the jsonNode object
            email = jsonNode.get("facebookCredentials").get("email").asText();
            password = jsonNode.get("facebookCredentials").get("password").asText();

            System.out.println("Email: " + email);
            System.out.println("Password: " + password);

        } catch (IOException e) {
            e.printStackTrace();
        }

        $(By.name("username")).val(email);
        $(By.name("password")).val(password);

        // Click on the login button
        $(By.name("submit")).click();

        Selenide.sleep(1000);

        $(By.linkText("Intyg")).click();

        Selenide.sleep(1000);

        // Click on the Student Transcript
        $(By.linkText("Registreringsintyg")).click();

        Selenide.sleep(1000);

        File downloadedFile = null;
        int timeoutInSeconds = 30;
        long startTime = System.currentTimeMillis();
        File[] files;
        while ((System.currentTimeMillis() - startTime) < (timeoutInSeconds * 1000)) {
            files = new File(targetPath).listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.getName().equals("Intyg.pdf")) {
                        downloadedFile = file;
                        break;
                    }
                }
            }
            if (downloadedFile != null) {
                break;
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }

}
