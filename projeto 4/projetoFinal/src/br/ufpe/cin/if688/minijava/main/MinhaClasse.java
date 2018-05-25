package br.ufpe.cin.if688.minijava.main;

import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;

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
		return null;
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
		return null;
	}

	@Override
	public Object visitGoal(GoalContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visitExpression(ExpressionContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visitMethoddeclaration(MethoddeclarationContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visitStatement(StatementContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visitType(TypeContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

}
