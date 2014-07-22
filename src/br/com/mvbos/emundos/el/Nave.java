package br.com.mvbos.emundos.el;

import javax.swing.ImageIcon;

import br.com.mvbos.emundos.Config;
import br.com.mvbos.jeg.element.ElementModel;

public class Nave extends ElementModel {

	@Override
	public void loadElement() {
		setAll(10, 10, 140, 120);
		//setImageAndSize(new ImageIcon(Config.PATH + "n.png"));
	}
	
	
}
