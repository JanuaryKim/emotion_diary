package emotion.diary.server.util;

import com.google.gson.Gson;
import org.springframework.stereotype.Component;


public class Serializer {

    private static Gson gson = new Gson();

    public static String toJson(Object obj){
        return gson.toJson(obj);
    }

}
