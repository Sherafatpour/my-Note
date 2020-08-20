package name.sherafatpour.mynotepad

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.note_item.view.*
import name.sherafatpour.mynotepad.room.Note

class NoteAdapter: ListAdapter<Note, NoteAdapter.ViewHolder?>(DIFF_CALLBACK)  {

    companion object{

val DIFF_CALLBACK =object :DiffUtil.ItemCallback<Note>(){
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {

return  oldItem.id == newItem.id;

    }

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {

return oldItem.title == newItem.title &&
        oldItem.description == newItem.description &&
        oldItem.priority == newItem.priority

    }

}
    }

    lateinit var listener:OnItemClickListener

  fun getNoteAt(position:Int):Note{

      return getItem(position)
  }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.note_item,null,false))
    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = getItem(position)

        holder.bind(note,listener)


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


        fun bind(note: Note, listener: OnItemClickListener){
            noteTitle!!.text = note.title
            noteDescription!!.text = note.description
            notePriority!!.text = note.priority.toString()
            itemView.setOnClickListener {
                     listener.onItemClick(note)
            }
        }

    }

    interface OnItemClickListener{
        fun onItemClick(note: Note)

    }

    fun setOnItemClickListener(listener:OnItemClickListener){
this.listener = listener

    }
}