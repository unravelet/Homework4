package at.technikum_wien.tscheppen.homework5.data

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [NewsItem::class], version = 1, exportSchema = false)
abstract class ApplicationDatabase : RoomDatabase() {
    abstract fun entryDao() : NewsItemDao

    companion object {
        @Volatile
        private var INSTANCE : ApplicationDatabase? = null

        fun getDatabase(context : Context) : ApplicationDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null)
                return tempInstance
            synchronized(this) {
                val tempInstance2 = INSTANCE
                if (tempInstance2 != null)
                    return tempInstance2
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ApplicationDatabase::class.java,
                    "telephone_book"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}