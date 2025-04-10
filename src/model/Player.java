package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a player in the game world.
 * The player can move between rooms, carry items, engage in combat,
 * and accumulate score.
 */
public class Player {
  private String name;
  private int health;
  private final List<Item> inventory;
  private Room currentRoom;
  private int score;
  private static final int MAX_WEIGHT = 13;
  private static final int MAX_HEALTH = 100;
  private final int attackPower;
  private final int criticalChance;

  /**
   * Creates a new player in the specified starting room.
   *
   * @param startRoom The room where the player starts
   * @throws IllegalArgumentException if startRoom is null
   */
  public Player(Room startRoom) {
    if (startRoom == null) {
      throw new IllegalArgumentException("Start room cannot be null");
    }
    this.name = "Player";
    this.health = MAX_HEALTH;
    this.inventory = new ArrayList<>();
    this.currentRoom = startRoom;
    this.score = 0;
    this.attackPower = 10;
    this.criticalChance = 15;
  }

  /**
   * Gets the player's name.
   *
   * @return The player's name
   */
  public String getName() {
    return this.name;
  }

  /**
   * Reduces the player's health by the specified amount.
   * Health cannot go below 0.
   *
   * @param amount The amount of damage to take
   */
  public void takeDamage(int amount) {
    if (amount < 0) {
      throw new IllegalArgumentException("Damage amount cannot be negative");
    }
    this.health = Math.max(0, this.health - amount);
  }

  /**
   * Gets a description of the player's health status.
   *
   * @return A string describing the player's health status
   */
  public String getHealthStatus() {
    if (health <= 0) {
      return "ASLEEP";
    }
    if (health < 40) {
      return "WOOZY";
    }
    if (health < 70) {
      return "FATIGUED";
    }
    return "AWAKE";
  }

  /**
   * Gets the player's current health.
   *
   * @return The player's health
   */
  public int getHealth() {
    return this.health;
  }

  /**
   * Attempts to add an item to the player's inventory.
   *
   * @param item The item to add
   * @return true if the item was added, false if it would exceed weight limit
   * @throws IllegalArgumentException if item is null
   */
  public boolean addToInventory(Item item) {
    if (item == null) {
      throw new IllegalArgumentException("Item cannot be null");
    }
    if (getInventoryWeight() + item.getWeight() <= MAX_WEIGHT) {
      inventory.add(item);
      return true;
    }
    return false;
  }

  /**
   * Removes an item from the player's inventory.
   *
   * @param item The item to remove
   * @return true if the item was removed, false otherwise
   * @throws IllegalArgumentException if item is null
   */
  public boolean removeFromInventory(Item item) {
    if (item == null) {
      throw new IllegalArgumentException("Item cannot be null");
    }
    return inventory.remove(item);
  }

  /**
   * Gets an item from the inventory by name.
   *
   * @param itemName The name of the item to find
   * @return The item if found, null otherwise
   * @throws IllegalArgumentException if itemName is null or empty
   */
  public Item getItemFromInventory(String itemName) {
    if (itemName == null || itemName.trim().isEmpty()) {
      throw new IllegalArgumentException("Item name cannot be null or empty");
    }
    return inventory.stream()
            .filter(item -> item.getName().equalsIgnoreCase(itemName))
            .findFirst()
            .orElse(null);
  }

  /**
   * Gets the total weight of all items in the inventory.
   *
   * @return The total weight of the inventory
   */
  public int getInventoryWeight() {
    return inventory.stream()
            .mapToInt(Item::getWeight)
            .sum();
  }

  /**
   * Gets a copy of the player's inventory.
   *
   * @return A new list containing all items in the inventory
   */
  public List<Item> getInventory() {
    return new ArrayList<>(inventory);
  }

  /**
   * Sets the player's inventory to the specified list.
   *
   * @param inventory The new inventory list
   * @throws IllegalArgumentException if inventory is null
   */
  public void setInventory(List<Item> inventory) {
    if (inventory == null) {
      throw new IllegalArgumentException("Inventory cannot be null");
    }
    this.inventory.clear();
    this.inventory.addAll(inventory);
  }

