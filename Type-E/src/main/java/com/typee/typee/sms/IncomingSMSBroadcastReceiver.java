package com.typee.typee.sms;

/**
 * Created by winsonlim on 15/2/14.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import com.typee.typee.config.Config;
import com.typee.typee.network.model.SMS;
import com.typee.typee.ui.main.MainApplication;

public class IncomingSMSBroadcastReceiver extends BroadcastReceiver {

	public void onReceive(Context context, Intent intent) {
		final Bundle bundle = intent.getExtras();
		MainApplication.getEventBus().register(this);

		try {
			if (bundle != null) {
				final Object[] messages = (Object[]) bundle.get("pdus");

				for (int i = 0; i < messages.length; i++) {

					SmsMessage currentSMS = SmsMessage.createFromPdu((byte[]) messages[i]);
					String phoneNumber = currentSMS.getDisplayOriginatingAddress();

					String senderNum = phoneNumber;
					String message = currentSMS.getDisplayMessageBody();

					if (senderNum.equalsIgnoreCase(Config.APP_NAME)) {

						MainApplication.getEventBus().post(new SMS(senderNum, message));
					}
				}
			}

		} catch (Exception e) {
			MainApplication.getEventBus().post(new SMS(null, null));
		} finally {
			MainApplication.getEventBus().unregister(this);
		}
	}
}

