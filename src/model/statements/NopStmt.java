package model.statements;

import exceptions.MyException;
import model.ADTs.IDictionary;
import model.PrgState;
import model.statements.IStmt;
import model.values.Type;

public class NopStmt implements IStmt {

    public String toString(){ return "NOP Statement"; }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new NopStmt();
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }

}
