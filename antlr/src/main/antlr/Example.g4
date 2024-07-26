grammar Example;

// 문법의 시작 규칙
program: statement+ EOF;

// 다양한 종류의 문장을 처리
statement
    : assignment
    | printStatement
    | conditional
    | arrayDeclaration
    ;

// 변수 할당 문장
assignment
    : ID '=' expr ';'
    ;

// 출력 문장
printStatement
    : 'print' '(' expr ')' ';'
    ;

// 조건문
conditional
    : 'if' '(' expr ')' '{' statement* '}'
    ( 'else' '{' statement* '}' )?
    ;

// 배열 선언
arrayDeclaration
    : 'int' ID '[' INT ']' '=' '[' (INT (',' INT)*)? ']' ';'
    ;

// 표현식
expr
    : ID
    | INT
    | STRING
    | '(' expr ')'
    | expr op=('*'|'/') expr
    | expr op=('+'|'-') expr
    ;

// 토큰 정의
ID          : [a-zA-Z_][a-zA-Z0-9_]*;
INT         : [0-9]+;
STRING      : '"' (~["\r\n])* '"';
WS          : [ \t\r\n]+ -> skip;
COMMENT     : '/*' .*? '*/' -> skip;
LINE_COMMENT: '//' ~[\r\n]* -> skip;
