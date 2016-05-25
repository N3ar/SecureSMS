package com.research.near.encryptedsms;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;

public class SmsSender extends Activity {

    Button sendButton;
    Button backButton;
    EditText recipientRaw;
    EditText contentRaw;


    @Override
    protected void onCreate(Bundle instanceState) {
        super.onCreate(instanceState);
        setContentView(R.layout.send_sms);
        sendButton = (Button) findViewById(R.id.sendButton);
        backButton = (Button) findViewById(R.id.backButton);
        recipientRaw = (EditText) findViewById(R.id.recipientNumber);
        contentRaw = (EditText) findViewById(R.id.messageContent);
        sendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if(contentRaw.getText().toString().length() <= 160 &&
                        !contentRaw.getText().toString().isEmpty()) {
                    sendMessage();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Message body is not between 1 and 160 characters.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(SmsSender.this, SmsReader.class);
                startActivity(intent);
            }
        });
    }

    protected void sendMessage() {
        String recipient = recipientRaw.getText().toString();
        String content = contentRaw.getText().toString();
        try {
            EncryptSMS encryptMsg = new EncryptSMS(recipient);
            String encContent = encryptMsg.AESenc(content);
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(recipient, null, encContent, null, null);
            Toast.makeText(getApplicationContext(), "SMS sent.", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Sending SMS failed.",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}
