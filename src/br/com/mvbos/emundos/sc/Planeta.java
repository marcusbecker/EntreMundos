package br.com.mvbos.emundos.sc;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.ImageIcon;

import br.com.mvbos.emundos.Config;
import br.com.mvbos.emundos.data.NavePlaces;
import br.com.mvbos.emundos.el.BackgroundElement;
import br.com.mvbos.emundos.el.Bloco;
import br.com.mvbos.emundos.el.Loja;
import br.com.mvbos.emundos.el.Nave;
import br.com.mvbos.emundos.el.Player;
import br.com.mvbos.jeg.element.ElementModel;
import br.com.mvbos.jeg.engine.Clicked;
import br.com.mvbos.jeg.engine.Engine;
import br.com.mvbos.jeg.engine.GraphicTool;
import br.com.mvbos.jeg.engine.KeysMap;
import br.com.mvbos.jeg.scene.Click;
import br.com.mvbos.jeg.scene.Pxy;
import br.com.mvbos.jeg.scene.impl.SceneDefault;
import br.com.mvbos.jeg.window.Camera;
import br.com.mvbos.jeg.window.impl.MemoryImpl;

public class Planeta extends SceneDefault {

	private static final Clicked CLICK_MAP = new Clicked();

	private static final GraphicTool G = GraphicTool.g();

	private Player p;
	private Nave n;

	// private final ElementModel BLANK = new ElementModel();

	private ElementModel temp;

	// cima, baixo, esq, dir, Q, E
	private int[] keysCode = { 38, 40, 37, 39, 81, 69 };
	private char[] keysChar = { 'w', 's', 'a', 'd', 'q', 'e' };

	private ImageIcon bg; // background
	private ImageIcon bt; // bottom

	private BackgroundElement fbg;
	// private BackgroundElement sbg;

	public static int w = 3970;
	public static int h = 3970;

	public static int base = 75;

	@Override
	public boolean startScene() {
		memo = new MemoryImpl(90);

		n = new Nave();
		p = new Player();

		NavePlaces np = new NavePlaces();// config externa
		np.setControl(new Pxy(55, 0));
		np.setEnergy(new Pxy(30, 0));
		n.setPlaces(np);

		Loja loja = new Loja(this);
		loja.setSize(170, 140);

		for (int i = 1; i < 40; i++) {
			memo.registerElement(new Bloco(450 * i, Planeta.h - 450, 30, 30));
		}

		for (int i = 1; i < 40; i++) {
			memo.registerElement(new Bloco(450, Planeta.h - (300 * i), 30, 30));
		}

		memo.registerElement(p);
		memo.registerElement(n);

		memo.registerElement(loja);

		for (int i = 0; i < memo.getElementCount(); i++) {
			memo.getByElement(i).loadElement();
		}

		resizeWindow();

		Engine.endGame = false;

		bg = new ImageIcon(Config.PATH + "bg_space.png");
		bt = new ImageIcon(Config.PATH + "p01/bt.png");

		fbg = new BackgroundElement();
		fbg.setImage(new ImageIcon(Config.PATH + "p01/fnd.png"));
		fbg.loadElement();
		// sbg = new BackgroundElement();
		// sbg.setImage(new ImageIcon(Config.PATH + "f1s2.png"));
		// sbg.loadElement();

		p.setPy(Planeta.h - Planeta.base - p.getHeight());
		n.setPy(Planeta.h - Planeta.base - n.getHeight());
		loja.setPxy(750, Planeta.h - Planeta.base - loja.getHeight());

		Camera.c().config(w, h).rollY(Planeta.h - Engine.getIWindowGame().getCanvasHeight());
		// System.out.println(Camera.c());

		return true;
	}

	@Override
	public Color getBgColor() {
		return Color.ORANGE;
	}

	@Override
	public void update() {
		if (G.collide(p, n) != null) {
			n.setPlayer(p);
			p.inShip();

			temp = null;

		} else {
			n.setPlayer(null);
			p.exitShip();

			temp = GraphicTool.g().collide(p, memo);

			if (temp instanceof Loja) {
				// p.setState(Player.State.IN_FOCUS);
			} else {
				p.setState(Player.State.DEF);
			}
		}

		for (int i = 0; i < memo.getElementCount(); i++) {
			memo.getByElement(i).update();
		}

		if (p.getState() == Player.State.IN_CONTROLLER) {
			Camera.c().center(n);
		} else {
			Camera.c().center(p);
		}
	}

