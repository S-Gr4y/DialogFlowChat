package be.qaz.app.aichatbot.aichat.interactor;

import ai.api.android.AIDataService;

/**
 * Created by qaz on 11/02/2018.
 */

public interface AiInteractor {

    AIDataService getAiService();

    void sendRequest(String textQuery);

    void cancelRequest();

    void setTaskCallback(AiInteractorTask mTaskCallback);
}
