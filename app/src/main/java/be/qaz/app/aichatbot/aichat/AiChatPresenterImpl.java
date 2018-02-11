package be.qaz.app.aichatbot.aichat;

import android.support.annotation.NonNull;

import java.util.UUID;

import ai.api.model.AIError;
import ai.api.model.AIResponse;
import be.qaz.app.aichatbot.aichat.interactor.AiInteractorImpl;
import be.qaz.app.aichatbot.aichat.interactor.AiInteractorTask;
import be.qaz.app.aichatbot.component.chatkit.User;

/**
 * Created by qaz on 11/02/2018.
 */

public class AiChatPresenterImpl implements AiChatPresenter, AiInteractorTask {

    private AiInteractorImpl mAiInteractor;
    private User mUserBot;
    private User mUser;
    private AiChatCallback mAiCallback;

    public AiChatPresenterImpl(@NonNull AiInteractorImpl aiInteractor) {
        mAiInteractor = aiInteractor;
        mAiInteractor.setTaskCallback(this);
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onUserTextInput(String text) {
        mAiInteractor.sendRequest(text);
    }

    @Override
    public User getUser() {
        if(mUser == null) {
            //TODO get from repo
            mUser = new User(UUID.randomUUID().toString(), "User", null, true);
        }
        return mUser;
    }

    @Override
    public void onAiDialogResult(AIResponse result) {
        mAiInteractor.sendRequest(result.getResult().getResolvedQuery());
    }

    @Override
    public User getBot() {
        if(mUserBot == null) {
            //TODO get from repo
            mUserBot = new User(UUID.randomUUID().toString(), "AiBot", null, true);
        }
        return mUserBot;
    }

    @Override
    public void setAiCallback(AiChatCallback callback) {
        mAiCallback = callback;
    }

    @Override
    public void onTaskResult(AIResponse response) {
        mAiCallback.onResultFromPresenter(response.getResult().getFulfillment().getSpeech());
    }

    @Override
    public void onTaskError(AIError error) {
        mAiCallback.onError(error);
    }
}
