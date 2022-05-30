package com.eternalpixel.eternalpixeldungeon.effects;

import com.eternalpixel.eternalpixeldungeon.Assets;
import com.watabou.glwrap.Blending;
import com.watabou.noosa.Game;
import com.watabou.noosa.Image;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PointF;

public class newBeam extends Image {

    private static final double A = 180 / Math.PI;

    private float duration;
    private float timeLeft;
    private float a;

    public newBeam(PointF s, PointF e) {
        super(Effects.get(Effects.Type.DEATH_RAY));

        origin.set( 0, height / 2 );

        x = s.x - origin.x;
        y = s.y - origin.y;

        float dx = e.x - s.x;
        float dy = e.y - s.y;
        angle = (float)(Math.atan2( dy, dx ) * A);
        a = (float)Math.sqrt( dx * dx + dy * dy ) / width;
        scale.x = 0;
        Sample.INSTANCE.play( Assets.Sounds.RAY );

        timeLeft = this.duration = 0.5f;
    }

    @Override
    public void update() {
        super.update();

        float p = timeLeft / duration;

        if (p > 2/3f) {
            scale.set((1 - p) * a * 3/2f, p);
        } else {
            scale.set(a, p);
            alpha(p * 3/2f);
        }

        if ((timeLeft -= Game.elapsed) <= 0) {
            killAndErase();
        }

    }

    @Override
    public void draw() {
        Blending.setLightMode();
        super.draw();
        Blending.setNormalMode();
    }
}
