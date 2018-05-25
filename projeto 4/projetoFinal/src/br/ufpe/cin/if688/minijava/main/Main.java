
package br.ufpe.cin.if688.minijava.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;




import br.ufpe.cin.if688.minijava.ast.BooleanType;
import br.ufpe.cin.if688.minijava.ast.ClassDeclExtends;
import br.ufpe.cin.if688.minijava.ast.ClassDeclList;
import br.ufpe.cin.if688.minijava.ast.ClassDeclSimple;
import br.ufpe.cin.if688.minijava.ast.Identifier;
import br.ufpe.cin.if688.minijava.ast.IdentifierType;
import br.ufpe.cin.if688.minijava.ast.IntegerLiteral;
import br.ufpe.cin.if688.minijava.ast.IntegerType;
import br.ufpe.cin.if688.minijava.ast.MainClass;
import br.ufpe.cin.if688.minijava.ast.MethodDeclList;
import br.ufpe.cin.if688.minijava.ast.Print;
import br.ufpe.cin.if688.minijava.ast.Program;
import br.ufpe.cin.if688.minijava.ast.VarDecl;
import br.ufpe.cin.if688.minijava.ast.VarDeclList;
import br.ufpe.cin.if688.minijava.visitor.PrettyPrintVisitor;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

public class Main {

	public static void main(String[] args) throws IOException {
		
		// System.out.print("\n\nAnalisando o arquivo: ");
        //System.out.println(file);
			InputStream stream = new FileInputStream("src/BinarySearch.java");
			ANTLRInputStream input = new ANTLRInputStream(stream);
			antlrLexer lexer = new antlrLexer(input);
			CommonTokenStream token = new CommonTokenStream(lexer);
			antlrParser parser = new antlrParser(token);
			//parser.goal();
			
			
		//assert parser.getNumberOfSyntaxErrors() == 0;
		//System.out.print("Terminou de analisar o arquivo: ");
        //System.out.println(file);
        //System.out.println("\n");
			Program program = (Program) new MinhaClasse().visitGoal(parser.goal());
			PrettyPrintVisitor ppv = new PrettyPrintVisitor();
			ppv.visit(program);
	}

}
