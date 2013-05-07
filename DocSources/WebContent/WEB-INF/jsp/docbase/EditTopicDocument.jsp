<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="LoadingImageURL" value="/images/loading_autocomplete.gif"/>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="EditTopicsDocumentURL" value="/de/docbase/EditTopicsDocument.do">
			<c:param name="entryId" value="${command.entryId}" />
		</c:url>
	</security:authorize>
	
	<c:url var="ShowTopicDescription" value="/src/docbase/ShowTopicDescription.do" />
	
	<br>
	<%-- Loading div when saving the form --%>
	<div id="loadingDiv"></div>
	<form:form id="EditTopicDocumentForm" cssClass="edit">
		<fieldset>
			<c:if test="${command.eplToId == 0}">  
				<legend><b><fmt:message key="docbase.editTopicDocument.title.addNewTopic"/></b></legend>
			</c:if>
			<c:if test="${command.eplToId > 0}">
				<legend><b><fmt:message key="docbase.editTopicDocument.title.editTopic"/></b></legend>
			</c:if>
			
			<div class="listForm">
				<div class="row">
					<div class="col_l">
						<a class="helpIcon" title="<fmt:message key="docbase.editTopicDocument.help.show"></fmt:message>">?</a>
						<form:label id="topicDescriptionLabel" for="topicId" path="topicId" cssErrorClass="error"><fmt:message key="docbase.editPeopleDocument.title.addNewPerson"/></form:label>
					</div>
					<div class="col_l">
						<form:select path="topicId" id="topicId" cssClass="selectform_XXXlong" items="${topicsList}" itemValue="topicId" itemLabel="topicTitle" />
<%-- 						<form:input id="topicDescriptionAutoCompleter" path="topicDescription" cssClass="input_25c" /> --%>
						<!--<a class="topicDescription" id="refreshDescription" title="TOPIC DESCRIPTION" href="${ShowTopicDescription}"></a>-->
					</div>
					<div class="col_l">
						<a class="topicDescription" title="TOPIC DESCRIPTION" href="${ShowTopicDescription}"></a>
					</div>
				</div>
				<div class="row">
					<div class="col_l"><form:label id="placeDescriptionLabel" for="placeDescription" path="placeDescription" cssErrorClass="error">Topic Place</form:label></div>
					<div class="col_l"><form:input id="placeDescriptionAutoCompleter" path="placeDescription" cssClass="input_39c" /></div>
				</div>
			</div> 
			<form:errors path="topicId" cssClass="inputerrors" htmlEscape="false"/>
			
			<div>
				<input id="closeTopic" class="button_small fl" type="submit" value="Close" title="do not save changes" />
				<input id="save" class="button_small fr" type="submit" value="Save" />
			</div>		
			<input type="hidden" value="" id="modify"/>
		</fieldset>	

		<form:hidden path="eplToId"/>
		<form:hidden path="placeId"/>
		<form:hidden path="placePrefered"/>
		<form:hidden path="entryId"/>
	</form:form>

