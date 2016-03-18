package ast;

import environment.Environment;
import emitter.Emitter;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Executes the procedure based on its declaration in the environment.
 * @author Jackelyn Shen
 * @version January 8, 2015
 */
public class ProcedureCall extends Expression
{
	private String procedureName;
	private ArrayList<Expression> parameters;
	
	/**
	 * Creates a ProcedureCall object and initializes the procedure name and parameters.
	 * @param name  the String name to be set to an instance variable
	 * @param parameters  an ArrayList of Expressions to be set to an instance variable
	 */
	public ProcedureCall(String name, ArrayList<Expression> p)
	{
		procedureName=name;
		parameters=p;
	}
	
	/**
	 * Creates a local environment to store local variables, looks up the 
	 * corresponding Statement of the procedure in the procedure
	 * HashMap in the parent environment, executes that Statement, and returns 0.
	 * Also initializes local procedure variables in the environment.
	 * @param parentEnv  the parent environment in which global variables and procedures
	 * are stored
	 * @return the value of the variable procedureName in env
	 */
	public int eval(Environment parentEnv)
	{
		HashMap<String,Integer> vMap=new HashMap<String,Integer>();
		HashMap<String,ProcedureDeclaration> pMap=new HashMap<String,ProcedureDeclaration>();
		Environment env=new Environment(vMap, pMap, parentEnv);
		ProcedureDeclaration procedure=parentEnv.getProcedure(procedureName);
		env.declareVariable(procedureName, 0);
		for(int i=0; i<parameters.size(); i++)
		{
			env.declareVariable(procedure.getParameter(i), parameters.get(i).eval(env));
		}
		ArrayList<String> localVariables=procedure.getVariables();
        for(int i=0; i<localVariables.size(); i++)
        {
            env.declareVariable(localVariables.get(i),0);
        }
		procedure.getStatement().exec(env);
		return env.getVariable(procedureName);
	}
	
	/**
	 * Pushes the return address and any compiled parameters onto the stack, outputs a MIPS
	 * instruction that jumps to the procedure declaration, then pops everything off 
	 * the stack.
	 * @param e  the Emitter that outputs MIPS instructions
	 */
	public void compile(Emitter e)
	{
		e.emitPush("$ra");
		for(int i=0; i<parameters.size(); i++)
		{
			parameters.get(i).compile(e);
			e.emitPush("$v0");
		}
		e.emit("jal proc"+procedureName);
		for(int i=0; i<parameters.size(); i++)
		{
			e.emitPop("$t0");
		}
		e.emitPop("$ra");
	}
}
