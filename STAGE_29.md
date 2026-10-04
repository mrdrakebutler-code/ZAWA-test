# ZAWA: Evolved NeoForge 1.21.1 — Stage 29

Stage 29 migrates the zoo infrastructure inventory/menu layer from the original Forge build.

Implemented:
- NeoForge MenuType registration for feeder, incubator, bug box, hydroponics table and mussel box.
- Persistent BaseContainerBlockEntity inventory implementations.
- Server-side machine ticking and incubator processing scaffold.
- Functional block interaction: right-clicking machine blocks opens the corresponding menu.
- Shift-click item movement between machine and player inventories.
- Feed/plant/fuel/result slot restrictions.
- Basic client AbstractContainerScreen registration for all five menus.
- Persistent inventory/progress NBT.

The original bytecode was used to preserve the menu families and slot structure. Specialized breeding/growth recipes, capture mechanics, and original GUI art remain later migration work.

Validation: 107 Java source files; balanced-brace scan passes. A full NeoForge Gradle build was not performed in this environment.
