package model.ADTs;


import java.util.Map;

public interface IDictionary<Type1, Type2> {
    void add(Type1 key, Type2 val);
    void remove(Type1 key);
    void update(Type1 key, Type2 val);
    Type2 lookup(Type1 key);
    boolean isDefined(Type1 key);
    String toString();
    Map<Type1, Type2> getContent();
    IDictionary<Type1, Type2> deepCopy();
}
