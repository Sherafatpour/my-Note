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

        var EXTRA_ID = "name.sherafatpour.mynotepad.EXTRA_ID"
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

if (intent.hasExtra(EXTRA_ID)){

    title = "Edite Note"
    edit_text_title.setText(intent.getStringExtra(EXTRA_TITLE).toString())
    edit_text_description.setText(intent.getStringExtra(EXTRA_DESCRIPTION).toString())
    number_picker_priority.value = intent.getIntExtra(EXTRA_TITLE,1)



}else{

    title = "Add Note"

}

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
                true
            }else->{

                super.onOptionsItemSelected(item)

            }

        }

    }

    private fun saveNote() {

        val title = edit_text_title.text.toString()
        val description = edit_text_description.text.toString()
        val priority = number_picker_priority.value


            if (title.trim().isEmpty() || description.trim().isEmpty()){

            Toast.makeText(this@AddNoteActivity,"Please insert  a title and description",Toast.LENGTH_LONG).show()

            return
        }


        val data = Intent()
        data.putExtra(EXTRA_TITLE,title)
        data.putExtra(EXTRA_DESCRIPTION,description)
        data.putExtra(EXTRA_PRIORITY,priority)

        val id  = intent.getIntExtra(EXTRA_ID,-1)
        if (id != -1){
            data.putExtra(EXTRA_ID,id)

        }
        setResult(RESULT_OK , data)
        finish()

    }
}