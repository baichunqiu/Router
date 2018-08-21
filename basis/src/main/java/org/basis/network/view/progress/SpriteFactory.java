package org.basis.network.view.progress;


import org.basis.network.view.progress.sprite.Sprite;
import org.basis.network.view.progress.style.ChasingDots;
import org.basis.network.view.progress.style.Circle;
import org.basis.network.view.progress.style.CubeGrid;
import org.basis.network.view.progress.style.DoubleBounce;
import org.basis.network.view.progress.style.FadingCircle;
import org.basis.network.view.progress.style.FoldingCube;
import org.basis.network.view.progress.style.MultiplePulse;
import org.basis.network.view.progress.style.MultiplePulseRing;
import org.basis.network.view.progress.style.Pulse;
import org.basis.network.view.progress.style.PulseRing;
import org.basis.network.view.progress.style.RotatingCircle;
import org.basis.network.view.progress.style.RotatingPlane;
import org.basis.network.view.progress.style.ThreeBounce;
import org.basis.network.view.progress.style.WanderingCubes;
import org.basis.network.view.progress.style.Wave;

/**
 * Created by ybq.
 */
public class SpriteFactory {

    public static Sprite create(Style style) {
        Sprite sprite = null;
        switch (style) {
            case ROTATING_PLANE:
                sprite = new RotatingPlane();
                break;
            case DOUBLE_BOUNCE:
                sprite = new DoubleBounce();
                break;
            case WAVE:
                sprite = new Wave();
                break;
            case WANDERING_CUBES:
                sprite = new WanderingCubes();
                break;
            case PULSE:
                sprite = new Pulse();
                break;
            case CHASING_DOTS:
                sprite = new ChasingDots();
                break;
            case THREE_BOUNCE:
                sprite = new ThreeBounce();
                break;
            case CIRCLE:
                sprite = new Circle();
                break;
            case CUBE_GRID:
                sprite = new CubeGrid();
                break;
            case FADING_CIRCLE:
                sprite = new FadingCircle();
                break;
            case FOLDING_CUBE:
                sprite = new FoldingCube();
                break;
            case ROTATING_CIRCLE:
                sprite = new RotatingCircle();
                break;
            case MULTIPLE_PULSE:
                sprite = new MultiplePulse();
                break;
            case PULSE_RING:
                sprite = new PulseRing();
                break;
            case MULTIPLE_PULSE_RING:
                sprite = new MultiplePulseRing();
                break;
            default:
                break;
        }
        return sprite;
    }
}
