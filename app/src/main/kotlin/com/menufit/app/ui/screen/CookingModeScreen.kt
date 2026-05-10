package com.menufit.app.ui.screen

import android.os.Build
import android.view.WindowManager
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.menufit.app.ui.viewmodel.CookingModeViewModel
import kotlin.math.abs

@Composable
fun CookingModeScreen(
    recipeId: Long,
    navController: NavController,
    viewModel: CookingModeViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    // Initialize cooking mode
    LaunchedEffect(recipeId) {
        viewModel.initializeCooking(recipeId)
    }

    // Keep screen on while cooking
    val window = (context as? android.app.Activity)?.window
    DisposableEffect(Unit) {
        window?.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        onDispose {
            window?.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }
    }

    val cookingState by viewModel.cookingState.collectAsState()
    val error by viewModel.error.collectAsState()

    if (error != null) {
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
    } else if (cookingState.steps.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Chargement...")
        }
    } else {
        CookingModeContent(
            viewModel = viewModel,
            navController = navController
        )
    }
}

@Composable
fun CookingModeContent(
    viewModel: CookingModeViewModel,
    navController: NavController
) {
    val cookingState by viewModel.cookingState.collectAsState()
    var dragOffset by remember { mutableFloatStateOf(0f) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectHorizontalDragGestures(
                        onDrag = { change, dragAmount ->
                            dragOffset += dragAmount
                            change.consume()
                        },
                        onDragEnd = {
                            if (dragOffset > 100) {
                                // Swipe left -> previous step
                                viewModel.previousStep()
                            } else if (dragOffset < -100) {
                                // Swipe right -> next step
                                viewModel.nextStep()
                            }
                            dragOffset = 0f
                        }
                    )
                }
        ) {
            // Header with progress and close button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Étape ${cookingState.currentStepIndex + 1} / ${cookingState.totalSteps}",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Fermer",
                        tint = Color.White
                    )
                }
            }

            // Main content - centered
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    // Step instruction - BIG
                    val currentStep = cookingState.steps.getOrNull(cookingState.currentStepIndex)
                    if (currentStep != null) {
                        Text(
                            text = currentStep.instruction,
                            style = MaterialTheme.typography.headlineLarge.copy(
                                fontSize = 28.sp,
                                lineHeight = 36.sp
                            ),
                            color = MaterialTheme.colorScheme.onBackground,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(48.dp))

                        // Timer section if duration > 0
                        if (currentStep.durationSeconds > 0) {
                            TimerDisplay(
                                timeRemainingSeconds = cookingState.timeRemainingSeconds,
                                isRunning = cookingState.isTimerRunning,
                                isCompleted = cookingState.isStepCompleted,
                                onStart = { viewModel.startTimer() },
                                onPause = { viewModel.pauseTimer() },
                                onReset = { viewModel.resetTimer() }
                            )
                        }
                    }
                }
            }

            // Navigation buttons - bottom
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f)
                    )
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Previous button
                Button(
                    onClick = { viewModel.previousStep() },
                    enabled = cookingState.currentStepIndex > 0,
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Text(
                        text = "◀ Précédent",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White
                    )
                }

                // Next button
                Button(
                    onClick = { viewModel.nextStep() },
                    enabled = cookingState.currentStepIndex < cookingState.totalSteps - 1,
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Text(
                        text = "Suivant ▶",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun TimerDisplay(
    timeRemainingSeconds: Int,
    isRunning: Boolean,
    isCompleted: Boolean,
    onStart: () -> Unit,
    onPause: () -> Unit,
    onReset: () -> Unit
) {
    val minutes = timeRemainingSeconds / 60
    val seconds = timeRemainingSeconds % 60
    val timeText = "%02d:%02d".format(minutes, seconds)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // Timer display - HUGE
        Box(
            modifier = Modifier
                .background(
                    color = when {
                        isCompleted -> MaterialTheme.colorScheme.tertiary
                        isRunning -> MaterialTheme.colorScheme.primary
                        else -> MaterialTheme.colorScheme.surface
                    },
                    shape = RoundedCornerShape(24.dp)
                )
                .padding(32.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = timeText,
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontSize = 72.sp,
                        lineHeight = 80.sp
                    ),
                    color = when {
                        isCompleted -> Color.Black
                        isRunning -> Color.White
                        else -> MaterialTheme.colorScheme.onSurface
                    },
                    fontWeight = FontWeight.Bold
                )
                if (isCompleted) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "✓ Terminé",
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // Control buttons
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isRunning) {
                Button(
                    onClick = onPause,
                    modifier = Modifier.height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Text(
                        text = "⏸ Pause",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White
                    )
                }
            } else {
                Button(
                    onClick = onStart,
                    enabled = timeRemainingSeconds > 0,
                    modifier = Modifier.height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(
                        text = "▶ Démarrer",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White
                    )
                }
            }

            Button(
                onClick = onReset,
                modifier = Modifier.height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.tertiary
                )
            ) {
                Text(
                    text = "🔄 Réinitialiser",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black
                )
            }
        }
    }
}
