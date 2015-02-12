package co.creativev.cwc2015;

import android.util.Log;

import com.google.android.gms.analytics.GoogleAnalytics;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static co.creativev.cwc2015.Constant.DB_NAME;
import static co.creativev.cwc2015.Constant.LOG_TAG;

public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        initDb();
        GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
        analytics.newTracker(R.xml.tracker);
    }

    private void initDb() {
        File databaseFile = getApplicationContext().getDatabasePath(DB_NAME);
        File parent = databaseFile.getParentFile();
        if (!parent.exists())
            if (!parent.mkdirs())
                Log.w(LOG_TAG, "Failed to create database directory");
        if (!databaseFile.exists()) {
            try {
                copyDatabase(databaseFile);
            } catch (IOException e) {
                throw new RuntimeException("Error creating source database", e);
            }
        }
    }

    private void copyDatabase(File databaseFile) throws IOException {
        InputStream is = getApplicationContext().getAssets().open(DB_NAME);
        OutputStream os = new FileOutputStream(databaseFile);

        byte[] buffer = new byte[1024];
        while (is.read(buffer) > 0) {
            os.write(buffer);
        }

        os.flush();
        os.close();
        is.close();
    }

}
