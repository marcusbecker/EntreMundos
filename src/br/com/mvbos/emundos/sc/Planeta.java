package br.com.mvbos.emundos.sc;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import br.com.mvbos.emundos.el.Nave;
import br.com.mvbos.emundos.el.Player;
import br.com.mvbos.jeg.engine.Engine;
import br.com.mvbos.jeg.engine.GraphicTool;
import br.com.mvbos.jeg.engine.KeysMap;
import br.com.mvbos.jeg.scene.impl.SceneDefault;
import br.com.mvbos.jeg.window.impl.MemoryImpl;

public class Planeta extends SceneDefault {
	
	private static final GraphicTool G = GraphicTool.g();
	private Player p = new Player();
	private Nave n = new Nave();

	private int[] keys = { 38, 40, 37, 39 };// cima, baixo, esq, dir

	@Override
	public boolean startScene() {
		memo = new MemoryImpl(30);

		memo.registerElement(p);
		memo.registerElement(n);

		for (int i = 0; i < memo.getElementCount(); i++) {
			memo.getByElement(i).loadElement();
		}

		resizeWindow();
		
		return true;
	}

	@Override
	public Color getBgColor() {
		return Color.ORANGE;
	}

	@Override
	public void update() {
		for (int i = 0; i < memo.getElementCount(); i++) {
			memo.getByElement(i).update();
		}		
	}

	@Override
	public void resizeWindow() {
		p.setPy(Engine.getIWindowGame().getCanvasHeight() - 40);
		n.setPy(Engine.getIWindowGame().getCanvasHeight() - 60);
	}

	@Override
	public void drawElements(Graphics2D g2d) {
		if (memo == null) {
			Engine.log("Draw Error: Scene don't started.");
			return;
		}

		for (int i = memo.getElementCount() - 1; i >= 0; i--) {
			if (memo.getByElement(i).isVisible()) {
				memo.getByElement(i).drawMe(g2d);
			}
		}
		
		
		//AffineTransform old = g2d.getTransform();
		//AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
		//g2d.setTransform(tx);
	}

	@Override
	public void keyEvent(char keyChar, int keyCode) {
		if (keyCode == keys[0]) {
			p.go(KeysMap.UP);
			n.go(KeysMap.UP);
			
		} else if (keyCode == keys[1]) {
			p.go(KeysMap.DOWN);
			n.go(KeysMap.DOWN);
			
		} else if (keyCode == keys[2]) {
			p.go(KeysMap.LEFT);
			n.go(KeysMap.LEFT);

		} else if (keyCode == keys[3]) {
			p.go(KeysMap.RIGHT);
			n.go(KeysMap.RIGHT);

		} else /*if (keyCode == keys[4])*/ {
			p.action();
			n.action();			
		}
		
		//n.setInside(G.collide(p, n) != null);
		
		if (G.collide(p, n) != null) {
			n.setPlayer(p);
		} else {
			n.setPlayer(null);
		}
	}
	
	@Override
	public void keyRelease(char keyChar, int keyCode) {
		p.stop();
	}
}

