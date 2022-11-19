package net.andychen.mineterra.sounds;

import net.andychen.mineterra.MineTerra;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModSounds {
    public static SoundEvent ENTITY_PLAYER_FULL_MANA = registerSoundEvent("entity.player.full_mana");
    public static SoundEvent ITEM_MANA_CRYSTAL_USE = registerSoundEvent("item.mana_crystal.use");

    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = new Identifier(MineTerra.MOD_ID, name);
        return Registry.register(Registry.SOUND_EVENT, id, new SoundEvent(id));
    }

    public static void registerSounds() {
        System.out.println("Registering ModSounds for " + MineTerra.MOD_ID);
    }
}
