package com.example.rustorescreen.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.rustorescreen.R

@Composable
fun AppDescription(
    description: String,
    expanded: Boolean,
    onExpandClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        Text(text = stringResource(R.string.app_details_description))
        Spacer(Modifier.height(8.dp))
        Text(
            text = description,
            style = MaterialTheme.typography.bodyLarge,
            /*shows all lines, when expanded
            * if !expended shows only 2 lines*/
            maxLines = if (!expanded) Int.MAX_VALUE else 2,
            overflow = TextOverflow.Ellipsis
        )
        // Expand/collapse description button
        TextButton(
            onClick = onExpandClick
        ) {
            Text(if (expanded) {
                stringResource(R.string.app_details_read_less)
            } else {
                stringResource(R.string.app_details_read_more)
            })
        }
    }
}