package com.masliaiev.composition.domain.repository

import com.masliaiev.composition.domain.entity.GameSettings
import com.masliaiev.composition.domain.entity.Level
import com.masliaiev.composition.domain.entity.Question

interface GameRepository {

    fun generateQuestion (maxSumValue: Int, countOfOptions: Int): Question

    fun getGameASettings (level: Level): GameSettings
}