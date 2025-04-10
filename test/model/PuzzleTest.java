package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * tests for the puzzle class
 */
class PuzzleTest {
    
    /**
     * test creating a puzzle
     */
    @Test
    public void testCreatePuzzle() {
        // make a test puzzle
        Puzzle puzzle = new Puzzle(
            "Test Puzzle",     // name
            true,              // active
            true,              // affects target
            false,             // affects player
            "'answer'",        // solution
            10,                // value
            "Test puzzle",     // description
            "It worked!",      // effects
            "room:1"           // target
        );
        
        // check that all properties were set right
        assertEquals("Test Puzzle", puzzle.getName());
        assertTrue(puzzle.isActive());
        assertTrue(puzzle.affectsTarget());
        assertFalse(puzzle.affectsPlayer());
        assertEquals("'answer'", puzzle.getSolution());
        assertEquals(10, puzzle.getValue());
        assertEquals("Test puzzle", puzzle.getDescription());
        assertEquals("It worked!", puzzle.getEffects());
        assertEquals("room:1", puzzle.getTarget());
    }
    
    /**
     * test solving a text puzzle
     */
    @Test
    public void testSolveTextPuzzle() {
        // make a puzzle with a text answer
        Puzzle puzzle = new Puzzle(
            "Riddle",          // name
            true,              // active
            true,              // affects target
            false,             // affects player
            "'magic'",         // solution (with quotes)
            10,                // value
            "Say the magic word",  // description
            "The door opens",  // effects
            "door"             // target
        );
        
        // try a wrong answer
        assertFalse(puzzle.solve("please"));
        
        // puzzle should still be active
        assertTrue(puzzle.isActive());
        
        // try the right answer
        assertTrue(puzzle.solve("magic"));
        
        // puzzle should now be inactive
        assertFalse(puzzle.isActive());
    }
    
    /**
     * test solving an item puzzle
     */
    @Test
    public void testSolveItemPuzzle() {
        // make a puzzle that needs an item
        Puzzle puzzle = new Puzzle(
            "Lock",            // name
            true,              // active
            false,             // affects player
            false,             // affects target
            "key",             // solution (no quotes)
            5,                 // value
            "A locked door",   // description
            "The door unlocks", // effects
            "door"             // target
        );
        
        // try a wrong item
        assertFalse(puzzle.solve("hammer"));
        
        // puzzle should still be active
        assertTrue(puzzle.isActive());
        
        // try the right item
        assertTrue(puzzle.solve("key"));
        
        // puzzle should now be inactive
        assertFalse(puzzle.isActive());
    }
    
    /**
     * test that case doesn't matter
     */
    @Test
    public void testCaseDoesntMatter() {
        // make a puzzle
        Puzzle puzzle = new Puzzle(
            "Case Test",       // name
            true,              // active
            false,             // affects target
            false,             // affects player
            "'Password'",      // solution (with quotes)
            10,                // value
            "Enter password",  // description
            "It works",        // effects
            "test"             // target
        );
        
        // try lowercase
        assertTrue(puzzle.solve("password"));
        
        // make another puzzle
        puzzle = new Puzzle(
            "Item Case",       // name
            true,              // active
            false,             // affects target 
            false,             // affects player
            "Golden Key",      // solution (no quotes)
            5,                 // value
            "Locked chest",    // description
            "Chest opens",     // effects
            "chest"            // target
        );
        
        // try different case
        assertTrue(puzzle.solve("golden key"));
    }
    
    /**
     * test inactive puzzles
     */
    @Test
    public void testInactivePuzzle() {
        // make an inactive puzzle
        Puzzle puzzle = new Puzzle(
            "Inactive",        // name
            false,             // active (false)
            true,              // affects target
            false,             // affects player
            "'test'",          // solution
            10,                // value
            "Inactive puzzle", // description
            "Nothing happens", // effects
            "none"             // target
        );
        
        // check it's inactive
        assertFalse(puzzle.isActive());
        
        // try to solve it
        assertFalse(puzzle.solve("test"));
    }
    
    /**
     * test changing active state
     */
    @Test
    public void testChangeActiveState() {
        // make a puzzle
        Puzzle puzzle = new Puzzle(
            "Active Test",     // name
            true,              // active
            false,             // affects target
            false,             // affects player
            "'test'",          // solution
            10,                // value
            "Test puzzle",     // description
            "Works",           // effects
            "test"             // target
        );
        
        // check it's active
        assertTrue(puzzle.isActive());
        
        // change to inactive
        puzzle.setActive(false);
        
        // check it changed
        assertFalse(puzzle.isActive());
    }
}
