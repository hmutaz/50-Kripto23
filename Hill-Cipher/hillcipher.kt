private const val N = 3 //Dimensi matriks kunci

fun main() {
    println("Enter key:")
    //Inisiasi kunci enkripsi
    var key = Array(3) {IntArray(3) {0}}
    key = insertMatrix(key)

    //Cek apakah kunci dekripsi mungkin ditemukan
    if (determinant(key, 3) == 0 || gcd(determinant(key, 3), 26) != 1) {
        println("Coba kunci lain!")
    } else {
        //Inisiasi kunci dekripsi
        var dKey = inverseMatrix(key)
        printMatrix(dKey)
    
        //group adalah jumlah bagian enkripsi yang dilakukan dan groupArray adalah objek arraynya
        var input: Int;
        var group: Int;
        var groupArray = Array(3) {IntArray(1) {0}};
        var menuLoop = true;

        //Looping menu program
        while (menuLoop){
            println("\nKey:")
            printMatrix(key)
            print("1. Encrypt\n2. Decrypt\n3. Find key\nInput: ")
            input = readLine()!!.toInt()
            when (input) {

                //Opsi untuk enkripsi
                1 -> {
                    print("\nPlaintext: ")
                    val plaintext = readLine()!!

                    //Bagi panjang text menjadi 3
                    group = Math.ceil((plaintext.length.toDouble())/3).toInt()
                    print("Ciphertext: ")
                    for (i in 0 until group) {

                        //Manipulasi ASCII (A = 65)
                        groupArray[0][0] = plaintext[i*3].code - 65
                        groupArray[1][0] = if (i*3 + 1 < plaintext.length) plaintext[i*3 + 1].code - 65 else 0
                        groupArray[2][0] = if (i*3 + 2 < plaintext.length) plaintext[i*3 + 2].code - 65 else 0
    
                        //Enkripsi dengan key
                        groupArray = mulMat(key, groupArray, 3, 3, 1)
                        for (j in 0..2) {
                            print((groupArray[j][0] + 65).toChar())
                        }
                    }
                    println()
                }

                //Opsi untuk dekripsi
                2 -> {
                    print("\nCiphertext: ")
                    val ciphertext = readLine()!!

                    //Bagi panjang text menjadi 3
                    group = Math.ceil((ciphertext.length.toDouble())/3).toInt()
                    print("Plaintext: ")
                    for (i in 0 until group) {

                        //Manipulasi ASCII (A = 65)
                        groupArray[0][0] = ciphertext[i*3].code - 65
                        groupArray[1][0] = if (i*3 + 1 < ciphertext.length) ciphertext[i*3 + 1].code - 65 else 0
                        groupArray[2][0] = if (i*3 + 2 < ciphertext.length) ciphertext[i*3 + 2].code - 65 else 0
    
                        //Dekripsi dengan dKey
                        groupArray = mulMat(dKey, groupArray, 3, 3, 1)
                        for (j in 0..2) {
                            print((groupArray[j][0] + 65).toChar())
                        }
                    }
                    println()
                }

                //Opsi untuk mencari matriks kunci
                3 -> {
                    print("\nPlaintext: ")
                    val p = readLine()!!
                    if (p.length < 9) {
                        println("Kunci yang bisa dicari butuh 9 karakter!")
                    } else {
                        var pArray = arrayOf(
                            intArrayOf(p[0].code - 65, p[3].code - 65, p[6].code - 65),
                            intArrayOf(p[1].code - 65, p[4].code - 65, p[7].code - 65),
                            intArrayOf(p[2].code - 65, p[5].code - 65, p[8].code - 65),
                        )
                        if (determinant(pArray, 3) == 0 || gcd(determinant(pArray, 3), 26) != 1) {
                            println("Determinan matriks = ${determinant(pArray, 3)}")
                            println("Invers matriks plaintext tidak dapat dihitung, gunakan teks lain!")
                        } else {
                            print("Ciphertext: ")
                            val c = readLine()!!

                            if (p.length != c.length) {
                                println("Panjang teks tidak sama!")
                            } else {
                                pArray = inverseMatrix(pArray)
                                var cArray = arrayOf(
                                    intArrayOf(c[0].code - 65, c[3].code - 65, c[6].code - 65),
                                    intArrayOf(c[1].code - 65, c[4].code - 65, c[7].code - 65),
                                    intArrayOf(c[2].code - 65, c[5].code - 65, c[8].code - 65),
                                )
                                val newKey = mulMat(cArray, pArray, 3, 3, 3)
                                println("Key:")
                                printMatrix(newKey)
                            }
                        }
                    }
                }
                else -> menuLoop = false
            }
        }
    }
}

