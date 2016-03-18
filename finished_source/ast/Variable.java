package ast;

import environment.Environment;
import emitter.Emitter;

/**
 * A Variable object contains information for identifiers.
 * @author Jackelyn Shen
 * @version January 8, 2015
 */
public class Variable extends Expression
{
	private String name;
	
	/**
	 * Creates a Variable object and sets the String identifier to an instance variable.
	 * @param n  the name of the variable
	 */
	public Variable(String n)
	{
		name=n;
	}
	
	/**
	 * Returns the integer value of the variable in the HashMap.
	 * @param env  the environment in which the HashMap is stored
	 * @return the integer value of the variable
	 */
	public int eval(Environment env)
	{
		return env.getVariable(name);
	}
	
	/**
	 * Depending on whether the variable is not local, loads the value of the variable
	 * into $v0. Otherwise, gets the value of the variable from the stack and by 
	 * calculating its offset.
	 * @param e  the Emitter that outputs MIPS instructions
	 */
	public void compile(Emitter e)
	{
		if(!e.isLocalVariable(name))
		{	
			e.emit("la $t0 var"+name);
			e.emit("lw $v0 ($t0)");
		}
		else
		{
			int offset=4*e.getOffset(name);
			e.emit("lw $v0 "+offset+"($sp)");
		}
	}
}
