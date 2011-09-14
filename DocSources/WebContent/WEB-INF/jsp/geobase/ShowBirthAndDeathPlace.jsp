<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div class="background" id="EditBirthDeathPlaceDiv">
	<div class="title">
		<h5>BIRTH and DEATH PLACE </h5>
	</div>
	
	<div class="list">	
		<div class="row">
			<div class="value">${place.bornedPeople.size()} Birth</div>
		</div>
		<div class="row">
			<div class="value">${place.deathPeople.size()} Death</div>
		</div>
	</div>
</div>

<br />
<br />