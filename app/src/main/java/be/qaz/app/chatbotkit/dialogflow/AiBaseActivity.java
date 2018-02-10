package be.qaz.app.chatbotkit.dialogflow;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;

import ai.api.android.AIConfiguration;
import ai.api.android.AIDataService;
import ai.api.model.AIError;
import ai.api.model.AIResponse;
import ai.api.ui.AIDialog;
import be.qaz.app.chatbotkit.R;

public class AiBaseActivity extends AppCompatActivity implements AiAsyncTaskCallback, AIDialog.AIDialogListener {

    private static final int REQUEST_AUDIO_PERMISSIONS_ID = 33;

    protected AIDataService aiDataService;
    protected AIDialog aiDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initVocal();
    }

    protected void initVocal() {
        final AIConfiguration config = new AIConfiguration(DialogFlowConfig.ACCESS_TOKEN,
                AIConfiguration.SupportedLanguages.French,
                AIConfiguration.RecognitionEngine.System);

        TTS.init(this);

        aiDataService = new AIDataService(this, config);

        aiDialog = new AIDialog(this, config);
        aiDialog.setResultsListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        aiDialog.setResultsListener(null);
    }

    protected void sendRequest(String textQuery) {

        if (TextUtils.isEmpty(textQuery)) {
            onTaskError(new AIError(getString(R.string.non_empty_query)));
            return;
        }

        new AiAsyncTask(aiDataService, this).execute(textQuery, null, null);
    }

    public void onTaskResult(AIResponse response) {

    }

    public void onTaskError(AIError error) {

    }

    @Override
    public void onResult(AIResponse result) {

    }

    @Override
    public void onError(AIError error) {

    }

    @Override
    public void onCancelled() {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    protected void checkAudioRecordPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.RECORD_AUDIO)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.RECORD_AUDIO},
                        REQUEST_AUDIO_PERMISSIONS_ID);

            }
        }
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
