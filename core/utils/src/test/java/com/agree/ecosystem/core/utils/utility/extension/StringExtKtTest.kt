package com.agree.ecosystem.core.utils.utility.extension

import org.junit.Assert
import org.junit.Test

internal class StringExtKtTest {

    @Test
    fun containsAll() {
        val sampleText = "com.agree.ecosystem.fisherman.main"
        Assert.assertTrue(
            sampleText.containsAll(
                "poultry",
                "ruminants",
                "fisherman",
                "cultivator"
            )
        )
    }
}
