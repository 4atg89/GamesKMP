package com.atg.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class ShortGameDTO(
    @SerialName("id") val id: Int,
    @SerialName("title") val title: String,
    @SerialName("thumbnail") val thumbnail: String,
    @SerialName("short_description") val shortDescription: String,
    @SerialName("game_url") val gameUrl: String,
    @SerialName("genre") val genre: String,
    @SerialName("publisher") val publisher: String,
    @SerialName("developer") val developer: String,
    @SerialName("release_date") val releaseDate: String,
    @SerialName("freetogame_profile_url") val freeToGameProfileUrl: String,
)

@Serializable
class GameDTO(
    @SerialName("id") val id: Int,
    @SerialName("title") val title: String,
    @SerialName("thumbnail") val thumbnail: String,
    @SerialName("short_description") val shortDescription: String,
    @SerialName("game_url") val gameUrl: String,
    @SerialName("genre") val genre: String,
    @SerialName("publisher") val publisher: String,
    @SerialName("developer") val developer: String,
    @SerialName("release_date") val releaseDate: String,
    @SerialName("freetogame_profile_url") val freeToGameProfileUrl: String,
)