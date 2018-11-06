import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/*
 * TODO: look at saving all the line from the txt file into an ArrayList
 */
public class main {
	static Scanner scan;
	public static void main(String arg []) throws IOException {
		String fileName = "Output.txt";
		BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
		try {
			scan = new Scanner(new BufferedReader(new FileReader("test1.txt")));

			writer.write("Group 2 - Juan Moran, Sarah de Pillis-Lindheim, Matt Perera");
			writer.newLine();
			while (scan.hasNext()) {
				boolean passed  = mainCheck();
				String currentLine = scan.nextLine();
				if(currentLine.contains("void")||currentLine.contains("main")) {
					if(currentLine.substring(0, 4).equals("void")) {
						currentLine=currentLine.substring(4);
						if(currentLine.charAt(0)==' ') {
							currentLine=currentLine.substring(1);
							if(currentLine.substring(0, 4).equals("main")) {
								currentLine=currentLine.substring(4);
								if(currentLine.charAt(0)!=' '){
									 if(currentLine.charAt(0)=='(') {
										if(currentLine.charAt(currentLine.length()-1)==')') {
											System.out.println("<APU_CS370> ::=void main() { <statement> }");
											statment();
										}else {
											System.out.println("There is a ( missing after the main");
										}
									}else {
										System.out.println("There is a ) missing from the end of the statment");
									}
								}else {
									System.out.println("There is a extra space in between main and ()");
								}
							}else {
								System.out.println("main is misspelled or missing");
							}
						}else {
							System.out.println("There is a missing space after void");
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

	private static void statment() {
		while (scan.hasNext()) {
			String currentLine = scan.nextLine();
			//System.out.println(currentLine);
			if(currentLine.equals("{")) {
				if(scan.hasNext("}")) {
					scan.nextLine();
				}
				currentLine = scan.nextLine();
				//System.out.println(currentLine);
				if(currentLine.equals("}")) {
					System.out.println("<Statement> ::= < Empty >\n"
							+ "< Empty > ::= Epsilon\n" + 
							"Syntax is correct No errors Found\n");
				}else if(!currentLine.isEmpty()){
					//System.out.println("The line is not empty");
					inner(currentLine);
				}else {
					System.out.println("Missing }");
				}
			}else {
				/*
				 * TODO: Fix it so that it does understand the last } is missing
				 */
				System.out.println("Missing {");
				break;
			}
		}
	}

	private static void inner(String current) {
		// TODO Auto-generated method stub
		System.out.println(current);
		String currentWord = scan.next();
		System.out.println(currentWord);
	}
}
