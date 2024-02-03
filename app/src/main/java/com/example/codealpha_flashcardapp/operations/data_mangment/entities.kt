package com.example.codealpha_flashcardapp.operations.data_mangment

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import androidx.room.Index

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userName: String,
    val email: String,
    val password: String
)



@Entity(
    tableName = "decks",
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = ["id"],
        childColumns = ["user_id"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("user_id")]
)
data class Deck(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val user_id: Int,
    val title: String,
    val description: String,
    val bg_color: Int // You can change this to match the type of color you're using
)




@Entity(
    tableName = "flashcards",
    foreignKeys = [ForeignKey(
        entity = Deck::class,
        parentColumns = ["id"],
        childColumns = ["Deck_id"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("Deck_id")]
)
@TypeConverters(StringListConverter::class) // Add this line
data class FlashCard(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val Deck_id: Int,
    var question: String,
    val answer: String,
    val hasOptions: Boolean,
    val options: List<String> = emptyList() // Nullable list of options
)





class StringListConverter {
    @TypeConverter
    fun fromString(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<String>): String {
        return Gson().toJson(list)
    }
}
