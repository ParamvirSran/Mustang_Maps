package uwo.team33;

import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class UtilityTest {

    private static JSONObject poi;

    @BeforeAll
    static void setUp() {
        poi = new JSONObject();
        poi.put("name", "Test POI");
        poi.put("description", "Test POI Description");
        poi.put("floorNum", 1);
        poi.put("location", new double[] { 43.009857, -81.273625 });
        poi.put("layer", "Test Layer");
    }

    @Test
    void testGetPOIs() throws IOException, URISyntaxException {
        ArrayList<JSONObject> pois = Utility.getPOIs(null, null, null);
        Assertions.assertNotNull(pois);
    }

}
