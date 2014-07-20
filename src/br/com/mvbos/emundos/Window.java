package br.com.mvbos.emundos;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import br.com.mvbos.jeg.element.ElementMovableModel;
import br.com.mvbos.jeg.engine.Engine;
import br.com.mvbos.jeg.engine.GameEngineModel;
import br.com.mvbos.jeg.scene.IScene;
import br.com.mvbos.jeg.scene.impl.SceneDefault;
import br.com.mvbos.jeg.window.IWindowGame;

public class Window extends JFrame implements IWindowGame {

	private static final int FPS = 20;
	private static final int UPS = 30;

	private static final long serialVersionUID = -8393832754925763237L;

	private JPanel canvas;
	private BufferedImage buffer;
	private int w = 600;
	private int h = 600;

	private IScene scene;

	private boolean freeze = true;
	
	private final GameEngineModel gem = new GameEngineModel(0);

	public void init() {
		buffer = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

		canvas = new JPanel() {
			private static final long serialVersionUID = 1L;

			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(buffer, 0, 0, null);
			}
		};

		// resize control
		addComponentListener(new ComponentListener() {
			@Override
			public void componentShown(ComponentEvent e) {
			}

			@Override
			public void componentResized(ComponentEvent e) {
				if (getWidth() != w || getHeight() != h) {
					w = getWidth();
					h = getHeight();
					Engine.log("w wid", w);
					Engine.log("h hei", h);

					buffer = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
				}

			}

			@Override
			public void componentMoved(ComponentEvent e) {
			}

			@Override
			public void componentHidden(ComponentEvent e) {
				System.out.println("hidden");
			}
		});
		// setResizable(false);

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		getContentPane().add(canvas);

		setSize(w, h);
		setVisible(true);

		startConfig();
		startGame();
	}

	@Override
	public void changeScene(IScene scene) {
		if (this.scene != null) {
			this.scene.changeSceneEvent();
		}

		this.scene = scene;
		freeze = !scene.startScene();
	}

	@Override
	public void freeze(boolean b, int option) {
		freeze = b;

	}

	@Override
	public void startConfig() {
		Engine.window = this;

	}

	@Override
	public void startGame() {
		gem.fill(this, FPS, UPS);
		gem.start();
	}

	@Override
	public void resumeGame() {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateGame() {
		if (!freeze && scene != null)
			scene.update();
	}

	@Override
	public void drawGame() {
		if (freeze) {
			return;
		}

		Graphics2D g2d = buffer.createGraphics();

		// clear
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, getWidth(), getHeight());

		if (scene != null)
			scene.drawElements(g2d);


		canvas.repaint();
		g2d.dispose();
	}

	@Override
	public void engineNotification(int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getWindowWidth() {
		return w;
	}

	@Override
	public int getWindowHeight() {
		return h;
	}

	@Override
	public void selectMovableElement(ElementMovableModel e) {
		// TODO Auto-generated method stub

	}

	@Override
	public ElementMovableModel getMovableElement() {
		// TODO Auto-generated method stub
		return null;
	}

	public static void main(String[] args) {
		Window game = new Window();
		game.init();
		game.changeScene(new SceneDefault());
	}

}
