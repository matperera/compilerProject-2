import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.locks.Condition;

/*
 * TODO: look at saving all the line from the txt file into an ArrayList
 */
public class main {
    static Scanner scan;
    public static ArrayList<String> Errs = new ArrayList<String>();
    public static ArrayList<String> Conditions = new ArrayList<String>();


    public static void main(String arg []) throws IOException {
        Conditions.add("==");
        Conditions.add(">");
        Conditions.add("<");
        Conditions.add(">=");
        Conditions.add("<=");
        Conditions.add("!=");

        String fileName = "Output.txt";
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        try {
            scan = new Scanner(new BufferedReader(new FileReader("test1.txt")));

            writer.write("Group 2 - Juan Moran, Sarah de Pillis-Lindheim, Matt Perera");
            writer.newLine();
            while (scan.hasNext()) {
                String currentLine = scan.nextLine();
                if(currentLine.contains("void")||currentLine.contains("main")) {
//					System.out.print("<APU_CS370> ::=");
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
            if (scan != null) {
                scan.close();
                writer.close();
            }
        }printErrors();
    }


    private static void statement() {
        String currentLine = scan.nextLine();
        if(currentLine.equals("{")) {
            System.out.print("<Statement> ::= ");
            if(scan.hasNext("}")) {
                scan.nextLine();
                epsilon();
            }
            currentLine = scan.nextLine();
            if(currentLine.equals("}")) {
                epsilon();
            }else if(!currentLine.isEmpty()){
                System.out.println("<Declaration>");
                Declaration(currentLine);
            }else {
                System.out.println("Missing }");
            }
        }
    }



    private static void Declaration(String current) {
        System.out.print("<Declaration> ::= ");
        String word = current;
        int indexOfSpace = 0;

        //used incase there are no spaces
        if(current.contains(" ")) {
            indexOfSpace = current.indexOf(' ');
            word = current.substring(0, indexOfSpace);
        }

        if (word.equals("Integer"))
            Integer(current.substring(indexOfSpace));
        else if(word.contains("DOWhile"))
            DoWHile(current);
        else{
            Errs.add("Unknown Token");
        }
        if(!(current.substring(current.length()-1).equals(";"))&&(word.contains("Integer")||word.contains("Float")))
            Errs.add("Missing semicolon.");

    }


    private static void DoWHile(String current) {
        System.out.println("<DoWhile statement> ");
        System.out.println("<DOWhile statement> ::= DOWhile( <Condition> ) { <Statement> }");
        String word = current.replace("DOWhile","");
        word=word.replace(" ","");
        if(word.charAt(0)=='('){
            if(word.charAt(word.length()-1)==')'){
                word=word.replace("(","");
                word=word.replace(")","");
                conditon(word);
                statement();
            }
            else{
                Errs.add("Missing )");
                word=word.replace("(","");
                word=word.replace(")","");
                conditon(word);
                statement();
            }
        }
        else{
            Errs.add("Missing (");
        }

    }

    private static void conditon(String current) {
        System.out.println("<Condition> ::= <Identifier> <CompOperator> <Identifier>");

        boolean conditionFound = false;
        int indexOfCon = -1;
        String cond="";
        for (String a:Conditions) {
            if(current.contains(a)){
                cond = a;
                conditionFound=true;

            }

        }
        indexOfCon = current.indexOf(cond);
        if(conditionFound){
            String first = current.substring(0,indexOfCon);
            String second = current.substring(indexOfCon+cond.length());
//            System.out.println("First: " + first);
//            System.out.println("Second: "+ second);
            System.out.println("<Identifier> ::= <Letter>");
            System.out.println("<CompOperator> ::= == | > | < | >= | <= | != ");
            System.out.print("<CompOperator> ::= ");
            System.out.println(cond);
            System.out.println("<Identifier> ::= <Letter>");
        }



    }

    private static void printErrors() {
        // TODO Auto-generated method stub
        if(Errs.size() > 0) {
            System.out.println("\n\n*****************************************************");
            for (int i = 0; i < Errs.size(); i++) {
                System.out.println(Errs.get(i));
            }
            System.out.println("*****************************************************");
            System.out.println("\n\nSyntax is incorrect. Check error above. Total error(s) found " + Errs.size());
        }
        else
            System.out.println("\n\nSyntax is correct. No errors found.");
    }

    private static void Integer(String current) {
        // TODO Auto-generated method stub
        System.out.println(" Integer <Identifier> = <Integer>;");
//		System.out.println(current);
        while (current.charAt(0) == (' ')) {
            current = current.substring(1);
        }

        String identifier = current;
        if (!Character.isLetter(identifier.charAt(0))){
            Errs.add("Invalid or missing identifier");
        }
        else {
            System.out.println("<Identifier> ::= <Letter>");
            System.out.println("<Letter> ::= a|...|Z");
            int equal = current.indexOf('=');
            identifier = identifier.substring(0, equal);
            current = current.substring(equal);
            while (current.charAt(0) == (' ')) {
                current = current.substring(1);
            }
            boolean error = false;
            try {
                int num = Integer.parseInt(current);
            }
            catch(Error e) {
                error = true;
                Errs.add("Missing 'integer value'");
            }
            finally {
                if (error == false)
                    System.out.println("<Integer> ::= 0|...|9");
            }

        }

    }

    private static void epsilon() {
        System.out.println("< Empty > ::= Epsilon");
    }
}
