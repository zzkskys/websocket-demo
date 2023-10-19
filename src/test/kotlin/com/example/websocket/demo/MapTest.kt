package com.example.websocket.demo

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 *
 * @since : 2023/10/19
 * @author zzk
 */
class MapTest {


    lateinit var map: MutableMap<String, Int>

    @BeforeEach
    fun setup() {
        map = mutableMapOf(
            "a" to 1,
            "b" to 2,
            "c" to 3
        )
    }

    @Test
    fun computeIfPresent用法() {
        val result = map.computeIfPresent("a") { str, i ->
            i + 10
        }

        assertEquals(11, map["a"])
        assertEquals(11, result)

        val r2 = map.computeIfPresent("d") { str, i -> i + 10 }
        assertNull(r2)
        assertNull(map["d"])
    }

    @Test
    fun computeIfAbsent用法() {
        val r = map.computeIfAbsent("d") { str -> 10 }

        assertEquals(r, 10)
        assertEquals(10, map["d"])

        val r2 = map.computeIfAbsent("c") { str -> 10 }
        assertEquals(3, r2)
        assertEquals(3, map["c"])
    }

    @Test
    fun t3() {
        map["c"]?.let {
            println(it)
        }

        map["c"]?.apply {
            println(this)
        }
        assertNotNull(map["c"])
    }
}