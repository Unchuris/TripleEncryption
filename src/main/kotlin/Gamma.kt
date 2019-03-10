import kotlin.experimental.xor

class Gamma {

    companion object {
        val DEFAULT_GAMMA = byteArrayOf(15, 3, 2, 14, 13, 10, 9, 8, 5, 0, 7, 1, 4, 6, 12, 11)

        private val defaultGammaMatrix: Matrix = arrayOf(
                booleanArrayOf(true, false, false, false, false, false, false, false),
                booleanArrayOf(false, false, false, false, true, false, false, false),
                booleanArrayOf(false, false, false, false, false, true, false, false),
                booleanArrayOf(false, true, false, false, false, false, false, false),
                booleanArrayOf(false, false, false, false, false, false, true, false),
                booleanArrayOf(false, false, true, false, false, false, false, false),
                booleanArrayOf(false, false, false, false, false, false, false, true),
                booleanArrayOf(false, false, false, true, false, false, false, true)
        )
        private val gammaTransposition = defaultGammaMatrix.toTransposition()

        fun encrypt(text: ByteArray, gamma: ByteArray = DEFAULT_GAMMA): ByteArray {
            val result = ByteArray(text.size)
            for (i in text.indices) {
                result[i] = (text[i] xor gamma[i % gamma.size])
            }
            return result
        }

        fun decrypt(text: ByteArray, gamma: ByteArray) = encrypt(text, gamma)

        fun getNextGamma(gamma: ByteArray): ByteArray {
            val newGamma = ByteArray(gamma.size)
            gamma.forEachIndexed { index, byte ->
                newGamma[index] = byte.toBooleanArray().transposition(gammaTransposition).toByte()
            }
            return newGamma
        }
    }
}
