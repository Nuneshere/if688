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
		Type tipo = (Type) ctx.type().accept(this);
		Identifier iden = (Identifier) ctx.identifier().accept(this);
		
		VarDecl varD= new VarDecl(tipo,iden);
		return null;
	}

	@Override
	public Object visitMainclass(MainclassContext ctx) {
		//como as outras so basta olhar a classe no ast provido, e criar o mainclasse com o parametros desejado, apos isso retorna-lo
		Identifier iden = (Identifier) ctx.identifier(0).accept(this);
		Identifier iden2 = (Identifier) ctx.identifier(0).accept(this);
		Statement state = (Statement) ctx.statement().accept(this);
		MainClass main = new MainClass(iden,iden2,state);
		return main;
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
		for(int i = 1 ; i < ctx.type().size(); i++ ){
			Type tipo = (Type) ctx.type(i).accept(this);
			Identifier iden = (Identifier) ctx.identifier(i).accept(this);
			Formal f = new Formal(tipo,iden);
			
			afl.addElement(f);
		}
		//não precisamos correr o for brutsamente como no FormalList pois tenho metodo so para isso ali em cima
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
		
	}

	@Override
	public Object visitStatement(StatementContext ctx) {
		//para verificar como iremos montar a ast, precisamos utilizar o começo como o gatilho do statement, oq vai definir oq faremos dps, isso ta claro no antlr.g4 
		String gatilho = ctx.getStart().getText();
		
		//se começar com {, traremos um novo statement tipo recursivo.
		switch(gatilho) {
		case "if":
			Exp expIf = (Exp) ctx.expression(0).accept(this);
			Statement stateIf = (Statement) ctx.statement(0).accept(this);
			Statement stateIf2 = (Statement) ctx.statement(1).accept(this);
			If condition1 = new If(expIf,stateIf,stateIf2);
			return condition1;
		case "while":
			Exp expWhile = (Exp) ctx.expression(0).accept(this);
			Statement stateWhile = (Statement) ctx.statement(0).accept(this);
			While condition2 = new While(expWhile,stateWhile);
			return condition2;
		case "System.out.println":
			Exp expPrint = (Exp) ctx.expression(0).accept(this);
			Print printx = new Print(expPrint);
			return printx;
		case "{":
			StatementList asl = new StatementList();
			for(int i = 0 ; i < ctx.statement().size(); i++ ){
				Statement s = (Statement) ctx.statement(i).accept(this);
				asl.addElement(s);
			}
			Block b = new Block(asl);
			return b;	
		default:
			if ( ctx.expression().size() == 1) {
				//caso "=" e ";"
				Identifier idEqual = (Identifier) ctx.identifier().accept(this);
				Exp expEqual = (Exp) ctx.expression(0).accept(this);
				return new Assign(idEqual,expEqual);
			} else {
				Identifier idQua = (Identifier) ctx.identifier().accept(this);
				Exp expQua = (Exp) ctx.expression(0).accept(this);
				Exp expQua2 = (Exp) ctx.expression(1).accept(this);
				return new ArrayAssign(idQua,expQua,expQua2);
			}
		}
	}

	@Override
	public Object visitType(TypeContext ctx) {
		//reconhecer o type e retornar o devido tipo;
		String tipo = ctx.getText();
		switch(tipo) {
		case "boolean":
			return new BooleanType();
		case "int" :
			return new IntegerType();
		case "int []": 
			return new IntArrayType();
		default:
			//é um identifier segundo a grammar
			return new IdentifierType(tipo);
		}
	}

}
