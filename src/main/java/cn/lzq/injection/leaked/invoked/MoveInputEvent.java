package cn.lzq.injection.leaked.invoked;

import lombok.AllArgsConstructor;
import cherish.event.impl.Event;

@AllArgsConstructor
public class MoveInputEvent implements Event {
    public float forwardImpulse, leftImpulse;
    public boolean keyJump, keyShift;
}
