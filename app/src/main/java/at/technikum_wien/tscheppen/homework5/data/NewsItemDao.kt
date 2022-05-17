package at.technikum_wien.tscheppen.homework5.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*

@Dao
interface NewsItemDao {
    @get:Query("SELECT * FROM newsItems ORDER BY publicationDate")
    val newsItemEntries : MutableLiveData<List<NewsItem>?>

    @Insert //(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entry : NewsItem)

    @Update
    suspend fun update(entry : NewsItem)

    @Delete
    suspend fun delete(entry : NewsItem)
}