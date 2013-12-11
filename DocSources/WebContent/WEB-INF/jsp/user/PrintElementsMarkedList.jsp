<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn2" uri="http://bia.medici.org/jsp:jstl" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<a href="javascript:window.print()" class="print" title="Print"></a>
	
	<style type="text/css">
		@media print{
			.newPage{
				page-break-after: always;
			}
		}
	</style>
	
	<c:forEach items="${elementsToPrint}" var="currentElement" varStatus="status">
		<!-- Document print -->
		<c:if test="${currentElement.document != null}">
	
			<div id="top">
				<div id="logoMap"><img src="<c:url value="/images/1024/img_map_print.jpg" />" alt="The Medici Archive Project" /></div>
			</div>
			
			<h4>Documentary Sources for the Arts and Humanities 1537 - 1743<br />Document Report</h4>
		
			<h5 class="first"><fmt:message key="docbase.printDocument.title.documentDetails"/></h5>
			<table>
				<tr> 
					<td width="25%"><fmt:message key="docbase.printDocument.volumeNumber"/></td>
					<td width="25%" class="value">${currentElement.document.volume.MDP}</td>
					<c:choose>
						<c:when test="${not empty document.insertNum}">
							<td width="25%"><fmt:message key="docbase.printDocument.insertNumber"/></td>
							<td width="25%" class="value">${currentElement.document.insertNum} <c:if test="${not empty currentElement.document.insertLet}">/ ${currentElement.document.insertLet}</c:if></td>
						</c:when>
						<c:otherwise>
							<td width="25%"></td>
							<td width="25%"></td>
						</c:otherwise>
					</c:choose>
				</tr>
				<tr>
					<td width="25%"><fmt:message key="docbase.printDocument.folioNumber"/></td>
					<td width="25%" class="value">${currentElement.document.folioNum}<c:if test="${not empty currentElement.document.folioMod}"> / ${currentElement.document.folioMod}</c:if><c:if test="${not empty currentElement.document.folioRectoVerso}"> / ${currentElement.document.folioRectoVerso}</c:if></td>
					<td width="25%"><fmt:message key="docbase.printDocument.transcribeFolioNumber"/></td>
					<td width="25%" class="value">${currentElement.document.transcribeFolioNum}<c:if test="${not empty currentElement.document.transcribeFolioMod}"> / ${currentElement.document.transcribeFolioMod}</c:if><c:if test="${not empty currentElement.document.transcribeFolioRectoVerso}"> / ${currentElement.document.transcribeFolioRectoVerso}</c:if></td>
				</tr>
				<tr>
					<td width="25%"><fmt:message key="docbase.printDocument.citationFormat"/></td>
					<td width="75%" colspan="3" class="value">BIA: The Medici Archive Project, Doc ID# ${currentElement.document.entryId} (Archivio di Stato di Firenze, Mediceo del Principato ${currentElement.document.volume.MDP} 
						<c:if test="${not empty currentElement.document.insertNum}">
							<span>, <fmt:message key="docbase.printDocument.insert"/> ${currentElement.document.insertNum}<c:if test="${not empty currentElement.document.insertLet}"> / ${currentElement.document.insertLet}</c:if></span>
						</c:if>
						<c:choose>
							<c:when test="${not empty currentElement.document.folioNum}">
								<span>, <fmt:message key="docbase.printDocument.folio"/> ${currentElement.document.folioNum}<c:if test="${not empty currentElement.document.folioMod}">/ ${currentElement.document.folioMod}</c:if></span>
							</c:when>
							<c:otherwise>
								<span>, <fmt:message key="docbase.printDocument.notnumberedfolio"/></span>
							</c:otherwise> 
						</c:choose>
						<c:choose>
							<c:when test="${not empty currentElement.document.transcribeFolioNum}">
								<span>, <fmt:message key="docbase.printDocument.transcribefolio"/> ${currentElement.document.transcribeFolioNum}<c:if test="${not empty currentElement.document.transcribeFolioMod}">/ ${currentElement.document.transcribeFolioMod}</c:if>)</span>
							</c:when>
							<c:otherwise>
								<span>, <fmt:message key="docbase.printDocument.notnumberedtranscribefolio"/>)</span>
							</c:otherwise> 
						</c:choose>
				</tr>
			</table> 
		 
			<img src="<c:url value="/images/1024/img_hr_print.png"/>" style="margin:10px 0 10px 85px"/>
		
			<h5><fmt:message key="docbase.printDocument.title.correspondentsPeople"/></h5>
			
			<table>
				<tr> 
					<td width="15%"><fmt:message key="docbase.printDocument.sender"/></td>
					<td width="35%" class="value">${currentElement.document.senderPeople.mapNameLf}</td>
					<td width="15%"><fmt:message key="docbase.printDocument.senderFrom"/></td>
					<td width="35%" class="value">${currentElement.document.senderPlace.placeNameFull}</td>
				</tr>
				<c:if test="${not empty currentElement.document.sendNotes}">
					<tr>
						<td width="15%"><fmt:message key="docbase.printDocument.senderNotes"/></td>
						<td width="85%" colspan="3" class="value">${currentElement.document.sendNotes}</td>
					</tr>
				</c:if>
			
				<tr> 
					<td width="15%"><fmt:message key="docbase.printDocument.recipient"/></td>
					<td width="35%" class="value">${currentElement.document.recipientPeople.mapNameLf}</td>
					<td width="15%"><fmt:message key="docbase.printDocument.recipientTo"/></td>
					<td width="35%" class="value">${currentElement.document.recipientPlace.placeNameFull}</td>
				</tr>
				<c:if test="${not empty currentElement.document.recipNotes}">
					<tr>
						<td width="15%"><fmt:message key="docbase.printDocument.recipientNotes"/></td>
						<td width="85%" colspan="3" class="value">${currentElement.document.recipNotes}</td>
					</tr>
				</c:if>
				
				<c:if test="${not empty currentElement.document.epLink}">
					<c:set var="anotherPerson" value="false" />
					<td width="15%"><fmt:message key="docbase.printDocument.peopleReferredTo"/></td>
					<td width="85%" colspan="3" class="value">
						<c:forEach items="${currentElement.document.epLink}" var="currentPeople">
							<c:choose>
								<c:when test="${another && currentPeople.docRole!= 'S' && currentPeople.docRole != 'R'}">
									<span> - ${currentPeople.person.mapNameLf}</span>
								</c:when>
								<c:when test="${not another && currentPeople.docRole!= 'S' && currentPeople.docRole != 'R'}">
									<span>${currentPeople.person.mapNameLf}</span>
									<c:set var="anotherPerson" value="true" />
								</c:when>
								<c:otherwise>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</td>
				</c:if>
				
				<tr> 
					<td width="15%"><fmt:message key="docbase.printDocument.date"/></td>
					<td width="35%" class="value">${currentElement.document.docYear} ${currentElement.document.docMonthNum} ${currentElement.document.docDay}</td>
				</tr>
				<c:if test="${not empty currentElement.document.yearModern}">
					<tr>
						<td width="15%"><fmt:message key="docbase.printDocument.yearModern" /></td>
						<td width="35%" class="value">${currentElement.document.yearModern}</td>
					</tr>
				</c:if>
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
			
			<h5><fmt:message key="docbase.printDocument.title.transcriptionSynopsis"/></h5>
			<table>
				<tr> 
					<td width="100%"><fmt:message key="docbase.printDocument.transcription"/></td>
				</tr>
				<tr>
					<td width="100%" class="value">${currentElement.document.synExtract.docExtract}</td>
				</tr>
				<tr>
					<td width="100%"></td>
				</tr>
				<tr>
					<td width="100%"><fmt:message key="docbase.printDocument.synopsis"/></td>
				</tr>
				<tr> 
					<td width="100%" class="value">${currentElement.document.synExtract.synopsis}</td>
				</tr>
			</table>
				
			
			<img src="<c:url value="/images/1024/img_hr_print.png"/>" style="margin:10px 0 10px 85px"/>
			
			
			<h5><fmt:message key="docbase.printDocument.title.topics"/></h5>
			<table>
				<c:forEach items="${currentElement.document.eplToLink}" var="currentTopicAndPlace">
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
<!-- 			<div id="footer"> -->
<!-- 				<p> -->
<!-- 					The Medici Archive Project, Inc. &copy; -->
<!-- 					<br />  -->
<!-- 					Written content on the Medici Archive Project website and the BIA platform are sole property of the Medici Archive Project, Inc.,a 501(c)(3) Tax Exempt Organization. In any subsequent -->
<!-- 					use the Medici Archive Project must be given appropriate acknowledgment in accordance with U.S. and foreign copyright laws.  -->
<!-- 				</p> -->
<!-- 				<p> To cite content from the BIA platform in a publication, use the "citation format" near the top  of the page.</p> -->
<!-- 			</div> -->
 			<c:if test="${!status.last}">
 				<div class="newPage"></div>
 			</c:if>
		</c:if>
		
		<!-- Place Print -->
		<c:if test="${currentElement.place != null}">
			<div id="top">
				<div id="logoMap"><img src="<c:url value="/images/1024/img_map_print.jpg" />" alt="The Medici Archive Project" /></div>
			</div>
			
			<h4>Documentary Sources for the Arts and Humanities 1537 - 1743<br />Place Report</h4>
            
		    <h5 class="first">Place Details</h5>
		    <table>
		        <tr> 
		            <td width="20%">Place ID</td>
		            <td width="20%" class="value">${currentElement.place.placeAllId}</td>
		            <td width="20%">Place name</td>
		            <td width="40%" class="value">${currentElement.place.placeName}</td>
		       </tr>
		       <tr>
		            <td width="20%">(with accents)</td>
		            <td width="80%" class="value" colspan="3">${currentElement.place.placeNameFull}</td>
		        </tr>
		        <tr>
		        	<td width="20%">Place type</td>
		            <td width="80%" class="value" colspan="3">${currentElement.place.plType}</td>
		        </tr>
		        <tr>
		            <td width="20%">Place Parent</td>
		            <td width="80%" colspan="3" class="value">${currentElement.place.plParent}</td>
		        </tr>
		        <tr>
		            <td width="20%">Place Notes</td>
		            <td width="80%" colspan="5" class="value">${currentElement.place.placesMemo}</td>
		        </tr>
		    </table> 
		    
		    <img src="<c:url value="/images/1024/img_hr_print.png"/>" style="margin:10px 0 10px 85px"/>
		    
		    <h5>Name or Name Variants</h5>
		    	<table>
		   		 <c:forEach items="${placeNamesValues.get(currentElement.id)}" varStatus="iterator">
		   		 	
		   		 	<c:if test="${placeNamesValues.get(currentElement.id)[iterator.index].prefFlag == 'P'}">
						<tr>
							<td width="20%">Principal</td>
							<td width="80%" class="value">${placeNamesValues.get(currentElement.id)[iterator.index].placeName}</td>
						</tr>
					</c:if>
					<c:if test="${placeNamesValues.get(currentElement.id)[iterator.index].prefFlag == 'V'}">
						<tr>
							<td width="20%">Variant</td>
							<td width="80%" class="value">${placeNamesValues.get(currentElement.id)[iterator.index].placeName}</td>
						</tr>
					</c:if>	
											
				</c:forEach> 		 
		    </table>
		    <img src="<c:url value="/images/1024/img_hr_print.png"/>" style="margin:10px 0 10px 85px"/>
		    
		    <h5>Senders and Recipients</h5>
		    <table>
		        <tr>
		        	<c:if test="${placesValues.get(currentElement.id).get(2) != null && placesValues.get(currentElement.id).get(2) != 0 && placesValues.get(currentElement.id).get(2) != 1}">
		            	<td width="100%">${placesValues.get(currentElement.id).get(2)} Senders</td>
		            </c:if>
		            <c:if test="${placesValues.get(currentElement.id).get(2) == 1 }">
		            	<td width="100%">${placesValues.get(currentElement.id).get(2)} Sender</td>
		            </c:if>
		            <c:if test="${placesValues.get(currentElement.id).get(2) == 0 || placesValues.get(currentElement.id).get(2) == null}">
						<td width="100%">0 Senders</td>
					</c:if>
		            
		        </tr>
		        <tr>
		        	<c:if test="${placesValues.get(currentElement.id).get(3) != null && placesValues.get(currentElement.id).get(3) != 0 && placesValues.get(currentElement.id).get(3) != 1}">
						<td width="100%">${placesValues.get(currentElement.id).get(3)} Recipients</td>
					</c:if>
					<c:if test="${placesValues.get(currentElement.id).get(3) == 1}">
						<td width="100%">${placesValues.get(currentElement.id).get(3)} Recipient</td>
					</c:if>
					<c:if test="${placesValues.get(currentElement.id).get(3) == 0 || placesValues.get(currentElement.id).get(3) == null}">
						<td width="100%">0 Recipients</td>
					</c:if>
		        </tr>
		    </table>
		    
		   <img src="<c:url value="/images/1024/img_hr_print.png"/>" style="margin:10px 0 10px 85px"/>
		    
		    <h5>Topics List</h5>
		    <table>
		        <tr>
			        <c:if test="${placesValues.get(currentElement.id).get(0) != null && placesValues.get(currentElement.id).get(0) != 0 && placesValues.get(currentElement.id).get(0) != 1 && placesValues.get(currentElement.id).get(1) != 1}"> 
			        	<td width="100%">${placesValues.get(currentElement.id).get(1)} Documents on ${placesValues.get(currentElement.id).get(0)} Topics</td>
			        </c:if>
					<c:if test="${placesValues.get(currentElement.id).get(0) == 1}">
			    		<td width="100%">${placesValues.get(currentElement.id).get(1)} Document on ${placesValues.get(currentElement.id).get(0)} Topic</td>
					</c:if>
					<c:if test="${placesValues.get(currentElement.id).get(1) == 1 && placesValues.get(currentElement.id).get(0) != 1}">
			    		<td width="100%">${placesValues.get(currentElement.id).get(1)} Document on ${placesValues.get(currentElement.id).get(0)} Topics</td>
					</c:if>
					<c:if test="${placesValues.get(currentElement.id).get(0) == 0 || placesValues.get(currentElement.id).get(0) == null}">
			    		<td width="100%">0 Documents on 0 Topics</td>
					</c:if>
		        </tr>
		    </table> 
		    
		    <img src="<c:url value="/images/1024/img_hr_print.png"/>" style="margin:10px 0 10px 85px"/>
		    
		    <h5>Birth and Death Place</h5>
		    <table>
		        <tr>
		        	<td width="100%">
			        	<c:if test="${placesValues.get(currentElement.id).get(4) != 0}">${placesValues.get(currentElement.id).get(4)} Births </c:if>
			        	<c:if test="${placesValues.get(currentElement.id).get(4) == 1}">1 Birth </c:if>
			        	<c:if test="${placesValues.get(currentElement.id).get(4) == 0}">0 Births </c:if>
			        	<c:if test="${placesValues.get(currentElement.id).get(5) != 0}">${placesValues.get(currentElement.id).get(5)} Active Starts</c:if>
			        	<c:if test="${placesValues.get(currentElement.id).get(5) == 1}">1 Active Start</c:if>
			        	<c:if test="${placesValues.get(currentElement.id).get(5) == 0}">0 Active Starts</c:if>
		        	</td>
		        </tr>
		        <tr> 
		        	<td width="100%">
			        	<c:if test="${placesValues.get(currentElement.id).get(6) != 0}">${placesValues.get(currentElement.id).get(6)} Deaths </c:if>
			        	<c:if test="${placesValues.get(currentElement.id).get(6) == 1}">1 Death </c:if>
			        	<c:if test="${placesValues.get(currentElement.id).get(6) == 0}">0 Deaths </c:if>
			        	<c:if test="${placesValues.get(currentElement.id).get(7) != 0}">${placesValues.get(currentElement.id).get(7)} Active Ends</c:if>
			        	<c:if test="${placesValues.get(currentElement.id).get(7) == 1}">1 Active End</c:if>
			        	<c:if test="${placesValues.get(currentElement.id).get(7) == 0}">0 Active Ends</c:if>
		        	</td>
		        </tr>
		    </table> 
		    
		    <img src="<c:url value="/images/1024/img_hr_print.png"/>" style="margin:10px 0 10px 85px"/>
		    
		    
		    <h5>Geographic Coordinates</h5>
		    <c:if test="${currentElement.place.prefFlag == 'P'}">
		    <table>
		        <tr> 
		        	<td width="20%">Latitude</td>
		            <td width="80%">${currentElement.place.placeGeographicCoordinates.degreeLatitude}° ${currentElement.place.placeGeographicCoordinates.minuteLatitude}' ${currentElement.place.placeGeographicCoordinates.secondLatitude}'' ${currentElement.place.placeGeographicCoordinates.directionLatitude}</td>
		        </tr>
		        <tr> 
		        	<td width="20%">Longitude</td>
		            <td width="80%">${currentElement.place.placeGeographicCoordinates.degreeLatitude}° ${currentElement.place.placeGeographicCoordinates.minuteLatitude}' ${currentElement.place.placeGeographicCoordinates.secondLatitude}'' ${currentElement.place.placeGeographicCoordinates.directionLatitude}</td>
		        </tr>
		    </table> 
			</c:if>
