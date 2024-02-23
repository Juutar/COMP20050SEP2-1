
##### Rendering Hexagon Board: 

The board is currently rendered at game start with size 3 for easier debugging. Size 3 means that there are 3 layers of hexagons around the central hexagon in the grid. However, this means there are 37 hexagons in the grid. We mentioned that we would introduce the feature to resize it in sprint 4, when we will set the default size at 5 which will result in 91 hexagons.
Because this feature is mainly rendering, the test covers the number of Hexagons in the board depending on its size.

##### Place / Remove Atoms

The "guess atoms" (that the player places) are rendered on click when the "set rays" button is not activated. 
The "true atoms" (that are placed by the game) are rendered when the "show true atoms" buttons is activated.
The tests ensure that:
- the atoms are properly placed when the board is clicked
- no more than the max amount of atoms can be added to the board this way

##### Rendering Atoms

The "guess atoms" (that the player places) are differentiated from the "true atoms" (that are placed by the game) by their colour. They are sized depending on their hexagon size and positioned in the middle of their hexagon.
The tests ensure that:
- the atoms have the proper colour code depending on they type.
- the atoms are properly placed and sized

##### Randomly Placing Atoms

The required number (currently 6) of "true atoms" are randomly placed when the game starts.
The test ensures that two game instances don't have the same set of "true atoms".

##### Atom Properties

The true/guess property is described in the Place / Remove Atoms part of this document. The correct/incorrect property responsibility was moved from the Atom class to the Hexagon class, because the correctness must be computed based on the presence or not of 2 atoms in one hexagon. 
When the "show true atoms" button is clicked, the hexagons get assessed. It can be correct (the "guess atom" was properly placed), which displays a validation icon. It can be incorrect (the "guess atom" was improperly placed, or a "true atom" hexagon has no "guess atom"), which displays an error icon. Otherwise, it is neutral and nothing is displayed.
The tests ensure that:
- a hexagon with 2 atoms is marked as correct
- a hexagon with 1 atom is marked as incorrect
- a hexagon with no atom is not marked as anything

##### Rendering Circles of Influence

The circles of influence are created whenever an atom is created, and based on its atom's size, position and colour.
The test ensures that the colour, position and size are correct relative to the atom.


##### Rendering Entry Numbers

The hexagon edges that shape the outside of the board are associated a label displaying the edge number.
The test ensures that the number of labels for each hexagon is correct.

##### Rendering Entry Arrows

When the "set rays" button is clicked, atoms cannot be placed anymore. On sprint 2, when we will implement rays, this button will enable rays to be shot. In the meantime, atom previews are replaced with "ray pointer" previews that replace the closest edge label with a pointer indicating the ray direction for this edge.
The test ensures that:
- the closest edge is computed properly
- atoms cannot be placed when "set rays" is enabled.
