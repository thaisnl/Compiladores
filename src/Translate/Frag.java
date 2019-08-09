package Translate;

//classe dada no livro
public abstract class Frag {
	public Frag next;
	
	public void appendFrag(Frag n) {
		this.next = n;
	}
}
