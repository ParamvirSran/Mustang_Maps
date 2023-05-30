package uwo.team33;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MCControllerTest {
    MCController controller;

    @BeforeEach
    void setUp() {
        controller = new MCController();
    }

    @Test
    void testGetPOIs() {
        ArrayList<JSONObject> pois = null;
        try {
            pois = Utility.getPOIs("Middlesex College", 0, null);
        } catch (Exception e) {
            fail("Unexpected exception thrown");
        }
        assertNotNull(pois);
        assertTrue(pois.size() > 0);
    }

}
