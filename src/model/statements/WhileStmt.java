package model.statements;

import exceptions.MyException;
import model.ADTs.IDictionary;
import model.ADTs.IHeap;
import model.ADTs.IStack;
import model.PrgState;
import model.expressions.IExpression;
import model.values.BoolType;
import model.values.BoolValue;
import model.values.Value;

public class WhileStmt implements IStmt{
    private final IExpression conditionExpression;
    private final IStmt toBeRepeatedStatement;

    public WhileStmt(IExpression e, IStmt s){
        conditionExpression = e;
        toBeRepeatedStatement = s;
    }


    @Override
    public PrgState execute(PrgState state) throws MyException {
        IDictionary<String, Value> symTbl = state.getSymTable();
        IHeap<Integer, Value> heap = state.getHeap();
        IStack<IStmt> execStack = state.getExecStack();

        Value tmpValue = conditionExpression.evaluate(symTbl, heap);
        if(tmpValue.getType().equals(new BoolType())){
            if(((BoolValue) tmpValue).getValue()){
                execStack.push(this);
                execStack.push(toBeRepeatedStatement);
            }
        } else throw new MyException("Expression can not evaluate to bool!");

        return state;
    }

    @Override
    public IStmt deepCopy() {
        IExpression newe = conditionExpression.deepCopy();
        IStmt news = toBeRepeatedStatement.deepCopy();
        return new WhileStmt(newe, news);
    }

    @Override
    public String toString(){
        return "While("+conditionExpression.toString()+"){ "+toBeRepeatedStatement.toString()+"; }";
    }
}
