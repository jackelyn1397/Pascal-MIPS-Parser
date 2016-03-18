package origparser;

import scanner.*;
import ast.*;
import ast.Number;
import java.util.ArrayList;
import environment.*;

/**
 * Creates a Parser object to parse Pascal expressions using an abstract syntax trees.
 * @author Jackelyn Shen
 * @version October 17, 2014
 */
public class Parser2 
{
    Scanner scan;
    Environment environment;
    String currentToken;

    /**
     * Constructor for Parser object.
     * Takes in a Scanner and stores it in an instance variable.
     * Takes in an Environment to store identifier values.
     * Call's the Scanner's next method and stores the returned String in another 
     * instance variable that represents the current token.
     * Catches a ScanErrorExpection if the next token cannot be scanned, then aborts
     * the program.
     * @param s  the Scanner to be set in an instance variable
     * @param env  the environment to be set in an instance variable
     */
    public Parser2(Scanner s, Environment env)
    {
        scan=s;
        environment=env;
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
    
    public String getToken()
    {
        return currentToken;
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
     * If the current token is an integer, returns a Number containing the integer.
     * precondition: current token is an integer
     * postcondition: number token has been eaten
     * @return a Number object containing the parsed integer
     */
    public Expression parseNumber()
    {
        int num=Integer.parseInt(currentToken);
        eat(currentToken);
        return new Number(num);
    }

    /**
     * Handles BEGIN/END blocks and IF/WHILE statements, prints out the
     * evaluated expression contained in WRITELN, and sets identifier values.
     * Throws an IOException if the statement cannot be parsed (the current token
     * is neither WRITELN, BEGIN or an identifier)
     * stmt -> WRITELN ( exp ) ; | BEGIN whilebegin | id := exp ; | IF cond THEN stmt |
     * WHILE cond DO stmt
     * cond -> exp relop exp
     * relop -> = | <> | < | > | <= | >=
     * whilebegin -> END ; | stmt whilebegin
     * postcondition: all tokens associated with WRITELN or BEGIN have been eaten
     */
    public Statement parseStatement()
    {
        char first=currentToken.charAt(0);
        if(currentToken.equals("WRITELN"))
        {
            eat("WRITELN");
            eat("(");
            Expression exp=parseExp();
            eat(")");
            eat(";");
            return new Writeln(exp);
        }
        else if(currentToken.equals("BEGIN"))
        {
            eat("BEGIN");
            ArrayList<Statement> list=new ArrayList<Statement>();
            while(!currentToken.equals("END"))
            {
                Statement s=parseStatement();
                list.add(s);
            }
            eat("END");
            eat(";");
            return new Block(list);
        }
        else if(currentToken.equals("IF"))
        {
            eat("IF");
            Expression exp1=parseExp();
            String relop=currentToken;
            eat(currentToken);
            Expression exp2=parseExp();
            eat("THEN");
            Statement thenstatement=parseStatement();
            Condition condition=new Condition(exp1,relop,exp2);
            return new If(condition,thenstatement);

        }
        else if(currentToken.equals("WHILE"))
        {
            eat("WHILE");
            Expression exp1=parseExp();
            String relop=currentToken;
            eat(currentToken);
            Expression exp2=parseExp();
            eat("DO");
            Statement dostatement=parseStatement();
            Condition condition=new Condition(exp1,relop,exp2);
            return new While(condition,dostatement);
        }
        else if(Scanner.isLetter(first))
        {
            String identifier=currentToken;
            eat(currentToken);
            eat(":=");
            Expression value=parseExp();
            eat(";");
            return new Assignment(identifier, value);
        }
        else
        {
        	return null;
        }

    }

    /**
     * Evaluates a factor inside of WRITELN according to the following grammar:
     * factor -> ( exp ) | - factor | num | id
     * precondition: current token is inside a WRITELN statement
     * postcondition: all tokens associated with the factor have been eaten
     * @return an Expression containing the evaluated factor
     */
    public Expression parseFactor()
    {
        char first=currentToken.charAt(0);
        if(currentToken.equals("("))
        {
            eat("(");
            Expression exp=parseExp();
            eat(")");
            return exp;
        }
        else if(currentToken.equals("-"))
        {
            eat("-");
            return new BinOp(new Number(0),"-",parseFactor());
        }
        else if(Scanner.isLetter(first))
        {
            String identifier=currentToken;
            eat(currentToken);
            return new Variable(identifier);
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
     * @return an Expression containing the evaluated term
     */
    public Expression parseTerm()
    {
        Expression term=parseFactor();
        while(currentToken.equals("*") || currentToken.equals("/"))
        {
            if(currentToken.equals("*"))
            {
                eat("*");
                term=new BinOp(term,"*",parseFactor());
            }
            else
            {
                eat("/");
                term=new BinOp(term,"/",parseFactor());
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
     * @return an Expression containing the evaluated expression
     */
    public Expression parseExp()
    {
        Expression exp=parseTerm();
        while(currentToken.equals("+") || currentToken.equals("-"))
        {
            if(currentToken.equals("+"))
            {
                eat("+");
                exp=new BinOp(exp,"+",parseTerm());
            }
            else
            {
                eat("-");
                exp=new BinOp(exp,"-",parseTerm());
            }
        }
        return exp;
    }
}
