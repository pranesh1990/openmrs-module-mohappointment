/**
 * 
 */
package org.openmrs.module.mohappointment.observerpattern;

import org.openmrs.module.mohappointment.model.MoHAppointment;

/**
 * @author Kamonyo
 * 
 */
public class ConcreteObserver implements IObserver {

	private MoHAppointment appointment;

	public ConcreteObserver(MoHAppointment appointment) {
		this.appointment = appointment;
		this.appointment.addObserver(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.openmrs.module.mohappointment.observerpattern.IObserver#update(org
	 * .openmrs.module.mohappointment.observerpattern.ISubject)
	 */
	@Override
	public void update(ISubject subject) {
		System.out.println("********* Observer Pattern *********");
		System.out.println("The Subject State is : "
				+ appointment.getState().toString());
	}

}
