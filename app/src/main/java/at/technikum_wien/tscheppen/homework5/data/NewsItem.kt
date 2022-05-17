package at.technikum_wien.tscheppen.homework5.data

import androidx.annotation.NonNull
import java.io.Serializable
import java.util.*
import androidx.room.*

@Entity(tableName = "newsItems", indices = [Index(value = ["identifier", "title", "link", "description",
    "imageUrl","author","publicationDate", "keywords"], unique = true)])
data class NewsItem(
    @PrimaryKey @ColumnInfo var identifier : String,
    @ColumnInfo @NonNull var title : String,
    @ColumnInfo @NonNull var link : String?,
    @ColumnInfo @NonNull var description : String?,
    @ColumnInfo @NonNull var imageUrl : String?,
    @ColumnInfo @NonNull var author : String?,
    @ColumnInfo @NonNull var publicationDate : String?,
    @ColumnInfo @NonNull var keywords : Set<String>) : Serializable