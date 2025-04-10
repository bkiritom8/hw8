package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * tests for the solution class
 */
class SolutionTest {
    
    /**
     * test creating a solution
     */
    @Test
    public void testCreateSolution() {
        // create an item solution
        Solution itemSolution = new Solution(SolutionType.ITEM, "key");
        
        // check its properties
        assertEquals(SolutionType.ITEM, itemSolution.getType());
        assertEquals("key", itemSolution.getValue());
        
        // create a text solution
        Solution textSolution = new Solution(SolutionType.ANSWER, "open sesame");
        
        // check its properties
        assertEquals(SolutionType.ANSWER, textSolution.getType());
        assertEquals("open sesame", textSolution.getValue());
    }
    
    /**
     * test matching solutions
     */
    @Test
    public void testMatchingSolutions() {
        // create two matching solutions
        Solution solution1 = new Solution(SolutionType.ITEM, "key");
        Solution solution2 = new Solution(SolutionType.ITEM, "KEY");
        
        // check that they match
        assertTrue(solution1.matches(solution2));
        assertTrue(solution2.matches(solution1));
        
        // create non-matching solutions (different values)
        Solution solution3 = new Solution(SolutionType.ITEM, "sword");
        
        // check that they don't match
        assertFalse(solution1.matches(solution3));
        
        // create non-matching solutions (different types)
        Solution solution4 = new Solution(SolutionType.ANSWER, "key");
        
        // check that they don't match
        assertFalse(solution1.matches(solution4));
    }
    
    /**
     * test case insensitivity
     */
    @Test
    public void testCaseInsensitivity() {
        // create solutions with different cases
        Solution solution1 = new Solution(SolutionType.ANSWER, "Password");
        Solution solution2 = new Solution(SolutionType.ANSWER, "password");
        Solution solution3 = new Solution(SolutionType.ANSWER, "PASSWORD");
        
        // check they all match each other
        assertTrue(solution1.matches(solution2));
        assertTrue(solution1.matches(solution3));
        assertTrue(solution2.matches(solution3));
    }
}