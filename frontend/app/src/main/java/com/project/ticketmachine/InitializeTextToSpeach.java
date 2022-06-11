package com.project.ticketmachine;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.Locale;

public class InitializeTextToSpeach {

    TextToSpeech textToSpeech;

    public InitializeTextToSpeach(Context context) {
        textToSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i != textToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.ROOT);
                    Log.e("InitializeTextToSpeach", "OK");
                } else {
                    Log.e("InitializeTextToSpeach", "ERROR");

                }
            }
        });
    }

    public void speak(String text) {
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    public void destroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }

    public void stop() {
        textToSpeech.stop();
    }

    public static void speakAfer(InitializeTextToSpeach initializeTextToSpeach) {

    }
}
