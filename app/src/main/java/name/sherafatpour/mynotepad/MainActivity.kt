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
import kotlinx.android.synthetic.main.note_item.*
import name.sherafatpour.mynotepad.room.Note

class MainActivity : AppCompatActivity() {

    companion object{
        val ADD_NOTE_REQUEST = 1
        val EDIT_NOTE_REQUEST = 2
    }
    lateinit var noteViewModel: NoteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       val adapter = NoteAdapter()
        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel::class.java);
        noteRecyclerview.layoutManager = LinearLayoutManager(this)
        noteRecyclerview.setHasFixedSize(true)
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

            adapter.submitList(notesList)

        }


        addNote.setOnClickListener {
            val intent = Intent(this@MainActivity,AddNoteActivity::class.java )
            startActivityForResult(intent, ADD_NOTE_REQUEST)
        }


        adapter.setOnItemClickListener(object : NoteAdapter.OnItemClickListener{
            override fun onItemClick(note: Note) {

                val intent =Intent(this@MainActivity , AddNoteActivity::class.java)
                intent.putExtra(AddNoteActivity.EXTRA_ID , note.id)
                intent.putExtra(AddNoteActivity.EXTRA_TITLE , note.title)
                intent.putExtra(AddNoteActivity.EXTRA_DESCRIPTION , note.description)
                intent.putExtra(AddNoteActivity.EXTRA_PRIORITY , note.priority)
                startActivityForResult(intent, EDIT_NOTE_REQUEST)

            }

        })

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


        }else if(requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK){


            val  id = data!!.getIntExtra(AddNoteActivity.EXTRA_ID,-1)
            val  title = data.getStringExtra(AddNoteActivity.EXTRA_TITLE)
            val  desc = data.getStringExtra(AddNoteActivity.EXTRA_DESCRIPTION)
            val  priority = data.getIntExtra(AddNoteActivity.EXTRA_PRIORITY,1)

            if (id == -1){

                Toast.makeText(this@MainActivity,"Can't be updated",Toast.LENGTH_LONG).show()
                return
            }
            val note = Note(title!!,desc!!,priority);
            note.id = id
            noteViewModel.update(note)
            Toast.makeText(this@MainActivity,"Note Updated",Toast.LENGTH_LONG).show()

        }else{
            Toast.makeText(this@MainActivity,"Note Not Saved",Toast.LENGTH_LONG).show()


        }

    }

}