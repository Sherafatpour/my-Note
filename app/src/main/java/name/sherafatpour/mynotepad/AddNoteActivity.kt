package name.sherafatpour.mynotepad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_note.*

class AddNoteActivity : AppCompatActivity() {

    companion object{

        var EXTRA_TITLE = "name.sherafatpour.mynotepad.EXTRA_TITLE"
        var EXTRA_DESCRIPTION = "name.sherafatpour.mynotepad.DESCRIPTION"
        var EXTRA_PRIORITY = "name.sherafatpour.mynotepad.PRIORITY"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        number_picker_priority.minValue = 1
        number_picker_priority.maxValue = 10

        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_close)

    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.add_node_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.save_note->{
                saveNote()
                Toast.makeText(this@AddNoteActivity,"added a Note",Toast.LENGTH_LONG).show()
                true
            }else->{

                super.onOptionsItemSelected(item)

            }

        }

    }

    private fun saveNote() {

        val title = edit_text_title.text.toString()
        val description = edit_text_description.text.toString()
        var priority = number_picker_priority.value

        if (title.trim().isEmpty() || description.trim().isEmpty()){

            Toast.makeText(this@AddNoteActivity,"Please insert  a title and description",Toast.LENGTH_LONG).show()

            return
        }


        val data = Intent()

        data.putExtra(EXTRA_TITLE,title)
        data.putExtra(EXTRA_DESCRIPTION,description)
        data.putExtra(EXTRA_PRIORITY,priority)
        setResult(RESULT_OK , data)
        finish()
    }
}