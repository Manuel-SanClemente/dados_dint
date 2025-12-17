package com.example.dados

// CLASE DE DATOS. Esto determina los atributos de la entidad DiceCard. Estos son los datos importantes de un dado.

data class DiceCardItem (
    val diceName: String,
    var diceCount: Int = 1
)