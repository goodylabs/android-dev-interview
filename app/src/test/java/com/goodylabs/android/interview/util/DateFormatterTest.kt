package com.goodylabs.android.interview.util

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
internal class DateFormatterTest {

    @Test
    fun format1() {
        val result = DateFormatter.format("2017-11-04T18:50:21.651Z")
        assert(result.equals("04-11-2017"))
    }

    @Test
    fun format2() {
        val result = DateFormatter.format("2011-12-04T18:50:21.651Z")
        assert(result.equals("04-12-2011"))
    }
}