package com.example.app;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

import java.util.Iterator;
import java.util.Set;


public class MainActivity extends AppCompatActivity {

    EditText user_input;
    TextView displayEmoji, codeHTML, codeUnicode;
    JSONObject jsonObject;
    ClipboardManager clipboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user_input = (EditText) findViewById(R.id.editTextText);
        displayEmoji = (TextView) findViewById(R.id.textView);
        codeHTML = (TextView) findViewById(R.id.html_code);
        codeUnicode = (TextView) findViewById(R.id.unicode_code);
        clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);


        try {
            Log.i("Debug", "DEBUG: PRIMEIRO TRY ENTROU");
            AssetManager assetManager = getAssets();
            InputStream emojisInfos = assetManager.open("data.json");

            if (emojisInfos != null) {Log.i("Debug", "emojisInfos is not null");}
            else {Log.i("Debug", "emojisInfos is null");}

            int size = emojisInfos.available();
            byte[] buffer = new byte[size];
            emojisInfos.read(buffer);
            emojisInfos.close();

            String json = new String(buffer, "UTF-8");
            if (json != null) {
                Log.i("Debug", "json is not null");
                Log.i("Debug", json);
            }
            else {Log.i("Debug", "json is null");}

            jsonObject = new JSONObject(json);

            if (jsonObject != null) {Log.i("Debug", "jsonObject is not null");}
            else {Log.i("Debug", "jsonObject is null");}

            Log.i("Debug", "DEBUG: PRIMEIRO TRY SAIU");

        } catch (Exception e) {
            Log.i("Debug", "DEBUG: PRIMEIRO CATCH");
            e.printStackTrace();
        }
    }

    public void displayInfo(View view) {
        String emoji = user_input.getText().toString();
        displayEmoji.setText(emoji);
        Log.i("Debug", "DEBUG: " + emoji);

        try {
            Log.i("Debug", "DEBUG: SEGUNDO TRY ENTROU");
            JSONObject infos = jsonObject.getJSONObject(emoji);
            codeHTML.setText(infos.getString("html"));
            codeUnicode.setText(infos.getString("unicode"));
            Log.i("Debug", "DEBUG: SEGUNDO TRY SAIU");

        } catch (JSONException e) {
            Log.i("Debug", "DEBUG: SEGUNDO CATCH");
            e.printStackTrace(); // Criar um tratamento melhor para quando não tiver o emoji pesquisado
        }
    }

    public void copyInfo(View view) {
        ClipData clip = ClipData.newPlainText("label", codeUnicode.getText());
        clipboard.setPrimaryClip(clip);

    }
}