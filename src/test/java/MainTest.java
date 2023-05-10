import org.junit.jupiter.api.Test;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void mainTesting() throws IOException {
        var test = new Main();
        assertEquals("2023-05-30 09:00 - 14:00", test.finalDate());

    }

}