package model.statements;

import exceptions.MyException;
import model.ADTs.IDictionary;
import model.PrgState;
import model.statements.IStmt;
import model.values.Type;
import model.values.Value;

public class VarDeclStmt implements IStmt {
    private final String name;
    private final Type varType;

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

        return null;
    }

    @Override
    public IStmt deepCopy() {
        Type newType = varType.deepCopy();
        return new VarDeclStmt(name, newType);
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnv) throws MyException {
        typeEnv.add(name, varType);
        return typeEnv;
    }

    public String toString(){ return ""+varType.toString() + " " + name; }
}