	@Override
	public void resizeWindow() {
		// TODO reajuste camera
		// p.setPy(Engine.getIWindowGame().getCanvasHeight() - 40);
		// n.setPy(Engine.getIWindowGame().getCanvasHeight() - 60);
	}

	@Override
	public void drawElements(Graphics2D g) {

		g.drawImage(bg.getImage(), 0, (int) -Camera.c().getCpy(), w, h, null);

		fbg.drawMe(g);

		if (Engine.endGame) {
			g.setColor(Color.BLUE);
			g.drawString("GAME OVER", 300, 300);

			return;
		}

		if (memo == null) {
			Engine.log("Draw Error: Scene don't started.");
			return;
		}

		for (int i = memo.getElementCount() - 1; i >= 0; i--) {
			if (memo.getByElement(i).isVisible()) {
				memo.getByElement(i).drawMe(g);
			}
		}

		int base = (int) (h - Camera.c().getCpy());
		g.drawImage(bt.getImage(), 0, base - bt.getIconHeight(), w, bt.getIconHeight(), null);

		g.drawImage(bt.getImage(), 0, base - bt.getIconHeight(), w, bt.getIconHeight(), null);

		// g2d.setColor(Color.BLUE);
		// g2d.drawRect(Engine.getIWindowGame().getWindowWidth() / 2, 0, 1,
		// Engine.getIWindowGame().getWindowHeight());

		// AffineTransform old = g2d.getTransform();
		// AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
		// g2d.setTransform(tx);

		// sbg.drawMe(g);
	}

	private boolean isKey(char keyChar, int keyCode, int idKeyMap) {
		return Character.toLowerCase(keyChar) == Character.toLowerCase(keysChar[idKeyMap])
				|| keyCode == keysCode[idKeyMap];
	}

	@Override
	public void keyEvent(char keyChar, int keyCode) {
		KeysMap k = null;

		if (isKey(keyChar, keyCode, 0)) {
			k = KeysMap.UP;

		} else if (isKey(keyChar, keyCode, 1)) {
			k = KeysMap.DOWN;

		} else if (isKey(keyChar, keyCode, 2)) {
			k = KeysMap.LEFT;

		} else if (isKey(keyChar, keyCode, 3)) {
			k = KeysMap.RIGHT;

		} else if (isKey(keyChar, keyCode, 4)) {
			k = KeysMap.B0;

		} else if (isKey(keyChar, keyCode, 5)) {
			k = KeysMap.B1;
		}

		CLICK_MAP.press(k);

		if (k != null) {
			if (temp != null) {
				if (temp instanceof Loja) {
					// TODO dialogs and actions
					((Loja) temp).press(k);
				}
			}

			p.press(k);
			// n.press(k);

		}
	}

	@Override
	public void keyRelease(char keyChar, int keyCode) {
		p.stop();
		
		KeysMap k = null;

		if (isKey(keyChar, keyCode, 0)) {
			//n.release(KeysMap.UP);
			p.release(KeysMap.UP);
			
			k = KeysMap.UP;

		} else if (isKey(keyChar, keyCode, 1)) {
			//n.release(KeysMap.DOWN);
			p.release(KeysMap.DOWN);
			
			k = KeysMap.DOWN;

		} else if (isKey(keyChar, keyCode, 2)) {
			//n.release(KeysMap.LEFT);
			p.release(KeysMap.LEFT);
			
			k = KeysMap.LEFT;

		} else if (isKey(keyChar, keyCode, 3)) {
			//n.release(KeysMap.RIGHT);
			p.release(KeysMap.RIGHT);
			
			k = KeysMap.RIGHT;

		} else if (isKey(keyChar, keyCode, 4)) {
			k = KeysMap.B0;

		} else if (isKey(keyChar, keyCode, 5)) {
			k = KeysMap.B1;
		}
		
		CLICK_MAP.release(k);
	}

	public Player getPlayer() {
		return p;
	}
}
