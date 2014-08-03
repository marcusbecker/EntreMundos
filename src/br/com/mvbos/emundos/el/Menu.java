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

	private Nave nave;
	private boolean active;
	private Type type = Type.NAV;

	@Override
	public void loadElement() {
		if (type == Type.ENERGY) {
			setImage(new ImageIcon(Config.PATH + "ico_nav_en.png"));
		} else {
			setImage(new ImageIcon(Config.PATH + "ico_nav_ac.png"));
		}
	}

	final Font f = new Font("Arial", Font.PLAIN, 16);

	@Override
	public void drawMe(Graphics2D g2d) {

		drawBorders(g2d);

		if (isVisible() && !isActive()) {

			if (getImage() != null) {

				g2d.drawImage(getImage().getImage(), Camera.c().fx(getPx() - 10), Camera.c().fy(getPy() - 100), 40, 32,
						null);
				g2d.setColor(Color.LIGHT_GRAY);
				g2d.drawRect(Camera.c().fx(getPx() + 10), Camera.c().fy(getPy() - 60), 1, 40);

				/*
				 * SpriteTool s = SpriteTool.s(getImage()).matriz(4, 1);
				 * g2d.setFont(f);
				 * 
				 * 
				 * final String cmd = "Entrar"; int w = 10 * cmd.length() +
				 * 15;// 90; int h = 50;
				 * 
				 * g2d.setColor(Color.WHITE); g2d.fillRect(Camera.c().fx(getPx()
				 * - w / 2), Camera.c().fy(nave.getPy() - 70), w, h);
				 * 
				 * g2d.setColor(Color.BLACK); g2d.drawString(cmd,
				 * Camera.c().fx((getPx() - w / 2) + 15),
				 * Camera.c().fy(nave.getPy() - h / 2 - 15));
				 * 
				 * // borders s.invert(false) .draw(g2d, Camera.c().fx(getPx() -
				 * w / 2), Camera.c().fy(nave.getPy() - 70), 0, 0) .draw(g2d,
				 * Camera.c().fx(getPx() + w / 2), Camera.c().fy(nave.getPy() -
				 * 70), 1, 0);
				 */
			} else {
				g2d.setColor(Color.BLUE);
				g2d.fillRect(Camera.c().fx(getPx() + 5), Camera.c().fy(nave.getPy() - 150), 100, 100);
				g2d.setColor(Color.WHITE);
				g2d.drawString("Entrar", Camera.c().fx(getPx() + 10), Camera.c().fy(nave.getPy() - 100));
			}

		}
	}

	@Override
	public void update() {
	}

	public Menu(int positionX, int positionY, int width, int height) {
		setAll(positionX, positionY, width, height);
	}

	public void setNave(Nave nave) {
		this.nave = nave;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

}
