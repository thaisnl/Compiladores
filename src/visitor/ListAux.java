package visitor;

import java.util.ArrayList;
import java.util.Iterator;

public class ListAux {
	public ArrayList<Aux> aList = new ArrayList<Aux>();

	public void addAux(Aux a) {
		this.aList.add(a);
	}

	public Frame.Access getAccess(String varName) {
		Iterator<Aux> i = aList.iterator();
		while (i.hasNext()) {
			if(i.next().varName.equals(varName)) {
				return i.next().access;
			}
		}
		return null;

	}

}
