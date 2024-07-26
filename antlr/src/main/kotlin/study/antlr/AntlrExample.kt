package study.antlr

import ExampleBaseListener
import ExampleLexer
import ExampleParser
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.tree.ParseTree


class AntlrExample {

}

fun main() {
    val inputExamples =
        listOf(
            // 변수 할당
            "x = 42;",
            // 출력 문장
            "print(\"Hello World\");",
            // 조건문
            """
            if (x > 10) {
                print("x is greater than 10");
            } else {
                print("x is 10 or less");
            }
            """,
            // 배열 선언 및 초기화
            "int arr[5] = [1, 2, 3, 4, 5];",
            // 복잡한 표현식
            "y = (x + 5) * (3 - 1) / 2;",
            // 문자열 변수와 조건문
            """
            string greeting = "Hello";
            if (greeting == "Hello") {
                print(greeting);
            }
            """,
            // 잘못된 입력 (문법 오류가 있을 경우)
            """
            int num[3] = [1, 2;  // 배열 선언이 잘못됨
            """,
        )

    inputExamples.forEach { input ->
        println("Testing input:\n$input\n")
        try {
            val inputStream = CharStreams.fromString(input)
            val lexer = ExampleLexer(inputStream)
            val tokens = CommonTokenStream(lexer)
            val parser = ExampleParser(tokens)
            val tree: ParseTree = parser.program() // 'program'은 시작 규칙입니다
            println("Parse tree: ${tree.toStringTree(parser)}\n")
        } catch (e: Exception) {
            println("Error: ${e.message}\n")
        }
    }

    val stream = CharStreams.fromString("he")

    // Lexer와 Parser를 생성
    val lexer = ExampleLexer(stream)
    val tokens = CommonTokenStream(lexer)
    val parser = ExampleParser(tokens)

    // 파서의 시작 규칙을 호출하여 ParseTree를 생성
    val tree: ParseTree = parser.program() // 'program'은 문법의 시작 규칙입니다

    // ParseTree를 출력
    println("Result: ${tree.toStringTree(parser)}")

    // ParseTree를 탐색하면서 노드 처리하기 (선택적)
    // Traverse tree
    val walker = org.antlr.v4.runtime.tree.ParseTreeWalker()
    walker.walk(MyCustomListener(), tree) // MyCustomListener는 ParseTreeListener를 구현한 클래스
}

// ParseTreeListener를 구현하여 트리를 탐색하는 클래스 (선택적)
class MyCustomListener : ExampleBaseListener() {
    override fun enterAssignment(ctx: ExampleParser.AssignmentContext) {
        println("Entering rule r with context: ${ctx.text}")
    }

    override fun exitAssignment(ctx: ExampleParser.AssignmentContext) {
        println("Exiting rule r with context: ${ctx.text}")
    }
}
