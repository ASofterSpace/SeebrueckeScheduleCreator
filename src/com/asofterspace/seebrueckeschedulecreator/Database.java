/**
 * Unlicensed code created by A Softer Space, 2023
 * www.asofterspace.com/licenses/unlicense.txt
 */
package com.asofterspace.seebrueckeschedulecreator;

import com.asofterspace.toolbox.io.JSON;
import com.asofterspace.toolbox.io.JsonFile;
import com.asofterspace.toolbox.io.JsonParseException;
import com.asofterspace.toolbox.utils.Record;
import com.asofterspace.toolbox.utils.SortOrder;
import com.asofterspace.toolbox.utils.SortUtils;
import com.asofterspace.toolbox.utils.Stringifier;

import java.util.ArrayList;
import java.util.List;


public class Database {

	private JsonFile dbFile;

	private JSON root;

	private List<EntryKind> kinds;

	private List<ScheduleElement> schedules;


	public Database() {

		this.dbFile = new JsonFile("input/input.json");
		this.dbFile.createParentDirectory();
		try {
			this.root = dbFile.getAllContents();
		} catch (JsonParseException e) {
			System.err.println("Oh no!");
			e.printStackTrace(System.err);
			System.exit(1);
		}

		List<Record> kindRecs = root.getArray("kinds");

		this.kinds = new ArrayList<>();

		for (Record rec : kindRecs) {
			EntryKind cur = new EntryKind(rec);
			kinds.add(cur);
			// DEBUG OUTPUT System.out.println(cur);
		}

		List<Record> scheduleRecs = root.getArray("schedule");

		this.schedules = new ArrayList<>();

		for (Record rec : scheduleRecs) {
			schedules.add(new ScheduleElement(rec, kinds));
		}

		Stringifier<ScheduleElement> sortStringifier = new ScheduleElementStringifier();
		schedules = SortUtils.sort(schedules, SortOrder.ALPHABETICAL, sortStringifier);
	}

	public Record getRoot() {
		return root;
	}

	public void save() {

		root.makeObject();

		List<Record> kindRecs = new ArrayList<>();

		for (EntryKind kind : kinds) {
			kindRecs.add(kind.toRecord());
		}

		root.set("kinds", kindRecs);

		List<Record> scheduleRecs = new ArrayList<>();

		for (ScheduleElement scheduleElem : schedules) {
			scheduleRecs.add(scheduleElem.toRecord());
		}

		root.set("schedule", scheduleRecs);

		dbFile.setAllContents(root);
		dbFile.save();
	}

	public List<EntryKind> getKinds() {
		return kinds;
	}

	public List<ScheduleElement> getSchedules() {
		return schedules;
	}

}
