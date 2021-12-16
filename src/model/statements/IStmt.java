package model.statements;

import exceptions.MyException;
import model.ADTs.IDictionary;
import model.PrgState;
import model.values.Type;

public interface IStmt {
    PrgState execute(PrgState state) throws MyException;
    IStmt deepCopy();
    IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnv) throws MyException;
}
