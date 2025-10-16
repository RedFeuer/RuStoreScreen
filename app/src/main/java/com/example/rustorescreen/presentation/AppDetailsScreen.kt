package com.example.rustorescreen.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.rustorescreen.model.AppItem

@OptIn(ExperimentalMaterial3Api::class) // using Material3 experimental API
@Composable
fun AppDetailsScreen(
    app: AppItem,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {Text(app.title, maxLines = 1) },
                /* button to go back to list of apps */
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
                    }
                }
            )
        }
    ) { padding -> // inner padding from Scaffold
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.Start, // Align content to the start (left)
        ) {
            Icon(app.icon, contentDescription = null, modifier = Modifier.size(84.dp))
            Spacer(Modifier.height(16.dp))
            Text(app.title, style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.height(8.dp))
            Text(app.subtitle, style = MaterialTheme.typography.bodyLarge)
            Spacer(Modifier.height(8.dp))
            AssistChip(onClick = {}, label = { Text(app.category) })
        }
    }
}