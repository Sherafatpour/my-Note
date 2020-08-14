package name.sherafatpour.mynotepad

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.note_item.view.*
import name.sherafatpour.mynotepad.room.Note

class NoteAdapter : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    private var notes :List<Note> = ArrayList()

    public fun setNote(notes: List<Note>){
        this.notes = notes
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.note_item,null,false))
    }

    override fun getItemCount(): Int {
return notes.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = notes[position]

        holder.bind(note)


    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        private var noteTitle:TextView?=null
        private var noteDescription:TextView?=null
        private var notePriority:TextView?=null


        init {
            noteTitle = itemView.noteTitle
            noteDescription = itemView.noteDescription
            notePriority = itemView.notePriority
        }


        fun bind(note: Note){
            noteTitle!!.text = note.title
            noteDescription!!.text = note.description
            notePriority!!.text = note.priority.toString()

        }

    }
}