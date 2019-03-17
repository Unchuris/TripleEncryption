//import org.junit.Assert
//import org.junit.Test
//
//class SubstitutionCipherTest {
//
//    @Test
//    fun testCipher() {
//        val byteArray = byteArrayOf(2, 42, 127, 23, 94, 22, 34, 4, 5, 43)
//        val rez = SubstitutionCipher.decrypt(SubstitutionCipher.encrypt(byteArray))
//        Assert.assertArrayEquals(byteArray, rez)
//    }
//
//    @Test
//    fun testCipherCycle() {
//        val byteArray = byteArrayOf(
//                2, 42, 127, 23, 94, 22, 34, 4, 5, 43,
//                75, 23, 45, 65, 24, 12, 68, 42, 12, 10,
//                87, 31, 121, 2, 11, 111, 32, 98, 27, 64)
//
//        val result = ByteArray(byteArray.size)
//        val resultDecrypt = ByteArray(byteArray.size)
//
//        for (i in 0 until byteArray.size step 10) {
//            val array = byteArray.sliceArray(i..i + 9)
//            result.set(i, SubstitutionCipher.encrypt(array))
//        }
//
//        for (i in 0 until result.size step 10) {
//            val array = result.sliceArray(i..i + 9)
//            resultDecrypt.set(i, SubstitutionCipher.decrypt(array))
//        }
//        Assert.assertArrayEquals(byteArray, resultDecrypt)
//    }
//}