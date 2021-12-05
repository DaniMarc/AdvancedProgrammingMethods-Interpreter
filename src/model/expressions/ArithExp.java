package model.expressions;

import exceptions.MyException;
import model.ADTs.IDictionary;
import model.ADTs.IHeap;
import model.values.IntType;
import model.values.IntValue;
import model.values.Value;

public final class ArithExp implements IExpression{
    private final IExpression exp1;
    private final IExpression exp2;
    private final char operand;

    public ArithExp(char o, IExpression e1, IExpression e2){
        operand = o;
        exp1 = e1;
        exp2 = e2;
    }

    @Override
    public Value evaluate(IDictionary<String, Value> symbolTable, IHeap<Integer, Value> heap) throws MyException {
        Value intVal1, intVal2;
        intVal1 = exp1.evaluate(symbolTable, heap);
        if (intVal1.getType().equals(new IntType())){
            intVal2 = exp2.evaluate(symbolTable, heap);
            if(intVal2.getType().equals(new IntType())){
                int intValNumber1 = ((IntValue) intVal1).getValue();
                int intValNumber2 = ((IntValue) intVal2).getValue();
                switch (operand) {
                    case '+':
                        return new IntValue(intValNumber1 + intValNumber2);
                    case '-':
                        return new IntValue(intValNumber1 - intValNumber2);
                    case '*':
                        return new IntValue(intValNumber1 * intValNumber2);
                    case '/':
                        if (intValNumber2 == 0) throw new MyException("Division by zero!");
                        else return new IntValue(intValNumber1 / intValNumber2);
                    default:
                        return new IntValue(0);
                }
            } else throw new MyException("Second operand is not an int!");
        } else throw new MyException("First operand is not an int!");
    }

    @Override
    public String toString(){
        return "" + exp1.toString() + " " + operand + " " + exp2.toString();
    }

    public char getOp() {return this.operand;}

    public IExpression getFirst() {
        return this.exp1;
    }

    public IExpression getSecond() {
        return this.exp2;
    }

    @Override
    public IExpression deepCopy(){
        return new ArithExp(operand, exp1, exp2);
    }
}
