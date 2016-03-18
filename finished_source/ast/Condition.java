package ast;

import environment.Environment;
import emitter.Emitter;

/**
 * A Condition object organizes and determines the conditions for Pascal 
 * If and While statements.
 * @author Jackelyn Shen
 * @version January 8, 2015
 */
public class Condition extends Expression
{
	private Expression lefthand;
	private String relop;
	private Expression righthand;
	
	/**
	 * Creates a Condition object and takes in two expressions and their determining relop.
	 * @param left  the leftmost Expression
	 * @param r  the relop
	 * @param right  the rightmost Expression
	 */
	public Condition(Expression left, String r, Expression right)
	{
		lefthand=left;
		relop=r;
		righthand=right;
	}
	
	/**
	 * Evaluates whether or not the condition is valid based on the relop between
	 * two expressions.
	 * Relops include =, <>, <, >, <=, >=.
	 * @param env  the environment in which the HashMap is stored.
	 * @return 1 if the condition is valid; otherwise 0
	 */
	public int eval(Environment env)
	{
		if(relop.equals("="))
		{
			if(lefthand.eval(env)==righthand.eval(env))
			{
				return 1;
			}
			return 0;
		}
		else if(relop.equals("<>"))
		{
			if(lefthand.eval(env)!=righthand.eval(env))
			{
				return 1;
			}
			return 0;
		}
		else if(relop.equals("<"))
		{
			if(lefthand.eval(env)<righthand.eval(env))
			{
				return 1;
			}
			return 0;
		}
		else if(relop.equals(">"))
		{
			if(lefthand.eval(env)>righthand.eval(env))
			{
				return 1;
			}
			return 0;
		}
		else if(relop.equals("<="))
		{
			if(lefthand.eval(env)<=righthand.eval(env))
			{
				return 1;
			}
			return 0;
		}
		else if(relop.equals(">="))
		{
			if(lefthand.eval(env)>=righthand.eval(env))
			{
				return 1;
			}
			return 0;
		}
		return 0;
	}
	
	/**
	 * Calls the compile method for the lefthand expression and pushes it onto a stack.
	 * Then calls the compile method for the righthand expression and pops
	 * the lefthand expression off the stack into a temporary register.
	 * Outputs MIPS instructions for evaluating the relop and expressions.
	 * @param e  the Emitter that outputs MIPS instructions
	 * @param label  the label name to jump to if the relop expression is true
	 */
	public void compile(Emitter e, String label)
	{
		lefthand.compile(e);
		e.emitPush("$v0");
		righthand.compile(e);
		e.emitPop("$t0");
		if(relop.equals("="))
		{
			e.emit("bne $t0, $v0, "+label);
		}
		else if(relop.equals("<>"))
		{
			e.emit("beq $t0, $v0, "+label);
		}
		else if(relop.equals("<"))
		{
			e.emit("bge $t0, $v0, "+label);
		}
		else if(relop.equals(">"))
		{
			e.emit("ble $t0, $v0, "+label);
		}
		else if(relop.equals("<="))
		{
			e.emit("bgt $t0, $v0, "+label);
		}
		else if(relop.equals(">="))
		{
			e.emit("blt $t0, $v0, "+label);
		}
	}
}
