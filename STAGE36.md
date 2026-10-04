# Stage 36 - Interactive Enrichment

The user-supplied decompiled 1.20.1 archive materially improved this stage.
It contains javap bytecode for BallItem, IceTreatItem, Ball, IceTreat, SprinklerBlock,
TireSwingBlock, PuzzleFeederBlock, ScratchingPostBlock, RopeBlock, PerchingStandBlock,
HeatLampBlock and HeatRockBlock.

Recovered behavior used here:
- ball and ice-treat items place physical entities at the targeted block
- boomer/scented are distinct ball types
- apple/beef are distinct ice-treat types
- balls use bouncing physics; ice treats use sliding physics
- physical enrichment objects are damageable and can be picked back up
- entity_stats zawa:entities lists determine which species accept each object

This stage ports those physical enrichment objects and connects them to Stage 34/35
species enrichment data. More exact special block geometry/state behavior can now be
ported from the same archive in following stages.
