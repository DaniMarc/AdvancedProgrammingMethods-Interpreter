package model.statements;

import exceptions.MyException;
import model.ADTs.IDictionary;
import model.PrgState;
import model.statements.IStmt;
import model.values.Type;
import model.values.Value;

public class VarDeclStmt implements IStmt {
    private String name;
    private Type varType;

    public VarDeclStmt(String n, Type vt){
        name = n;
        varType = vt;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IDictionary<String, Value> tmpSymTable = state.getSymTable();

        if(tmpSymTable.isDefined(name) || name == null)
            throw new MyException("Invalid or existing key!");

        tmpSymTable.add(name, varType.defaultValue());

        return state;
    }

    @Override
    public IStmt deepCopy() {
        return new VarDeclStmt(name, varType);
    }

    public String toString(){ return ""+varType.toString() + " " + name; }
}
