package scenarios;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import enginedriver.GameEngineApp;
import model.*;

/**
 * Scenario 4: Implements a test scenario where a player solves a puzzle to access a hidden room.
 * This class creates a temporary game file with a library puzzle scenario, runs the game
 * with predefined commands, and verifies the expected outcomes.
 */
public class LibraryRiddleScenario {
  private final String jsonData = "{\n"
          + "  \"name\": \"Puzzle Solving Scenario\",\n"
          + "  \"version\": \"1.0\",\n"
          + "  \"rooms\": [\n"
          + "    {\n"
          + "      \"room_name\": \"Library\",\n"
          + "      \"room_number\": \"1\",\n"
          + "      \"description\": \"A grand library with tall bookshelves and a reading area.\",\n"
          + "      \"N\": \"0\", \"S\": \"0\", \"E\": \"0\", \"W\": \"-2\",\n"
          + "      \"puzzle\": \"Bookshelf Riddle\",\n"
          + "      \"fixtures\": [\"Bookshelf\"]\n"
          + "    },\n"
          + "    {\n"
          + "      \"room_name\": \"Secret Study\",\n"
          + "      \"room_number\": \"2\",\n"
          + "      \"description\": \"A hidden study with rare books and ancient scrolls.\",\n"
          + "      \"N\": \"0\", \"S\": \"0\", \"E\": \"1\", \"W\": \"0\",\n"
          + "      \"items\": [\"Ancient Scroll\"]\n"
          + "    }\n"
          + "  ],\n"
          + "  \"items\": [\n"
          + "    {\n"
          + "      \"name\": \"Ancient Scroll\",\n"
          + "      \"weight\": \"1\",\n"
          + "      \"max_uses\": \"1\",\n"
          + "      \"uses_remaining\": \"1\",\n"
          + "      \"value\": \"20\",\n"
          + "      \"when_used\": \"You carefully unroll the ancient scroll and read its contents.\",\n"
          + "      \"description\": \"A yellowed scroll with ancient text written on it.\"\n"
          + "    }\n"
          + "  ],\n"
          + "  \"fixtures\": [\n"
          + "    {\n"
          + "      \"name\": \"Bookshelf\",\n"
          + "      \"weight\": \"500\",\n"
          + "      \"description\": \"A tall bookshelf with a riddle inscribed on one shelf: 'What has keys but can't open locks?'\"\n"
          + "    }\n"
          + "  ],\n"
          + "  \"puzzles\": [\n"
          + "    {\n"
          + "      \"name\": \"Bookshelf Riddle\",\n"
          + "      \"active\": \"true\",\n"
          + "      \"affects_target\": \"true\",\n"
          + "      \"affects_player\": \"false\",\n"
          + "      \"solution\": \"'piano'\",\n"
          + "      \"value\": \"75\",\n"
          + "      \"description\": \"A riddle inscribed on the bookshelf: 'What has keys but can't open locks?'\",\n"
          + "      \"effects\": \"The bookshelf blocks the path to the west.\",\n"
          + "      \"target\": \"1:Library\"\n"
          + "    }\n"
          + "  ]\n"
          + "}";

  /**
   * Runs the puzzle solving scenario with predefined commands.
   * Creates a temporary JSON game file, executes the commands through the game engine,
   * and verifies the expected outcomes.
   *
   * @throws IOException If there is an error creating or writing to the temporary file
   */
  public void run() throws IOException {
    // Write the scenario data to a temporary file
    File tempFile = File.createTempFile("puzzle_scenario", ".json");
    try (FileWriter writer = new FileWriter(tempFile)) {
      writer.write(jsonData);
    }

    // Commands to execute the scenario
    String commands = "Player\n"
            + "l\n"
            + "x Bookshelf\n"
            + "w\n"
            + "a piano\n"
            + "w\n"
            + "t Ancient Scroll\n";

    StringReader input = new StringReader(commands);
    StringWriter output = new StringWriter();

    // Run the game with our scenario
    GameEngineApp app = new GameEngineApp(tempFile.getAbsolutePath(), input, output);
    app.start();

    // Print the output to see what happened
    String result = output.toString();
    System.out.println("=== Puzzle Scenario Output ===");
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

    // Check if the player saw the riddle
    if (!output.contains("What has keys but can't open locks?")) {
      System.out.println("FAIL: Player didn't see the riddle");
      success = false;
    }

    // Check if the riddle was answered correctly
    if (!output.contains("You solved the puzzle!") && !output.contains("correct answer")) {
      System.out.println("FAIL: Player's answer wasn't recognized as correct");
      success = false;
    }

    // Check if the player's score increased
    if (!output.contains("Score: 75")) {
      System.out.println("FAIL: Player's score didn't increase after solving the puzzle");
      success = false;
    }

    // Check if the player could move west after solving the puzzle
    if (!output.contains("Secret Study")) {
      System.out.println("FAIL: Player couldn't access the Secret Study after solving the puzzle");
      success = false;
    }

    if (success) {
      System.out.println("SUCCESS: Puzzle scenario completed successfully!");
    }
  }

  /**
   * Main method to run this scenario independently for testing purposes.
   *
   * @param args Command line arguments (not used)
   */
  public static void main(String[] args) {
    try {
      new LibraryRiddleScenario().run();
    } catch (IOException e) {
      System.out.println("Error running puzzle scenario: " + e.getMessage());
    }
  }
}