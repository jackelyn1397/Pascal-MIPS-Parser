package ast;

import environment.Environment;
import emitter.Emitter;

/**
 * An abstract Statement object creates the foundation for specific Statement
 * subclasses such as Writeln, Assignment, and Block.
 * @author Jackelyn Shen
 * @version January 8, 2015
 */
public abstract class Statement
{
	/**
	 * Creates an execution method for subclasses to modify.
	 * @param env  the environment in which the HashMap is stored.
	 */
	public void exec(Environment env)
	{
		
	}
	
	/**
	 * Creates a compile method that subclasses to override.
	 * @param e  the Emitter that outputs MIPS instructions
	 */
	public void compile(Emitter e) 
	{ 
		throw new RuntimeException("Implement me!!!!!"); 
	} 
}
