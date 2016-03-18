package ast;

import emitter.Emitter;
import environment.Environment;

/**
 * A BinOp object organizes and evaluates operations.
 * @author Jackelyn Shen
 * @version January 8, 2015
 */
public class BinOp extends Expression
{
	private Expression lefthand;
	private String operator;
	private Expression righthand;
	
	/**
	 * Creates a BinOp object and takes in two expressions and their determining operator.
	 * @param left  the leftmost Expression
	 * @param op  the operator
	 * @param right  the rightmost Expression
	 */
	public BinOp(Expression left, String op, Expression right)
	{
		lefthand=left;
		operator=op;
		righthand=right;
	}
	
	/**
	 * Evaluates the operation between the two expressions.
	 * Operators include +, -, *, /.
	 * @param env  the environment in which the HashMap is stored
	 * @return  an integer value representing the evaluated operation
	 */
	public int eval(Environment env)
	{
		if(operator.equals("+"))
		{
			return lefthand.eval(env)+righthand.eval(env);
		}
		else if(operator.equals("-"))
		{
			return lefthand.eval(env)-righthand.eval(env);
		}
		else if(operator.equals("*"))
		{
			return lefthand.eval(env)*righthand.eval(env);
		}
		else
		{
			return lefthand.eval(env)/righthand.eval(env);
		}
	}
	
	/**
	 * Calls the compile method for the lefthand expression and pushes it onto a stack.
	 * Then calls the compile method for the righthand expression and pops
	 * the lefthand expression off the stack into a temporary register.
	 * Outputs MIPS instructions for evaluating the operator and expressions.
	 * @param e  the Emitter that outputs MIPS instructions
	 */
	public void compile(Emitter e) 
	{ 
		lefthand.compile(e);
		e.emitPush("$v0");
		righthand.compile(e);
		e.emitPop("$t0");
		if(operator.equals("+"))
		{
			e.emit("addu $v0 $t0 $v0");
		}
		else if(operator.equals("-"))
		{
			e.emit("subu $v0 $t0 $v0");
		}
		else if(operator.equals("*"))
		{
			e.emit("mult $v0 $t0");
			e.emit("mflo $v0");
		}
		else if(operator.equals("/"))
		{
			e.emit("div $t0 $v0");
			e.emit("mflo $v0");
		}
	} 
}
