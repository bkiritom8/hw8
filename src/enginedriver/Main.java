package enginedriver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;

/**
 * Main class for running the Adventure Game Engine.
 * Contains a main method with examples of how to run the engine.
 */
public class Main {

  /**
   * Main method for running the Adventure Game Engine.
   *
   * @param args Command line arguments (not used)
   * @throws IOException If there is an error reading from the input or writing to the output
   */
  public static void main(String[] args) throws IOException {
    // Example 1: Run with empty rooms
    runWithEmptyRooms();

    // Example 2: Run with simple hallway
    runWithSimpleHallway();

    // Example 3: Run with museum
    runWithMuseum();

    // Example 4: Run with align quest
    runWithAlignQuest();

    // Example 5: Run with interactive input from console
    runWithInteractiveInput();
  }

  /**
   * Runs the game with the empty rooms data file and synthetic input.
   * This demonstrates basic movement through empty rooms.
   *
   * @throws IOException If there is an error reading from the input or writing to the output
   */
  private static void runWithEmptyRooms() throws IOException {
    // Create synthetic input for testing
    // Player enters name, looks around, moves north twice, then south twice, looks again, then quits
    String input = "Test Player\nl\nn\nl\nn\nl\ns\nl\ns\nl\nq";
    BufferedReader stringReader = new BufferedReader(new StringReader(input));

    // Create game engine app
    GameEngineApp gameEngineApp = new GameEngineApp("./resources/empty_rooms.json", stringReader, System.out);

    // Start the game
    System.out.println("=== Running with empty_rooms.json ===");
    gameEngineApp.start();
    System.out.println("=== End of empty_rooms.json run ===\n");
  }

  /**
   * Runs the game with the simple hallway data file and synthetic input.
   * This demonstrates interaction with items, fixtures, puzzles and monsters.
   *
   * @throws IOException If there is an error reading from the input or writing to the output
   */
  private static void runWithSimpleHallway() throws IOException {
    // Create synthetic input for testing
    // This sequence demonstrates a player exploring rooms, taking and using items,
    // examining fixtures, solving puzzles, and defeating monsters
    String input = "Adventurer\n" +  // Player name
            "l\n" +           // Look around
            "x painting\n" +   // Examine the painting fixture
            "t notebook\n" +   // Take the notebook item
            "i\n" +           // Check inventory
            "n\n" +           // Move north
            "l\n" +           // Look around
            "t key\n" +       // Take the key
            "t hair clippers\n" + // Take the hair clippers
            "u key\n" +       // Use the key to solve the lock puzzle
            "n\n" +           // Move north (now possible after solving puzzle)
            "l\n" +           // Look around
            "u hair clippers\n" + // Use the hair clippers to defeat the monster
            "l\n" +           // Look around
            "n\n" +           // Move north
            "l\n" +           // Look around
            "u lamp\n" +      // Use the lamp to solve the darkness puzzle
            "i\n" +           // Check inventory
            "d notebook\n" +  // Drop the notebook
            "l\n" +           // Look around
            "q";              // Quit the game

    BufferedReader stringReader = new BufferedReader(new StringReader(input));

    // Create game engine app
    GameEngineApp gameEngineApp = new GameEngineApp("./resources/simple_hallway.json", stringReader, System.out);

    // Start the game
    System.out.println("=== Running with simple_hallway.json ===");
    gameEngineApp.start();
    System.out.println("=== End of simple_hallway.json run ===\n");
  }

  /**
   * Runs the game with the museum data file and synthetic input.
   * This demonstrates solving puzzles with both items and text answers.
   *
   * @throws IOException If there is an error reading from the input or writing to the output
   */
  private static void runWithMuseum() throws IOException {
    // Create synthetic input for testing
    // This sequence demonstrates a player exploring the museum,
    // using a ticket item to solve one puzzle, and providing a text answer to solve another
    String input = "Museum Visitor\n" + // Player name
            "l\n" +             // Look around
            "t ticket\n" +      // Take the ticket
            "i\n" +             // Check inventory
            "u ticket\n" +      // Use the ticket to pass the turnstile
            "n\n" +             // Move north
            "l\n" +             // Look around
            "a Align\n" +       // Answer the password puzzle with "Align"
            "n\n" +             // Move north
            "l\n" +             // Look around
            "n\n" +             // Move north
            "l\n" +             // Look around
            "s\n" +             // Move south
            "s\n" +             // Move south
            "l\n" +             // Look around
            "q";                // Quit the game

    BufferedReader stringReader = new BufferedReader(new StringReader(input));

    // Create game engine app
    GameEngineApp gameEngineApp = new GameEngineApp("./resources/museum.json", stringReader, System.out);

    // Start the game
    System.out.println("=== Running with museum.json ===");
    gameEngineApp.start();
    System.out.println("=== End of museum.json run ===\n");
  }

