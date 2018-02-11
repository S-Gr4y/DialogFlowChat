package be.qaz.app.aichatbot.aichat;

import ai.api.model.AIError;

/**
 * Created by qaz on 11/02/2018.
 */

public interface AiChatCallback {

    void onResultFromPresenter(String text);

    void onError(AIError error);

}
