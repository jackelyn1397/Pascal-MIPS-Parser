package ast;

import environment.Environment;
import emitter.Emitter;

/**
 * A While object contains and executes Pascal While statements.
 * @author Jackelyn Shen
 * @version January 8, 2015
 */
public class While extends Statement
{
	private Condition whilestatement;
	private Statement dostatement;
	
	/**
	 * Creates a While object which takes in a Expression representing the While
	 * condition and a Statement representing the Do execution.
	 * @param whiles  the while Expression
	 * @param dos  the do Statement
	 */
	public While(Condition whiles, Statement dos)
	{
		whilestatement=whiles;
		dostatement=dos;
	}
	
	/**
	 * Executes the Do statement while the While condition is valid.
	 * @param env  the environment in which the HashMap is stored.
	 */
	public void exec(Environment env)
	{
		while(whilestatement.eval(env)==1)
		{
			dostatement.exec(env);
		}
	}
	
	/**
	 * Outputs MIPS instructions setting up the loop and calls the compile methods
	 * for both the while expression and do statements.
	 * @param e  the Emitter that outputs MIPS instructions
	 */
	public void compile(Emitter e)
	{
		e.emit("looptest: ");
		whilestatement.compile(e, "endloop");
		dostatement.compile(e);
		e.emit("j looptest");
		e.emit("endloop: ");
	}
}
