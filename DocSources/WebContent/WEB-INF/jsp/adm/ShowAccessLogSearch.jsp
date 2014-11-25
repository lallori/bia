<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS">
	<c:url var="ShowAccessLogSearchURL" 	value="/admin/ShowAccessLogSearch.do" />

<h3 class="accessLogSearch"><fmt:message key="adm.showAccessLogSearch.accessLogSearch"/></h3>

<form:form id="accessLogSearchForm" method="post" cssClass="edit" action="${ShowAccessLogSearchURL}">
	<fieldset>
	<legend><b><fmt:message key="adm.showAccessLogSearch.nEwFilter"/></b></legend>
		<div class="listForm">
			<div class="row">
				<div class="col_l"><form:label path="account" for="account"><fmt:message key="adm.showAccessLogSearch.account"/></form:label></div>
				<div class="col_r"><input id="account" name="account" class="input_7c" type="text" value="Contains" disabled=""></div>
				<div class="col_r"><form:input path="account" cssClass="input_25c"/></div>	
			</div>	
			<div class="row">
				<div class="col_l"><form:label path="fromDate" for="fromDate" ><fmt:message key="adm.showAccessLogSearch.fromDate"/></form:label></div>
				<div class="col_r">&nbsp;</div>
		        <div class="col_r"><form:input path="fromDate" cssClass="input_25c"/></div>
			</div>
			<div class="row">
				<div class="col_l"><form:label path="toDate" for="toDate"><fmt:message key="adm.showAccessLogSearch.toDate"/></form:label></div>
		        <div class="col_r">&nbsp;</div>
		        <div class="col_r"><form:input path="toDate" cssClass="input_25c"/></div>
			</div>
			<div class="row">
				<div class="col_l"><form:label path="action" for="account"><fmt:message key="adm.showAccessLogSearch.action"/></form:label></div>
				<div class="col_r"><input id="action" name="action" class="input_7c" type="text" value="Contains" disabled=""></div>
				<div class="col_r"><form:input path="action" cssClass="input_25c"/></div>	
			</div>
		</div>	
	<input class="search" class="button_small fl" type="submit" value="Search" />
	</fieldset>	
</form:form>



<script>
	$j(document).ready(function() {
				
		$j("#accessLogSearchForm").submit(function(){
			var title = "AccessLog Search";
			var formSubmitURL = $j(this).attr("action") + '?' + $j(this).serialize();
			
			var tabExist = false;
			var numTab = 0;
			$j("#tabs ul li a").each(function(){
				if(!tabExist)
					numTab++;
				if(this.text == title)
					tabExist = true;
			});
			if(!tabExist){
				$j("#tabs").tabs( "add" , formSubmitURL, title + "</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
				$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
				return false;
			} else {
				$j("#tabs").tabs("select", numTab-2);
				$j('#tabs ul li').eq(numTab-2).data('loaded', false).find('a').attr('href', formSubmitURL);

				console.log(" tabs ->" + (numTab-1) + " url ->" + formSubmitURL);

				$j("#tabs").tabs("load", numTab-2);
				return false;
			}
		});
		
		$j( "#fromDate" ).datepicker(); 
		$j( "#toDate" ).datepicker();
	});
</script>
</security:authorize>