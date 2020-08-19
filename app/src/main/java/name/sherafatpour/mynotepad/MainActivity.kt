package name.sherafatpour.mynotepad

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
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

    }
}