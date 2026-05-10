package com.menufit.app.ui.viewmodel

import android.content.Context
import android.media.RingtoneManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.menufit.app.data.repository.RecipeRepository
import com.menufit.app.domain.model.Step
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CookingState(
    val currentStepIndex: Int = 0,
    val totalSteps: Int = 0,
    val steps: List<Step> = emptyList(),
    val isTimerRunning: Boolean = false,
    val timeRemainingSeconds: Int = 0,
    val isStepCompleted: Boolean = false,
    val recipeTitle: String = ""
)

@HiltViewModel
class CookingModeViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository
) : ViewModel() {

    private val _cookingState = MutableStateFlow(CookingState())
    val cookingState: StateFlow<CookingState> = _cookingState.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private var timerJob: kotlinx.coroutines.Job? = null

    fun initializeCooking(recipeId: Long) {
        viewModelScope.launch {
            try {
                recipeRepository.getRecipeById(recipeId).collect { recipe ->
                    if (recipe != null) {
                        recipeRepository.getStepsForRecipe(recipeId).collect { steps ->
                            _cookingState.value = CookingState(
                                currentStepIndex = 0,
                                totalSteps = steps.size,
                                steps = steps,
                                recipeTitle = recipe.title,
                                timeRemainingSeconds = if (steps.isNotEmpty()) steps[0].durationSeconds else 0
                            )
                        }
                    }
                }
            } catch (e: Exception) {
                _error.value = "Erreur: ${e.message}"
            }
        }
    }

    fun startTimer() {
        val state = _cookingState.value
        if (state.timeRemainingSeconds <= 0) return
        if (state.isTimerRunning) return

        _cookingState.value = state.copy(isTimerRunning = true)

        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            var remaining = state.timeRemainingSeconds

            while (remaining > 0) {
                delay(1000)
                remaining--
                _cookingState.value = _cookingState.value.copy(
                    timeRemainingSeconds = remaining,
                    isTimerRunning = remaining > 0
                )
            }

            // Timer finished
            _cookingState.value = _cookingState.value.copy(
                isTimerRunning = false,
                isStepCompleted = true
            )
        }
    }

    fun pauseTimer() {
        timerJob?.cancel()
        _cookingState.value = _cookingState.value.copy(isTimerRunning = false)
    }

    fun resetTimer() {
        timerJob?.cancel()
        val state = _cookingState.value
        val currentStep = state.steps.getOrNull(state.currentStepIndex)
        _cookingState.value = state.copy(
            isTimerRunning = false,
            timeRemainingSeconds = currentStep?.durationSeconds ?: 0,
            isStepCompleted = false
        )
    }

    fun nextStep() {
        timerJob?.cancel()
        val state = _cookingState.value
        val nextIndex = state.currentStepIndex + 1

        if (nextIndex < state.totalSteps) {
            val nextStep = state.steps[nextIndex]
            _cookingState.value = state.copy(
                currentStepIndex = nextIndex,
                timeRemainingSeconds = nextStep.durationSeconds,
                isTimerRunning = false,
                isStepCompleted = false
            )
        }
    }

    fun previousStep() {
        timerJob?.cancel()
        val state = _cookingState.value
        val prevIndex = state.currentStepIndex - 1

        if (prevIndex >= 0) {
            val prevStep = state.steps[prevIndex]
            _cookingState.value = state.copy(
                currentStepIndex = prevIndex,
                timeRemainingSeconds = prevStep.durationSeconds,
                isTimerRunning = false,
                isStepCompleted = false
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }
}
