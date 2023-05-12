import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FinalDateTest {

    @Test
    void dateTesting() throws IOException {

        FinalDate test = new FinalDate();
        assertEquals("2023-05-30 09:00 - 14:00", test.finalDate());

    }

}