package net.andychen.mineterra.screen;

import net.minecraft.screen.ScreenHandlerType;

public class ModScreenHandlers {
    public static ScreenHandlerType<HellforgeScreenHandler> HELLFORGE_SCREEN_HANDLER;

    public static void registerAllScreenHandlers() {
        HELLFORGE_SCREEN_HANDLER = new ScreenHandlerType<>(HellforgeScreenHandler::new);
    }
}
