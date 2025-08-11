package cn.lzq.injection.leaked.invoked;

import lombok.AllArgsConstructor;
import cherish.event.impl.Event;

@AllArgsConstructor
public class PitchRenderEvent implements Event {
    public float pitch;
}
