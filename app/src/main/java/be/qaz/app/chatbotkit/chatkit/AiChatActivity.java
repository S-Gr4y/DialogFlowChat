package be.qaz.app.chatbotkit.chatkit;

import android.support.annotation.NonNull;
import android.os.Bundle;
import android.util.Log;

import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

import java.util.UUID;

import ai.api.model.AIError;
import ai.api.model.AIResponse;
import be.qaz.app.chatbotkit.R;
import be.qaz.app.chatbotkit.dialogflow.AiBaseActivity;
import be.qaz.app.chatbotkit.dialogflow.TTS;

public class AiChatActivity extends AiBaseActivity {

    private static final String TAG = "AiChatActivity";
    private MessagesList messagesList;
    protected MessagesListAdapter<Message> messagesAdapter;
    private User mUser = new User("User");
    private User mAiBot = new User("AiBot");
    private MessageInput messageInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        this.messagesList = (MessagesList) findViewById(R.id.messagesList);

        messageInput = (MessageInput) findViewById(R.id.input);

        initAdapter();
    }

    private void initAdapter() {
        messagesAdapter = new MessagesListAdapter<Message>(mUser.getId(), null);

        messagesList.setAdapter(messagesAdapter);
        messageInput.setInputListener(new MessageInput.InputListener() {
            @Override
            public boolean onSubmit(CharSequence input) {
                String text = input.toString();
                addUserChatMessage(text);
                return true;
            }
        });

        messageInput.setAttachmentsListener(new MessageInput.AttachmentsListener() {
            @Override
            public void onAddAttachments() {
                checkAudioRecordPermission();
                aiDialog.showAndListen();
            }
        });
    }

    private void addUserChatMessage(String text) {
        messagesAdapter.addToStart(
                new Message(UUID.randomUUID().toString(), mUser, text), true);
        sendRequest(text);
    }

    @Override
    public void onTaskResult(AIResponse response) {
        super.onTaskResult(response);
        extractAction(response);
        addBotChatMessage(extractSpeech(response));
    }

    @Override
    public void onResult(AIResponse result) {
        super.onResult(result);
        //here is result coming from the AiDialog
        extractAction(result);
        addUserChatMessage(result.getResult().getResolvedQuery());
    }

    private String extractAction(@NonNull AIResponse response) {
        return response.getResult().getAction();
    }

    private void addBotChatMessage(@NonNull String textMessage) {
        messagesAdapter.addToStart(
                new Message(UUID.randomUUID().toString(), mAiBot, textMessage.trim()), true);
        TTS.speak(textMessage);
    }

    @Override
    public void onTaskError(AIError error) {
        super.onTaskError(error);
        String errorMessage = error.getMessage();
        Log.e(TAG, errorMessage);
        addBotChatMessage(errorMessage);
    }

    private String extractSpeech(AIResponse response) {
        return response.getResult().getFulfillment().getSpeech();
    }
}
