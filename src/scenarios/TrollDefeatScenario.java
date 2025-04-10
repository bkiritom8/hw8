package scenarios;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import enginedriver.GameEngineApp;
import model.*;

/**
 * Scenario 3: Implements a test scenario where a player encounters and defeats a monster.
 * This class creates a temporary game file with a troll encounter scenario, runs the game
 * with predefined commands, and verifies the expected outcomes.
 */
public class TrollDefeatScenario {
  private final String jsonData = "{\n"
          + "  \"name\": \"Monster Encounter Scenario\",\n"
          + "  \"version\": \"1.0\",\n"
          + "  \"rooms\": [\n"
          + "    {\n"
          + "      \"room_name\": \"Castle Hall\",\n"
          + "      \"room_number\": \"1\",\n"
          + "      \"description\": \"A grand hall with stone walls. There's a doorway to the east.\",\n"
          + "      \"N\": \"0\", \"S\": \"0\", \"E\": \"2\", \"W\": \"0\"\n"
          + "    },\n"
          + "    {\n"
          + "      \"room_name\": \"Dungeon Room\",\n"
          + "      \"room_number\": \"2\",\n"
          + "      \"description\": \"A dark dungeon with damp walls. The exit is to the south.\",\n"
          + "      \"N\": \"0\", \"S\": \"-3\", \"E\": \"0\", \"W\": \"1\",\n"
          + "      \"monster\": \"Troll\"\n"
          + "    },\n"
          + "    {\n"
          + "      \"room_name\": \"Treasure Chamber\",\n"
          + "      \"room_number\": \"3\",\n"
          + "      \"description\": \"A chamber filled with gold and jewels.\",\n"
          + "      \"N\": \"2\", \"S\": \"0\", \"E\": \"0\", \"W\": \"0\",\n"
          + "      \"items\": [\"Gold Coin\"]\n"
          + "    },\n"
          + "    {\n"
          + "      \"room_name\": \"Armory\",\n"
          + "      \"room_number\": \"4\",\n"
          + "      \"description\": \"A room filled with weapons and armor.\",\n"
          + "      \"N\": \"0\", \"S\": \"0\", \"E\": \"1\", \"W\": \"0\",\n"
          + "      \"items\": [\"Magic Sword\"]\n"
          + "    }\n"
          + "  ],\n"
          + "  \"items\": [\n"
          + "    {\n"
          + "      \"name\": \"Magic Sword\",\n"
          + "      \"weight\": \"3\",\n"
          + "      \"max_uses\": \"3\",\n"
          + "      \"uses_remaining\": \"3\",\n"
          + "      \"value\": \"10\",\n"
          + "      \"when_used\": \"You swing the magic sword with great effect!\",\n"
          + "      \"description\": \"A gleaming sword with magical runes on the blade.\"\n"
          + "    },\n"
          + "    {\n"
          + "      \"name\": \"Gold Coin\",\n"
          + "      \"weight\": \"1\",\n"
          + "      \"max_uses\": \"1\",\n"
          + "      \"uses_remaining\": \"1\",\n"
          + "      \"value\": \"5\",\n"
          + "      \"when_used\": \"The gold coin shimmers in the light.\",\n"
          + "      \"description\": \"A shiny gold coin with strange markings.\"\n"
          + "    }\n"
          + "  ],\n"
          + "  \"monsters\": [\n"
          + "    {\n"
          + "      \"name\": \"Troll\",\n"
          + "      \"active\": \"true\",\n"
          + "      \"damage\": \"20\",\n"
          + "      \"can_attack\": \"true\",\n"
          + "      \"attack_description\": \"The troll swings its club at you!\",\n"
          + "      \"description\": \"A fierce troll with green skin and a large club.\",\n"
          + "      \"effects\": \"The troll is blocking the path to the south.\",\n"
          + "      \"value\": \"100\",\n"
          + "      \"solution\": \"Magic Sword\",\n"
          + "      \"target\": \"2:Dungeon Room\"\n"
          + "    }\n"
          + "  ]\n"
          + "}";

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
    String commands = "Player\n"
            + "e\n"
            + "w\n"
            + "w\n"
            + "t Magic Sword\n"
            + "e\n"
            + "e\n"
            + "u Magic Sword\n"
            + "s\n";

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

    // Check if the player defeated the troll
    if (!output.contains("You swing the magic sword with great effect!")) {
      System.out.println("FAIL: Player didn't use the Magic Sword properly");
      success = false;
    }

    // Check if the player's score increased
    if (!output.contains("Score: 100")) {
      System.out.println("FAIL: Player's score didn't increase after defeating the troll");
      success = false;
    }

    // Check if the player could move south after defeating the troll
    if (!output.contains("Treasure Chamber")) {
      System.out.println("FAIL: Player couldn't access the Treasure Chamber after defeating the troll");
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