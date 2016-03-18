package ast;

import java.util.ArrayList;
import environment.Environment;
import emitter.Emitter;

/**
 * Organizes global variables, procedure declarations and becomes the root of the abstract 
 * syntax tree of the Pascal Parser.
 * @author Jackelyn Shen
 * @version January 8, 2015
 */
public class Program extends Statement
{
	private ArrayList<String> variables;
	private ArrayList<ProcedureDeclaration> procedures;
	private Statement statement;
	
	/**
	 * Creates a Program object and sets the program statement, list of procedures,
	 * and global variables.
	 * @param p  the list of ProcedureDeclarations to be set to an instance variable
	 * @param s  the Statement to be set to an instance variable
	 */
	public Program(ArrayList<String> v, ArrayList<ProcedureDeclaration> p, Statement s)
	{
		variables=v;
		procedures=p;
		statement=s;
	}
	
    /**
     * Executes both the ArrayList of ProcedureDeclarations and the Statement.
     * @param env  the environment in which the HashMaps are stored
     */
    public void exec(Environment env)
    {
    	for(int i=0; i<procedures.size(); i++)
        {
            procedures.get(i).exec(env);
        }
        statement.exec(env);
    }
    
    /**
     * Outputs initial MIPS instructions for the program, including termination and
     * .data lines, and calls the compile method for the program's statement.
     * @param outputFileName  the String name of the output file
     */
    public void compile(String outputFileName)
    {
    	Emitter e=new Emitter(outputFileName);
    	e.emit(".text");
    	e.emit(".globl main");
    	e.emit("main:");
    	statement.compile(e);
    	e.emit("li $v0 10	#halt");
    	e.emit("syscall 	#halt");
    	for(int i=0; i<procedures.size(); i++)
    	{
    		procedures.get(i).compile(e);
    	}
    	e.emit(".data");
    	for(int i=0; i<variables.size(); i++)
    	{
    		e.emit("var"+variables.get(i)+": ");
    		e.emit(".word 0");
    	}
		e.emit("line:");
		e.emit(".asciiz \"\\n\"");
    	e.close();
    }
}
