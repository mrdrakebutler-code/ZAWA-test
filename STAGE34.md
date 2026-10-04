# Stage 34 - Enclosure Care AI

Adds bounded server-side AI goals for hungry/thirsty/under-enriched ZAWA animals:
- seek compatible ground/wall feeders and consume diet items
- seek nearby water and restore thirst
- seek enrichment blocks allowed by each species' entity_stats JSON
- per-animal enrichment cooldowns to avoid repeatedly farming one object

This stage intentionally does not invent missing enrichment blocks; it uses whatever registered block IDs are present and match the original species data.
