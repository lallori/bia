<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<a href="javascript:window.print()" class="print" title="Print"></a>
	
	<style type="text/css">
		@media print{
			#newPage{
				page-break-after: always;
			}
		}
	</style>
	
	<c:forEach items="${elementsToPrint}" var="currentElement">
		<!-- Document print -->
		
	
			<div id="top">
				<div id="logoMap"><img src="<c:url value="/images/1024/img_map_print.jpg" />" alt="The Medici Archive Project" /></div>
			</div>
			
			<h4><fmt:message key="search.printElementsSearch.documentarySource"/><br /><fmt:message key="search.printElementsSearch.documentReport"/></h4>
		
			<h5 class="first"><fmt:message key="search.printElementsSearch.documentDetails"/></h5>
			<table>
			    <tr> 
			      <td width="25%"><fmt:message key="search.printElementsSearch.volNum"/></td>
			      <td width="30%" class="value">${currentElement.volume.MDP}</td>
			      <td width="25%"><fmt:message key="search.printElementsSearch.folNum"/></td>
			      <td width="30%" class="value">${currentElement.folioNum} <c:if test="${not empty currentElement.folioMod}">/ ${currentElement.folioMod}</c:if></td>
		
			    </tr>
			    <tr>
			      <td width="25%"><fmt:message key="search.printElementsSearch.citationFormat"/></td>
			      <td width="70%" colspan="3" class="value"><fmt:message key="search.printElementsSearch.biaThe"/> ${currentElement.entryId} <fmt:message key="search.printElementsSearch.archivioDi"/> ${currentElement.volume.MDP} 
			      <c:if test="${not empty currentElement.folioNum}"><fmt:message key="search.printElementsSearch.commaFolio"/> ${currentElement.folioNum}</c:if>
			      <c:if test="${empty currentElement.folioNum}"><fmt:message key="search.printElementsSearch.unnumberedFolio"/></c:if>
			      <c:if test="${not empty currentElement.folioMod}">/ ${currentElement.folioMod}</c:if>)</td>
			   	</tr>
			</table> 
		 
			<img src="<c:url value="/images/1024/img_hr_print.png"/>" style="margin:10px 0 10px 85px"/>
		
			<h5><fmt:message key="search.printElementsSearch.correspondentsPeople"/></h5>
			
			<table>
			    <tr> 
			      <td width="15%"><fmt:message key="search.printElementsSearch.sender"/></td>
			      <td width="40%" class="value">${currentElement.senderPeople.mapNameLf}</td>
			      <td width="15%"><fmt:message key="search.printElementsSearch.from"/></td>
			      <td width="40%" class="value">${currentElement.senderPlace.placeNameFull}</td>
			    </tr>
			
			    <tr> 
			      <td width="15%"><fmt:message key="search.printElementsSearch.recipient"/></td>
			      <td width="40%" class="value">${currentElement.recipientPeople.mapNameLf}</td>
			      <td width="15%"><fmt:message key="search.printElementsSearch.to"/></td>
			      <td width="40%" class="value">${currentElement.recipientPlace.placeNameFull}</td>
			    </tr>
			    <tr> 
			      <td width="15%"><fmt:message key="search.printElementsSearch.date"/></td>
			
			      <td width="40%" class="value">${currentElement.docYear} ${currentElement.docMonthNum} ${currentElement.docDay}</td>
			    </tr>
			</table> 
			
			<img src="<c:url value="/images/1024/img_hr_print.png"/>" style="margin:10px 0 10px 85px"/>
			
			<%-- <!-- su due colonne -->
			<h5>Extract/Synopsis</h5>
			<table>
			    <tr> 
					<td width="150" style="padding-right:10px">Document Extract</td>
					<td width="150">Document Synopsis</td>
				</tr>
				<tr> 
					<td width="150" class="value" style="padding-right:10px">${document.synExtract.docExtract}</td>
					<td width="150" class="value">${document.synExtract.synopsis}</td>
				</tr>
			</table>
			
			--%>
			
			<h5><fmt:message key="search.printElementsSearch.extractSynopsis"/></h5>
			<table>
		        <tr> 
		        	<td width="100%"><fmt:message key="search.printElementsSearch.documentExtract"/></td>
		        </tr>
		        <tr>
		        	<td width="100%" class="value">${currentElement.synExtract.docExtract}</td>
		        </tr>
		        <tr>
		        	<td width="100%"></td>
		        </tr>
		        <tr>
					<td width="100%"><fmt:message key="search.printElementsSearch.documentSynopsis"/></td>
		        </tr>
		        <tr> 
		        	<td width="100%" class="value">${currentElement.synExtract.synopsis}</td>
		        </tr>
			</table>
				
			
			<img src="<c:url value="/images/1024/img_hr_print.png"/>" style="margin:10px 0 10px 85px"/>
			
			
			<h5>Topics</h5>
			<table>
				<c:forEach items="${currentElement.eplToLink}" var="currentTopicAndPlace">
				<tr> 
					<td width="60"><fmt:message key="search.printElementsSearch.topic"/></td>
					<td width="300" class="value">${currentTopicAndPlace.topic.topicTitle}</td>
			    </tr>
			    <tr> 
					<td width="60"><fmt:message key="search.printElementsSearch.topicPlace"/></td>
					<td width="300" class="value">${currentTopicAndPlace.place.placeNameFull}</td>
			    </tr>
				</c:forEach>
			</table> 
			
			<img src="<c:url value="/images/1024/img_hr_print.png"/>" style="margin:10px 0 10px 85px"/>
<!-- 			<div id="footer"> -->
<!-- 				<p> -->
<!-- 					The Medici Archive Project, Inc. &copy; -->
<!-- 					<br />  -->
<!-- 					Written content on the Medici Archive Project website and the BIA platform are sole property of the Medici Archive Project, Inc.,a 501(c)(3) Tax Exempt Organization. In any subsequent -->
<!-- 					use the Medici Archive Project must be given appropriate acknowledgment in accordance with U.S. and foreign copyright laws.  -->
<!-- 				</p> -->
<!-- 				<p> To cite content from the BIA platform in a publication, use the "citation format" near the top  of the page.</p> -->
<!-- 			</div> -->
<!-- 			<div id="newPage"></div> -->
		
		
		

	</c:forEach>
	
	 <div id="footer">
		<p>
			<fmt:message key="search.printElementsSearch.theMediciArchive"/> &copy;
			<br /> 
			<fmt:message key="search.printElementsSearch.writtenContent"/>
		</p>
		<p> <fmt:message key="search.printElementsSearch.toCite"/></p>
	</div>
