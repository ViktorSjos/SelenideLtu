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

public class SyllabusDownload {

    public static String SyllabusMethod () throws InterruptedException{

        String dlloce;

        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);

        String targetPath2 = System.getProperty("user.dir") + "/target";

        Configuration.downloadsFolder = targetPath2;

        System.setProperty("selenide.holdBrowserOpen", "true");
        System.setProperty("chromeoptions.args", "--headless");

        Configuration.browser = "chrome";
        // open LTU Student website
        open("https://www.ltu.se/student");
        getWebDriver().manage().window().maximize();

        if ($(By.id("CybotCookiebotDialogBodyLevelButtonLevelOptinAllowAll")).isDisplayed()) {
            $(By.id("CybotCookiebotDialogBodyLevelButtonLevelOptinAllowAll")).click();
        }

        $(By.className("fa-search")).click();

        $(By.id("cludo-search-bar-input")).sendKeys("Systemvetenskap");

        $(".is-medium.is-info").click();

        $(By.xpath("//h2[contains(text(),'Systemvetenskap, kandidat, 180 högskolepoäng')]")).click();

        $(By.xpath("//div[contains(text(),'Utbildningsplan')]")).click();

        $(By.xpath("//div[contains(text(),'Utbildningsplan')]")).click();



        Configuration.reportsFolder = "target";
        String[] subfolders = new File(Configuration.reportsFolder).list();
        int length = subfolders.length;
        String subfolderName2 = subfolders[length - 6];

        // download the file


        String targetPath3 = System.getProperty("user.dir") + "/target";
        String filePath = (targetPath3+File.separator+subfolderName2+"/Utbildningsplan_FKSYG.pdf");
        File downloadedFile = new File(filePath);

        sleep(8000);

        if (downloadedFile != null) {
            dlloce = downloadedFile.getAbsolutePath();
        } else {
            dlloce = "File not found";
        }

        return dlloce;
    }
}


