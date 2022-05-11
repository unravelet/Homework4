package at.technikum_wien.tscheppen.homework4.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import at.technikum_wien.tscheppen.homework4.R
import at.technikum_wien.tscheppen.homework4.data.NewsItem

class DetailsActivity : AppCompatActivity() {
    companion object {
        const val ITEM_KEY = "item"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        val item = intent?.extras?.getSerializable(ITEM_KEY) as? NewsItem
        if (item != null) {
            findViewById<TextView>(R.id.tv_title).text = item.title
            findViewById<TextView>(R.id.tv_description).text = item.description
            findViewById<TextView>(R.id.tv_author).text = item.author
            findViewById<TextView>(R.id.tv_image_url).text = item.imageUrl
            findViewById<TextView>(R.id.tv_publication_date).text = item.publicationDate.toString()
            findViewById<TextView>(R.id.tv_keywords).text = item.keywords.joinToString("\n")
        }
    }
}