package introduction

fun main() {
    var neverNull: String = "This can't be null"
    // neverNull = null -> error

    var nullable: String? = "You can keep a null here"
    nullable = null // done

    var inferredNonNull = "The compiler assumes non-null"
    // inferredNonNull = null -> error

    fun strLength(notNull: String): Int {
        return notNull.length
    }
    strLength(neverNull)
    // strLength(nullable) -> error

    fun describeString(maybeString: String?): String {
        if (maybeString != null && maybeString.length > 0) {
            return "String of length ${maybeString.length}"
        } else {
            return "Empty or null string"
        }
    }
}