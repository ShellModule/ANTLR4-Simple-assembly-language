grammar Lab1;

fragment A: 'A' | 'a';
fragment B: 'B' | 'b';
fragment C: 'C' | 'c';
fragment D: 'D' | 'd';
fragment E: 'E' | 'e';
fragment H: 'H' | 'h';
fragment I: 'I' | 'i';
fragment M: 'M' | 'm';
fragment N: 'N' | 'n';
fragment O: 'O' | 'o';
fragment P: 'P' | 'p';
fragment R: 'R' | 'r';
fragment S: 'S' | 's';
fragment T: 'T' | 't';
fragment U: 'U' | 'u';
fragment V: 'V' | 'v';
fragment X: 'X' | 'x';

REGISTER: ('%'E A X | '%'E B X | '%'E C X | '%'E D X);

MOV: M O V ' ';
PUSH: P U S H ' ';
INT: I N T ' ' '0x80';
XOR: X O R ' ';

ADD: '+';
SUB: '-';
MUL: '*';
DIV: '/';
LBRA: '(';
RBRA: ')';
NUMBER: [0-9]+;
WHITESPACE: [ \t]+ -> skip;
NEWLINE: '\r'? '\n' ;

start
    : instructions NEWLINE+
    | NEWLINE+
    ;

instructions
    : intCommand                                       #Int
    | pushCommand                                      #Push
    | xorCommand                                       #Xor
    | movCommand                                       #Mov
    | additiveExpression                               #Expression
    ;

movCommand
    : MOV additiveExpression ',' registers
    ;

pushCommand
    : PUSH additiveExpression
    ;

xorCommand
    : XOR additiveExpression ',' registers
    ;

intCommand
    : INT
    ;

additiveExpression
    : multiplicativeExpression                         #MultiplicativeExp
    | additiveExpression ADD multiplicativeExpression  #Add
    | additiveExpression SUB multiplicativeExpression  #Subtract
    ;

multiplicativeExpression
    : bracketExpression                                #BracketExp
    | multiplicativeExpression MUL bracketExpression   #Multiply
    | multiplicativeExpression DIV bracketExpression   #Divide
    ;

bracketExpression
    : numericValue                                     #NumericVal
    | registers                                        #ExpRegister
    | LBRA additiveExpression RBRA                     #BRexp
    ;

numericValue
    : NUMBER                                           #Number
    ;

registers
    : REGISTER                                         #Register
    ;