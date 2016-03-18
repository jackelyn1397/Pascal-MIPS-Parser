package ast;

import environment.Environment;
import emitter.Emitter;

/**
 * An Assignment object organizes and evaluates assignments.
 * @author Jackelyn Shen
 * @version January 8, 2015
 */
public class Assignment extends Statement
{
	private String var;
	private Expression exp;
	
	/**
	 * Creates an Assignment object and takes in a String identifier and the Expression
	 * to be assigned.
	 * @param v  the String identifier
	 * @param e  the Expression to be assigned to the variable
	 */
	public Assignment(String v, Expression e)
	{
		var=v;
		exp=e;
	}
	
	/**
	 * Adds the variable and its Expression to the HashMap.
	 * @param env  the environment in which the HashMap is stored
	 */
	public void exec(Environment env)
	{
		env.setVariable(var, exp.eval(env));
	}
	
	/**
	 * Depending on whether the variable is not local, outputs MIPS instructions 
	 * that load the value associated with the variable, calls the expression's 
	 * compile method, and sets the new value to the variable's value. Otherwise, gets
	 * the variable value from the stack by getting its offset.
	 * @param e  the Emitter that outputs MIPS instructions
	 */
	public void compile(Emitter e)
	{
		if(!e.isLocalVariable(var))
		{
			e.emit("la $t0 var"+var);
			e.emit("lw $v0 ($t0)");
			exp.compile(e);
			e.emit("la $t0 var"+var);
			e.emit("sw $v0 ($t0)");
		}
		else
		{
			exp.compile(e);
			int offset=4*e.getOffset(var);
			e.emit("sw $v0 "+offset+"($sp)");
			
		}
	}
}
