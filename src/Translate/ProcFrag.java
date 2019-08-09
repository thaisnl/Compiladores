package Translate;

//dado no livro
public class ProcFrag extends Frag {
	Tree.Stm body;
	Frame.Frame frame;
	
	public ProcFrag(Tree.Stm body, Frame.Frame frame){
		this.body = body;
		this.frame = frame;
	}
}
