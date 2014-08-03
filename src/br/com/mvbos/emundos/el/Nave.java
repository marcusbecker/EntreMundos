package br.com.mvbos.emundos.el;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.ImageIcon;

import br.com.mvbos.emundos.Config;
import br.com.mvbos.emundos.data.NavePlaces;
import br.com.mvbos.emundos.sc.Planeta;
import br.com.mvbos.jeg.element.ElementModel;
import br.com.mvbos.jeg.engine.Engine;
import br.com.mvbos.jeg.engine.GraphicTool;
import br.com.mvbos.jeg.engine.KeysMap;
import br.com.mvbos.jeg.engine.SpriteTool;
import br.com.mvbos.jeg.window.Camera;

public class Nave extends ElementModel {

	private static final int RISE_LIMIT = 1600;

	private static final int GRAVITY = 50;

	private static final float RANGE = 0.1f;

	private static final int MAX_DAMAGE = 100;

	private Player player;

	private NavePlaces places;
	private Menu naveControl;
	private Menu energy;
	private BarraStatus bar;

	private boolean action;

	private boolean invert;

	private float _vel;

	private float velInc = 0.1f;

	private float velMax = 15f;

	private float velRise;

	private float velRiseMax = 6f;// 1.5f;

	private float velRiseInc = 0.02f;

	// private ElementModel contexto;

	private ImageIcon fogo;

	private boolean on;

	boolean open;

	private boolean up;

	private boolean down;

	private boolean lft;

	private boolean rgt;

	private int damage;

	@Override
	public void loadElement() {
		setSize(85, 60);
		setImage(new ImageIcon(Config.PATH + "n.png"));

		fogo = new ImageIcon(Config.PATH + "fogo.png");

		naveControl = new Menu(0, 0, 1, 1);
		naveControl.setNave(this);
		naveControl.loadElement();

		energy = new Menu(0, 0, 1, 1);
		energy.setNave(this);
		energy.setType(Menu.Type.ENERGY);
		energy.loadElement();

		bar = new BarraStatus(Engine.getIWindowGame().getCanvasWidth() - 110, 10, 100, 15);
		bar.loadElement();
	}

	@Override
	public void update() {

		keyUpdateShip();
		keyUpdatePlayer();

		if (getPy() < RISE_LIMIT) {
			// incDamage();
			// setPy(RISE_LIMIT);
			// velRise = -velRiseMax * 0.2f;//kick

		}

		// reset controls
		naveControl.setVisible(false);
		energy.setVisible(false);
		// naveControl.update();

		if (player != null) {

			repCont(naveControl);
			repCont(energy);

			if (player.getState() == Player.State.DEF) {
				open = true;

			} else if (player.getState() == Player.State.IN) {
				open = true;
				player.setPy(getAllHeight() - player.getHeight());

			} else if (player.getState() == Player.State.IN_CONTROLLER) {
				player.setPxy(naveControl.getPx() - 5, naveControl.getAllHeight() - player.getHeight());

			}

			player.setVisible(open);

			if (naveControl.isActive() || GraphicTool.g().collide(player, naveControl) != null) {

				naveControl.setVisible(player.getState() != Player.State.IN_CONTROLLER);

				if (action) {

					if (naveControl.isActive()) {
						player.reposition(isInvert(), player.getPx(), naveControl.getAllHeight() - player.getHeight());

						player.setState(Player.State.IN);
						naveControl.setActive(false);

						releaseControll();

					} else {
						player.reposition(isInvert(), naveControl.getPx() - 5,
								naveControl.getAllHeight() - player.getHeight());

						player.setState(Player.State.IN_CONTROLLER);
						naveControl.setActive(true);

						releaseControll();
					}

				}

			} else if (GraphicTool.g().collide(player, energy) != null) {
				energy.setVisible(true);
				//energy.setActive(false);
			}

			// update camera
			Camera.c().center(this);

		} else {
			open = false;
		}

		// TODO criar wrap util:
		if (getPx() < 0) {
			setPx(0);
			_vel = 0;

		} else if (getAllWidth() > Planeta.w) {
			setPx(Planeta.w - getWidth());
			_vel = 0;
		}

		if (getPy() < 0) {
			setPy(0);
			velRise = -velRiseMax * 0.2f;// kick
		} else if (getAllHeight() > Planeta.h) {
			setPy(Planeta.h - getHeight());
			velRise = 0;
		}
		// ------------------------------

		if (naveControl.isActive()) {
		}

		// subs updates
		bar.setDamage(damage);
		bar.update();
		bar.setVisible(player != null);

		action = false;
	}