<!-- 			Variant no geographic coordinates -->
<%-- 			<c:forEach items="${placeNames}" var="currentName"> --%>
<%-- 				<c:if test="${currentName.prefFlag == 'P'}"> --%>
<%-- 					<p style="margin:0 0 5px 10px"><font color="red">This place is a Variant Name for '${currentName.placeName}'. Find the 'Principal' name to visualize ${currentName.placeName} geographic coordinates</font></p> --%>
<%-- 				</c:if> --%>
<%-- 			</c:forEach> --%>
		
			<img src="<c:url value="/images/1024/img_hr_print.png"/>" style="margin:10px 0 10px 85px"/>
			
			<h5>External Links</h5>
			<table>
			<c:forEach items="${currentElement.place.placeExternalLinks}" var="currentExternalLink">
				<tr>
					<td width="20%">Name Link</td>
					<td width="80%">${currentExternalLink.description}</td>
				</tr>
			</c:forEach>
			</table>
			
			<img src="<c:url value="/images/1024/img_hr_print.png"/>" style="margin:10px 0 10px 85px"/>
			 
			<h5>Hierarchy</h5>
			<table>
				<tr>
			 		<td width="20%">Parent</td>
			 		<td width="80%">${currentElement.place.parentPlace.placeAllId}</td>
			 	</tr>
			 	<tr>
			 		<td width="20%">GParent</td>
			 		<td width="80%">${currentElement.place.gParent}</td>
			 	</tr>
			 	<tr>
			 		<td width="20%">GGParent</td>
			 		<td width="80%">${currentElement.place.ggp}</td>
			 	</tr>
			 	<tr>
			 		<td width="20%">GP2</td>
			 		<td width="80%">${currentElement.place.gp2}</td>
			 	</tr>
			 	<tr>
			 		<td width="20%">Parent_TGN_id</td>
			 		<td width="80%">${currentElement.place.plParentTermId}</td>
			 	</tr>
			 	<tr>
			 		<td width="20%">Parent_GEOKEY</td>
			 		<td width="80%">${currentElement.place.plParentSubjectId}</td>
			 	</tr>
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
 			<c:if test="${!status.last}">
 				<div class="newPage"></div>
 			</c:if>
		</c:if>
		
		<!-- Person print -->
		<c:if test="${currentElement.person != null}">
			<div id="top">
				<div id="logoMap"><img src="<c:url value="/images/1024/img_map_print.jpg" />" alt="The Medici Archive Project" /></div>
			</div>
			
			<h4>Documentary Sources for the Arts and Humanities 1537 - 1743<br />Person Report</h4>
	
			<h3>${currentElement.person.mapNameLf}</h3>
			<table>
				<tr>
					<td width="70">Birth</td>
					<td width="300" class="value">${currentElement.person.bornYear} ${currentElement.person.bornMonth} ${currentElement.person.bornDay}</td>
				</tr>
				<tr>
					<td width="70">Death</td>
			
					<td width="300" class="value">${currentElement.person.deathYear} ${currentElement.person.deathMonth} ${currentElement.person.deathDay}</td>
				</tr>
				<tr>
					<td width="70">Birth Place</td>
					<td width="300" class="value">${currentElement.person.bornPlace.placeNameFull}</td>
				</tr>
				<tr>
			
					<td width="70">Death Place</td>
					<td width="300" class="value">${currentElement.person.deathPlace.placeNameFull}</td>
				</tr>
			</table>
			
			<c:if test="${peopleValues.get(currentElement.id).get(0) > 0}">
				<img src="<c:url value="/images/1024/img_hr_print.png"/>" style="margin: 10px 0 10px 85px" />
	
				<h5>Documents Related</h5>
				<table>
					<tr>
						<td width="240">Documents Related to this person entry</td><td class="value">${peopleValues.get(currentElement.id).get(0)}</td>
					</tr>
					<tr>
						<td width="240">Sender</td><td class="value">${peopleValues.get(currentElement.id).get(1)}</td>
					</tr>
					<tr>
						<td width="240">Recipient</td><td class="value">${peopleValues.get(currentElement.id).get(2)}</td>
					</tr>
					<tr>
						<td width="240">Referring To</td><td class="value">${peopleValues.get(currentElement.id).get(3)}</td>
					</tr>			
				</table>
			</c:if>
			
			<img src="<c:url value="/images/1024/img_hr_print.png"/>" style="margin: 10px 0 10px 85px" />
			
			<h5>Names</h5>
			<table>
				<c:forEach items="${currentElement.person.altName}" var="currentName">
					<tr>
						<td width="70">${currentName.nameType}</td>
						<td width="300" class="value">${currentName.namePrefix} ${currentName.altName}</td>
					</tr>
				</c:forEach>	
			</table>
			
			<img src="<c:url value="/images/1024/img_hr_print.png"/>" style="margin: 10px 0 10px 85px" />
			
			<h5>Titles/Occupations</h5>
			<table>
				<c:forEach items="${currentElement.person.poLink}" var="currentPoLink">
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
				<c:forEach items="${currentElement.person.parents}" var="currentParent">
					<tr>
						<c:if test="${currentParent.parent.gender == 'M'}">
							<td width="60">Father</td>
							<td width="235" class="value">${currentParent.parent}</td>
							<td width="120" class="valueRight">Born ${currentParent.parent.bornYear} | Death ${currentParent.parent.deathYear}</td>
						</c:if>
					</tr>
				</c:forEach>
				
				<c:forEach items="${currentElement.person.parents}" var="currentParent">
					<tr>				
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
				<c:forEach items="${peopleChildrenValues.get(currentElement.id)}" varStatus="iterator">
					<tr>
						<td width="290">${peopleChildrenValues.get(currentElement.id)[iterator.index].child}</td>
						<td width="120" class="valueRight">Birth ${peopleChildrenValues.get(currentElement.id)[iterator.index].child.bornYear} | Death ${peopleChildrenValues.get(currentElement.id)[iterator.index].child.deathYear}</td>
					</tr>
				</c:forEach>
			</table>	
			
			<img src="<c:url value="/images/1024/img_hr_print.png"/>" style="margin: 10px 0 10px 85px" />
			
			<h5>Spouses</h5>
			<table>
				<c:forEach items="${peopleMarriageValues.get(currentElement.id)}" varStatus="iterator">
					<tr>
					<c:if test="${currentElement.person.personId == peopleMarriageValues.get(currentElement.id)[iterator.index].husband.personId}">
						<td width="200">${peopleMarriageValues.get(currentElement.id)[iterator.index].wife}</td>
						<td width="200" class="valueRight">Marriage ${peopleMarriageValues.get(currentElement.id)[iterator.index].startYear} - ${peopleMarriageValues.get(currentElement.id)[iterator.index].endYear} | Death ${peopleMarriageValues.get(currentElement.id)[iterator.index].wife.deathYear}</td>
					</c:if>
					
					<c:if test="${currentElement.person.personId == peopleMarriageValues.get(currentElement.id)[iterator.index].wife.personId}">
						<td width="200">${peopleMarriageValues.get(currentElement.id)[iterator.index].husband}</td>
						<td width="200" class="valueRight">Marriage ${peopleMarriageValues.get(currentElement.id)[iterator.index].startYear} - ${peopleMarriageValues.get(currentElement.id)[iterator.index].endYear} | Death ${peopleMarriageValues.get(currentElement.id)[iterator.index].husband.deathYear}</td>
					</c:if>
					</tr>
				</c:forEach>
			</table>
			
			<img src="<c:url value="/images/1024/img_hr_print.png"/>" style="margin: 10px 0 10px 85px" />
			
			
			<h5>Research Notes</h5>
			<table>
				<tr>
					<td width="400" class="value">${currentElement.person.bioNotes}</td>
				</tr>
			</table>
			
			<img src="<c:url value="/images/1024/img_hr_print.png"/>" style="margin: 10px 0 10px 85px" />
