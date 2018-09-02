package com.yezi.todolist.data

import org.junit.Assert
import org.junit.Test

class TaskTest {

    @Test
    fun tagInspect() {
        for (s in TagInspect.INVALID_CHARSET) {
            Assert.assertFalse(TagInspect.valid(java.lang.String.valueOf(s)))
        }
        Assert.assertTrue(TagInspect.valid("abc"))
        Assert.assertTrue(TagInspect.valid("a"))
        Assert.assertFalse(TagInspect.valid("a${TagInspect.INVALID_CHARSET}"))
    }
}