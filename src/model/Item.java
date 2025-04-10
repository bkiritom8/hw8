package model;

/**
 * Represents an item in the game world.
 * Each item has attributes like name, weight, max uses, current uses,
 * value, and a description of what happens when it is used.
 */
public class Item {
  private final String name;
  private final int weight;
  private final int maxUses;
  private int usesRemaining;
  private final int value;
  private final String whenUsed;
  private final String description;

  /**
   * Constructs an Item with the specified attributes.
   *
   * @param name          The name of the item
   * @param weight        The weight of the item
   * @param maxUses       The maximum number of uses for the item
   * @param usesRemaining The current number of uses remaining
   * @param value         The value of the item
   * @param whenUsed      A description of what happens when the item is used
   * @param description   A description of the item
   */
  public Item(String name,
              int weight,
              int maxUses,
              int usesRemaining,
              int value,
              String whenUsed,
              String description) {
    this.name = name;
    this.weight = weight;
    this.maxUses = maxUses;
    this.usesRemaining = usesRemaining;
    this.value = value;
    this.whenUsed = whenUsed;
    this.description = description;
  }

  /**
   * Uses the item, reducing the number of uses remaining.
   * Returns true if the item was successfully used, false if no uses are left.
   *
   * @return true if the item was used, false otherwise
   */
  public boolean use() {
    if (usesRemaining > 0) {
      usesRemaining--;
      return true;
    }
    return false;
  }

  /**
   * Gets the description of what happens when the item is used.
   *
   * @return The description of the item's effect
   */
  public String getWhenUsed() {
    return this.whenUsed;
  }

  /**
   * Gets the name of the item.
   *
   * @return The name of the item
   */
  public String getName() {
    return this.name;
  }

  /**
   * Gets the weight of the item.
   *
   * @return The weight of the item
   */
  public int getWeight() {
    return this.weight;
  }

  /**
   * Gets the maximum number of uses for the item.
   *
   * @return The maximum number of uses
   */
  public int getMaxUses() {
    return this.maxUses;
  }

  /**
   * Gets the number of uses remaining for the item.
   *
   * @return The number of uses remaining
   */
  public int getUsesRemaining() {
    return this.usesRemaining;
  }

  /**
   * Sets the number of uses remaining for the item.
   *
   * @param uses The new number of uses remaining
   */
  public void setUsesRemaining(int uses) {
    this.usesRemaining = uses;
  }

  /**
   * Gets the value of the item.
   *
   * @return The value of the item
   */
  public int getValue() {
    return this.value;
  }

  /**
   * Gets the description of the item.
   *
   * @return The description of the item
   */
  public String getDescription() {
    return this.description;
  }
}
