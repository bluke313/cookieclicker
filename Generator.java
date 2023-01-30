package cookieclicker;

import cs2.Button;
import cs2.TextShape;

public class Generator {

	private MyButton button;
	private TextShape text;
	private String type;
	private double CPSPG; // cookies per second per generator
	private int count;
	private double cost;
	
	/**
	 * (type, CPSPG, count, cost)
	 * @param type
	 * @param CPSPG
	 * @param cost
	 */
	public Generator(String type, double CPSPG, int cost) {
		this.type = type;
		button = new MyButton();
		button.setGen(this);
		text = new TextShape(0, 0, "");
		this.CPSPG = CPSPG;
		this.cost = cost;
		count = 0;
	}

	public Button getButton() { return button; }
	public TextShape getText() { return text; }
	public String getType() { return type; }
	public int getCount() { return count; }
	public double getCost() { return cost; }
	public double getCPSPG() { return CPSPG; }
	
	public double getCPS() { // get cookies per second
		return CPSPG * count;
	}
	
	public void purchase() {
		count++;
		CookieCalculator.cookieCount -= cost;
		cost *= 1.15;
	}
	
	public void sell() {
		count--;
		cost /= 1.15;
		
	}
}
