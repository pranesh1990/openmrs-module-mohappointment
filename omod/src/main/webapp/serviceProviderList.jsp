<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<openmrs:require privilege="Manage Services and Providers" otherwise="/login.htm" redirect="/module/mohappointment/serviceProvider.list"/>

<openmrs:htmlInclude file="/moduleResources/@MODULE_ID@/style/appointment.css" />
<openmrs:htmlInclude file="/moduleResources/@MODULE_ID@/style/listing.css" />
<openmrs:htmlInclude file="/scripts/calendar/calendar.js" />

<openmrs:htmlInclude file="/moduleResources/@MODULE_ID@/scripts/jquery-1.3.2.js" />

<%@ include file="templates/serviceProviderHeader.jsp"%>

<h2><spring:message code="@MODULE_ID@.appointment.service.provider.management"/></h2>
<br/>
<a href="serviceProvider.form"><spring:message code="@MODULE_ID@.appointment.service.provider.add"/></a>
<br/><br/>

<script type="text/javascript">
	var $app = jQuery.noConflict();
	
	// Checking whether the User wants to delete the data
	function submitData(spId){
		var sp = "#deleteCtrl_"+spId
		var hrefValue = "serviceProvider.list?deleteSP=true&serviceProviderId=" + spId;
		
		if(confirm("<spring:message code='@MODULE_ID@.general.delete.confirm'/>"))
			$app(sp).attr("href", hrefValue);
	}
	
	function showExportDialog(){
		showDialog();
	}
	
	function showDialog(){
		$app("#divDlg").html("<div id='dialog' style='font-size: 0.9em;' title='<spring:message code='@MODULE_ID@.export.data'/>'><p><div id='result'>"+$app('#dlgCtnt').html()+"</div></p></div>");
		$app("#dialog").dialog({
			zIndex: 980,
			bgiframe: true,
			height: 220,
			width: 550,
			modal: true
		});	
	}
	
	function showEditDialog(pId){
		distroyResultDiv();
		showEditWindow(pId);
	}
	
	function showEditWindow(pId){
		$app("#divWindow").html("<div id='dialog' style='font-size: 0.9em;' title='<spring:message code='@MODULE_ID@.appointment.service.provider.form'/>'><div id='result'>"+$app('#editWindow').html()+"</div></div>");
		$app("#dialog").dialog({
			zIndex: 980,
			bgiframe: true,
			height: 220,
			width: 550,
			modal: true
		});

		var url="editServiceProvider.form?serviceProviderId="+pId;
		$app.get(url, function(data) {
			  	$app("#result").html(data);
		});	
	}

	function distroyResultDiv(){
		while(document.getElementById("dialog")){
			var DIVtoRemove = document.getElementById("dialog");
			DIVtoRemove.parentNode.removeChild(DIVtoRemove);
		}
	}
		
</script>

<div class="searchParameterBox box">
	<div class="list_container" style="width: 99%">
		<div class="list_title">
			<div class="list_title_msg"><spring:message code="@MODULE_ID@.appointment.service.provider.current"/></div>
			<div class="list_title_bts">
			
				<form style="display: inline;" action="#" method="post">
					<input onclick="showExportDialog();" type="button" class="list_exportBt" value="<spring:message code="@MODULE_ID@.general.export"/>"/>
				</form>	
					
			</div>
			<div style="clear:both;"></div>
		</div>
		<table class="list_data">
			<tr>
				<th class="columnHeader">Services</th>
				<th class="columnHeader"><spring:message code="@MODULE_ID@.general.number"/></th>
				<th class="columnHeader">Providers</th>
				<th class="columnHeader">Startdate</th>
				<th class="columnHeader"></th>
			</tr>
			<c:if test="${empty serviceProviders}">
				<tr>
					<td colspan="4" style="text-align: center;"><spring:message code="@MODULE_ID@.general.empty"/></td>
				</tr>
			</c:if>
			<c:set value="0" var="index"/>
			<c:forEach items="${serviceProviders}" var="sp" varStatus="status">
				<tr>
					<c:choose>
						<c:when test="${sp.service.serviceId == currentService}">
							<td class="rowValue" <c:if test="${index%2!=0}">style="background-color: whitesmoke;"</c:if>><c:if test="${sp.service.serviceId!=currentService}">${sp.service.name}<c:set value="${sp.service.serviceId}" var="currentService"/></c:if></td>
						</c:when>
						<c:otherwise>
							<c:set value="${index+1}" var="index"/>
							<td class="rowValue" style="border-top: 1px solid cadetblue; <c:if test="${index%2!=0}">background-color: whitesmoke;</c:if>"><c:if test="${sp.service.serviceId!=currentService}">${sp.service.name}<c:set value="${sp.service.serviceId}" var="currentService"/></c:if></td>
						</c:otherwise>
					</c:choose>
					<td class="rowValue ${status.count%2!=0?'even':''}">${((param.page-1)*pageSize)+status.count}.</td>
					<td class="rowValue ${status.count%2!=0?'even':''}">${sp.provider.personName}</td>
					<td class="rowValue ${status.count%2!=0?'even':''}"><openmrs:formatDate date="${sp.startDate}" type="medium"/></td>
					<!-- <td class="rowValue ${status.count%2!=0?'even':''}"><input type="button" id="providerServiceEdit" value="<spring:message code='@MODULE_ID@.general.edit'/>"></td> -->
					<td class="rowValue ${status.count%2!=0?'even':''}"><a href="serviceProvider.form?editSP=true&editServiceProviderId=${sp.serviceProviderId}"><spring:message code='@MODULE_ID@.general.edit'/></a>
					 | <a id="deleteCtrl_${sp.serviceProviderId}" onclick="submitData(${sp.serviceProviderId});" href="#"><spring:message code='@MODULE_ID@.general.delete'/></a></td>	
				</tr>
			</c:forEach>
		</table>
		<div class="list_footer">
			<div class="list_footer_info">&nbsp;&nbsp;&nbsp;</div>
			<div class="list_footer_pages">
				&nbsp;&nbsp;&nbsp;
			</div>
			<div style="clear: both"></div>
		</div>
	</div>

</div>

<div id="divDlg"></div>
<div id="dlgCtnt" style="display: none;">
	<form action="service.list?export=true" method="post">
	
		<%@ include file="templates/exportForm.jsp"%>
		
	</form>
</div>

<%@ include file="/WEB-INF/template/footer.jsp"%>