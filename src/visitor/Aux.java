package visitor;

public class Aux {
	public String varName;
	public Frame.Access access;

	public Aux(String v, Frame.Access a) {
		this.varName = v;
		this.access = a;
	}
	
	public String getVarName() {
		return varName;
	}
}
