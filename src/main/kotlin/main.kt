import java.io.File
import java.io.FileNotFoundException

private const val BLOCK_SIZE = 10
private const val ROUND = 5

fun main(args: Array<String>) {
    try {
        initMenu()
    } catch (e: FileNotFoundException) {
        println("Не удалось прочитать файл, возможно путь указан неверно")
    }
}

private fun initMenu() {
    println("Шифрование шифром перестановки, затем шифром замены и гаммированием.")
    println("Выберите режим: " +
            "\n1. Шифрование" +
            "\n   Иначе расшифрование")
    when (readLine()) {
        "1" -> {
            val file = File(getFilePathName())
            val bytes = file.readBytes()
            file.writeBytes(encrypt(bytes, ROUND))
            println("Файл зашифрован!")
        }
        else -> {
            val file = File(getFilePathName())
            val bytes = file.readBytes()
            file.writeBytes(decrypt(bytes, ROUND))
            println("Файл расшифрован!")
        }
    }
}

private fun encrypt(byteArray: ByteArray, round: Int = 1): ByteArray {
    var gamma = Gamma.DEFAULT_GAMMA
    var keyMatrix = TranspositionCipher.DEFAULT_MATRIX
    val result = ByteArray(byteArray.size)
    for (i in 0 until byteArray.size step BLOCK_SIZE) {
        var array = if (i + BLOCK_SIZE <= byteArray.size) byteArray.sliceArray(i until i + BLOCK_SIZE) else byteArray.sliceArray(i until i + byteArray.size - i)
        (0 until round).forEach {
            val step1 = TranspositionCipher.encrypt(array, keyMatrix)
            val step2 = SubstitutionCipher.encrypt(step1)
            val step3 = Gamma.encrypt(step2, gamma)
            array = step3
        }
        keyMatrix = TranspositionCipher.getNextMatrix(keyMatrix)
        gamma = Gamma.getNextGamma(gamma)
        result.set(i, array)
    }
    return result
}

private fun decrypt(byteArray: ByteArray, round: Int = 1): ByteArray {
    var gamma = Gamma.DEFAULT_GAMMA
    var keyMatrix = TranspositionCipher.DEFAULT_MATRIX
    val result = ByteArray(byteArray.size)
    for (i in 0 until byteArray.size step BLOCK_SIZE) {
        var array = if (i + BLOCK_SIZE <= byteArray.size) byteArray.sliceArray(i until i + BLOCK_SIZE) else byteArray.sliceArray(i until i + byteArray.size - i)
        (0 until round).forEach {
            val step1 = Gamma.decrypt(array, gamma)
            val step2 = SubstitutionCipher.decrypt(step1)
            val step3 = TranspositionCipher.decrypt(step2, keyMatrix)
            array = step3
        }
        gamma = Gamma.getNextGamma(gamma)
        keyMatrix = TranspositionCipher.getNextMatrix(keyMatrix)
        result.set(i, array)
    }
    return result
}

private fun getFilePathName(): String {
//    println("Полный путь к файлу: ")
//    var string:String? = null
//    do {
//        string = readLine()
//    } while (string == null)
    return "C:\\Users\\vlady\\Desktop\\logoOmsu.jpg"
}