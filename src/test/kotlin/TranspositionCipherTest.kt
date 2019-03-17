//import org.junit.Assert
//import org.junit.Test
//
//class TranspositionCipherTest {
//
//    private var keyMatrix = TranspositionCipher.DEFAULT_MATRIX
//
//    @Test
//    fun testCipher() {
//        val byteArray = byteArrayOf(2, 42, 127, 23, 94, 22, 34, 4, 5, 43)
//        val rez = TranspositionCipher.decrypt(TranspositionCipher.encrypt(byteArray, keyMatrix), keyMatrix)
//        Assert.assertArrayEquals(byteArray, rez)
//    }
//
//    @Test
//    fun testCipherCycle() {
//        val byteArray = byteArrayOf(
//                2, 42, 127, 23, 94, 22, 34, 4, 5, 43,
//                75, 23, 45, 65, 24, 12, 68, 42, 12, 10,
//                87, 31, 121, 2, 11, 111, 32, 98, 27, 64)
//        val result = ByteArray(byteArray.size)
//        val resultDecrypt = ByteArray(byteArray.size)
//        for (i in 0 until byteArray.size step 10) {
//            val array = byteArray.sliceArray(i..i + 9)
//            result.set(i, TranspositionCipher.encrypt(array, keyMatrix))
//            keyMatrix = TranspositionCipher.getNextMatrix(keyMatrix)
//        }
//        keyMatrix = TranspositionCipher.DEFAULT_MATRIX
//        for (i in 0 until result.size step 10) {
//            val array = result.sliceArray(i..i + 9)
//            resultDecrypt.set(i, TranspositionCipher.decrypt(array, keyMatrix))
//            keyMatrix = TranspositionCipher.getNextMatrix(keyMatrix)
//        }
//        Assert.assertArrayEquals(byteArray, resultDecrypt)
//    }
//
//    @Test
//    fun testTimesMatrix() {
//        val matrix1: Matrix = arrayOf(
//                booleanArrayOf(true, false),
//                booleanArrayOf(false, true)
//        )
//
//        val matrix2: Matrix = arrayOf(
//                booleanArrayOf(false, true),
//                booleanArrayOf(true, false)
//        )
//        Assert.assertArrayEquals(matrix2, matrix1 * matrix2)
//    }
//
//    @Test
//    fun testTimesMatrix2() {
//        val matrix1: Matrix = arrayOf(
//                booleanArrayOf(true, false, false, false),
//                booleanArrayOf(false, false, true, false),
//                booleanArrayOf(true, false, false, false),
//                booleanArrayOf(false, false, false, true)
//        )
//
//        val matrix2: Matrix = arrayOf(
//                booleanArrayOf(false, false, true, false),
//                booleanArrayOf(false, true, false, false),
//                booleanArrayOf(true, false, false, false),
//                booleanArrayOf(false, false, false, true)
//        )
//
//        val matrix3: Matrix = arrayOf(
//                booleanArrayOf(false, false, true, false),
//                booleanArrayOf(true, false, false, false),
//                booleanArrayOf(false, false, true, false),
//                booleanArrayOf(false, false, false, true)
//        )
//        Assert.assertArrayEquals(matrix3, matrix1 * matrix2)
//    }
//}