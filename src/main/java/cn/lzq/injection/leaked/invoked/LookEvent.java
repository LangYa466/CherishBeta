package cn.lzq.injection.leaked.invoked;

import lombok.AllArgsConstructor;
import cherish.event.impl.Event;

@AllArgsConstructor
public class LookEvent implements Event {
    public float rotationYaw, rotationPitch;
}
