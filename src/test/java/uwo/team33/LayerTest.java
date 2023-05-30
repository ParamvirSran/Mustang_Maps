package uwo.team33;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LayerTest {

    @Test
    public void testToString() {
        assertEquals("Accessibility", Layer.Accessibility.toString());
        assertEquals("Classroom", Layer.Classroom.toString());
        assertEquals("Restaurant", Layer.Restaurant.toString());
        assertEquals("ComputerLab", Layer.ComputerLab.toString());
        assertEquals("CollaborativeRoom", Layer.CollaborativeRoom.toString());
        assertEquals("Library", Layer.Library.toString());
        assertEquals("Favourite", Layer.Favourite.toString());
        assertEquals("UserCreated", Layer.UserCreated.toString());
        assertEquals("General", Layer.General.toString());
    }

    @Test
    public void testValues() {
        Layer[] layers = Layer.values();
        assertEquals(9, layers.length);
        assertArrayEquals(new Layer[] { Layer.Accessibility, Layer.Classroom, Layer.Restaurant,
                Layer.ComputerLab, Layer.CollaborativeRoom, Layer.Library, Layer.Favourite, Layer.UserCreated,
                Layer.General }, layers);
    }
}