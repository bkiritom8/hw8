package model;

/**
 * a solution to a puzzle
 */
public class Solution {
    // the type of solution (ITEM or ANSWER)
    private SolutionType type;
    // the value of the solution (item name or text answer)
    private String value;
    
    /**
     * create a new solution
     */
    public Solution(SolutionType type, String value) {
        // save the type and value
        this.type = type;
        this.value = value;
    }
    
    /**
     * check if this solution matches another one
     */
    public boolean matches(Solution other) {
        // first check if types match
        if (this.type != other.getType()) {
            return false;
        }
        
        // then check if values match (ignoring case)
        return this.value.equalsIgnoreCase(other.getValue());
    }
    
    /**
     * get the solution type
     */
    public SolutionType getType() {
        return type;
    }
    
    /**
     * get the solution value
     */
    public String getValue() {
        return value;
    }
}
