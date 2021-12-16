package model.statements;

import exceptions.MyException;
import model.ADTs.IDictionary;
import model.ADTs.IHeap;
import model.ADTs.IStack;
import model.PrgState;
import model.expressions.IExpression;
import model.values.Type;
import model.values.Value;

public final class AssignStmt implements IStmt{
    private final String id;
    private final IExpression expression;

    public AssignStmt(String i, IExpression e){
        id = i;
        expression = e;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IDictionary<String, Value> symTable = state.getSymTable();
        IHeap<Integer, Value> heap = state.getHeap();
        if (symTable.isDefined(id)){
            Value val = expression.evaluate(symTable, heap);
            Type typeId = (symTable.lookup(id)).getType();
            if(val.getType().equals(typeId))
                symTable.update(id, val);
            else
                throw new MyException("Declared type of variable "+id+" and type of the assigned expression do not match");
        }
        else throw new MyException("The used variable "+id+" was not declared before!");
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new AssignStmt(id, expression);
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnv) throws MyException {
        Type typeVar = typeEnv.lookup(id);
        Type typeExp = expression.typeCheck(typeEnv);
        if (typeVar.equals(typeExp))
            return typeEnv;
        else throw new MyException("Assignment: right hand side and left hand side have different types! ["+id+"("+typeEnv.lookup(id)+") "+"can't be assigned "+expression+"]");
    }

    @Override
    public String toString(){
        return ""+id+"="+expression.toString();
    }
}
