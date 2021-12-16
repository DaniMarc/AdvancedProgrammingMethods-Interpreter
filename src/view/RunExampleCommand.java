package view;

import controller.Controller;
import exceptions.MyException;

public class RunExampleCommand extends Command {
    private Controller ctrl;
    public RunExampleCommand(String k, String d, Controller c){
        super(k, d);
        ctrl = c;
    }

    @Override
    public void execute() {
        try{
            ctrl.executeProgram();
        } catch (MyException me) {
            System.out.println(me.getMessage());
        }
    }
}
