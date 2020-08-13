package name.sherafatpour.mynotepad

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import name.sherafatpour.mynotepad.room.Note
import name.sherafatpour.mynotepad.room.NoteDao
import name.sherafatpour.mynotepad.room.NoteDatabase

class NoteRepository(application: Application?) {
    private val noteDao: NoteDao
    val allNotes: LiveData<List<Note>>
    fun insert(note: Note?) {
        InsertNoteAsyncTask(noteDao).execute(note)
    }

    fun update(note: Note?) {
        UpdateNoteAsyncTask(noteDao).execute(note)
    }

    fun delete(note: Note?) {
        DeleteNoteAsyncTask(noteDao).execute(note)
    }

    fun deleteAllNotes() {
        DeleteAllNoteAsyncTask(noteDao).execute()
    }

    private class InsertNoteAsyncTask ( val noteDao: NoteDao) :
        AsyncTask<Note?, Void?, Void?>() {
        override fun doInBackground(vararg p0: Note?): Void? {
            noteDao.insert(p0[0])
            return null
        }


    }

    private class UpdateNoteAsyncTask( val noteDao: NoteDao) :
        AsyncTask<Note?, Void?, Void?>() {


        override fun doInBackground(vararg p0: Note?): Void? {
            noteDao.update(p0[0])
            return null

        }

    }

    private class DeleteNoteAsyncTask  (val noteDao: NoteDao) :
        AsyncTask<Note?, Void?, Void?>() {


        override fun doInBackground(vararg p0: Note?): Void? {
            noteDao.delete(p0[0])
            return null        }

    }

    private class DeleteAllNoteAsyncTask  constructor(private val noteDao: NoteDao) : AsyncTask<Note?, Void?, Void?>() {

        override fun doInBackground(vararg p0: Note?): Void? {
            noteDao.deleteAllNotes()
            return null
        }

    }

    init {
        val database = NoteDatabase.getInstance(application)
        noteDao = database.noteDao()
        allNotes = noteDao.allNotes
    }
}