class SubstitutionCipher {
    companion object {

        private const val s0 = 9
        private const val st = 7
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