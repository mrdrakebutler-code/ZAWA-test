# Stage 31 - Animal interaction and capture mechanics

Migrated the first functional layer of ZAWA's interaction systems to NeoForge 1.21.1.

## Added
- Data Book item implementation and client encyclopedia screen scaffold.
- Capture Net item with entity capture/release NBT flow.
- Capture Net uses ZAWA size limits for ZAWA animals and rejects players/dead targets.
- Captured entity state persists inside the item.
- Slingshot Net interaction scaffold retained for later projectile migration.
- Capture blacklist tag scaffold.

## Notes
- This is a source-level migration; no NeoForge Gradle compilation was available in this workspace.
- The original SlingshotNet projectile, tranquilizer projectile, and full bucket transfer mechanics remain separate migration work.
