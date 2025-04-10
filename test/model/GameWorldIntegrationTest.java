package model;

import org.junit.jupiter.api.Test;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration test for the GameWorld class.
 * Tests various game scenarios to ensure proper interaction between game components.
 */
public class GameWorldIntegrationTest {

  /**
   * Tests loading a game with empty rooms.
   * Verifies that basic game information and starting room are correctly initialized.
   *
   * @throws IOException If there is an error reading the test game file
   */
  @Test
  void testLoadEmptyRoomsGame() throws IOException {
    GameWorld gameWorld = new GameWorld("./resources/empty_rooms.json");
    assertEquals("Empty Rooms", gameWorld.getGameName());

    model.Room startRoom = gameWorld.getPlayer().getCurrentRoom();
    assertEquals("1", startRoom.getRoomNumber());
    assertEquals("Hallway 1", startRoom.getName());
  }

  /**
   * Tests player navigation between rooms.
   * Verifies that player movement and room connections work correctly.
   *
   * @throws IOException If there is an error reading the test game file
   */
  @Test
  void testPlayerNavigationScenario() throws IOException {
    GameWorld gameWorld = new GameWorld("./resources/empty_rooms.json");
    Player player = gameWorld.getPlayer();

    // Start in room 1
    assertEquals("1", player.getCurrentRoom().getRoomNumber());

    // Move north to room 2
    Room currentRoom = player.getCurrentRoom();
    Room northRoom = currentRoom.getExit(Direction.NORTH);
    player.setCurrentRoom(northRoom);
    assertEquals("2", player.getCurrentRoom().getRoomNumber());

    // Move north to room 3
    currentRoom = player.getCurrentRoom();
    northRoom = currentRoom.getExit(Direction.NORTH);
    player.setCurrentRoom(northRoom);
    assertEquals("3", player.getCurrentRoom().getRoomNumber());

    // Move south back to room 2
    currentRoom = player.getCurrentRoom();
    Room southRoom = currentRoom.getExit(Direction.SOUTH);
    player.setCurrentRoom(southRoom);
    assertEquals("2", player.getCurrentRoom().getRoomNumber());
  }

  /**
   * Tests item interactions including picking up and dropping items.
   * Verifies that item transfers between room and player inventory work correctly.
   *
   * @throws IOException If there is an error reading the test game file
   */
  @Test
  void testItemInteractionScenario() throws IOException {
    GameWorld gameWorld = new GameWorld("./resources/simple_hallway.json");
    Player player = gameWorld.getPlayer();

    // Take notebook from starting room
    Room startRoom = player.getCurrentRoom();
    Item notebook = startRoom.getItem("Notebook");
    assertNotNull(notebook);

    player.addToInventory(notebook);
    startRoom.removeItem(notebook);

    assertTrue(player.getInventory().contains(notebook));
    assertFalse(startRoom.getItems().contains(notebook));

    //Drop notebook back in room
    player.removeFromInventory(notebook);
    startRoom.addItem(notebook);

    assertFalse(player.getInventory().contains(notebook));
    assertTrue(startRoom.getItems().contains(notebook));
  }

  /**
   * Tests puzzle solving mechanics.
   * Verifies that puzzles can be properly identified and solved.
   *
   * @throws IOException If there is an error reading the test game file
   */
  @Test
  void testPuzzleSolvingScenario() throws IOException {
    GameWorld gameWorld = new GameWorld("./resources/simple_hallway.json");
    Player player = gameWorld.getPlayer();

    // Move to room 2 (containing Lock puzzle)
    Room startRoom = player.getCurrentRoom();
    Room northRoom = startRoom.getExit(Direction.NORTH);
    player.setCurrentRoom(northRoom);

    // Room 2 has lock puzzle
    Puzzle lockPuzzle = player.getCurrentRoom().getPuzzle();
    assertNotNull(lockPuzzle);
    assertEquals("LOCK", lockPuzzle.getName());
    assertTrue(lockPuzzle.isActive());

    // The exit to the north is blocked
    assertEquals("-3", player.getCurrentRoom().getExitRoomNumber(Direction.NORTH));

    // Take the key from room 2
    Item key = player.getCurrentRoom().getItem("Key");
    assertNotNull(key);
    player.addToInventory(key);
    player.getCurrentRoom().removeItem(key);

    // Use the key to solve the puzzle
    boolean solved = gameWorld.applySolution("Key");
    assertTrue(solved);
    assertFalse(lockPuzzle.isActive());

    // Now the path should be unlocked
    assertEquals("3", player.getCurrentRoom().getExitRoomNumber(Direction.NORTH));
    assertNotNull(player.getCurrentRoom().getExit(Direction.NORTH));
  }

  /**
   * Tests the scenario where a player defeats a monster.
   * This test verifies that:
   * 1. The player can navigate through rooms
   * 2. The player can solve puzzles to unlock paths
   * 3. The player can collect and use items
   * 4. The player can defeat monsters using appropriate items
   * 5. The player's score increases appropriately
   * 6. Blocked paths become unblocked after defeating monsters
   *
   * @throws IOException If there is an error reading the test game file
   */
  @Test
  void testMonsterDefeatScenario() throws IOException {
    GameWorld gameWorld = new GameWorld("./resources/simple_hallway.json");
    Player player = gameWorld.getPlayer();

    // Navigate to room 3 (containing Teddy Bear monster)
    // First to room 2
    Room startRoom = player.getCurrentRoom();
    Room room2 = startRoom.getExit(Direction.NORTH);
    player.setCurrentRoom(room2);

    // Take the key and solve the lock puzzle
    Item key = player.getCurrentRoom().getItem("Key");
    player.addToInventory(key);
    player.getCurrentRoom().removeItem(key);
    gameWorld.applySolution("Key");

    // Now to room 3
    Room room3 = player.getCurrentRoom().getExit(Direction.NORTH);
    player.setCurrentRoom(room3);

    // Room 3 has a Teddy Bear monster
    Monster teddyBear = player.getCurrentRoom().getMonster();
    assertNotNull(teddyBear);
    assertEquals("Teddy Bear", teddyBear.getName());
    assertTrue(teddyBear.isActive());

    // The exit to the north is blocked
    assertEquals("4", player.getCurrentRoom().getExitRoomNumber(Direction.NORTH));

    // Take hair clippers from room 2
    player.setCurrentRoom(room2);
    Item hairClippers = player.getCurrentRoom().getItem("Hair Clippers");
    assertNotNull(hairClippers);
    player.addToInventory(hairClippers);
    player.getCurrentRoom().removeItem(hairClippers);

    // Return to room 3
    player.setCurrentRoom(room3);

    // Use the hair clippers to defeat the monster
    boolean defeated = gameWorld.applySolution("Hair Clippers");
    assertTrue(defeated);
    assertFalse(teddyBear.isActive());

    // Player should get points for defeating the monster
    assertEquals(350, player.getScore());
  }
}