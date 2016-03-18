package ast;

import environment.Environment;
import emitter.Emitter;
import java.util.ArrayList;

/**
 * Adds a procedure declaration, including the procedure identifier, its statement, 
 * parameters, and local variables, to the environment.
 * @author Jackelyn Shen
 * @version January 8, 2015
 *
 */
public class ProcedureDeclaration extends Statement
{
	private String procedureName;
	private Statement procedureStatement;
	private ArrayList<String> parameters;
	private ArrayList<String> localVariables;
	
	/**
	 * Creates a ProcedureDeclaration object and initializes the procedure's name
	 * and statement.
	 * @param name  the String name to be set to an instance variable
	 * @param statement  the Statement to be set to an instance variable
	 */
	public ProcedureDeclaration(String name, Statement statement, ArrayList<String> p,
			ArrayList<String> v)
	{
		procedureName=name;
		procedureStatement=statement;
		parameters=p;
		localVariables=v;
	}
	
	/**
	 * Returns the procedure's String parameter at the specified index.
	 * @param index  the index value of the parameter ArrayList
	 * @return the String parameter at index
	 */
	public String getParameter(int index)
	{
		return parameters.get(index);
	}
	
	/**
	 * Returns the procedure's String identifier.
	 * @return the the procedure name
	 */
	public String getName()
	{
		return procedureName;
	}
	
	/**
	 * Returns the procedure's Statement.
	 * @return the Statement value of the procedure
	 */
	public Statement getStatement()
	{
		return procedureStatement;
	}
	
	/**
	 * Returns an ArrayList containing all the procedure's parameters.
	 * @return the procedure's parameters
	 */
	public ArrayList<String> getParameters()
	{
		return parameters;
	}
	
	/**
	 * Returns an ArrayList containing all the procedure's local variables.
	 * @return the procedure's local variables
	 */
	public ArrayList<String> getVariables()
	{
		return localVariables;
	}
	
	/**
	 * Adds the String procedure identifier and this ProcedureDeclaration to the 
	 * procedure HashMap.
	 * @param env  the environment in which the HashMap is stored
	 */
	public void exec(Environment env)
	{
		env.setProcedure(procedureName, this);
	}
	
	/**
	 * Pushes all local variables onto the stack, sets the procedure context, compiles
	 * the statement, then removes the variables from the stack.
	 * @param e  the Emitter that outputs MIPS instructions
	 */
	public void compile(Emitter e)
	{
		e.emit("proc"+procedureName+":");
		e.emit("li $v0 0");
		e.emitPush("$v0");
		for(int i=0; i<localVariables.size(); i++)
		{
			e.emit("li $v0 0");
			e.emitPush("$v0");
		}
		e.setProcedureContext(this);
		procedureStatement.compile(e);
		for(int i=0; i<localVariables.size(); i++)
		{
			e.emitPop("$t0");
		}
		e.emitPop("$v0");
		e.emit("jr $ra");
		e.clearProcedureContext();
	}
}
