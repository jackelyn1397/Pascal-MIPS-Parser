package origparser;

import ast.ProcedureDeclaration;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import scanner.Scanner;
import environment.Environment;

/**
 * Creates a Scanner and Parser object and parses a file containing Pascal expressions.
 * @author Jackelyn Shen
 * @version October 17, 2014
 */
public class ParserTester2
{
    /**
     * Creates a FileInputStream object with a specific tester file to be
     * inputted into the Scanner.
     * Catches an IOErrorException when the file is not valid and terminates the program
     * or when the user input statement cannot be parsed.
     * Creates a HashMap with parameters String and Integer to pass into a new Environment
     * used for the Parser.
     * Constructs a Scanner object and a Parser object to parse the text file
     * until the end of the file.
     */
    public static void main(String[] args)
    {
        FileInputStream inStream=null;
        try
        {
            inStream=new FileInputStream(new File("./parseTest.txt"));
        }
        catch(IOException e)
        {
            System.out.println("Not a valid file");
            System.exit(-1);
        }
        Scanner scan=new Scanner(inStream);
        HashMap<String,Integer> vMap=new HashMap<String,Integer>();
        HashMap<String,ProcedureDeclaration> pMap=new HashMap<String,ProcedureDeclaration>();
        Environment env=new Environment(vMap, pMap, null);
        Parser2 parse=new Parser2(scan, env);
        while(!parse.getToken().equals("."))
        {
            parse.parseStatement().exec(env);
        }
    }
}