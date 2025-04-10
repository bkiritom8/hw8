package model;

import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Represents the game world model containing all game elements and state.
 * This class manages rooms, items, fixtures, puzzles, monsters, and the player.
 * It handles game data loading, saving, and various game mechanics.
 */
public class GameWorld {
  // Game metadata
  private String gameName;
  private String version;

  // Game elements
  private Map<String, Room> rooms;
  private Map<String, Item> items;
  private Map<String, Fixture> fixtures;
  private Map<String, Puzzle> puzzles;
  private Map<String, Monster> monsters;

  // Player
  private Player player;

  /**
   * Constructs a new GameWorld by loading game data from the specified JSON file.
   * Initializes all game elements and places the player in the first defined room.
   *
   * @param gameFileName Path to the JSON file containing game data
   * @throws IOException If there is an error reading or parsing the game file
   */
  public GameWorld(String gameFileName) throws IOException {
    this.rooms = new HashMap<>();
    this.items = new HashMap<>();
    this.fixtures = new HashMap<>();
    this.puzzles = new HashMap<>();
    this.monsters = new HashMap<>();

    try {
      loadGameData(gameFileName);
    } catch (Exception e) {
      throw new IOException("Error loading game data: " + e.getMessage(), e);
    }

    // Initialize player in the first room
    if (!rooms.isEmpty()) {
      Room startRoom = rooms.values().iterator().next(); // Get the first room
      this.player = new Player(startRoom);
    } else {
      throw new IOException("No rooms defined in the game file.");
    }
  }

  /**
   * Loads and parses game data from the specified JSON file.
   * Initializes game elements including items, fixtures, puzzles, monsters, and rooms.
   *
   * @param gameFileName Path to the JSON file containing game data
   * @throws IOException    If there is an error reading the file
   * @throws ParseException If there is an error parsing the JSON data
   */
  private void loadGameData(String gameFileName) throws IOException,
          org.json.simple.parser.ParseException {
    JSONParser parser = new JSONParser();

    try (FileReader reader = new FileReader(gameFileName)) {
      JSONObject gameData = (JSONObject) parser.parse(reader);

      // Load game metadata
      this.gameName = (String) gameData.get("name");
      this.version = (String) gameData.get("version");

      // Load items first so they can be referenced by rooms
      if (gameData.containsKey("items")) {
        loadItems((JSONArray) gameData.get("items"));
      }

      // Load fixtures
      if (gameData.containsKey("fixtures")) {
        loadFixtures((JSONArray) gameData.get("fixtures"));
      }

      // Load puzzles
      if (gameData.containsKey("puzzles")) {
        loadPuzzles((JSONArray) gameData.get("puzzles"));
      }

      // Load monsters
      if (gameData.containsKey("monsters")) {
        loadMonsters((JSONArray) gameData.get("monsters"));
      }

      // Load rooms (must be loaded last)
      if (gameData.containsKey("rooms")) {
        loadRooms((JSONArray) gameData.get("rooms"));
      } else {
        throw new IOException("No rooms defined in the game file.");
      }

    } catch (IOException e) {
      throw e;
    } catch (org.json.simple.parser.ParseException e) {
      throw e;
    }
  }

