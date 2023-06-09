import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

import java.io.File;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;


public class IntygButton {

    public static String GetButton() throws InterruptedException, FileNotFoundException {



        System.setProperty("selenide.holdBrowserOpen", "true");


        Configuration.browser = "chrome";
        // open LTU website
        open("https://www.ltu.se");
        getWebDriver().manage().window().maximize();


        try {
            $( "#CybotCookiebotDialogBodyLevelButtonLevelOptinAllowAll").shouldBe( visible ).click();

        } catch (ElementNotFound e) {
            System.out.println("Count not find cookies");
        }

        try {
            // Click on the Student link
            $(byXpath("//a[text()='Student']")).click();

            // Click on the "Studera" link
            Selenide.$("a[href='#'][onclick*='Studera']").click();

            // Click on the "Registerutdrag" link
            Selenide.$(byAttribute("href", "/student/Studera/Resultat/Registerutdrag-1.160158")).click();

            // Click on the "Registerutdrag" link
            Selenide.$("a[href='https://student.ladok.se']").click();

            String originalHandle = getWebDriver().getWindowHandle();
            for (String handle : getWebDriver().getWindowHandles()) {
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
            SelenideElement institutionElement = $("div.institution-text");

            // click on the element
            institutionElement.click();

        } catch (ElementNotFound e) {
            System.out.println("Count not find Element");
        }


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


        try {
            $("[name='username']").val(email);
            $("[name='password']").val(password);

            // Click on the login button
            $("[name='submit']").click();

            $(byText("Intyg")).click();

            $(byClassName("btn-ladok-brand")).click();


            $("#intygstyp").click();

            $("#intygstyp").selectOptionByValue("1: Object");

            String buttonText = $(".btn.btn-ladok-brand.text-nowrap.me-lg-3").getText();
            String webtext = $("html").getText();
            String[] lines2 = webtext.split("\n");

            $(byText("Logga ut")).click();
            return lines2[13];
        } catch (ElementNotFound e) {
            System.out.println("Count not find Element");
            return null;
        }

    }
}

