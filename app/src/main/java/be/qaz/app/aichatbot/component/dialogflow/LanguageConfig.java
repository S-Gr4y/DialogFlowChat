package be.qaz.app.aichatbot.component.dialogflow;

/**
 * Created by qaz on 09/02/2018.
 */

public class LanguageConfig {
    private final String languageCode;
    private final String accessToken;

    public LanguageConfig(final String languageCode, final String accessToken) {
        this.languageCode = languageCode;
        this.accessToken = accessToken;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public String getAccessToken() {
        return accessToken;
    }

    @Override
    public String toString() {
        return languageCode;
    }
}
