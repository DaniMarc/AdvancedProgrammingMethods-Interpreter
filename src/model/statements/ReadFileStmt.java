package model.statements;

import exceptions.MyException;
import model.ADTs.IDictionary;
import model.ADTs.IHeap;
import model.PrgState;
import model.expressions.IExpression;
import model.values.*;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFileStmt implements IStmt{
    private final IExpression expression;
    private final String varName;

    public ReadFileStmt(IExpression e, String i){
        expression = e;
        varName = i;
    }

    @Override
    public String toString(){
        return "readFile("+expression.toString()+", "+ varName +")";
    }

    @Override
    public IStmt deepCopy() {
        IExpression newExp = expression.deepCopy();
        return new ReadFileStmt(newExp, varName);
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IDictionary<String, Value> symTbl = state.getSymTable();
        IDictionary<StringValue, BufferedReader> fileTbl = state.getFileTable();
        IHeap<Integer, Value> heap = state.getHeap();

        Type toCheckInt = new IntType();
        if(symTbl.isDefined(varName)){
            if(symTbl.lookup(varName).getType() != toCheckInt){
                Value tmpValue = expression.evaluate(symTbl, heap);
                Type toCheckString = new StringType();
                if(tmpValue.getType().equals(toCheckString)){
                    StringValue stringValue = (StringValue) tmpValue;
                    if(fileTbl.isDefined(stringValue)){
                        BufferedReader bufferedReader = fileTbl.lookup(stringValue);
                        try{
                            String line = bufferedReader.readLine();
                            int buffer;
                            if(line.length() == 0)
                                buffer = 0;
                            else
                                buffer = Integer.parseInt(line);
                            IntValue intValue = new IntValue(buffer);
                            symTbl.update(varName, intValue);
                        } catch(IOException e){
                            throw new MyException("Error while reading from file "+stringValue);
                        }
                    } else throw new MyException("There is no such file!");
                } else throw new MyException("The file name should be a string!");
            } else throw new MyException("The variable to read must be of type Int!");
        } else throw new MyException("There is no such file in the file table!");
        return state;
    }
}
