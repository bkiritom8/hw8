package scenarios;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import enginedriver.GameEngineApp;
import model.*;

/**
 * Scenario 6: Implements a test scenario where a player discovers a pearl in a coral cavern.
 * This class creates a temporary game file with a coral cavern scenario, runs the game
 * with predefined commands, and verifies the expected outcomes.
 */
public class UnlockPearlScenario {
    private final String jsonData = "{\n"
            + "  \"name\": \"Coral Cavern Scenario\",\n"
            + "  \"version\": \"1.0\",\n"
            + "  \"rooms\": [\n"
            + "    {\n"
            + "      \"room_name\": \"Beach Shore\",\n"
            + "      \"room_number\": \"1\",\n"
            + "      \"description\": \"A sandy beach with gentle waves. There's a path leading to a hidden cove.\",\n"
            + "      \"N\": \"0\", \"S\": \"0\", \"E\": \"2\", \"W\": \"0\"\n"
            + "    },\n"
            + "    {\n"
            + "      \"room_name\": \"Hidden Cove\",\n"
            + "      \"room_number\": \"2\",\n"
            + "      \"description\": \"A secluded cove with crystal clear water. You can see a cave entrance underwater.\",\n"
            + "      \"N\": \"0\", \"S\": \"0\", \"E\": \"0\", \"W\": \"1\",\n"
            + "      \"items\": [\"Diving Mask\"]\n"
            + "    },\n"
            + "    {\n"
            + "      \"room_name\": \"Coral Cavern\",\n"
            + "      \"room_number\": \"3\",\n"
            + "      \"description\": \"An underwater cavern filled with colorful coral formations. The cavern glows with hints of hidden treasure.\",\n"
            + "      \"N\": \"0\", \"S\": \"0\", \"E\": \"0\", \"W\": \"0\",\n"
            + "      \"puzzle\": \"Water Current Lock\",\n"
            + "      \"monster\": \"Guardian Octopus\"\n"
            + "    }\n"
            + "  ],\n"
            + "  \"items\": [\n"
            + "    {\n"
            + "      \"name\": \"Diving Mask\",\n"
            + "      \"weight\": \"1\",\n"
            + "      \"max_uses\": \"3\",\n"
            + "      \"uses_remaining\": \"3\",\n"
            + "      \"value\": \"15\",\n"
            + "      \"when_used\": \"You put on the diving mask, allowing you to see clearly underwater.\",\n"
            + "      \"description\": \"A high-quality diving mask with a snorkel attachment.\"\n"
            + "    },\n"
            + "    {\n"
            + "      \"name\": \"Luminous Pearl\",\n"
            + "      \"weight\": \"1\",\n"
            + "      \"max_uses\": \"1\",\n"
            + "      \"uses_remaining\": \"1\",\n"
            + "      \"value\": \"200\",\n"
            + "      \"when_used\": \"The pearl glows brightly, illuminating the entire cavern.\",\n"
            + "      \"description\": \"A rare pearl that seems to glow with its own inner light.\"\n"
            + "    }\n"
            + "  ],\n"
            + "  \"fixtures\": [\n"
            + "    {\n"
            + "      \"name\": \"Ancient Coral Formation\",\n"
            + "      \"weight\": \"500\",\n"
            + "      \"description\": \"A formation of arrow-like coral patterns pointing to hidden secrets.\"\n"
            + "    }\n"
            + "  ],\n"
            + "  \"monsters\": [\n"
            + "    {\n"
            + "      \"name\": \"Guardian Octopus\",\n"
            + "      \"active\": \"true\",\n"
            + "      \"damage\": \"10\",\n"
            + "      \"can_attack\": \"true\",\n"
            + "      \"attack_description\": \"The octopus lashes out with its tentacles!\",\n"
            + "      \"description\": \"A giant octopus that guards ancient treasures.\",\n"
            + "      \"effects\": \"A giant octopus blocks your way with its tentacles spread wide.\",\n"
            + "      \"value\": \"50\",\n"
            + "      \"solution\": \"Luminous Pearl\",\n"
            + "      \"target\": \"3:Coral Cavern\"\n"
            + "    }\n"
            + "  ],\n"
            + "  \"puzzles\": [\n"
            + "    {\n"
            + "      \"name\": \"Water Current Lock\",\n"
            + "      \"active\": \"true\",\n"
            + "      \"affects_target\": \"true\",\n"
            + "      \"affects_player\": \"false\",\n"
            + "      \"solution\": \"'rotate-left-right-center'\",\n"
            + "      \"value\": \"100\",\n"
            + "      \"description\": \"A mechanism that controls the water currents in the cavern.\",\n"
            + "      \"effects\": \"Strong water currents prevent you from exploring deeper into the cavern.\",\n"
            + "      \"target\": \"3:Coral Cavern\"\n"
            + "    }\n"
            + "  ]\n"
            + "}";

    /**
     * Runs the coral cavern scenario with predefined commands.
     * Creates a temporary JSON game file, executes the commands through the game engine,
     * and verifies the expected outcomes.
     *
     * @throws IOException If there is an error creating or writing to the temporary file
     */
    public void run() throws IOException {
        // Write the scenario data to a temporary file
        File tempFile = File.createTempFile("coral_scenario", ".json");
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write(jsonData);
        }

        // Commands to execute the scenario
        String commands = "Explorer\n"
                + "e\n"
                + "t Diving Mask\n"
                + "l\n"
                + "e\n"
                + "l\n"
                + "a rotate-left-right-center\n"
                + "l\n"
                + "t Luminous Pearl\n"
                + "l\n";

        StringReader input = new StringReader(commands);
        StringWriter output = new StringWriter();

        // Run the game with our scenario
        GameEngineApp app = new GameEngineApp(tempFile.getAbsolutePath(), input, output);
        app.start();

        // Print the output to see what happened
        String result = output.toString();
        System.out.println("=== Coral Cavern Scenario Output ===");
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

        // Check if the player solved the water current puzzle
        if (!output.contains("rotate-left-right-center") || !output.contains("The water currents settle")) {
            System.out.println("FAIL: Player didn't solve the water current puzzle correctly");
            success = false;
        }

        // Check if the player found the luminous pearl
        if (!output.contains("Luminous Pearl added to inventory") || !output.contains("A rare pearl")) {
            System.out.println("FAIL: Player couldn't find or take the Luminous Pearl");
            success = false;
        }

        // Check if the player's score increased
        if (!output.contains("Score: 100")) {
            System.out.println("FAIL: Player's score didn't increase after solving the puzzle");
            success = false;
        }

        if (success) {
            System.out.println("SUCCESS: Coral Cavern scenario completed successfully!");
        }
    }

    /**
     * Main method to run this scenario independently for testing purposes.
     *
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        try {
            new UnlockPearlScenario().run();
        } catch (IOException e) {
            System.out.println("Error running coral cavern scenario: " + e.getMessage());
        }
    }
}