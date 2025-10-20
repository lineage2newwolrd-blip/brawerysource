package ru.akumu.smartguard;

import smartguard.core.utils.LogUtils;

@Deprecated
public class SmartGuard {
	@Deprecated
	public static void main(String[] args) {
		LogUtils.log("Warning! You are using old SmartGuard interface!");
		LogUtils.log("Change \"ru.akumu.smartguard.SmartGuard\" to \"smartguard.SmartGuard\" in your server start script.");

		smartguard.SmartGuard.main(args);
	}
}
