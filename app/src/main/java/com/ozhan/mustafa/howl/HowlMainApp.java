package com.ozhan.mustafa.howl;

import android.app.Application;

/**
 * Created by Mustafa Özhan on 3/4/17 at 10:42 AM.
 */

public class HowlMainApp extends Application {

    private static boolean isIsChatActivityOpen = false;

    public static boolean isChatActivityOpen() {
        return isIsChatActivityOpen;
    }

    public static void setChatActivityOpen(boolean isChatActivityOpen) {
        HowlMainApp.isIsChatActivityOpen = isChatActivityOpen;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
