package cookieclicker;

public class CookieCalculator {

	public static double cookieCount;
	private final int CPC[] = {1, 2, 3, 5, 10, 20, 30, 50, 75, 100};
	private final int UpgradeCosts[] = {50, 200, 400, 1000, 5000, 10000, 100000, 100000, 100000, -1};
	private Generator generators[];
	private int CPCIndex;
	
	public CookieCalculator() {
	    CPCIndex = 0;
		cookieCount = 0;
		generators = new Generator[4];
		generators[0] = new Generator("Oven", 0.1, 10);
		generators[1] = new Generator("Grandma", 1, 30);
		generators[2] = new Generator("Farm", 2.5, 50);
		generators[3] = new Generator("Factory", 5, 100); 
		
	}

	public int getCPC() { return CPC[CPCIndex]; }
	
	public Generator[] getGenerators() {
		return generators;
	}
	
	public double getCPS() { 
		double totalCPS = 0;
		for (Generator gen : generators) {
			totalCPS += gen.getCPS();
		}
		return totalCPS; 
		}
	
	public void cookieClick() { cookieCount += CPC[CPCIndex]; }

	public void applyCPS() {
		cookieCount += getCPS() * 0.01;
	}
	
	public int getUpgradeCost() {
	    return UpgradeCosts[CPCIndex];
	}
	
	public void increaseCPC() {
	    if (CPCIndex < CPC.length - 1) {
	        cookieCount -= UpgradeCosts[CPCIndex];
	        CPCIndex += 1;
	    }
	}
}
