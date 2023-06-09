package com.example.moviesgrab.networking.person

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SinglePerson(
    @SerializedName("adult") val adult: Boolean?,
    @SerializedName("gender") val gender: Int?,
    @SerializedName("id") val id: Int?,
    @SerializedName("known_for") val known_for: List<KnownFor>?,
    @SerializedName("known_for_department") val known_for_department: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("popularity") val popularity: Double?,
    @SerializedName("profile_path") val profile_path: String?
): Serializable