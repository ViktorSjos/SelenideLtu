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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static com.codeborne.selenide.Selenide.*;

import java.io.File;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class IntygDownload {

    public static String getIntyg() throws InterruptedException, FileNotFoundException {



        String targetPath = System.getProperty("user.dir") + "/target";


        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);
        options.addArguments("download.default_directory", targetPath);

        Configuration.downloadsFolder = targetPath;


        System.setProperty("selenide.holdBrowserOpen", "true");



        Configuration.browser = "chrome";
        // open LTU website
        open("https://www.ltu.se");
        getWebDriver().manage().window().maximize();


        if ($(By.id("CybotCookiebotDialogBodyLevelButtonLevelOptinAllowAll")).isDisplayed()) {
            $(By.id("CybotCookiebotDialogBodyLevelButtonLevelOptinAllowAll")).click();
        }


        // Click on the Student link
        $(By.xpath("//a[text()='Student']")).click();

        // Click on the "Studera" link
        Selenide.$("a[href='#'][onclick*='Studera']").click();

        // Click on the "Registerutdrag" link
        Selenide.$(byAttribute("href", "/student/Studera/Resultat/Registerutdrag-1.160158")).click();

        // Click on the "Registerutdrag" link
        Selenide.$("a[href='https://student.ladok.se']").click();

        String originalHandle = getWebDriver().getWindowHandle();
        for(String handle : getWebDriver().getWindowHandles()) {
            if (!handle.equals(originalHandle)) {
                getWebDriver().switchTo().window(handle);
                break;
            }
        }

        // Click on the "Inloggning via ditt lärosäte" element
        Selenide.$(byText("Inloggning via ditt lärosäte")).click();

        // Click on the "searchinput" element
        Selenide.$(byId("searchinput")).sendKeys("luleå tekniska universitet");

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

        } catch (IOException e) {
            e.printStackTrace();
        }

        $(By.name("username")).val(email);
        $(By.name("password")).val(password);

        // Click on the login button
        $(By.name("submit")).click();

        $(By.linkText("Intyg")).click();

        // Click on the Student Transcript
        $(By.linkText("Registreringsintyg")).click();

        sleep(5000);

        Configuration.reportsFolder = "target";
        String[] subfolders = new File(Configuration.reportsFolder).list();
        int length = subfolders.length;
        String subfolderName = subfolders[length - 6];
        String downloadDirectory = Configuration.reportsFolder + File.separator + subfolderName;
        String fileName = "intyg.pdf";
        File downloadedFile = new File(downloadDirectory, fileName);
        String dlloc = null;

        if (downloadedFile.exists() && !downloadedFile.isDirectory()) {
            dlloc = downloadedFile.getAbsolutePath();
        }

        return dlloc;

    }

}
