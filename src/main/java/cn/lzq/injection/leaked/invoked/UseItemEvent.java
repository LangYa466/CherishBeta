package cn.lzq.injection.leaked.invoked;

import lombok.AllArgsConstructor;
import lombok.Setter;
import cherish.event.impl.Event;

@Setter
@AllArgsConstructor
public class UseItemEvent implements Event {
    public float yaw, pitch;
}
