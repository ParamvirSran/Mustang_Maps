package uwo.team33;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class FloorTest {

    @Test
    public void testFloorConstructor() {
        Floor floor = new Floor();
        assertEquals(-1, floor.getFloorNum());
        assertEquals("", floor.getImagePath());
        assertEquals(new ArrayList<POI>(), floor.getPointsList());
    }

    @Test
    public void testFloorConstructorWithParams() {
        ArrayList<POI> pointsList = new ArrayList<>();
        POI point = new POI("POI 1", null, null, null);
        pointsList.add(point);

        Floor floor = new Floor(0, "path/to/image", pointsList);
        assertEquals(0, floor.getFloorNum());
        assertEquals("path/to/image", floor.getImagePath());
        assertEquals(pointsList, floor.getPointsList());
    }

    @Test
    public void testSetters() {
        Floor floor = new Floor();
        floor.setFloorNum(1);
        assertEquals(1, floor.getFloorNum());

        floor.setImagePath("path/to/image");
        assertEquals("path/to/image", floor.getImagePath());

        ArrayList<POI> pointsList = new ArrayList<>();
        POI point = new POI("POI 1", null, null, null);
        pointsList.add(point);

        floor.setPointsList(pointsList);
        assertEquals(pointsList, floor.getPointsList());
    }

    @Test
    public void testAddAndRemovePoint() {
        Floor floor = new Floor();
        POI point = new POI("POI 1", null, null, null);
        floor.addPoint(point);
        assertEquals(point, floor.getPointsList().get(0));

        floor.removePoint(point);
        assertTrue(floor.getPointsList().isEmpty());
    }

}
