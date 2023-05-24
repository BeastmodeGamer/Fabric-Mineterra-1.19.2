package net.andychen.mineterra.util;

import java.util.Random;

public class WeaponUtil {

    public static boolean calculateCritChance(float crit) {
        Random random = new Random();
        if (random.nextFloat(0, 1) <= crit) {
            return true;
        }
        return false;
    }
}
