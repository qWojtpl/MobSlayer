package pl.mobslayer.mobs;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class MobSchema {

    private final String name;
    private EntityType entityType;
    private double maxHealth;
    private String mobName;
    private final List<String> spawnHours = new ArrayList<>();
    private final List<Location> spawnLocations = new ArrayList<>();
    private String spawnMessage;
    private String killMessage;
    private final HashMap<String, List<ItemStack>> rewards = new HashMap<>();

    public MobSchema(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public EntityType getEntityType() {
        return this.entityType;
    }

    public double getMaxHealth() {
        return this.maxHealth;
    }

    public String getMobName() {
        return this.mobName;
    }

    public List<String> getSpawnHours() {
        return new ArrayList<>(spawnHours);
    }

    public List<Location> getSpawnLocations() {
        return new ArrayList<>(spawnLocations);
    }

    @Nullable
    public Location getRandomSpawnLocation() {
        if(getSpawnLocations().size() == 0) {
            return null;
        }
        return getSpawnLocations().get(new Random().nextInt(getSpawnLocations().size()));
    }

    public String getSpawnMessage() {
        return this.spawnMessage;
    }

    public String getKillMessage() {
        return this.killMessage;
    }

    public HashMap<String, List<ItemStack>> getRewards() {
        return new HashMap<>(rewards);
    }

    public List<ItemStack> getReward(String key) {
        return rewards.getOrDefault(key, null);
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    public void setMaxHealth(double maxHealth) {
        this.maxHealth = maxHealth;
    }

    public void setMobName(String mobName) {
        this.mobName = mobName;
    }

    public void addSpawnHour(String hour) {
        spawnHours.add(hour);
    }

    public void addSpawnHours(List<String> hours) {
        for(String h : hours) {
            addSpawnHour(h);
        }
    }

    public void removeSpawnHour(String hour) {
        spawnHours.remove(hour);
    }

    public void addSpawnLocation(Location location) {
        spawnLocations.add(location);
    }

    public void addSpawnLocations(List<Location> locations) {
        for(Location l : locations) {
            addSpawnLocation(l);
        }
    }

    public void removeSpawnLocation(Location location) {
        spawnLocations.remove(location);
    }

    public void setSpawnMessage(String message) {
        this.spawnMessage = message;
    }

    public void setKillMessage(String message) {
        this.killMessage = message;
    }

    public void addReward(String key, List<ItemStack> itemStack) {
        rewards.put(key, itemStack);
    }

    public void removeReward(String key) {
        rewards.remove(key);
    }

}
