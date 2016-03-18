package parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import scanner.Scanner;
import ast.ProcedureDeclaration;
import environment.Environment;
import ast.Program;

/**
 * Creates a Scanner and Parser object and parses a file containing Pascal expressions.
 * @author Jackelyn Shen
 * @version January 8, 2015
 */
public class ParserTester
{
    /**
     * Creates a FileInputStream object with a specific tester file to be
     * inputted into the Scanner.
     * Creates a variable HashMap with parameters String and Integer and a procedure
     * HashMap with parameters String and ProcedureDeclaration to pass into a new 
     * Environment used for the Parser.
     * Constructs a Scanner object and a Parser object to parse the text file
     * until the end of the file by parsing a Program and executing the resulting
     * abstract syntax tree. Also calls the program's compile method to convert
     * Pascal code into MIPS instructions in an output file.
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
        Parser parse=new Parser(scan);
        while(!parse.getToken().equals("."))
        {
            Program p=parse.parseProgram();
            p.exec(env);
            p.compile("test.asm");
        }
    }
}