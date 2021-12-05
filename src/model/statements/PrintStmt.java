package model.statements;

import exceptions.MyException;
import model.ADTs.IDictionary;
import model.ADTs.IHeap;
import model.ADTs.IList;
import model.PrgState;
import model.expressions.IExpression;
import model.values.Value;

public class PrintStmt implements IStmt{
    private IExpression exp;

    public PrintStmt(IExpression e){
        exp = e;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IList<Value> outList = state.getOutList();
        IDictionary<String, Value> symTable = state.getSymTable();
        IHeap<Integer, Value> heap = state.getHeap();

        outList.add(exp.evaluate(symTable, heap));

        return state;
    }

    @Override
    public IStmt deepCopy() {
        return new PrintStmt(exp);
    }

    @Override
    public String toString(){
        return "print "+exp.toString();
    }

}
