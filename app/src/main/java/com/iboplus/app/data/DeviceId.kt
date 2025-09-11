
package com.iboplus.app.data

import java.util.Random

object DeviceId {
    fun generateMac(): String {
        val r = Random()
        val bytes = ByteArray(6)
        r.nextBytes(bytes)
        bytes[0] = (bytes[0].toInt() and 0xFE or 0x02).toByte() // local-admin, unicast
        return bytes.joinToString(":") { String.format("%02X", it) }
    }
    fun generateKey(): String {
        val r = Random()
        val n = r.nextInt(900000) + 100000
        return n.toString()
    }
}
