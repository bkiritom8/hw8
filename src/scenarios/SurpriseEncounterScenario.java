package scenarios;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import enginedriver.GameEngineApp;
import model.*;

/**
 * Scenario 7: Implements a test scenario where a player encounters what they think is a fixture,
 * but is actually a monster. This monster is also a special monster that cannot be defeated because
 * of its unique outer build. However, it is possible to drop a specific item in front of it, which
 * will cause the monster to pick it and drop another item for the player to pick. This item is a
 * Chamber Compass, a rare item, that can be used to navigate to more Treasure Chambers like this
 * located outside the town within the same country.
 */
public class SurpriseEncounterScenario {
  private final String jsonData = """
          {
            "name": "A Surprising Encounter Scenario",
            "version": "1.0",
            "rooms": [
              {
                "room_name": "Dungeon Room",
                "room_number": "1",
                "description": "A dark dungeon with damp walls. The exit is to the south.",
                "N": "0", "S": "-3", "E": "0", "W": "1",
                "monster": ""
              },
              {
                "room_name": "Treasure Chamber",
                "room_number": "2",
                "description": "A chamber filled with gold and jewels.",
                "N": "2", "S": "0", "E": "0", "W": "0",
                "monster": "Treasure Chest"
                "items": ["Chamber Compass"]
              },
              {
                "room_name": "Library Basement",
                "room_number": "3",
                "description": "A messy room with damaged books and painted walls.",
                "N": "0", "S": "0", "E": "1", "W": "0",
                "items": [""]
              }
            ],
            "items": [
              {
                "name": "Magic Sword",
                "weight": "3",
                "max_uses": "3",
                "uses_remaining": "1",
                "value": "10",
                "when_used": "You swing the magic sword with great effect!",
                "description": "A gleaming sword with magical runes on the blade."
              },
              {
                "name": "Luminous Pearl",
                "weight": "1",
                "max_uses": "1",
                "uses_remaining": "1",
                "value": "20",
                "when_used": "The pearl begins to glow in front of the Chest Monster.",
                "description": "A luminous pearl with an unknown effect."
              }
            ],
            "monsters": [
              {
                "name": "Chest Monster",
                "active": "true",
                "damage": "30",
                "can_attack": "true",
                "attack_description": "The Chest Monster forcefully dashes onto you and paralyzes you!",
                "description": "A monster that looks like a treasure chest.",
                "effects": "The Chest Monster is reacting to an item in the inventory.",
                "value": "300",
                "solution": "Luminous Pearl",
                "target": "2:Treasure Chamber"
              }
            ]
          }""";

  /**
   * Runs the monster encounter scenario with predefined commands.
   * Creates a temporary JSON game file, executes the commands through the game engine,
   * and verifies the expected outcomes.
   *
   * @throws IOException If there is an error creating or writing to the temporary file
   */
  public void run() throws IOException {
    // Write the scenario data to a temporary file
    File tempFile = File.createTempFile("monster_scenario", ".json");
    try (FileWriter writer = new FileWriter(tempFile)) {
      writer.write(jsonData);
    }

    // Commands to execute the scenario
    String commands = """
            Player
            s  // go deeper inside the Dungeon
            s  // go down the stairs
            e  // only one path after exiting stairs, which is east
            n  // enter the Treasure Chamber
            l  // look around inside the Chamber
            w  // spots an interesting looking treasure chest, so moves west towards it
            x Treasure Chest  // inspect the treasure chest
            // Chest is actually a monster!
            k  // attacks the monster with sword, but to no effect
            i  // player notices monster is reacting to an item in inventory, so searches for the item
            d Luminous Pearl  // drops the pearl in front of the monster
            x Treasure Chest  // monster drops something, player examines it
            t Chamber Compass  // picks up item, acquires a Chamber Compass
            e  // moves back to the previous position (middle of the chamber)
            l  // looks around for items
            n // moves towards the treasure area of the chamber
            t Treasure  // picks up treasure
            l  // hears something and looks at it
            e  // finds an exit that leads back to the library basement
            q  // exit game
            """;

    StringReader input = new StringReader(commands);
    StringWriter output = new StringWriter();

    // Run the game with our scenario
    GameEngineApp app = new GameEngineApp(tempFile.getAbsolutePath(), input, output);
    app.start();

    // Print the output to see what happened
    String result = output.toString();
    System.out.println("=== Monster Scenario Output ===");
    System.out.println(result);

    // Clean up
    tempFile.delete();

    // Verify the scenario outcomes
    verifyScenarioOutcomes(result);
  }

  /**
   * Verifies that the scenario played out as expected by checking the output text.
   * Looks for specific indicators of success or failure in the game output.
   *
   * @param output The text output from the game run
   */
  private void verifyScenarioOutcomes(String output) {
    boolean success = true;

    // Check if the player calmed the Chest Monster
    if (!output.contains("The Chest picks up the Pearl and drops another item!")) {
      System.out.println("FAIL: Player didn't drop the Pearl!");
      success = false;
    }

    // Check if the player's score increased
    if (!output.contains("Score: 300")) {
      System.out.println("FAIL: Player's score didn't increase after calming the Chest Monster");
      success = false;
    }

    // Check if the player could move West after calming the Chest Monster
    if (!output.contains("Treasure Chamber")) {
      System.out.println("FAIL: Player couldn't unlock the magical door leading to the library " +
              "basement after calming the Chest Monster");
      success = false;
    }

    if (success) {
      System.out.println("SUCCESS: Monster scenario completed successfully!");
    }
  }

  /**
   * Main method to run this scenario independently for testing purposes.
   *
   * @param args Command line arguments (not used)
   */
  public static void main(String[] args) {
    try {
      new TrollDefeatScenario().run();
    } catch (IOException e) {
      System.out.println("Error running monster scenario: " + e.getMessage());
    }
  }
}
