package model.statements;

import exceptions.MyException;
import model.ADTs.IDictionary;
import model.ADTs.IHeap;
import model.PrgState;
import model.expressions.IExpression;
import model.values.RefType;
import model.values.RefValue;
import model.values.Type;
import model.values.Value;

public class HeapAllocationStmt implements IStmt{
    private String id;
    private IExpression expression;

    public HeapAllocationStmt(String i, IExpression e){
        id = i;
        expression = e;
    }

    @Override
    public String toString(){
        return "new("+id+", "+expression.toString()+")";
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IDictionary<String, Value> symTbl = state.getSymTable();
        IHeap<Integer, Value> heap = state.getHeap();

        if(symTbl.isDefined(id)){
            if(symTbl.lookup(id) instanceof RefValue){
                Value tmpValue = expression.evaluate(symTbl, heap);
                RefValue refValue = (RefValue) symTbl.lookup(id);
                if(refValue.getLocationType().equals(tmpValue.getType())){
                    Integer allocatedAddress = heap.add(tmpValue);
                    RefValue newRef = new RefValue(allocatedAddress, refValue.getLocationType());
                    symTbl.update(id, newRef);
                } else throw new MyException("The expression references a wrong type!");
            } else throw new MyException("The variable "+id+" is not of RefType!");
        } else throw new MyException("The variable "+id+" is not defined!");
        return null;
    }

    @Override
    public IStmt deepCopy() {
        IExpression ex = expression.deepCopy();
        return new HeapAllocationStmt(id, ex);
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnv) throws MyException {
        Type varType = typeEnv.lookup(id);
        Type expType = expression.typeCheck(typeEnv);
        if (varType.equals(new RefType(expType)))
            return typeEnv;
        else throw new MyException("New statement: left side and right side have different types!");
    }
}
