import org.junit.Assert.assertArrayEquals
import org.junit.Test


class GammaTest {

    private var gamma = key.sliceArray(0 until 34)

    @Test
    fun testCipher() {
        val byteArray = byteArrayOf(2, 42, 127, 23, 94, 22, 34, 4, 5, 43, 23, 21, 23, 54, 23, 99)
        val rez = Gamma.decrypt(Gamma.encrypt(byteArray, gamma), gamma)
        assertArrayEquals(byteArray, rez)
    }

    @Test
    fun testCipherCycle() {
        val byteArray = byteArrayOf(
                2, 42, 127, 23, 94, 22, 34, 4, 5, 43, 32, 23, 21, 35, 67, 99,
                75, 23, 45, 65, 24, 12, 68, 42, 12, 10, 23, 94, 22, 34, 4, 77,
                87, 31, 121, 2, 11, 111, 32, 98, 27, 64, 34, 4, 5, 67, 99, 21)
        val result = ByteArray(byteArray.size)
        val resultDecrypt = ByteArray(byteArray.size)
        for (i in 0 until byteArray.size step 16) {
            val array = byteArray.sliceArray(i until i + 16)
            result.set(i, Gamma.encrypt(array, gamma))
            gamma = Gamma.getNextGamma(gamma)
        }
        gamma = key.sliceArray(0 until 34)
        for (i in 0 until result.size step 16) {
            val array = result.sliceArray(i until i + 16)
            resultDecrypt.set(i, Gamma.decrypt(array, gamma))
            gamma = Gamma.getNextGamma(gamma)
        }
        assertArrayEquals(byteArray, resultDecrypt)
    }
}