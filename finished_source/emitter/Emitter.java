package emitter;

import java.io.*;
import ast.ProcedureDeclaration;
import java.util.ArrayList;

/**
 * Converts Pascal programs delineated in an abstract syntax tree to MIPS instructions
 * that are outputted to a file.
 * @author Jackelyn Shen
 * @version January 8, 2015
 */
public class Emitter
{
	private PrintWriter out;
	private int labelID;
	private ProcedureDeclaration procedureContext;
	private int excessStackHeight;

	/**
	 * Sets the initial label ID to 0, procedureContext to 0, excessStackHeight to 0,
	 * and creates an Emitter for writing to a new file with a given name.
	 * @param outputFileName  the name of the output file
	 */
	public Emitter(String outputFileName)
	{
		labelID=0;
		procedureContext=null;
		excessStackHeight=0;
		try
		{
			out = new PrintWriter(new FileWriter(outputFileName), true);
		}
		catch(IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	/**
	 * Prints one line of code to file (with non-labels indented)
	 * @param code  the code to be printed out
	 */
	public void emit(String code)
	{
		if (!code.endsWith(":"))
			code = "\t" + code;
		out.println(code);
	}

	/**
	 * Closes the file, should be called after all calls to emit.
	 */
	public void close()
	{
		out.close();
	}
	
	/**
	 * Outputs MIPS instructions that push 4 bytes onto the stack register and
	 * sets mem[$sp]=reg.
	 * @param reg the String register
	 */
	public void emitPush(String reg)
	{
		emit("subu $sp $sp 4");
		emit("sw "+reg+" ($sp)");
		excessStackHeight++;
	}
	
	/**
	 * Outputs MIPS instructions that set reg=mem[$sp] and pops 4 bytes off 
	 * of the stack register.
	 * @param reg the String register
	 */
	public void emitPop(String reg)
	{
		emit("lw "+reg+" ($sp)");
		emit("addu $sp $sp 4");
		excessStackHeight--;
	}
	
	/**
	 * Returns the next sequentially available integer for a label ID.
	 * @return  an integer for the next label ID
	 */
	public int nextLabelID()
	{
		labelID=labelID+1;
		return labelID;
	}
	
	/**
	 * Sets the procedure context of the Emitter and the excessStackHeight to 0.
	 * @param proc  the Procedure Declaration currently in process
	 */
	public void setProcedureContext(ProcedureDeclaration proc)
	{
		procedureContext=proc;
		excessStackHeight=0;
	}
	
	/**
	 * Sets the procedure context of the Emitter to null.
	 */
	public void clearProcedureContext()
	{
		procedureContext=null;
	}
	
	/**
	 * Determines whether a variable is a local or a global variable.
	 * @param varName  the String identifier of the variable
	 */
	public boolean isLocalVariable(String varName)
	{
		if(procedureContext!=null)
		{
			if(varName.equals(procedureContext.getName()))
			{
				return true;
			}
			ArrayList<String> parameters=procedureContext.getParameters();
			for(int i=0; i<parameters.size(); i++)
			{
				if(parameters.get(i).equals(varName))
				{
					return true;
				}
			}
			ArrayList<String> localVariables=procedureContext.getVariables();
			for(int i=0; i<localVariables.size(); i++)
			{
				if(localVariables.get(i).equals(varName))
				{
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Gets the offset value of the variable inside of the stack.
	 * @param localVarName  the String identifier of the local variable
	 */
	public int getOffset(String localVarName)
	{
		if(localVarName.equals(procedureContext.getName()))
		{
			return 0;
		}
		ArrayList<String> parameters=procedureContext.getParameters();
		ArrayList<String> localVariables=procedureContext.getVariables();
		for(int i=0; i<parameters.size(); i++)
		{
			if(parameters.get(i).equals(localVarName))
			{
				return parameters.size()-i+excessStackHeight+localVariables.size();
			}
		}
		for(int i=0; i<localVariables.size(); i++)
		{
			if(localVariables.get(i).equals(localVarName))
			{
				return localVariables.size()-i+excessStackHeight;
			}
		}
		return -1;
	}
}