/**
 * Unlicensed code created by A Softer Space, 2023
 * www.asofterspace.com/licenses/unlicense.txt
 */
package com.asofterspace.seebrueckeschedulecreator;

import com.asofterspace.toolbox.utils.Record;


public class EntryKind {

	private String title;

	private String color;

	private String code;


	public EntryKind(Record rec) {
		this.title = rec.getString("title");
		this.color = rec.getString("color");
		this.code = rec.getString("code");
	}

	public Record toRecord() {
		Record result = Record.emptyObject();
		result.set("title", title);
		result.set("color", color);
		result.set("code", code);
		return result;
	}

	public String getTitle() {
		return title;
	}

	public String getColor() {
		return color;
	}

	public String getCode() {
		return code;
	}

}
