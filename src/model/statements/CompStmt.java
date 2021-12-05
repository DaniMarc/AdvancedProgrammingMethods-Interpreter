package model.statements;

import exceptions.MyException;
import model.ADTs.IStack;
import model.PrgState;

public class CompStmt implements IStmt{
    IStmt first, second;

    public CompStmt(IStmt f, IStmt s){
        first = f;
        second = s;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IStack<IStmt> stack = state.getExecStack();
        stack.push(second);
        stack.push(first);
        return state;
    }

    @Override
    public IStmt deepCopy() {
        return new CompStmt(first, second);
    }

    public String toString(){
        return "[ "+first.toString() + " ; " + second.toString()+" ]";
//        return "Compound statement"+"[ first->"+first.toString() + ";\n\t\t\t\t\tsecond->" + second.toString()+"\n\t\t\t\t]";
    }
}
