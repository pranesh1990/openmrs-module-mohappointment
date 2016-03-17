/**
 * 
 */
package org.openmrs.module.mohappointment.web.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.mohappointment.model.ServiceProviders;
import org.openmrs.module.mohappointment.service.IAppointmentService;
import org.openmrs.module.mohappointment.utils.FileExporterUtil;
import org.openmrs.web.WebConstants;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

/**
 * @author Yves GAKUBA
 * 
 */
public class AppointmentServiceProviderListController extends
		ParameterizableViewController {

	private Log log = LogFactory.getLog(getClass());

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName(getViewName());


		if (request.getParameter("deleteSP") != null) {

			boolean deleted = deleteServiceProvider(request, mav);

			if (deleted)
				request.getSession().setAttribute(
						WebConstants.OPENMRS_MSG_ATTR,
						"Service Provider Removed");
			else
				request.getSession().setAttribute(
						WebConstants.OPENMRS_ERROR_ATTR,
						"Provider Service NOT DELETED!");
		}
		
		if (request.getParameter("export") != null) {
			FileExporterUtil xprt = new FileExporterUtil();
			xprt.exportToCSVFile(request, response,
					"List of current service providers");
		}

		IAppointmentService ias = Context.getService(IAppointmentService.class);
		List<ServiceProviders> providers = (List<ServiceProviders>) ias.getServiceProviders();
		mav.addObject("serviceProviders", providers);
		mav.addObject("today", Context.getDateFormat().format(new Date()));
		mav.addObject("creator", Context.getAuthenticatedUser());
		mav.addObject("reportName",
				"mohappointment.appointment.service.provider.current");

		return mav;
	}
	
	/**
	 * Deletes/Voids the Service Provider that was clicked
	 * 
	 * @param request the HttpServletRequest
	 * @param mav ModelAndView that allows us to display/send to page/view
	 * @return true when deleted successfully, false otherwise
	 * @throws Exception
	 */
	private boolean deleteServiceProvider(HttpServletRequest request,
			ModelAndView mav) throws Exception {

		IAppointmentService ias = Context.getService(IAppointmentService.class);

		if (request.getParameter("deleteSP") != null)
			if (request.getParameter("deleteSP").equals("true")) {

				ServiceProviders serviceProvider = ias
						.getServiceProviderById(Integer.valueOf(request
								.getParameter("serviceProviderId")));

				mav.addObject("spId", serviceProvider.getServiceProviderId());

				serviceProvider.setVoided(true);
				serviceProvider.setVoidedDate(new Date());
				serviceProvider.setVoidedBy(Context.getAuthenticatedUser());
				serviceProvider
						.setVoidedReason("The service is no longer associated to this provider");

				ias.saveServiceProviders(serviceProvider);

			}
		return true;
	}
}
