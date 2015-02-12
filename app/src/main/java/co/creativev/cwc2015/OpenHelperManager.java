package co.creativev.cwc2015;

import android.content.Context;

public class OpenHelperManager {
    private static Datasource dataSource;
    private static int count = 0;

    public static synchronized Datasource getDataSource(Context context) {
        if (dataSource == null) {
            dataSource = new Datasource(context);
        }
        count++;
        return dataSource;
    }

    public static synchronized void releaseHelper() {
        count--;
        if (count == 0) {
            dataSource.close();
            dataSource = null;
        }
    }

}
