package Translate;

public class Translate {
	// lista encadeada de fragmentos
	private Frag head = null;
	private Frag frags = null;

	// essa aqui vai "lembrar" um ProcFrag
	public void procEntryExit(Tree.Stm body, Frame.Frame currFrame){
		ProcFrag newfrag = new ProcFrag(body, currFrame);
		if (frags == null) {
			frags = newfrag;
			head.next = frags;
		}
		else {
			frags.next = newfrag;
			frags = newfrag;
		}
	}

	// essa aqui vai extrair a lista de fragmentos
	public Frag getResult() {
		return head.next;
	}

}
