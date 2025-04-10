## **Game Scenarios**

### Scenario 1: Unlocking with Sealed Key

Unlocking the Sealed Key, the player discovers a locked door in the Library where the system reveals a puzzle requiring a SealedKey. When the player searches their inventory, the outcome depends on what they find. If the SealedKey exists, using it will unlock the door (earning 15 points) while the key remains in the inventory for future use. However, if the key isn't present, the system displays a message stating, "The door rejects your touch. Find the Sealed Key\!

### Scenario 2: Monster Loot Drops Key

Monster Loot Drops Key, after the player defeats the Dungeon Guardian monster, the system automatically adds the SealedKey to the room's available items. When the player attempts to pick up the key, the system performs an inventory weight check which, upon passing, adds the key to the player's inventory. With the SealedKey now in their possession, the puzzle previously discovered in the Library becomes solvable, allowing the player to progress in their adventure.

### Scenario 3: Player Encounters and Defeats a Monster

The player enters the Dungeon Room from the Castle Hall by moving east. Upon entering, they see a description indicating a fierce Troll is blocking the exit to the south, which would lead to the Treasure Chamber. The player has previously collected a Magic Sword item from the Armory. The player uses the Magic Sword on the Troll. Since the Troll is vulnerable to the Magic Sword, it is defeated and disappears. The room description updates to indicate the southern path is now clear. The player's score increases by the Troll's point value. The player can now move south to the Treasure Chamber.

### Scenario 4: Player Solves a Puzzle to Access a Hidden Room

The player is in the Library and notices a strange Bookshelf fixture with a riddle inscribed on it. The riddle reads: "What has keys but can't open locks?" The player examines the fixture further and decides to input the answer "piano" as the solution. The system recognizes this as the correct answer to the puzzle. The Bookshelf slides aside, revealing a previously hidden exit to the west leading to a Secret Study. The room description updates to include the new exit, and the player's score increases by the puzzle's point value. The player can now move west to explore the Secret Study, potentially finding valuable items.

### Scenario 5: Activating a Crafting Station

The player enters a Cave and notices an arrangement of wood and stones-this is a Crafting Station, implemented as a Fixture. The station appears to be inactive until the player has two essential items: iron and diamond. When the player interacts with the crafting station while carrying both essential items, the crafting process is activated. The two items are removed from the players inventory, and a new Item \- Diamond Sword \- is created and added to the players inventory. This new weapon is crucial for defeating a Monster in a future room.


### Scenario 6: A Surprising Encounter\!

As the player walks inside a dungeon, they find a treasure chest. The player tries to open the chest to look for items, but the chest suddenly attacks. It is actually a monster disguised as a chest. The only way to defeat it is by dropping another item they found in a Coral Cavern (connected to scenario 6).

If the player lacks the item, they are put to sleep and transported to another location that has the item to be picked up by solving a similar (or the same) puzzle. If the player has the item but does not use it, they will lose 30% of their health from the monster’s first attack and be paralyzed until the monster attacks twice), dealing a fixed 20% damage each time. If the player uses the item, the monster moves out of their way and lets the player explore the dungeon further.

In our case, the player already has the item, but does not use it, thereby taking damage. Fortunately, they still have some HP to drop the item, causing the monster to move further into the dungeon.