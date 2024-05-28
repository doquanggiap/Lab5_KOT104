package fpoly.giapdqph34273.lab5_ph34273

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AssistChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
@Preview(showBackground = true)
fun Bai3() {
    CategoryApp()
}

@Composable
fun CategoryApp() {
    val categories = listOf(
        "Fiction", "Mystery", "Science Fiction", "Fantasy", "Adventure", "Historical", "Horror",
        "Romance"
    )
    val suggestions = listOf(
        "Biography", "Cookbook", "Poetry",
        "Self-help", "Thriller"
    )
    var selectedCategories by remember {
        mutableStateOf(setOf<String>())
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = 16.dp,
                top = 30.dp,
            )
    ) {

        Text("Choose a category:", style =
        MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        AssistChip(
            onClick = { /* Do something */ },
            label = { Text("Need help?") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        CategoryChips(
            categories = categories,
            selectedCategories = selectedCategories,
            onCategorySelected = { category ->
                selectedCategories = selectedCategories.toMutableSet().apply {
                    if (contains(category)) {
                        remove(category)
                    } else {
                        add(category)
                    }
                }
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        SuggestionChips(
            suggestions = suggestions,
            selectedCategories = selectedCategories,
            onSuggestionSelected = { suggestion ->
                selectedCategories = selectedCategories.toMutableSet().apply {
                    add(suggestion)
                }
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        SelectedCategoriesChips(
            selectedCategories = selectedCategories,
            onCategoryRemoved = { category ->
                selectedCategories = selectedCategories.toMutableSet().apply {
                    remove(category)
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryChips(
    categories: List<String>,
    selectedCategories: Set<String>,
    onCategorySelected: (String) -> Unit
) {
    Text("Choose categories:", style = MaterialTheme.typography.titleMedium)
    Spacer(modifier = Modifier.height(8.dp))
    Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
        categories.forEach { category ->
            FilterChip(
                selected = selectedCategories.contains(category),
                onClick = { onCategorySelected(category) },
                label = { Text(category) },
                modifier = Modifier.padding(end = 8.dp)
            )
        }
    }
}

@Composable
fun SuggestionChips(
    suggestions: List<String>,
    selectedCategories: Set<String>,
    onSuggestionSelected: (String) -> Unit
) {
    Text("Suggestions:", style = MaterialTheme.typography.titleMedium)
    Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
        suggestions.forEach { suggestion ->
            val isSelected = selectedCategories.contains(suggestion)
            val chipColors = if (isSelected) {
                SuggestionChipDefaults.suggestionChipColors(
                    containerColor = Color.LightGray
                )
            } else {
                SuggestionChipDefaults.suggestionChipColors()
            }
            SuggestionChip(
                onClick = { onSuggestionSelected(suggestion) },
                label = { Text(suggestion) },
                colors = chipColors,
                modifier = Modifier.padding(end = 8.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectedCategoriesChips(
    selectedCategories: Set<String>,
    onCategoryRemoved: (String) -> Unit
) {
    Text(
        "Selected categories:", style =
        MaterialTheme.typography.titleMedium
    )
    Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
        selectedCategories.forEach { selectedCategory ->
            InputChip(
                selected = true,
                onClick = { },
                label = { Text(selectedCategory) },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Deselect",
                        modifier = Modifier.clickable {
                            onCategoryRemoved(selectedCategory)
                        }.size(18.dp)
                    )
                },
                modifier = Modifier.padding(end = 8.dp),
            )
        }
    }
}