package com.example.rustorescreen.presentation.ui

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.rustorescreen.domain.domainModel.App

@OptIn(ExperimentalMaterial3Api::class) // using Material3 experimental API
@Composable
fun AppDetailsScreen(
    app: App,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    var descriptionExpanded by remember { mutableStateOf(false) }

    val workInProgressText = "Функция в разработке"

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {Text(app.name, maxLines = 1) },
                /* button to go back to list of apps */
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Назад")
                    }
                },
                actions = { // share button
                    IconButton(onClick =  {
                        val shareText = "Check out this app: ${app.name}\n\n${app.description}"
                        val shareIntent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_TEXT, shareText)
                        }
                        context.startActivity(
                            Intent.createChooser(shareIntent, "Поделиться с помощью")
                        )
                    }) {
                        Icon(Icons.Default.Share, contentDescription = "Поделиться")
                    }
                }
            )
        }
    ) { padding -> // inner padding from Scaffold
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.Start, // Align content to the start (left)
        ) {
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
            ) {
                Spacer(Modifier.height(8.dp))

                /* Name and category */
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                ) {
                    Text(app.name, style = MaterialTheme.typography.headlineSmall)
                    Spacer(Modifier.height(8.dp))
                    AssistChip(onClick = {}, label = { Text(app.category.toString()) })
                }

                Spacer(Modifier.height(16.dp))

                /* Installation button */
                Button(
                    onClick = {
                        Toast.makeText(context, workInProgressText, Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Text("Установить")
                }

                Spacer(Modifier.height(12.dp))

                /* Description with expand/collapse */
                Column (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = app.description,
                        style = MaterialTheme.typography.bodyLarge,
                        /*shows all lines, when expanded
                        * if !expended shows only 2 lines*/
                        maxLines = if (descriptionExpanded) Int.MAX_VALUE else 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    TextButton(
                        onClick = { descriptionExpanded = !descriptionExpanded }
                    ) {
                        Text(if (descriptionExpanded) "Свернуть" else "Читать далее")
                    }
                }

                Spacer(Modifier.height(12.dp))

                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )

                Spacer(Modifier.height(12.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp)
                        .clickable{
                            Toast.makeText(context, workInProgressText, Toast.LENGTH_SHORT).show()
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        /*TODO: developer's screen*/
                        text = "Другие сведения",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}