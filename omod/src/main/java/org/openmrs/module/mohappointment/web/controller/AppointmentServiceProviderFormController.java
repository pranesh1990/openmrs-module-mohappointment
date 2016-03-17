/**
 * 
 */
package org.openmrs.module.mohappointment.web.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openmrs.Person;
import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.module.mohappointment.model.ServiceProviders;
import org.openmrs.module.mohappointment.model.Services;
import org.openmrs.module.mohappointment.service.IAppointmentService;
import org.openmrs.web.WebConstants;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ParameterizableViewController;
import org.springframework.web.servlet.view.RedirectView;

/**
 * @author Kamonyo
 * 
 */
public class AppointmentServiceProviderFormController extends
		ParameterizableViewController {

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName(getViewName());

		IAppointmentService ias = Context.getService(IAppointmentService.class);
		mav.addObject("services", ias.getServices());

		if (request.getParameter("editSP") != null) {

			editServiceProvider(request, mav);
		}

		if (request.getParameter("save") != null) {

			boolean saved = saveServiceProvider(request);

			if (saved)
				request.getSession().setAttribute(
						WebConstants.OPENMRS_MSG_ATTR, "Service Provider successfully Saved");
			else
				request.getSession().setAttribute(
						WebConstants.OPENMRS_ERROR_ATTR,
						"Service Provider Not Saved (Maybe the Provider is already"
								+ " associated with this service!)");
			
			return new ModelAndView(new RedirectView("serviceProvider.list"));
		}

		return mav;
	}

	/**
	 * Saves the Service Provider Object from the "serviceProviderForm.jsp" page
	 * 
	 * @param request
	 * @return true when it saved successfully, false otherwise
	 * @throws Exception
	 */
	private boolean saveServiceProvider(HttpServletRequest request)
			throws Exception {

		IAppointmentService ias = Context.getService(IAppointmentService.class);
		ServiceProviders serviceProvider = null;
		if (request.getParameter("spId") != null) {
			if (!request.getParameter("spId").equals(""))

				serviceProvider = ias.getServiceProviderById(Integer
						.valueOf(request.getParameter("spId")));
		}

		Date startDate = (request.getParameter("startDate").trim()
				.compareTo("") != 0) ? Context.getDateFormat().parse(
				request.getParameter("startDate")) : null;
		Person provider = (request.getParameter("provider").trim()
				.compareTo("") != 0) ? Context.getUserService()
				.getUser(Integer.valueOf(request.getParameter("provider")))
				.getPerson() : null;
		Services service = (request.getParameter("service").trim()
				.compareTo("") != 0) ? ias.getServiceById(Integer
				.valueOf(request.getParameter("service"))) : null;

		if (startDate == null || provider == null || service == null) {
			return false;
		} else {
			ServiceProviders sp = null;
			if (serviceProvider == null) {
				sp = new ServiceProviders();
				sp.setProvider(provider);
				sp.setService(service);
				sp.setStartDate(startDate);
				sp.setVoided(false);
				sp.setCreatedDate(new Date());
				sp.setCreator(Context.getAuthenticatedUser());

			} else {
				sp = serviceProvider;
				sp.setProvider(provider);
				sp.setService(service);
				sp.setStartDate(startDate);
			}

			if (!providerIsAlreadyAssignedThisService(provider, service))
				ias.saveServiceProviders(sp);
			return true;
		}
	}

	/**
	 * Edits/Updates Service Provider with the new Values selected by the User
	 * 
	 * @param request
	 *            the HTTPServletRequest object
	 * @param mav
	 *            the ModelAndView object that allows us to display/sent to
	 *            page/view
	 * @throws Exception
	 */
	private void editServiceProvider(HttpServletRequest request,
			ModelAndView mav) throws Exception {

		IAppointmentService ias = Context.getService(IAppointmentService.class);

		if (request.getParameter("editSP") != null) {

			ServiceProviders serviceProvider = ias
					.getServiceProviderById(Integer.valueOf(request
							.getParameter("editServiceProviderId")));

			String startingDate = Context.getDateFormat().format(
					serviceProvider.getStartDate());
			User user = Context.getUserService()
					.getUsersByPerson(serviceProvider.getProvider(), true)
					.get(0);
			mav.addObject("service", serviceProvider.getService());
			mav.addObject("provider", user);
			mav.addObject("startDate", startingDate);
			mav.addObject("spId", serviceProvider.getServiceProviderId());

		}
	}

	/**
	 * Checks whether the entered Provider and Service are not already
	 * associated in the ServiceProviders list
	 * 
	 * @param provider
	 *            the provider to be matched
	 * @param service
	 *            the service to be matched
	 * @return true if they are already associated
	 */
	private boolean providerIsAlreadyAssignedThisService(Person provider,
			Services service) {
		IAppointmentService ias = Context.getService(IAppointmentService.class);

		for (ServiceProviders sp : ias.getServiceProviders()) {
			if (sp.getProvider().equals(provider)
					&& sp.getService().equals(service) && !sp.isVoided())
				return true;
		}

		return false;
	}

}
