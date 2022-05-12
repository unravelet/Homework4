package at.technikum_wien.tscheppen.homework4.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import at.technikum_wien.tscheppen.homework4.R
import at.technikum_wien.tscheppen.homework4.data.NewsItem
import com.bumptech.glide.Glide
import android.net.Uri
import android.os.Build

import org.jsoup.Jsoup
import android.text.Html;

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


            //findViewById<TextView>(R.id.tv_description).text = Jsoup.parseBodyFragment(item.description, "UTF-8").toString()
            val tvDescription = findViewById<TextView>(R.id.tv_description)

            //parse html
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                tvDescription.text = Html.fromHtml(item.description, Html.FROM_HTML_MODE_LEGACY)
            }else {
                tvDescription.text = Html.fromHtml(item.description)
            }



            val itemViewImage = findViewById<ImageView>(R.id.imageView)
            Glide.with(this).load(item.imageUrl).into(itemViewImage)

            val fullStoryButton = findViewById<Button>(R.id.details_btn)

            fullStoryButton.setOnClickListener{
                val openURL = Intent(Intent.ACTION_VIEW)
                openURL.data = Uri.parse(item.link)
                startActivity(openURL)
            }


        }
    }
}