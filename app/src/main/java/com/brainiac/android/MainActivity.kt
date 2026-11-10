package com.brainiac.android

import android.app.Application
import android.app.KeyguardManager
import android.os.BatteryManager
import android.os.Bundle
import android.text.format.DateFormat
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.brainiac.android.commands.LocalCommand
import com.brainiac.android.commands.canRun
import com.brainiac.android.commands.parseLocalCommand
import java.util.Date

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme(colorScheme = darkColorScheme()) {
                AssistantScreen()
            }
        }
    }
}

class AssistantViewModel(private val app: Application) : AndroidViewModel(app) {
    var input by mutableStateOf("")
    var reply by mutableStateOf("")
        private set

    fun submit() {
        if (input.isBlank()) return
        val command = parseLocalCommand(input)
        val locked = app.getSystemService(KeyguardManager::class.java)?.isDeviceLocked ?: true
        reply = when {
            command == null -> "Try asking for the time or battery percentage."
            !canRun(command, locked) -> "Unlock your device to run this command."
            command == LocalCommand.Time -> "It's ${DateFormat.getTimeFormat(app).format(Date())}."
            else -> batteryReply()
        }
        input = ""
    }

    private fun batteryReply(): String {
        val level = app.getSystemService(BatteryManager::class.java)
            ?.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
        return if (level != null && level in 0..100) {
            "Battery is at $level%."
        } else {
            "Battery percentage is unavailable."
        }
    }
}

@Composable
private fun AssistantScreen(model: AssistantViewModel = viewModel()) {
    Scaffold { insets ->
        Column(
            modifier = Modifier.fillMaxSize().padding(insets).verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text("Brainiac", style = MaterialTheme.typography.headlineLarge)
            Text("Try asking for the time or battery percentage.")
            OutlinedTextField(
                value = model.input,
                onValueChange = { model.input = it },
                label = { Text("Command") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
            )
            Button(onClick = model::submit, enabled = model.input.isNotBlank()) {
                Text("Send")
            }
            if (model.reply.isNotEmpty()) Text(model.reply)
        }
    }
}
