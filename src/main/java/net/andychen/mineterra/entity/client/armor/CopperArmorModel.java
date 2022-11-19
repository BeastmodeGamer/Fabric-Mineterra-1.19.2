package net.andychen.mineterra.entity.client.armor;

import net.andychen.mineterra.MineTerra;
import net.andychen.mineterra.item.custom.armor.CopperArmorItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class CopperArmorModel extends AnimatedGeoModel<CopperArmorItem> {
    @Override
    public Identifier getModelResource(CopperArmorItem object) {
        return new Identifier(MineTerra.MOD_ID, "geo/copper_armor.geo.json");
    }

    @Override
    public Identifier getTextureResource(CopperArmorItem object) {
        return new Identifier(MineTerra.MOD_ID, "textures/models/armor/copper_armor.png");
    }

    @Override
    public Identifier getAnimationResource(CopperArmorItem animatable) {
        return new Identifier(MineTerra.MOD_ID, "animations/dummy_armor_animation.json");
    }
}
