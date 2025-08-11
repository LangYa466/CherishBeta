package cn.lzq.injection.leaked.invoked;

import lombok.AllArgsConstructor;
import cherish.event.impl.CancellableEvent;

@AllArgsConstructor
public class SlowEvent extends CancellableEvent {
    public boolean state;
}
