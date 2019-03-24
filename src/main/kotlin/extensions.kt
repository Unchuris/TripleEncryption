import java.util.*

typealias Vector = BooleanArray
typealias Matrix = Array<Vector>

operator fun Matrix.times(other: Matrix): Matrix {
    val rows1 = this.size
    val cols1 = this[0].size
    val rows2 = other.size
    val cols2 = other[0].size
    require(cols1 == rows2)
    val result = Matrix(rows1) { Vector(cols2) }
    for (i in 0 until rows1) {
        for (j in 0 until cols2) {
            for (k in 0 until rows2) {
                result[i][j] = this[i][k] and other[k][j] or result[i][j]
            }
        }
    }
    return result
}

fun Matrix.toTransposition(): ByteArray {
    require(size == this[0].size)
    val result = ByteArray(size)
    for (i in 0 until size) {
        for (j in 0 until size) {
            if (this[i][j]) {
                result[i] = j.toByte()
                break
            }
        }
    }
    return result
}

fun ByteArray.toMatrix(): Matrix {
    val result: Matrix = Array(size) { BooleanArray(size) }
    this.removeReplays().forEachIndexed { index, byte ->
        result[index][byte.toInt()] = true
    }
    return result
}

fun ByteArray.transposition(transposition: ByteArray): ByteArray {
    require(size == transposition.size)
    val result = ByteArray(size)
    transposition.removeReplays().forEachIndexed { index, item ->
        result[item.toInt()] = this[index]
    }
    return result
}


fun Byte.toUnsignedInt() = toInt() and 0xFF

fun ByteArray.transpositionReverse(transposition: ByteArray): ByteArray {
    require(size == transposition.size)
    val result = ByteArray(size)
    transposition.removeReplays().forEachIndexed { index, item ->
        result[index] = this[item.toInt()]
    }
    return result
}

fun BooleanArray.transposition(transposition: ByteArray): BooleanArray {
    require(size == transposition.size)
    val result = BooleanArray(size)
    transposition.removeReplays().forEachIndexed { index, item ->
        result[item.toInt()] = this[index]
    }
    return result
}

fun Byte.toBooleanArray(): BooleanArray {
    val binary = BooleanArray(8)
    var value = this.toInt()
    for (i in 0..7) {
        binary[i] = value and 128 != 0
        value = value shl 1
    }
    return binary
}

fun BooleanArray.toByte(): Byte {
    val bits = BitSet(size)
    for (i in this.indices) {
        if (this[i]) {
            bits.set(i)
        }
    }
    val bytes = bits.toByteArray()
    return if (bytes.size * 8 >= size) {
        bytes[0]
    } else {
        Arrays.copyOf(bytes, size / 8 + if (size % 8 == 0) 0 else 1)[0]
    }
}

fun ByteArray.set(startPosition: Int, byteArray: ByteArray) {
    byteArray.forEachIndexed { index, byte ->
        this[startPosition + index] = byte
    }
}

fun ByteArray.removeReplays(): ByteArray {
    val array = this.transform()
    check(array)
    return array
}

private fun check(array: ByteArray) {
    for (i in 0 until array.size) {
        for (j in 0 until array.size) {
            if (i != j && array[i] == array[j]) {
                array[j] = (((array[j] + 1) % array.size).toByte())
                check(array)
                break
            }
        }
    }
}

private fun ByteArray.transform(): ByteArray {
    val result = ByteArray(size)
    val sum = this.sum()
    forEachIndexed { index, byte ->
        val value = ((byte + sum) * (index + 1)).toByte()
        result[index] = (value.toUnsignedInt() % size).toByte()
    }
    return result
}
