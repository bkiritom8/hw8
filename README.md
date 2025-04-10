README.txt

Project Title: Data-Driven Adventure Game Engine  
Course: CS5004 - Object Oriented Design  
Team Members:
- Theresa Coleman (coleman.t@northeastern.edu)
- Zuzu Nyirakanyange (nyirakanyange.z@northeastern.edu)
- Nishnath Kandarpa (kandarpa.n@northeastern.edu)
- Bhargav Pamidighantam (pamidighantam.b@northeastern.edu)
- Kaige Zheng (zheng.kaig@northeastern.edu)

------------------------------------------------------------
Overview:
This project implements a modular, extensible text-based adventure game engine in Java. Players explore a virtual world by navigating between rooms, collecting and using items, solving puzzles, and confronting monsters. The entire game is defined using JSON configuration files, allowing creators to define new adventures without changing the underlying code.

------------------------------------------------------------
Key Components:
- **Player**: Tracks health, score, inventory, and active status effects.
- **Room**: Represents locations with exits and contains fixtures, items, puzzles, and monsters.
- **Item**: Collectible objects that may have weight, usage limits, or special effects.
- **Fixture**: Non-movable objects that players interact with, such as crafting stations or clue providers.
- **Monster**: Opponents that can block paths and be defeated using specific items.
- **Puzzle**: Logic-based challenges that, when solved, can change room configurations or grant access to new areas.

------------------------------------------------------------
Design Evolution from HW7:
Our design for HW8 is an implementation of the analysis and high-level architecture developed in HW7. The following major changes were made:

1. **Simplified Class Structure**:
    - We eliminated unnecessary inheritance and instead favored clear, concise classes with single responsibilities.

2. **Robust JSON Integration**:
    - All game entities (rooms, items, puzzles, monsters, fixtures) are now dynamically loaded from JSON files at runtime.

3. **Enhanced Room Navigation Logic**:
    - Exits may be blocked based on the presence of unsolved puzzles or active monsters. Logic now determines when exits become available.

4. **Improved Inventory Management**:
    - Items now have weight and use limits. The player can carry only what they have capacity for, and some items are consumable or single-use.

5. **Health and Status System**:
    - Player health decreases upon monster attacks or other events. Health thresholds trigger different status effects (e.g., weakened state).

6. **Save/Restore Functionality**:
    - Players can save their current state to a file and resume later, supporting extended play sessions and testing.

7. **Game Engine and Controller Layering**:
    - `GameEngineApp` and `GameController` coordinate model behavior and user interaction, isolating the logic from direct I/O operations. They serve as the main interface for processing user commands and integrating the game model (including puzzles, items, and rooms).

8. **Scenario Support**:
    - Each of the design scenarios described in HW7 (monster drops, puzzle unlocking, crafting, etc.) has a concrete implementation in HW8.

------------------------------------------------------------
Usage Instructions:
1. Open the project in IntelliJ IDEA.
2. Ensure you are using Java 17 or higher.
3. Run the `GameEngineApp` class to start the game.
4. The game will automatically load the world from JSON files located in `resources/`.
5. Use the following command syntax to play:
    - `go [direction]` (e.g., `go north`)
    - `pickup [item]`
    - `use [item] on [target]`
    - `solve puzzle [answer]`
    - `inventory`
    - `look`
    - `save` or `load` to persist or resume a session

------------------------------------------------------------
Implemented Design Scenarios:
- **Monster Loot Drops Key**: Defeating a monster drops an item needed to solve a downstream puzzle.
- **Dynamic Monster Encounter**: Monster blocks an exit and requires specific item usage to defeat.
- **Puzzle Unlocks Hidden Room**: Solving a puzzle dynamically updates room connections.
- **Fixture-based Crafting System**: Specific item combinations through a fixture yield a new item required to progress.

------------------------------------------------------------
Assumptions:
- The world is fully described via JSON before gameplay begins.
- One player instance is active throughout the game.
- Items, puzzles, and monster logic are pre-defined and not dynamically generated during gameplay.

------------------------------------------------------------
Acknowledgments:
- Diagrams created using PlantUML and draw.io
- References: Visual Paradigm, GeeksForGeeks, Creately, Game Programming Patterns
- Thanks to course staff for ongoing support and guidance.

------------------------------------------------------------
Future Improvements:
- Add advanced NPCs with dialogue systems.
- Expand combat to include probabilistic or turn-based encounters.
- Introduce GUI or web front-end for better accessibility.
- Enable multiplayer or branching narrative support.

