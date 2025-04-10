package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


class MonsterTest {
  private Player lucas;
  private Monster orc;
  private String monsterDescription;
  private String monsterAttackDescription;
  private String monsterEffects;
  private String monsterSolution;

  @BeforeEach
  void setUp() {
    monsterDescription = "Orcs dwell deep in Dungeons and have a high damage output";
    monsterAttackDescription = "Orcs swing their club at players resulting in a high attack and " +
            "critical damage output";
    monsterEffects = "Attacks affect the ground uniformity and can cause the player to get stuck";
    monsterSolution = "Orcs might be strong, but are dumb. You can use their own effects " +
            "against them and attack them while they are stuck";
    Monster orc = new Monster("Orc", monsterDescription, true, 45, true,
            monsterAttackDescription, monsterEffects, 3000, monsterSolution, "lucas");
  }

  // Tests (Qt: 14)
  @Test
  public void testAttack() {
    orc.setActive(false);
    assertEquals(0, orc.attack(lucas));
  }

  @Test
  public void testDefeat() {
    orc.setActive(true);
    orc.defeat();
    assertFalse(orc.isActive());
    assertEquals(0, orc.getHealthPercentage());
  }

  @Test
  public void testTakeDamage() {
    // 1st damage from attack (+ critical)
    orc.takeDamage(80, true);
    assertEquals(20, orc.getHealthPercentage());

    // 2nd damage from attack
    orc.takeDamage(20, false);
    assertFalse(orc.isActive());
  }

  @Test
  public void testGetHealthPercentage() {
    assertEquals(100, orc.getHealthPercentage());
  }

  @Test
  public void testGetName() {
    assertEquals("Orc", orc.getName());
  }

  @Test
  public void testGetDescription() {
    assertEquals(monsterDescription, orc.getDescription());
  }

  @Test
  public void testGetDamage() {
    assertEquals(45, orc.getDamage());
  }

  @Test
  public void testGetAttackDescription() {
    assertEquals(monsterAttackDescription, orc.getAttackDescription());
  }

  @Test
  public void testGetEffects() {
    assertEquals(monsterEffects, orc.getEffects());
  }

  @Test
  public void testGetValue() {
    assertEquals(30000, orc.getValue());
  }

  @Test
  public void testGetSolution() {
    assertEquals(monsterSolution, orc.getSolution());
  }

  @Test
  public void testGetTarget() {
    assertEquals("lucas", orc.getTarget());
  }

  @Test
  public void testSetActiveAndIsActive() {
    orc.setActive(false);
    assertFalse(orc.isActive());

    orc.setActive(true);
    assertTrue(orc.isActive());
  }

  @Test
  public void testCanAttack() {
    assertTrue(orc.canAttack());
  }
}