<%-- 	<c:url var="searchTopicLinkableToDocumentURL" value="/de/docbase/SearchTopicLinkableToDocument.json"> --%>
<%-- 		<c:param name="entryId" value="${command.entryId}" /> --%>
<%-- 	</c:url> --%>

	<c:url var="searchPlaceLinkableToTopicDocumentURL" value="/de/docbase/SearchPlaceLinkableToTopicDocument.json">
		<c:param name="entryId" value="${command.entryId}" />
	</c:url>
	
	<c:url var="ShowPlaceLinkableToTopicDocumentURL" value="/de/geobase/ShowPlaceLinkableToTopicDocument.json" />

	<script type="text/javascript">
		$j(document).ready(function() {
			$j.scrollTo("#EditTopicDocumentForm");
			
			$j("#EditTopicDocumentForm :input").change(function(){
				$j("#modify").val(1); <%-- //set the hidden field if an element is modified --%>
				return false;
			});

			var placeDescription = $j('#placeDescriptionAutoCompleter').autocompletePlace({ 
			    serviceUrl:'${searchPlaceLinkableToTopicDocumentURL}',
			    loadingImageUrl:'${LoadingImageURL}',
			    minChars:3, 
			    delimiter: /(,|;)\s*/, // regex or character
			    maxHeight:400,
			    width:600,
			    zIndex: 9999,
			    deferRequestBy: 0, //miliseconds
			    noCache: true, //default is false, set to true to disable caching
			    onSelect: function(value, data){ 
			    	$j('#placeId').val(data);
			    	$j.get("${ShowPlaceLinkableToTopicDocumentURL}", { placeAllId: "" + data }, function(data) {
			    		$j('#placePrefered').val(data.prefFlag);
			    	});
			    }
			  });
			

			$j('#closeTopic').click(function() { 
				placeDescription.killSuggestions();
				if($j("#modify").val() == 1){
	            	$j('#EditTopicDocumentDiv').block({ message: $j('#question'),
	            		css: { 
							border: 'none', 
							padding: '5px',
							boxShadow: '1px 1px 10px #666',
							'-webkit-box-shadow': '1px 1px 10px #666'
							} ,
							overlayCSS: { backgroundColor: '#999' }	
	            	}); 
	            	return false;
				}else{
					$j.ajax({ url: '${EditTopicsDocumentURL}', cache: false, success:function(html) { 
						$j("#EditTopicsDocumentDiv").html(html);
					}});
						
					return false;
				}
			});

			$j('.helpIcon').tooltip({
				track: true,
				fade: 350 
			});

			$j(".topicDescription").click(function() {
				if($j("#topicId").val() != ''){
					$j(this).attr('href', '${ShowTopicDescription}?topicId=' + $j("#topicId").val());
					Modalbox.show(this.href, {title: this.title, width: 750});
				}
				return false;
			});
			
			$j("#placeDescriptionAutoCompleter").change(function(){
				if($j("#placeNotExist").length > 0){
					$j("#placeNotExist").remove();
				}
			});

			$j("#EditTopicDocumentForm").submit(function (){
				if($j("#placeDescriptionAutoCompleter").val() == '' || $j("#placeId").val() == ''){
					if($j("#placeNotExist").length <= 0){
						$j("#closeTopic").before("<span class=\"inputerrorsPlaceNotExist\" id=\"placeNotExist\" style=\"color:red\">Place is not present, you cannot create this topic. Save is disabled.<br></span>");
					}
					return false;
				}
				if($j("#placePrefered").val() == 'V'){
					$j('#EditTopicDocumentDiv').block({ message: $j('.notPrincipal'),
						css: { 
							border: 'none', 
							padding: '5px',
							boxShadow: '1px 1px 10px #666',
							'-webkit-box-shadow': '1px 1px 10px #666'
							} ,
							overlayCSS: { backgroundColor: '#999' }	
					});
					return false;
				}else{
					$j("#loadingDiv").css('height', $j("#loadingDiv").parent().height());
		        	$j("#loadingDiv").css('visibility', 'visible');
					$j.ajax({ type:"POST", url:$j(this).attr("action"), data:$j(this).serialize(), async:false, success:function(html) {
						if ($j(html).find(".inputerrors").length > 0){
							$j("#EditTopicDocumentDiv").html(html);
						}else{
							$j("#EditTopicsDocumentDiv").load('${EditTopicsDocumentURL}');
						}
					}})
					return false;
				}
				
			});
		});
	</script>
	
	<div id="question" style="display:none; cursor: default"> 
		<h1>Discard changes?</h1> 
		<input type="button" id="yes" value="Yes" /> 
		<input type="button" id="no" value="No" /> 
	</div>
	
	<div id="questionPlace" class="notPrincipal" style="display:none; cursor: default">
		<h1>This name place is classified as a Variant Name and will be adjusted to its Preferred Name</h1>
		<input type="button" id="ok" value="Ok" />
	</div>
	
	<script type="text/javascript">
	$j(document).ready(function() {
		$j('#no').click(function() { 
			$j.unblockUI();
			$j(".blockUI").fadeOut("slow");
			$j("#question").hide();
			$j("#EditTopicDocumentDiv").append($j("#question"));
			$j(".blockUI").remove();
			$j('.autocomplete').attr('visibility', 'visible');
			return false; 
		}); 
        
		$j('#yes').click(function() { 
			$j.ajax({ url: '${EditTopicsDocumentURL}', cache: false, success:function(html) { 
				$j("#EditTopicsDocumentDiv").html(html);
			}});
				
			return false; 
		}); 
		
		$j("#ok").click(function(){
			$j.unblockUI();
			$j(".blockUI").fadeOut("slow");
			$j(".notPrincipal").hide();
			$j("#EditTopicDocumentDiv").append($j(".notPrincipal"));
			$j(".blockUI").remove();
			$j.ajax({ type:"POST", url:$j("#EditTopicDocumentForm").closest('form').attr("action"), data:$j("#EditTopicDocumentForm").closest('form').serialize(), async:false, success:function(html) { 
				$j("#EditTopicsDocumentDiv").load('${EditTopicsDocumentURL}');
			}});
			return false;
		});
     
	});
</script>
