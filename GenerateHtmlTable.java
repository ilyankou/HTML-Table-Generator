import java.io.*;
import java.util.*;
import java.util.regex.*;

/**
 * Generates HTML code for a <table> from a text file where a new line (\n)
 * corresponds to a new table row, and cell contents are separated by a tab (\t).
 * Outputs the HTML code into output.html
 *
 * Example of the source file content:
 * Country	Capital
 * Austria	Vienna
 * Germany	Berlin
 * Italy	Rome
 *
 * @author Ilya Ilyankou <ilya.ilyankou@trincoll.edu>
 * @version 0.1, 05/31/2015
 */
public class GenerateHtmlTable {

	public static void main(String[] args) {
		
		if (args.length < 1) {
			System.out.println("Usage: java GenerateHtmlTable sourcefile [int border] [int cellpadding]");
			return;
	    }

	    try {
			
	    	File f = new File(args[0]);
	      	InputStreamReader iStream = new InputStreamReader(new FileInputStream(f));
	      	int length = (int) f.length();
	     	char[] input = new char[length];
	    	iStream.read(input);
	    	String inString = new String(input);
			
			while (inString.indexOf("\n\n") != -1) {
				inString = inString.replace("\n\n", "\n");
			}
				
			while (inString.indexOf("\t\t") != -1) {
				inString = inString.replace("\t\t", "\t");
			}
			
	    	StringTokenizer st = new StringTokenizer(inString, "\t\n", true);
		  
			String token1 = "";			// cell content
			String token2 = "";			// delimeter
			String cellOpenTag = "<th>";
			String cellCloseTag = "</th>";
		 	boolean newLine = true;
			
			int border = (args.length > 1) ? Integer.parseInt(args[1]) : 1;
			int cellpadding = (args.length > 2) ? Integer.parseInt(args[2]) : 1;
				
		  	StringBuffer output = new StringBuffer(String.format("<table border=%d cellpadding=%d>\n", border, cellpadding));
		  
		  	while (st.hasMoreTokens()) {
				token1 = st.nextToken();
				token2 = st.hasMoreTokens() ? st.nextToken() : "\n";

				if (token2.equals("\t")) {
					if (newLine) {
						newLine = false;
						output.append("\t<tr>\n");
					}
				  	output.append("\t\t" + cellOpenTag);
				  	output.append(token1.trim());
				  	output.append(cellCloseTag + "\n");
			 	}
			  
				if (token2.equals("\n")) {
				  	output.append("\t\t" + cellOpenTag);
				  	output.append(token1.trim());
				  	output.append(cellCloseTag + "\n");
				  	output.append("\t</tr>\n");
				  
				  	newLine = true;
				  	cellOpenTag = "<td>";
				  	cellCloseTag = "</td>";
				}
			}
		  
			output.append("</table>");
		  
			PrintWriter writer = new PrintWriter("output.html", "UTF-8");
			writer.print(output);
			writer.close();
		} 
		
		catch (FileNotFoundException e) {
	    	System.err.println("Error: File " + args[0] + " not found");
	    	e.printStackTrace();
	    } 
		
		catch (IOException e) {
	    	System.err.println("Error: I/O exception");
	    	e.printStackTrace();
	    }
		
	}
}