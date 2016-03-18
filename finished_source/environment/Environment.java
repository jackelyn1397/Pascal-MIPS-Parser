package environment;

import java.util.HashMap;
import ast.ProcedureDeclaration;

/**
 * An Environment object encompasses the variable HashMap that stores and links 
 * certain integers to their String variables and the procedure HashMap that stores
 * and links certain Statements to their String identifiers representing the procedure.
 * Possibly links to a parent environment to distinguish variable scope.
 * @author Jackelyn Shen
 * @version January 8, 2015
 */

public class Environment
{
	private HashMap<String,Integer> variableMap;
	private HashMap<String,ProcedureDeclaration> procedureMap;
	private Environment parentEnv;
	
	/**
	 * Creates an Environment object, sets the parent environment, and
	 * initializes the variable and procedure HashMaps.
	 * @param vM  the variable HashMap to be set to an instance variable
	 * @oaram pM  the procedure HashMap to be set to an instance variable
	 */
	public Environment(HashMap<String,Integer> vM, HashMap<String,ProcedureDeclaration> pM,
			Environment e)
	{
		variableMap=vM;
		procedureMap=pM;
		parentEnv=e;
	}
	
	/**
	 * Adds a variable and its value to the parent environment's
	 * variable HashMap. If the variable is in this environment's HashMap,
	 * sets the variable in this environment's HashMap.
	 * @param variable  the variable added to the HashMap
	 * @param value  the value of the variable added to the variable HashMap
	 */
	public void setVariable(String variable, int value)
	{
		Integer current=variableMap.get(variable);
		if(current==null && parentEnv!=null)
		{
			parentEnv.declareVariable(variable, value);
		}
		else
		{
			variableMap.put(variable,value);
		}
	}
	
	/**
	 * Gets the integer value of a specific String in the variable HashMap.
	 * If the variable is not located in this environment's HashMap, searches
	 * the parent environment's HashMap for the variable.
	 * @param variable  the String whose value is returned
	 * @return the integer value of the variable
	 */
	public int getVariable(String variable)
	{
		Integer value=variableMap.get(variable);
		if(value==null && parentEnv!=null)
		{
			return parentEnv.getVariable(variable);
		}
		else
		{
			return value;
		}
	}
	
	/**
	 * Adds a procedure and its value to the procedure HashMap.
	 * @param p  the String identifier of the procedure
	 * @param e  the Statement value of the procedure
	 */
	public void setProcedure(String p, ProcedureDeclaration e)
	{
		if(parentEnv==null)
		{
			procedureMap.put(p, e);
		}
		else
		{
			parentEnv.setProcedure(p, e);
		}
	}
	
	/**
	 * Gets the Statement value of a specific procedure in the parent
	 * environment's procedure HashMap. If there is no parent environment,
	 * gets the value of the procedure from this environment's HashMap.
	 * @param procedure  the procedure whose value is returned
	 * @return the Statement value of the procedure
	 */
	public ProcedureDeclaration getProcedure(String procedure)
	{
		if(parentEnv==null)
		{
			return procedureMap.get(procedure);
		}
		else
		{
			return parentEnv.getProcedure(procedure);
		}
	}
	
	/**
	 * Adds a variable and its value to the variable HashMap.
	 * @param variable  the variable added to the HashMap
	 * @param value  the value of the variable added to the variable HashMap
	 */
	public void declareVariable(String variable, int value)
	{
		variableMap.put(variable,value);
	}
}
