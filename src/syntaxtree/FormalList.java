package syntaxtree;

import java.util.Vector;
import visitor.*;

public class FormalList {
	private Vector<Formal> list;

	public FormalList() {
		list = new Vector<Formal>();
	}

	public void addElement(Formal n) {
		list.addElement(n);
	}

	public Formal elementAt(int i) {
		return (Formal) list.elementAt(i);
	}

	public int size() {
		return list.size();
	}
}