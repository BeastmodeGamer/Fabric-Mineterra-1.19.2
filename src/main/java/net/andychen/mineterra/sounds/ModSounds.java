package net.andychen.mineterra.sounds;

import net.andychen.mineterra.MineTerra;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModSounds {
    public static SoundEvent BLOCK_POT_BREAK = registerSoundEvent("block.pot.break");
    public static SoundEvent ENTITY_BOSS_ROAR = registerSoundEvent("entity.boss.roar");
    public static SoundEvent ENTITY_FALLEN_STAR_DISAPPEAR = registerSoundEvent("entity.fallen_star.disappear");
    public static SoundEvent ENTITY_FALLEN_STAR_FALLING = registerSoundEvent("entity.fallen_star.falling");
    public static SoundEvent ENTITY_GENERIC_DEATH = registerSoundEvent("entity.generic.death");
    public static SoundEvent ENTITY_GENERIC_HURT = registerSoundEvent("entity.generic.hurt");
    public static SoundEvent ENTITY_GENERIC_TELEPORT = registerSoundEvent("entity.generic.teleport");
    public static SoundEvent ENTITY_GOBLIN_AMBIENT_1 = registerSoundEvent("entity.goblin.ambient_1");
    public static SoundEvent ENTITY_GOBLIN_AMBIENT_2 = registerSoundEvent("entity.goblin.ambient_2");
    public static SoundEvent ENTITY_GOBLIN_DEATH = registerSoundEvent("entity.goblin.death");
    public static SoundEvent ENTITY_GOBLIN_HURT = registerSoundEvent("entity.goblin.hurt");
    public static SoundEvent ENTITY_PLAYER_FULL_MANA = registerSoundEvent("entity.player.full_mana");
    public static SoundEvent ENTITY_SPELL_DEATH = registerSoundEvent("entity.spell.death");
    public static SoundEvent ENTITY_SPELL_HURT = registerSoundEvent("entity.spell.hurt");
    public static SoundEvent ITEM_EXPLOSIVE_TRAP_EXPLODE = registerSoundEvent("item.explosive_trap.explode");
    public static SoundEvent ITEM_MANA_CRYSTAL_USE = registerSoundEvent("item.mana_crystal.use");
    public static SoundEvent ITEM_GUN_SHOOT = registerSoundEvent("item.gun.shoot");
    public static SoundEvent MUSIC_GOBLIN_ARMY = registerSoundEvent("music.goblin_army");
    public static SoundEvent MUSIC_GOBLIN_ARMY_LOOP = registerSoundEvent("music.goblin_army_loop");

    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = new Identifier(MineTerra.MOD_ID, name);
        return Registry.register(Registry.SOUND_EVENT, id, new SoundEvent(id));
    }

    public static void registerSounds() {
        System.out.println("Registering ModSounds for " + MineTerra.MOD_ID);
    }
}
