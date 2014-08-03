package br.com.mvbos.emundos.data;

import br.com.mvbos.jeg.scene.Pxy;

public class NavePlaces {

	public static final NavePlaces blank = new NavePlaces();
	
	private Pxy control;
	private Pxy energy;

	public Pxy getControl() {
		return control;
	}

	public void setControl(Pxy control) {
		this.control = control;
	}

	public Pxy getEnergy() {
		return energy;
	}

	public void setEnergy(Pxy energy) {
		this.energy = energy;
	}

}
