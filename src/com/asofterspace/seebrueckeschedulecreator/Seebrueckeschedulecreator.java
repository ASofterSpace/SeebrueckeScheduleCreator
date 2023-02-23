/**
 * Unlicensed code created by A Softer Space, 2023
 * www.asofterspace.com/licenses/unlicense.txt
 */
package com.asofterspace.seebrueckeschedulecreator;

import com.asofterspace.toolbox.images.ColorRGBA;
import com.asofterspace.toolbox.io.TextFile;
import com.asofterspace.toolbox.utils.DateUtils;
import com.asofterspace.toolbox.Utils;

import java.util.List;


public class Seebrueckeschedulecreator {

	public final static String PROGRAM_TITLE = "SeebrueckeScheduleCreator";
	public final static String VERSION_NUMBER = "0.0.0.1(" + Utils.TOOLBOX_VERSION_NUMBER + ")";
	public final static String VERSION_DATE = "11th of January 2023 - 11th of January 2023";

	public static void main(String[] args) {

		// let the Utils know in what program it is being used
		Utils.setProgramTitle(PROGRAM_TITLE);
		Utils.setVersionNumber(VERSION_NUMBER);
		Utils.setVersionDate(VERSION_DATE);

		if (args.length > 0) {
			if (args[0].equals("--version")) {
				System.out.println(Utils.getFullProgramIdentifierWithDate());
				return;
			}

			if (args[0].equals("--version_for_zip")) {
				System.out.println("version " + Utils.getVersionNumber());
				return;
			}
		}

		System.out.println("Loading database...");

		Database database = new Database();

		System.out.println("Saving database...");

		database.save();

		TextFile outFile = new TextFile("output/schedule.htm");
		StringBuilder html = new StringBuilder();
		html.append("<html>");
		html.append("<head>");
		html.append("<meta charset=\"utf-8\">");
		html.append("</head>");
		html.append("<body>");

		html.append("<div style='width: 400px;'>");
		List<ScheduleElement> schedule = database.getSchedules();
		for (ScheduleElement scheduleElem : schedule) {
			if (scheduleElem.getLastApplicableDate().before(DateUtils.now())) {
				continue;
			}
			ColorRGBA mainColor = ColorRGBA.fromString(scheduleElem.getColor());
			ColorRGBA lighterColor = ColorRGBA.intermix(mainColor, ColorRGBA.WHITE, 0.5);
			html.append("<div style='background: linear-gradient(17deg, " + lighterColor.toHexString() + ", #FFF, " + mainColor.toHexString() + ");border-radius: 12px;padding: 4px 8px;margin: 2px;box-shadow: 3px 3px 5px 3px #DDD;'>");
			html.append("<div>");
			html.append(scheduleElem.getTitle());
			html.append("</div>");
			html.append("<div style='position: relative;'>");
			html.append(scheduleElem.getDateString());
			if (scheduleElem.getTime() != null) {
				html.append(" ");
				html.append(scheduleElem.getTime());
			}
			html.append("<div style='position: absolute; top: 0; right: 0;'>");
			html.append(scheduleElem.getCode());
			html.append("</div>");
			html.append("</div>");
			html.append("</div>");
		}
		html.append("</div>");

		html.append("<div style='padding-left: 150px;padding-top: 14px;'>");
		List<EntryKind> kinds = database.getKinds();
		for (EntryKind kind : kinds) {
			html.append("<div style='position: relative;'>");
			html.append("<div style='position: absolute; left: -50pt; width: 48pt; text-align: right;'>");
			html.append(kind.getCode() + ":");
			html.append("</div>");
			html.append("<span style='width: 16px; border-radius: 8px; height: 16px; display: inline-block; background: " + kind.getColor() + "; vertical-align: middle; margin-right: 6px;'>");
			html.append("</span>");
			html.append(kind.getTitle());
			html.append("</div>");
		}
		html.append("</div>");

		html.append("</body>");
		html.append("</html>");
		outFile.saveContent(html.toString());

		System.out.println("Done! Have a nice day! :)");
	}

}
