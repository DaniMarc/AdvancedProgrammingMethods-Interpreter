package model.expressions;

import exceptions.MyException;
import model.ADTs.IDictionary;
import model.ADTs.IHeap;
import model.values.Value;

public interface IExpression {
    Value evaluate(IDictionary<String, Value> symbolTable, IHeap<Integer, Value> heap) throws MyException;
    String toString();
    IExpression deepCopy();
}