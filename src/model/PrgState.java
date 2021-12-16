package model;

import exceptions.MyException;
import model.ADTs.IDictionary;
import model.ADTs.IHeap;
import model.ADTs.IList;
import model.ADTs.IStack;
import model.statements.IStmt;
import model.values.StringValue;
import model.values.Value;

import java.io.BufferedReader;

public class PrgState {
    private IStack<IStmt> exeStack;
    private IDictionary<String, Value> symTable;
    private IList<Value> outList;
    private IDictionary<StringValue, BufferedReader> fileTable;
    private IHeap<Integer, Value> heap;
    private IStmt originalProgram;
    static int MaxPrgId = 0;
    private final int currentId;

    public PrgState(IStack<IStmt> es, IDictionary<String, Value> st, IList<Value> ol, IDictionary<StringValue, BufferedReader> ft, IHeap<Integer, Value> h, IStmt op){
        exeStack = es;
        symTable = st;
        outList = ol;
        fileTable = ft;
        originalProgram = op;
        exeStack.push(op);
        heap = h;
        setMaxPrgId(getMaxPrgId() + 1);
        currentId = getMaxPrgId();
    }

    public static synchronized int getMaxPrgId(){ return MaxPrgId; }

    public static synchronized void setMaxPrgId(int newId){ MaxPrgId = newId; }

    public int getCurrentId() { return currentId; }

    public IStack<IStmt> getExecStack() { return exeStack; }

    public IDictionary<String, Value> getSymTable() {return symTable;}

    public IList<Value> getOutList() { return outList; }

    public IDictionary<StringValue, BufferedReader> getFileTable() { return fileTable; }

    public IHeap<Integer, Value> getHeap() { return heap; }

    public void setExecStack(IStack<IStmt> nes) { exeStack = nes; }

    public void setSymTable(IDictionary<String, Value> nsm) { symTable = nsm; }

    public void setOutList(IList<Value> nol) { outList = nol; }

    public String toString() { return String.format("Program %d state: \n Execution stack:\n%s\n Symbol table\n%s\n Output list \n%s\n File table \n%s\n",
                                                    currentId,
                                                    exeStack.toString(),
                                                    symTable.toString(),
                                                    outList.toString(),
                                                    fileTable.toString()); }

    public static IStmt deepCopy(IStmt program) { return program.deepCopy(); }

    public boolean isNotCompleted() {
        return !exeStack.isEmpty();
    }

    public PrgState oneStep() throws MyException {
        if(exeStack.isEmpty())
            throw new MyException("Program stack is empty!");
        IStmt currentState = exeStack.pop();
        return currentState.execute(this);
    }
}
