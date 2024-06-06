package lateinit_lazy

class Person {
    lateinit var name: String
    // lateinit var age: int // Primitive 타입은 사용하지 못함, nullable 변수로 변환이 되어야 하기 때문이다

    val isKim: Boolean
        get() = this.name.startsWith("김")

    val maskingName: String
        get() = name[0] + (1..<name.length).joinToString("") { "*" }
}

class PersonLazy {
    val name: String
//        get() {
//            Thread.sleep(2_000)
//            return "김수한무"
//        }

    init {
        Thread.sleep(2_000)
        name = "김수한무"
    }
}

class PersonLazy2 {
//    private var _name: String? = null
//    val name: String
//        get() {
//            // backing property 방식, _를 붙이는 게 관례이다
//            if (_name == null) {
//                Thread.sleep(2_000)
//                this._name = "김수한무"
//            }
//            return this._name!!
//        }

    // by lazy - 코틀린에서 지원하는 backing property
    val name: String by lazy {
        Thread.sleep(2_000)
        "김수한무"
    }
}
