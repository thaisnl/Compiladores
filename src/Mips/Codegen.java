package Mips;

import Assem.OPER;
import Temp.TempList;
import Tree.*;

public class Codegen {
	Frame.Frame frame;

	public Codegen(Frame.Frame f) {
		this.frame = f;
	}

	private Assem.InstrList ilist = null, last = null;

	private void emit(Assem.Instr inst) {
		if (last != null) {
			last = last.tail = new Assem.InstrList(inst, null);
		} else {
			last = ilist = new Assem.InstrList(inst, null);
		}
	}
	
	void munchStm(Stm s){
		if(s instanceof SEQ){
			munchStm(((SEQ)s).left);
			munchStm(((SEQ)s).right);
		}
		else if(s instanceof MOVE){
			munchStmMove((MOVE)s);
		}
		else if(s instanceof LABEL){
			munchStmLabel((LABEL)s);
		}
		//acho que precisa do CALL, JUMP e CJUMP também pelo q entendi nos slides
	}

	void munchStmMove(MOVE s) {
		if(s.src instanceof MEM && ((MEM)s.src).exp instanceof BINOP && ((BINOP)((MEM)s.src).exp).right instanceof CONST){
			emit(new Assem.OPER("sw s1, " + ((CONST)((BINOP)((MEM)s.src).exp).right).value + "(s0)\n", null,
				new Temp.TempList(munchExp(((BINOP)((MEM)s.src).exp).left),
				new Temp.TempList(munchExp(s.dst), null))));
		} 
		else if(s.src instanceof MEM && ((MEM)s.src).exp instanceof BINOP && ((BINOP)((MEM)s.src).exp).left instanceof CONST){
			emit(new Assem.OPER("sw s1, " + ((CONST)((BINOP)((MEM)s.src).exp).left).value + "(s0)\n", null,
					new Temp.TempList(munchExp(((BINOP)((MEM)s.src).exp).right),
					new Temp.TempList(munchExp(s.dst), null))));
		} 
		//moveM no mips? vou botar um sw
		else if(s.src instanceof MEM && s.dst instanceof MEM) {
			emit(new Assem.OPER("sw s0, s1\n", null,
					new Temp.TempList(munchExp(((MEM)s.src).exp),
					new Temp.TempList(munchExp(((MEM)s.dst).exp), null))));
		}
		
		else if(s.src instanceof MEM && ((MEM)s.src).exp instanceof CONST) {
			emit(new Assem.OPER("sw s1, " + ((CONST)((MEM)s.src).exp).value + "(s0)\n", null,
					new Temp.TempList(munchExp(s.dst), null)));
		}
		
		else if(s.src instanceof MEM) {
			emit(new Assem.OPER("sw s1, 0(s0)\n)", null,
					new Temp.TempList(munchExp(((MEM)s.src).exp),
					new Temp.TempList(munchExp(s.dst), null))));
		}
		
		else if(s.src instanceof TEMP) {
			emit(new Assem.OPER("add d0, s0, $zero\n", 
					new Temp.TempList(((TEMP)s.src).temp,null),
					new Temp.TempList(munchExp(s.dst),null)));
		}
	
	}

	void munchStmLabel(LABEL s){
			emit(new Assem.LABEL(s.toString() + ":\n", s.label));
	}

	Temp.Temp munchExp(Exp e){
		if(e instanceof MEM){
            return munchExpMem((MEM)e);
		}
		else if(e instanceof BINOP){
            return munchExpBinop((BINOP)e);
		}
		else if(e instanceof CONST){
            return munchExpConst((CONST)e);
		}
		else{
            return munchExpTemp((TEMP)e);
		}
                
	}

	Temp.Temp munchExpMem(MEM e){
		Temp.Temp r = new Temp.Temp();
		if(e.exp instanceof BINOP && ((BINOP)e.exp).binop==BINOP.PLUS && ((BINOP)e.exp).right instanceof CONST) {
			emit(new Assem.OPER("lw d0, " + ((CONST)((BINOP)e.exp).right).value + "(s0)\n",
				new Temp.TempList(r, null), new Temp.TempList(munchExp(((BINOP)e.exp).left), null)));
		}
			
		else if(((MEM)e).exp instanceof BINOP && ((BINOP)e.exp).binop==BINOP.PLUS && ((BINOP)e.exp).left instanceof CONST) {
			emit(new Assem.OPER("lw d0, " + ((CONST)((BINOP)e.exp).left).value + "(s0)\n",
				new Temp.TempList(r, null), new Temp.TempList(munchExp(((BINOP)e.exp).right), null)));
		}
			
		else if(((MEM)e).exp instanceof CONST) {
			emit(new Assem.OPER("lw d0, " + ((CONST)e.exp).value + "($zero)\n",
				new Temp.TempList(r, null), null));
		}
		else{
			emit(new OPER("lw d0, 0(s0)\n",
				new Temp.TempList(r, null), new Temp.TempList(munchExp(e.exp), null)));
		}
		return r;
	}

	Temp.Temp munchExpBinop(BINOP e){
		Temp.Temp r = new Temp.Temp();
		if(e.binop == BINOP.PLUS && e.right instanceof CONST) {
			emit(new Assem.OPER("addi d0, s0," +  ((CONST)e.right).value + "\n",
				new Temp.TempList(r, null), new Temp.TempList(munchExp(e.left), null)));
		}
			
		else if(e.binop == BINOP.PLUS && e.left instanceof CONST) {
			emit(new Assem.OPER("addi d0, s0, " +  ((CONST)e.left).value + "\n",
				new Temp.TempList(r, null), new Temp.TempList(munchExp(e.right), null)));
		}

		else if(e.binop == BINOP.PLUS) {
			emit(new Assem.OPER("add d0, s0, s1\n",
				new Temp.TempList(r, null), 
				new Temp.TempList(munchExp(e.left), new Temp.TempList(munchExp(e.right), null))));
		}

		//vou colocar o minus e o mul pq tem na sintaxe do minijava, ai tem que ver o AND
		else if(e.binop == BINOP.MINUS) {
			emit(new Assem.OPER("sub d0, s0, s1\n",
				new Temp.TempList(r, null), 
				new Temp.TempList(munchExp(e.left), new Temp.TempList(munchExp(e.right), null))));
		}

		else if(e.binop == BINOP.MUL) {
			emit(new Assem.OPER("mult d0, s0, s1\n",
				new Temp.TempList(r, null), 
				new Temp.TempList(munchExp(e.left), new Temp.TempList(munchExp(e.right), null))));
		}
		
		else if(e.binop == BINOP.AND) {
			emit(new Assem.OPER("and d0, s0, s1\n",
					new Temp.TempList(r, null), 
					new Temp.TempList(munchExp(e.left), new Temp.TempList(munchExp(e.right), null))));
		}
		
		return r;
	}

	Temp.Temp munchExpConst(CONST e){
		Temp.Temp r = new Temp.Temp();
		//r0 é o $zero no mips, segundo o google
		emit(new Assem.OPER("addi d0, $zero, " + e.value + "\n",null, null));
		return r;
	}

	Temp.Temp munchExpTemp(TEMP e){
		return ((TEMP)e).temp;
	}

	public Assem.InstrList codegen(Stm s) {
		Assem.InstrList l;
		munchStm(s);
		l = ilist;
		ilist = last = null;
		return l;
	}



}