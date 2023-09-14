fun main() {
    print("Text: ")
    var text: String = readLine()!!
    print("Key: ")
    var shifter: Int = readLine()!!.toInt()
    shiftCipher(text, shifter)
    // shiftDecipher(text, shifter)
}

fun shiftCipher(text: String, shiftValue: Int) {
    text.forEach { value ->
        when {
            value.code < 91 -> {
                var shift: Int = (value.code - 64 + shiftValue) % 26
                print((shift + 64).toChar())
            }
            value.code > 96 -> {
                var shift: Int = (value.code - 96 + shiftValue) % 26
                print((shift + 96).toChar())
            }
        }
    }
}

fun shiftDecipher(text: String, shiftValue: Int) {
    text.forEach { value ->
        when {
            value.code < 91 -> {
                var shift: Int = (value.code - 64 - shiftValue) % 26
                print((shift + 64).toChar())
            }
            value.code > 96 -> {
                var shift: Int = (value.code - 96 - shiftValue) % 26
                print((shift + 96).toChar())
            }
        }
    }
}