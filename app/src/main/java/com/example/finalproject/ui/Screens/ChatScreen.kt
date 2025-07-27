package com.example.finalproject.ui.Screens

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.NavController
import com.example.finalproject.ui.Session.OnboardingViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

@Composable
fun ChatScreen(
    navController: NavController,
    viewModel: OnboardingViewModel,
    receiverName: String,
    receiverUid: String
) {
    val currentUid = FirebaseAuth.getInstance().currentUser?.uid ?: return
    val chatId = listOf(currentUid, receiverUid).sorted().joinToString("_")
    val dbRef = FirebaseDatabase.getInstance().getReference("chats/$chatId")

    var message by remember { mutableStateOf("") }
    var messages by remember { mutableStateOf(listOf<ChatMessage>()) }

    // Listener de mensajes
    DisposableEffect(chatId) {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val msgList = snapshot.children.mapNotNull {
                    it.getValue(ChatMessage::class.java)
                }
                messages = msgList.sortedBy { it.timestamp }
            }

            override fun onCancelled(error: DatabaseError) {}
        }
        dbRef.addValueEventListener(listener)
        onDispose { dbRef.removeEventListener(listener) }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Chat con $receiverName", style = MaterialTheme.typography.titleLarge)

        Spacer(Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier.weight(1f),
            reverseLayout = false
        ) {
            items(messages) { msg ->
                val isMe = msg.sender == currentUid
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = if (isMe) Arrangement.End else Arrangement.Start
                ) {
                    Text(
                        msg.text,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .padding(8.dp)
                            .background(if (isMe) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary)
                            .padding(8.dp)
                    )
                }
            }
        }

        Row {
            TextField(
                value = message,
                onValueChange = { message = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Escribe un mensaje") }
            )
            Button(onClick = {
                if (message.isNotBlank()) {
                    val msg = ChatMessage(
                        sender = currentUid,
                        text = message,
                        timestamp = System.currentTimeMillis()
                    )
                    dbRef.push().setValue(msg)
                    message = ""
                }
            }) {
                Text("Enviar")
            }
        }
    }
}

data class ChatMessage(
    val sender: String = "",
    val text: String = "",
    val timestamp: Long = 0
)
