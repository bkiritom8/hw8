package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Test class for the Player class.
 */
class PlayerTest {

  private Room testRoom;
  private Room nextRoom;
  private Item testItem;
  private Monster testMonster;

  @BeforeEach
  void setUp() {
    // Create test rooms with connections
    Map<Direction, String> exits1 = new HashMap<>();
    exits1.put(Direction.NORTH, "R2");
    
    Map<Direction, String> exits2 = new HashMap<>();
    exits2.put(Direction.SOUTH, "R1");
    
    testRoom = new Room("Test Room", "R1", "A test room for testing", exits1, 
                        "field1", "field2", "items", "field3", "");
    nextRoom = new Room("Next Room", "R2", "The next room", exits2,
                        "field1", "field2", "items", "field3", "");

    // Connect the rooms
    testRoom.setExit(Direction.NORTH, nextRoom);
    nextRoom.setExit(Direction.SOUTH, testRoom);

    // Create a test item
    testItem = new Item("Test Item", 2, 3, 3, 10, "Used!", "A test item");
    
    // Create test monster
    testMonster = new Monster("Test Monster", "Standard monster for testing", true, 50, true,
            "Test Attack", "Test Effects", 100, "Test Solution", "Test Target");
  }

  @Test
  void testConstructor() {
    Player player = new Player(testRoom);
    assertEquals("Player", player.getName());
    assertEquals(100, player.getHealth());
    assertEquals(0, player.getScore());
    assertTrue(player.getInventory().isEmpty());
  }

  @Test
  void testConstructorWithNullRoom() {
    assertThrows(IllegalArgumentException.class, () -> new Player(null));
  }

  @Test
  void testSetHealth() {
    Player player = new Player(testRoom);

    // Normal health setting
    player.setHealth(50);
    assertEquals(50, player.getHealth());

    // Health below 0 should be set to 0
    player.setHealth(-10);
    assertEquals(0, player.getHealth());

    // Health above MAX_HEALTH should be capped
    player.setHealth(1000);
    assertEquals(100, player.getHealth());
  }

  @Test
  void testTakeDamage() {
    Player player = new Player(testRoom);

    // Normal damage
    player.takeDamage(30);
    assertEquals(70, player.getHealth());

    // Damage that would reduce health below 0
    player.takeDamage(100);
    assertEquals(0, player.getHealth());

    // Negative damage should throw exception
    assertThrows(IllegalArgumentException.class, () -> player.takeDamage(-10));
  }

  @Test
  void testGetHealthStatus() {
    Player player = new Player(testRoom);

    // Full health
    assertEquals("AWAKE", player.getHealthStatus());

    // Fatigued range
    player.setHealth(60);
    assertEquals("FATIGUED", player.getHealthStatus());

    // Woozy range
    player.setHealth(30);
    assertEquals("WOOZY", player.getHealthStatus());

    // Dead/Asleep
    player.setHealth(0);
    assertEquals("ASLEEP", player.getHealthStatus());
  }

  @Test
  void testInventoryManagement() {
    Player player = new Player(testRoom);

    // Add item to inventory
    assertTrue(player.addToInventory(testItem));
    assertEquals(1, player.getInventory().size());
    assertEquals(testItem, player.getInventory().get(0));

    // Add null item
    assertThrows(IllegalArgumentException.class, () -> player.addToInventory(null));

    // Test inventory weight
    assertEquals(testItem.getWeight(), player.getInventoryWeight());

    // Test weight limit
    Item heavyItem = new Item("Heavy Item", 20, 1, 1, 5, "Heavy!", "A very heavy item");
    assertFalse(player.addToInventory(heavyItem));

    // Remove item
    assertTrue(player.removeFromInventory(testItem));
    assertTrue(player.getInventory().isEmpty());

    // Remove non-existent item
    assertFalse(player.removeFromInventory(testItem));

    // Remove null item
    assertThrows(IllegalArgumentException.class, () -> player.removeFromInventory(null));
  }

  @Test
  void testGetItemFromInventory() {
    Player player = new Player(testRoom);
    player.addToInventory(testItem);

    // Find existing item
    Item found = player.getItemFromInventory("Test Item");
    assertEquals(testItem, found);

    // Find non-existent item
    assertNull(player.getItemFromInventory("Non-existent Item"));

    // Find with null/empty name
    assertThrows(IllegalArgumentException.class, () -> player.getItemFromInventory(null));
    assertThrows(IllegalArgumentException.class, () -> player.getItemFromInventory(""));
    assertThrows(IllegalArgumentException.class, () -> player.getItemFromInventory("  "));
  }

  @Test
  void testSetInventory() {
    Player player = new Player(testRoom);

    // Create a new inventory
    List<Item> newInventory = new ArrayList<>();
    newInventory.add(testItem);

    // Set the inventory
    player.setInventory(newInventory);
    assertEquals(1, player.getInventory().size());
    assertEquals(testItem, player.getInventory().get(0));

    // Set null inventory
    assertThrows(IllegalArgumentException.class, () -> player.setInventory(null));
  }

  @Test
  void testRoomManagement() {
    Player player = new Player(testRoom);

    // Test current room
    assertEquals(testRoom, player.getCurrentRoom());

    // Test setting room
    player.setCurrentRoom(nextRoom);
    assertEquals(nextRoom, player.getCurrentRoom());

    // Test setting null room
    assertThrows(IllegalArgumentException.class, () -> player.setCurrentRoom(null));
  }

  @Test
  void testMovement() {
    Player player = new Player(testRoom);

    // Test can move
    assertTrue(player.canMove(Direction.NORTH));
    assertFalse(player.canMove(Direction.EAST));

    // Test null direction for canMove
    assertThrows(IllegalArgumentException.class, () -> player.canMove(null));

    // Test move
    assertTrue(player.move(Direction.NORTH));
    assertEquals(nextRoom, player.getCurrentRoom());

    // Test move in invalid direction
    assertFalse(player.move(Direction.EAST));

    // Test null direction for move
    assertThrows(IllegalArgumentException.class, () -> player.move(null));
  }

  @Test
  void testScoreManagement() {
    Player player = new Player(testRoom);

    // Test initial score
    assertEquals(0, player.getScore());

    // Test adding score
    player.addScore(100);
    assertEquals(100, player.getScore());

    // Test adding negative score
    assertThrows(IllegalArgumentException.class, () -> player.addScore(-10));

    // Test setting score
    player.setScore(500);
    assertEquals(500, player.getScore());

    // Test setting negative score
    assertThrows(IllegalArgumentException.class, () -> player.setScore(-10));
  }

  @Test
  void testGetRank() {
    Player player = new Player(testRoom);

    // Test ranks at different score levels
    assertEquals("Beginner", player.getRank());

    player.setScore(250);
    assertEquals("Novice Explorer", player.getRank());

    player.setScore(500);
    assertEquals("Seasoned Adventurer", player.getRank());

    player.setScore(750);
    assertEquals("Expert Explorer", player.getRank());

    player.setScore(1000);
    assertEquals("Adventure Master", player.getRank());
  }

  @Test
  void testAttack() {
    Player player = new Player(testRoom);

    // Test successful attack
    int damage = player.attack(testMonster);
    assertTrue(damage > 0);
    
    // We can't check monster's health directly because getHealth() is undefined in Monster
    // Instead, check that the player's attack returns a positive damage value

    // Test attack null monster
    assertThrows(IllegalArgumentException.class, () -> player.attack(null));
  }
}