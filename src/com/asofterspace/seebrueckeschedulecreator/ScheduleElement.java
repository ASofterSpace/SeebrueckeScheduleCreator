/**
 * Unlicensed code created by A Softer Space, 2023
 * www.asofterspace.com/licenses/unlicense.txt
 */
package com.asofterspace.seebrueckeschedulecreator;

import com.asofterspace.toolbox.utils.DateUtils;
import com.asofterspace.toolbox.utils.Record;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class ScheduleElement {

	private String title;

	private String kind;

	private String date;
	private String dateFrom;
	private String dateTo;

	private String time;

	private String color;

	private static final String FALLBACK_DATE_FORMAT_STR = "dd.MM.yyyy";
	private static SimpleDateFormat FALLBACK_DATE_FORMAT = new SimpleDateFormat(FALLBACK_DATE_FORMAT_STR);
	public static final String[] DAY_NAMES = new String[]{"Sa", "So", "Mo", "Di", "Mi", "Do", "Fr", "Sa", "So"};


	public ScheduleElement(Record rec, List<EntryKind> kinds) {
		this.title = rec.getString("title");
		this.kind = rec.getString("kind");
		this.date = rec.getString("date");
		this.dateFrom = rec.getString("dateFrom");
		this.dateTo = rec.getString("dateTo");
		this.time = rec.getString("time");

		for (EntryKind eKind : kinds) {
			if (kind.equals(eKind.getTitle())) {
				color = eKind.getColor();
			}
		}
	}

	public Record toRecord() {
		Record result = Record.emptyObject();
		result.set("title", title);
		result.set("kind", kind);
		result.setOrRemove("date", date);
		result.setOrRemove("dateFrom", dateFrom);
		result.setOrRemove("dateTo", dateTo);
		result.setOrRemove("time", time);
		return result;
	}

	public String getTitle() {
		return title;
	}

	public String getKind() {
		return kind;
	}

	public String getDate() {
		return date;
	}

	public String getDateFrom() {
		return dateFrom;
	}

	public String getDateTo() {
		return dateTo;
	}

	public Date getLastApplicableDate() {
		if (date == null) {
			return DateUtils.parseDate(dateTo);
		}
		return DateUtils.parseDate(date);
	}

	public String getDateString() {
		if (date == null) {
			return serializeDate(dateFrom) + " - " + serializeDate(dateTo);
		}
		return serializeDate(date);
	}

	public String getTime() {
		return time;
	}

	private String serializeDate(String date) {
		Date dateObj = DateUtils.parseDate(date);
		return DAY_NAMES[DateUtils.getDayOfWeek(dateObj)] + " " + FALLBACK_DATE_FORMAT.format(dateObj);
	}

	public String getColor() {
		return color;
	}

}
