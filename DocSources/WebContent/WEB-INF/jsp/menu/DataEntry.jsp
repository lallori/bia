<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
					<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS,ROLE_ONSITE_FELLOWS,ROLE_DISTANT_FELLOWS">
						<li><a href="#"><img src="<c:url value="/images/button_entrymenu.png"/>" alt="Entry Menu" width="99" height="65"  /></a>
							<ul>
								<li><a href="#"><i>Add new Document</i></a></li>
								<li><a href="#"><i>Add new Person</i></a></li>

								<li><a href="#"><i>Add new Volume</i></a></li>
								<li><a href="#"><i>Add new Place</i></a></li>
								<li><a href="#"><i>Delete this item</i></a></li>
							</ul> 
						</li>
						<li><a href="#"><img src="<c:url value="/images/button_vetmenu.png"/>" alt="Vet Menu" width="99" height="65"  /></a>
							<ul>
								<li><a href="#"><i>Set "Ready to ve vetted"</i></a></li>

								<li><a href="#"><i>Vet this document</i></a></li>
								<li><a href="#"><i>Un-vet this document</i></a></li>
							</ul>
						</li>
					</security:authorize>