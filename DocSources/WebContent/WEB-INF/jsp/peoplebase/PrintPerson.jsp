<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<a href="javascript:window.print()" class="print" title="Print"></a>

	<h4><fmt:message key=“people.printPerson.documentarySourcesForArtsAndHumanitiesYears”/><br />Person Report</h4>
	
	
	<h3>${person.mapNameLf}</h3>
	<table>
		<tr>
			<td width="70"><fmt:message key=“people.printPerson.birth”/></td>
			<td width="300" class="value">${person.bornYear} ${person.bornMonth} ${person.bornDay}</td>
		</tr>
		<tr>
			<td width="70"><fmt:message key=“people.printPerson.death”/></td>
	
			<td width="300" class="value">${person.deathYear} ${person.deathMonth} ${person.deathDay}</td>
		</tr>
		<tr>
			<td width="70"><fmt:message key=“people.printPerson.birthPlace”/></td>
			<td width="300" class="value">${person.bornPlace.placeNameFull}</td>
		</tr>
		<tr>
	
			<td width="70"><fmt:message key=“people.printPerson.deathPlace”/></td>
			<td width="300" class="value">${person.deathPlace.placeNameFull}</td>
		</tr>
	</table>
	
	<c:if test="${docsRelated != 0}">
		<img src="<c:url value="/images/1024/img_hr_print.png"/>" style="margin: 10px 0 10px 85px" />
	
		<h5><fmt:message key=“people.printPerson.documentsRelated”/></h5>
		<table>
			<tr>
				<td width="240"><fmt:message key=“people.printPerson.relatedToThisPersonEntry”/></td><td class="value">${docsRelated}</td>
			</tr>
			<tr>
				<td width="240"><fmt:message key=“people.printPerson.sender”/></td><td class="value">${senderDocsRelated}</td>
			</tr>
			<tr>
				<td width="240"><fmt:message key=“people.printPerson.recipient”/></td><td class="value">${recipientDocsRelated}</td>
			</tr>
			<tr>
				<td width="240"><fmt:message key=“people.printPerson.referringTo”/></td><td class="value">${referringDocsRelated}</td>
			</tr>			
		</table>
	</c:if>
	
	<img src="<c:url value="/images/1024/img_hr_print.png"/>" style="margin: 10px 0 10px 85px" />
	
	<h5><fmt:message key=“people.printPerson.names”/></h5>
	<table>
		<c:forEach items="${person.altName}" var="currentName">
			<tr>
				<td width="70">${currentName.nameType}</td>
				<td width="300" class="value">${currentName.namePrefix} ${currentName.altName}</td>
			</tr>
		</c:forEach>	
	</table>
	
	<img src="<c:url value="/images/1024/img_hr_print.png"/>" style="margin: 10px 0 10px 85px" />
	
	<h5><fmt:message key=“people.printPerson.titlesOccupations”/></h5>
	<table>
		<c:forEach items="${person.poLink}" var="currentPoLink">
			<tr>
				<c:if test="${currentPoLink.preferredRole}">
					<td width="10"><img src="<c:url value="/images/1024/img_preferred.png"/>" alt="Preferred Role" /></td>
				</c:if>
				<c:if test="${!currentPoLink.preferredRole}">
					<td width="10"></td>
				</c:if>
				<td width="190">${currentPoLink.titleOccList.titleOcc}<br />${currentPoLink.titleOccList.roleCat.roleCatMinor}</td>
				<td width="200" class="valueRight"><fmt:message key=“people.printPerson.start”/> ${currentPoLink.startDate} | <fmt:message key=“people.printPerson.end”/> ${currentPoLink.endDate}</td>
			</tr>
		</c:forEach>
	</table>
	
	<img src="<c:url value="/images/1024/img_hr_print.png"/>" style="margin: 10px 0 10px 85px" />
	
	<h5><fmt:message key=“people.printPerson.parents”/></h5>
	<table>
		<c:forEach items="${person.parents}" var="currentParent">
			<tr>
				<c:if test="${currentParent.parent.gender == 'M'}">
					<td width="60"><fmt:message key=“people.printPerson.father”/></td>
					<td width="235" class="value">${currentParent.parent}</td>
					<td width="120" class="valueRight"><fmt:message key=“people.printPerson.born”/> ${currentParent.parent.bornYear} | <fmt:message key=“people.printPerson.death”/> ${currentParent.parent.deathYear}</td>
				</c:if>
			</tr>
		</c:forEach>
		
		<c:forEach items="${person.parents}" var="currentParent">
			<tr>				
				<c:if test="${currentParent.parent.gender == 'F'}">
					<td width="60"><fmt:message key=“people.printPerson.mother”/></td>
					<td width="235" class="value">${currentParent.parent}</td>
					<td width="120" class="valueRight"><fmt:message key=“people.printPerson.born”/> ${currentParent.parent.bornYear} | <fmt:message key=“people.printPerson.death”/> ${currentParent.parent.deathYear}</td>
				</c:if>
			</tr>
		</c:forEach>
	</table>
	
	<img src="<c:url value="/images/1024/img_hr_print.png"/>" style="margin: 10px 0 10px 85px" />
	
	<h5><fmt:message key=“people.printPerson.children”/></h5>
	<table>
		<c:forEach items="${children}" var="currentChild">
			<tr>
				<td width="290">${currentChild.child}</td>
				<td width="120" class="valueRight"><fmt:message key=“people.printPerson.birth”/> ${currentChild.child.bornYear} | <fmt:message key=“people.printPerson.death”/> ${currentChild.child.deathYear}</td>
			</tr>
		</c:forEach>
	</table>	
	
	<img src="<c:url value="/images/1024/img_hr_print.png"/>" style="margin: 10px 0 10px 85px" />
	
	<h5><fmt:message key=“people.printPerson.spouses”/></h5>
	<table>
		<c:forEach items="${marriages}" var="currentMarriage">
			<tr>
			<c:if test="${person.personId == currentMarriage.husband.personId}">
				<td width="200">${currentMarriage.wife}</td>
				<td width="200" class="valueRight"><fmt:message key=“people.printPerson.marriage”/> ${currentMarriage.startYear} - ${currentMarriage.endYear} | <fmt:message key=“people.printPerson.death”/> ${currentMarriage.wife.deathYear}</td>
			</c:if>
			
			<c:if test="${person.personId == currentMarriage.wife.personId}">
				<td width="200">${currentMarriage.husband}</td>
				<td width="200" class="valueRight"><fmt:message key=“people.printPerson.marriage”/> ${currentMarriage.startYear} - ${currentMarriage.endYear} | <fmt:message key=“people.printPerson.death”/> ${currentMarriage.husband.deathYear}</td>
			</c:if>
			</tr>
		</c:forEach>
	</table>
	
	<img src="<c:url value="/images/1024/img_hr_print.png"/>" style="margin: 10px 0 10px 85px" />
	
	
	<h5><fmt:message key=“people.printPerson.researchNotes”/></h5>
	<table>
		<tr>
			<td width="400" class="value">${person.bioNotes}</td>
		</tr>
	</table>
	
	<img src="<c:url value="/images/1024/img_hr_print.png"/>" style="margin: 10px 0 10px 85px" />
	
