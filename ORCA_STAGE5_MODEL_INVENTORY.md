# Stage 5 — Original model structure inventory

Source: user-supplied original ZAWA 1.20.1 alpha5 JAR; inspected with JDK javap -p -c.

Original model nested classes:
- `OrcaModel$AdultMale`: 52 named parts
- `OrcaModel$AdultFemale`: 52 named parts
- `OrcaModel$Child`: 27 named parts

Adult male: Body, Tail1–Tail5, TailFinRight1–2, TailFinLeft1–2, TailUnder1–4, Chest, Head, HeadTop, Snout1–3, SnoutTop1–6, HeadUnder, Jaw1–3, JawUnder1–3, EyeRight1–2, EyeLeft1–2, PectoralFinRight1–5, PectoralFinLeft1–5, ChestUnder, BackFin1–4.

Adult female: same count; differs by having BackFin5 and no HeadTop.

Child: Body, Tail1–4, TailFinRight1–2, TailFinLeft1–2, Chest, Head, Snout1–2, SnoutTop1–2, HeadUnder, Jaw1–2, JawUnder1–2, EyeRight, EyeLeft, PectoralFinLeft1–2, PectoralFinRight1–2, BackFin.

Each class exposes `createBodyLayer`, `playIdleAnimation` and `playMovementAnimation`. The original adult male animation bytecode includes sinusoidal body and tail rotations with idle 0.2 and movement 0.3 phase factors. These are not yet translated into NeoForge model source.

**Status:** inspection complete; geometry cuboids, pivot values, rotation sequences, and NeoForge renderer integration remain to be implemented. No build/test completed. Do not describe these models as already ported.
