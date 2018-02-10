package be.qaz.app.chatbotkit.dialogflow;

import ai.api.model.AIError;
import ai.api.model.AIResponse;

/**
 * Created by qaz on 10/02/2018.
 */

interface AiAsyncTaskCallback {

    void onTaskResult(AIResponse response);
    void onTaskError(AIError error);

}
