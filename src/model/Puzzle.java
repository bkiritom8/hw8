package model;

/**
 * a puzzle that players can solve
 */
public class Puzzle {
    // store basic info about the puzzle
    private String name;
    private boolean active;
    private boolean affectsTarget;
    private boolean affectsPlayer;
    private String solution;
    private int value;
    private String description;
    private String effects;
    private String target;
    
    /**
     * create a new puzzle
     */
    public Puzzle(String name, boolean active, boolean affectsTarget, boolean affectsPlayer,
                String solution, int value, String description, String effects, String target) {
        // save all the info about this puzzle
        this.name = name;
        this.active = active;
        this.affectsTarget = affectsTarget;
        this.affectsPlayer = affectsPlayer;
        this.solution = solution;
        this.value = value;
        this.description = description;
        this.effects = effects;
        this.target = target;
    }
    
    /**
     * try to solve the puzzle
     */
    public boolean solve(String answer) {
        // can't solve inactive puzzles
        if (!active) {
            return false;
        }
        
        // convert the answer to a Solution object
        Solution providedSolution;
        
        // check if this is a word answer (has quotes)
        if (solution.startsWith("'") && solution.endsWith("'")) {
            // create a text answer solution
            providedSolution = new Solution(SolutionType.ANSWER, answer);
            
            // get the correct solution (without quotes)
            String correctAnswer = solution.substring(1, solution.length() - 1);
            Solution correctSolution = new Solution(SolutionType.ANSWER, correctAnswer);
            
            // check if they match
            if (providedSolution.matches(correctSolution)) {
                active = false;  // puzzle is now solved
                return true;
            }
        } 
        // check if this is an item answer (no quotes)
        else {
            // create an item solution
            providedSolution = new Solution(SolutionType.ITEM, answer);
            
            // create the correct solution
            Solution correctSolution = new Solution(SolutionType.ITEM, solution);
            
            // check if they match
            if (providedSolution.matches(correctSolution)) {
                active = false;  // puzzle is now solved
                return true;
            }
        }
        
        // wrong answer
        return false;
    }
    
    // simple getters
    public String getName() {
        return name;
    }
    
    public boolean isActive() {
        return active;
    }
    
    public void setActive(boolean active) {
        this.active = active;
    }
    
    public boolean affectsTarget() {
        return affectsTarget;
    }
    
    public boolean affectsPlayer() {
        return affectsPlayer;
    }
    
    public String getSolution() {
        return solution;
    }
    
    public int getValue() {
        return value;
    }
    
    public String getDescription() {
        return description;
    }
    
    public String getEffects() {
        return effects;
    }
    
    public String getTarget() {
        return target;
    }
}
