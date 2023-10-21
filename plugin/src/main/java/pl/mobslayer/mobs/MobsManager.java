package pl.mobslayer.mobs;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class MobsManager {

    private final List<MobSchema> schemas = new ArrayList<>();
    private final List<Mob> mobs = new ArrayList<>();

    public void addSchema(MobSchema schema) {
        schemas.add(schema);
    }

    public void removeSchema(MobSchema schema) {
        schemas.remove(schema);
    }

    @Nullable
    public MobSchema getSchemaByName(String name) {
        for(MobSchema schema : schemas) {
            if(schema.getName().equals(name)) {
                return schema;
            }
        }
        return null;
    }

    public List<MobSchema> getSchemas() {
        return new ArrayList<>(schemas);
    }

    public void spawnMob(MobSchema schema) {
        Mob mob = new Mob(schema);
        mob.spawnMob();
        mobs.add(mob);
    }

    public void killMob(Mob mob) {
        if(mob == null) {
            return;
        }

    }

    public List<Mob> getMobs() {
        return new ArrayList<>(mobs);
    }

}
