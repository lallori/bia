<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<a href="javascript:window.print()" class="print" title="Print"></a>
	<a href="#" class="pdf" title="Save as PDF"></a>

	<h4>Documentary Sources for the Arts and Humanities 1537 - 1743<br />Person Report</h4>
	
	
	<h3>${person.mapNameLf}</h3>
	<table>
		<tr>
			<td width="70">Birth</td>
			<td width="300" class="value">${person.bornYear} ${person.bornMonth} ${person.bornDay}</td>
		</tr>
		<tr>
			<td width="70">Death</td>
	
			<td width="300" class="value">${person.deathYear} ${person.deathMonth} ${person.deathDay}</td>
		</tr>
		<tr>
			<td width="70">Birth Place</td>
			<td width="300" class="value">${person.bornPlace.placeNameFull}</td>
		</tr>
		<tr>
	
			<td width="70">Death Place</td>
			<td width="300" class="value">${person.deathPlace.placeNameFull}</td>
		</tr>
	</table>
	
	<img src="<c:url value="/images/1024/img_hr_print.png"/>" style="margin: 10px 0 10px 85px" />
	
	<h5>Names</h5>
	<table>
		<c:forEach items="${person.altName}" var="currentName">
			<tr>
				<td width="70">${currentName.nameType}</td>
				<td width="300" class="value">${currentName.namePrefix} ${currentName.altName}</td>
			</tr>
		</c:forEach>	
	</table>
	
	<img src="<c:url value="/images/1024/img_hr_print.png"/>" style="margin: 10px 0 10px 85px" />
	
	<h5>Titles/Occupations</h5>
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
				<td width="200" class="valueRight">Start ${currentPoLink.startDate} | End ${currentPoLink.endDate}</td>
			</tr>
		</c:forEach>
	</table>
	
	<img src="<c:url value="/images/1024/img_hr_print.png"/>" style="margin: 10px 0 10px 85px" />
	
	<h5>Parents</h5>
	<table>
		<c:forEach items="${person.parents}" var="currentParent">
			<tr>
				<c:if test="${currentParent.parent.gender == 'M'}">
					<td width="60">Father</td>
					<td width="235" class="value">${currentParent.parent}</td>
					<td width="120" class="valueRight">Born ${currentParent.parent.bornYear} | Death ${currentParent.parent.deathYear}</td>
				</c:if>
							
				<c:if test="${currentParent.parent.gender == 'F'}">
					<td width="60">Mother</td>
					<td width="235" class="value">${currentParent.parent}</td>
					<td width="120" class="valueRight">Born ${currentParent.parent.bornYear} | Death ${currentParent.parent.deathYear}</td>
				</c:if>
			</tr>
		</c:forEach>
	</table>
	
	<img src="<c:url value="/images/1024/img_hr_print.png"/>" style="margin: 10px 0 10px 85px" />
	
	<h5>Children</h5>
	<table>
		<c:forEach items="${person.children}" var="currentChild">
			<tr>
				<td width="290">${currentChild.child}</td>
				<td width="120" class="valueRight">Birth ${currentChild.child.bornYear} | Death ${currentChild.child.deathYear}</td>
			</tr>
		</c:forEach>
	</table>	
	
	<img src="<c:url value="/images/1024/img_hr_print.png"/>" style="margin: 10px 0 10px 85px" />
	
	<h5>Spouses</h5>
	<table>
		<c:forEach items="${marriages}" var="currentMarriage">
			<tr>
			<c:if test="${person.personId == currentMarriage.husband.personId}">
				<td width="200">${currentMarriage.wife}</td>
				<td width="200" class="valueRight">Marriage ${currentMarriage.startYear} - ${currentMarriage.endYear} | Death ${currentMarriage.wife.deathYear}</td>
			</c:if>
			
			<c:if test="${person.personId == currentMarriage.wife.personId}">
				<td width="200">${currentMarriage.husband}</td>
				<td width="200" class="valueRight">Marriage ${currentMarriage.startYear} - ${currentMarriage.endYear} | Death ${currentMarriage.husband.deathYear}</td>
			</c:if>
			</tr>
		</c:forEach>
	</table>
	
	<img src="<c:url value="/images/1024/img_hr_print.png"/>" style="margin: 10px 0 10px 85px" />
	
	
	<h5>Research Notes</h5>
	<table>
		<tr>
			<td width="400" class="value">${person.bioNotes}</td>
		</tr>
	</table>
	
	<img src="<c:url value="/images/1024/img_hr_print.png"/>" style="margin: 10px 0 10px 85px" />
	
