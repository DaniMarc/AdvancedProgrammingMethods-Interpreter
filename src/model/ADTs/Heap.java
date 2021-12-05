package model.ADTs;

import exceptions.MyException;
import model.values.Value;

import java.util.HashMap;
import java.util.Map;

public class Heap<K, V> implements IHeap<K, V>{
    private Map<Integer, Value> heapTable;
    private int nextFreeLocation;

    public Heap(){
        heapTable = new HashMap<>();
        nextFreeLocation = 1;
    }


    @Override
    public Integer add(Value value) {
        heapTable.put(nextFreeLocation, value);
        nextFreeLocation++;
        return nextFreeLocation-1;
    }


    @Override
    public void update(Integer id, Value value) {
        if(!isDefined(id))
            throw new MyException("No variable at this address");
        heapTable.replace(id, value);
    }

    @Override
    public Value lookup(Integer id) {
        return heapTable.get(id);
    }

    @Override
    public boolean isDefined(Integer id) {
        return heapTable.containsKey(id);
    }

    @Override
    public String toString(){
        StringBuilder retString = new StringBuilder("{ ");
        for(Integer i : heapTable.keySet()){
            retString.append(i.toString()).append("->").append(heapTable.get(i).toString()).append("; ");
        }
        retString.append("}");
        return retString.toString();
    }

    @Override
    public Map<Integer, Value> getContent() {
        return heapTable;
    }

    @Override
    public void setContent(Map<Integer, Value> newHeap) {
        heapTable = (HashMap<Integer, Value>) newHeap;
    }
}
