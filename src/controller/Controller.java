package controller;

import exceptions.MyException;
import model.ADTs.IStack;
import model.PrgState;
import model.statements.CompStmt;
import model.statements.IStmt;
import model.values.RefValue;
import model.values.Value;
import repository.IRepository;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Controller {
    private final IRepository repo;
    private final boolean display_state;
    private final boolean detailed_execution;

    public Controller(IRepository r, boolean ds, boolean d){
        repo = r;
        display_state = ds;
        detailed_execution = d;
    }

    private PrgState oneStep(PrgState state) throws MyException {
        IStack<IStmt> execStack = state.getExecStack();
        if(execStack.isEmpty())
            throw new MyException("Program stack is empty!");

        IStmt currentState = execStack.pop();
        if(detailed_execution)
            if(!(currentState instanceof CompStmt))
                System.out.println("EXECUTING NOW:"+currentState);
        return currentState.execute(state);
    }

    private Map<Integer, Value> garbageCollector(List<Integer> symTableAddresses, Map<Integer, Value> heap){
        return heap.entrySet().stream()
                .filter(e->symTableAddresses.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private List<Integer> getUsedAddresses(Collection<Value> symTableValues, Collection<Value> heapValues){
        List<Integer> symbolTableAddresses = symTableValues.stream()
                                                .filter(v->v instanceof RefValue)
                                                .map(v->{RefValue v1 = (RefValue) v; return v1.getAddress();})
                                                .collect(Collectors.toList());

        List<Integer> heapAddresses = heapValues.stream()
                            .filter(v->v instanceof RefValue)
                            .map(v->{RefValue v1 = (RefValue) v; return v1.getAddress();})
                            .collect(Collectors.toList());

        symbolTableAddresses.addAll(heapAddresses);
        return symbolTableAddresses;
    }

    public void allStep(){
        PrgState program = repo.getCurrentProg(0);
        System.out.println("Starting the program...");

        //Printing the initial program
        if(display_state)
            try { repo.logPrgStateExec();
            } catch (IOException e) {throw new MyException("Error displaying initial program");}

        while(!program.getExecStack().isEmpty()){
            oneStep(program);
            if(display_state)
                // Printing the program on its way to finish
                try { repo.logPrgStateExec();
                } catch (IOException e) {throw new MyException("Error displaying the step by step program");}
            Collection<Value> symbolTableValues = program.getSymTable().getContent().values();
            Collection<Value> heapValues        = program.getHeap().getContent().values();
            program.getHeap().setContent(garbageCollector(getUsedAddresses(symbolTableValues, heapValues),
                    program.getHeap().getContent()));
        }
        System.out.println("Program ended successfully!");
        System.out.println("The result is: "+program.getOutList());
    }
}
