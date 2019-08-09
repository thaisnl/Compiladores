package envs;

import syntaxtree.*;
import java.util.HashMap;

public class MainTable {
	public HashMap<Symbol, ClassTable> mainTable;

	public MainTable() {
		mainTable = new HashMap<Symbol, ClassTable>();
	}

	public boolean addClass(Symbol c, ClassTable cTable) {
		if (mainTable.get(c) == null) {
			mainTable.put(c, cTable);
			return true;
		}
		return false;
	}

	public boolean removeClass(Symbol c) {
		if (mainTable.get(c) == null) {
			return false;
		}
		mainTable.remove(c);
		return true;
	}

	public ClassTable getClass(Symbol c) {
		return mainTable.get(c);
	}
}