package cherish.utils.animations.impl;

import cherish.utils.animations.Animation;
import cherish.utils.animations.Direction;

public class EaseOutExpo extends Animation {
    public EaseOutExpo(int ms, double endPoint) {
        super(ms, endPoint);
    }

    public EaseOutExpo(int ms, double endPoint, Direction direction) {
        super(ms, endPoint, direction);
    }

    protected double getEquation(double x) {
        return Math.abs((float) x - 1.0F) < 1.0E-5F ? 1 : 1 - Math.pow(2, -10 * x);
    }
}
