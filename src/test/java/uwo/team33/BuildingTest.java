package uwo.team33;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BuildingTest {

    @Test
    public void testGettersAndSetters() {
        // create a building with two floors
        Floor[] floors = new Floor[2];
        Building building = new Building("Test Building", floors);

        // test getters
        Assertions.assertEquals("Test Building", building.getName());
        Assertions.assertArrayEquals(floors, building.getFloors());
        Assertions.assertEquals(floors[0], building.getFloor(0));
        Assertions.assertEquals(floors[1], building.getFloor(1));
        Assertions.assertEquals(2, building.getNumFloors());

        // test setters
        Floor newFloor1 = new Floor();
        building.setFloor(0, newFloor1);
        Assertions.assertEquals(newFloor1, building.getFloor(0));

        Floor newFloor2 = new Floor();
        building.setFloor(1, newFloor2);
        Assertions.assertEquals(newFloor2, building.getFloor(1));

        String newName = "New Building Name";
        building.setName(newName);
        Assertions.assertEquals(newName, building.getName());

        Floor[] newFloors = new Floor[3];
        building.setFloors(newFloors);
        Assertions.assertArrayEquals(newFloors, building.getFloors());
        Assertions.assertEquals(3, building.getNumFloors());
    }
}
// This test case creates a Building object with two floors,
// and tests all the getters and setters to ensure that they work as expected.
// You can add more test cases as needed to ensure that the Building class
// functions correctly.

