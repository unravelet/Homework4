package at.technikum_wien.tscheppen.homework5.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository(private val newsItemDao: NewsItemDao) {
    val newsItems : MutableLiveData<List<NewsItem>?> = newsItemDao.newsItemEntries

    suspend fun insert(entry : NewsItem) {
        newsItemDao.insert(entry)
    }

    suspend fun delete(entry : NewsItem) {
        newsItemDao.delete(entry)
    }
}