<%@ include file="/WEB-INF/template/include.jsp"%>

<openmrs:require privilege="Edit Provider Service" otherwise="/login.htm" redirect="/module/mohappointment/serviceProvider.list"/>

<script type="text/javascript">
	var $app = jQuery.noConflict();
	
	$app(document).ready(function(){
		$app("#btEdit").click(function(){
			if(validateFormFields()){
				if(confirm("<spring:message code='@MODULE_ID@.general.save.confirm'/>"))
					this.form.submit();
			}
		});
		
		$app("#btDelete").click(function(){
			if(validateFormFields()){
				if(confirm("<spring:message code='@MODULE_ID@.general.save.confirm'/>"))
					this.form.submit();
			}
		});
	});

	function validateFormFields(){
		var valid=true;
		if(document.getElementsByName("provider")[0].value==''){
			$app("#providerError").html("*");
			$app("#providerError").addClass("error");
			valid=false;
		} else {
			$app("#providerError").html("");
			$app("#providerError").removeClass("error");
		}

		if(document.getElementById("service").value==''){
			$app("#serviceError").html("*");
			$app("#serviceError").addClass("error");
			valid=false;
		} else {
			$app("#serviceError").html("");
			$app("#serviceError").removeClass("error");
		}

		if($app("#startDate").val()==''){
			$app("#startDateError").html("*");
			$app("#startDateError").addClass("error");
			valid=false;
		} else {
			$app("#startDateError").html("");
			$app("#startDateError").removeClass("error");
		}

		if(!valid){
			$app("#errorDiv").html("<spring:message code='@MODULE_ID@.general.fillbeforesubmit'/>");
			$app("#errorDiv").addClass("error");
		} else {
			$app("#errorDiv").html("");
			$app("#errorDiv").removeClass("error");
		}
		
		return valid;
	}
</script>

<div id="errorDiv"></div><br/>

<div id="edit_provider">
<form action="editServiceProvider.form?edit=true" method="get" class="box">

	<table>
		<tr>
			<td><b><spring:message code="@MODULE_ID@.general.provider"/></b></td>
			<td><img border="0" src="<openmrs:contextPath/>/moduleResources/@MODULE_ID@/images/help.gif" title="?"/></td>
			<td><openmrs_tag:userField roles="Provider" formFieldName="provider" initialValue="${user}" /></td>
			<td valign="top"><span id="providerError"></span></td>
		</tr>
		<tr> 
			<td><b><spring:message code="@MODULE_ID@.general.service"/></b></td>
			<td><img border="0" src="<openmrs:contextPath/>/moduleResources/@MODULE_ID@/images/help.gif" title="?"/></td>
			<td><select name="service" id="service">
					<option value="">--</option>
					<c:forEach items="${services}" var="service">
						<option value="${service.serviceId}" <c:if test='${serviceProvider.service.serviceId==service.serviceId}'>selected='selected'</c:if>>${service.name}</option>
					</c:forEach>
				</select>
			</td>
			<td valign="top"><span id="serviceError"></span></td>
		</tr>
		<tr>
			<td><b><spring:message code="@MODULE_ID@.general.startdate"/></b></td>
			<td><img border="0" src="<openmrs:contextPath/>/moduleResources/@MODULE_ID@/images/help.gif" title="?"/></td>
			<td><input value="${serviceProvider.startDate}" type="text" name="startDate" id="startDate" size="11" onclick="showCalendar(this);"/></td>
			<td valign="top"><span id="startDateError"></span></td>
		</tr>
		<tr>
			<td></td>
			<td></td>
			<td><input type="button" id="btEdit" value="<spring:message code='@MODULE_ID@.general.edit'/>"></td>
			<td><input type="button" id="btDelete" value="<spring:message code='@MODULE_ID@.general.delete'/>"></td>
		</tr>
	</table>

</form>
</div>