  /**
   * Runs the game with the align quest data file and synthetic input.
   * This demonstrates a more complex game with multiple puzzles and interactions.
   *
   * @throws IOException If there is an error reading from the input or writing to the output
   */
  private static void runWithAlignQuest() throws IOException {
    // Create synthetic input for testing
    // This sequence demonstrates a more complex adventure through multiple rooms with various interactions
    String input = "Align Student\n" +  // Player name
            "l\n" +              // Look around
            "x billboard\n" +     // Examine the billboard
            "t hair clippers\n" + // Take hair clippers
            "i\n" +              // Check inventory
            "n\n" +              // Move north
            "l\n" +              // Look around
            "t thumb drive\n" +  // Take thumb drive
            "t modulo 2\n" +     // Take modulo 2
            "i\n" +              // Check inventory
            "n\n" +              // Move north
            "l\n" +              // Look around
            "u hair clippers\n" + // Use hair clippers on teddy bear monster
            "l\n" +              // Look around
            "e\n" +              // Move east
            "l\n" +              // Look around
            "x bookshelf\n" +     // Examine bookshelf
            "t key\n" +          // Take key
            "i\n" +              // Check inventory
            "u modulo 2\n" +     // Use modulo 2 to solve puzzle
            "e\n" +              // Move east
            "l\n" +              // Look around
            "t algorithms book\n" + // Take algorithms book
            "i\n" +              // Check inventory
            "q";                 // Quit the game

    BufferedReader stringReader = new BufferedReader(new StringReader(input));

    // Create game engine app
    GameEngineApp gameEngineApp = new GameEngineApp("./resources/align_quest_game_elements.json", stringReader, System.out);

    // Start the game
    System.out.println("=== Running with align_quest_game_elements.json ===");
    gameEngineApp.start();
    System.out.println("=== End of align_quest_game_elements.json run ===\n");
  }

  /**
   * Runs the game with interactive input from the console.
   * This allows direct user interaction with the game.
   *
   * @throws IOException If there is an error reading from the input or writing to the output
   */
  private static void runWithInteractiveInput() throws IOException {
    // Present a menu of available game files
    System.out.println("Available game files:");
    System.out.println("1. empty_rooms.json - Simple navigation test");
    System.out.println("2. simple_hallway.json - Basic items, fixtures, puzzles and monsters");
    System.out.println("3. museum.json - Text answer puzzles and themed adventures");
    System.out.println("4. align_quest_game_elements.json - Complex adventure with multiple puzzles");
    System.out.print("Enter the number of the game file to use: ");

    // Read user choice
    BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
    String choice = consoleReader.readLine();

    // Select game file based on user choice
    String gameFile;
    switch (choice) {
      case "1":
        gameFile = "./resources/empty_rooms.json";
        break;
      case "2":
        gameFile = "./resources/simple_hallway.json";
        break;
      case "3":
        gameFile = "./resources/museum.json";
        break;
      case "4":
        gameFile = "./resources/align_quest_game_elements.json";
        break;
      default:
        System.out.println("Invalid choice. Using empty_rooms.json.");
        gameFile = "./resources/empty_rooms.json";
        break;
    }

    // Display available commands
    System.out.println("\nAvailable commands:");
    System.out.println("- Movement: (N)orth, (S)outh, (E)ast, (W)est");
    System.out.println("- Actions: (I)nventory, (L)ook, (T)ake [item], (D)rop [item]");
    System.out.println("- Interaction: e(X)amine [target], (U)se [item], (A)nswer [text]");
    System.out.println("- Game: Save, Restore, (Q)uit");
    System.out.println();

    // Create game engine app with console input/output
    GameEngineApp gameEngineApp = new GameEngineApp(gameFile, consoleReader, System.out);

    // Start the game
    System.out.println("\n=== Running with interactive input ===");
    gameEngineApp.start();
    System.out.println("=== End of interactive input run ===");
  }
}