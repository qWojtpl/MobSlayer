package pl.mobslayer.mobs;

import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.w3c.dom.Attr;
import pl.mobslayer.MobSlayer;

import java.util.HashMap;
import java.util.logging.Logger;

public class Mob {

    private final MobSchema schema;
    private Entity entity;
    private LivingEntity livingEntity;
    private double maxDamage = 0.0;
    private final HashMap<String, Double> playerDamage = new HashMap<>();

    public Mob(MobSchema schema) {
        this.schema = schema;
    }

    public void spawnMob() {
        Logger logger = MobSlayer.getInstance().getLogger();
        Location spawnLocation = schema.getRandomSpawnLocation();
        if(spawnLocation == null) {
            logger.severe("Tried to spawn " + schema.getName() + ", but can't find any spawn location!");
            return;
        }
        if(spawnLocation.getWorld() == null) {
            logger.severe("Tried to spawn " + schema.getName() + ", but can't find location's world.");
            return;
        }
        this.entity = spawnLocation.getWorld().spawnEntity(spawnLocation, schema.getEntityType());
        this.livingEntity = (LivingEntity) entity;
        setAttribute(Attribute.GENERIC_MAX_HEALTH, schema.getMaxHealth());
        setAttribute(Attribute.GENERIC_MOVEMENT_SPEED, schema.getMovementSpeed());
        setAttribute(Attribute.GENERIC_ATTACK_DAMAGE, schema.getAttackDamage());
        setAttribute(Attribute.GENERIC_ATTACK_SPEED, schema.getAttackSpeed());
        livingEntity.setHealth(schema.getMaxHealth());
        livingEntity.setRemoveWhenFarAway(false);
        entity.setCustomName(schema.getMobName());
        entity.setCustomNameVisible(true);
    }

    private void setAttribute(Attribute attribute, double value) {
        AttributeInstance attr = livingEntity.getAttribute(attribute);
        if(attr == null) {
            MobSlayer.getInstance().getLogger().warning("This entity don't have attribute: " + attribute.name() + ", keeping default values...");
            return;
        }
        attr.setBaseValue(value);
    }

    public void registerDamage(String playerName, double damage) {
        double actualDamage = playerDamage.getOrDefault(playerName, 0.0);
        actualDamage += damage;
        maxDamage += damage;
        playerDamage.put(playerName, actualDamage);
    }

    public MobSchema getSchema() {
        return this.schema;
    }

    public Entity getEntity() {
        return this.entity;
    }

    public LivingEntity getLivingEntity() {
        return this.livingEntity;
    }

    public double getMaxDamage() {
        return this.maxDamage;
    }

    public HashMap<String, Double> getPlayerDamage() {
        return new HashMap<>(playerDamage);
    }

}
