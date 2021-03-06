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
import br.com.mvbos.jeg.engine.MathTool;
import br.com.mvbos.jeg.engine.SpriteTool;
import br.com.mvbos.jeg.window.Camera;

public class Nave extends ElementModel {

	private static final int BACK_LIMIT = 2;

	private static final int FRONT_LIMIT = 5;

	private static final int RISE_LIMIT = 1600;

	private static final float RANGE = 0.1f;

	private Player player;

	private NavePlaces naveControl;

	private NavePlaces[] np = new NavePlaces[5];

	private BarraStatus bar;

	private boolean invert;

	private float _vel;

	private float velInc = 0.1f;

	private float velMax = 15f;

	private float velRise;

	private float velRiseMax = 6f;// 1.5f;

	private float velRiseInc = 0.02f;

	// private ElementModel contexto;

	// private float consumo = 0.0001f;

	private ImageIcon fogo;

	private boolean on;

	boolean open;

	private boolean bAction;

	@Override
	public void loadElement() {
		setSize(85, 60);
		setImage(new ImageIcon(Config.PATH + "n.png"));

		fogo = new ImageIcon(Config.PATH + "fogo.png");

		/*
		 * naveControl = new Menu(0, 0, 1, 1); naveControl.setNave(this);
		 * naveControl.loadElement();
		 */

		np[0] = new NavePlaces(true, 30, 60, Menu.Type.ENERGY);
		np[1] = new NavePlaces(true, 50, 60, Menu.Type.NAV);

		np[0].setMenu(new Menu(true, Menu.Type.ENERGY, 5));
		np[1].setMenu(new Menu(true, Menu.Type.NAV, 5));
		naveControl = np[1];

		bar = new BarraStatus(Engine.getIWindowGame().getCanvasWidth() - 110, 10, 100, 15);
		bar.loadElement();
	}

	@Override
	public void update() {

		keyUpdateShip();

		// reset controls
		np[0].getMenu().setVisible(false);
		np[1].getMenu().setVisible(false);

		if (player != null) {

			repContAll();
			keyUpdatePlayer();

			bAction = player.getPad().first(KeysMap.B1) && !player.isInMenu();

			if (player.getState() == Player.State.DEF) {
				open = true;

			} else if (player.getState() == Player.State.IN) {
				open = true;
				player.setPy(getAllHeight() - player.getHeight());

			} else if (player.getState() == Player.State.IN_CONTROLLER) {
				if (player.getPad().first(KeysMap.B2)) {
					invert = !invert;
				}

				player.reposition(invert, naveControl.getMenu().getPx(), getPy() + naveControl.getPy() - player.getHeight());
			}

			player.setVisible(open);

			for (NavePlaces p : np) {

				if (p == null || !p.haveActiveMenu() || !GraphicTool.g().bcollide(player, p.getMenu()))
					continue;

				if (p.getType() == Menu.Type.NAV /* && inControl() */) {
					p.getMenu().setVisible(player.getState() != Player.State.IN_CONTROLLER);

					if (!bAction)
						continue;

					if (inControl()) {
						player.reposition(invert, np[1].getMenu().getPx() - 5, getPy() + np[1].getPy() - player.getHeight());
						player.setState(Player.State.IN);

					} else {
						player.reposition(invert, np[1].getMenu().getPx() - 5, getPy() + np[1].getPy() - player.getHeight());
						player.setState(Player.State.IN_CONTROLLER);
					}

				} else if (p.getType() == Menu.Type.ENERGY) {
					p.getMenu().setVisible(true);

					if (bAction && player.getItemActive() != null) {
						player.removeItemActive();

						bar.incDamage(-50);
						bar.incEnergy(90);
					}
				}

			}

		} else {
			open = false;
		}

		// TODO criar wrap util:
		if (getPx() < 0 || getAllWidth() > Planeta.w) {
			setPx(getPx() < 0 ? 0 : Planeta.w - getWidth());
			_vel = 0;
		}

		if (collideTop()) {
			// velRise = -velRiseMax * 0.2f;// kick
		} else if (collideBottom() && velRise < -0.55) {
			velRise = velRise * -0.3f; // kick
			bar.incDamage(velRise);
		}

		// TODO verificar
		if (getAllHeight() > Planeta.h - Planeta.base) {
			setPy(Planeta.h - Planeta.base - getHeight());
		}
		// ------------------------------

		if (inControl()) {
		}

		if (on) {
			bar.incEnergy(-0.01f);

			if (bar.fullDamage()) {
				on = false;
			}

		} else {
			if (!collideBottom()) {
				velRise -= velRiseInc * 3;
				// System.out.println("baixo");
			}
		}

		// subs updates
		bar.update();
		bar.setVisible(player != null);

	}

	/**
	 * Reposiciona elementos na nave
	 * 
	 */
	private void repContAll() {

		for (NavePlaces p : np) {
			if (p == null || !p.haveActiveMenu())
				continue;

			p.update(this);
		}
	}

