package ast;

import environment.Environment;
import emitter.Emitter;

/**
 * An abstract Expression object creates the foundation for specific Expression
 * subclasses such as Number, Variable, and BinOp.
 * @author Jackelyn Shen
 * @version January 8, 2015
 */
public abstract class Expression
{
	/**
	 * Creates an evaluation method for subclasses to modify.
	 * @param env  the environment in which the HashMaps are stored.
	 * @return 0, a default number
	 */
	public int eval(Environment env)
	{
		return 0;
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
