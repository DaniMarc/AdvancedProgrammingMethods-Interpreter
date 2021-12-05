package repository;

import exceptions.MyException;
import model.PrgState;

import java.io.IOException;

public interface IRepository {
    void addProg(PrgState newPrg);
    PrgState getCurrentProg(int index);
    void logPrgStateExec() throws MyException, IOException;
}
