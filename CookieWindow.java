package cookieclicker;

import java.awt.Color;
import java.util.Random;

import cs2.Button;
import cs2.CircleShape;
import cs2.TextShape;
import cs2.Window;
import cs2.WindowSide;


public class CookieWindow {
	
	private Random rand;
	
	private static Color BROWN = new Color(102, 66, 11);

	private Window window;
	private CookieCalculator cookieCalculator;
	private CircleShape cookie;
	private Button upgrade;

	private int clickTimer;
	private TextShape click;
	private TextShape cookieCount;
	private TextShape cps;	
	private TextShape cpc;

	/**
	 * Constructor
	 */
	public CookieWindow() {
		rand = new Random();
		clickTimer = 0;
		
		window = new Window();
		window.setTitle("Cookie Clicker");
		cookieCalculator = new CookieCalculator();
		
		cookieCount = new TextShape(0, 0, "", BROWN, 40, "Serif");
		cps = new TextShape(window.getWidth() / 3, 100, "", Color.GRAY, 15);
		click = new TextShape(0, 0, "", Color.BLACK, 15);
		cpc = new TextShape(0, 0, "", Color.BLACK, 15);
		
		cookie = new CircleShape(0, 0, 100, BROWN);
		cookie.repaint();
		cookie.onClick(this, "clickedCookie");
		
		// Add all generators
		for (Generator gen : cookieCalculator.getGenerators()) {
			gen.getButton().onClick(this, "clickedGenerator");
			window.addButton(gen.getButton(), WindowSide.EAST);
			window.addShape(gen.getText());
		}
		
		// Add click upgrade button
		upgrade = new MyButton();
		upgrade.onClick(this, "clickedUpgrade");
		window.addButton(upgrade, WindowSide.SOUTH);
		
		window.addShape(cookie);
		window.addShape(cookieCount);
		window.addShape(cps);
		window.addShape(click);
		window.addShape(cpc);
	}
	
	
	
	public void loop() {
		cookieCalculator.applyCPS();		
		update();
	}
	
	public void clickedUpgrade(MyButton button) {
	    cookieCalculator.increaseCPC();
	    update();
	}
	
	public void clickedCookie(CircleShape shape) {
		cookieCalculator.cookieClick();
		displayClick();
		update();
	}
	
	public void clickedGenerator(MyButton button) {
		button.getGen().purchase();
		update();
	}

	//---------------------------------------------------------------------------------------------
	
	private void displayClick() {
		click.setText("+" + cookieCalculator.getCPC());
		clickTimer = 0;
		
		int rand1 = rand.nextInt(4);
		int rand2 = rand.nextInt(15);
		int randX = rand.nextInt(cookie.getWidth() + 30) - 15;
		int randY = rand.nextInt(cookie.getHeight() + 30) - 15;
		
		if (rand1 == 0) {
			// top
			click.setX(cookie.getX() + randX - click.getWidth() / 2);
			click.setY(cookie.getY() - click.getHeight() - rand2);
		}
		else if (rand1 == 1) {
			// right
			click.setX(cookie.getX() + cookie.getWidth() + rand2);
			click.setY(cookie.getY() + randY - click.getHeight());
		}
		else if (rand1 == 2) {
			// bottom
			click.setX(cookie.getX() + randX - click.getWidth() / 2);
			click.setY(cookie.getY() + cookie.getHeight() + rand2);
		}
		else {
			// left
			click.setX(cookie.getX() - click.getWidth() - rand2 - 5);
			click.setY(cookie.getY() + randY - click.getHeight());
		}
	}
	
	//---------------------------------------------------------------------------------------------
	
	private void update() {
		// Set Display for titles
		cookieCount.setText("Cookies: " + (int) CookieCalculator.cookieCount);
		cookieCount.setX( (window.getGraphPanelWidth()/2) - (cookieCount.getWidth()/2) );
		cookieCount.setY(cps.getHeight() + 1);
		
		cookie.setX( (window.getGraphPanelWidth()/2) - (cookie.getWidth()/2) );
		cookie.setY(cookieCount.getHeight() + cookieCount.getY() + 30);
		
		cps.setText("Cookies per second: " + (int) cookieCalculator.getCPS());
		cps.setX( (window.getGraphPanelWidth()/2) - (cps.getWidth() / 2) );
		cps.setY(1);

		//click Timer
		if (clickTimer < 20) {
			clickTimer++;
		}
		else {
			clickTimer = 0;
			click.setText("");
		}

		int counter = 0;
		//format all generator buttons and display texts
		for (Generator gen : cookieCalculator.getGenerators()) {
			// Set title for button
			if ((gen.getCPSPG() * 10) % 10 != 0) {
				gen.getButton().setTitle("Buy " + gen.getType() + " (" + (int) Math.ceil(gen.getCost()) + ") +" + String.format("%.1f", gen.getCPSPG()) + " cps");
			}
			else {
				gen.getButton().setTitle("Buy " + gen.getType() + " (" + (int) Math.ceil(gen.getCost()) + ") +" + (int) gen.getCPSPG() + " cps");				
			}
				
			// Set text
			if (gen.getCount() > 0) {
			    if ((gen.getCPS() * 10) % 10 != 0) {
			        gen.getText().setText(gen.getType() + ": " + gen.getCount() + " (" + String.format("%.1f", gen.getCPS()) + " CPS)");
			    }
			    else {
			        gen.getText().setText(gen.getType() + ": " + gen.getCount() + " (" + (int) gen.getCPS() + " CPS)");
			    }
				gen.getText().setY(window.getGraphPanelHeight() - (gen.getText().getHeight() + 2) * (counter + 1) - 3); gen.getText().setX(3);
			}
			else { gen.getText().setText(""); }
			
			// Enable / Disable button
			if (CookieCalculator.cookieCount < gen.getCost()) {
				gen.getButton().disable();
			}
			else { gen.getButton().enable(); }
			counter++;
		}
		
		// format upgrade button
		
		if (cookieCalculator.getUpgradeCost() < 0) {
		    upgrade.disable();
            upgrade.setTitle("Maxed out! " + cookieCalculator.getCPC() + " Cookies per Click");
		}
		else if (CookieCalculator.cookieCount < cookieCalculator.getUpgradeCost()) {
            upgrade.disable();
            upgrade.setTitle("Upgrade Cookies per Click (" + cookieCalculator.getUpgradeCost() + ") Current: " + cookieCalculator.getCPC());
        }
        else { 
            upgrade.enable();
            upgrade.setTitle("Upgrade Cookies per Click (" + cookieCalculator.getUpgradeCost() + ") Current: " + cookieCalculator.getCPC());
        }

	}
}
