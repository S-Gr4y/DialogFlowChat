package be.qaz.app.aichatbot;

import android.app.Application;

import be.qaz.app.aichatbot.component.dialogflow.TTS;

/**
 * Created by qaz on 09/02/2018.
 */

public class AiChatApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //TODO find something better than a singleton
        TTS.init(this);
    }
}
