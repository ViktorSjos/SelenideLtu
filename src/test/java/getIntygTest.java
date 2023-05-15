import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class getIntygTest {

        @Test
        void dateTesting() throws IOException, InterruptedException {
                IntygDownload test = new IntygDownload();
                String filePathString = test.getIntyg();
                Path filePath = Paths.get(filePathString);

                if (Files.exists(filePath) && !Files.isDirectory(filePath) && filePath.getFileName().toString().equals("intyg.pdf")) {
                        System.out.println("Success! The file " + filePathString + " exists.");
                } else {
                        System.out.println("The file " + filePathString + " does not exist.");
                }
        }
}


