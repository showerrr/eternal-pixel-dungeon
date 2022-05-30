package com.eternalpixel.eternalpixeldungeon.ui.changelist;

import com.eternalpixel.eternalpixeldungeon.Assets;
import com.eternalpixel.eternalpixeldungeon.messages.Messages;
import com.eternalpixel.eternalpixeldungeon.scenes.ChangesScene;
import com.eternalpixel.eternalpixeldungeon.scenes.PixelScene;
import com.eternalpixel.eternalpixeldungeon.sprites.ItemSprite;
import com.eternalpixel.eternalpixeldungeon.sprites.ItemSpriteSheet;
import com.eternalpixel.eternalpixeldungeon.sprites.RatSprite;
import com.eternalpixel.eternalpixeldungeon.sprites.SnakeSprite;
import com.eternalpixel.eternalpixeldungeon.ui.Icons;
import com.eternalpixel.eternalpixeldungeon.ui.SPDchangelist.ChangeButton;
import com.eternalpixel.eternalpixeldungeon.ui.SPDchangelist.ChangeInfo;
import com.eternalpixel.eternalpixeldungeon.ui.Window;
import com.watabou.noosa.Image;

import java.util.ArrayList;

public class v01X {

    public static void addAllChanges(ArrayList<ChangeInfo> changeInfos){

        add_v0_1_0_Changes(changeInfos);
    }

//changes.addButton(new ChangeButton(new RatSprite(),"",
//            "" ));

    public static void add_v0_1_0_Changes(ArrayList<ChangeInfo> changeInfos) {
        ChangeInfo changes = new ChangeInfo("v0.1.0", true, "");
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);

        //新内容
        changes = new ChangeInfo(Messages.get(ChangesScene.class, "new"), false, null);
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(Icons.get(Icons.EPX),"开发者的话",
                "_-_ 2022/1/31\n\n" +
                        "写下这段话的时候正值2022年除夕晚上，\n" +
                        "但是此mod的完善度完全没达到能够发行的程度，\n" +
                        "也远达不到我所构想的内容。\n" +
                        "不过我还是尽可能抽出时间以让elona地牢尽快能让大家玩到。\n\n" +
                        "不说多了，这是一份迟来的新年祝福，祝大家虎年如虎添翼，虎虎生威！"));

        changes.addButton(new ChangeButton(new ItemSprite(ItemSpriteSheet.MELONFLAVOREDBREAD, null),"新食物！",
                "尽管每一次世纪的兴衰都会给文化带来一定的冲击，\n" +
                        "但由于第十一纪再生的时代“西埃尔·泰尔”是较为和平与和谐的时代，\n" +
                        "在食物这方面有着不小的成就。\n\n" +
                        "毫不夸张地说，新增的食物贴图数量比破碎的怪物种类还要多，\n" +
                        "当然目前不同食物暂时没区别（笑）。\n\n" +
                        "嘛吃还是能吃的，\n" +
                        "但请注意有些食物是_会腐烂的_，\n" +
                        "会腐烂的食物会在左上角标示剩余多少回合。\n" +
                        "据说有方法可以让食物保鲜。"));

        changes.addButton(new ChangeButton(new ItemSprite(ItemSpriteSheet.FOODMAKER, null),"新厨具！",
                "在elona地牢中，你可以使用新加入的厨具对食物进行烹饪，\n" +
                        "食物原料可以烹饪出最多_9种rank_的料理（目前暂无区别），\n" +
                        "可以让会腐烂的食物_重置新鲜度_。"));

        changes.addButton(new ChangeButton(new ItemSprite(ItemSpriteSheet.JUICE),"口渴值",
                "加入口渴系统。"));

        changes.addButton(new ChangeButton(new ItemSprite(ItemSpriteSheet.BACKPACK),"负重系统",
                "加入负重系统。"));

        Image cardback = new Image(Assets.Interfaces.CARDBACK);
        cardback.scale.set(PixelScene.align(0.5f));
        changes.addButton(new ChangeButton(cardback,"新游戏！",
                "有谁规定地牢只能打怪的？\n" +
                        "打打杀杀腻了之后，\n" +
                        "就去找帕罗米亚左下角的游戏玩家玩玩小游戏吧。"));

//        changes.addButton(new ChangeButton(new ZubatSprite(),"新怪物：音蝠",
//                "这一类蝙蝠并不吸血，\n" +
//                        "他们习惯于与敌人保持距离，\n" +
//                        "并使用超声波进行攻击。"));

        Image town = new Image(Assets.Map.TOWN);
        town.scale.set(PixelScene.align(0.3f));
        changes.addButton(new ChangeButton(town,"帕罗米亚",
                "帕罗米亚作为冒险的初始地点并不合适，\n" +
                        "但考虑到他的重要性，\n" +
                        "就先将0层设为帕罗米亚了。"));

        //改动
        changes = new ChangeInfo(Messages.get(ChangesScene.class, "changes"), false, null);
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(new SnakeSprite(),"怪物行动逻辑改动",
                "与破碎怪物只会按逻辑和概率行动不同，\n" +
                        "elona地牢中新加入的怪物会先过_移动率_判定是否尝试调整为_最适距离_，\n" +
                        "如果不需要移动，有_主要行动概率_在_主要行动_中随机抽取一个使用，\n" +
                        "否则在_辅助行动_中抽取一个使用。"));

        changes.addButton(new ChangeButton(new RatSprite(),"怪物分布改动",
                "现在怪物并不会按区域分布，\n" +
                        "而是在特定层数之后随机生成，\n" +
                        "怪物的属性值会_随层数改变_。\n" +
                        "部分怪物可能暂时不予生成"));

        changes.addButton(new ChangeButton(Icons.get(Icons.PREFS), Messages.get(ChangesScene.class, "misc"),
                "饥饿值、口渴值、负重不再有buff图标，\n" +
                        "改为_左侧状态栏_表示"));
    }
}
