package at.technikum_wien.tscheppen.homework4.viewmodels

import androidx.lifecycle.*
import at.technikum_wien.tscheppen.homework4.data.NewsDownloader
import at.technikum_wien.tscheppen.homework4.data.NewsItem
import kotlinx.coroutines.launch

class NewsListViewModel : ViewModel(){
    private val _newsItems = MutableLiveData<List<NewsItem>?>(listOf())
    private val _error = MutableLiveData<Boolean>(false)
    private val _busy = MutableLiveData<Boolean>(true)

    init {
        reload()
    }

    val newsItems : MutableLiveData<List<NewsItem>?>
        get() = _newsItems
    val error : LiveData<Boolean>
        get() = _error
    val busy : LiveData<Boolean>
        get() = _busy

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
            _busy.value = false
        }
    }

    fun reload() {
        downloadNewsItems("https://www.engadget.com/rss.xml")
    }

}