package br.com.mvbos.emundos.el;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.ImageIcon;

import br.com.mvbos.emundos.Config;
import br.com.mvbos.jeg.element.ElementModel;
import br.com.mvbos.jeg.engine.Engine;
import br.com.mvbos.jeg.engine.GraphicTool;
import br.com.mvbos.jeg.engine.KeysMap;
import br.com.mvbos.jeg.engine.SpriteTool;
import br.com.mvbos.jeg.window.Camera;

public class Nave extends ElementModel {

	private static final float RANGE = 0.1f;

	private Player player;

	private Menu naveControl;

	private boolean action;

	private boolean invert;

	private float vel;

	private float velInc = 0.1f;

	private float velMax = 15f;

	private float velRise;

	private float velRiseMax = 1.5f;

	private float velRiseInc = 0.02f;

	// private ElementModel contexto;

	private ImageIcon fogo;

	private boolean on;

	boolean open;

	private boolean stable;

	private boolean up;

	private boolean down;

	private boolean lft;

	private boolean rgt;

	private int life;

	@Override
	public void loadElement() {
		setSize(85, 60);
		setImage(new ImageIcon(Config.PATH + "n.png"));

		fogo = new ImageIcon(Config.PATH + "fogo.png");

		naveControl = new Menu(0, 0, 10, 10);
		naveControl.setNave(this);
	}

	@Override
	public void update() {

		processKeyPress();

		// open = player != null && !on;

		naveControl.update();

		if (player != null) {

			if (player.getState() == Player.State.DEF) {
				open = true;

			} else if (player.getState() == Player.State.IN) {
				open = true;
				player.setPy(naveControl.getAllHeight() - player.getHeight());

			} else if (player.getState() == Player.State.IN_CONTROLLER) {
				player.setPxy(naveControl.getPx() - 5,
						naveControl.getAllHeight() - player.getHeight());

			}

			player.setVisible(open);

			if (naveControl.isActive()
					|| GraphicTool.g().collide(player, naveControl) != null) {

				naveControl
						.setVisible(player.getState() != Player.State.IN_CONTROLLER);

				if (action) {

					if (naveControl.isActive()) {
						player.reposition(isInvert(), player.getPx(),
								naveControl.getAllHeight() - player.getHeight());

						player.setState(Player.State.IN);
						naveControl.setActive(false);
						
						//release controllers
						up = false;
						down = false;
						lft = false;
						rgt = false;						

					} else {
						player.reposition(isInvert(), naveControl.getPx() - 5,
								naveControl.getAllHeight() - player.getHeight());

						player.setState(Player.State.IN_CONTROLLER);
						naveControl.setActive(true);
					}

				} else {
					// player.reposition(isInvert(), naveControl.getPx(),
					// naveControl.getAllHeight() - player.getHeight());
				}

				action = false;

			}

			/*
			 * if (player.getState() != Player.State.DEF) {
			 * player.setPy(naveControl.getAllHeight() - player.getHeight());
			 * 
			 * if (player.getPx() < getPx() || player.getAllWidth() >
			 * getAllWidth()) { //player.setPx(getPx()); } }
			 */
		} else {
			open = false;
		}

		if (naveControl.isActive()) {

			/*
			 * 
			 * if (stable && getAllHeight() <
			 * Engine.getIWindowGame().getCanvasHeight()) { incPy(0.05f);
			 * 
			 * } else if (getAllHeight() >=
			 * Engine.getIWindowGame().getCanvasHeight()) { started = false; //
			 * stable = false; // power = 0f; // System.out.println("solo"); }
			 */

			/*
			 * if (!stable && getAllHeight() <
			 * Engine.getIWindowGame().getCanvasHeight()) { incPy(0.5f); //vel =
			 * 0; // incPy(0.5f - vel);
			 * 
			 * /* if (vel > 0f) vel -= velInc / 2f;// 0.005f; else vel = 0f;
			 */
			// }
		}

		if (getAllWidth() - getHalfWidth() > Engine.getIWindowGame().getCanvasWidth() / 2) {
			Camera.c().rollX(-vel);
		}
		
		if (getPy() - getHalfHeight() < Engine.getIWindowGame().getCanvasHeight() / 2) {
			Camera.c().rollY(-velRise);
		}

	}

