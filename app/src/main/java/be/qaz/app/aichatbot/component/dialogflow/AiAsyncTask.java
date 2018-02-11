package be.qaz.app.aichatbot.component.dialogflow;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import java.util.Collections;
import java.util.List;

import ai.api.AIServiceException;
import ai.api.RequestExtras;
import ai.api.android.AIDataService;
import ai.api.model.AIContext;
import ai.api.model.AIError;
import ai.api.model.AIEvent;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;

/**
 * Created by qaz on 09/02/2018.
 */

public class AiAsyncTask extends AsyncTask<String, Void, AIResponse> {

    private static final String TAG = "AiAsyncTask";
    private AIDataService mAiDataService;
    private AIError mAiError;
    private AiAsyncTaskCallback mCallback;

    public AiAsyncTask(@NonNull AIDataService aiDataService, AiAsyncTaskCallback callback) {
        mAiDataService = aiDataService;
        mCallback = callback;
    }

    @Override
    protected AIResponse doInBackground(String... strings) {
        final AIRequest request = new AIRequest();
        String query = strings[0];
        String event = strings[1];

        if (!TextUtils.isEmpty(query))
            request.setQuery(query);
        if (!TextUtils.isEmpty(event))
            request.setEvent(new AIEvent(event));
        final String contextString = strings[2];
        RequestExtras requestExtras = null;
        if (!TextUtils.isEmpty(contextString)) {
            final List<AIContext> contexts = Collections.singletonList(new AIContext(contextString));
            requestExtras = new RequestExtras(contexts, null);
        }

        try {
            return mAiDataService.request(request, requestExtras);
        } catch (final AIServiceException e) {
            mAiError = new AIError(e);
            Log.e(TAG, mAiError.getMessage());
            mCallback.onTaskError(mAiError);
            return null;
        }
    }

    @Override
    protected void onPostExecute(AIResponse aiResponse) {
        super.onPostExecute(aiResponse);
        mCallback.onTaskResult(aiResponse);
    }
}
