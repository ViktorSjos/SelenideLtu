import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class getIntygTest {

        @Test
        void dateTesting() throws IOException {
        IntygDownload test = new IntygDownload();

        Path path = Path.of("/path/to/your/file.txt");
        assertTrue(Files.exists(path), "File does not exist at path: " + path);


    }

}