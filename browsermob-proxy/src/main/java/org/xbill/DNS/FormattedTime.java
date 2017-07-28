// Copyright (c) 1999-2004 Brian Wellington (bwelling@xbill.org)

package org.xbill.DNS;

/**
 * Routines for converting time values to and from YYYYMMDDHHMMSS format.
 *
 * @author Brian Wellington
 */

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

// TODO: Auto-generated Javadoc
/**
 * The Class FormattedTime.
 */
final class FormattedTime {

	/** The w4. */
	private static NumberFormat w2, w4;

	static {
		w2 = new DecimalFormat();
		w2.setMinimumIntegerDigits(2);

		w4 = new DecimalFormat();
		w4.setMinimumIntegerDigits(4);
		w4.setGroupingUsed(false);
	}

	/**
	 * Instantiates a new formatted time.
	 */
	private FormattedTime() {
	}

	/**
	 * Converts a Date into a formatted string.
	 * 
	 * @param date
	 *            The Date to convert.
	 * @return The formatted string.
	 */
	public static String format(Date date) {
		Calendar c = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
		StringBuffer sb = new StringBuffer();

		c.setTime(date);
		sb.append(w4.format(c.get(Calendar.YEAR)));
		sb.append(w2.format(c.get(Calendar.MONTH) + 1));
		sb.append(w2.format(c.get(Calendar.DAY_OF_MONTH)));
		sb.append(w2.format(c.get(Calendar.HOUR_OF_DAY)));
		sb.append(w2.format(c.get(Calendar.MINUTE)));
		sb.append(w2.format(c.get(Calendar.SECOND)));
		return sb.toString();
	}

	/**
	 * Parses a formatted time string into a Date.
	 * 
	 * @param s
	 *            The string, in the form YYYYMMDDHHMMSS.
	 * @return The Date object.
	 * @throws TextParseException
	 *             the text parse exception
	 */
	public static Date parse(String s) throws TextParseException {
		Calendar c = new GregorianCalendar(TimeZone.getTimeZone("UTC"));

		if (s.length() != 14) {
			throw new TextParseException("Invalid time encoding: " + s);
		}
		try {
			int year = Integer.parseInt(s.substring(0, 4));
			int month = Integer.parseInt(s.substring(4, 6)) - 1;
			int date = Integer.parseInt(s.substring(6, 8));
			int hour = Integer.parseInt(s.substring(8, 10));
			int minute = Integer.parseInt(s.substring(10, 12));
			int second = Integer.parseInt(s.substring(12, 14));
			c.set(year, month, date, hour, minute, second);
		} catch (NumberFormatException e) {
			throw new TextParseException("Invalid time encoding: " + s);
		}
		return c.getTime();
	}

}