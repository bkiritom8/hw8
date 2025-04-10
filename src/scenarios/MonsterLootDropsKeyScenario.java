package scenarios;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import enginedriver.GameEngineApp;

/**
 * Scenario 1: Monster Loot Drops Key
 * This scenario tests the monster loot and key-based puzzle mechanics:
 * 1. Player encounters and defeats the Dungeon Guardian monster
 * 2. System automatically adds SealedKey to the room's available items
 * 3. Player attempts to pick up the SealedKey
 * 4. System performs inventory weight check
 * 5. Upon passing weight check, SealedKey is added to player's inventory
 * 6. With SealedKey in possession, Library puzzle becomes solvable
 * 7. Player can progress in their adventure
 * This class creates a temporary game file with this scenario, runs the game
 * with predefined commands, and verifies the expected outcomes.
 */
public class MonsterLootDropsKeyScenario {
    
    /**
     * JSON game data defining the scenario world, items, monsters, and puzzles.
     */
    private final String jsonData = "{\n"
        + "  \"name\": \"Monster Loot Drops Key Scenario\",\n"
        + "  \"version\": \"1.0\",\n"
        + "  \"rooms\": [\n"
        + "    {\n"
        + "      \"room_name\": \"Dungeon Chamber\",\n"
        + "      \"room_number\": \"1\",\n"
        + "      \"description\": \"A dark chamber with ancient runes on the walls. A fearsome guardian stands in the center.\",\n"
        + "      \"N\": \"0\", \"S\": \"0\", \"E\": \"2\", \"W\": \"0\",\n"
        + "      \"monsters\": [\"Dungeon Guardian\"]\n"
        + "    },\n"
        + "    {\n"
        + "      \"room_name\": \"Library\",\n"
        + "      \"room_number\": \"2\",\n"
        + "      \"description\": \"A grand library with ancient texts and a sealed door.\",\n"
        + "      \"N\": \"0\", \"S\": \"0\", \"E\": \"3\", \"W\": \"1\",\n"
        + "      \"puzzle\": \"Sealed Door\",\n"
        + "      \"fixtures\": [\"Sealed Door\"]\n"
        + "    },\n"
        + "    {\n"
        + "      \"room_name\": \"Hidden Chamber\",\n"
        + "      \"room_number\": \"3\",\n"
        + "      \"description\": \"A secret chamber containing ancient treasures.\",\n"
        + "      \"N\": \"0\", \"S\": \"0\", \"E\": \"0\", \"W\": \"2\"\n"
        + "    }\n"
        + "  ],\n"
        + "  \"items\": [\n"
        + "    {\n"
        + "      \"name\": \"SealedKey\",\n"
        + "      \"weight\": \"2\",\n"
        + "      \"max_uses\": \"1\",\n"
        + "      \"uses_remaining\": \"1\",\n"
        + "      \"value\": \"50\",\n"
        + "      \"when_used\": \"The key fits perfectly into the door's lock.\",\n"
        + "      \"description\": \"An ornate key with ancient runes etched into its surface.\"\n"
        + "    }\n"
        + "  ],\n"
        + "  \"fixtures\": [\n"
        + "    {\n"
        + "      \"name\": \"Sealed Door\",\n"
        + "      \"weight\": \"1000\",\n"
        + "      \"description\": \"A massive door sealed with ancient magic. It requires a special key to open.\"\n"
        + "    }\n"
        + "  ],\n"
        + "  \"monsters\": [\n"
        + "    {\n"
        + "      \"name\": \"Dungeon Guardian\",\n"
        + "      \"health\": \"50\",\n"
        + "      \"attack_power\": \"15\",\n"
        + "      \"description\": \"A fearsome guardian of the dungeon, clad in ancient armor.\",\n"
        + "      \"loot\": \"SealedKey\"\n"
        + "    }\n"
        + "  ],\n"
        + "  \"puzzles\": [\n"
        + "    {\n"
        + "      \"name\": \"Sealed Door\",\n"
        + "      \"active\": \"true\",\n"
        + "      \"affects_target\": \"true\",\n"
        + "      \"affects_player\": \"false\",\n"
        + "      \"solution\": \"use SealedKey\",\n"
        + "      \"value\": \"100\",\n"
        + "      \"description\": \"The door is sealed with ancient magic and requires a special key.\",\n"
        + "      \"effects\": \"The door unlocks and reveals a new path.\",\n"
        + "      \"target\": \"2:Library\"\n"
        + "    }\n"
        + "  ]\n"
        + "}";

    /**
     * Runs the monster loot drops key scenario with predefined commands.
     * Creates a temporary JSON game file, executes the commands through the game engine,
     * and verifies the expected outcomes.
     *
     * @throws IOException If there is an error creating or writing to the temporary file
     */
    public void run() throws IOException {
        // Create temporary file for the scenario
        File tempFile = createTempGameFile();
        
        // Define the command sequence
        String commands = buildCommandSequence();
        
        // Execute the game with our commands
        String result = executeGame(tempFile, commands);
        
        // Display the results
        System.out.println("=== Monster Loot Drops Key Scenario Output ===");
        System.out.println(result);
        
        // Clean up the temporary file
        tempFile.delete();
        
        // Verify the scenario outcomes
        verifyScenarioOutcomes(result);
    }
    
