package model;

/**
 * A Direction enum that represents all the possible directions a player can move in.
 */
public enum Direction {
  NORTH, SOUTH, EAST, WEST;

  // Method
  /**
   * This method provides the direction opposite to the current direction.
   *
   * @return The opposite direction
   */
  public Direction getOpposite() {
    return switch (this) {
      case NORTH -> SOUTH;
      case SOUTH -> NORTH;
      case EAST -> WEST;
      case WEST -> EAST;
      default -> null;
    };
  }
}
