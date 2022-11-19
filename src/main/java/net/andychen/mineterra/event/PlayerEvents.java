package net.andychen.mineterra.event;

import net.andychen.mineterra.mana.ManaManager;
import net.andychen.mineterra.mana.ManaManagerAccess;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.server.network.ServerPlayerEntity;

public class PlayerEvents implements ServerPlayerEvents.CopyFrom {
    @Override
    public void copyFromPlayer(ServerPlayerEntity oldPlayer, ServerPlayerEntity newPlayer, boolean alive) {
        int maxMana = ((ManaManagerAccess) oldPlayer).getManaManager().getMaxMana();
        ManaManager player = ((ManaManagerAccess) newPlayer).getManaManager();

        player.setMaxMana(maxMana);
        player.setMana(maxMana);
    }
}
