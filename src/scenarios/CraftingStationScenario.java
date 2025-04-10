package scenarios;

import model.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Scenario 4: Activating a Crafting Station.
 * The player enters a cave and discovers a Crafting Station made of wood and stones.
 * The station is inactive until the player has both Iron and Diamond.
 * When the player interacts with the station while holding both items,
 * the station consumes them and crafts a Diamond Sword.
 */
public class CraftingStationScenario {

  // Placeholder fields to simulate parameters used when constructing the room
  public static int field1;
  public static int field2;
  public static int itemsField;
  public static String field3;
  public static String picture;

  /**
   * Demonstrates the crafting station scenario, including inventory checks and item interaction.
   *
   * @param args command-line arguments (not used)
   */
  public static void main(String[] args) {
    // Step 1: Create a dummy room as the required starting point for the player
    Map<Direction, String> defaultExits = new HashMap<>();
    defaultExits.put(Direction.NORTH, "0");
    defaultExits.put(Direction.SOUTH, "0");
    defaultExits.put(Direction.EAST, "0");
    defaultExits.put(Direction.WEST, "0");

    Room startingRoom = new Room(
            "Cave Entrance",
            "0",
            "A dark cave entrance.",
            defaultExits,
            field1,
            field2,
            itemsField,
            0, field3,
            picture
    );

    // Step 2: Set up the player with a starting room
    Player player = new Player(startingRoom);
    System.out.println("You enter a dark cave and discover a strange structure made of wood and stone.");

    // Step 3: Set up the crafting station fixture
    Fixture craftingStation = new Fixture(
            "Crafting Station",
            0,
            "A crafting station that is inactive until you have both Iron and Diamond."
    );

    // Step 4: Show current inventory
    System.out.println("Current Inventory: " + player.getInventory());

    // Step 5: Try interacting with the station without required items
    System.out.println("You approach the crafting station...");
    System.out.println(craftingStation.interact(player));

    // Step 6: Add required items to the player's inventory
    Item iron = new Item("Iron", 1, 1, 1, 10, "Shiny and metallic.", "Useful for crafting.");
    player.addToInventory(iron);
    Item diamond = new Item("Diamond", 1, 1, 1, 100, "A sparkling gem.", "Valuable and sharp.");
    player.addToInventory(diamond);
    System.out.println("\nYou found Iron and Diamond!");

    // Step 7: Show updated inventory
    System.out.println("Current Inventory: " + player.getInventory());

    // Step 8: Try interacting again with required items
    System.out.println("\nYou try using the crafting station again...");
    System.out.println(craftingStation.interact(player));

    // Step 9: Final inventory check
    System.out.println("Final Inventory: " + player.getInventory());
  }
}