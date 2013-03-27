/**
 * 
 */
package org.openmrs.module.mohappointment.impl;

import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.openmrs.Concept;
import org.openmrs.Patient;
import org.openmrs.Person;
import org.openmrs.module.mohappointment.db.AppointmentDAO;
import org.openmrs.module.mohappointment.model.Appointment;
import org.openmrs.module.mohappointment.model.AppointmentState;
import org.openmrs.module.mohappointment.model.ServiceProviders;
import org.openmrs.module.mohappointment.model.Services;
import org.openmrs.module.mohappointment.service.IAppointmentService;

/**
 * @author Kamonyo
 * 
 */
public class AppointmentService implements IAppointmentService {

	private AppointmentDAO appointmentDAO;

	public AppointmentDAO getAppointmentDAO() {
		return appointmentDAO;
	}

	public void setAppointmentDAO(AppointmentDAO appointmentDAO) {
		this.appointmentDAO = appointmentDAO;
	}

	@Override
	public void cancelAppointment(Appointment appointment) {

		appointmentDAO.cancelAppointment(appointment);
	}

	@Override
	public Collection<Appointment> getAllAppointments() {

		return appointmentDAO.getAllAppointments();
	}

	@Override
	public Appointment getAppointmentById(int appointmentId) {

		return appointmentDAO.getAppointmentById(appointmentId);
	}

	@Override
	public List<Integer> getAppointmentIdsByMulti(Object[] conditions, int limit) {

		return appointmentDAO.getAppointmentIdsByMulti(conditions, limit);
	}

	@Override
	public Integer lastAppointmentId() {

		return appointmentDAO.lastAppointmentId();
	}

	@Override
	public void loadAllAppointments() {

		appointmentDAO.loadAllAppointments();
	}

	@Override
	public void saveAppointment(Appointment appointment) {

		appointmentDAO.saveAppointment(appointment);
	}

	@Override
	public void updateAppointment(Appointment appointment) {

		appointmentDAO.updateAppointment(appointment);
	}

	@Override
	public void updateState(Appointment appointment, Integer stateId) {

		appointmentDAO.updateAppointment(appointment);
	}

	@Override
	public Collection<AppointmentState> getAppointmentStates() {

		return appointmentDAO.getAppointmentStates();
	}

	@Override
	public AppointmentState getAppointmentStatesByName(String name) {

		return appointmentDAO.getAppointmentStatesByName(name);
	}

	@Override
	public void saveService(Services service) {
		appointmentDAO.saveService(service);
	}

	@Override
	public void saveServiceProviders(ServiceProviders serviceProvider) {
		appointmentDAO.saveServiceProviders(serviceProvider);
	}

	@Override
	public void updateService(Services service) {
		appointmentDAO.updateService(service);
	}

	@Override
	public void updateServiceProviders(ServiceProviders serviceProvider) {
		appointmentDAO.updateServiceProviders(serviceProvider);
	}

	@Override
	public Collection<Integer> getPersonsByService(Services service) {
		return appointmentDAO.getPersonsByService(service);
	}

	@Override
	public Services getServiceByProvider(Person provider) {
		return appointmentDAO.getServiceByProvider(provider);
	}

	@Override
	public Services getServiceById(Integer serviceId) {
		return appointmentDAO.getServiceById(serviceId);
	}

	@Override
	public Collection<ServiceProviders> getServiceProviders() {
		return appointmentDAO.getServiceProviders();
	}

	@Override
	public Collection<Services> getServices() {
		return appointmentDAO.getServices();
	}

	@Override
	public Collection<Services> getServicesByProvider(Person provider) {
		return appointmentDAO.getServicesByProvider(provider);
	}

	@Override
	public Services getServiceByConcept(Concept concept) {
		return appointmentDAO.getServiceByConcept(concept);
	}

	@Override
	public ServiceProviders getServiceProviderById(int serviceProviderId) {
		return appointmentDAO.getServiceProviderById(serviceProviderId);
	}

	/**
	 * @throws ParseException 
	 * @see org.openmrs.module.mohappointment.service.IAppointmentService#getAllWaitingAppointmentsByPatient(org.openmrs.Patient, org.openmrs.module.mohappointment.model.AppointmentState)
	 */
	@Override
	public Collection<Appointment> getAllWaitingAppointmentsByPatient(
			Patient patient, AppointmentState state, Date appointmentDate) throws ParseException {
		return appointmentDAO
				.getAllWaitingAppointmentsByPatient(patient, state, appointmentDate);
	}

}
