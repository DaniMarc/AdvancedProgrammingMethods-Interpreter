package model.ADTs;

import java.util.Stack;

public class ExecutionStack<ValueType> implements IStack<ValueType>{
    private Stack<ValueType> execStack;

    public ExecutionStack(){
        execStack = new Stack<>();
    }

    @Override //removes and returns the top of the stack
    public ValueType pop() {
        return execStack.pop();
    }

    @Override //adds an element of generic type Type1 to the top of the stack
    public void push(ValueType val) {
        execStack.push(val);
    }

    @Override //return the status of emptiness of the stack
    public boolean isEmpty() {
        return execStack.isEmpty();
    }

    @Override
    public String toString(){
        String[] stackString = new String[execStack.size()];
        StringBuilder retString = new StringBuilder("[ ");
        int stackIterator = 0;
        for(ValueType i : execStack){
            stackString[stackIterator] = i.toString();
            stackIterator++;
        }
        for(int i = stackIterator-1; i>=0; i--){
            retString.append(stackString[i]).append(" | ");
        }
        retString.append("]");
        return retString.toString();
    }
}