<!-- 			<div id="footer"> -->
<!-- 				<p> -->
<!-- 					The Medici Archive Project, Inc. &copy; -->
<!-- 					<br />  -->
<!-- 					Written content on the Medici Archive Project website and the BIA platform are sole property of the Medici Archive Project, Inc.,a 501(c)(3) Tax Exempt Organization. In any subsequent -->
<!-- 					use the Medici Archive Project must be given appropriate acknowledgment in accordance with U.S. and foreign copyright laws.  -->
<!-- 				</p> -->
<!-- 				<p> To cite content from the BIA platform in a publication, use the "citation format" near the top  of the page.</p> -->
<!-- 			</div> -->
 			<c:if test="${!status.last}">
 				<div class="newPage"></div>
 			</c:if>
		</c:if>
		
		<!-- Volume print -->
		<c:if test="${currentElement.volume != null}">
		
			<div id="top">
				<div id="logoMap"><img src="<c:url value="/images/1024/img_map_print.jpg" />" alt="The Medici Archive Project" /></div>
			</div>
		
			<h4>Documentary Sources for the Arts and Humanities 1537 - 1743<br />Volume Report</h4>

     
		     <h3 class="first">Minute di Lettere e Registri / Minute: Cosimo I / Segretario: Concino</h3>
		     <table>
		         <tr> 
		             <td width="100">Volume/Filza (MDP)</td>
		             <td width="285" class="value">${currentElement.volume.volNum}${currentElement.volume.volLetExt}</td>
		         </tr>
		         <tr>
		             <td width="100">Start Date</td>
		
		             <td width="285" class="value">${currentElement.volume.startYear} ${currentElement.volume.startMonthNum.monthName} ${currentElement.volume.startDay}</td>
		         </tr>
		         <tr>
		             <td width="100">End Date</td>
		             <td width="285" class="value">${currentElement.volume.endYear} ${currentElement.volume.endMonthNum.monthName} ${currentElement.volume.endDay}</td>
		         </tr>
		         <tr>
		
		             <td width="100">Date Notes</td>
		             <td width="285" class="value">${currentElement.volume.dateNotes}</td>
		         </tr>
		     </table> 
		     
		     <img src="<c:url value="/images/1024/img_hr_print.png"/>" style="margin:10px 0 10px 85px"/>
		     
		     <h5>Description</h5>
		     <table>
		         <tr> 
		             <td width="90">Organizational Criteria</td>
		             <td width="330" class="value" colspan="3">${currentElement.volume.orgNotes}</td>
		         </tr>
		         <tr>
		             <td width="70">Condition</td>
		             <td width="330" class="value" colspan="3">${currentElement.volume.ccondition}</td>
		         </tr>
		         <tr> 
		             <td width="70">Bound</td>
		             <td width="130" class="value">${currentElement.volume.bound ? 'Yes' : 'No'}</td>
		             <td width="150">Folios Numbered</td>
		             <td width="30" class="valueRight">${currentElement.volume.folsNumbrd ? 'Yes' : 'No'}</td>
		         </tr>
		         <tr> 
		             <td width="70">Folio Count</td>
		             <td width="150" class="value">${currentElement.volume.folioCount}</td>
		
		             <td width="100">Index of Names</td>
		             <td width="50" class="valueRight">${currentElement.volume.oldAlphaIndex ? 'Yes' : 'No'}</td>
		         </tr>
		         <tr> 
		             <td width="70">Printed material</td>
		             <td width="150" class="value">${currentElement.volume.printedMaterial ? 'Yes' : 'No'}</td>
		             <td width="100">Printed drawings</td>
		             <td width="50" class="valueRight">${currentElement.volume.printedDrawings ? 'Yes' : 'No'}</td>
		         </tr>
		         <tr> 
		             <td width="70">Languages</td>
		             <td width="150" class="valueLeft" colspan="3">${currentElement.volume.italian ? 'Italian' : '' } 
											${currentElement.volume.spanish ? 'Spanish' : ''}
											${currentElement.volume.english ? 'English' : ''}
											${currentElement.volume.latin ? 'Latin' : ''}
											${currentElement.volume.german ? 'German' : ''}
											${currentElement.volume.french ? 'French' : ''}
											${currentElement.volume.otherLang}</td>
		         </tr>
		         <tr>
		             <td width="130">Some Documents in Cipher</td>
		             <td width="50" class="value">${currentElement.volume.cipher ? 'Yes' : 'No'}</td>
		         </tr>
		         <tr> 
		             <td width="70">Cipher Notes</td>
		             <td width="350" class="value" colspan="3">${currentElement.volume.cipherNotes}</td>
		         </tr>
		     </table> 
		     
		     <img src="<c:url value="/images/1024/img_hr_print.png"/>" style="margin:10px 0 10px 85px"/>
		
		     
		     <h5>Correspondents</h5>
		     <table>
		         <tr> 
		           <td width="70">From</td>
		           <td class="value">${currentElement.volume.senders}</td>
		         </tr>
		         <tr> 
		           <td width="70">To</td>
		
		           <td class="value">${currentElement.volume.recips}</td>
		         </tr>
		     </table>
		     
		     <img src="<c:url value="/images/1024/img_hr_print.png"/>" style="margin:10px 0 10px 85px"/>
		     
		     <h5>Context</h5>
		     <table>
		         <tr> 
		           <td width="70">Context</td>
		
		           <td class="value">${currentElement.volume.ccontext}</td>
		         </tr>
		         <tr> 
		           <td width="70">Inventario Sommario Description</td>
		           <td class="value">${currentElement.volume.inventarioSommarioDescription}</td>
		         </tr>
		     </table> 
		     
		     <img src="<c:url value="/images/1024/img_hr_print.png"/>" style="margin:10px 0 10px 85px"/>
