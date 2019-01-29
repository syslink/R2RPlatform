 
package com.pirobot.client.comparator;

import java.util.Comparator;

import com.pirobot.client.model.ScriptCommand;

public class CommandComparator  implements Comparator<ScriptCommand>{

	@Override
	public int compare(ScriptCommand arg0, ScriptCommand arg1) {
		return (int)(arg0.getSort()- arg1.getSort());
	}

}
