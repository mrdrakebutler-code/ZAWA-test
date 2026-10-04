# Stage 25 — ZAWA Animation & Pose Layer

Stage 25 adds a shared animation controller on top of the Stage 24 reconstructed model system.

Implemented:
- Reset-to-bind-pose animation each frame.
- Four-legged ground locomotion with species-dependent cadence.
- Aquatic swimming/body and tail motion.
- Flying/wing flapping for flying entities.
- Sitting pose support through vanilla living-entity sitting state.
- Climbing pose support through the recovered `ClimbingEntity` interface.
- Baby model scaling and vertical adjustment.
- Species-specific animation hooks for giraffe, flamingo, sloth, kangaroo, butterfly, mantis, panda/red panda, etc.
- Continued use of the original ZAWA texture resources.

This is a reconstructed animation layer, not a byte-for-byte port of every original animation class. The original JAR's individual hand-authored animations remain a later refinement target.

Build status: source-level validation only; the environment does not currently contain the Gradle/Minecraft dependency cache required for a real NeoForge compile.
