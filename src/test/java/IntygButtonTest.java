import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class IntygButtonTest {

    @Test
    void IntygButtonTest() throws IOException, InterruptedException {
        IntygButton test = new IntygButton();
        assertEquals("Skapa intyg", test.GetButton());

    }
}