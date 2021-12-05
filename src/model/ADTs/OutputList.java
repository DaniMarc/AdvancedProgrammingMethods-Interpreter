package model.ADTs;

import java.util.ArrayList;

public class OutputList<ValueType> implements IList<ValueType>{
    private ArrayList<ValueType> outList;

    public OutputList(){
        outList = new ArrayList<>();
    }

    @Override //appends an element of generic type Type1 to the List
    public void add(ValueType val) {
        outList.add(val);
    }

    @Override //removes and returns the head of the List
    public ValueType getElement(int index) {
        return outList.get(index);
    }

    @Override
    public String toString(){
        return outList.toString();
    }
}
