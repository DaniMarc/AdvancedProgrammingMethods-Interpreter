package model.statements;

import exceptions.MyException;
import model.ADTs.ExecutionStack;
import model.ADTs.IDictionary;
import model.PrgState;
import model.values.Type;
import model.values.Value;

public class ForkStmt implements IStmt{
    private final IStmt statement;

    public ForkStmt(IStmt s) { statement = s; }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IDictionary<String, Value> newSymTable = state.getSymTable().deepCopy();
        return new PrgState(new ExecutionStack<IStmt>(), newSymTable, state.getOutList(), state.getFileTable(), state.getHeap(), statement);
    }

    @Override
    public String toString(){
        return "fork("+statement.toString()+")";
    }

    @Override
    public IStmt deepCopy() {
        IStmt newS = statement.deepCopy();
        return new ForkStmt(newS);
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnv) throws MyException {
        statement.typeCheck(typeEnv.deepCopy());
        return typeEnv;
    }
}