	private void keyUpdateShip() {

		boolean collideTop = collideTop();
		boolean collideBottom = collideBottom();

		if (readyToFly()) {

			if (!collideTop && inControl() && player.getPad().is(KeysMap.UP)) {
				if (on && velRise < velRiseMax) {
					velRise += velRiseInc;
				}
				
				open = false;
				on = true;

			} else if (inControl() && player.getPad().is(KeysMap.DOWN)) {
				open = false;
				if (velRise > -velRiseMax) {
					velRise -= velRiseInc * 2f;
				}

			} else if (velRise > RANGE || velRise < -RANGE) {
				if (velRise > 0f) {
					velRise -= velRiseInc * 0.5;
				} else if (velRise < 0f) {
					velRise += velRiseInc * 0.5;
				}

			} else if (on) {
				// Estabiliza
				velRise = 0f;
			}

		} else {
			on = false;
		}

		if (collideBottom && velRise < 0f) {
			velRise = 0f;
			on = false;
			open = true;

		} else if (collideTop) {
			on = false;
			velRise -= velRiseInc * 2f;
			bar.incDamage(0.05f);
		}

		if (!collideBottom) {
			if (on) {
				if (inControl() && player.getPad().is(KeysMap.LEFT)) {
					open = false;
					if (_vel < velMax) {
						_vel += velInc;
					}

				} else if (inControl() && player.getPad().is(KeysMap.RIGHT)) {
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
			}

		} else if (_vel > RANGE || _vel < -RANGE) {
			// bar.incDamage(_vel < 0 ? -_vel : _vel);
			bar.incDamage(1);

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

	private void keyUpdatePlayer() {
		if (player.getState() != Player.State.IN) {
			return;
		}

		/*
		 * if (player.getState() == Player.State.IN_CONTROLLER) { if
		 * (player.getPad().first(KeysMap.B2)) { invert = !invert; } }
		 */

		player.incPx(-_vel);

		if (player.getPad().is(KeysMap.UP)) {
			// player.incPy(-velRise);
		} else if (player.getPad().is(KeysMap.DOWN)) {
			// player.incPy(-velRise);
		}

		if (player.getPad().is(KeysMap.LEFT)) {
			if (on && player.getPx() - player.velInc - (invert ? FRONT_LIMIT : BACK_LIMIT) < getPx()) {
				player.stop();
			} else {
				player.moveX(-player.velInc);
			}

		} else if (player.getPad().is(KeysMap.RIGHT)) {
			if (on && player.getAllWidth() + player.velInc + (invert ? BACK_LIMIT : FRONT_LIMIT) > getAllWidth()) {
				player.stop();
			} else {
				player.moveX(player.velInc);
			}
		}

	}

	/**
	 * Return true if Ship has Life and Energy
	 * 
	 * @return
	 */
	public boolean readyToFly() {
		return !bar.fullDamage() && bar.getEnergy() > 0;
	}

	private boolean collideTop() {
		return getPy() < RISE_LIMIT || getPy() < 0;
	}

	public void fix() {
		bar.setDamage(0);
	}

	public boolean isFly() {
		return getAllHeight() < Engine.getIWindowGame().getCanvasHeight();
	}

	private boolean collideBottom() {
		return getAllHeight() >= Planeta.h - Planeta.base;
	}

	@Override
	public void drawMe(Graphics2D g) {
		if (!open && player != null && player.getState() == Player.State.IN_CONTROLLER) {
			player.drawMe(g);
		}

		SpriteTool s = SpriteTool.s(getImage()).matriz(2, 1);

		if (open) {
			s.invert(invert).draw(g, Camera.c().fx(getPx()), Camera.c().fy(getPy()), 1, 0);

			for (NavePlaces p : np) {
				if (p != null && p.haveActiveMenu())
					p.getMenu().drawMe(g);
			}

		} else {
			s.invert(invert).draw(g, Camera.c().fx(getPx()), Camera.c().fy(getPy()), 0, 0);

			if (on) {
				if (velRise >= -velRiseMax) {
					s = SpriteTool.s(fogo).matriz(5, 1).invert(false);
					s.draw(g, Camera.c().fx(getPx()) + (invert ? 40 : 25), Camera.c().fy(getAllHeight() - 21), player.getPad()
							.is(KeysMap.DOWN) ? MathTool.r.nextInt(2) : SpriteTool.SORT, 0);
				}
			}
		}

		bar.drawMe(g);

		g.setColor(Color.LIGHT_GRAY);
		g.drawString("Rise: " + velRise, 10, 15);
		g.drawString("Vel: " + _vel, 10, 25);
		g.drawString("x,y: " + getPx() + "," + getPy(), 10, 45);
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public boolean isInvert() {
		return invert;
	}

	public void setInvert(boolean invert) {
		this.invert = invert;
	}

	private boolean inControl() {
		return player != null && player.getState() == Player.State.IN_CONTROLLER;
	}

}
