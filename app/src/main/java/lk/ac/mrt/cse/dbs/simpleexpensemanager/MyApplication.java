package lk.ac.mrt.cse.dbs.simpleexpensemanager;

import android.app.Application;
import android.content.Context;

/**
 * Created by Neo_ on 12/5/2015.
 */
public class MyApplication extends Application {
    private static Context context;

    public void onCreate(){
        context=getApplicationContext();
    }

    public static Context getCustomAppContext(){
        return context;
    }

}