  /**
   * Loads room data from the JSON array and creates Room objects.
   * Links rooms with items, fixtures, puzzles, and monsters as specified.
   *
   * @param roomsArray The JSON array containing room data
   */
  private void loadRooms(JSONArray roomsArray) {
    for (Object obj : roomsArray) {
      JSONObject roomData = (JSONObject) obj;

      String roomName = (String) roomData.get("room_name");
      String roomNumber = (String) roomData.get("room_number");
      String description = (String) roomData.get("description");

      // Parse exits
      Map<Direction, String> exits = new HashMap<>();
      exits.put(Direction.NORTH, (String) roomData.get("N"));
      exits.put(Direction.SOUTH, (String) roomData.get("S"));
      exits.put(Direction.EAST, (String) roomData.get("E"));
      exits.put(Direction.WEST, (String) roomData.get("W"));

      // Create room
      Room room = new Room(roomName, roomNumber, description, exits,
              0, 0, 0, 0,"itemsField", "field3");

      // Add items to room if present
      String itemsList = (String) roomData.get("items");
      if (itemsList != null && !itemsList.isEmpty()) {
        for (String itemName : itemsList.split(",")) {
          Item item = items.get(itemName.trim().toUpperCase());
          if (item != null) {
            room.addItem(item);
          }
        }
      }

      // Add fixtures to room if present
      String fixturesList = (String) roomData.get("fixtures");
      if (fixturesList != null && !fixturesList.isEmpty()) {
        for (String fixtureName : fixturesList.split(",")) {
          Fixture fixture = fixtures.get(fixtureName.trim().toUpperCase());
          if (fixture != null) {
            room.addFixture(fixture);
          }
        }
      }

      // Add puzzle to room if present
      String puzzleName = (String) roomData.get("puzzle");
      if (puzzleName != null && !puzzleName.isEmpty()) {
        Puzzle puzzle = puzzles.get(puzzleName.trim().toUpperCase());
        if (puzzle != null) {
          room.setPuzzle(puzzle);
        }
      }

      // Add monster to room if present
      String monsterName = (String) roomData.get("monster");
      if (monsterName != null && !monsterName.isEmpty()) {
        Monster monster = monsters.get(monsterName.trim().toUpperCase());
        if (monster != null) {
          room.setMonster(monster);
        }
      }

      // Add room to map
      rooms.put(roomNumber, room);
    }

    // Connect rooms after all rooms are loaded
    connectRooms();
  }

  /**
   * Loads item data from the JSON array and creates Item objects.
   *
   * @param itemsArray The JSON array containing item data
   */
  private void loadItems(JSONArray itemsArray) {
    if (itemsArray == null) {
      return;
    }

    for (Object obj : itemsArray) {
      JSONObject itemData = (JSONObject) obj;

      String name = (String) itemData.get("name");
      int weight = parseIntOrDefault(itemData.get("weight"), 1);
      int maxUses = parseIntOrDefault(itemData.get("max_uses"), 1);
      int usesRemaining = parseIntOrDefault(itemData.get("uses_remaining"), 1);
      int value = parseIntOrDefault(itemData.get("value"), 0);
      String whenUsed = (String) itemData.get("when_used");
      String description = (String) itemData.get("description");

      Item item = new Item(name, weight, maxUses, usesRemaining, value, whenUsed, description);
      items.put(name.toUpperCase(), item);
    }
  }

  /**
   * Loads fixture data from the JSON array and creates Fixture objects.
   *
   * @param fixturesArray The JSON array containing fixture data
   */
  private void loadFixtures(JSONArray fixturesArray) {
    if (fixturesArray == null) {
      return;
    }

    for (Object obj : fixturesArray) {
      JSONObject fixtureData = (JSONObject) obj;

      String name = (String) fixtureData.get("name");
      int weight = parseIntOrDefault(fixtureData.get("weight"), 1000);
      String description = (String) fixtureData.get("description");

      Fixture fixture = new Fixture(name, weight, description);
      fixtures.put(name.toUpperCase(), fixture);
    }
  }

  /**
   * Loads puzzle data from the JSON array and creates Puzzle objects.
   *
   * @param puzzlesArray The JSON array containing puzzle data
   */
  private void loadPuzzles(JSONArray puzzlesArray) {
    if (puzzlesArray == null) {
      return;
    }

    for (Object obj : puzzlesArray) {
      JSONObject puzzleData = (JSONObject) obj;

      String name = (String) puzzleData.get("name");
      boolean active = Boolean.parseBoolean((String) puzzleData.get("active"));
      boolean affectsTarget = Boolean.parseBoolean((String) puzzleData.get("affects_target"));
      boolean affectsPlayer = Boolean.parseBoolean((String) puzzleData.get("affects_player"));
      String solution = (String) puzzleData.get("solution");
      int value = parseIntOrDefault(puzzleData.get("value"), 0);
      String description = (String) puzzleData.get("description");
      String effects = (String) puzzleData.get("effects");
      String target = (String) puzzleData.get("target");

      Puzzle puzzle = new Puzzle(name, active, affectsTarget, affectsPlayer, solution, value,
              description, effects, target);
      puzzles.put(name.toUpperCase(), puzzle);
    }
  }

