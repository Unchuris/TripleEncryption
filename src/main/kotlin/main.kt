import java.io.File
import java.io.FileNotFoundException
import java.util.Random
import kotlin.experimental.xor

val key = byteArrayOf(
        15, 3, 25, 14, 13, 10, 9, 8, 5, 0, 7, 1, 4, 6, 12, 11, 15, 3, 2, 14, 13, 10, 9, 8, 5, 0, 7, 1, 4, 6, 12, 11, 22, 12, //Y
        5, 7, 6, 1, 4, 2, 0, 3,
        5, 9, 7, 0, 4, 2, 8, 3, 1, 6,
        3, 8, 4, 5, 1, 0, 9, 6, 7, 2,
        7,
        9
)

private const val BLOCK_SIZE = 10
private const val ROUND = 5

fun main(args: Array<String>) {
    try {
        println("${key.size * 8} бит")
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
            val bytes = addByte(file.readBytes())
            file.writeBytes(encrypt(bytes, ROUND))
            println("Файл зашифрован!")
        }
        else -> {
            val file = File(getFilePathName())
            val bytes = file.readBytes()
            file.writeBytes(removeByte(decrypt(bytes, ROUND)))
            println("Файл расшифрован!")
        }
    }
}

private fun encrypt(byteArray: ByteArray, round: Int = 1): ByteArray {
    var gamma = key.sliceArray(0 until 34)
    var keyMatrix = key.sliceArray(42 until 42 + BLOCK_SIZE)
    val result = ByteArray(byteArray.size)
    for (i in 0 until byteArray.size step BLOCK_SIZE) {
        var array = if (i + BLOCK_SIZE <= byteArray.size) byteArray.sliceArray(i until i + BLOCK_SIZE) else byteArray.sliceArray(i until i + byteArray.size - i)
        (0 until round).forEach {
            val step1 = TranspositionCipher.encrypt(array, keyMatrix)
            val step2 = SubstitutionCipher.encrypt(step1)
            val step3 = Gamma.encrypt(step2, gamma)
            array = step3
        }
        keyMatrix = TranspositionCipher.getNextMatrix(keyMatrix.toMatrix()).toTransposition()
        gamma = Gamma.getNextGamma(gamma)
        result.set(i, array)
    }
    return result
}

private fun decrypt(byteArray: ByteArray, round: Int = 1): ByteArray {
    var gamma = key.sliceArray(0 until 34)
    var keyMatrix = key.sliceArray(42 until 42 + BLOCK_SIZE)
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
        keyMatrix = TranspositionCipher.getNextMatrix(keyMatrix.toMatrix()).toTransposition()
        result.set(i, array)
    }
    return result
}

private fun addByte(byteArray: ByteArray): ByteArray {
    val s = byteArray.size % BLOCK_SIZE
    var spaceSize = if (s != 0) BLOCK_SIZE - s else s
    if (byteArray.isEmpty()) {
        spaceSize = 10
    }
    return byteArray.copyOf(byteArray.size + spaceSize).apply {
        if (spaceSize != 0) {
            this[size - 1] = spaceSize.toByte() xor 111
            val byteRandom = ByteArray(spaceSize - 1)
            Random().nextBytes(byteRandom)
            byteRandom.forEachIndexed { index, byte ->
                this[size - index - 2] = byte
            }
        }
    }
}

private fun removeByte(byteArray: ByteArray): ByteArray {
    if (byteArray.isEmpty()) return byteArray
    val spaceSize = byteArray[byteArray.size - 1] xor 111
    return if (spaceSize > - 1 && spaceSize < 11 && byteArray.size - spaceSize > -1) byteArray.copyOf(byteArray.size - spaceSize) else byteArray
}

private fun getFilePathName(): String {
//    println("Полный путь к файлу: ")
//    var string:String? = null
//    do {
//        string = readLine()
//    } while (string == null)
    //return "C:\\Users\\vlady\\Desktop\\test33.txt"
    return "C:\\Users\\vlady\\Desktop\\logoOmsu.jpg"
}