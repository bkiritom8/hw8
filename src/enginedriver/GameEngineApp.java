package enginedriver;

import java.io.IOException;
import model.GameWorld;
import controller.GameController;

/**
 * The main application class that initializes and runs the text-based adventure game.
 * This class serves as the entry point for the game, connecting the model (GameWorld)
 * with the controller (GameController) and handling I/O operations.
 */
public class GameEngineApp {
  private final String gameFileName;
  private final Readable input;
  private final Appendable output;

  /**
   * Constructs a new GameEngineApp with the specified game file and I/O interfaces.
   *
   * @param gameFileName The path to the JSON file containing game data
   * @param input The input source for reading player commands
   * @param output The output destination for displaying game text
   */
  public GameEngineApp(String gameFileName, Readable input, Appendable output) {
    this.gameFileName = gameFileName;
    this.input = input;
    this.output = output;
  }

  /**
   * Initializes and starts the game.
   * This method loads the game data from the specified JSON file,
   * creates the game world and controller, and begins the main game loop.
   *
   * @throws IOException If there is an error reading the game file or during I/O operations
   */
  public void start() throws IOException {
    try {
      // Create the game model by loading the specified JSON
      GameWorld gameWorld = new GameWorld(gameFileName);

      // Create the controller, linking it to the model and I/O
      GameController controller = new GameController(gameWorld, input, output);

      // Start game loop
      controller.play();
    } catch (IOException e) {
      throw new IOException("Error starting game: " + e.getMessage(), e);
    }
  }
}