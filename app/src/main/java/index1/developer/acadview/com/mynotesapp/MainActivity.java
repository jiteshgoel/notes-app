package index1.developer.acadview.com.mynotesapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView ls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ls = (ListView) findViewById(R.id.main_listview);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_create: //run NoteActivity in new note mode
                startActivity(new Intent(this, NoteActivity.class));
                break;

            case R.id.action_settings:
                //TODO show settings activity
                break;
        }

        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        ls.setAdapter(null);

        ArrayList<Note> notes = Utilities.getAllSavedNotes(this);

        if(notes == null || notes.size() == 0)
        {
            Toast.makeText(this, " No saved notes", Toast.LENGTH_SHORT).show();
            return;
        }else
        {
            NotesAdapter na = new NotesAdapter(this, R.layout.item_note, notes);
            ls.setAdapter(na);
            ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String filename = ((Note)ls.getItemAtPosition(i)).getmDateTime()
                            + Utilities.FILE_EXTENSION;

                    Intent viewNoteIntent = new Intent(getApplicationContext(), NoteActivity.class);
                    viewNoteIntent.putExtra(Utilities.EXTRAS_NOTE_FILENAME, filename);
                    startActivity(viewNoteIntent);
                }
            });
        }
    }
}


