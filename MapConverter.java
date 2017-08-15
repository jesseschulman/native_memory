import jdk.nashorn.api.scripting.*;

import javax.script.ScriptException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MapConverter {

    private ScriptObjectMirror globalBindings;
    private static final String ALPHA = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final Random RANDOM = new Random();


    public MapConverter(ScriptObjectMirror bindings) {
        this.globalBindings = bindings;
    }

    public Object getScriptObject() {
        return getJavascriptValueFromJava(getMap(true));
    }

    public void convert(int mapCount) throws ScriptException {
        System.out.println(new SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SS").format(new Date()) + " converting " + mapCount + " maps");
        for (int i = 0; i < mapCount; i++)
            getJavascriptValueFromJava(getMap(true));
    }

    private String randomString() {
        return new StringBuilder()
                .append(ALPHA.charAt(RANDOM.nextInt(52)))
                .append(ALPHA.charAt(RANDOM.nextInt(52)))
                .append(ALPHA.charAt(RANDOM.nextInt(52)))
                .toString();
    }

    private Map<String, Object> getMap(boolean depth) {
        Map<String, Object> map = new HashMap<>();
        map.put("intVal" + randomString(), -1);
        map.put("intVal2" + randomString(), 100);
        map.put("stringVal" + randomString(), randomString());
        map.put("stringVal2" + randomString(), randomString());

        if (depth) {
            map.put("mapVal" + randomString(), getMap(false));
            map.put("mapVal2" + randomString(), getMap(false));
            List<Object> list = new ArrayList<>();
            list.add("one" + randomString());
            list.add(2);
            list.add(getMap(false));
            map.put("listVal1" + randomString(), list);
            map.put("listVal2" + randomString(), list);
        }

        return map;
    }

    private ScriptObjectMirror createNewGlobalObject(String objName) {
        return (ScriptObjectMirror) ((ScriptObjectMirror) globalBindings.getMember(objName)).newObject();
    }

    private Object getJavascriptValueFromJava(Object value) {
        if (value instanceof List) {
            return javaListObjectToJS((List)value);
        }

        if (value instanceof Map) {
            return javaMapObjectToJS((Map)value);
        }

        if ((value instanceof JSObject) || (value instanceof Double) || (value instanceof Float) ||
                (value instanceof Integer) || (value instanceof Long) || (value instanceof Boolean) ||
                (value == null) || ScriptObjectMirror.isUndefined(value)) {
            return value;
        }

        return value.toString();
    }

    @SuppressWarnings({ "rawtypes" })
    private ScriptObjectMirror javaListObjectToJS(List a) {
        ScriptObjectMirror arrayObj = createNewGlobalObject("Array");

        for(int i = 0; i < a.size(); i++)
            arrayObj.setSlot(i, getJavascriptValueFromJava(a.get(i)));

        return arrayObj;
    }

    @SuppressWarnings("rawtypes")
    private ScriptObjectMirror javaMapObjectToJS(Map m) {
        ScriptObjectMirror objectObj = createNewGlobalObject("Object");

        for (Object n : m.keySet()) {
            String name = n.toString();
            Object value = getJavascriptValueFromJava(m.get(name));
            objectObj.put(name, value);
        }

        return objectObj;
    }
}
