package scanner;

import java.io.*;

/**
 * Scanner is a simple scanner for Compilers and Interpreters (2014-2015) lab exercise 1
 * @author Jackelyn Shen
 * @version October 3, 2014
 *  
 * Usage:
 * Reads the input, determines lexemes according to a set of rules,
 * and produces a string of all tokens found.
 *
 */
public class Scanner
{
    private BufferedReader in;
    private char currentChar;
    private boolean eof;
    /**
     * Scanner constructor for construction of a scanner that 
     * uses an InputStream object for input.  
     * Usage: 
     * FileInputStream inStream = new FileInputStream(new File(<file name>);
     * Scanner lex = new Scanner(inStream);
     * @param inStream the input stream to use
     */
    public Scanner(InputStream inStream)
    {
        in = new BufferedReader(new InputStreamReader(inStream));
        eof = false;
        getNextChar();
    }
    
    /**
     * Scanner constructor for constructing a scanner that 
     * scans a given input string.  It sets the end-of-file flag an then reads
     * the first character of the input string into the instance field currentChar.
     * Usage: Scanner lex = new Scanner(input_string);
     * @param inString the string to scan
     */
    public Scanner(String inString)
    {
        in = new BufferedReader(new StringReader(inString));
        eof = false;
        getNextChar();
    }
    
    /**
     * Sets the value of currentChar by reading in the next character in the input.
     * Checks if it is the end of the file; if it is, sets eof to true.
     * Catches an IOErrorException when the input cannot be read.
     */
    private void getNextChar()
    {
        try
        {
            int temp=in.read();
            if(temp=='.')
            {
                eof=true;
            }
            else
            {
                currentChar=(char)temp;
            }
        }
        catch(IOException e)
        {
            System.out.println("IOErrorException");
            System.exit(-1);
        }
        
    }
    
    /**
     * Compares an expected char to currentChar.
     * If they are the same, calls getNextChar.
     * Thows a ScanErrorException if currentChar is not an expected value.
     * @param expected  the expected value of currentChar
     */
    
    private void eat(char expected) throws ScanErrorException
    {
        if(expected==currentChar)
        {
            getNextChar();
        }
        else
        {
            throw new ScanErrorException(
                        "Illegal character - expected "+expected+" and found "+currentChar);
        }
    }
    
    /**
     * Determines whether the end of the file has been reached.
     * @return true if it is not the end of the file; otherwise false
     */
    public boolean hasNext()
    {
       if(eof==false)
       {
           return true;
       }
       else
       {
           return false;
       }
    }
    
    /**
     * Skips any white space at the beginning and determines the next token based on the value
     * of currentChar (token can be a number, an identifier, or an operand).
     * Throws a ScanErrorException if currentChar is neither a digit, a letter, or an operator.
     * When the end of the file has been reached, outputs ".".
     * @return a String representing the next token in the input
     */
    public String nextToken() throws ScanErrorException
    {
        if(!hasNext())
        {
            return ".";
        }
        while(isWhiteSpace(currentChar))
        {
            eat(currentChar);
            if(!hasNext())
            {
                return ".";
            }
        }
        if(isDigit(currentChar))
        {
        	return scanNumber();
        }
        else if(isLetter(currentChar))
        {
            return scanIdentifier();
        }
        else if(isOperand(currentChar))
        {
            return scanOperand();
        }
        else
        {
            throw new ScanErrorException(
            		"Illegal expression - cannot read character "+currentChar);
        }
    }
    
    /**
     * Determines whether a character is an operand.
     * @param character the character to be determined
     * @return true if the character is an operand; otherwise false
     */
    public static boolean isOperand(char character)
    {
    	if(character=='=' || character==':' || character=='+' || character=='-' || 
    			character=='=' || character=='*' || character=='/' || character=='%' || 
    			character=='(' || character==')' || character=='>' || character=='<' || 
    			character==';' || character==',')
    	{
    		return true;
    	}
    	else
    	{
    		return false;
    	}
    }
    
    /**
     * Determines whether a character is a digit.
     * @param character the character to be determined
     * @return true if the character is a digit; otherwise false
     */
    public static boolean isDigit(char character)
    {
        if(character>='0' && character<='9')
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    /**
     * Determines whether a character is a letter.
     * @param character the character to be determined
     * @return true if the character is a letter; otherwise false
     */
    public static boolean isLetter(char character)
    {
        if((character>='a' && character<='z') || (character>='A' && character<='Z'))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    /**
     * Determines whether a character is a white space.
     * @param character the character to be determined
     * @return true if the character is a white space; otherwise false
     */
    public static boolean isWhiteSpace(char character)
    {
        if(character==' ' || character=='\r' || character=='\n' || character=='\t')
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    /**
     * Scans the input to determine if the suggested number is a valid token
     * based on digit(digit)*.
     * Throws a ScanErrorExpection if the number is not valid.
     * @return a String representing the number
     */
    private String scanNumber() throws ScanErrorException
    {
    	String number="";
        if(!isDigit(currentChar))
        {
        	throw new ScanErrorException(
            		"Illegal character - expected a digit or a letter and found "+currentChar);
        }
        while(eof==false && isDigit(currentChar) )
        {
            number+=currentChar;
            eat(currentChar);
        }
        return number;
    }
    
    /**
     * Scans the input to determine if the suggested identifier is a valid token
     * based on letter (letter | digit)*.
     * Throws a ScanErrorExpection if the identifier is not valid.
     * @return a String representing the identifier
     */
    private String scanIdentifier() throws ScanErrorException
    {
        String identifier="";
        if(!isLetter(currentChar))
        {
        	throw new ScanErrorException(
            		"Illegal character - expected a digit or a letter and found "+currentChar);
        }
        while(eof==false && (isLetter(currentChar) || isDigit(currentChar)))
        {
        	identifier+=currentChar;
        	eat(currentChar);
        }
        return identifier;
    }
    
    /**
     * Scans the input to determine if the suggested operand is a valid token.
     * Throws a ScanErrorExpection if the operand is not valid.
     * @return a String representing the operand
     */
    private String scanOperand() throws ScanErrorException
    {
        String operand="";
        if(isOperand(currentChar))
        {
            operand+=currentChar;
        	eat(currentChar);
        	if(currentChar=='=' || currentChar=='>')
        	{
        		operand+=currentChar;
        		eat(currentChar);
        	}
        	return operand;
        }
        else
        {
            throw new ScanErrorException(
            		"Illegal character - expected an operand and found "+currentChar);
        }
    }
        
}