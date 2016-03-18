package ast;

import emitter.Emitter;
import environment.Environment;

/**
 * A Writeln object contains and executes Pascal Writeln statements.
 * @author Jackelyn Shen
 * @version January 8, 2014
 */
public class Writeln extends Statement
{
	private Expression exp;
	
	/**
	 * Creates a Writeln object and sets the expression inside the Writeln to
	 * an instance variable.
	 * @param e  the expression inside of the Writeln
	 */
	public Writeln(Expression e)
	{
		exp=e;
	}
	
	/**
	 * Prints out the expression inside the Writeln.
	 * @param env  the environment in which the HashMap is stored
	 */
	public void exec(Environment env)
	{
		System.out.println(exp.eval(env));
	}
	
	/**
	 * Calls exp's compile method and uses the Emitter to output MIPS instructions that
	 * print out the Expression
	 * @param e  the Emitter that outputs MIPS instructions
	 */
	public void compile(Emitter e) 
	{ 
		exp.compile(e);
		e.emit("move $a0 $v0"); 
		e.emit("li $v0 1");
		e.emit("syscall");
		e.emit("la $a0, line");
		e.emit("li $v0, 4");
		e.emit("syscall");
	} 
}
