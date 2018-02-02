package com.DanielSpindelbauer.ScraphEEp.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils
{
	private static final String IPADDRESS_PATTERN = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
	private static Pattern pattern = Pattern.compile(IPADDRESS_PATTERN);
	private static Matcher matcher = null;

	public static boolean isIP(final String ip)
	{
		matcher = pattern.matcher(ip);
		return matcher.matches();
	}
}
