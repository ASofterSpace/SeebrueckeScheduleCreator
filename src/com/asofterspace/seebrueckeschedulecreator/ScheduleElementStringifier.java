/**
 * Unlicensed code created by A Softer Space, 2023
 * www.asofterspace.com/licenses/unlicense.txt
 */
package com.asofterspace.seebrueckeschedulecreator;

import com.asofterspace.toolbox.utils.Stringifier;


public class ScheduleElementStringifier implements Stringifier<ScheduleElement> {

	public String getString(ScheduleElement elem) {
		if (elem.getDate() == null) {
			return elem.getDateFrom();
		}
		return elem.getDate();
	}
}
