
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ex.ElementNotFound;
import java.io.File;


import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

import java.io.File;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static com.codeborne.selenide.Selenide.executeJavaScript;

public class SyllabusDownload {


    public static String SyllabusMethod () throws InterruptedException{

        String dlloce;

        String targetPath2 = System.getProperty("user.dir") + "/target";

        Configuration.downloadsFolder = targetPath2;

        System.setProperty("selenide.holdBrowserOpen", "true");

        Configuration.browser = "chrome";
        // open LTU Student website
        open("https://www.ltu.se/student");
        getWebDriver().manage().window().maximize();

        try {
            $( "#CybotCookiebotDialogBodyLevelButtonLevelOptinAllowAll").shouldBe( visible ).click();

        } catch (ElementNotFound e) {
            System.out.println("Count not find cookies");
        }

        try {

            $(".fa-search").click();

            $("#cludo-search-bar-input").sendKeys("Systemvetenskap");

            $(".is-medium.is-info").click();

            $(".programTitle").click();

            $(byText("Utbildningsplan")).click();

            $(byText("Utbildningsplan Antagna: Hösten 2023")).shouldBe(visible).click();


        } catch (ElementNotFound e) {
            System.out.println("Count not find Element");
        }




        try {
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

        } catch (NullPointerException e) {
            System.out.println("Error in file download");
            return null;
        }catch ( ArrayIndexOutOfBoundsException e){
            System.out.println("Error in file download");
            return null;
        }


    }

}


