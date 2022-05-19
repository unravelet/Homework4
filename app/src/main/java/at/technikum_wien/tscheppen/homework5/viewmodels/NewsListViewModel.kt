package at.technikum_wien.tscheppen.homework5.viewmodels

import android.app.Application
import android.database.sqlite.SQLiteConstraintException
import androidx.lifecycle.*
import at.technikum_wien.tscheppen.homework5.R
import at.technikum_wien.tscheppen.homework5.data.ApplicationDatabase
import at.technikum_wien.tscheppen.homework5.data.NewsDownloader
import at.technikum_wien.tscheppen.homework5.data.NewsItem
import at.technikum_wien.tscheppen.homework5.data.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class NewsListViewModel(application: Application) : AndroidViewModel(application){
    private val repository : Repository
    private val _newsItems : MutableLiveData<List<NewsItem>?>


    private val _error = MutableLiveData<Boolean>(false)
    val _errorString = MutableLiveData<String?>()
    private val _busy = MutableLiveData<Boolean>(true)

    init {
        val newsItemDao = ApplicationDatabase.getDatabase(application).entryDao()
        repository  = Repository(newsItemDao)
        _newsItems = repository.newsItems
        reload()

    }

    val identifier by lazy { MutableLiveData("") }
    val title by lazy { MutableLiveData("") }
    val link by lazy { MutableLiveData("") }
    val description by lazy { MutableLiveData("") }
    val imageUrl by lazy { MutableLiveData("") }
    val author by lazy { MutableLiveData("") }
    val publicationDate by lazy { MutableLiveData("") }
    val keywords by lazy { MutableLiveData("") }


    //val error by lazy { MutableLiveData<Boolean>(null) }

    val newsItems : MutableLiveData<List<NewsItem>?>
        get() = _newsItems
    val error : LiveData<Boolean>
        get() = _error
    val busy : LiveData<Boolean>
        get() = _busy



    fun insert() = viewModelScope.launch {


        _errorString.value = null
        val id = identifier.value ?: ""
        val ti = title.value ?: ""
        val li = link.value ?: ""
        val de = description.value ?: ""
        val imgUrl = imageUrl.value ?: ""
        val au = author.value ?: ""
        val pd = publicationDate.value ?: ""
        val kw = keywords.value ?: ""


        if (id.isEmpty()|| ti.isEmpty() || li.isEmpty() || de.isEmpty() || imgUrl.isEmpty()
            || au.isEmpty() || pd.isEmpty() || kw.isEmpty() ) {
            _errorString.value = getApplication<Application>().applicationContext.getString(R.string.input_error)
        } else {
            try {
                val entry = NewsItem(id, ti , li, de, imgUrl, au, pd, setOf(kw))
                withContext(Dispatchers.IO) {
                    repository.insert(entry)
                }
                identifier.value = ""
                title.value = ""
                link.value = ""
                description.value = ""
                imageUrl.value = ""
                author.value = ""
                publicationDate.value = ""
                keywords.value = ""

            } catch(ex : SQLiteConstraintException) {
                _errorString.value = getApplication<Application>().applicationContext.
                getString(R.string.constraint_error)
            } catch (ex : Exception) {
                _errorString.value = getApplication<Application>().applicationContext.
                getString(R.string.general_error)
            }

    }


        fun delete(entry: NewsItem) = viewModelScope.launch {
            _errorString.value = null
            try {
                withContext(Dispatchers.IO) {
                    repository.delete(entry)
                }
            } catch (ex : Exception) {
                _errorString.value = getApplication<Application>().applicationContext.
                getString(R.string.general_error)
            }
        }

    }

    private fun downloadNewsItems(newsFeedUrl: String) {
        _error.value = false
        _newsItems.value = listOf()
        _busy.value = true
        viewModelScope.launch {
            val value = NewsDownloader().load(newsFeedUrl)
            if (value == null)
                _error.value = true
            else
                _newsItems.value = value
            insert()
            _busy.value = false
        }
    }



    fun reload() {
        downloadNewsItems("https://www.engadget.com/rss.xml")
    }

}