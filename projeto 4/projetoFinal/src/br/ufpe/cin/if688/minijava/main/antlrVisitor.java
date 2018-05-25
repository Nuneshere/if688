// Generated from antlr.g4 by ANTLR 4.4
package br.ufpe.cin.if688.minijava.main;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link antlrParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface antlrVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link antlrParser#vardeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVardeclaration(@NotNull antlrParser.VardeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link antlrParser#mainclass}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMainclass(@NotNull antlrParser.MainclassContext ctx);
	/**
	 * Visit a parse tree produced by {@link antlrParser#classdeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassdeclaration(@NotNull antlrParser.ClassdeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link antlrParser#identifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentifier(@NotNull antlrParser.IdentifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link antlrParser#goal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGoal(@NotNull antlrParser.GoalContext ctx);
	/**
	 * Visit a parse tree produced by {@link antlrParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(@NotNull antlrParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link antlrParser#methoddeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethoddeclaration(@NotNull antlrParser.MethoddeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link antlrParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(@NotNull antlrParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link antlrParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(@NotNull antlrParser.TypeContext ctx);
}