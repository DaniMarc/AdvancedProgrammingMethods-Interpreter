package repository;

import exceptions.MyException;
import model.PrgState;

import model.ADTs.OutputList;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Repository implements IRepository{
    private OutputList<PrgState> myProgStates;
    private String logFilePath;

    public Repository(){
        myProgStates = new OutputList<>();
    }

    public Repository(PrgState ps, String lfp) {
        this();
        addProg(ps);
        logFilePath = lfp;
        try {
            DateTimeFormatter DTF = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy");
            LocalDateTime rightNow = LocalDateTime.now();
            String time;
            time = DTF.format(rightNow);
            // Clearing the log file of the previous program
            PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, false)));
            logFile.println("\t\t\t\t\t\t"+time);
            logFile.close();
        } catch (IOException e) {
            throw new MyException("Error opening log file!");
        }
    }

    @Override
    public void addProg(PrgState newPrg) {
        myProgStates.add(newPrg);
    }

    @Override
    public PrgState getCurrentProg(int index) {
        return myProgStates.getElement(index);
    }

    @Override
    public void logPrgStateExec() throws MyException, IOException {
        PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
        logFile.println("========================= STARTING LOG SESSION =========================");
        logFile.println(">Execution Stack");
        logFile.println("\t"+getCurrentProg(0).getExecStack().toString());
        logFile.println(">Symbol Table");
        logFile.println("\t"+getCurrentProg(0).getSymTable().toString());
        logFile.println(">Output List");
        logFile.println("\t"+getCurrentProg(0).getOutList().toString());
        logFile.println(">File Table");
        logFile.println("\t"+getCurrentProg(0).getFileTable().toString());
        logFile.println(">Heap");
        logFile.println("\t"+getCurrentProg(0).getHeap().toString());
        logFile.println("========================== ENDING LOG SESSION ==========================");
        logFile.flush();
        logFile.close();
    }
}
