package com.example.texttalk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    EditText e1;
    Button b1;
    TextToSpeech tts;

    Spinner langSpinner;

    HashMap<String, Locale> languageMap =new HashMap<String, Locale>(){{
        put("English (UK)",Locale.UK);
        put("English (US)", Locale.US);
        put("Hindi", new Locale("hi","IN"));
        put("Marathi", new Locale("mr", "IN"));

    }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        e1=findViewById(R.id.getTextt);
        b1=findViewById(R.id.speakbtns);
        langSpinner=findViewById(R.id.spinnerLang);

        ArrayAdapter<String> adapter =new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_dropdown_item,
                languageMap.keySet().toArray(new String[0])
        );
        langSpinner.setAdapter(adapter);

        tts=new TextToSpeech(this, status -> {
            if (status==TextToSpeech.SUCCESS){
                tts.setLanguage(Locale.UK);
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt=e1.getText().toString();
                String selectLang=langSpinner.getSelectedItem().toString();
                if (!txt.isEmpty())
                {
                    Locale selectLocale=languageMap.get(selectLang);
                    int result=tts.setLanguage(selectLocale);

                    if (result==TextToSpeech.LANG_MISSING_DATA || result==TextToSpeech.LANG_NOT_SUPPORTED)
                    {
                        Toast.makeText(MainActivity.this, "Selected Language Not Supported", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        tts.speak(txt, TextToSpeech.QUEUE_FLUSH, null, null);

                    }
                }
            }
        });



    }
}