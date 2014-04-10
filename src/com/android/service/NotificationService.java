package com.android.service;

import android.content.Context;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;

public class NotificationService {

	public static void wakeUpScreen(Context context) {

		PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);

		boolean isScreenOn = pm.isScreenOn();
		WakeLock wl = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.FULL_WAKE_LOCK
				| PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.ON_AFTER_RELEASE, "MyLock");
		
		WakeLock wl_cpu = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MyCpuLock");
		if (isScreenOn == false) {
			wl.acquire(50000);
			wl_cpu.acquire(10000);

		}
		
	}
}