<!-- 		     <div id="footer"> -->
<!-- 				<p> -->
<!-- 					The Medici Archive Project, Inc. &copy; -->
<!-- 					<br />  -->
<!-- 					Written content on the Medici Archive Project website and the BIA platform are sole property of the Medici Archive Project, Inc.,a 501(c)(3) Tax Exempt Organization. In any subsequent -->
<!-- 					use the Medici Archive Project must be given appropriate acknowledgment in accordance with U.S. and foreign copyright laws.  -->
<!-- 				</p> -->
<!-- 				<p> To cite content from the BIA platform in a publication, use the "citation format" near the top  of the page.</p> -->
<!-- 			</div> -->
			<c:if test="${!status.last}">
 				<div class="newPage"></div>
 			</c:if>
		</c:if>

	</c:forEach>
	
	 <div id="footer">
		<p>
			The Medici Archive Project, Inc. &copy;
			<br /> 
			Written content on the Medici Archive Project website and the BIA platform are sole property of the Medici Archive Project, Inc.,a 501(c)(3) Tax Exempt Organization. In any subsequent
			use the Medici Archive Project must be given appropriate acknowledgment in accordance with U.S. and foreign copyright laws. 
		</p>
		<p> To cite content from the BIA platform in a publication, use the "citation format" near the top  of the page.</p>
	</div>
