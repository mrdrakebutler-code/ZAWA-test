package org.zawamod.zawa.world.entity.stats;

import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.Item;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class EntityStats {
    private final EntityTemperamentCategory temperament;
    private final Item kibble;
    private final EntitySizeCategory sizeCategory;
    private final EntityFertilityCategory fertility;
    private final Item breedingItem;
    private final UniformInt litterSize;
    private final EntityDiet diet;
    private final EntitySpeedCategory speed;
    private final EntityEnrichment enrichment;
    private final int variantCount;
    private final List<String> captiveVariants;

    public EntityStats(EntityTemperamentCategory temperament, Item kibble,
                       EntitySizeCategory sizeCategory, EntityFertilityCategory fertility,
                       Item breedingItem, UniformInt litterSize, EntityDiet diet,
                       EntitySpeedCategory speed, EntityEnrichment enrichment,
                       int variantCount, List<String> captiveVariants) {
        this.temperament = temperament;
        this.kibble = kibble;
        this.sizeCategory = sizeCategory;
        this.fertility = fertility;
        this.breedingItem = breedingItem;
        this.litterSize = litterSize;
        this.diet = diet;
        this.speed = speed;
        this.enrichment = enrichment;
        this.variantCount = variantCount;
        this.captiveVariants = captiveVariants;
    }
    public EntityTemperamentCategory getTemperament() { return temperament; }
    public Item getKibble() { return kibble; }
    public EntitySizeCategory getSizeCategory() { return sizeCategory; }
    public EntityFertilityCategory getFertility() { return fertility; }
    public Item getBreedingItem() { return breedingItem; }
    public UniformInt getLitterSize() { return litterSize; }
    public EntityDiet getDiet() { return diet; }
    public EntitySpeedCategory getSpeed() { return speed; }
    public EntityEnrichment getEnrichment() { return enrichment; }
    public Set<Item> getEnrichmentAsItems() { return enrichment == null ? Collections.emptySet() : enrichment.asItems(); }
    public int getTotalVariants() { return variantCount; }
    public int getCaptiveVariants() { return captiveVariants.size(); }
    public List<String> getCaptiveVariantsList() { return captiveVariants; }
}
