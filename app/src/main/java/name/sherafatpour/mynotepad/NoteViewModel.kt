package name.sherafatpour.mynotepad

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import name.sherafatpour.mynotepad.room.Note

class NoteViewModel(application: Application) : AndroidViewModel(application) {
   private var  repository: NoteRepository = NoteRepository(application)
    private val allNotes: LiveData<List<Note>>

    init {
        allNotes = repository.allNotes
    }

    fun insert(note: Note?) {
        repository.insert(note)
    }

    fun update(note: Note?) {
        repository.update(note)
    }

    fun delete(note: Note?) {
        repository.delete(note)
    }

    fun deleteAllNotes() {
        repository.deleteAllNotes()
    }

    fun getAllNote(): LiveData<List<Note>>? {
        return allNotes
    }

}