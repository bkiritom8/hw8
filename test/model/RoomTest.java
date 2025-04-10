package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Test suite for the Room class.
 * Covers room creation, field access, exit handling, fixtures, items, puzzle/monster assignment,
 * and equality/hash code behavior.
 */
@DisplayName("Room Test Suite")
class RoomTest {

  /**
   * Helper method to create a Room using constructor with string-based fields and exits.
   */
  private Room createRoom(String name, String roomNumber, String description,
                          String north, String south, String east, String west,
                          String field1, String field2, String itemsField,
                          String field3, String picture) {
    Map<Direction, String> exits = new HashMap<>();
    exits.put(Direction.NORTH, north);
    exits.put(Direction.SOUTH, south);
    exits.put(Direction.EAST, east);
    exits.put(Direction.WEST, west);
    return new Room(name,
            roomNumber,
            description,
            exits,
            field1,
            field2,
            itemsField,
            field3,
            picture);
  }

  /**
   * Helper for extended Room constructor.
   */
  private Room createRoomExtended(String name, String roomNumber, String description,
                                  String north, String south, String east, String west,
                                  String field1, String field2, String itemsField,
                                  String field3, String picture) {
    Map<Direction, String> exits = new HashMap<>();
    exits.put(Direction.NORTH, north);
    exits.put(Direction.SOUTH, south);
    exits.put(Direction.EAST, east);
    exits.put(Direction.WEST, west);
    return new Room(name, roomNumber, description, exits, field1, field2,
            itemsField, field3, picture);
  }

  /**
   * Tests basic Room creation and field getters.
   */
  @Test
  @DisplayName("Test Room Creation and Getters")
  void testRoomCreationAndGetters() {
    Room room = createRoom("Hallway 1", "1", "A long hallway.",
            "2", "0", "0", "0", "", "", "", "", "");
    assertEquals("Hallway 1", room.getRoomName());
    assertEquals("1", room.getRoomNumber());
    assertEquals("A long hallway.", room.getDescription());
    assertEquals(2, room.getNorth());
    assertEquals(0, room.getSouth());
    assertEquals(0, room.getEast());
    assertEquals(0, room.getWest());
    assertTrue(room.getFixtureList().isEmpty());
  }

  /**
   * Tests adding and removing a fixture from a Room.
   */
  @Test
  @DisplayName("Test Adding and Removing Fixtures")
  void testAddAndRemoveFixture() {
    Room room = createRoom("Hallway 2", "2", "Another hallway.",
            "3", "1", "0", "0", "", "", "", "", "");
    Fixture fixture = new Fixture("Computer", 1000,
            "A computer with a password screen active.");
    room.addFixture(fixture);
    assertTrue(room.getFixtureList().contains(fixture));
    assertTrue(room.removeFixture(fixture));
    assertFalse(room.getFixtureList().contains(fixture));
  }

  /**
   * Tests handling multiple fixtures in a Room.
   */
  @Test
  @DisplayName("Test Multiple Fixtures in a Room")
  void testMultipleFixtures() {
    Room room = createRoom("Exhibit", "3", "A museum exhibit.",
            "0", "0", "0", "0", "", "", "", "", "");
    room.addFixture(new Fixture("Desk", 250, "A desk."));
    room.addFixture(new Fixture("Bookshelf", 300, "A bookshelf."));
    assertEquals(2, room.getFixtureList().size());
  }

  /**
   * Tests adding and removing items from a Room.
   */
  @Test
  @DisplayName("Test Adding and Removing Items")
  void testAddRemoveItems() {
    Room room = createRoom("Storage",
            "5",
            "A dusty storage room.",
            "0",
            "0",
            "0",
            "0",
            "",
            "",
            "",
            "",
            "");
    Item item = new Item("Lantern",
            2,
            3,
            3,
            10,
            "Shines bright",
            "An old lantern.");
    room.addItem(item);
    assertEquals(item, room.getItem("Lantern"));
    room.removeItem(item);
    assertTrue(room.getItems().isEmpty());
  }

  /**
   * Tests assigning a puzzle and a monster to a Room.
   */
  @Test
  @DisplayName("Test Puzzle and Monster Assignment")
  void testPuzzleAndMonsterAssignment() {
    Room room = createRoom("Chamber",
            "8",
            "A sealed chamber",
            "0",
            "0",
            "0",
            "0",
            "",
            "",
            "",
            "",
            "");
    Puzzle puzzle = new Puzzle("Lock",
            true,
            true,
            false,
            "key",
            25,
            "A tricky lock.",
            "",
            "");
    Monster monster = new Monster("Goblin",
            "Tiny and aggressive",
            true,
            15,
            true,
            "Slash!",
            "",
            50,
            "Sword",
            "");
    room.setPuzzle(puzzle);
    room.setMonster(monster);
    assertEquals(puzzle, room.getPuzzle());
    assertEquals(monster, room.getMonster());
  }

  /**
   * Tests setting and getting a Room exit.
   */
  @Test
  @DisplayName("Test Room Exit Assignment with setExit and getExit")
  void testSetAndGetExit() {
    Room room1 = createRoom("One", "1", "Room One", "0", "0", "0", "0", "", "", "", "", "");
    Room room2 = createRoom("Two", "2", "Room Two", "0", "0", "0", "0", "", "", "", "", "");
    room1.setExit(Direction.EAST, room2);
    assertEquals(room2, room1.getExit(Direction.EAST));
  }

  /**
   * Tests toString() returns a non-null string.
   */
  @Test
  @DisplayName("Test toString")
  void testToString() {
    Room room = createRoom("Hallway", "1", "A long hallway.",
            "2", "0", "0", "0", "", "", "", "", "");
    assertNotNull(room.toString());
  }

  /**
   * Tests that equals() returns true for rooms with identical fields.
   */
  @Test
  @DisplayName("Test equals")
  void testEquals() {
    Room room1 = createRoom("Hallway", "1", "A long hallway.",
            "2", "0", "0", "0", "", "", "", "", "");
    Room room2 = createRoom("Hallway", "1", "A long hallway.",
            "2", "0", "0", "0", "", "", "", "", "");
    assertEquals(room1, room2);
  }

  /**
   * Tests that hashCode() returns the same value for equal rooms.
   */
  @Test
  @DisplayName("Test hashCode")
  void testHashCode() {
    Room room1 = createRoom("Hallway", "1", "A long hallway.",
            "2", "0", "0", "0", "", "", "", "", "");
    Room room2 = createRoom("Hallway", "1", "A long hallway.",
            "2", "0", "0", "0", "", "", "", "", "");
    assertEquals(room1.hashCode(), room2.hashCode());
  }

  // Additional simple getter tests like getNorth(), getSouth(), etc.,
  // are already well named and self-explanatory per JUnit standard.
}