package be.qaz.app.aichatbot.aichat;

import ai.api.model.AIResponse;
import be.qaz.app.aichatbot.component.chatkit.User;

/**
 * Created by qaz on 11/02/2018.
 */

public interface AiChatPresenter {

    void onResume();

    void onPause();

    void onUserTextInput(String text);

    void onAiDialogResult(AIResponse result);

    User getUser();

    User getBot();

    void setAiCallback(AiChatCallback callback);
}
