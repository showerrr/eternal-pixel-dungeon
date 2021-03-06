package com.eternalpixel.eternalpixeldungeon;

import com.eternalpixel.eternalpixeldungeon.actors.mobs.YogDzewa;
import com.eternalpixel.eternalpixeldungeon.items.Gold;
import com.eternalpixel.eternalpixeldungeon.items.Item;
import com.eternalpixel.eternalpixeldungeon.items.potions.PotionOfExperience;
import com.eternalpixel.eternalpixeldungeon.items.potions.PotionOfFrost;
import com.eternalpixel.eternalpixeldungeon.items.potions.PotionOfHaste;
import com.eternalpixel.eternalpixeldungeon.items.potions.PotionOfHealing;
import com.eternalpixel.eternalpixeldungeon.items.potions.PotionOfInvisibility;
import com.eternalpixel.eternalpixeldungeon.items.potions.PotionOfLevitation;
import com.eternalpixel.eternalpixeldungeon.items.potions.PotionOfLiquidFlame;
import com.eternalpixel.eternalpixeldungeon.items.potions.PotionOfMindVision;
import com.eternalpixel.eternalpixeldungeon.items.potions.PotionOfParalyticGas;
import com.eternalpixel.eternalpixeldungeon.items.potions.PotionOfPurity;
import com.eternalpixel.eternalpixeldungeon.items.potions.PotionOfStrength;
import com.eternalpixel.eternalpixeldungeon.items.scrolls.ScrollOfIdentify;
import com.eternalpixel.eternalpixeldungeon.items.scrolls.ScrollOfLullaby;
import com.eternalpixel.eternalpixeldungeon.items.scrolls.ScrollOfMagicMapping;
import com.eternalpixel.eternalpixeldungeon.items.scrolls.ScrollOfMirrorImage;
import com.eternalpixel.eternalpixeldungeon.items.scrolls.ScrollOfRage;
import com.eternalpixel.eternalpixeldungeon.items.scrolls.ScrollOfRecharging;
import com.eternalpixel.eternalpixeldungeon.items.scrolls.ScrollOfRemoveCurse;
import com.eternalpixel.eternalpixeldungeon.items.scrolls.ScrollOfRetribution;
import com.eternalpixel.eternalpixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.eternalpixel.eternalpixeldungeon.items.scrolls.ScrollOfTerror;
import com.eternalpixel.eternalpixeldungeon.items.scrolls.ScrollOfTransmutation;
import com.eternalpixel.eternalpixeldungeon.items.scrolls.ScrollOfUpgrade;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Wishing {

    public static float getSimilarityRatio(String str, String target) {
//see https://blog.csdn.net/JavaReact/article/details/82144732
        int d[][]; // ??????
        int n = str.length();
        int m = target.length();
        int i; // ??????str???
        int j; // ??????target???
        char ch1; // str???
        char ch2; // target???
        int temp; // ??????????????????,?????????????????????????????????,??????0??????1
        if (n == 0 || m == 0) {
            return 0;
        }
        d = new int[n + 1][m + 1];
        for (i = 0; i <= n; i++) { // ??????????????????
            d[i][0] = i;
        }

        for (j = 0; j <= m; j++) { // ??????????????????
            d[0][j] = j;
        }

        for (i = 1; i <= n; i++) { // ??????str
            ch1 = str.charAt(i - 1);
            // ?????????target
            for (j = 1; j <= m; j++) {
                ch2 = target.charAt(j - 1);
                if (ch1 == ch2 || ch1 == ch2 + 32 || ch1 + 32 == ch2) {
                    temp = 0;
                } else {
                    temp = 1;
                }
                // ??????+1,??????+1, ?????????+temp?????????
                d[i][j] = Math.min(Math.min(d[i - 1][j] + 1, d[i][j - 1] + 1), d[i - 1][j - 1] + temp);
            }
        }

        return (1 - (float) d[n][m] / Math.max(str.length(), target.length())) * 100F;
    }

    public static String checkSimilarity(String target) {
        float a = 0;
        float b = 0;
        String wish = "";
        int c = 12;

        for (String str : wishToCompare) {
            a = getSimilarityRatio(str, target);
            if (a > b) {
                b = a;
                wish = str;
            } else if (a == b && a != 0 && Random.Int(c) != 0) {
                wish = str;
            }

            c--;
            if (c < 2) c = 2;
        }
        return wish;
    }



    private final static ArrayList<String> wishToCompare = new ArrayList<String>(){{

        add("????????????");
        add("????????????");
        add("????????????");
        add("????????????");
        add("????????????");
        add("????????????");
        add("????????????");
        add("????????????");
        add("????????????");
        add("????????????");
        add("????????????");
        add("????????????");
        add("????????????");
        add("????????????");
        add("????????????");
        add("????????????");
        add("????????????");
        add("????????????");
        add("????????????");
        add("????????????");
        add("????????????");
        add("????????????");
        add("????????????");
        add("????????????");
        add("??????");
        add("??????");
//        ??????");
//        add("
    }};
    public static void doWish(String string) {
        string = checkSimilarity(string);
        Item i;
        switch (string) {
            case "????????????":
                i = new PotionOfExperience().identify();
                Dungeon.level.drop(i, Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);
                break;
            case "????????????":
                i = new PotionOfFrost().identify();
                Dungeon.level.drop(i, Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);
                break;
            case "????????????":
                i = new PotionOfHaste().identify();
                Dungeon.level.drop(i, Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);
                break;
            case "????????????":
                i = new PotionOfHealing().identify();
                Dungeon.level.drop(i, Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);
                break;
            case "????????????":
                i = new PotionOfInvisibility().identify();
                Dungeon.level.drop(i, Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);
                break;
            case "????????????":
                i = new PotionOfLevitation().identify();
                Dungeon.level.drop(i, Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);
                break;
            case "????????????":
                i = new PotionOfLiquidFlame().identify();
                Dungeon.level.drop(i, Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);
                break;
            case "????????????":
                i = new PotionOfMindVision().identify();
                Dungeon.level.drop(i, Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);
                break;
            case "????????????":
                i = new PotionOfParalyticGas().identify();
                Dungeon.level.drop(i, Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);
                break;
            case "????????????":
                i = new PotionOfPurity().identify();
                Dungeon.level.drop(i, Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);
                break;
            case "????????????":
                i = new PotionOfStrength().identify();
                Dungeon.level.drop(i, Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);
                break;
            case "????????????":
                i = new ScrollOfIdentify().identify();
                Dungeon.level.drop(i, Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);
                break;
            case "????????????":
                i = new ScrollOfLullaby().identify();
                Dungeon.level.drop(i, Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);
                break;
            case "????????????":
                i = new ScrollOfMagicMapping().identify();
                Dungeon.level.drop(i, Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);
                break;
            case "????????????":
                i = new ScrollOfMirrorImage().identify();
                Dungeon.level.drop(i, Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);
                break;
            case "????????????":
                i = new ScrollOfRetribution().identify();
                Dungeon.level.drop(i, Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);
                break;
            case "????????????":
                i = new ScrollOfRage().identify();
                Dungeon.level.drop(i, Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);
                break;
            case "????????????":
                i = new ScrollOfRecharging().identify();
                Dungeon.level.drop(i, Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);
                break;
            case "????????????":
                i = new ScrollOfRemoveCurse().identify();
                Dungeon.level.drop(i, Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);
                break;
            case "????????????":
                i = new ScrollOfTeleportation().identify();
                Dungeon.level.drop(i, Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);
                break;
            case "????????????":
                i = new ScrollOfTerror().identify();
                Dungeon.level.drop(i, Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);
                break;
            case "????????????":
                i = new ScrollOfTransmutation().identify();
                Dungeon.level.drop(i, Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);
                break;
            case "????????????":
                i = new ScrollOfUpgrade().identify();
                Dungeon.level.drop(i, Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);
                break;
            case "??????":
                Dungeon.hero.earnExp(Dungeon.hero.maxExp(), YogDzewa.class);
                break;
            case "??????":
                i = new Gold(100);
                Dungeon.level.drop(i, Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);
                break;
            case "":
            default:
                i = new Gold(50);
                Dungeon.level.drop(i, Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);
        }

    }
}
