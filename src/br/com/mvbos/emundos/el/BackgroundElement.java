package br.com.mvbos.emundos.el;

import java.awt.Graphics2D;

import br.com.mvbos.emundos.sc.Planeta;
import br.com.mvbos.jeg.element.ElementModel;
import br.com.mvbos.jeg.engine.Engine;
import br.com.mvbos.jeg.window.Camera;

public class BackgroundElement extends ElementModel {

	// TODO criar versao pre-load

	private short total;

	private boolean useLoop = false;

	public BackgroundElement() {

	}

	public BackgroundElement(boolean useLoop) {
		this.useLoop = useLoop;
	}

	@Override
	public void loadElement() {
		setSize(getImage().getIconWidth(), getImage().getIconHeight());
		setPxy(0, Planeta.h - getHeight());

		if (!useLoop) {
			total = (short) Math.round((float) Planeta.w / getWidth());
			System.out.printf("%s div %s\n", Planeta.w, getWidth());
			System.out.println("Total = " + total);
		}
	}

	@Override
	public void drawMe(Graphics2D g) {

		if (!useLoop) {
			for (short i = 0; i < total; i++) {

				//if (Camera.c().getCpx() >= getWidth() * i) {
					g.drawImage(getImage().getImage(), Camera.c().fx(getWidth() * i), Camera.c().fy(getPy()), null);
					//System.out.print("draw ");
				//}

			}

			//System.out.println();

		} else {
			int st = getAllWidth();
			if (st <= 0) {
				return;
			}

			if (Camera.c().getCpx() < getAllWidth()) {
				g.drawImage(getImage().getImage(), Camera.c().fx(getPx()), Camera.c().fy(getPy()), null);
			}

			while (st < Engine.getIWindowGame().getCanvasWidth() + Camera.c().getCpx()) {
				if (st + getWidth() > Camera.c().getCpx()) {
					g.drawImage(getImage().getImage(), Camera.c().fx(st), Camera.c().fy(getPy()), null);
				}

				st += getWidth();
			}
		}
	}
}