  /**
   * Loads monster data from the JSON array and creates Monster objects.
   *
   * @param monstersArray The JSON array containing monster data
   */
  private void loadMonsters(JSONArray monstersArray) {
    if (monstersArray == null) {
      return;
    }

    for (Object obj : monstersArray) {
      JSONObject monsterData = (JSONObject) obj;

      String name = (String) monsterData.get("name");
      String description = (String) monsterData.get("description");
      boolean active = Boolean.parseBoolean((String) monsterData.get("active"));
      int damage = parseIntOrDefault(monsterData.get("damage"), 5);
      boolean canAttack = Boolean.parseBoolean((String) monsterData.get("can_attack"));
      String attackDescription = (String) monsterData.get("attack");
      String effects = (String) monsterData.get("effects");
      int value = parseIntOrDefault(monsterData.get("value"), 0);
      String solution = (String) monsterData.get("solution");
      String target = (String) monsterData.get("target");

      Monster monster = new Monster(name, description, active, damage, canAttack,
              attackDescription, effects, value, solution, target);
      monsters.put(name.toUpperCase(), monster);
    }
  }

  /**
   * Establishes connections between rooms based on exit information.
   * This is called after all rooms are loaded to ensure all room references exist.
   */
  private void connectRooms() {
    for (Room room : rooms.values()) {
      for (Direction dir : Direction.values()) {
        String targetRoomNumber = room.getExitRoomNumber(dir);

        // Skip if no exit in this direction (0) or if there's a puzzle/monster blocking (-n)
        if (targetRoomNumber.equals("0")) {
          continue;
        }

        // If positive, direct connection to another room
        if (Integer.parseInt(targetRoomNumber) > 0) {
          Room targetRoom = rooms.get(targetRoomNumber);
          if (targetRoom != null) {
            room.setExit(dir, targetRoom);
          }
        }
        // If negative, there's a puzzle or monster blocking; will handle this during gameplay
      }
    }
  }

  /**
   * Utility method to parse integer values from JSON, providing a default if parsing fails.
   *
   * @param value        The value to parse
   * @param defaultValue The default value to return if parsing fails
   * @return The parsed integer value or the default value
   */
  private int parseIntOrDefault(Object value, int defaultValue) {
    if (value == null) {
      return defaultValue;
    }

    try {
      if (value instanceof String) {
        return Integer.parseInt((String) value);
      } else if (value instanceof Number) {
        return ((Number) value).intValue();
      }
    } catch (NumberFormatException e) {
      // Ignore and return default
    }

    return defaultValue;
  }

  /**
   * Gets the player object representing the user in the game world.
   *
   * @return The Player object
   */
  public Player getPlayer() {
    return player;
  }

  /**
   * Sets the player's name.
   *
   * @param name The name to set for the player
   */
  public void setPlayerName(String name) {
    player.setName(name);
  }

  /**
   * Gets a room by its unique room number.
   *
   * @param roomNumber The unique identifier for the room
   * @return The Room object with the specified number, or null if not found
   */
  public Room getRoom(String roomNumber) {
    return rooms.get(roomNumber);
  }

  /**
   * Retrieves a puzzle by its name.
   *
   * @param name The name of the puzzle to retrieve
   * @return The Puzzle object with the specified name, or null if not found
   */
  public Puzzle getPuzzleByName(String name) {
    return puzzles.get(name.toUpperCase());
  }

  /**
   * Gets the name of the game.
   *
   * @return The game name as defined in the game data
   */
  public String getGameName() {
    return gameName;
  }

