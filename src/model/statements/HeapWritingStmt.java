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

import java.sql.Ref;

public class HeapWritingStmt implements IStmt{
    private String id;
    private IExpression expression;

    public HeapWritingStmt(String i, IExpression e){
        id = i;
        expression = e;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IDictionary<String, Value> symTable = state.getSymTable();
        IHeap<Integer, Value> heap = state.getHeap();

        if(symTable.isDefined(id)){
            Value tmpValue = symTable.lookup(id);
            if(tmpValue.getType() instanceof RefType){
                RefValue refValue = (RefValue) tmpValue;
                int address = refValue.getAddress();
                if(heap.isDefined(address)){
                    Value evaluatedExpression = expression.evaluate(symTable, heap);
                    if(evaluatedExpression.getType().equals(refValue.getLocationType())){
                        heap.update(address, evaluatedExpression);
                    } else throw new MyException("There is no match between types in Symbol Table and Heap!");
                } else throw new MyException("There is no variable at this address! #NoodyHome");
            } else throw new MyException("The value of the variable "+id+" must be of RefType!");
        } else throw new MyException("There is no variable with this name");
        return null;
    }

    @Override
    public String toString(){
        return "wH("+id+", "+expression.toString()+")";
    }

    @Override
    public IStmt deepCopy() {
        IExpression exp = expression.deepCopy();
        return new HeapWritingStmt(id, exp);
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnv) throws MyException {
        Type varType = typeEnv.lookup(id);
        Type expType = expression.typeCheck(typeEnv);
        if(varType.equals(new RefType(expType)))
            return typeEnv;
        else throw new MyException("Heap writing: left side and right side have different types!");
    }
}