	private void processKeyPress() {
		// only true when naveControl.isActive() is true

		if (up) {
			on = true;
			open = false;

			if (velRise < velRiseMax) {
				velRise += velRiseInc;
			}

		} else if (down) {
			open = false;
			if (velRise > -velRiseMax) {
				velRise -= velRiseInc;
			}

		} else if (velRise > RANGE || velRise < -RANGE) {
			if (velRise > 0f) {
				velRise -= velRiseInc * 0.5;
			} else if (velRise < 0f) {
				velRise += velRiseInc * 0.5;
			}

		} else {
			velRise = 0f;
		}

		boolean collideBottom = collide();

		if (collideBottom && velRise < 0f) {
			velRise = 0f;
			on = false;
			// open = true;
		}

		if (!collideBottom) {
			if (lft) {
				open = false;
				if (vel < velMax) {
					vel += velInc;
				}

				/*
				 * if (getPx() < Engine.getIWindowGame().getCanvasWidth() * 0.5)
				 * { Camera.c().rollX(vel); }
				 */

			} else if (rgt) {
				open = false;
				if (vel > -velMax) {
					vel -= velInc;
				}

			} else if (vel > RANGE || vel < -RANGE) {
				if (vel > 0.1f) {
					vel -= velInc * 0.5;

				} else if (vel < 0.1f) {
					vel += velInc * 0.5;

				}

			} else {
				vel = 0f;
			}

		} else if (vel > RANGE || vel < -RANGE) {
			life--;

			if (vel > velInc) {
				vel -= velInc * 2;

			} else if (vel < velInc) {
				vel += velInc * 2;
			}

		} else {
			vel = 0f;
		}

		/*
		 * up = false; down = false; lft = false; rgt = false;
		 */

		incPx(-vel);
		incPy(-velRise);
	}

	private boolean isFly() {
		return getAllHeight() < Engine.getIWindowGame().getCanvasHeight();
	}

	private boolean collide() {
		return getAllHeight() >= Engine.getIWindowGame().getCanvasHeight();
	}

	@Override
	public void drawMe(Graphics2D g2d) {
		SpriteTool s = SpriteTool.s(getImage()).matriz(2, 1);

		if (open) {
			s.invert(false).draw(g2d, Camera.c().fx(getPx()),
					Camera.c().fy(getPy()), 1, 0);
			naveControl.drawMe(g2d);

		} else {
			s.invert(false).draw(g2d, Camera.c().fx(getPx()),
					Camera.c().fy(getPy()), 0, 0);

			if (on) {
				s = SpriteTool.s(fogo).matriz(5, 1);
				s.invert(false).draw(g2d, Camera.c().fx(getPx()) + 25,
						Camera.c().fy(getAllHeight() - 21), SpriteTool.SORT, 0);
			}
		}
		
		g2d.setColor(Color.BLACK);
		g2d.drawString("Life: " + life, 10, 15);
		g2d.drawString("Vel: " + vel, 10, 25);
		g2d.drawString("Vel Mx: " + velMax, 10, 45);
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

	public void press(KeysMap direction) {
		action = false;
		if (!naveControl.isActive()) {
			return;
		}

		switch (direction) {
		case UP:
			up = true;
			break;
		case DOWN:
			down = true;
			break;
		case LEFT:
			lft = true;
			break;
		case RIGHT:
			rgt = true;
			break;
		default:
			break;
		}
	}

	public void release(KeysMap direction) {
		action = false;
		if (!naveControl.isActive()) {
			return;
		}

		switch (direction) {
		case UP:
			up = false;
			break;
		case DOWN:
			down = false;
			break;
		case LEFT:
			lft = false;
			break;
		case RIGHT:
			rgt = false;
			break;
		default:
			break;
		}
	}

}
