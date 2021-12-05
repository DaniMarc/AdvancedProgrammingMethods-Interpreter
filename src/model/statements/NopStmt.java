package model.statements;

import exceptions.MyException;
import model.PrgState;
import model.statements.IStmt;

public class NopStmt implements IStmt {

    public String toString(){ return "NOP Statement"; }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        return state;
    }

    @Override
    public IStmt deepCopy() {
        return new NopStmt();
    }

}
