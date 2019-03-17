import java.util.*

typealias Vector = BooleanArray
typealias Matrix = Array<Vector>

fun getEmpty(): Matrix = arrayOf(
        booleanArrayOf(false, false, false, false, false, false, false, false, false, false),
        booleanArrayOf(false, false, false, false, false, false, false, false, false, false),
        booleanArrayOf(false, false, false, false, false, false, false, false, false, false),
        booleanArrayOf(false, false, false, false, false, false, false, false, false, false),
        booleanArrayOf(false, false, false, false, false, false, false, false, false, false),
        booleanArrayOf(false, false, false, false, false, false, false, false, false, false),
        booleanArrayOf(false, false, false, false, false, false, false, false, false, false),
        booleanArrayOf(false, false, false, false, false, false, false, false, false, false),
        booleanArrayOf(false, false, false, false, false, false, false, false, false, false),
        booleanArrayOf(false, false, false, false, false, false, false, false, false, false)
)

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
    val result = getEmpty()
    this.forEachIndexed { index, byte ->
        result[index][byte.toInt()] = true
    }
    return result
}

fun ByteArray.transposition(transposition: ByteArray): ByteArray {
    require(size == transposition.size)
    val result = ByteArray(size)
    transposition.forEachIndexed { index, item ->
        result[item.toInt()] = this[index]
    }
    return result
}

fun ByteArray.transpositionReverse(transposition: ByteArray): ByteArray {
    require(size == transposition.size)
    val result = ByteArray(size)
    transposition.forEachIndexed { index, item ->
        result[index] = this[item.toInt()]
    }
    return result
}

fun BooleanArray.transposition(transposition: ByteArray): BooleanArray {
    require(size == transposition.size)
    val result = BooleanArray(size)
    transposition.forEachIndexed { index, item ->
        result[item.toInt()] = this[index]
    }
    return result
}

fun ByteArray.toBooleanArray(): BooleanArray {
    val binary = BooleanArray(size * 8)
    var j = 0
    for (byte in this) {
        var value = byte.toInt()
        for (i in 0..7) {
            binary[j] = value and 128 != 0
            value = value shl 1
            j++
        }
    }
    return binary
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