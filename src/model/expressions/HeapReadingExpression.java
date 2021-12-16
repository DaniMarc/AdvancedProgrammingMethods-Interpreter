package model.expressions;

import exceptions.MyException;
import model.ADTs.IDictionary;
import model.ADTs.IHeap;
import model.values.RefType;
import model.values.RefValue;
import model.values.Type;
import model.values.Value;

public class HeapReadingExpression implements IExpression{
    private IExpression expression;

    public HeapReadingExpression(IExpression e){
        expression = e;
    }

    @Override
    public Value evaluate(IDictionary<String, Value> symbolTable, IHeap<Integer, Value> heap) throws MyException {
        Value tmpValue = expression.evaluate(symbolTable, heap);
        if(tmpValue instanceof RefValue){
            RefValue refValue = (RefValue) tmpValue;
            int address = refValue.getAddress();
            if(heap.isDefined(address)){
                return heap.lookup(address);
            } else throw new MyException("There is no such variable in the heap at the specified address!");
        } else throw new MyException("The expression is not a RefValue!");
    }

    @Override
    public IExpression deepCopy() {
        IExpression exp = expression.deepCopy();
        return new HeapReadingExpression(exp);
    }

    @Override
    public Type typeCheck(IDictionary<String, Type> typeEnv) throws MyException {
        Type typ = expression.typeCheck(typeEnv);
        if (typ instanceof RefType) {
            RefType refType = (RefType) typ;
            return refType.getInner();
        } else throw new MyException("The rH argument is not a RefType!");
    }

    @Override
    public String toString(){
        return "rH("+expression.toString()+")";
    }
}
