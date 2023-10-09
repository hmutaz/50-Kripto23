fun main() {
    var active = true
    var menu: Int
    while (active) {
        print("\n1. Cipher\n2. Decipher\nInput: ")
        menu = readLine()!!.toInt()
        when (menu) {
            1 -> {
                print("Plaintext: ")
                val plaintext: String = readLine()!!
                print("Key: ")
                val key: String = readLine()!!
                cipher(plaintext, key)
            }
            2 -> {
                print("Ciphertext: ")
                val ciphertext: String = readLine()!!
                print("Key: ")
                val key: String = readLine()!!
                decipher(ciphertext, key)
            }
            else -> {
                active = false
                print("Exiting...")
            }
        }
    }
}

fun cipher(text: String, key: String){
    for (i in 0..(text.length-1)) {
        when {
            text[i].code < 91 && text[i].code > 64 -> {
                var shift: Int = (text[i].code - 65 + key.uppercase()[i % key.length].code - 65) % 26
                print((shift + 65).toChar())
            }
            text[i].code > 96 && text[i]. code < 123 -> {
                var shift: Int = (text[i].code - 97 + key.uppercase()[i % key.length].code - 65) % 26
                print((shift + 97).toChar())
            }
            else -> print(text[i])
        }
    }
}

fun decipher(text: String, key: String){
    for (i in 0..(text.length-1)) {
        when {
            text[i].code < 91 && text[i].code > 64 -> {
                var shift: Int = Math.floorMod((text[i].code - 65 - key.uppercase()[i % key.length].code + 65), 26)
                print((shift + 65).toChar())
            }
            text[i].code > 96 && text[i]. code < 123 -> {
                var shift: Int = Math.floorMod((text[i].code - 97 - key.uppercase()[i % key.length].code + 65), 26)
                print((shift + 97).toChar())
            }
            else -> print(text[i])
        }
    }
}