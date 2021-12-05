package model.statements;

import exceptions.MyException;
import model.ADTs.IStack;
import model.PrgState;
import model.expressions.IExpression;
import model.values.BoolType;
import model.values.BoolValue;
import model.values.Value;

import javax.swing.*;

public class IfStmt implements IStmt{
    IExpression expression;
    IStmt thenS;
    IStmt elseS;

    public IfStmt(IExpression ex, IStmt t, IStmt e){
        expression = ex;
        thenS = t;
        elseS = e;
    }

    public String toString(){
        return ""+"(IF(" + expression.toString() + ")THEN("+thenS.toString()+")ELSE("+elseS.toString()+"))";
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        Value cond;
        IStack<IStmt> tmpStack = state.getExecStack();

        cond = expression.evaluate(state.getSymTable(), state.getHeap());
        if(!(cond.getType() instanceof BoolType)){
            throw new MyException("Conditional expression is not a boolean!");
        }

        if(((BoolValue)cond).getValue())
            tmpStack.push(thenS);
        else
            tmpStack.push(elseS);

        state.setExecStack(tmpStack);

        return state;
    }

    @Override
    public IStmt deepCopy() {
        return new IfStmt(expression, thenS, elseS);
    }
}
