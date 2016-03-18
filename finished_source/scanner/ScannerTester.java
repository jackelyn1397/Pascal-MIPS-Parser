package scanner;

import java.io.*;
/**
 * A ScannerTester object inputs a file into a Scanner object and runs nextToken until the end of the file.
 * 
 * @author Jackelyn Shen 
 * @version September 2, 2014
 */
public class ScannerTester
{
    /**
     * Creates a FileInputStream object with a specific tester file to be inputted into the Scanner.
     * Catches an IOErrorException when the file is not valid and terminates the program.
     * Constructs a Scanner object and runs nextToken until the end of the file.
     * Prints out all tokens found in the string or the file inputted to the Scanner.
     * Catches a ScanErrorException based on reasons stated in the individual methods.
     */
    public static void main(String[] args)
    {
        FileInputStream inStream=null;
        try
        {
            inStream=new FileInputStream(new File("/Users/admin/Desktop/scannerTest.txt"));
        }
        catch(IOException e)
        {
            System.out.println("Not a valid file");
            System.exit(-1);
        }
        Scanner scan=new Scanner(inStream);
        try
        {
            String s=scan.nextToken();
            System.out.println(s);
            while(!s.equals("."))
            {
                s=scan.nextToken();
                System.out.println(s);
            }
        }
        catch(ScanErrorException e)
        {
            System.out.println(e);
            System.exit(-1);
        }
    }
}
