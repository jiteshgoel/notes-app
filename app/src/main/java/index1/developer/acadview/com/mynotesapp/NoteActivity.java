package index1.developer.acadview.com.mynotesapp;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class NoteActivity extends AppCompatActivity {

    private EditText title_et;
    private EditText body_et;
    private String mFileName;
    private Note mLoadedNote = null;
    private boolean mIsViewingOrUpdating; //state of the activity
    private long mNoteCreationTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        title_et=(EditText)findViewById(R.id.title_edit);
        body_et=(EditText)findViewById(R.id.body_edit);

        mFileName = getIntent().getStringExtra(Utilities.EXTRAS_NOTE_FILENAME);
        if(mFileName != null && !mFileName.isEmpty() && mFileName.endsWith(Utilities.FILE_EXTENSION)) {
            mLoadedNote = Utilities.getNoteByFileName(getApplicationContext(), mFileName);
            if (mLoadedNote != null) {
                //update the widgets from the loaded note
                title_et.setText(mLoadedNote.getmTitle());
                body_et.setText(mLoadedNote.getmContent());

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note_new, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.action_save_note:
                saveNote();
                break;

            case R.id.action_cancel:
                deleteNote();
                break;
        }
        return  true;
    }

    private void deleteNote() {

        if(mLoadedNote == null)
        {
            finish();
        }
        else
        {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Delete Note")
                    .setMessage("Are you sure you want to delete "+title_et.getText().toString()+" note ?")
                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Utilities.deleteNote(getApplicationContext(), mLoadedNote.getmDateTime()+ Utilities.FILE_EXTENSION);
                            Toast.makeText(getApplicationContext(),title_et.getText().toString()+" note has been deleted", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    })
                    .setNegativeButton("NO", null)
                    .setCancelable(false);

            alert.show();


        }
    }

    private void saveNote()
    {
        Note note;
        if(title_et.getText().toString().trim().isEmpty() && body_et.getText().toString().trim().isEmpty())
        {
            Toast.makeText(this,"Please enter your note info", Toast.LENGTH_SHORT).show();
            return;
        }
        if (title_et.getText().toString().isEmpty())
        {
            Toast.makeText(this,"Enter Note titile", Toast.LENGTH_SHORT).show();
            return;
        }
        if (body_et.getText().toString().isEmpty())
        {
            Toast.makeText(this,"Enter Content of your note", Toast.LENGTH_SHORT).show();
            return;
        }
        if(mLoadedNote == null){
             note=new Note(System.currentTimeMillis(), title_et.getText().toString(), body_et.getText().toString());
        }
        else
        {
            note=new Note(mLoadedNote.getmDateTime(), title_et.getText().toString(), body_et.getText().toString());
        }

        if(Utilities.saveNote(this,note))
        {
            Toast.makeText(this, "note has been saved", Toast.LENGTH_SHORT).show();

        }
        else
        {
            Toast.makeText(this, "can not save the note. make sure you have enough space " +
                    "on your device", Toast.LENGTH_SHORT).show();
        }

        finish();
    }
}
