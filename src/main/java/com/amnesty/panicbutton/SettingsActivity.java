package com.amnesty.panicbutton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.amnesty.panicbutton.model.SMSSettings;
import com.amnesty.panicbutton.sms.SMSAdapter;
import com.amnesty.panicbutton.sms.SMSSettingsActivity;
import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

@ContentView(R.layout.settings_layout)
public class SettingsActivity extends RoboActivity {
    @InjectView(R.id.activate_alert) private Button activateButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initActivateButton();
    }

    private void initActivateButton() {
        SMSSettings smsSettings = SMSSettings.retrieve(this);

        if (smsSettings.isConfigured()) {
            activateButton.setEnabled(true);
        } else {
            activateButton.setEnabled(false);
        }
    }

    public void launchSmsActivity(View view) {
        startActivity(new Intent(this, SMSSettingsActivity.class));
    }

    public void activateAlert(View view) {
        SMSSettings smsSettings = SMSSettings.retrieve(this);
        SMSAdapter smsAdapter = getSMSAdapter();
        for (String phoneNumber : smsSettings.validPhoneNumbers()) {
            smsAdapter.sendSMS(phoneNumber, smsSettings.message());
        }
    }

    SMSAdapter getSMSAdapter() {
        return new SMSAdapter();
    }
}