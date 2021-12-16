package repository;

import exceptions.MyException;
import model.ADTs.OutputList;
import model.PrgState;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface IRepository {
    void addProg(PrgState newPrg);
    ArrayList<PrgState> getProgramList();
    void logPrgStateExec(PrgState program) throws MyException, IOException;
    void setPrgList(List<PrgState> newPrgStateList);
}
