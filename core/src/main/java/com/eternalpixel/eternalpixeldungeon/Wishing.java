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
        int d[][]; // 矩阵
        int n = str.length();
        int m = target.length();
        int i; // 遍历str的
        int j; // 遍历target的
        char ch1; // str的
        char ch2; // target的
        int temp; // 记录相同字符,在某个矩阵位置值的增量,不是0就是1
        if (n == 0 || m == 0) {
            return 0;
        }
        d = new int[n + 1][m + 1];
        for (i = 0; i <= n; i++) { // 初始化第一列
            d[i][0] = i;
        }

        for (j = 0; j <= m; j++) { // 初始化第一行
            d[0][j] = j;
        }

        for (i = 1; i <= n; i++) { // 遍历str
            ch1 = str.charAt(i - 1);
            // 去匹配target
            for (j = 1; j <= m; j++) {
                ch2 = target.charAt(j - 1);
                if (ch1 == ch2 || ch1 == ch2 + 32 || ch1 + 32 == ch2) {
                    temp = 0;
                } else {
                    temp = 1;
                }
                // 左边+1,上边+1, 左上角+temp取最小
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

        add("经验药剂");
        add("冰霜药剂");
        add("极速药剂");
        add("治疗药剂");
        add("隐形药剂");
        add("浮空药剂");
        add("液火药剂");
        add("灵视药剂");
        add("麻痹药剂");
        add("净化药剂");
        add("力量药剂");
        add("毒气药剂");
        add("鉴定卷轴");
        add("催眠卷轴");
        add("探地卷轴");
        add("镜像卷轴");
        add("复仇卷轴");
        add("盛怒卷轴");
        add("充能卷轴");
        add("祛邪卷轴");
        add("传送卷轴");
        add("恐惧卷轴");
        add("嬗变卷轴");
        add("升级卷轴");
        add("等级");
        add("金币");
//        卷轴");
//        add("
    }};
    public static void doWish(String string) {
        string = checkSimilarity(string);
        Item i;
        switch (string) {
            case "经验药剂":
                i = new PotionOfExperience().identify();
                Dungeon.level.drop(i, Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);
                break;
            case "冰霜药剂":
                i = new PotionOfFrost().identify();
                Dungeon.level.drop(i, Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);
                break;
            case "极速药剂":
                i = new PotionOfHaste().identify();
                Dungeon.level.drop(i, Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);
                break;
            case "治疗药剂":
                i = new PotionOfHealing().identify();
                Dungeon.level.drop(i, Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);
                break;
            case "隐形药剂":
                i = new PotionOfInvisibility().identify();
                Dungeon.level.drop(i, Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);
                break;
            case "浮空药剂":
                i = new PotionOfLevitation().identify();
                Dungeon.level.drop(i, Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);
                break;
            case "液火药剂":
                i = new PotionOfLiquidFlame().identify();
                Dungeon.level.drop(i, Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);
                break;
            case "灵视药剂":
                i = new PotionOfMindVision().identify();
                Dungeon.level.drop(i, Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);
                break;
            case "麻痹药剂":
                i = new PotionOfParalyticGas().identify();
                Dungeon.level.drop(i, Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);
                break;
            case "净化药剂":
                i = new PotionOfPurity().identify();
                Dungeon.level.drop(i, Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);
                break;
            case "力量药剂":
                i = new PotionOfStrength().identify();
                Dungeon.level.drop(i, Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);
                break;
            case "鉴定卷轴":
                i = new ScrollOfIdentify().identify();
                Dungeon.level.drop(i, Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);
                break;
            case "催眠卷轴":
                i = new ScrollOfLullaby().identify();
                Dungeon.level.drop(i, Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);
                break;
            case "探地卷轴":
                i = new ScrollOfMagicMapping().identify();
                Dungeon.level.drop(i, Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);
                break;
            case "镜像卷轴":
                i = new ScrollOfMirrorImage().identify();
                Dungeon.level.drop(i, Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);
                break;
            case "复仇卷轴":
                i = new ScrollOfRetribution().identify();
                Dungeon.level.drop(i, Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);
                break;
            case "盛怒卷轴":
                i = new ScrollOfRage().identify();
                Dungeon.level.drop(i, Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);
                break;
            case "充能卷轴":
                i = new ScrollOfRecharging().identify();
                Dungeon.level.drop(i, Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);
                break;
            case "祛邪卷轴":
                i = new ScrollOfRemoveCurse().identify();
                Dungeon.level.drop(i, Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);
                break;
            case "传送卷轴":
                i = new ScrollOfTeleportation().identify();
                Dungeon.level.drop(i, Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);
                break;
            case "恐惧卷轴":
                i = new ScrollOfTerror().identify();
                Dungeon.level.drop(i, Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);
                break;
            case "嬗变卷轴":
                i = new ScrollOfTransmutation().identify();
                Dungeon.level.drop(i, Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);
                break;
            case "升级卷轴":
                i = new ScrollOfUpgrade().identify();
                Dungeon.level.drop(i, Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);
                break;
            case "等级":
                Dungeon.hero.earnExp(Dungeon.hero.maxExp(), YogDzewa.class);
                break;
            case "金币":
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
