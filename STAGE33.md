# Stage 33 — Feeding, Diet, Thirst and Enrichment

This stage connects the previously recovered JSON-driven species diets to ZawaBaseEntity interactions.

Implemented:
- Diet foods restore hunger using the per-item byte value from EntityDiet.
- Favorite food grants a 50% hunger bonus.
- Species kibble restores hunger, heals, and can tame an untamed animal.
- Baby formula feeds/heals baby animals.
- Target stick supplies a small enrichment interaction.
- Shared feeder hooks (`ZawaFeedingUtil`) allow block entities/AI to feed, water, and enrich animals without duplicating logic.
- All changes are server-authoritative and consume items outside creative mode.

Still deferred:
- Exact original feeding animation/sound timing.
- Per-enrichment-object compatibility lists and cooldowns.
- Feeder AI pathfinding to block entities.
- Full Gradle/NeoForge compile validation.
