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
	private Boolean showDate;

	private String time;

	private String color;
	private String code;

	private static final String FALLBACK_DATE_FORMAT_STR = "dd.MM.yyyy";
	private static SimpleDateFormat FALLBACK_DATE_FORMAT = new SimpleDateFormat(FALLBACK_DATE_FORMAT_STR);
	public static final String[] DAY_NAMES = new String[]{"Sa", "So", "Mo", "Di", "Mi", "Do", "Fr", "Sa", "So"};


	public ScheduleElement(Record rec, List<EntryKind> kinds) {
		this.title = rec.getString("title");
		this.kind = rec.getString("kind");
		this.date = rec.getString("date");
		this.dateFrom = rec.getString("dateFrom");
		this.dateTo = rec.getString("dateTo");
		this.showDate = rec.getBoolean("showDate", true);
		this.time = rec.getString("time");

		if (this.kind == null) {
			System.out.println("Entry '" + this.title + "' on " + this.date + " has NO kind whatsoever!");
			return;
		}

		for (EntryKind eKind : kinds) {
			if (this.kind.equals(eKind.getTitle())) {
				color = eKind.getColor();
				code = eKind.getCode();
				return;
			}
		}

		System.out.println("Encountered kind '" + this.kind + "' in entry '" + this.title + "' on " + this.date + ", " +
			"but no entry kind has this kind as title! (It should be title, not code, that is referenced!)");
	}

	public Record toRecord() {
		Record result = Record.emptyObject();
		result.set("title", title);
		result.set("kind", kind);
		result.setOrRemove("date", date);
		result.setOrRemove("dateFrom", dateFrom);
		result.setOrRemove("dateTo", dateTo);
		if (showDate) {
			result.remove("showDate");
		} else {
			result.set("showDate", false);
		}
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
		if (date != null) {
			return DateUtils.parseDate(date);
		}

		if (dateTo != null) {
			return DateUtils.parseDate(dateTo);
		}

		System.out.println("Entry " + title + " has no date at all assigned!");
		return DateUtils.now();
	}

	public String getDateString() {
		if (!showDate) {
			return "";
		}
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

	public String getCode() {
		return code;
	}

	@Override
	public String toString() {
		return "ScheduleElement [title: " + this.title + ", kind: " + this.kind + ", date: " + this.date + ", dateFrom: " + this.dateFrom + ", dateTo: " + this.dateTo + ", showDate: " + this.showDate + ", time: " + this.time + ", color: " + this.color + ", code: " + this.code + "]";
	}

}
