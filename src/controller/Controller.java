package controller;

import exceptions.MyException;
import model.PrgState;
import model.values.RefValue;
import model.values.Value;
import repository.IRepository;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller {
    private final IRepository repo;
    private final boolean display_state;
    private ExecutorService executor;

    public Controller(IRepository r, boolean ds){
        repo = r;
        display_state = ds;
    }

    private Map<Integer, Value> garbageCollector(List<Integer> symTableAddresses, Map<Integer, Value> heap){
        return heap.entrySet().stream()
                .filter(e->symTableAddresses.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private List<PrgState> removeCompletedPrograms(List<PrgState> inPrgList){
        return inPrgList.stream()
                .filter(PrgState::isNotCompleted)
                .collect(Collectors.toList());
    }

    public void oneStepForAllPrograms(List<PrgState> programStates){
        programStates.forEach(prg -> {
                                        try {
                                            repo.logPrgStateExec(prg);
                                        } catch (MyException | IOException me) { me.printStackTrace(); }
                                    });
        List<Callable<PrgState>> callList = programStates.stream()
                                                        .map((PrgState prgState) -> (Callable<PrgState>)(prgState::oneStep))
                                                        .collect(Collectors.toList());
        List<PrgState> newPrgList = null;
        try {
            newPrgList = executor.invokeAll(callList).stream()
                                                        .map(future -> {
                                                            try {
                                                                return future.get();
                                                            } catch (InterruptedException | ExecutionException e) {
                                                                e.printStackTrace();
                                                                return null;
                                                            }
                                                        })
                                                        .filter(Objects::nonNull)
                                                        .collect(Collectors.toList());
        } catch (MyException | InterruptedException me) {
            me.printStackTrace();
        }
        programStates.addAll(newPrgList);
        programStates.forEach(program -> {
                                            try {
                                                repo.logPrgStateExec(program);
                                            } catch (IOException e){
                                                e.printStackTrace();
                                            }
                                        });
        repo.setPrgList(programStates);
    }

    private List<Integer> getUsedAddresses(List<PrgState> programStateList){
        List<Integer> allSymbolsTableAddresses = programStateList.stream()
                .flatMap(pair -> pair.getSymTable().getContent().values().stream()
                        .filter(value -> value instanceof RefValue)
                        .map(value -> ((RefValue)value).getAddress()))
                .collect(Collectors.toList());

        Collection<Value> heapValues = programStateList.get(0).getHeap().getContent().values();
        List<Integer> heapAddresses = heapValues.stream()
                .filter(v->v instanceof RefValue)
                .map(v->{RefValue v1 = (RefValue) v; return v1.getAddress();})
                .collect(Collectors.toList());
        allSymbolsTableAddresses.addAll(heapAddresses);
        return allSymbolsTableAddresses;
    }

    public void allStep(){
        executor = Executors.newFixedThreadPool(2);
        List<PrgState> programStateList = removeCompletedPrograms(repo.getProgramList());
        while(programStateList.size() > 0){
            oneStepForAllPrograms(programStateList);
            List<Integer> allSymbolsTableAddresses = getUsedAddresses(programStateList);
            programStateList.get(0).getHeap().setContent(garbageCollector(allSymbolsTableAddresses, programStateList.get(0).getHeap().getContent()));
            programStateList = removeCompletedPrograms(repo.getProgramList());
        }
        executor.shutdownNow();
        repo.setPrgList(programStateList);
    }

    public void executeProgram(){
        allStep();
        System.out.println("Program ended successfully!");
    }
}