	/**
	 * Reposicionar controle
	 * 
	 * @param el
	 */
	private void repCont(ElementModel el) {
		float x = 0;
		float y = 0;

		if (el == naveControl) {
			x = places.getControl().x;
			y = places.getControl().y;

		} else {
			x = places.getEnergy().x;
			y = places.getEnergy().y;
		}

		if (isInvert()) {
			// el.setPxy(getPx() + x, getAllHeight() - el.getHeight() - y);
			el.setPxy(getAllWidth() - x, getAllHeight() - el.getHeight() - y);
		} else {
			// el.setPxy(getAllWidth() - x, getAllHeight() - el.getHeight() -
			// y);
			el.setPxy(getPx() + x, getAllHeight() - el.getHeight() - y);
		}
	}

	private void releaseControll() {
		// release controllers
		up = false;
		down = false;
		lft = false;
		rgt = false;
	}

	private void keyUpdateShip() {

		boolean collideTop = collideTop();
		boolean collideBottom = collide();

		if (damage < MAX_DAMAGE) {
			if (!collideTop && naveControl.isActive() && up) {
				open = false;
				on = true;

				if (velRise < velRiseMax) {
					velRise += velRiseInc;
				}

			} else if (naveControl.isActive() && down) {
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
				// TODO
				velRise = 0f;
			}

		}

		if (collideBottom) {
			// TODO implementar
			// System.out.println(velRise);
			if (velRise < 0f && velRise > -velRiseInc * 2) {
				System.out.println(velRise + " x " + -velRiseInc);
				incDamage();
			}

		}

		if (collideBottom && velRise < 0f) {
			velRise = 0f;
			on = false;
			// open = true;

		} else if (collideTop) {
			on = false;
			velRise -= velRiseInc * 2f;
		}

		if (!collideBottom) {
			if (naveControl.isActive() && lft) {
				open = false;
				if (_vel < velMax) {
					_vel += velInc;
				}

			} else if (naveControl.isActive() && rgt) {
				open = false;
				if (_vel > -velMax) {
					_vel -= velInc;
				}

			} else if (_vel > RANGE || _vel < -RANGE) {
				if (_vel > 0.1f) {
					_vel -= velInc * 0.5;

				} else if (_vel < 0.1f) {
					_vel += velInc * 0.5;

				}

			} else {
				_vel = 0f;
			}

			if (damage >= MAX_DAMAGE) {
				on = false;
				if (velRise > -GRAVITY) {
					velRise -= velRiseInc * 2;
				}
			}

		} else if (_vel > RANGE || _vel < -RANGE) {
			incDamage();

			if (_vel > velInc) {
				_vel -= velInc * 2;

			} else if (_vel < velInc) {
				_vel += velInc * 2;
			}

		} else {
			_vel = 0f;

		}

		/*
		 * up = false; down = false; lft = false; rgt = false;
		 */

		incPx(-_vel);
		incPy(-velRise);
	}

	private boolean collideTop() {
		return getPy() < RISE_LIMIT || getPy() < 0;
	}

	private void incDamage() {
		if (++damage > MAX_DAMAGE) {
			damage = MAX_DAMAGE;
		}
	}

	private void fix() {
		damage = 0;
	}

	private void keyUpdatePlayer() {
		if (player == null || player.getState() != Player.State.IN) {
			return;
		}

		player.incPx(-_vel);
		// player.incPy(-velRise);

		if (up) {

		} else if (down) {

		}

		if (lft) {
			player.moveX(-player.velInc);

		} else if (rgt) {
			player.moveX(player.velInc);
		}

		releaseControll();

	}

	private boolean isFly() {
		return getAllHeight() < Engine.getIWindowGame().getCanvasHeight();
	}

	private boolean collide() {
		return getAllHeight() >= Planeta.h;
	}

	@Override
	public void drawMe(Graphics2D g) {
		SpriteTool s = SpriteTool.s(getImage()).matriz(2, 1);

		if (open) {
			s.invert(false).draw(g, Camera.c().fx(getPx()), Camera.c().fy(getPy()), 1, 0);
			
			naveControl.drawMe(g);
			energy.drawMe(g);

		} else {
			s.invert(false).draw(g, Camera.c().fx(getPx()), Camera.c().fy(getPy()), 0, 0);

			if (on && velRise >= -velRiseMax) {
				s = SpriteTool.s(fogo).matriz(5, 1);
				s.invert(false).draw(g, Camera.c().fx(getPx()) + 25, Camera.c().fy(getAllHeight() - 21),
						SpriteTool.SORT, 0);
			}
		}

		bar.drawMe(g);

		g.setColor(Color.LIGHT_GRAY);
		g.drawString("Rise: " + velRise, 10, 15);
		g.drawString("Vel: " + _vel, 10, 25);
		g.drawString("Py: " + getPy(), 10, 45);
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

		switch (direction) {
		case UP:
			up = true;
			down = false;
			break;
		case DOWN:
			down = true;
			up = false;
			break;
		case LEFT:
			lft = true;
			rgt = false;
			break;
		case RIGHT:
			rgt = true;
			lft = false;
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

	public void setPlaces(NavePlaces np) {
		this.places = np;
	}
}
