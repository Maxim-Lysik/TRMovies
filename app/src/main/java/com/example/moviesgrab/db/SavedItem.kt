package com.example.moviesgrab.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable


@Entity(tableName = "saved_articles")
data class SavedItem(
    @SerializedName("title_name") val title_name: String,
    @SerializedName("article_link") val article_link: String,
    @SerializedName("vote_average") val vote_average: Double,
    @SerializedName("profile_path") val profile_path: String?

): Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null
}
