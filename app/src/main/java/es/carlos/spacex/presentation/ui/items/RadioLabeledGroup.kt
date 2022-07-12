package es.carlos.spacex.presentation.ui.items

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role

@Composable
fun RadioGroup(
    modifier: Modifier,
    items: List<String>,
    selection: String,
    onItemClick: (String) -> Unit
) {
    Column(modifier = modifier.selectableGroup()) {
        items.forEach { item ->
            LabelledRadioButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = item == selection,
                        onClick = { onItemClick(item) },
                        role = Role.RadioButton
                    ),
                label = item,
                selected = item == selection,
                onClick = null
            )
        }
    }
}