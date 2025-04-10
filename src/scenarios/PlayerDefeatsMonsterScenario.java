package scenarios;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import enginedriver.GameEngineApp;
import model.*;

/**
 * Scenario 2: Player Encounters and Defeats a Monster
 * 
 * This scenario tests the monster encounter and defeat mechanics:
 * 1. Player starts in Castle Hall with a Magic Sword from the Armory
 * 2. Player moves east to enter the Dungeon Room
 * 3. Player sees a Troll blocking the southern exit to Treasure Chamber
 * 4. Player uses Magic Sword on the Troll
 * 5. Troll is defeated (vulnerable to Magic Sword) and disappears
 * 6. Room description updates to show clear southern path
 * 7. Player's score increases by Troll's point value
 * 8. Player can now move south to Treasure Chamber
 * 
 * This class creates a temporary game file with this scenario, runs the game
 * with predefined commands, and verifies the expected outcomes.
 */
public class PlayerDefeatsMonsterScenario {
    private final String jsonData = "{\n"
            + "  \"name\": \"Player Defeats Monster Scenario\",\n"
            + "  \"version\": \"1.0\",\n"
            + "  \"rooms\": [\n"
            + "    {\n"
            + "      \"room_name\": \"Castle Hall\",\n"
            + "      \"room_number\": \"1\",\n"
            + "      \"description\": \"A grand hall with suits of armor lining the walls.\",\n"
            + "      \"N\": \"0\", \"S\": \"0\", \"E\": \"2\", \"W\": \"0\",\n"
            + "      \"items\": [\"Magic Sword\"]\n"
            + "    },\n"
            + "    {\n"
            + "      \"room_name\": \"Dungeon Room\",\n"
            + "      \"room_number\": \"2\",\n"
            + "      \"description\": \"A dimly lit dungeon room. A fierce Troll blocks the southern exit.\",\n"
            + "      \"N\": \"0\", \"S\": \"3\", \"E\": \"0\", \"W\": \"1\",\n"
            + "      \"monsters\": [\"Troll\"]\n"
            + "    },\n"
            + "    {\n"
            + "      \"room_name\": \"Treasure Chamber\",\n"
            + "      \"room_number\": \"3\",\n"
            + "      \"description\": \"A chamber filled with gold and precious gems.\",\n"
            + "      \"N\": \"2\", \"S\": \"0\", \"E\": \"0\", \"W\": \"0\"\n"
            + "    }\n"
            + "  ],\n"
            + "  \"items\": [\n"
            + "    {\n"
            + "      \"name\": \"Magic Sword\",\n"
            + "      \"weight\": \"3\",\n"
            + "      \"max_uses\": \"1\",\n"
            + "      \"uses_remaining\": \"1\",\n"
            + "      \"value\": \"30\",\n"
            + "      \"when_used\": \"The Magic Sword glows with power as you strike.\",\n"
            + "      \"description\": \"A gleaming sword that radiates magical energy.\"\n"
            + "    }\n"
            + "  ],\n"
            + "  \"monsters\": [\n"
            + "    {\n"
            + "      \"name\": \"Troll\",\n"
            + "      \"health\": \"40\",\n"
            + "      \"attack_power\": \"10\",\n"
            + "      \"description\": \"A fierce Troll with thick hide and sharp claws.\",\n"
            + "      \"vulnerability\": \"Magic Sword\",\n"
            + "      \"value\": \"75\"\n"
            + "    }\n"
            + "  ]\n"
            + "}";

    /**
     * Runs the player defeats monster scenario with predefined commands.
     * Creates a temporary JSON game file, executes the commands through the game engine,
     * and verifies the expected outcomes.
     *
     * @throws IOException If there is an error creating or writing to the temporary file
     */
    public void run() throws IOException {
        // Write the scenario data to a temporary file
        File tempFile = File.createTempFile("monster_defeat_scenario", ".json");
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write(jsonData);
        }

        // Commands to execute the scenario
        String commands = "Player\n"
                + "t Magic Sword\n"
                + "e\n"
                + "l\n"
                + "use Magic Sword\n"
                + "l\n"
                + "s\n";

        StringReader input = new StringReader(commands);
        StringWriter output = new StringWriter();

        // Run the game with our scenario
        GameEngineApp app = new GameEngineApp(tempFile.getAbsolutePath(), input, output);
        app.start();

        // Print the output to see what happened
        String result = output.toString();
        System.out.println("=== Player Defeats Monster Scenario Output ===");
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

        // Check if the player picked up the Magic Sword
        if (!output.contains("Magic Sword added to inventory")) {
            System.out.println("FAIL: Player couldn't pick up the Magic Sword");
            success = false;
        }

        // Check if the player entered the Dungeon Room
        if (!output.contains("Dungeon Room")) {
            System.out.println("FAIL: Player didn't enter the Dungeon Room");
            success = false;
        }

        // Check if the Troll was visible in the room description
        if (!output.contains("A fierce Troll blocks the southern exit")) {
            System.out.println("FAIL: Troll wasn't visible in the room description");
            success = false;
        }

        // Check if the Magic Sword was used successfully
        if (!output.contains("The Magic Sword glows with power as you strike")) {
            System.out.println("FAIL: Magic Sword wasn't used successfully");
            success = false;
        }

        // Check if the Troll was defeated
        if (!output.contains("Troll defeated")) {
            System.out.println("FAIL: Troll wasn't defeated");
            success = false;
        }

        // Check if the room description updated after Troll defeat
        if (!output.contains("The southern path is now clear")) {
            System.out.println("FAIL: Room description didn't update after Troll defeat");
            success = false;
        }

        // Check if the player's score increased
        if (!output.contains("Score: 75")) {
            System.out.println("FAIL: Player's score didn't increase after defeating the Troll");
            success = false;
        }

        // Check if the player could move to the Treasure Chamber
        if (!output.contains("Treasure Chamber")) {
            System.out.println("FAIL: Player couldn't move to the Treasure Chamber");
            success = false;
        }

        if (success) {
            System.out.println("SUCCESS: Player Defeats Monster scenario completed successfully!");
        }
    }

    /**
     * Main method to run this scenario independently for testing purposes.
     *
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        try {
            new PlayerDefeatsMonsterScenario().run();
        } catch (IOException e) {
            System.out.println("Error running monster defeat scenario: " + e.getMessage());
        }
    }
} 