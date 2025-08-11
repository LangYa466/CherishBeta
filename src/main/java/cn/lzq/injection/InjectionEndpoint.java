package cn.lzq.injection;

import cn.lzq.injection.leaked.mixin.MixinLoader;
import cherish.cherish;
import cherish.antileak.ZenlessZone0;

public class InjectionEndpoint {
    public static void load() {
        ZenlessZone0.a(null);
        init();
    }

    private static void init() {
        MixinLoader.init();
        cherish.isDllInject = true;
        cherish.INSTANCE.init();
    }
}
