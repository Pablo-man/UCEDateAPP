package com.example.finalproject.ui.Screens

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color as AndroidColor
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
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

    // Colores personalizados
    val turquoise = Color(0xFF1DE9B6)
    val pinkNeon = Color(0xFFFF69B4)
    val darkBg = Color(0xFF23243A)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(darkBg, turquoise, pinkNeon)
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Card(
            shape = RoundedCornerShape(28.dp),
            elevation = CardDefaults.cardElevation(14.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White.copy(alpha = 0.92f)
            ),
            modifier = Modifier
                .fillMaxWidth(0.98f)
                .fillMaxHeight(0.98f)
        ) {
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(18.dp)
            ) {
                Text(
                    "Chat con $receiverName",
                    style = MaterialTheme.typography.titleLarge,
                    color = pinkNeon,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(vertical = 8.dp)
                )

                Spacer(Modifier.height(8.dp))

                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 4.dp),
                    reverseLayout = false
                ) {
                    items(messages) { msg ->
                        val isMe = msg.sender == currentUid
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 2.dp),
                            horizontalArrangement = if (isMe) Arrangement.End else Arrangement.Start
                        ) {
                            Surface(
                                shape = RoundedCornerShape(16.dp),
                                color = if (isMe) turquoise else pinkNeon.copy(alpha = 0.8f),
                                tonalElevation = 4.dp,
                                shadowElevation = 2.dp,
                                modifier = Modifier
                                    .widthIn(max = 260.dp)
                                    .padding(horizontal = 6.dp)
                            ) {
                                Text(
                                    msg.text,
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = if (isMe) Color.White else Color.Black,
                                    modifier = Modifier
                                        .padding(12.dp)
                                )
                            }
                        }
                    }
                }

                Spacer(Modifier.height(6.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                ) {
                    TextField(
                        value = message,
                        onValueChange = { message = it },
                        placeholder = { Text("Escribe un mensaje") },
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedIndicatorColor = turquoise,
                            unfocusedIndicatorColor = turquoise.copy(alpha = 0.5f),
                            cursorColor = pinkNeon
                        ),
                        shape = RoundedCornerShape(14.dp)
                    )
                    Spacer(Modifier.width(10.dp))
                    Button(
                        onClick = {
                            if (message.isNotBlank()) {
                                val msg = ChatMessage(
                                    sender = currentUid,
                                    text = message,
                                    timestamp = System.currentTimeMillis()
                                )
                                dbRef.push().setValue(msg)
                                message = ""
                            }
                        },
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = pinkNeon,
                            contentColor = Color.White
                        ),
                        modifier = Modifier.height(50.dp)
                    ) {
                        Text("Enviar", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

data class ChatMessage(
    val sender: String = "",
    val text: String = "",
    val timestamp: Long = 0
)