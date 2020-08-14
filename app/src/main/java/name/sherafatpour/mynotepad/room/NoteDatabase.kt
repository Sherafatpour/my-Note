package name.sherafatpour.mynotepad.room

import android.app.Application
import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
    companion object {
        private var instance: NoteDatabase? = null

        @Synchronized
        fun getInstance(context: Application): NoteDatabase? {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java, "note_database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .allowMainThreadQueries()
                    .build()
            }
            return instance
        }

        private val roomCallback: Callback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                PopulateDbAsyncTask(instance).execute()
            }
        }
    }
    private class PopulateDbAsyncTask (db: NoteDatabase?) :
        AsyncTask<Void?, Void?, Void?>() {
        private val noteDao: NoteDao = db!!.noteDao()


        override fun doInBackground(vararg p0: Void?): Void? {
            noteDao.insert(Note("title1", "Description1", 1))
            noteDao.insert(Note("title2", "Description2", 2))
            noteDao.insert(Note("title3", "Description3", 3))
        return null
        }
    }


}