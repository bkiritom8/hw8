package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ItemTest {
  
  private Item testItem;
  
  @BeforeEach
  void setUp() {
    // Create standard test item
    testItem = new Item("Test Item", 2, 3, 3, 10, "Used!", "A test item");
  }

  @Test
  void testItemCreation() {
    // Test normal creation
    assertEquals("Test Item", testItem.getName());
    assertEquals(2, testItem.getWeight());
    assertEquals(3, testItem.getMaxUses());
    assertEquals(3, testItem.getUsesRemaining());
    assertEquals(10, testItem.getValue());
    assertEquals("Used!", testItem.getWhenUsed());
    assertEquals("A test item", testItem.getDescription());
    
    // Test invalid creation
    assertThrows(IllegalArgumentException.class, () -> 
            new Item("", 1, 3, 3, 10, "Used!", "A test item"));
    assertThrows(IllegalArgumentException.class, () -> 
            new Item("Test Item", 0, 3, 3, 10, "Used!", "A test item"));
    assertThrows(IllegalArgumentException.class, () -> 
            new Item("Test Item", 1, 3, 4, 10, "Used!", "A test item"));
  }

  @Test
  void testUse() {
    // Test successful use
    assertTrue(testItem.use());
    assertEquals(2, testItem.getUsesRemaining());
    
    // Use remaining uses
    testItem.use();
    testItem.use();
    
    // Test use when depleted
    assertFalse(testItem.use());
    assertEquals(0, testItem.getUsesRemaining());
  }

  @Test
  void testSetUsesRemaining() {
    // Test setting to a valid value
    testItem.setUsesRemaining(1);
    assertEquals(1, testItem.getUsesRemaining());
    
    // Test setting to zero
    testItem.setUsesRemaining(0);
    assertEquals(0, testItem.getUsesRemaining());
  }

  @Test
  void testGetters() {
    // Test all getters in one test
    assertEquals("Test Item", testItem.getName());
    assertEquals(2, testItem.getWeight());
    assertEquals(3, testItem.getMaxUses());
    assertEquals(3, testItem.getUsesRemaining());
    assertEquals(10, testItem.getValue());
    assertEquals("Used!", testItem.getWhenUsed());
    assertEquals("A test item", testItem.getDescription());
  }
}