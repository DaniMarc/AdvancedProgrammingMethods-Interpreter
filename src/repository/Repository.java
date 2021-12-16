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
import java.util.ArrayList;
import java.util.List;

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
    public ArrayList<PrgState> getProgramList() {
        return myProgStates.getOutList();
    }

    @Override
    public void logPrgStateExec(PrgState crtProgram) throws MyException, IOException {
        PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
        logFile.println("========================= STARTING LOG SESSION =========================");
        if (crtProgram.getCurrentId() >= 10)
            logFile.println((String.format("Currently printing program with ID: %d", crtProgram.getCurrentId()-10+1)));
        else
            logFile.println((String.format("Currently printing program with ID: %d", crtProgram.getCurrentId())));
        logFile.println(">Execution Stack");
        logFile.println("\t"+ crtProgram.getExecStack().toString());
        logFile.println(">Symbol Table");
        logFile.println("\t"+ crtProgram.getSymTable().toString());
        logFile.println(">Output List");
        logFile.println("\t"+ crtProgram.getOutList().toString());
        logFile.println(">File Table");
        logFile.println("\t"+ crtProgram.getFileTable().toString());
        logFile.println(">Heap");
        logFile.println("\t"+ crtProgram.getHeap().toString());
        logFile.println("========================== ENDING LOG SESSION ==========================");
        logFile.flush();
        logFile.close();
    }

    @Override
    public void setPrgList(List<PrgState> newPrgStateList) {
        myProgStates.setOutList((ArrayList<PrgState>) newPrgStateList);
    }
}
