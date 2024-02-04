
Model = Business Logic
- setup atoms
- compute ray path
- compute score
- add atom guess

View = UI Logic
- display board (hexagons, numbered arrows, atoms, circles of influence)
- display score

Controller = Input Logic
- start game -> setup atoms (Model), display board (View)
- inquire entry -> compute ray path (Model)
- place atom -> add atom guess (Model)
- end round -> display board (View), compute score (Model)
