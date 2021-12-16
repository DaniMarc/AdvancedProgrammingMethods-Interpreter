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
import java.io.FileNotFoundException;
import java.io.FileReader;

public class OpenRFileStmt implements IStmt{
    private final IExpression expression;

    public OpenRFileStmt(IExpression exp){
        expression = exp;
    }

    @Override
    public String toString(){
        return "openRFile("+expression.toString()+")";
    }

    @Override
    public IStmt deepCopy(){
        IExpression exp = expression.deepCopy();
        return new OpenRFileStmt(exp);
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
            if(fileTbl.isDefined(stringValue)) throw new MyException("The file is already defined in the file table");
            else{
                try {
                    FileReader fileReader = new FileReader(stringValue.getValue());
                    BufferedReader bufferedReader = new BufferedReader(fileReader);
                    fileTbl.add(stringValue, bufferedReader);
                } catch (FileNotFoundException e) {
                    throw new MyException("There is no file with this name! "+stringValue);
                }
            }
        }
        else {
            throw new MyException("The expression must be a StringType!");
        }
        return null;
    }
}
