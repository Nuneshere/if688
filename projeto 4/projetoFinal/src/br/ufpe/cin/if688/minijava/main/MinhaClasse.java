package br.ufpe.cin.if688.minijava.main;

import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import br.ufpe.cin.if688.minijava.ast.*;
import br.ufpe.cin.if688.minijava.main.antlrParser.ClassdeclarationContext;
import br.ufpe.cin.if688.minijava.main.antlrParser.ExpressionContext;
import br.ufpe.cin.if688.minijava.main.antlrParser.GoalContext;
import br.ufpe.cin.if688.minijava.main.antlrParser.IdentifierContext;
import br.ufpe.cin.if688.minijava.main.antlrParser.MainclassContext;
import br.ufpe.cin.if688.minijava.main.antlrParser.MethoddeclarationContext;
import br.ufpe.cin.if688.minijava.main.antlrParser.StatementContext;
import br.ufpe.cin.if688.minijava.main.antlrParser.TypeContext;
import br.ufpe.cin.if688.minijava.main.antlrParser.VardeclarationContext;

public class MinhaClasse implements antlrVisitor<Object>{

	@Override
	public Object visit(ParseTree arg0) {
		// TODO Auto-generated method stub
		return arg0.accept(this);
	}

	@Override
	public Object visitChildren(RuleNode arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visitErrorNode(ErrorNode arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visitTerminal(TerminalNode arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visitVardeclaration(VardeclarationContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visitMainclass(MainclassContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visitClassdeclaration(ClassdeclarationContext ctx) {
		
		//vamos seguir então o a sequencia de VaredeclList e depois MethodDeclList, percorrendo pela lista e aceitando todos componentes
		VarDeclList avl = new  VarDeclList();
		for(int i = 0 ; i < ctx.vardeclaration().size(); i++ ){
			VarDecl v =  (VarDecl) ctx.vardeclaration(i).accept(this);
			avl.addElement(v);
		}
		
		MethodDeclList mdl = new  MethodDeclList();
		for(int i = 0 ; i < ctx.methoddeclaration().size(); i++ ){
			MethodDecl m =  (MethodDecl) ctx.methoddeclaration(i).accept(this);
			mdl.addElement(m);
		}
		ClassDecl classDec;
		//aqui os identifiers iniciais são importantes, somente com 1 se torna uma declaração de classe simples, porém com mais de 1 seria uma classe que extende
		if(ctx.identifier().size() > 1) {
			classDec = new ClassDeclExtends( ( Identifier )  ctx.identifier(0).accept(this), (Identifier) ctx.identifier(1).accept(this), avl, mdl );
		} else {
			//caso somente 1 identifier então se torna simples
			classDec = new ClassDeclSimple(( Identifier )  ctx.identifier(0).accept(this),avl,mdl);
		}
		return classDec;
	}

	@Override
	public Object visitIdentifier(IdentifierContext ctx) {
		// TODO Auto-generated method stub
		return new Identifier(ctx.getText());
	}

	@Override
	public Object visitGoal(GoalContext ctx) {
		// TODO Auto-generated method stub
		//composta por mainclass class declaration e EOF
		MainClass main = (MainClass) ctx.mainclass().accept(this);
		
		ClassDeclList declList = new ClassDeclList();
		for (ClassdeclarationContext cd: ctx.classdeclaration()) {
			declList.addElement((ClassDecl) cd.accept(this));
		}
		
		
		return new Program(main, declList);
	}

	@Override
	public Object visitExpression(ExpressionContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visitMethoddeclaration(MethoddeclarationContext ctx) {
		//composta por type, identifier, formalist, vardeclist, statement list [listas especificadas no methoddecllist] e expressões
		//vamos aceitar diretamente tudo que não seja listas
		
		Type at = (Type) ctx.type(0).accept(this);
		Identifier ai = (Identifier) ctx.identifier(0).accept(this);
		
		FormalList afl = new FormalList();
		for(int i = 1 ; i < ctx.type().size(); i++ ){
			Type tipo = (Type) ctx.type(i).accept(this);
			Identifier iden = (Identifier) ctx.identifier(i).accept(this);
			Formal f = new Formal(tipo,iden);
			
			afl.addElement(f);
		}
		
		VarDeclList avl = new  VarDeclList();
		for(int i = 0 ; i < ctx.vardeclaration().size(); i++ ){
			VarDecl v =  (VarDecl) ctx.vardeclaration(i).accept(this);
			avl.addElement(v);
		}
		
		StatementList asl = new StatementList();
		for(int i = 0 ; i < ctx.statement().size(); i++ ){
			Statement s = (Statement) ctx.statement(i).accept(this);
			asl.addElement(s);
		}
		
		Exp ae = (Exp) ctx.expression().accept(this);
		
		MethodDecl metodoFinal = new MethodDecl(at,ai,afl,avl,asl,ae);
		return metodoFinal;
		
		return null; 
	}

	@Override
	public Object visitStatement(StatementContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visitType(TypeContext ctx) {
		//reconhecer o type e retornar o devido tipo;
		String tipo = ctx.getText();
		if(tipo=="boolean") {
			return new BooleanType();
		} else if (tipo=="int") {
			return new IntegerType();
		} else if (tipo=="int []") {
			return new IntArrayType();
		} else {
			//é um identifier segundo a grammar
			return new IdentifierType(tipo);
		}
	}

}
