package origparser;

import scanner.*;
import java.io.*;
import java.util.HashMap;

/**
 * Creates a Parser object to parse Pascal expressions.
 * @author Jackelyn Shen
 * @version October 3, 2014
 */
public class Parser 
{
	Scanner scan;
	HashMap<String,Integer> map;
	String currentToken;
	
	/**
	 * Constructor for Parser object.
	 * Takes in a Scanner and stores it in an instance variable.
	 * Takes in a HashMap with String and Integer parameters to store identifier values.
	 * Call's the Scanner's next method and stores the returned String in another 
	 * instance variable that represents the current token.
	 * Catches a ScanErrorExpection if the next token cannot be scanned, then aborts
	 * the program.
	 * @param s  the Scanner to be set in an instance variable
	 * @param m  the HashMap to be set in an instance variable
	 */
	public Parser(Scanner s, HashMap<String,Integer> m)
	{
		scan=s;
		map=m;
		try
		{
			currentToken=scan.nextToken();
		}
		catch(ScanErrorException e)
		{
			System.out.println(e);
			System.exit(-1);
		}
	}
	
	/**
	 * Takes in a String representing the current token.
	 * If the expected token matches the current token, asks Scanner for the next token
	 * to store as the current token.
	 * Catches a ScanErrorExpection if the next token cannot be scanned, then aborts
	 * the program.
	 * @throws IllegalArgumentException if expected token does not match current token
	 * @param expected  the expected String for currentToken
	 */
	public void eat(String expected)
	{
		if(expected.equals(currentToken))
        {
            try
            {
            	currentToken=scan.nextToken();
            }
            catch(ScanErrorException e)
            {
            	System.out.println(e);
            	System.exit(-1);
            }
        }
        else
        {
            throw new IllegalArgumentException(
                        "Illegal character - expected "+expected+" and found "+currentToken);
        }
	}
	
	/**
	* If the current token is an integer, returns that integer.
	* precondition: current token is an integer
	* postcondition: number token has been eaten
	* @return the value of the parsed integer
	*/
	public int parseNumber()
	{
		int num=Integer.parseInt(currentToken);
		eat(currentToken);
		return num;
	}
	
	/**
	* Handles BEGIN/END blocks, prints out the evaluated expression contained 
	* in WRITELN, and sets identifier values.
	* Throws an IOException if the statement cannot be parsed (the current token
	* is neither WRITELN, BEGIN or an identifier)
	* stmt -> WRITELN ( exp ) ; | BEGIN whilebegin | id := exp ;
	* whilebegin -> END ; | stmt whilebegin
	* postcondition: all tokens associated with WRITELN or BEGIN have been eaten
	*/
	public void parseStatement() throws IOException
	{
		char first=currentToken.charAt(0);
		if(currentToken.equals("WRITELN"))
		{
			eat("WRITELN");
			eat("(");
			System.out.println(parseExp());
			eat(")");
			eat(";");
		}
		else if(currentToken.equals("BEGIN"))
		{
			eat("BEGIN");
			while(!currentToken.equals("END"))
			{
				parseStatement();
			}
			eat("END");
			eat(";");
		}
		else if(Scanner.isLetter(first))
		{
			String identifier=currentToken;
			eat(currentToken);
			eat(":=");
			map.put(identifier,parseExp());
			eat(";");
		}
		else
		{
			throw new IOException("Statement does not follow the grammar.");
		}
	}
	
	/**
	* Evaluates a factor inside of WRITELN according to the following grammar:
	* factor -> ( exp ) | - factor | num | id
	* precondition: current token is inside a WRITELN statement
	* postcondition: all tokens associated with the factor have been eaten
	* @return an evaluated integer
	*/
	public int parseFactor()
	{
		char first=currentToken.charAt(0);
	    if(currentToken.equals("("))
		{
			eat("(");
			int num=parseExp();
			eat(")");
			return num;
		}
		else if(currentToken.equals("-"))
		{
			eat("-");
			return -parseFactor();
		}
		else if(Scanner.isLetter(first))
		{
		    String identifier=currentToken;
		    eat(currentToken);
		    return map.get(identifier);
		}
		else
		{
			return parseNumber();
		}
	}
	
	/**
	 * Evaluates a term inside of WRITELN according to the following grammar:
	 * term -> factor whileterm
	 * whileterm -> * factor whileterm | / factor whileterm | empty string
	 * precondition: current token is inside a WRITELN statement
	 * postcondition: all tokens associated with the term have been eaten
	 * @return an evaluated integer
	 */
	public int parseTerm()
	{
		int term=parseFactor();
		while(currentToken.equals("*") || currentToken.equals("/"))
		{
			if(currentToken.equals("*"))
			{
				eat("*");
				term=term*parseFactor();
			}
			else
			{
				eat("/");
				term=term/parseFactor();
			}
		}
		return term;
	}
	
	/**
	 * Evaluates an expression inside of WRITELN according to the following grammar:
	 * exp -> term whileexp
	 * whileexp -> + term whileexp | - term whileexp | empty string
	 * precondition: current token is inside a WRITELN statement
	 * postcondition: all tokens associated with the exp have been eaten
	 * @return an evaluated integer
	 */
	public int parseExp()
	{
		int exp=parseTerm();
		while(currentToken.equals("+") || currentToken.equals("-"))
		{
			if(currentToken.equals("+"))
			{
				eat("+");
				exp=exp+parseTerm();
			}
			else
			{
				eat("-");
				exp=exp-parseTerm();
			}
		}
		return exp;
	}
}
