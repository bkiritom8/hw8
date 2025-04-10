package model;

import java.util.Objects;

/**
 * Represents a fixture element in the game (e.g., a computer, desk, painting, etc.).
 * Fields match the JSON structure. Some fields like puzzle or states may be null.
 */
public class Fixture {
  private String name;
  private int weight;       // Stored as an integer (JSON weight is a string)
  private String puzzle;    // Optional: puzzle associated with the fixture
  private String states;    // Optional: state model (not used in HW8)
  private String description;
  private String picture;   // Optional: path or URL to a picture

  /** TODO: Add method documentation. */
  public Fixture() {}

  /**
   * Constructs a Fixture with all possible fields.
   *
   * @param name the name of the fixture
   * @param weight the weight of the fixture
   * @param puzzle associated puzzle (nullable)
   * @param states state model (nullable)
   * @param description description of the fixture
   * @param picture path or URL to a picture (nullable)
   */
  public Fixture(String name, int weight, String puzzle, String states,
                 String description, String picture) {
    this.name = name;
    this.weight = weight;
    this.puzzle = puzzle;
    this.states = states;
    this.description = description;
    this.picture = picture;
  }

  /** TODO: Add method documentation. */
  public Fixture(String name, int weight, String description) {
    this(name, weight, null, null, description, null);
  }

  /** TODO: Add method documentation. */
  public String getName() {
    return name;
  }

  /** TODO: Add method documentation. */
  public int getWeight() {
    return weight;
  }

  /** TODO: Add method documentation. */
  public String getPuzzle() {
    return puzzle;
  }

  /** TODO: Add method documentation. */
  public String getStates() {
    return states;
  }

  /** TODO: Add method documentation. */
  public String getDescription() {
    return description;
  }

  /** TODO: Add method documentation. */
  public String getPicture() {
    return picture;
  }

  @Override
  public String toString() {
    return "Fixture [name=" + name + ", weight=" + weight
            + ", description=" + description + "]";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Fixture fixture)) {
      return false;
    }
    return weight == fixture.weight
            && Objects.equals(name, fixture.name)
            && Objects.equals(description, fixture.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, weight, description);
  }

  /** TODO: Add method documentation. */
  public boolean interact(Player ignoredPlayer) {
    return false;
  }
}