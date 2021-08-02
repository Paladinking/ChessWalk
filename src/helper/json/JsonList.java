package helper.json;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JsonList implements JsonParsable, Iterable<Object> {

    private final List<Object> content;

    public JsonList() {
        content = new ArrayList<>();
    }

    public boolean add(Object o) {
        return content.add(o);
    }

    public boolean remove(Object o) {
        return content.remove(o);
    }

    @Override
    public String toJsonString(){
        StringBuilder s = new StringBuilder("[");
        for (Object object : content) {
            if (object instanceof JsonParsable){
                s.append(((JsonParsable) object).toJsonString());
            } else if(object instanceof Number || object == null){
                s.append(object);
            } else {
                s.append(JSonParser.jsonStringLiteral(object));
            }
            s.append(",");
        }
        s.replace(s.length() - 1, s.length(), "");
        s.append("]");
        return s.toString();
    }

    @Override
    public String toString(){
        return this.toJsonString();
    }

    @Override
    public Iterator<Object> iterator() {
        return content.iterator();
    }

    public List<Object> toList(){
        return content;
    }
}
