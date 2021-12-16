package model.statements;

import exceptions.MyException;
import model.ADTs.IDictionary;
import model.ADTs.IHeap;
import model.PrgState;
import model.expressions.IExpression;
import model.values.StringType;
import model.values.StringValue;
import model.values.Type;
import model.values.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseRFileStmt implements IStmt{
    private final IExpression expression;

    public CloseRFileStmt(IExpression e){ expression = e;}

    @Override
    public String toString(){
        return "closeRFile("+expression.toString()+")";
    }

    @Override
    public IStmt deepCopy() {
        IExpression newExp = expression.deepCopy();
        return new CloseRFileStmt(newExp);
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnv) throws MyException {
        Type expType = expression.typeCheck(typeEnv);
        if (expType.equals(new StringType()))
            return typeEnv;
        else throw new MyException("Expression in Open(expression) must be a string!");
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IDictionary<String, Value> symTbl = state.getSymTable();
        IDictionary<StringValue, BufferedReader> fileTbl = state.getFileTable();
        IHeap<Integer, Value> heap = state.getHeap();

        Value tmpValue = expression.evaluate(symTbl, heap);
        Type toCheckString = new StringType();
        if(tmpValue.getType().equals(toCheckString)){
            StringValue stringValue = (StringValue) tmpValue;
            if(fileTbl.isDefined(stringValue)){
                BufferedReader bufferedReader = fileTbl.lookup(stringValue);
                try{
                    bufferedReader.close();
                } catch (IOException e){
                    throw new MyException("Error at closing the file "+stringValue);
                }
                fileTbl.remove(stringValue);
            } else throw new MyException("The file does not exist!");
        } else throw new MyException("The expression must be of string type!");
        return null;
    }
}