  /**
   * Gets the player's current room.
   *
   * @return The current room
   */
  public Room getCurrentRoom() {
    return this.currentRoom;
  }

  /**
   * Gets the maximum weight the player can carry.
   *
   * @return The maximum weight
   */
  public int getMaxWeight() {
    return MAX_HEALTH;
  }

  /**
   * Sets the player's current room.
   *
   * @param room The new room
   * @throws IllegalArgumentException if room is null
   */
  public void setCurrentRoom(Room room) {
    if (room == null) {
      throw new IllegalArgumentException("Room cannot be null");
    }
    this.currentRoom = room;
  }

  /**
   * Adds points to the player's score.
   *
   * @param points The points to add
   * @throws IllegalArgumentException if points is negative
   */
  public void addScore(int points) {
    if (points < 0) {
      throw new IllegalArgumentException("Points cannot be negative");
    }
    this.score += points;
  }

  /**
   * Sets the player's name.
   *
   * @param name The name to set for the player
   */
  public void setName(String name) {
    if (name == null || name.trim().isEmpty()) {
      throw new IllegalArgumentException("Name cannot be null or empty");
    }
    this.name = name;
  }

  /**
   * Sets the player's score.
   *
   * @param score The new score
   * @throws IllegalArgumentException if score is negative
   */
  public void setScore(int score) {
    if (score < 0) {
      throw new IllegalArgumentException("Score cannot be negative");
    }
    this.score = score;
  }

  /**
   * Sets the player's health.
   *
   * @param health The new health value
   * @throws IllegalArgumentException if health is negative
   */
  public void setHealth(int health) {
    if (health < 0) {
      throw new IllegalArgumentException("Health cannot be negative");
    }
    this.health = Math.min(health, MAX_HEALTH);
  }

  /**
   * Gets the player's current score.
   *
   * @return The current score
   */
  public int getScore() {
    return this.score;
  }

  /**
   * Gets the player's rank based on their score.
   *
   * @return A string representing the player's rank
   */
  public String getRank() {
    if (score >= 1000) {
      return "Adventure Master";
    }
    if (score >= 750) {
      return "Expert Explorer";
    }
    if (score >= 500) {
      return "Seasoned Adventurer";
    }
    if (score >= 250) {
      return "Novice Explorer";
    }
    return "Beginner";
  }

  /**
   * Attacks a monster with a chance for critical hits.
   *
   * @param monster The monster to attack
   * @return The damage dealt to the monster, or 0 if the attack was unsuccessful
   * @throws IllegalArgumentException if monster is null
   */
  public int attack(Monster monster) {
    if (monster == null) {
      throw new IllegalArgumentException("Monster cannot be null");
    }

    if (!monster.isActive()) {
      return 0;
    }

    int damage = this.attackPower;
    boolean isCritical = isCriticalHit();
    if (isCritical) {
      damage *= 2;
    }

    monster.takeDamage(damage, isCritical);
    return damage;
  }

  /**
   * Determines if the attack is a critical hit.
   *
   * @return true if the attack is a critical hit
   */
  private boolean isCriticalHit() {
    return Math.random() < criticalChance;
  }

  /**
   * Checks if the player can move in a given direction.
   *
   * @param direction The direction to check
   * @return true if the player can move in that direction, false otherwise
   * @throws IllegalArgumentException if direction is null
   */
  public boolean canMove(Direction direction) {
    if (direction == null) {
      throw new IllegalArgumentException("Direction cannot be null");
    }
    return currentRoom.getExit(direction) != null;
  }

  /**
   * Moves the player in a given direction.
   *
   * @param direction The direction to move
   * @return true if the move was successful, false otherwise
   * @throws IllegalArgumentException if direction is null
   */
  public boolean move(Direction direction) {
    if (direction == null) {
      throw new IllegalArgumentException("Direction cannot be null");
    }
    Room nextRoom = currentRoom.getExit(direction);
    if (nextRoom != null) {
      currentRoom = nextRoom;
      return true;
    }
    return false;
  }
}