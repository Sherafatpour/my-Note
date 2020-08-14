package name.sherafatpour.mynotepad

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
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
        noteViewModel.getAllNote()!!.observe(this) { notesList ->

            adapter.setNote(notesList)



        }

    }
}