  /**
   * Attempts to solve a puzzle or defeat a monster in the player's current room
   * by applying the provided solution.
   * If successful, updates the player's score and unblocks any paths that were blocked.
   *
   * @param solution The solution to apply
   * @return true if the solution was successful, false otherwise
   */
  public boolean applySolution(String solution) {
    Room currentRoom = player.getCurrentRoom();

    // Check if there's a puzzle in the room
    if (currentRoom.getPuzzle() != null && currentRoom.getPuzzle().isActive()) {
      Puzzle puzzle = currentRoom.getPuzzle();
      if (puzzle.solve(solution)) {
        // Update player score
        player.addScore(puzzle.getValue());

        // Unblock paths
        for (Direction dir : Direction.values()) {
          String exitNumber = currentRoom.getExitRoomNumber(dir);
          if (Integer.parseInt(exitNumber) < 0) {
            // Convert negative to positive to unblock
            currentRoom.setExitRoomNumber(dir, String.valueOf(Math.abs
                    (Integer.parseInt(exitNumber))));
            // Set the actual exit
            Room targetRoom = rooms.get(String.valueOf(Math.abs(Integer.parseInt(exitNumber))));
            if (targetRoom != null) {
              currentRoom.setExit(dir, targetRoom);
            }
          }
        }
        return true;
      }
    }

    // Check if there's a monster in the room
    if (currentRoom.getMonster() != null && currentRoom.getMonster().isActive()) {
      Monster monster = currentRoom.getMonster();
      if (monster.getSolution().equalsIgnoreCase(solution)) {
        monster.defeat();
        // Update Players score
        player.addScore(monster.getValue());

        // Unblock paths
        for (Direction dir : Direction.values()) {
          String exitNumber = currentRoom.getExitRoomNumber(dir);
          if (Integer.parseInt(exitNumber) < 0) {
            // Convert negative to positive to unblock
            currentRoom.setExitRoomNumber(dir, String.valueOf(Math.abs
                    (Integer.parseInt(exitNumber))));
            // Set the actual exit
            Room targetRoom = rooms.get(String.valueOf(Math.abs(Integer.parseInt(exitNumber))));
            if (targetRoom != null) {
              currentRoom.setExit(dir, targetRoom);
            }
          }
        }
        return true;
      }
    }

    return false;
  }

  /**
   * Saves the current game state to a JSON file.
   * Includes player data, inventory, room states, and other game elements.
   *
   * @param filename The path where the save file will be created
   * @throws IOException If there is an error writing to the file
   */
  public void saveGame(String filename) throws IOException {
    JSONObject saveData = new JSONObject();

    // Save player data
    JSONObject playerData = new JSONObject(); // Fixed typo: JSONobject to JSONObject
    playerData.put("name", player.getName());
    playerData.put("health", player.getHealth());
    playerData.put("score", player.getScore());
    playerData.put("current_room", player.getCurrentRoom().getRoomNumber());

    // Save inventory
    JSONArray inventoryData = new JSONArray();
    for (Item item : player.getInventory()) {
      JSONObject itemData = new JSONObject();
      itemData.put("name", item.getName());
      itemData.put("uses_remaining", item.getUsesRemaining());
      inventoryData.add(itemData);
    }
    playerData.put("inventory", inventoryData);

    saveData.put("player", playerData);

    // Save room states
    JSONArray roomsData = new JSONArray();
    for (Room room : rooms.values()) {
      JSONObject roomData = new JSONObject();
      roomData.put("room_number", room.getRoomNumber());

      // Save puzzle state
      if (room.getPuzzle() != null) {
        roomData.put("puzzle_active", room.getPuzzle().isActive());
      }

      // Save monster state
      if (room.getMonster() != null) {
        roomData.put("monster_active", room.getMonster().isActive());
      }

      // Save room exits
      JSONObject exitsData = new JSONObject();
      for (Direction dir : Direction.values()) {
        exitsData.put(dir.toString(), room.getExitRoomNumber(dir));
      }
      roomData.put("exits", exitsData);

      // Save items in room
      JSONArray roomItemsData = new JSONArray();
      for (Item item : room.getItems()) {
        roomItemsData.add(item.getName());
      }
      roomData.put("items", roomItemsData);

      roomsData.add(roomData);
    }

    saveData.put("rooms", roomsData);
    saveData.put("game_name", gameName);
    saveData.put("version", version);

    try (FileWriter file = new FileWriter(filename)) {
      file.write(saveData.toJSONString());
    }
  }

