package model.ADTs;


import model.values.Value;

import java.util.Map;

public interface IHeap<K, V> {
    Integer add(Value value);
    void update(Integer id, Value value);
    Value lookup(Integer id);
    boolean isDefined(Integer id);
    String toString();
    Map<Integer, Value> getContent();
    void setContent(Map<Integer, Value> newHeap);
}
