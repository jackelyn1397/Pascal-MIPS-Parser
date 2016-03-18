package ast;

import emitter.Emitter;
import environment.Environment;

/**
 * A Number object contains integer values.
 * @author Jackelyn Shen
 * @version January 8, 2015
 */
public class Number extends Expression
{
	private int number;
	
	/**
	 * Creates a Number object and sets the integer value to an instance variable.
	 * @param num  the integer value to be set
	 */
	public Number(int num)
	{
		number=num;
	}
	
	/**
	 * Returns the integer value.
	 * @param env  the environment in which the HashMap is stored
	 * @return the instance variable number
	 */
	public int eval(Environment env)
	{
		return number;
	}
	
	/**
	 * Uses the Emitter to output MIPS instructions that set $v0 to number
	 * @param e  the Emitter that outputs MIPS instructions
	 */
	public void compile(Emitter e) 
	{ 
		e.emit("li $v0 "+number);
	} 
}
