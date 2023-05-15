import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.*;

class SyllabusMethodTest {

    @Test
    void syllabusTest() throws IOException, InterruptedException {
        SyllabusDownload test = new SyllabusDownload();
        String filePathString2 = test.SyllabusMethod();
        Path filePath = Paths.get(filePathString2);


        if (Files.exists(filePath) && !Files.isDirectory(filePath) && filePath.getFileName().toString().equals("Utbildningsplan_FKSYG.pdf")) {
            System.out.println("Success! The file " + filePathString2 + " exists.");
        } else {
            System.out.println("The file " + filePathString2 + " does not exist.");
        }
    }

}