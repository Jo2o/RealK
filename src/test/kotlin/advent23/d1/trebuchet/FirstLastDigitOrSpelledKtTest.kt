package advent23.d1.trebuchet

import org.junit.jupiter.api.Test


import org.junit.jupiter.api.Assertions.*

class FirstLastDigitOrSpelledKtTest {

    @Test
    fun twoDigitNumberFromFirstLastDigitOrSpelled() {
        assertEquals(65, twoDigitNumberFromFirstLastDigitOrSpelled("65"))
        assertEquals(55, twoDigitNumberFromFirstLastDigitOrSpelled("5"))
        assertEquals(99, twoDigitNumberFromFirstLastDigitOrSpelled("nine"))
        assertEquals(73, twoDigitNumberFromFirstLastDigitOrSpelled("7threeonethreex"))
    }
}