package gradowska.katarzyna.filmsapp.presentation.utils

import java.util.Locale

fun formatRate(rate: String): String =
    rate.toDoubleOrNull()
        ?.let { String.format(Locale.US, "%.2f", it) }
        ?: "-"