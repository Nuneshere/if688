package br.ufpe.cin.if688.minijava.main;

import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import br.ufpe.cin.if688.minijava.ast.*;
import br.ufpe.cin.if688.minijava.ast.BooleanType;
import br.ufpe.cin.if688.minijava.ast.Exp;
import br.ufpe.cin.if688.minijava.ast.Identifier;
import br.ufpe.cin.if688.minijava.ast.StatementList;
import br.ufpe.cin.if688.minijava.ast.Type;
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
		// TODO Auto-generated method stub
		return null;
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
		//expression '.' identifier '(' ( expression ( ',' expression )* )? ')'
		if (ctx.getChildCount() >= 5 && ctx.getChild(1).getText().equals(".")) {
			Exp exp = (Exp) ctx.expression(0).accept(this);
			Identifier id = (Identifier) ctx.identifier().accept(this);
			
			ExpList expList = new ExpList();
			for (ExpressionContext element: ctx.expression()) {
				expList.addElement((Exp) element.accept(this));
			}
			return new Call(exp, id, expList);
		} else if (ctx.expression().size() == 2) {
			Exp exp1 = (Exp) ctx.expression(0).accept(this);
			Exp exp2 = (Exp) ctx.expression(1).accept(this);
			
			String opperand = ctx.getChild(1).getText();
			if (opperand.equals("&&")) {
				return new And(exp1, exp2);
			} else if (opperand.equals("<")) {
				return new LessThan(exp1, exp2);
			} else if (opperand.equals("+")) {
				return new Plus(exp1, exp2);
			} else if (opperand.equals("-")) {
				return new Minus(exp1, exp2);
			} else if (opperand.equals("*")) {
				return new Times(exp1, exp2);
			} else {
				return new ArrayLookup(exp1, exp2);
			}
		} else if (ctx.expression().size() == 1) {
			Exp exp = (Exp) ctx.expression(0).accept(this);
			
			String opperand = ctx.getChild(0).getText();
			if (opperand.equals("!")) {
				return new Not(exp);
			} else if (opperand.equals("(")) {
				return exp;
			} else if (opperand.equals("new")) {
				return new NewArray(exp);
			} else  {
				return new ArrayLength(exp);
			}
		} else {
			String token = ctx.getChild(0).getText();
			
			if (token.equals("true")) {
				return new True();
			} else if (token.equals("false")) {
				return new False();
			} else if (token.equals("this")) {
				return new This();
			} else if (token.equals("new")) {
				return new NewObject((Identifier) ctx.identifier().accept(this));
			} else {
				if (token.matches("\\d+")) {
					return new IntegerLiteral(Integer.parseInt(ctx.getStart().getText()));
				} else {
					return (Identifier) ctx.identifier().accept(this);
				}
			}
		}
	}

	@Override
	public Object visitMethoddeclaration(MethoddeclarationContext ctx) {
		//composta por type, identifier, formalist, vardeclist, statement list [listas especificadas no methoddecllist] e expressões
		//vamos aceitar diretamente tudo que não seja listas
		
		Type at = (Type) ctx.type(0).accept(this);
		Identifier ai = (Identifier) ctx.identifier(0).accept(this);
		
		FormalList afl = new FormalList();
		for(int i =1 ; i < ctx.type().size(); i++ ){
			Type tipo = (Type) ctx.type(i).accept(this);
			Identifier iden = (Identifier) ctx.identifier(i).accept(this);
			Formal f = new Formal(tipo,iden);
			
			afl.addElement(f);
		}
		
		VarDeclList avl = new  VarDeclList();
		for(int i =1 ; i < ctx.type().size(); i++ ){
			Type tipo = (Type) ctx.type(i).accept(this);
			Identifier iden = (Identifier) ctx.identifier(i).accept(this);
			Formal f = new Formal(tipo,iden);
			
			afl.addElement(f);
		}
		StatementList asl = new StatementList();
		
		Exp ae = (Exp) ctx.expression().accept(this);
		
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
