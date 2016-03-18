package ast;

import environment.Environment;
import emitter.Emitter;

/**
 * A If object contains and executes Pascal If statements.
 * @author Jackelyn Shen
 * @version January 8, 2015
 */
public class If extends Statement
{
	private Condition ifstatement;
	private Statement thenstatement;
	
	/**
	 * Creates a If object which takes in a Expression representing the If
	 * condition and a Statement representing the Then execution.
	 * @param ifs  the if Expression
	 * @param thens  the then Statement
	 */
	public If(Condition ifs, Statement thens)
	{
		ifstatement=ifs;
		thenstatement=thens;
	}
	
	/**
	 * Executes the Then statement if the If condition is valid.
	 * @param env  the environment in which the HashMap is stored.
	 */
	public void exec(Environment env)
	{
		if(ifstatement.eval(env)==1)
		{
			thenstatement.exec(env);
		}
	}
	
	/**
	 * Calls the compile method for the if expression and the then statement
	 * and prints out the next label ID for the if statement.
	 * @param e  the Emitter that outputs MIPS instructions
	 */
	public void compile(Emitter e)
	{
		int labelNum=e.nextLabelID();
		String label="endif"+labelNum;
		ifstatement.compile(e, label);
		thenstatement.compile(e);
		e.emit(label+": ");
	}
}
