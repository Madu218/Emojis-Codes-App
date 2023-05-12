package com.example.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;


public class MainActivity extends AppCompatActivity {

    EditText user_input;
    TextView displayEmoji, codeHTML, codeUnicode;
    JSONObject jsonObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user_input = (EditText) findViewById(R.id.editTextText);
        displayEmoji = (TextView) findViewById(R.id.textView);
        codeHTML = (TextView) findViewById(R.id.html_code);
        codeUnicode = (TextView) findViewById(R.id.unicode_code);

        try {
            AssetManager assetManager = getAssets();
            InputStream emojisInfos = assetManager.open("data.json");

            int size = emojisInfos.available();
            byte[] buffer = new byte[size];
            emojisInfos.read(buffer);
            emojisInfos.close();

            String json = new String(buffer, "UTF-8");
            jsonObject = new JSONObject(json);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void displayInfo(View view) {
        String emoji = user_input.getText().toString();
        displayEmoji.setText(emoji);

        try {
            JSONObject infos = jsonObject.getJSONObject(emoji);
            codeHTML.setText(infos.getString("html"));
            codeUnicode.setText(infos.getString("unicode"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}