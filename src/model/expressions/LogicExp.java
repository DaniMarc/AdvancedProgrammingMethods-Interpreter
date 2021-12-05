package model.expressions;

import exceptions.MyException;
import model.ADTs.IDictionary;
import model.ADTs.IHeap;
import model.values.BoolType;
import model.values.BoolValue;
import model.values.Value;

public class LogicExp implements IExpression{
    private final IExpression exp1;
    private final IExpression exp2;
    private int operand; // 1-& ; 2-|; 3-^ ; 4-!=

    public LogicExp(String o, IExpression e1, IExpression e2){
        switch (o) {
            case "&" -> operand = 1;
            case "|" -> operand = 2;
            case "^" -> operand = 3;
            case "!=" -> operand = 4;
        }
        exp1 = e1;
        exp2 = e2;
    }

    @Override
    public Value evaluate(IDictionary<String, Value> symbolTable, IHeap<Integer, Value> heap) throws MyException {
        Value val1, val2;
        val1 = exp1.evaluate(symbolTable, heap);
        if(val1.getType().equals(new BoolType())) {
            val2 = exp2.evaluate(symbolTable, heap);
            if (val2.getType().equals(new BoolType())) {
                boolean boolVal1 = ((BoolValue) val1).getValue();
                boolean boolVal2 = ((BoolValue) val2).getValue();
                return switch (operand) {
                    case 1 -> new BoolValue(boolVal1 & boolVal2);
                    case 2 -> new BoolValue(boolVal1 | boolVal2);
                    case 3 -> new BoolValue(boolVal1 ^ boolVal2);
                    case 4 -> new BoolValue(boolVal1 != boolVal2);
                    default -> new BoolValue(false);
                };
            }
            else throw new MyException("Second operand is not boolean!");
        }
        else throw new MyException("First operand is not boolean!");
    }

    @Override
    public String toString() {
        return "" + exp1.toString() + " " + operand + " " + exp2.toString();
    }

    @Override
    public IExpression deepCopy() {
        String newOp="";
        switch (operand) {
            case 1 -> newOp = "&";
            case 2 -> newOp = "|";
            case 3 -> newOp = "^";
            case 4 -> newOp = "!=";
        }
        return new LogicExp(newOp, exp1, exp2);
    }
}
