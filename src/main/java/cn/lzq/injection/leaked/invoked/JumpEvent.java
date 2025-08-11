package cn.lzq.injection.leaked.invoked;

import lombok.AllArgsConstructor;
import lombok.Setter;
import cherish.event.impl.Event;

@Setter
@AllArgsConstructor
public class JumpEvent implements Event {
    public float rotationYaw;
}
