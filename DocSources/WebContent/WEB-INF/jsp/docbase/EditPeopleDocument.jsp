<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="EditCorrespondentsOrPeopleDocumentURL" value="/de/docbase/EditCorrespondentsOrPeopleDocument.do">
			<c:param name="entryId"   value="${command.document.entryId}" />
		</c:url>
		<c:url var="AddPersonURL" value="/de/docbase/EditPersonDocument.do">
			<c:param name="entryId"   value="${command.document.entryId}" />
			<c:param name="epLinkId"  value="0" />
		</c:url>
		<c:url var="ShowDocumentURL" value="/src/docbase/ShowDocument.do">
			<c:param name="entryId"   value="${command.document.entryId}" />
		</c:url>
	</security:authorize>
	
	
	<form:form id="PeopleCorrespondentsDocumentsForm" method="post" cssClass="edit">
		<fieldset>	
			<legend><b>PEOPLE</b></legend>
			<p>Individuals and corporate bodies indicated in the document extract:</p>
			
		<c:forEach items="${command.document.epLink}" var="currentPersonLinked">
			<c:if test="${currentPersonLinked.docRole!= 'S' && currentPersonLinked.docRole != 'R'}">
			<c:url var="EditPersonDocumentURL" value="/de/docbase/EditPersonDocument.do">
				<c:param name="entryId" value="${currentPersonLinked.document.entryId}" />
				<c:param name="epLinkId" value="${currentPersonLinked.epLinkId}" />
			</c:url>

			<c:url var="DeletePersonDocumentURL" value="/de/docbase/DeletePersonDocument.do" >
				<c:param name="entryId" value="${currentPersonLinked.document.entryId}" />
				<c:param name="epLinkId" value="${currentPersonLinked.epLinkId}" />
			</c:url>
			
			<div class="listForm">
				<div class="row">
					<div class="col_l"><input id="people_${currentPersonLinked.epLinkId}" name="people" class="input_35c_disabled" type="text" value="${currentPersonLinked.person.mapNameLf}" disabled="disabled"/></div>
					<div class="col_l"><a class="deleteIcon" title="Delete this entry" href="${DeletePersonDocumentURL}"></a></div>
					<div class="col_l"><a class="editValue" href="${EditPersonDocumentURL}" title="Edit this entry"></a></div>
					<div class="col_l">
						<c:url var="ComparePersonURL" value="/src/peoplebase/ComparePerson.do">
							<c:param name="personId"   value="${currentPersonLinked.person.personId}" />
						</c:url>
						<a title="Show this person record" id="personIcon" href="${ComparePersonURL}" class="linkPeople"><input type="hidden" style="display:none;" class="tabId" value="peopleId${currentPersonLinked.person.personId}" /></a>
					</div>
				</div>
			</div>
			
			</c:if>
		</c:forEach>
			<br>			
			<div>
				<input id="closePeople" type="submit" value="Close" title="Do not save changes" class="closeForm"/>
				<input id="AddNewValue" type="submit" value="Add" title="Add new Person" />
			</div>
		</fieldset>
	</form:form>
		
	<div id="EditPersonDocumentDiv"></div>		
	<c:url var="searchSenderPeopleURL" value="/de/peoplebase/SearchSenderPeople.json"/>
	<c:url var="searchSenderPlaceURL" value="/de/geobase/SearchSenderPlace.json"/>
	<c:url var="searchRecipientPeopleURL" value="/de/peoplebase/SearchRecipientPeople.json"/>
	<c:url var="searchRecipientPlaceURL" value="/de/geobase/SearchRecipientPlace.json"/>
	
	<c:url var="ShowSenderPlaceDetailsURL" value="/de/geobase/ShowSenderPlaceDetails.json" />
	<c:url var="ShowRecipientPlaceDetailsURL" value="/de/geobase/ShowRecipientPlaceDetails.json" />


	<script type="text/javascript">
		$j(document).ready(function() {
						
			$j("#AddNewValue").click(function(){
				$j("#EditPersonDocumentDiv").load("${AddPersonURL}");
				return false;
			});

			$j(".deleteIcon").click(function() {
				var temp = $j(this);
				$j("#PeopleCorrespondentsDocumentDiv").block({ message: $j(".questionPerson")});
				
				$j('.personNo').click(function() {
					$j.unblockUI();
					$j(".blockUI").fadeOut("slow");
					$j(".questionPerson").hide();
					$j("#PeopleCorrespondentsDocumentDiv").append($j(".questionPerson"));
					$j(".blockUI").remove();
					$j("#EditCorrespondentsDocumentDiv").load('${EditCorrespondentsOrPeopleDocumentURL}');
					return false; 
				}); 

				$j('.personYes').click(function() { 
					$j.get(temp.attr("href"), function(data) {
					if(data.match(/KO/g)){
			            var resp = $j('<div></div>').append(data); // wrap response
					} else {
						$j("#EditCorrespondentsDocumentDiv").load('${EditCorrespondentsOrPeopleDocumentURL}');
						return false;
					}
					return false;
		        });
				});
				return false;
			});

			$j(".editValue").click(function() {
				$j("#EditPersonDocumentDiv").load($j(this).attr("href"));
				return false;
			});

// 			$j("#closePeople").die();
// 			$j('#closePeople').live('click', function(){
// 				if($j("#modify").val() == 1){
// 					$j('#EditCorrespondentsDocumentDiv').block({ message: $j('#question'), 
// 						css: { 
// 							border: 'none', 
// 							padding: '5px',
// 							boxShadow: '1px 1px 10px #666',
// 							'-webkit-box-shadow': '1px 1px 10px #666'
// 							} ,
// 							overlayCSS: { backgroundColor: '#999' }
// 					}); 
// 					return false;
// 				}else{
// 					$j.ajax({ url: '${ShowDocumentURL}', cache: false, success:function(html) { 
// 						$j("#body_left").html(html);
// 					}});
// 				} 
// 			});

			$j('.linkPeople').click(function() {
				var tabName = $j(this).parent().parent();
				tabName = $j(tabName).find('.input_35c_disabled').val();
				
				if(tabName.length > 20){
					tabName = tabName.substring(0,17) + "...";
				}
				
				var numTab = 0;
				var id = $j(this).find(".tabId").val();
				
				//Check if already exist a tab with this person
				var tabExist = false;
				$j("#tabs ul li a").each(function(){
					if(!tabExist){
						if(this.text != ""){
							numTab++;
						}
					}
					if(this.text == tabName || this.text == "PersonId#" + id.substring(8, id.length) + " - " + tabName || this.text.substring(this.text.indexOf(" - ") + 3, this.text.length) == tabName){
						if($j(this).find("input").val() == id){
							tabExist = true;
						}else{
							//To change name of the tab
							if(this.text.indexOf("#") == -1){
								$j(this).find("span").text("PersonId#" + $j(this).find("input").val().substring(8, $j(this).find("input").val().length) + " - " + this.text);
							}
							if(tabName.indexOf("#") == -1){
								tabName = "PersonId#" + id.substring(8, id.length) + " - " + tabName;		
							}
						}
					}
				});
				
				if(!tabExist){
					$j( "#tabs" ).tabs( "add" , $j(this).attr("href"), tabName + "</span><input type=\"hidden\" value=\"" + id + "\" /></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
					$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
					return false;
				}else{
					$j("#tabs").tabs("select", numTab);
					return false;
				}
			});
		});
	</script>

<div class="questionPerson" style="display:none; cursor: default"> 
		<h1>Delete this Person entry?</h1> 
		<input type="button" class="personYes" value="Yes" /> 
		<input type="button" class="personNo" value="No" /> 
</div>

<script type="text/javascript">
	$j(document).ready(function() {
		$j('#no').click(function() { 
			$j.unblockUI();
			$j(".blockUI").fadeOut("slow");
			$j("#question").hide();
			$j("#EditCorrespondentsDocumentDiv").append($j("#question"));
			$j(".blockUI").remove();
			return false; 
		}); 
        
		$j('#yes').click(function() { 
			$j.ajax({ url: '${ShowDocumentURL}', cache: false, success:function(html) { 
				$j("#body_left").html(html);
			}});
				
			return false; 
		});      
	});
</script>