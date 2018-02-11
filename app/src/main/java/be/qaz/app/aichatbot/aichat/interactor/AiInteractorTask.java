package be.qaz.app.aichatbot.aichat.interactor;

import ai.api.model.AIError;
import ai.api.model.AIResponse;

/**
 * Created by qaz on 11/02/2018.
 */

public interface AiInteractorTask {

    void onTaskResult(AIResponse response);
    void onTaskError(AIError error);

}
