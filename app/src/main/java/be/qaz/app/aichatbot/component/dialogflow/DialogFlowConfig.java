package be.qaz.app.aichatbot.component.dialogflow;

import ai.api.android.AIConfiguration;

/**
 * Created by qaz on 12/02/2018.
 */

public class DialogFlowConfig {
    public static AIConfiguration getConfig() {
        return new AIConfiguration(ACCESS_TOKEN, ai.api.AIConfiguration.SupportedLanguages.French, AIConfiguration.RecognitionEngine.System);
    }

    public static final String ACCESS_TOKEN = "your_dialog_flow_token_here";
}
