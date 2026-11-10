package com.brainiac.android.commands

enum class LocalCommand(val allowedWhileLocked: Boolean) {
    Time(true),
    Battery(false),
}

fun parseLocalCommand(input: String): LocalCommand? =
    when (input.trim().lowercase().trimEnd('?', '.', '!')) {
        "time", "what time is it", "current time" -> LocalCommand.Time
        "battery", "battery percentage", "what is my battery percentage" -> LocalCommand.Battery
        else -> null
    }

fun canRun(command: LocalCommand, deviceLocked: Boolean): Boolean =
    !deviceLocked || command.allowedWhileLocked
