package uwo.team33;

import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Ignore
public class POITest {

    POI poi;

    @BeforeEach
    public void setUp() throws Exception {
        poi = new POI("Test POI", "This is a test POI", Layer.Classroom, new double[]{0.5, 0.5});
    }

    @Test
    public void testGetName() {
        assertEquals("Test POI", poi.getName());
    }

    @Test
    public void testSetName() {
        poi.setName("New Test POI");
        assertEquals("New Test POI", poi.getName());
    }

    @Test
    public void testGetDescription() {
        assertEquals("This is a test POI", poi.getDescription());
    }

    @Test
    public void testSetDescription() {
        poi.setDescription("This is a new test POI");
        assertEquals("This is a new test POI", poi.getDescription());
    }

    @Test
    public void testGetLayer() {
        assertEquals(Layer.Classroom, poi.getLayer());
    }

    @Test
    public void testSetLayer() {
        poi.setLayer(Layer.CollaborativeRoom);
        assertEquals(Layer.CollaborativeRoom, poi.getLayer());
    }

    @Test
    public void testGetLocation() {
        assertArrayEquals(new double[]{0.5, 0.5}, poi.getLocation(), 0.0);
    }

    @Test
    public void testSetLocation() {
        poi.setLocation(new double[]{0.6, 0.6});
        assertArrayEquals(new double[]{0.6, 0.6}, poi.getLocation(), 0.0);
    }

    @Test
    public void testGetBuilding() {
        assertEquals("", poi.getBuilding());
    }

    @Test
    public void testSetBuilding() {
        poi.setBuilding("Natural Sciences");
        assertEquals("Natural Sciences", poi.getBuilding());
    }

    @Test
    public void testGetFloor() {
        assertEquals(-1, poi.getFloor());
    }

    @Test
    public void testSetFloor() {
        poi.setFloor(2);
        assertEquals(2, poi.getFloor());
    }

}