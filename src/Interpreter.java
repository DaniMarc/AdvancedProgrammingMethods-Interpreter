import controller.Controller;
import exceptions.MyException;
import model.ADTs.Dictionary;
import model.ADTs.ExecutionStack;
import model.ADTs.Heap;
import model.ADTs.OutputList;
import model.PrgState;
import model.expressions.*;
import model.statements.*;
import model.values.*;
import repository.IRepository;
import repository.Repository;
import view.ExitCommand;
import view.RunExampleCommand;
import view.TextMenu;

import java.io.BufferedReader;

public class Interpreter {
    public static void main(String[] args) {
        boolean display_program_state = true;

        IStmt example1 = new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new AssignStmt("v",new ValueExp(new IntValue(2))), new PrintStmt(new VarExp("v"))));
        IStmt example2 = new CompStmt(new VarDeclStmt("a", new IntType()), new CompStmt(new VarDeclStmt("b",new IntType()), new CompStmt(new AssignStmt("a", new ArithExp('+',new ValueExp(new IntValue(2)),new ArithExp('*',new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))), new CompStmt(new AssignStmt("b",new ArithExp('+',new VarExp("a"), new ValueExp(new IntValue(1)))), new PrintStmt(new VarExp("b"))))));
        IStmt example3 = new CompStmt(new VarDeclStmt("a", new BoolType()), new CompStmt(new VarDeclStmt("v", new IntType()),new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))), new CompStmt(new IfStmt(new VarExp("a"),new AssignStmt("v",new ValueExp(new IntValue(2))), new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new VarExp("v"))))));
        IStmt example4 = new CompStmt(new VarDeclStmt("varf",new StringType()), new CompStmt(new AssignStmt("varf", new ValueExp(new StringValue("test.in"))), new CompStmt(new OpenRFileStmt(new VarExp("varf")),new CompStmt(new VarDeclStmt("varc",new IntType()), new CompStmt(new ReadFileStmt(new VarExp("varf"), "varc"),new CompStmt(new PrintStmt(new VarExp("varc")), new CompStmt(new ReadFileStmt(new VarExp("varf"), "varc"),  new CompStmt(new PrintStmt(new VarExp("varc")),new CloseRFileStmt(new VarExp("varf"))))))))));
        IStmt example5 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())), new CompStmt(new HeapAllocationStmt("v",new ValueExp(new IntValue(20))),
                new CompStmt(new VarDeclStmt("a",new RefType(new RefType(new IntType()))),new CompStmt( new HeapAllocationStmt("a", new VarExp("v")),
                        new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new VarExp("a")))))));
        IStmt example6 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new HeapAllocationStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(new HeapAllocationStmt("a", new VarExp("v")),
                                        new CompStmt(new PrintStmt(new HeapReadingExpression(new VarExp("v"))),
                                                new PrintStmt(new ArithExp('+',new HeapReadingExpression(new HeapReadingExpression(new VarExp("a"))),new ValueExp(new IntValue(5)))))))));
        IStmt example7 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new HeapAllocationStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new PrintStmt(new HeapReadingExpression(new VarExp("v"))),
                                new CompStmt(new HeapWritingStmt("v", new ValueExp(new IntValue(30))),
                                        new PrintStmt(new ArithExp('+',new HeapReadingExpression(new VarExp("v")), new ValueExp(new IntValue(5))))))));
        IStmt example8 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new HeapAllocationStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(new HeapAllocationStmt("a", new VarExp("v")),
                                        new CompStmt(new HeapAllocationStmt("v", new ValueExp(new IntValue(30))),
                                                new PrintStmt(new HeapReadingExpression(new HeapReadingExpression(new VarExp("a")))))))));
        IStmt example9 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(4))),
                        new CompStmt(new WhileStmt(new RelExp(">", new VarExp("v"), new ValueExp(new IntValue(0  ))),
                                new CompStmt(new PrintStmt(new VarExp("v")), new AssignStmt("v", new ArithExp('-',new VarExp("v"),
                                        new ValueExp(new IntValue(1)))))),
                                new PrintStmt(new VarExp("v")))));

        IStmt example10 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new VarDeclStmt("a", new RefType(new IntType())),
                        new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(10))),
                                new CompStmt(new HeapAllocationStmt("a", new ValueExp(new IntValue(22))),
                                        new CompStmt(new ForkStmt(new CompStmt(new HeapWritingStmt("a", new ValueExp(new IntValue(30))),
                                                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(32))),
                                                        new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new HeapReadingExpression(new VarExp("a"))))))),
                                                new CompStmt(new PrintStmt(new VarExp("v")),
                                                        new PrintStmt(new HeapReadingExpression(new VarExp("a")))))))));

        IStmt example11 = new CompStmt(new VarDeclStmt("a", new IntType()), new CompStmt(new VarDeclStmt("b",new StringType()),
                new CompStmt(new AssignStmt("a", new ArithExp('+',new ValueExp(new IntValue(2)),new ArithExp('*',
                        new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))), new CompStmt(new AssignStmt("b",
                        new ArithExp('+',new VarExp("a"), new ValueExp(new IntValue(1)))), new PrintStmt(new VarExp("b"))))));

        IStmt example12 = new CompStmt(new VarDeclStmt("varf",new StringType()), new CompStmt(new AssignStmt("varf",
                new ValueExp(new StringValue("test.in"))), new CompStmt(new OpenRFileStmt(new VarExp("varf")),
                new CompStmt(new VarDeclStmt("varc",new BoolType()), new CompStmt(new ReadFileStmt(new VarExp("varf"), "varc"),
                        new CompStmt(new PrintStmt(new VarExp("varc")), new CompStmt(new ReadFileStmt(new VarExp("varf"), "varc"),
                                new CompStmt(new PrintStmt(new VarExp("varc")),new CloseRFileStmt(new VarExp("varf"))))))))));


        PrgState program1 = new PrgState(new ExecutionStack<IStmt>(), new Dictionary<String, Value>(), new OutputList<Value>(), new Dictionary<StringValue, BufferedReader>(), new Heap<Integer, Value>(), example1);
        PrgState program2 = new PrgState(new ExecutionStack<IStmt>(), new Dictionary<String, Value>(), new OutputList<Value>(), new Dictionary<StringValue, BufferedReader>(), new Heap<Integer, Value>(), example2);
        PrgState program3 = new PrgState(new ExecutionStack<IStmt>(), new Dictionary<String, Value>(), new OutputList<Value>(), new Dictionary<StringValue, BufferedReader>(), new Heap<Integer, Value>(), example3);
        PrgState program4 = new PrgState(new ExecutionStack<IStmt>(), new Dictionary<String, Value>(), new OutputList<Value>(), new Dictionary<StringValue, BufferedReader>(), new Heap<Integer, Value>(), example4);
        PrgState program5 = new PrgState(new ExecutionStack<IStmt>(), new Dictionary<String, Value>(), new OutputList<Value>(), new Dictionary<StringValue, BufferedReader>(), new Heap<Integer, Value>(), example5);
        PrgState program6 = new PrgState(new ExecutionStack<IStmt>(), new Dictionary<String, Value>(), new OutputList<Value>(), new Dictionary<StringValue, BufferedReader>(), new Heap<Integer, Value>(), example6);
        PrgState program7 = new PrgState(new ExecutionStack<IStmt>(), new Dictionary<String, Value>(), new OutputList<Value>(), new Dictionary<StringValue, BufferedReader>(), new Heap<Integer, Value>(), example7);
        PrgState program8 = new PrgState(new ExecutionStack<IStmt>(), new Dictionary<String, Value>(), new OutputList<Value>(), new Dictionary<StringValue, BufferedReader>(), new Heap<Integer, Value>(), example8);
        PrgState program9 = new PrgState(new ExecutionStack<IStmt>(), new Dictionary<String, Value>(), new OutputList<Value>(), new Dictionary<StringValue, BufferedReader>(), new Heap<Integer, Value>(), example9);
        PrgState program10 = new PrgState(new ExecutionStack<IStmt>(), new Dictionary<String, Value>(), new OutputList<Value>(), new Dictionary<StringValue, BufferedReader>(), new Heap<Integer, Value>(), example10);
        PrgState program11 = new PrgState(new ExecutionStack<IStmt>(), new Dictionary<String, Value>(), new OutputList<Value>(), new Dictionary<StringValue, BufferedReader>(), new Heap<Integer, Value>(), example11);
        PrgState program12 = new PrgState(new ExecutionStack<IStmt>(), new Dictionary<String, Value>(), new OutputList<Value>(), new Dictionary<StringValue, BufferedReader>(), new Heap<Integer, Value>(), example12);

        IRepository repo1 = new Repository(program1, "logEx1.txt");
        IRepository repo2 = new Repository(program2, "logEx2.txt");
        IRepository repo3 = new Repository(program3, "logEx3.txt");
        IRepository repo4 = new Repository(program4, "logEx4.txt");
        IRepository repo5 = new Repository(program5, "logEx5.txt");
        IRepository repo6 = new Repository(program6, "logEx6.txt");
        IRepository repo7 = new Repository(program7, "logEx7.txt");
        IRepository repo8 = new Repository(program8, "logEx8.txt");
        IRepository repo9 = new Repository(program9, "logEx9.txt");
        IRepository repo10 = new Repository(program10, "logEx10.txt");
        IRepository repo11 = new Repository(program11, "logEx11.txt");
        IRepository repo12 = new Repository(program11, "logEx12.txt");

        Controller ctrl1 = new Controller(repo1, display_program_state);
        Controller ctrl2 = new Controller(repo2, display_program_state);
        Controller ctrl3 = new Controller(repo3, display_program_state);
        Controller ctrl4 = new Controller(repo4, display_program_state);
        Controller ctrl5 = new Controller(repo5, display_program_state);
        Controller ctrl6 = new Controller(repo6, display_program_state);
        Controller ctrl7 = new Controller(repo7, display_program_state);
        Controller ctrl8 = new Controller(repo8, display_program_state);
        Controller ctrl9 = new Controller(repo9, display_program_state);
        Controller ctrl10 = new Controller(repo10, display_program_state);
        Controller ctrl11 = new Controller(repo11, display_program_state);
        Controller ctrl12 = new Controller(repo12, display_program_state);

        TextMenu textMenu = new TextMenu();
        textMenu.addCommand(new ExitCommand("0", "exit"));
        try {
            example1.typeCheck(new Dictionary<String, Type>());
            textMenu.addCommand(new RunExampleCommand("1", "int v; v=2; Print(v);", ctrl1));
        } catch (MyException me) {
            System.out.println("Error example 1:\n\t"+me.getMessage());
        }
        try {
            example2.typeCheck(new Dictionary<String, Type>());
            textMenu.addCommand(new RunExampleCommand("2", "int a; int b; a=2+3*5; b=a+1; Print(b);", ctrl2));
        } catch (MyException me) {
            System.out.println("Error example 2:\n\t"+me.getMessage());
        }
        try {
            example3.typeCheck(new Dictionary<String, Type>());
            textMenu.addCommand(new RunExampleCommand("3", "bool a; int v; a=true; (If a Then v=2 Else v=3); Print(v)", ctrl3));
        } catch (MyException me) {
            System.out.println("Error example 3:\n\t"+me.getMessage());
        }
        try {
            example4.typeCheck(new Dictionary<String, Type>());
            textMenu.addCommand(new RunExampleCommand("4", "string varf; varf=\"test.in\"; openRFile(varf); int varc; readFile(varf, varc);print(varc); readFile(varf,varc);print(varc); closeRFile(varf);", ctrl4));
        } catch (MyException me) {
            System.out.println("Error example 4:\n\t"+me.getMessage());
        }
        try {
            example5.typeCheck(new Dictionary<String, Type>());
            textMenu.addCommand(new RunExampleCommand("5", "Ref int v;new(v,20);Ref Ref int a; new(a,v);print(v);print(a);", ctrl5));
        } catch (MyException me) {
            System.out.println("Error example 5:\n\t"+me.getMessage());
        }
        try {
            example6.typeCheck(new Dictionary<String, Type>());
            textMenu.addCommand(new RunExampleCommand("6", "Ref int v;new(v,20);Ref Ref int a; new(a,v);print(rH(v));print(rH(rH(a))+5);", ctrl6));
        } catch (MyException me) {
            System.out.println("Error example 6:\n\t"+me.getMessage());
        }
        try {
            example7.typeCheck(new Dictionary<String, Type>());
            textMenu.addCommand(new RunExampleCommand("7", "Ref int v;new(v,20);print(rH(v)); wH(v,30);print(rH(v)+5);", ctrl7));
        } catch (MyException me) {
            System.out.println("Error example 7:\n\t"+me.getMessage());
        }
        try {
            example8.typeCheck(new Dictionary<String, Type>());
            textMenu.addCommand(new RunExampleCommand("8", "Ref int v;new(v,20);Ref (Ref int a); new(a,v); new(v,30);print(rH(rH(a)));", ctrl8));
        } catch (MyException me) {
            System.out.println("Error example 8:\n\t"+me.getMessage());
        }
        try {
            example9.typeCheck(new Dictionary<String, Type>());
            textMenu.addCommand(new RunExampleCommand("9", "int v; v=4; (while (v>0) print(v);v=v-1);print(v);", ctrl9));
        } catch (MyException me) {
            System.out.println("Error example 10:\n\t"+me.getMessage());
        }
        try {
            example10.typeCheck(new Dictionary<String, Type>());
            textMenu.addCommand(new RunExampleCommand("10", "int v; Ref int a; v=10;new(a,22); fork(wH(a,30); v=32; print(v);print(rH(a))); print(v); print(rH(a));", ctrl10));
        } catch (MyException me) {
            System.out.println("Error example 10:\n\t"+me.getMessage());
        }
        try {
            example11.typeCheck(new Dictionary<String, Type>());
            textMenu.addCommand(new RunExampleCommand("11", "int a; string b; a=2+3*5; b=a+1; Print(b);", ctrl11));
        } catch (MyException me) {
            System.out.println("Error example 11:\n\t"+me.getMessage());
        }
        try {
            example12.typeCheck(new Dictionary<String, Type>());
            textMenu.addCommand(new RunExampleCommand("12", "string varf; varf=\"test.in\"; openRFile(varf); bool varc; readFile(varf,varc);print(varc); readFile(varf,varc);print(varc); closeRFile(varf);", ctrl12));
        } catch (MyException me) {
            System.out.println("Error example 12:\n\t"+me.getMessage());
        }

        textMenu.show();
    }
}