//Fungsi insert matriks
fun insertMatrix(matrix: Array<IntArray>): Array<IntArray> {
    for (i in 0..2) {
        for (j in 0..2){
            print("[${i+1}, ${j+1}] = ")
            matrix[i][j] = readLine()!!.toInt()
        } 
    }
    return matrix
}

//Fungsi print matriks
fun printMatrix(matrix: Array<IntArray>) {
    for (i in 0 until matrix.size) {
        for (j in 0 until matrix[0].size){
            print("${matrix[i][j]} ")
        } 
        println()
    }
}

//Fungsi perkalian matriks umum, rA = row A, rB = row B, cB = column B
fun mulMat(matrixA: Array<IntArray>, matrixB: Array<IntArray>, rA: Int, rB: Int, cB: Int): Array<IntArray> {
    var result = Array(rA) {IntArray(cB) {0}}
    for (i in 0 until rA) {
        for (j in 0 until cB) {
            for (k in 0 until rB)
                result[i][j] += matrixA[i][k] * matrixB[k][j]
            result[i][j] = mod(result[i][j])
        }
    }
    return result
}

//Fungsi untuk mencari Kofaktor
fun getCofactor(matrix: Array<IntArray>, p: Int, q: Int, n: Int): Array<IntArray>{
    var temp = Array(n - 1) {IntArray(n-1) {0}}
    var i = 0
    var j = 0

    for (row in 0 until n) {
        for (col in 0 until n) {
            if (row != p && col != q) {
                temp[i][j++] = matrix[row][col]

                if (j == n - 1) {
                    j = 0
                    i++
                }
            }
        }
    }

    return temp
}

//Fungsi untuk mencari determinan suatu matriks
fun determinant(matrix: Array<IntArray>, n: Int): Int {
    var d = 0

    if (n == 1) return matrix[0][0]

    var temp: Array<IntArray>
    var sign = 1

    for (f in 0 until n) {
        temp = getCofactor(matrix, 0, f, n)
        d += sign * matrix[0][f] * determinant(temp, n - 1)
        sign = -sign
    }

    return mod(d)
}

//Fungsi untuk mendapatkan adjoint matriks
fun adjoint(matrix: Array<IntArray>): Array<IntArray> {
    var adj = Array(N) {IntArray(N) {0}}

    if (N == 1) {
        adj[0][0] = 1
        return adj
    }

    var temp: Array<IntArray>
    var sign: Int

    for (i in 0 until N) {
        for (j in 0 until N) {
            temp = getCofactor(matrix, i, j, N)
            sign = if ((i + j) % 2 == 0) 1 else -1
            adj[j][i] = sign * determinant(temp, N - 1)
        }
    }

    return adj
}

//Fungsi invers matriks
fun inverseMatrix(matrix: Array<IntArray>): Array<IntArray> {
    var inverse = Array(3) {IntArray(3) {0}}
    val det = modInverse(determinant(matrix, N), 26)

    val adj = adjoint(matrix)

    for (i in 0 until N) {
        for (j in 0 until N) {
            inverse[i][j] = mod(adj[i][j] * det)
        }
    }

    return inverse
}

//Fungsi untuk memanggil dari invers mod 26
fun modInverse(a: Int, m: Int): Int {
    for (x in 0 until m)
        if (((a % m) * (x % m)) % m == 1)
            return x
    return 1
}

//Fungsi untuk pemanggilan cepat hasil mod 26
fun mod(a: Int): Int {
    var result = a % 26
    while (result < 0) {
        result += 26
    }
    return result
}

//Fungsi FPB
fun gcd(x: Int, y: Int): Int{
    var n1 = x
    var n2 = y
    while (n1 != n2) {
        if (n1 > n2)
            n1 -= n2
        else
            n2 -= n1
    }
    return n1
}