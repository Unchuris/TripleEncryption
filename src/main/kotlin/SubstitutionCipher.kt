class SubstitutionCipher {
    companion object {

        private val s0 = key[key.size - 2]
        private val st = key[key.size - 1]
        private const val MOD = 255

        fun encrypt(text: ByteArray): ByteArray {
            val result = ByteArray(text.size)
            result[0] = (text[0] + s0).rem(MOD).toByte()
            (1 until text.size).forEach {
                result[it] = (text[it] + st).rem(MOD).toByte()
            }
            return result
        }

        fun decrypt(text: ByteArray): ByteArray {
            val result = ByteArray(text.size)
            result[0] = (text[0] - s0).rem(MOD).toByte()
            (1 until text.size).forEach {
                result[it] = (text[it] - st).rem(MOD).toByte()
            }
            return result
        }
    }
}