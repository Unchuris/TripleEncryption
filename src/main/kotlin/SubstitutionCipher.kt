class SubstitutionCipher(private val s0: Byte, private val st: Byte) {

    private val mod = 255

    fun encrypt(text: ByteArray): ByteArray {
        val result = ByteArray(text.size)
        result[0] = (text[0] + s0).rem(mod).toByte()
        (1 until text.size).forEach {
            result[it] = (text[it] + st).rem(mod).toByte()
        }
        return result
    }

    fun decrypt(text: ByteArray): ByteArray {
        val result = ByteArray(text.size)
        result[0] = (text[0] - s0).rem(mod).toByte()
        (1 until text.size).forEach {
            result[it] = (text[it] - st).rem(mod).toByte()
        }
        return result
    }
}