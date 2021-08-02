package helper.json;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class JsonObject implements JsonParsable, Iterable<String>
{
    private final Map<String, Object> content;

    public JsonObject(){
        this.content = new HashMap<>();
    }

    public JsonObject(String key, Object object) {
        this();
        content.put(key, object);
    }

    @Override
    public String toJsonString() {
        StringBuilder s = new StringBuilder("{");
        for (String key : content.keySet()) {
            Object value = content.get(key);
            String valueString;
            if (value instanceof JsonParsable){
                valueString = ((JsonParsable) value).toJsonString();
            } else if(value instanceof Number || value == null){
                valueString = String.valueOf(value);
            } else {
                valueString = JSonParser.jsonStringLiteral(value);
            }
            s.append(JSonParser.jsonStringLiteral(key)).append(':').append(valueString).append(",");
        }
        s.replace(s.length() - 1, s.length(), "");
        s.append("}");
        return s.toString();
    }

    public void put(String label, Object object) {
        content.put(label, object);
    }

    public Object get(String key) {
        return content.get(key);
    }

    @Override
    public String toString(){
        return this.toJsonString();
    }

    public JsonList getList(String key) {
        return (JsonList) content.get(key);
    }

    public JsonObject getObject(String key){
        return (JsonObject) content.get(key);
    }

    public String getString(String key){
        return (String) content.get(key);
    }

    public int getInt(String key){
        return (int) content.get(key);
    }

    public double getDouble(String key){
        return (double) content.get(key);
    }

    @Override
    public Iterator<String> iterator() {
        return content.keySet().iterator();
    }

    public boolean containsKey(String key) {
        return content.containsKey(key);
    }
}
