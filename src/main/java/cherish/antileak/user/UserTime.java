package cherish.antileak.user;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import cherish.antileak.utils.JsonUtil;

public class UserTime {
    public long amiri;
    public long cherish;
    public long sigma;
    public long rainytime;

    public JsonObject toObject(){
        final JsonObject time = new JsonObject();
        time.addProperty("amiri", amiri);
        time.addProperty("cherish", cherish);
        time.addProperty("sigma", sigma);
        time.addProperty("rainytime", rainytime);
        return time;
    }

    public static UserTime fromObject(String time){
        final JsonElement element = JsonParser.parseString(time);
        if (!element.isJsonObject()) return new UserTime();
        final JsonUtil jsonUtil = new JsonUtil((JsonObject) element);
        final UserTime userTime = new UserTime();

        userTime.amiri = jsonUtil.getLong("amiri", 0);
        userTime.cherish = jsonUtil.getLong("cherish", 0);
        userTime.sigma = jsonUtil.getLong("sigma", 0);
        userTime.rainytime = jsonUtil.getLong("rainytime", 0);

        return userTime;
    }
}
