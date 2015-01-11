package br.com.mvbos.emundos.el;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import javax.swing.ImageIcon;

import br.com.mvbos.emundos.Config;
import br.com.mvbos.jeg.element.ElementModel;
import br.com.mvbos.jeg.window.Camera;

public class Menu extends ElementModel {

	public enum Type {
		NAV, FOOD, ENERGY, ARMOR, SHIELD
	}
	
	private boolean active;
	private final Menu.Type type;

	private final int hitWidth;

	public Menu(boolean active, Menu.Type type, int hitWidth) {
		this.active = active;
		this.type = type;

		setAll(0, 0, 5, 1);
		this.hitWidth = hitWidth;
	}

	@Override
	public void loadElement() {
		if (type == Menu.Type.ENERGY) {
			setImage(new ImageIcon(Config.PATH + "ico_nav_en.png"));
		} else {
			setImage(new ImageIcon(Config.PATH + "ico_nav_ac.png"));
		}

		setWidth(getImage().getIconWidth());
		setHeight(getImage().getIconHeight());
	}

	final Font f = new Font("Arial", Font.PLAIN, 16);

	@Override
	public void drawMe(Graphics2D g2d) {

		drawBorders(g2d);

		if (isVisible() && isActive()) {

			if (getImage() != null) {

				g2d.drawImage(getImage().getImage(), Camera.c().fx(getPx() - 10), Camera.c().fy(getPy() - 100), 40, 32,
						null);

				g2d.setColor(Color.LIGHT_GRAY);

				g2d.drawRect(Camera.c().fx(getPx() + 10), Camera.c().fy(getPy() - 60), 1, 40);

			} else {
				g2d.setColor(Color.BLUE);
				g2d.fillRect(Camera.c().fx(getPx() + 5), Camera.c().fy(getPy() - 150), 100, 100);
				g2d.setColor(Color.WHITE);
				g2d.drawString("Entrar", Camera.c().fx(getPx() + 10), Camera.c().fy(getPy() - 100));
			}

		}
	}

	@Override
	public int getHitWidth() {
		return hitWidth;
	}

	@Override
	public int getHitHeight() {
		return 1;
	}

	@Override
	public void update() {
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Menu.Type getType() {
		return type;
	}

}
