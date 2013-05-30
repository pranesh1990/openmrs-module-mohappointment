/**
 * 
 */
package org.openmrs.module.mohappointment.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.mohappointment.model.Appointment;
import org.openmrs.module.mohappointment.model.AppointmentState;
import org.openmrs.module.mohappointment.service.IAppointmentService;
import org.openmrs.module.mohappointment.utils.AppointmentUtil;
import org.openmrs.web.controller.PortletController;

/**
 * @author Yves GAKUBA
 * 
 */
public class AppointmentDashboardPortletController extends PortletController {

	@Override
	protected void populateModel(HttpServletRequest request,
			Map<String, Object> model) {

		try {

			Patient patient = Context.getPatientService().getPatient(
					Integer.valueOf(request.getParameter("patientId")));

			Object[] conditions = { patient.getPatientId(), null, null, null,
					null, null, null, null };

			List<Integer> appointmentIds = AppointmentUtil
					.getAppointmentService().getAppointmentIdsByMulti(
							conditions, 100);
			List<Appointment> appointments = new ArrayList<Appointment>();
			for (Integer appointmentId : appointmentIds) {
				appointments.add(AppointmentUtil.getAppointmentService()
						.getAppointmentById(appointmentId));
			}

			// model.put("appointments", appointments);
			request.setAttribute("appointments", appointments);
			request.setAttribute("patientId", patient.getPatientId());

			if (setAttendedAppointment(request))
				super.populateModel(request, model);
			if (cancelAppointment(request))
				super.populateModel(request, model);

		} catch (Exception e) {
			log.error(">>>>>>>>>>> APPOINTMENT >> An error occured when trying to load appointments on the patient dashboard");
			e.printStackTrace();
		}
		super.populateModel(request, model);
	}

	/**
	 * Sets an appointment as attended (i.e. <code>attended=true</code>)
	 * 
	 * @param request
	 *            the HTTP Servlet Request
	 * @param service
	 *            the Appointment Hibernate Service
	 * @return true when the update is successful, false otherwise
	 */
	private boolean setAttendedAppointment(HttpServletRequest request) {

		if (request.getParameter("appointmentId") != null)
			if (!request.getParameter("appointmentId").equals("")) {
				Integer appointmentId = Integer.valueOf(request
						.getParameter("appointmentId"));

				Appointment appointment = AppointmentUtil
						.getAppointmentService().getAppointmentById(
								appointmentId);

				if (request.getParameter("attended") != null)
					if (request.getParameter("attended").equals("true")) {

						AppointmentUtil.saveAttendedAppointment(appointment);

						return true;
					}
			}
		return false;
	}

	/**
	 * Cancels an appointment by setting it to <code>voided==true</code>
	 * 
	 * @param request
	 *            the HTTP Servlet Request
	 * @param service
	 *            the Appointment Hibernate Service
	 * @return true when the cancellation is successful, false otherwise
	 */
	private boolean cancelAppointment(HttpServletRequest request) {

		if (request.getParameter("appointmentId") != null)
			if (!request.getParameter("appointmentId").equalsIgnoreCase("")) {

				Integer appointmentId = Integer.valueOf(request
						.getParameter("appointmentId"));
				Appointment appointment = AppointmentUtil
						.getAppointmentService().getAppointmentById(
								appointmentId);

				if (request.getParameter("cancel") != null)
					if (request.getParameter("cancel").equals("true")) {

						AppointmentUtil.cancelAppointment(appointment);

						return true;
					}
			}
		return false;
	}

}