  /**
   * Loads a previously saved game state from a JSON file.
   * Restores player data, inventory, room states, and other game elements.
   *
   * @param filename The path to the save file to load
   * @throws IOException    If there is an error reading the file
   * @throws ParseException If there is an error parsing the JSON data
   */
  public void loadGame(String filename) throws IOException, org.json.simple.parser.ParseException {
    JSONParser parser = new JSONParser();

    try (FileReader file = new FileReader(filename)) {
      JSONObject saveData = (JSONObject) parser.parse(file);

      // Load player data
      JSONObject playerData = (JSONObject) saveData.get("player");
      String playerName = (String) playerData.get("name");
      long health = (Long) playerData.get("health");
      long score = (Long) playerData.get("score");
      String currentRoomNumber = (String) playerData.get("current_room");

      // Load inventory
      JSONArray inventoryData = (JSONArray) playerData.get("inventory");
      List<Item> inventory = new ArrayList<>();
      for (Object obj : inventoryData) {
        JSONObject itemData = (JSONObject) obj;
        String itemName = (String) itemData.get("name");
        long usesRemaining = (Long) itemData.get("uses_remaining");

        Item item = items.get(itemName.toUpperCase());
        if (item != null) {
          item.setUsesRemaining((int) usesRemaining);
          inventory.add(item);
        }
      }

      // Set Player State
      player.setName(playerName);
      player.setHealth((int) health);
      player.setScore((int) score);
      player.setCurrentRoom(rooms.get(currentRoomNumber));
      player.setInventory(inventory);

      // Load room state
      JSONArray roomsData = (JSONArray) saveData.get("rooms");
      for (Object obj : roomsData) {
        JSONObject roomData = (JSONObject) obj;
        String roomNumber = (String) roomData.get("room_number");
        Room room = rooms.get(roomNumber);

        if (room != null) {
          // Load puzzle state
          if (roomData.containsKey("puzzle_active") && room.getPuzzle() != null) {
            boolean puzzleActive = (Boolean) roomData.get("puzzle_active");
            room.getPuzzle().setActive(puzzleActive);
          }

          // Load monster state
          if (roomData.containsKey("monster_active") && room.getMonster() != null) {
            boolean monsterActive = (Boolean) roomData.get("monster_active");
            room.getMonster().setActive(monsterActive);
          }

          // Load room exits
          if (roomData.containsKey("exits")) {
            JSONObject exitsData = (JSONObject) roomData.get("exits");
            for (Direction dir : Direction.values()) {
              String exitNumber = (String) exitsData.get(dir.toString());
              room.setExitRoomNumber(dir, exitNumber);

              // Update actual exit connections
              if (!exitNumber.equals("0") && Integer.parseInt(exitNumber) > 0) {
                Room targetRoom = rooms.get(exitNumber);
                if (targetRoom != null) {
                  room.setExit(dir, targetRoom);
                }
              } else {
                room.setExit(dir, null);
              }
            }
          }

          // Load items in room
          if (roomData.containsKey("items")) {
            JSONArray roomsItemsData = (JSONArray) roomData.get("items");
            room.clearItems();
            for (Object itemObj : roomsItemsData) {
              String itemName = (String) itemObj;
              Item item = items.get(itemName.toUpperCase());
              if (item != null) {
                room.addItem(item);
              }
            }
          }
        }
      }
    }
  }
}