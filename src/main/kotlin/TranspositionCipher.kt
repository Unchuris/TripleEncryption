import java.util.*

class TranspositionCipher(private val p0: Matrix, private val pt: Matrix) {

    private var index = 0

    private val arrayKeys = initKeys()

    fun encrypt(text: ByteArray) = action(text, true)

    fun decrypt(text: ByteArray) = action(text, false)

    private fun action(byteArray: ByteArray, encrypt: Boolean): ByteArray {
        val result = ByteArray(byteArray.size)
        val transposition = (if (encrypt) arrayKeys[index] else arrayKeys[arrayKeys.size - index - 1]).toTransposition()
        val blockSize = transposition.size

        for (i in 0 until byteArray.size step blockSize) {
            val untilSize = i + if (i + blockSize <= byteArray.size) blockSize else byteArray.size - i

            val array = if (encrypt) {
                byteArray.transposition(transposition)
            } else {
                byteArray.transpositionReverse(transposition)
            }

            byteArray.sliceArray(i until untilSize).also {
                result.set(i, if (it.size != blockSize) it else array)
            }
        }

        index++

        return result
    }

    private fun initKeys(): ArrayList<Matrix> {
        val result = ArrayList<Matrix>()
        var matrix = p0
        (0 until ROUND).forEach {
            matrix = pt * matrix
            result.add(matrix)
        }
        return result
    }
}
