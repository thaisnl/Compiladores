package Frame;

import java.util.LinkedList;
import java.util.List;

import Mips.Codegen;
import Temp.Label;
import Temp.Temp;

public abstract class Frame {
	//coloquei aqui a assinatura de todos os métodos públicos do MipsFrame +
	//esses atributos que eram acessados lá mas não existiam lá
	public LinkedList<Access> formals;
	public Label name;
	public Assem.InstrList codegen(Tree.Stm stm) {
		return (new Codegen(this)).codegen(stm);
	}
	public abstract String programTail();
	public abstract Frame newFrame(envs.Symbol name, List<Boolean> formals);
	public abstract int wordSize();
	public abstract Access allocLocal(boolean escape);
	public abstract Temp FP();
	public abstract Temp RV();
	public abstract Tree.Exp externalCall(String s, List<Tree.Exp> args);
	public abstract String string(Label lab, String string);
	public abstract Label badPtr();
	public abstract Label badSub();
	public abstract String tempMap(Temp temp);
	public abstract void procEntryExit1(List<Tree.Stm> body);
	public abstract Temp[] registers();
}
