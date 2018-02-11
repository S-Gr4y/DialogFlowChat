package be.qaz.app.aichatbot.aichat;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

import java.util.UUID;

import ai.api.model.AIError;
import ai.api.model.AIResponse;
import ai.api.ui.AIDialog;
import be.qaz.app.aichatbot.R;
import be.qaz.app.aichatbot.component.chatkit.Message;
import be.qaz.app.aichatbot.component.chatkit.User;
import be.qaz.app.aichatbot.component.dialogflow.DialogFlowConfig;
import be.qaz.app.aichatbot.component.dialogflow.PermissionUtil;
import be.qaz.app.aichatbot.component.dialogflow.TTS;

import static be.qaz.app.aichatbot.component.dialogflow.PermissionUtil.REQUEST_AUDIO_PERMISSIONS_ID;

/**
 * Created by qaz on 10/02/2018.
 */

public class AiChatFragment extends Fragment implements AIDialog.AIDialogListener, AiChatCallback {

    private MessagesList messagesList;
    private MessageInput messageInput;

    private MessagesListAdapter<Message> messagesAdapter;

    protected AIDialog aiDialog;

    private AiChatPresenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_aichat, container, false);

        messagesList = (MessagesList) view.findViewById(R.id.messagesList);
        messageInput = (MessageInput) view.findViewById(R.id.input);
        initChatAdapter();
        initVocal();

        return view;
    }

    private void initVocal() {
        aiDialog = new AIDialog(getActivity(), DialogFlowConfig.getConfig());
        aiDialog.setResultsListener(this);
    }

    private void initChatAdapter() {
        messagesAdapter = new MessagesListAdapter<Message>(mPresenter.getUser().getId(), null);

        messagesList.setAdapter(messagesAdapter);
        messageInput.setInputListener(new MessageInput.InputListener() {
            @Override
            public boolean onSubmit(CharSequence input) {
                String userInput = input.toString();
                addNewMessage(userInput, mPresenter.getUser());
                mPresenter.onUserTextInput(userInput);
                return true;
            }
        });

        messageInput.setAttachmentsListener(new MessageInput.AttachmentsListener() {
            @Override
            public void onAddAttachments() {
                PermissionUtil.checkAudioRecordPermission(getActivity());
                aiDialog.showAndListen();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.onResume();
        mPresenter.setAiCallback(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.onPause();
        mPresenter.setAiCallback(null);
    }

    public void setPresenter(AiChatPresenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void onResult(AIResponse result) {
        mPresenter.onAiDialogResult(result);
        addNewMessage(result.getResult().getResolvedQuery(), mPresenter.getUser());
    }

    @Override
    public void onResultFromPresenter(String text) {
        addNewMessage(text.trim(), mPresenter.getBot());
        TTS.speak(text);
    }

    @Override
    public void onError(AIError error) {
        addNewMessage(error.getMessage(), mPresenter.getBot());
    }

    @Override
    public void onCancelled() {

    }

    private void addNewMessage(@NonNull String text, @NonNull User user) {
        messagesAdapter.addToStart(
                new Message(UUID.randomUUID().toString(), user, text), true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_AUDIO_PERMISSIONS_ID: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }
}
