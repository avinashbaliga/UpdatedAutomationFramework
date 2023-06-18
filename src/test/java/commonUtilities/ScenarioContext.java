package commonUtilities;

import io.cucumber.java.hu.Ha;

import java.util.HashMap;
import java.util.Map;

public class ScenarioContext {

    private static ThreadLocal<Map<String, Object>> dataStoreThreadLocal = new ThreadLocal<>();

    public static void put(String key, Object value){
        if(dataStoreThreadLocal.get() == null){
            dataStoreThreadLocal.set(new HashMap<String, Object>());
        }

        dataStoreThreadLocal.get().put(key, value);
    }

    public static Object get(String key){
        return dataStoreThreadLocal.get().get(key);
    }
}