    /**
     * Creates a temporary file with the JSON game data.
     * 
     * @return The created temporary file
     * @throws IOException If there is an error creating or writing to the file
     */
    private File createTempGameFile() throws IOException {
        File tempFile = File.createTempFile("monster_loot_scenario", ".json");
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write(jsonData);
        }
        return tempFile;
    }
    
    /**
     * Builds the sequence of commands to execute the scenario.
     * 
     * @return A string containing the command sequence
     */
    private String buildCommandSequence() {
        return String.join("\n", 
            "Player",                  // Set player name
            "look",                    // Look around to see current room and monster
            "examine Dungeon Guardian", // Examine the monster
            "attack Dungeon Guardian", // Attack and defeat the monster
            "look",                    // Look to see room after monster is defeated
            "examine SealedKey",       // Examine the dropped key
            "take SealedKey",          // Take the key
            "inventory",               // Check inventory to confirm key is there
            "east",                    // Move to the Library
            "look",                    // Look around the Library
            "examine Sealed Door",     // Examine the door puzzle
            "use SealedKey",           // Use the key to solve the puzzle
            "look",                    // Look to see changes after puzzle is solved
            "east"                     // Move through the unlocked door
        ) + "\n";
    }
    
    /**
     * Executes the game with the provided file and commands.
     * 
     * @param gameFile The game file to use
     * @param commands The commands to execute
     * @return The output from the game engine
     * @throws IOException If there is an error executing the game
     */
    private String executeGame(File gameFile, String commands) throws IOException {
        StringReader input = new StringReader(commands);
        StringWriter output = new StringWriter();
        
        GameEngineApp app = new GameEngineApp(gameFile.getAbsolutePath(), input, output);
        app.start();
        
        return output.toString();
    }

    /**
     * Verifies that the scenario played out as expected by checking the output text.
     * Looks for specific indicators of success or failure in the game output.
     *
     * @param output The text output from the game run
     */
    private void verifyScenarioOutcomes(String output) {
        boolean success = true;
        
        // Create verification tracking
        VerificationTracker tracker = new VerificationTracker();
        
        // Check initial room and monster description
        tracker.verify(output.contains("Dungeon Chamber"), 
                      "Player should see the Dungeon Chamber description");
        
        tracker.verify(output.contains("Dungeon Guardian"), 
                      "Player should encounter the Dungeon Guardian");
        
        // Check monster defeat and loot drop
        tracker.verify(output.contains("You attack the Dungeon Guardian") || 
                      output.contains("You hit the Dungeon Guardian"),
                      "Player should be able to attack the Dungeon Guardian");
        
        tracker.verify(output.contains("Dungeon Guardian defeated") || 
                      output.contains("Dungeon Guardian has been defeated"),
                      "Player should defeat the Dungeon Guardian");
        
        tracker.verify(output.contains("SealedKey has been added to the room") || 
                      output.contains("SealedKey dropped"),
                      "SealedKey should be dropped after defeating the monster");
        
        // Check item pickup and inventory
        tracker.verify(output.contains("SealedKey added to inventory") || 
                      output.contains("You pick up the SealedKey"),
                      "Player should be able to pick up the SealedKey");
        
        tracker.verify(output.contains("You are carrying:") && 
                      output.contains("SealedKey"),
                      "SealedKey should appear in the player's inventory");
        
        // Check room navigation
        tracker.verify(output.contains("Library"),
                      "Player should be able to move to the Library");
        
        tracker.verify(output.contains("Sealed Door") || 
                      output.contains("sealed door"),
                      "Player should see the Sealed Door in the Library");
        
        // Check puzzle solving
        tracker.verify(output.contains("The key fits perfectly") || 
                      output.contains("The door unlocks"),
                      "Player should be able to use the SealedKey on the door");
        
        tracker.verify(output.contains("The door unlocks and reveals a new path") || 
                      output.contains("pathway is now clear") || 
                      output.contains("door opens"),
                      "Door should unlock after using the key");
        
        // Check score
        tracker.verify(output.contains("Score:") && 
                      (output.contains("Score: 100") || 
                       output.contains("Score: 150")),
                      "Player's score should increase after completing objectives");
        
        // Check final room access
        tracker.verify(output.contains("Hidden Chamber"),
                      "Player should be able to access the Hidden Chamber");
        
        // Print verification results
        tracker.printResults();
    }
    
    /**
     * Helper class to track and report verification results.
     */
    private class VerificationTracker {
        private int totalChecks = 0;
        private int passedChecks = 0;
        private StringBuilder failures = new StringBuilder();
        
        /**
         * Verify a condition and track the result.
         * 
         * @param condition The condition to verify
         * @param message The message describing what's being verified
         */
        public void verify(boolean condition, String message) {
            totalChecks++;
            if (condition) {
                passedChecks++;
            } else {
                failures.append("FAIL: ").append(message).append("\n");
            }
        }
        
        /**
         * Print verification results.
         */
        public void printResults() {
            if (failures.length() > 0) {
                System.out.println(failures.toString());
            }
            
            System.out.println(String.format("Verification complete: %d/%d checks passed", 
                                           passedChecks, totalChecks));
            
            if (passedChecks == totalChecks) {
                System.out.println("SUCCESS: Monster Loot Drops Key scenario completed successfully!");
            } else {
                System.out.println("FAIL: Monster Loot Drops Key scenario had errors.");
            }
        }
    }

    /**
     * Main method to run this scenario independently for testing purposes.
     *
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        try {
            new MonsterLootDropsKeyScenario().run();
        } catch (IOException e) {
            System.err.println("Error running monster loot scenario: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 