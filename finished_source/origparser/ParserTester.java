package origparser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import scanner.*;

/**
 * Creates a Scanner and Parser object and parses a file containing Pascal expressions.
 * @author Jackelyn Shen
 * @version October 3, 2014
 */
public class ParserTester
{
	/**
     * Creates a FileInputStream object with a specific tester file to be inputted into the Scanner.
     * Catches an IOErrorException when the file is not valid and terminates the program
     * or when the user input statement cannot be parsed.
     * Constructs a Scanner object and a Parser object to parse the text file.
     */
    public static void main(String[] args)
    {
        FileInputStream inStream0=null;
        FileInputStream inStream01=null;
        FileInputStream inStream1=null;
        FileInputStream inStream2=null;
        FileInputStream inStream3=null;
        FileInputStream inStream4=null;
        try
        {
            inStream0=new FileInputStream(new File("/Users/admin/Downloads/parserTest0.txt"));
            inStream01=new FileInputStream(new File("/Users/admin/Downloads/parserTest01.txt"));
            inStream1=new FileInputStream(new File("/Users/admin/Downloads/parserTest1.txt"));
            inStream2=new FileInputStream(new File("/Users/admin/Downloads/parserTest2.txt"));
            inStream3=new FileInputStream(new File("/Users/admin/Downloads/parserTest3.txt"));
            inStream4=new FileInputStream(new File("/Users/admin/Downloads/parserTest4.txt"));
        }
        catch(IOException e)
        {
            System.out.println("Not a valid file");
            System.exit(-1);
        }
        Scanner scan0=new Scanner(inStream0);
        Scanner scan01=new Scanner(inStream01);
        Scanner scan1=new Scanner(inStream1);
        Scanner scan2=new Scanner(inStream2);
        Scanner scan3=new Scanner(inStream3);
        Scanner scan4=new Scanner(inStream4);
        HashMap<String,Integer> map=new HashMap<String,Integer>();
        Parser parse0=new Parser(scan0, map);
        Parser parse01=new Parser(scan01, map);
        Parser parse1=new Parser(scan1, map);
        Parser parse2=new Parser(scan2, map);
        Parser parse3=new Parser(scan3, map);
        Parser parse4=new Parser(scan4, map);
        try
        {
        	parse0.parseStatement();
        	parse01.parseStatement();
        	parse1.parseStatement();
        	parse2.parseStatement();
        	parse3.parseStatement();
        	parse4.parseStatement();
        }
        catch(IOException e)
        {
        	System.out.println(e);
        	System.exit(-1);
        }
    }
}
