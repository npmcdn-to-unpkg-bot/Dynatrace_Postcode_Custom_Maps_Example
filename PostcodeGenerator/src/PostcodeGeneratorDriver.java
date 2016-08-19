import java.util.Random;
import java.util.Scanner;

/*
 * This file acts as a driver for the PostcodeWeb application
 * It will wait in an endless loop for you to enter a postcode or the first part of
 * a postcode.
 * eg. Either SL6 or SL6 4AY
 * 
 * In AppMon, you must create an argument sensor on the submitPostcode method and
 * capture the String argument.
 * 
 * That's all this file is - a way to get postcode data into AppMon.
 */
public class PostcodeGeneratorDriver
{
	
	public static void main(String[] args)
	{
		try
		{
			System.out.println("Please enter postcode");
			Scanner oScanner = new Scanner(System.in);
			
			boolean bHasNextLine = oScanner.hasNextLine();
		
			/* 
			 * Wait in an endless loop for postcode entries.
			 * Type your postcode and hit Enter.
			 * To exit the program, type the word exit or quit.
			 */
			while (bHasNextLine)
			{
				System.out.println("Please enter postcode");
				
				String strInput = oScanner.nextLine();
				if ("quit".equalsIgnoreCase(strInput) || "exit".equalsIgnoreCase(strInput)) bHasNextLine = false;
				
				/*
				 * Just some safety checking, we don't want empty results in
				 * AppMon so only call the method if we have data. 
				 */
				if (strInput != null && !strInput.isEmpty()) submitPostcode(strInput);
			}
			
			/*
			 * If we get here, you've typed quit or exit.
			 * Close the scanner object to prevent memory leaks.
			 */
			oScanner.close();
		}
		catch (Exception e){}
	}
	
	public static void submitPostcode(String strPostcode)
	{
		// Print a confirmation message in the cmd window.
		System.out.println("Submitting Postcode: " + strPostcode);
	}
}
