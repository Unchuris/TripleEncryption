import kotlin.experimental.xor

class Gamma {

    companion object {

        private val defaultGammaMatrix = key.sliceArray(0 until 34)
        private val gammaTransposition = key.sliceArray(34 until 34 + 8)

        fun encrypt(text: ByteArray, gamma: ByteArray): ByteArray {
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
