package com.masliaiev.composition.domain.usecases

import com.masliaiev.composition.domain.entity.GameSettings
import com.masliaiev.composition.domain.entity.Level
import com.masliaiev.composition.domain.repository.GameRepository

class GetGameSettingsUseCase (private val repository: GameRepository) {
    operator fun invoke(level: Level): GameSettings {
        return repository.getGameASettings(level)
    }
}