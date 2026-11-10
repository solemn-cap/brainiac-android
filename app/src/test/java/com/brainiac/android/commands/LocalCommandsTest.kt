package com.brainiac.android.commands

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class LocalCommandsTest {
    @Test fun recognizesSupportedCommands() {
        assertEquals(LocalCommand.Time, parseLocalCommand(" What time is it? "))
        assertEquals(LocalCommand.Battery, parseLocalCommand("Battery percentage"))
    }

    @Test fun rejectsUnrecognizedCommands() {
        assertNull(parseLocalCommand("open the camera"))
    }

    @Test fun appliesLockScreenPolicy() {
        assertTrue(canRun(LocalCommand.Time, deviceLocked = true))
        assertFalse(canRun(LocalCommand.Battery, deviceLocked = true))
        assertTrue(canRun(LocalCommand.Battery, deviceLocked = false))
    }
}
