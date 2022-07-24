package pl.vortexinfinitum.game.utils;

//https://easings.net/
public class Easing {

    public static float apply(float from, float to, float scale) {
        return from + (to - from) * scale;
    }

    public static float easeOutElastic(Float x) {
        float c4 = (2 * (float) Math.PI) / 3;
        return (float) (x == 0 ? 0 : x == 1 ? 1 : Math.pow(2, -10 * x) * Math.sin((x * 10 - 0.75) * c4) + 1);
    }

    public static float easeInOutElastic(Float x) {
        float c5 = (2 * (float) Math.PI) / 4.5f;
        double sin = Math.sin((20 * x - 11.125) * c5);
        return (float) (x == 0 ? 0 : x == 1 ? 1 : x < 0.5 ? -(Math.pow(2, 20 * x - 10) * sin) / 2 : (Math.pow(2, -20 * x + 10) * sin) / 2 + 1);
    }

    public static float easeOutBounce(Float x) {
        float n1 = 7.5625f;
        float d1 = 2.75f;
        if (x < 1 / d1) {
            return n1 * x * x;
        } else if (x < 2 / d1) {
            return n1 * (x -= 1.5f / d1) * x + 0.75f;
        } else if (x < 2.5 / d1) {
            return n1 * (x -= 2.25f / d1) * x + 0.9375f;
        } else {
            return n1 * (x -= 2.625f / d1) * x + 0.984375f;
        }
    }
}
