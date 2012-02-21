<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="EditTopicsDocumentURL" value="/de/docbase/EditTopicsDocument.do">
			<c:param name="entryId" value="${command.entryId}" />
		</c:url>
	</security:authorize>
	
	<c:url var="ShowTopicDescription" value="/src/docbase/ShowTopicDescription.do">
		<c:param name="topicId" value="${command.topicId}" />
	</c:url>
	
	<br>
	<%-- Loading div when saving the form --%>
	<div id="loadingDiv"></div>
	<form:form id="EditTopicDocumentForm" cssClass="edit">
		<fieldset>
			<c:if test="${command.eplToId == 0}">  
				<legend><b>ADD NEW TOPIC</b></legend>
			</c:if>
			<c:if test="${command.eplToId > 0}">
				<legend><b>EDIT TOPIC</b></legend>
			</c:if> 
			<div>
				<form:label id="topicDescriptionLabel" for="topicDescription" path="topicDescription" cssErrorClass="error">Topic</form:label>
				<form:input id="topicDescriptionAutoCompleter" path="topicDescription" cssClass="input_25c" />
				<a class="helpIcon" title="A set of 42 Topic Categories related to the arts and humanities defines the scope of this database. Each document in the system is indexed to the relevant Topic Categories and also to the geographical places relevant to those Topic Categories.">?</a>
<!--			<a class="topicDescription" id="refreshDescription" title="TOPIC DESCRIPTION" href="${ShowTopicDescription}"></a>-->
			</div>
			<div>
				<form:label id="placeDescriptionLabel" for="placeDescription" path="placeDescription" cssErrorClass="error">Topic Place</form:label>
				<form:input id="placeDescriptionAutoCompleter" path="placeDescription" cssClass="input_39c" />
			</div>
			
			<div>
				<input id="closeTopic" type="submit" value="Close" title="do not save changes" class="button" />
				<input id="save" type="submit" value="Save" class="button"/>
			</div>		
		</fieldset>	

		<form:hidden path="eplToId"/>
		<form:hidden path="topicId"/>
		<form:hidden path="placeId"/>
		<form:hidden path="placePrefered"/>
		<form:hidden path="entryId"/>
	</form:form>

	<c:url var="searchTopicLinkableToDocumentURL" value="/de/docbase/SearchTopicLinkableToDocument.json">
		<c:param name="entryId" value="${command.entryId}" />
	</c:url>

	<c:url var="searchPlaceLinkableToTopicDocumentURL" value="/de/docbase/SearchPlaceLinkableToTopicDocument.json">
		<c:param name="entryId" value="${command.entryId}" />
	</c:url>
	
	<c:url var="ShowPlaceLinkableToTopicDocumentURL" value="/de/geobase/ShowPlaceLinkableToTopicDocument.json" />

	<script type="text/javascript">
		$j(document).ready(function() {
			$j.scrollTo("#EditTopicDocumentForm");
			
			var topicDescription = $j('#topicDescriptionAutoCompleter').autocompleteGeneral({ 
			    serviceUrl:'${searchTopicLinkableToDocumentURL}',
			    minChars:1, 
			    delimiter: /(,|;)\s*/, // regex or character
			    maxHeight:400,
			    width:250,
			    zIndex: 9999,
			    deferRequestBy: 0, //miliseconds
			    noCache: true, //default is false, set to true to disable caching
			    onSelect: function(value, data){ 
					var oldTopic = $j('#topicId').val();
					$j('#topicId').val(data);
					var link = $j('#refreshDescription').attr('href');
					if(oldTopic == '')
						link += data;
					else
						link = link.replace(oldTopic, data);
					$j('#refreshDescription').attr('href', link); 
				}
			  });

			var placeDescription = $j('#placeDescriptionAutoCompleter').autocompletePlace({ 
			    serviceUrl:'${searchPlaceLinkableToTopicDocumentURL}',
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
				$j('.autocomplete').attr('visibility', 'hidden');
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
			});

			$j('.helpIcon').tooltip({
				track: true,
				fade: 350 
			});

			$j(".topicDescription").click(function() {
				Modalbox.show(this.href, {title: this.title, width: 750});
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
						$j("#EditTopicsDocumentDiv").load('${EditTopicsDocumentURL}');
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
