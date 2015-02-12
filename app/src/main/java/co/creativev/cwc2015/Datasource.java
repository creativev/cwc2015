package co.creativev.cwc2015;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import static co.creativev.cwc2015.Constant.LOG_TAG;

public class Datasource {
    public static final String TABLE = "matches";
    public static final String ID = "_id";
    public static final String[] ALL_COLS = new String[]{
            ID,
            "name",
// "group_name",
            "match_date",
            "team1", "score1", "overs1",
            "team2", "score2", "overs2",
            "venue", "won", "result"};
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final DbHelper dbHelper;
    private final SQLiteDatabase database;

    public Datasource(Context context) {
        this.dbHelper = new DbHelper(context);
        database = dbHelper.getReadableDatabase();
    }

    public Match getByPosition(int position) {
        String selection = "1=1 order by " + ID + " limit 1 offset ?";
        String[] args = {Integer.toString(position)};
        log(selection, args);
        Cursor cursor = database.query(TABLE, ALL_COLS, selection, args, null, null, null);
        if (!cursor.moveToNext()) return null;
        Match match = null;
        try {
            match = new Match(
                    cursor.getInt(0),
                    cursor.getString(1),
                    DATE_FORMAT.parse(cursor.getString(2)),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getString(7),
                    cursor.getString(8),
                    cursor.getString(9),
                    cursor.getString(10),
                    cursor.getString(11)
            );
        } catch (ParseException e) {
            Log.w(LOG_TAG, "Couldn't parse date " + cursor.getString(1));
        } finally {
            cursor.close();
        }
        return match;
    }

    private void log(String query, String[] args) {
        Log.d(LOG_TAG, String.format("Query: %s, Params: %s", query, Arrays.toString(args)));
    }

    public void close() {
        dbHelper.close();
    }

    public int getCount() {
        return (int) DatabaseUtils.longForQuery(database, "select count(*) from " + TABLE, null);
    }
}
