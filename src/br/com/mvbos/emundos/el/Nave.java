package br.com.mvbos.emundos.el;

import java.awt.Graphics2D;

import javax.swing.ImageIcon;

import br.com.mvbos.emundos.Config;
import br.com.mvbos.jeg.element.ElementModel;
import br.com.mvbos.jeg.engine.GraphicTool;

public class Nave extends ElementModel {

	private Player player;

	private Menu controle;

	private boolean action;

	// private ElementModel contexto;

	@Override
	public void loadElement() {
		setSize(85, 60);
		setImage(new ImageIcon(Config.PATH + "n.png"));

		controle = new Menu(0, 0, 10, 10);
		controle.setNave(this);
	}

	@Override
	public void update() {
		if (player != null) {
			controle.update();

			if (GraphicTool.g().collide(player, controle) != null) {
				controle.setVisible(true);
				if (action) {
					action = false;
					player.setState(1);
				}
			} else {
				controle.setVisible(false);
			}
		}
	}

	@Override
	public void drawMe(Graphics2D g2d) {
		if (player != null) {
			g2d.drawImage(getImage().getImage(), getPx(), getPy(), getPx() + getWidth(), getPy() + getHeight(),
					getWidth(), 0, getWidth() * 2, getHeight(), null);

			controle.drawMe(g2d);

		} else {
			g2d.drawImage(getImage().getImage(), getPx(), getPy(), getPx() + getWidth(), getPy() + getHeight(), 0, 0,
					getWidth(), getHeight(), null);
		}
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public void action() {
		this.action = true;

	}

}
