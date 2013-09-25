<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn2" uri="http://bia.medici.org/jsp:jstl" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:set var="projectName" value="${fn2:getApplicationProperty('project.name')}"/>

	<a href="javascript:window.print()" class="print" title="Print"></a>
	
	<div id="top">
		<div id="logoMap"><img src="<c:url value="/images/1024/img_map_print.jpg" />" alt="The Medici Archive Project" /></div>
	</div>
	
	
	<h4>${fn2:getApplicationProperty("project.name")} - <br /><fmt:message key="docbase.printDocument.title.documentReport"/></h4>

	<h5 class="first"><fmt:message key="docbase.printDocument.title.documentDetails"/></h5>
	<table>
		<tr> 
			<td width="25%"><fmt:message key="docbase.printDocument.volumeNumber"/></td>
			<td width="25%" class="value">${document.volume.MDP}</td>
			<c:choose>
				<c:when test="${not empty document.insertNum}">
					<td width="25%"><fmt:message key="docbase.printDocument.insertNumber"/></td>
					<td width="25%" class="value">${document.insertNum} <c:if test="${not empty document.insertLet}">/ ${document.insertLet}</c:if></td>
				</c:when>
				<c:otherwise>
					<td width="25%"></td>
					<td width="25%"></td>
				</c:otherwise>
			</c:choose>
		</tr>
		<tr>
			<td width="25%"><fmt:message key="docbase.printDocument.folioNumber"/></td>
			<td width="25%" class="value">${document.folioNum}<c:if test="${not empty document.folioMod}"> / ${document.folioMod}</c:if><c:if test="${not empty document.folioRectoVerso}"> / ${document.folioRectoVerso}</c:if></td>
			<td width="25%"><fmt:message key="docbase.printDocument.transcribeFolioNumber"/></td>
			<td width="25%" class="value">${document.transcribeFolioNum}<c:if test="${not empty document.transcribeFolioMod}"> / ${document.transcribeFolioMod}</c:if><c:if test="${not empty document.transcribeFolioRectoVerso}"> / ${document.transcribeFolioRectoVerso}</c:if></td>
		</tr>
		<tr>
			<td width="25%"><fmt:message key="docbase.printDocument.citationFormat"/></td>
			<td width="75%" colspan="3" class="value">
				<c:if test="${not empty projectName}">
					<span>${projectName}, </span>
				</c:if>
				<span>Doc ID# ${document.entryId} (${fn2:getApplicationProperty("schedone.istituto")}, ${fn2:getApplicationProperty("schedone.fondo")} ${document.volume.MDP}
					<c:if test="${not empty document.insertNum}">
						<span>, <fmt:message key="docbase.printDocument.insert"/> ${document.insertNum}<c:if test="${not empty document.insertLet}"> / ${document.insertLet}</c:if></span>
					</c:if>
					<c:choose>
						<c:when test="${not empty document.folioNum}">
							<span>, <fmt:message key="docbase.printDocument.folio"/> ${document.folioNum}<c:if test="${not empty document.folioMod}">/ ${document.folioMod}</c:if></span>
						</c:when>
						<c:otherwise>
							<span>, <fmt:message key="docbase.printDocument.notnumberedfolio"/></span>
						</c:otherwise> 
					</c:choose>
					<c:choose>
						<c:when test="${not empty document.transcribeFolioNum}">
							<span>, <fmt:message key="docbase.printDocument.transcribefolio"/> ${document.transcribeFolioNum}<c:if test="${not empty document.transcribeFolioMod}">/ ${document.transcribeFolioMod}</c:if>)</span>
						</c:when>
						<c:otherwise>
							<span>, <fmt:message key="docbase.printDocument.notnumberedtranscribefolio"/>)</span>
						</c:otherwise> 
					</c:choose>
				</span>
			</td>
		</tr>
	</table> 
 
	<img src="<c:url value="/images/1024/img_hr_print.png"/>" style="margin:10px 0 10px 85px"/>

	<h5><fmt:message key="docbase.printDocument.title.correspondentsPeople"/></h5>
	
	<table>
	    <tr> 
	      <td width="15%"><fmt:message key="docbase.printDocument.sender"/></td>
	      <td width="40%" class="value">${document.senderPeople.mapNameLf}</td>
	      <td width="15%"><fmt:message key="docbase.printDocument.senderFrom"/></td>
	      <td width="40%" class="value">${document.senderPlace.placeNameFull}</td>
	    </tr>
	
	    <tr> 
	      <td width="15%"><fmt:message key="docbase.printDocument.recipient"/></td>
	      <td width="40%" class="value">${document.recipientPeople.mapNameLf}</td>
	      <td width="15%"><fmt:message key="docbase.printDocument.recipientTo"/></td>
	      <td width="40%" class="value">${document.recipientPlace.placeNameFull}</td>
	    </tr>
	    <tr> 
	      <td width="15%"><fmt:message key="docbase.printDocument.date"/></td>
	
	      <td width="40%" class="value">${document.docYear} ${document.docMonthNum} ${document.docDay}</td>
	    </tr>
	    <c:if test="${not empty document.yearModern}">
	    	<tr>
	    		<td width="15%"><fmt:message key="docbase.printDocument.yearModern" /></td>
	    		<td width="40%" class="value">${document.yearModern}</td>
	    	</tr>
	    </c:if>
	</table> 
	
	<img src="<c:url value="/images/1024/img_hr_print.png"/>" style="margin:10px 0 10px 85px"/>
	
	<%-- <!-- su due colonne -->
	<h5><fmt:message key="docbase.printDocument.title.extractSynopsis"/></h5>
	<table>
	    <tr> 
			<td width="150" style="padding-right:10px"><fmt:message key="docbase.printDocument.transcription"/></td>
			<td width="150">Document Synopsis</td>
		</tr>
		<tr> 
			<td width="150" class="value" style="padding-right:10px">${document.synExtract.docExtract}</td>
			<td width="150" class="value">${document.synExtract.synopsis}</td>
		</tr>
	</table>
	
	--%>
	
	<h5><fmt:message key="docbase.printDocument.title.transcriptionSynopsis"/></h5>
	<table>
        <tr> 
        	<td width="100%"><fmt:message key="docbase.printDocument.transcription"/></td>
        </tr>
        <tr>
        	<td width="100%" class="value">${document.synExtract.docExtract}</td>
        </tr>
        <tr>
        	<td width="100%"></td>
        </tr>
        <tr>
			<td width="100%"><fmt:message key="docbase.printDocument.synopsis"/></td>
        </tr>
        <tr> 
        	<td width="100%" class="value">${document.synExtract.synopsis}</td>
        </tr>
	</table>
		
	
	<img src="<c:url value="/images/1024/img_hr_print.png"/>" style="margin:10px 0 10px 85px"/>
	
	
	<h5><fmt:message key="docbase.printDocument.title.topics"/></h5>
	<table>
		<c:forEach items="${document.eplToLink}" var="currentTopicAndPlace">
		<tr> 
			<td width="60"><fmt:message key="docbase.printDocument.topic"/></td>
			<td width="300" class="value">${currentTopicAndPlace.topic.topicTitle}</td>
	    </tr>
	    <tr> 
			<td width="60"><fmt:message key="docbase.printDocument.topicPlace"/></td>
			<td width="300" class="value">${currentTopicAndPlace.place.placeNameFull}</td>
	    </tr>
		</c:forEach>
	</table> 
	
	<img src="<c:url value="/images/1024/img_hr_print.png"/>" style="margin:10px 0 10px 85px"/>
