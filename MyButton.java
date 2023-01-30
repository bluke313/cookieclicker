package cookieclicker;

public class MyButton extends cs2.Button {
    
	public MyButton() {
		super();
	}
	
	private Generator gen;
	
	public void setGen(Generator gen) {	this.gen = gen; }
	public Generator getGen() { return gen; }
}
