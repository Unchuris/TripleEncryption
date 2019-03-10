import org.junit.Assert
import org.junit.Test

class MainTest {

    private var keyMatrix = TranspositionCipher.DEFAULT_MATRIX

    private val BLOCK_SIZE = 10

    @Test
    fun testCipher() {
        val byteArray = byteArrayOf(
                2, 42, 127, 23, 94, 22, 34, 4, 5, 43,
                75, 23, 45, 65, 24, 12, 68, 42, 12, 10,
                87, 31, 121, 2, 11, 111, 32, 98, 27, 64)

        val result = ByteArray(byteArray.size)
        val resultDecrypt = ByteArray(byteArray.size)
        for (i in 0 until byteArray.size step BLOCK_SIZE) {
            val array = byteArray.sliceArray(i until i + BLOCK_SIZE)
            val step1 = TranspositionCipher.encrypt(array, keyMatrix)
            val step2 = SubstitutionCipher.encrypt(step1)
            result.set(i, step2)
            keyMatrix = TranspositionCipher.getNextMatrix(keyMatrix)
        }

        keyMatrix = TranspositionCipher.DEFAULT_MATRIX
        for (i in 0 until result.size step BLOCK_SIZE) {
            val array = result.sliceArray(i until i + BLOCK_SIZE)
            val step1 = SubstitutionCipher.decrypt(array)
            val step2 = TranspositionCipher.decrypt(step1, keyMatrix)
            resultDecrypt.set(i, step2)
            keyMatrix = TranspositionCipher.getNextMatrix(keyMatrix)
        }
        Assert.assertArrayEquals(byteArray, resultDecrypt)
    }
}