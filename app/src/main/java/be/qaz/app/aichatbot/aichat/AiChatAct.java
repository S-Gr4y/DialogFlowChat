package be.qaz.app.aichatbot.aichat;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import be.qaz.app.aichatbot.R;
import be.qaz.app.aichatbot.aichat.interactor.AiInteractorImpl;
import be.qaz.app.aichatbot.component.dialogflow.DialogFlowConfig;

/**
 * Created by qaz on 11/02/2018.
 */

public class AiChatAct extends AppCompatActivity {

    private AiChatPresenter mAiChatPresenter;
    private AiChatFragment mAiChatFragment;
    private FrameLayout mFragContainer;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_aichat);

        mFragContainer = (FrameLayout) findViewById(R.id.frag_container);

        mAiChatPresenter = new AiChatPresenterImpl(new AiInteractorImpl(this, DialogFlowConfig.getConfig()));
        mAiChatFragment = new AiChatFragment();
        mAiChatFragment.setPresenter(mAiChatPresenter);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frag_container, mAiChatFragment).commit();
    }
}
