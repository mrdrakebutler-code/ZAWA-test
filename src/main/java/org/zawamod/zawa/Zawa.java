package org.zawamod.zawa;

import java.util.List;

/**
 * Recovered constants from the 1.20.1 ZAWA: Evolved bytecode.
 *
 * This class intentionally contains only API-neutral constants. The original
 * Forge entrypoint is being rebuilt incrementally around NeoForge's 1.21.1 APIs.
 */
public final class Zawa {
    public static final String MOD_ID = "zawa";

    public static final List<String> PLUSHIES_LIST = List.of(
            "african_wild_dog", "eagle", "elephant", "flamingo", "giraffe",
            "gorilla", "kangaroo", "koala", "lemur", "lion", "blue_macaw",
            "red_macaw", "mandrill", "monkey", "orangutan", "orca", "panda",
            "pink_panda", "penguin", "red_panda", "zebra", "rainbow_zebra"
    );

    private Zawa() {
    }
}
