import KeyParser.Companion.BLOCK_SIZE
import java.io.File
import java.io.FileNotFoundException
import java.util.*
import kotlin.experimental.xor

const val ROUND = 7

private const val FILE_NAME = "C:\\Users\\vlady\\Desktop\\test33.txt"

private const val KEY_FILE_NAME = "C:\\Users\\vlady\\Desktop\\key1.txt"

fun main(args: Array<String>) {
    try {
        //generateKey()
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
            val key = File(getKeyFilePathName()).readBytes()
            if (key.size != 64) {
                println("Ошибка, длина ключа должна быть 512 бит")
                return
            }
            val file = File(getFilePathName())
            val bytes = addByte(file.readBytes())
            file.writeBytes(encrypt(bytes, ROUND, KeyParser(key)))
            println("Файл зашифрован!")
        }
        else -> {
            val key = File(getKeyFilePathName()).readBytes()
            if (key.size != 64) {
                println("Ошибка, длина ключа должна быть 512 бит")
                return
            }
            val file = File(getFilePathName())
            val bytes = file.readBytes()
            file.writeBytes(removeByte(decrypt(bytes, ROUND, KeyParser(key))))
            println("Файл расшифрован!")
        }
    }
}

private fun addByte(byteArray: ByteArray): ByteArray {
    val s = byteArray.size % BLOCK_SIZE
    var spaceSize = if (s != 0) BLOCK_SIZE - s else s
    if (byteArray.isEmpty()) {
        spaceSize = BLOCK_SIZE
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
    return if (spaceSize > -1 && spaceSize < (BLOCK_SIZE + 1) && byteArray.size - spaceSize > -1) byteArray.copyOf(byteArray.size - spaceSize) else byteArray
}

private fun getKeyFilePathName(): String {
    return KEY_FILE_NAME
}

private fun getFilePathName(): String {
    return FILE_NAME
}

private fun generateKey() {
    val byteRandom = ByteArray(64)
    Random().nextBytes(byteRandom)
    val fileName = "C:\\Users\\vlady\\Desktop\\key.txt"
    File(fileName).writeBytes(byteRandom)
}

private fun encrypt(byteArray: ByteArray, round: Int = 1, keyParser: KeyParser) =
        CustomCipher.encrypt(
                byteArray,
                round,
                keyParser.transpositionCipher,
                keyParser.substitutionCipher,
                keyParser.gammaCipher
        )


private fun decrypt(byteArray: ByteArray, round: Int = 1, keyParser: KeyParser) =
        CustomCipher.decrypt(
                byteArray,
                round,
                keyParser.transpositionCipher,
                keyParser.substitutionCipher,
                keyParser.gammaCipher
        )