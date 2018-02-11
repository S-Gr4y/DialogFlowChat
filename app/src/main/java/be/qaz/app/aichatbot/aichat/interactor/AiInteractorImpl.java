package be.qaz.app.aichatbot.aichat.interactor;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import ai.api.android.AIConfiguration;
import ai.api.android.AIDataService;
import ai.api.model.AIError;
import ai.api.model.AIResponse;
import be.qaz.app.aichatbot.component.dialogflow.AiAsyncTask;
import be.qaz.app.aichatbot.component.dialogflow.AiAsyncTaskCallback;

/**
 * Created by qaz on 11/02/2018.
 */

public class AiInteractorImpl implements AiInteractor, AiAsyncTaskCallback {

    private static final String TAG = "AiInteractorImpl";
    private AIDataService aiDataService;
    private AsyncTask<String, Void, AIResponse> mAiResponseAsyncTask;
    private AiInteractorTask mTaskCallback;

    public AiInteractorImpl(Context context, AIConfiguration aiConfiguration) {
        aiDataService = new AIDataService(context, aiConfiguration);
    }

    @Override
    public AIDataService getAiService() {
        return aiDataService;
    }

    public void sendRequest(String textQuery) {

        if (TextUtils.isEmpty(textQuery)) {
            onTaskError(new AIError("Request can't be null."));
            return;
        }

        mAiResponseAsyncTask = new AiAsyncTask(aiDataService, this).execute(textQuery, null, null);
    }

    @Override
    public void cancelRequest() {
        if(mAiResponseAsyncTask != null) {
            mAiResponseAsyncTask.cancel(true);
        }
    }

    @Override
    public void onTaskResult(AIResponse response) {
        if(mTaskCallback != null) {
            mTaskCallback.onTaskResult(response);
        }
    }

    @Override
    public void onTaskError(AIError error) {
        Log.e(TAG, error.getMessage());
        if(mTaskCallback != null) {
            mTaskCallback.onTaskError(error);
        }
    }

    public void setTaskCallback(AiInteractorTask mTaskCallback) {
        this.mTaskCallback = mTaskCallback;
    }
}
