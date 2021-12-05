package model.ADTs;

public interface IStack<Type1> {
    Type1 pop();
    void push(Type1 val);
    boolean isEmpty();
    String toString();
}
