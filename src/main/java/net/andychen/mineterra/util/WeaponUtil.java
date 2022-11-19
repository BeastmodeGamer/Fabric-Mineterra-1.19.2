package net.andychen.mineterra.util;

import java.util.Random;

public class WeaponUtil {
    private static Random random = new Random();

    public static boolean calculateCritChance(int crit) {
        if (random.nextInt(1, 101) <= crit) {
            return true;
        }
        return false;
    }
}
