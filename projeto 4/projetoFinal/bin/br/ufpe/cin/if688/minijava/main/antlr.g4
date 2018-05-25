grammar antlr;

goal: mainclass ( classdeclaration )* EOF;

mainclass : 'class' identifier '{' 'public' 'static' 'void' 'main' '(' 'String' '[' ']' identifier ')' '{' statement '}' '}';

classdeclaration: 'class' identifier ( 'extends' identifier )? '{' ( vardeclaration )* ( methoddeclaration )* '}';

vardeclaration : type identifier ';';

methoddeclaration: 'public' type identifier '(' ( type identifier ( ',' type identifier )* )? ')' '{' ( vardeclaration )* ( statement )* 'return' expression ';' '}';

type: 'int' '[' ']'
			| 'boolean' 
			|'int'
			| identifier;
statement : '{' ( statement )* '}'
			| 'if' '(' expression ')' statement 'else' statement
			| 'while' '(' expression ')' statement
			| 'System.out.println' '(' expression ')' ';'
			| identifier '=' expression ';'
			| identifier '[' expression ']' '=' expression ';';
expression: expression ( '&&' | '<' | '+' | '-' | '*' ) expression
			|	expression '[' expression ']'
			|	expression '.' 'length'
			|	expression '.' identifier '(' ( expression ( ',' expression )* )? ')'
			|	INTEGER_LITERAL
			|	'true'
			|	'false'
			|	identifier
			|	'this'
			|	'new' 'int' '[' expression ']'
			|	'new' identifier '(' ')'
			|	'!' expression
			|	'(' expression ')';
identifier: IDENTIFIER;

INTEGER_LITERAL: [1-9][0-9]* | '0';
IDENTIFIER: [_a-zA-Z$][a-zA-Z0-9$_]* ; 
WHITESPACE: [ \t\r\n]+ -> skip; 
COMMENTARIO: '//' ~[\r\n]* -> skip ; 
COMMENTARIO_MULTI: '/*' .*? '*/' -> skip;

