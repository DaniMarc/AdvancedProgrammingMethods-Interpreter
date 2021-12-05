package model.expressions;

import exceptions.MyException;
import model.ADTs.IDictionary;
import model.ADTs.IHeap;
import model.values.*;

public class RelExp implements IExpression{
    private final IExpression exp1;
    private final IExpression exp2;
    private final String operator;

    public RelExp(String o, IExpression e1, IExpression e2){
        exp1 = e1;
        exp2 = e2;
        operator = o;
    }

    @Override
    public Value evaluate(IDictionary<String, Value> symbolTable, IHeap<Integer, Value> heap) throws MyException {
        Value intVal1, intVal2;
        intVal1 = exp1.evaluate(symbolTable, heap);

        Type toCheckInt = new IntType();
        if(intVal1.getType().equals(toCheckInt)){
            intVal2 = exp2.evaluate(symbolTable, heap);
            if(intVal2.getType().equals(toCheckInt)){
                int intValNumber1 = ((IntValue) intVal1).getValue();
                int intValNumber2 = ((IntValue) intVal2).getValue();
                switch (operator){
                    case "<":
                        if(intValNumber1 < intValNumber2)
                            return new BoolValue(true);
                        return new BoolValue(false);
                    case "<=":
                        if(intValNumber1 <= intValNumber2)
                            return new BoolValue(true);
                        return new BoolValue(false);
                    case "==":
                        if(intValNumber1 == intValNumber2)
                            return new BoolValue(true);
                        return new BoolValue(false);
                    case "!=":
                        if(intValNumber1 != intValNumber2)
                            return new BoolValue(true);
                        return new BoolValue(false);
                    case ">":
                        if(intValNumber1 > intValNumber2)
                            return new BoolValue(true);
                        return new BoolValue(false);
                    case ">=":
                        if(intValNumber1 >= intValNumber2)
                            return new BoolValue(true);
                        return new BoolValue(false);
                    default:
                        return new BoolValue(false);
                }
            } else throw new MyException("Second expression must be integer!");
        } else throw new MyException("First expression must be integer!");
    }

    @Override
    public IExpression deepCopy() {
        return new RelExp(operator, exp1, exp2);
    }

    @Override
    public String toString(){
        return exp1.toString()+" "+operator+" "+exp2.toString();
    }
}
