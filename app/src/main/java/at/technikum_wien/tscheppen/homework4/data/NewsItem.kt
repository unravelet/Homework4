package at.technikum_wien.tscheppen.homework4.data

import java.io.Serializable
import java.util.*

data class NewsItem(var identifier : String,
                    var title : String,
                    var link : String?,
                    var description : String?,
                    var imageUrl : String?,
                    var author : String?,
                    var publicationDate : Date,
                    var keywords : Set<String>) : Serializable