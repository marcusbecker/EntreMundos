package br.com.mvbos.emundos.el;

import java.awt.Graphics2D;

import javax.swing.ImageIcon;

import br.com.mvbos.emundos.Config;
import br.com.mvbos.jeg.element.ElementModel;
import br.com.mvbos.jeg.engine.Engine;
import br.com.mvbos.jeg.engine.GraphicTool;
import br.com.mvbos.jeg.engine.KeysMap;
import br.com.mvbos.jeg.engine.SpriteTool;

public class Nave extends ElementModel {

	private Player player;

	private Menu controle;

	private boolean action;

	private boolean invert;

	private float vel;

	private float velMax = 5f;

	private float velInc = 0.02f;

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
					System.out.println("Action");
					if (controle.isActive()) {
						// free player
						player.reposition(isInvert(), controle.getPx(), controle.getAllHeight() - player.getHeight());
						player.setState(Player.State.DEF);
						controle.setActive(false);

					} else {
						// hold player
						player.setState(Player.State.IN);
						controle.setActive(true);
					}

				}

			} else {
				controle.setVisible(false);
			}

			if (controle.isActive()) {
				player.reposition(isInvert(), controle.getPx() - 5, controle.getAllHeight() - player.getHeight());
			}
		}

		action = false;
		if (getAllHeight() < Engine.getIWindowGame().getCanvasHeight()) {
			incPy(0.5f - vel);
			
			if(vel > 0f)
				vel -= velInc / 2f;//0.005f;
			else
				vel = 0f;
		}
	}

	@Override
	public void drawMe(Graphics2D g2d) {
		SpriteTool s = SpriteTool.s(getImage()).matriz(2, 1);

		if (player != null) {

			s.invert(false).draw(g2d, getPx(), getPy(), 1, 0);
			controle.drawMe(g2d);

		} else {
			s.invert(false).draw(g2d, getPx(), getPy(), 0, 0);
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

	public boolean isInvert() {
		return invert;
	}

	public void setInvert(boolean invert) {
		this.invert = invert;
	}

	public void go(KeysMap direction) {
		if (player == null || player.getState() != Player.State.IN) {
			return;
		}

		switch (direction) {
		case UP:
			up();
			break;
		case DOWN:
			vel -= 0.01f;//incPy(+1);
			break;
		case LEFT:
			// setDirection(true);
			incPx(-2);
			break;
		case RIGHT:
			// setDirection(false);
			incPx(+2);
			break;
		default:
			break;
		}

		update();
	}

	private void up() {
		if (vel < velMax) {
			vel += velInc;
		}

		incPy(-vel);
		System.out.println("vel " + vel);
	}
}
