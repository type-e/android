package com.typee.typee.sms;

import com.typee.typee.config.Config;
import com.typee.typee.util.CountDownTimer;

/**
 * Created by winsonlim on 28/1/14.
 */
public class SMSTimeOutHandler {

	private static SMSTimeOutHandler timeOutCountdown = null;
	private static TimeOutCountDownTimer timeoutCountdownTimer;

	private boolean countdownRunning = false;

	public SMSTimeOutHandler() {
	}

	/**
	 * A singleton of EventQuizTimeOutHandler
	 *
	 * @return an instance of the EventQuizTimeOutHandler
	 */
	public static SMSTimeOutHandler getTimer() {
		if (timeOutCountdown == null) {
			timeOutCountdown = new SMSTimeOutHandler();
		}

		return timeOutCountdown;
	}

	/**
	 * Starts a countdown timer with a default value
	 */
	public void startTime() {
		countdownRunning = true;

		if (timeoutCountdownTimer != null)
			timeoutCountdownTimer.cancel();
		else
			timeoutCountdownTimer = new TimeOutCountDownTimer();

		if (timeoutCountdownTimer != null) {
			timeoutCountdownTimer.start();
		}
	}

	/**
	 * Starts a countdown timer with value passed
	 *
	 * @param countdown duration of countdown in seconds
	 */
	public void startTime(int countdown) {
		countdownRunning = true;

		if (timeoutCountdownTimer != null)
			timeoutCountdownTimer.cancel();
		else
			timeoutCountdownTimer = new TimeOutCountDownTimer(countdown);

		if (timeoutCountdownTimer != null) {
			timeoutCountdownTimer.start();
		}
	}

	/**
	 * Resets the countdown timer with default value
	 */
	public void resetTimer() {
		stopTimer();
		startTime();
	}

	/**
	 * Stops the current countdown timer
	 */
	public void stopTimer() {
		countdownRunning = false;

		if (timeoutCountdownTimer != null) timeoutCountdownTimer.cancel();
		timeoutCountdownTimer = null;
	}

	/**
	 * Checks if the countdown timer is still running
	 *
	 * @return True; if timer is still running. False; if timer has stopped or finished
	 */
	public boolean isCountdownRunning() {
		return countdownRunning;
	}

	/**
	 * Get the remaining time on the countdown timer
	 *
	 * @return the remaining time in seconds
	 */
	public int getRemainingTime() {
		if (timeoutCountdownTimer != null) {
			return timeoutCountdownTimer.timeLeft;
		}

		return 0;
	}

	private class TimeOutCountDownTimer extends CountDownTimer {
		public int timeLeft = 0;   // in seconds

		public TimeOutCountDownTimer() {
			super(Config.SMS_TOKEN_TIMEOUT * 1000, 1000);
		}

		public TimeOutCountDownTimer(int newDuration) {
			super(newDuration * 1000, 1000);
		}

		@Override
		public void onFinish() {
			countdownRunning = false;
			timeLeft = 0;
		}

		@Override
		public void onTick(long millisUntilFinished) {
			timeLeft = (int) (((millisUntilFinished + 500) / 1000) % 60);
		}
	}
}
