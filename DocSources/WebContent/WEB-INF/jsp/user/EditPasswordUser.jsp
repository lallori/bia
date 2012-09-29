<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>


		<div id="ChangePasswordDiv">
		<form:form id="ChangePasswordForm" method="post" cssClass="edit">
			<fieldset> 
				<div class="listFormPassword"> 
					<div class="row"> 
						<div class="col_l">
							<form:label for="oldPassword" path="password">Old Password <font color="#990000">*</font></form:label>
						</div> 
						<div class="col_l">
							<form:password path="oldPassword" cssClass="input_20c" /><form:errors path="oldPassword" />
						</div> 
					</div> 
					<div class="row"> 
						<div class="col_l">
							<form:label for="password" path="password">Password <font color="#990000">*</font></form:label>
						</div> 
						<div class="col_l">
							<form:password path="password" cssClass="input_20c" /><form:errors path="password" />
						</div> 
					</div> 
					<div class="row"> 
						<div class="col_l">
							<form:label for="confirmPassword" path="confirmPassword">Confirm Password <font color="#990000">*</font></form:label>
						</div> 
						<div class="col_l">
							<form:password path="confirmPassword" cssClass="input_20c" /><form:errors path="confirmPassword" />
						</div>
					</div>
			        <div>
			        	<input id="save" type="submit" value="Save" title="Save User Preferences changes" />
			        </div>    
				</div>
			</fieldset>		
		</form:form>
		</div>
	