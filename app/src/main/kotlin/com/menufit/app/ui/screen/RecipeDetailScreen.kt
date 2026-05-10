package com.menufit.app.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.menufit.app.ui.viewmodel.RecipeDetailViewModel

@Composable
fun RecipeDetailScreen(
    recipeId: Long,
    navController: NavController,
    viewModel: RecipeDetailViewModel = hiltViewModel()
) {
    LaunchedEffect(recipeId) {
        viewModel.loadRecipe(recipeId)
    }

    val recipeDetail by viewModel.recipeDetail.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    val currentServings by viewModel.currentServings.collectAsState()
    val scaledIngredients = viewModel.getScaledIngredients()

    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else if (error != null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = error ?: "Une erreur est survenue",
                    color = MaterialTheme.colorScheme.error
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { navController.popBackStack() }) {
                    Text("Retour")
                }
            }
        }
    } else if (recipeDetail != null) {
        RecipeDetailContent(
            viewModel = viewModel,
            recipeId = recipeId,
            navController = navController
        )
    } else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Recette non trouvée")
        }
    }
}

@Composable
fun RecipeDetailContent(
    viewModel: RecipeDetailViewModel,
    recipeId: Long,
    navController: NavController
) {
    val recipeDetail by viewModel.recipeDetail.collectAsState()
    val currentServings by viewModel.currentServings.collectAsState()
    val scaledIngredients = viewModel.getScaledIngredients()

    val recipe = recipeDetail?.recipe ?: return
    val ingredients = recipeDetail?.ingredients ?: emptyList()
    val steps = recipeDetail?.steps ?: emptyList()
    val techniques = recipeDetail?.techniques ?: emptyList()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
    ) {
        // Header avec retour
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Retour",
                    tint = Color.White
                )
            }
            Text(
                text = recipe.title,
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                modifier = Modifier.weight(1f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Column(modifier = Modifier.padding(16.dp)) {
            // Info recette
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                InfoTag(label = "Difficulté", value = recipe.difficulty)
                InfoTag(label = "Durée", value = "${recipe.durationMinutes}min")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Portions selector
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Portions",
                    style = MaterialTheme.typography.bodyLarge
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ServingsButton("-") {
                        if (currentServings > 1) {
                            viewModel.updateServings(currentServings - 1)
                        }
                    }
                    Text(
                        text = "$currentServings pers.",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )
                    ServingsButton("+") {
                        viewModel.updateServings(currentServings + 1)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Ingredients section
            SectionTitle("Ingrédients")
            Spacer(modifier = Modifier.height(12.dp))
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                scaledIngredients.forEach { ingredient ->
                    IngredientItem(
                        name = ingredient.name,
                        quantity = ingredient.quantity,
                        unit = ingredient.unit
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Techniques section
            if (techniques.isNotEmpty()) {
                SectionTitle("Techniques utilisées")
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    techniques.take(3).forEach { technique ->
                        TechniqueTag(technique.name)
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }

            // Steps section
            SectionTitle("Préparation")
            Spacer(modifier = Modifier.height(12.dp))
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                steps.forEach { step ->
                    StepItem(
                        stepNumber = step.sortOrder,
                        instruction = step.instruction,
                        durationSeconds = step.durationSeconds
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // CTA button - Go to cooking mode
            Button(
                onClick = { navController.navigate("cooking_mode/$recipeId") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    text = "▶ Commencer à cuisiner",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun InfoTag(label: String, value: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(12.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.secondary
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun SectionTitle(text: String) {
    Text(
        text = text.uppercase(),
        style = MaterialTheme.typography.headlineSmall,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun IngredientItem(name: String, quantity: Double, unit: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "$quantity $unit",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun TechniqueTag(name: String) {
    Surface(
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.tertiary,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(8.dp),
        color = MaterialTheme.colorScheme.tertiary,
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
        )
    }
}

@Composable
fun StepItem(stepNumber: Int, instruction: String, durationSeconds: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.Top
    ) {
        // Step number badge
        Surface(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(50)
                )
                .padding(0.dp),
            color = MaterialTheme.colorScheme.primary,
            shape = RoundedCornerShape(50)
        ) {
            Text(
                text = "$stepNumber",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(8.dp)
            )
        }

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = instruction,
                style = MaterialTheme.typography.bodyMedium
            )
            if (durationSeconds > 0) {
                Spacer(modifier = Modifier.height(8.dp))
                val minutes = durationSeconds / 60
                val seconds = durationSeconds % 60
                val durationText = when {
                    minutes > 0 -> if (seconds > 0) "${minutes}m ${seconds}s" else "${minutes}m"
                    else -> "${seconds}s"
                }
                Text(
                    text = "⏱ $durationText",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}

@Composable
fun ServingsButton(label: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(4.dp)
            ),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Text(label, color = Color.White, fontWeight = FontWeight.Bold)
    }
}
