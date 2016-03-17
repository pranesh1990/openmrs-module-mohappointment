package org.openmrs.module.mohappointment.statepattern;

import org.openmrs.module.mohappointment.model.MoHAppointment;

public abstract class State {

	public void isNull() {
	}

	public MoHAppointment confirmed() {
		return null;
	}

	public MoHAppointment upcoming() {
		return null;
	}

	public void attended() {
	}

	public void expired() {
	}

	public void retired() {
	}

	public void postponed() {
	}

	public void inAdvance() {
	}

	public MoHAppointment waiting() {
		return null;
	}

	public static State enter(MoHAppointment appointment) {
		return null;
	}

	public String toString() {
		return null;
	}
}
