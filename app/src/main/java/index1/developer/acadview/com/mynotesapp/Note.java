package index1.developer.acadview.com.mynotesapp;

import android.content.Context;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by user on 30-May-18.
 */

public class Note implements Serializable{

    private long mDateTime;
    private String mTitle; //title of the note
    private String mContent; //content of the note

    public Note(long DateTime, String Title, String Content) {
        this.mDateTime = DateTime;
        this.mTitle = Title;
        this.mContent = Content;

    }


    public long getmDateTime() {
        return mDateTime;
    }

    public void setmDateTime(long mDateTime)
    {
        this.mDateTime=mDateTime;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle)
    {
        this.mTitle=mTitle;
    }


    public String getmContent() {
        return mContent;
    }

    public void setmContent(String mContent) {
        this.mContent = mContent;
    }

    public String getDateTimeFormatted(Context context) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"
                , context.getResources().getConfiguration().locale);
        formatter.setTimeZone(TimeZone.getDefault());
        return formatter.format(new Date(mDateTime));
    }
}


