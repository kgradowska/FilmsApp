package gradowska.katarzyna.filmsapp.domain.entity

import androidx.compose.runtime.Immutable

@Immutable
data class MovieDataModel(
    val movieID: String,
    val movieTitle: String,
    val movieDescription: String,
    val movieRate: Double?,
    val moviePhoto: String,
    val movieLiked: Boolean
)
