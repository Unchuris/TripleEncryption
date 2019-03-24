import java.util.ArrayList
import kotlin.experimental.xor

class Gamma(
        private val gamma: ByteArray,
        private val gammaTransposition: ByteArray) {

    private var index = 0

    private val gammaArray = initGammaArray()

    fun encrypt(text: ByteArray) = action(text, gammaArray[index])

    fun decrypt(text: ByteArray) =
            action(text, gammaArray[gammaArray.size - 1 - index])

    private fun action(text: ByteArray, currentGamma: ByteArray): ByteArray {
        val result = ByteArray(text.size)
        text.forEachIndexed { index, byte ->
            result[index] = (byte xor currentGamma[index % currentGamma.size])
        }
        index++
        return result
    }

    private fun getNextGamma(currentGamma: ByteArray): ByteArray {
        val newGamma = ByteArray(currentGamma.size)
        currentGamma.forEachIndexed { index, byte ->
            newGamma[index] = byte.toBooleanArray().transposition(gammaTransposition).toByte()
        }
        return newGamma
    }

    private fun initGammaArray(): List<ByteArray> {
        val result = ArrayList<ByteArray>()
        var gamma = gamma
        (0 until ROUND).forEach {
            result.add(gamma)
            gamma = getNextGamma(gamma)
        }
        return result
    }
}
