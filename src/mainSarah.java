import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
 * TODO: look at saving all the line from the txt file into an ArrayList
 */
public class main {
	static Scanner scan;
	public static ArrayList<String> Errs = new ArrayList<String>();
	public static void main(String arg []) throws IOException {
		String fileName = "Output.txt";
		BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
		try {
			scan = new Scanner(new BufferedReader(new FileReader("test1.txt")));

			writer.write("Group 2 - Juan Moran, Sarah de Pillis-Lindheim, Matt Perera");
			writer.newLine();
			while (scan.hasNext()) {
				String currentLine = scan.nextLine();
				if(currentLine.contains("void")||currentLine.contains("main")) {
					if(currentLine.substring(0, 4).equals("void")) {
						currentLine=currentLine.substring(4);
						if(currentLine.charAt(0)==' ') {
							while(currentLine.charAt(0)==' ') {
							 currentLine=currentLine.substring(1);
							}
							if(currentLine.substring(0, 4).equals("main")) {
								currentLine=currentLine.substring(4);
								if(currentLine.charAt(0)=='(') {
									currentLine=currentLine.substring(1);
									if(currentLine.charAt(0)==')') {
										System.out.println("<APU_CS370> ::= void main() { <statement> }");
										statement();
									}else {
										Errs.add("There is a ) missing from the end of the statment");
									}
								}else {
									Errs.add("There is a ( missing after the main");
								}
							}else {
								Errs.add("main is misspelled or missing");
							}
						}else {
							Errs.add("There is a missing space after void");
						}
					}
					else {
						System.out.println(currentLine.substring(0, 4));
						System.out.println("void is misspelled or missing");
					}
				}
				writer.write(currentLine);
				writer.newLine();
			}
		}finally {
			printErrors();
			if (scan != null) {
				scan.close();
				writer.close();
			}
		}
	}

	private static boolean mainCheck() {
		// TODO Auto-generated method stub
		return false;
	}

	private static void statement() {
		while (scan.hasNext()) {
			boolean closed = false;
			String currentLine = scan.nextLine();
			if(currentLine.equals("{")) {
				if(scan.hasNext("}")) {
					closed = true;
					System.out.println("<Statement> ::= <Empty>");
					System.out.println("<Empty> ::= Epsilon");
							
				}
				while (scan.hasNext()) {
					if (scan.hasNext()) {
						currentLine = scan.nextLine();
						while(currentLine.isEmpty()) {
							currentLine = scan.nextLine();
						}
						if(currentLine.equals("}")) {
							closed = true;
						}else if(!currentLine.isEmpty()){
							System.out.println("<Statement> ::= <Declaration>");
							Declaration(currentLine);
						}else {
							Errs.add("Missing '}'");
						}
					}
				}
		}
		else if(currentLine.isEmpty()) {
			
		}
		else {
			/*
			 * TODO: Fix it so that it does understand the last } is missing
			 */
			Errs.add("Missing '{'");
		}
			if(closed == false)
				Errs.add("Missing '}'");
	}
		
			
	}

	private static void Declaration(String current) {
		System.out.print("<Declaration> ::= ");
		int indexOfSpace = current.indexOf(' ');
		String word = current.substring(0, indexOfSpace);
		if (word.equals("Integer"))
			Integer(current.substring(indexOfSpace));
		else if (word.equals("Float"))
			Float(current.substring(indexOfSpace));
	}

	private static void printErrors() {
		if(Errs.size() > 0) {
			System.out.println("\n\n*****************************************************");
			System.out.println("ERRORS");
			for (int i = 0; i < Errs.size(); i++) {
				System.out.println((i+1) + ". " + Errs.get(i));
			}
			System.out.println("*****************************************************");
			System.out.println("\n\nSyntax is incorrect. Check error above. Total error(s) found " + Errs.size());
		}
		else
			System.out.println("\n\nSyntax is correct. No errors found.");
	}
	
	private static void Float(String current) {
		System.out.println(" Float <Identifier> = <Float>;");
		while (current.charAt(0) == (' ')) {
			current = current.substring(1);
		}
			
		String identifier = current;
		if (!Character.isLetter(identifier.charAt(0))){
			Errs.add("Invalid or missing identifier");
		}
		else {
			System.out.println("<Identifier> ::=<Letter>");
			System.out.println("<Letter> ::= a|...|Z");
			int indx = current.indexOf('=');
			if (indx < 0){
				Errs.add("Missing '='");
				indx = current.indexOf(' ');
				identifier = identifier.substring(0, indx);
			}
			else {
				identifier = identifier.substring(0, indx);
				
				current = current.substring(indx+1);
				while (current.charAt(0) == (' ')) {
					current = current.substring(1);
				}
				if((current.substring(current.length()-1).equals(";"))){
					current = current.replace(";", "");
					boolean error = false;
					try {
						double num = Double.parseDouble(current);
					}
					catch(NumberFormatException e) {
						error = true;
						Errs.add("Missing 'integer value'");
					}
					if (error != true)
						System.out.println("<Float> ::= .|0|...|9");
				}
				else
					Errs.add("Missing semicolon.");
			}
			
		}
	}

	private static void Integer(String current) {
		System.out.println(" Integer <Identifier> = <Integer>;");
		while (current.charAt(0) == (' ')) {
			current = current.substring(1);
		}
			
		String identifier = current;
		if (!Character.isLetter(identifier.charAt(0))){
			Errs.add("Invalid or missing identifier");
		}
		else {
			System.out.println("<Identifier> ::=<Letter>");
			System.out.println("<Letter> ::= a|...|Z");
			int indx = current.indexOf('=');
			if (indx < 0){
				Errs.add("Missing '='");
				indx = current.indexOf(' ');
				identifier = identifier.substring(0, indx);
			}
			else {
				identifier = identifier.substring(0, indx);

				current = current.substring(indx+1);
				while (current.charAt(0) == (' ')) {
					current = current.substring(1);
				}
				if((current.substring(current.length()-1).equals(";"))){
					current = current.replace(";", "");
					boolean error = false;
					try {
						int num = Integer.parseInt(current);
					}
					catch(NumberFormatException e) {
						error = true;
						Errs.add("Missing 'integer value'");
					}
					if (error != true)
						System.out.println("<Integer> ::= 0|...|9");
				}
				else
					Errs.add("Missing semicolon.");
			}
			
		}
				
	}
}
