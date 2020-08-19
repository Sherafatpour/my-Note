package name.sherafatpour.mynotepad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import name.sherafatpour.mynotepad.room.Note

class MainActivity : AppCompatActivity() {
    companion object{val ADD_NOTE_REQUEST = 1 }
    lateinit var noteViewModel: NoteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel::class.java);
        noteRecyclerview.layoutManager = LinearLayoutManager(this)
        noteRecyclerview.setHasFixedSize(true)
        val adapter = NoteAdapter()
        noteRecyclerview.adapter = adapter

       ItemTouchHelper( object : ItemTouchHelper.SimpleCallback(0 , ItemTouchHelper.LEFT or  ItemTouchHelper.RIGHT ){
           override fun onMove(
               recyclerView: RecyclerView,
               viewHolder: RecyclerView.ViewHolder,
               target: RecyclerView.ViewHolder
           ): Boolean {
               return false
           }

           override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

               Toast.makeText(this@MainActivity,"Note Removed",Toast.LENGTH_LONG).show()
               noteViewModel.delete(adapter.getNoteAt(viewHolder.adapterPosition))

           }

       }).attachToRecyclerView(noteRecyclerview)

        noteViewModel.getAllNote()!!.observe(this) { notesList ->

            adapter.setNote(notesList)

        }


        addNote.setOnClickListener {
            val intent = Intent(this@MainActivity,AddNoteActivity::class.java )
            startActivityForResult(intent, ADD_NOTE_REQUEST)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.main_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.delete_all_notes->{
                noteViewModel.deleteAllNotes()
                Toast.makeText(this@MainActivity,"Delete All Notes",Toast.LENGTH_LONG).show()
                return true
            }else->{

            return super.onOptionsItemSelected(item)

        }

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK){

            val  title = data!!.getStringExtra(AddNoteActivity.EXTRA_TITLE)
            val  desc = data.getStringExtra(AddNoteActivity.EXTRA_DESCRIPTION)
            val  priority = data.getIntExtra(AddNoteActivity.EXTRA_PRIORITY,1)

            val note = Note(title!!,desc!!,priority);
            noteViewModel.insert(note)

            Toast.makeText(this@MainActivity,"Note Saved",Toast.LENGTH_LONG).show()


        }

    }

}