package model.expressions;

import exceptions.MyException;
import model.ADTs.IDictionary;
import model.ADTs.IHeap;
import model.values.Type;
import model.values.Value;

public class ValueExp implements IExpression{
    private Value number;

    public ValueExp(Value n){
        number = n;
    }

    @Override
    public Value evaluate(IDictionary<String, Value> symbolTable, IHeap<Integer, Value> heap) throws MyException {
        return number;
    }

    @Override
    public String toString() {
        return String.format("%s", number);
    }

    @Override
    public IExpression deepCopy() {
        return new ValueExp(number);
    }

    @Override
    public Type typeCheck(IDictionary<String, Type> typeEnv) throws MyException {
        return number.getType();
    }
}
