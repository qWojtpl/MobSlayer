package pl.mobslayer.mobs;

import org.bukkit.entity.EntityType;

public class MobSchema {

    private EntityType entityType;
    private double maxHealth;

    public EntityType getEntityType() {
        return this.entityType;
    }

    public double getMaxHealth() {
        return this.maxHealth;
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    public void setMaxHealth(double maxHealth) {
        this.maxHealth = maxHealth;
    }

}
