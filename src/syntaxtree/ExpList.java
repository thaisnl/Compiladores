package syntaxtree;

import java.util.Vector;
import visitor.*;

public class ExpList {
	public Vector<Exp> list;

	public ExpList() {
		list = new Vector<Exp>();
	}

	public void addElement(Exp n) {
		list.addElement(n);
	}

	public Exp elementAt(int i) {
		return (Exp) list.elementAt(i);
	}

	public int size() {
		return list.size();
	}
}