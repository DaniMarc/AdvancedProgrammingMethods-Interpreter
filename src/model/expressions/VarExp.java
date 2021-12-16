package model.expressions;

import exceptions.MyException;
import model.ADTs.IDictionary;
import model.ADTs.IHeap;
import model.values.Type;
import model.values.Value;

public class VarExp implements IExpression {
    private String id;

    public VarExp(String i) {
        id = i;
    }

    public Value evaluate(IDictionary<String,Value> symTable, IHeap<Integer, Value> heap) {
        return symTable.lookup(id);
    }

    public String toString() {return id;}

    @Override
    public IExpression deepCopy() {
        return new VarExp(id);
    }

    @Override
    public Type typeCheck(IDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv.lookup(id);
    }
}
