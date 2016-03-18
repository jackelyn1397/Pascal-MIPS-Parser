package ast;

import java.util.ArrayList;
import emitter.Emitter;
import environment.*;

/**
 * A Block object contains and executes Pascal Block statements.
 * @author Jackelyn Shen
 * @version January 8, 2015
 */
public class Block extends Statement
{
	private ArrayList<Statement> stmts;
	
	/**
	 * Creates a Block object and sets an ArrayList of Statements to an instance variable.
	 * @param s  the ArrayList of Statements to be set
	 */
	public Block(ArrayList<Statement> s)
	{
		stmts=s;
	}
	
	/**
	 * Executes all Statements inside the ArrayList.
	 * @param env  the environment in which the HashMap is stored
	 */
	public void exec(Environment env)
	{
		for(int i=0; i<stmts.size(); i++)
		{
			stmts.get(i).exec(env);
		}
	}
	
	/**
	 * Calls the compile method for each object in stmts.
	 * @param e  the Emitter that outputs MIPS instructions
	 */
	public void compile(Emitter e)
	{
		for(int i=0; i<stmts.size(); i++)
		{
			stmts.get(i).compile(e);
		}
	}
}
