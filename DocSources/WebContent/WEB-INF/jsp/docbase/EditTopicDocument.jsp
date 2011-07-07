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
	<form:form id="EditTopicDocumentForm" cssClass="edit">
		<fieldset>
			<c:if test="${command.eplToId == 0}">  
				<b>ADD NEW TOPIC</b>
			</c:if>
			<c:if test="${command.eplToId > 0}">
				<b>EDIT TOPIC</b>
			</c:if> 
			<div>
				<form:label id="topicDescriptionLabel" for="topicDescription" path="topicDescription" cssErrorClass="error">Topic:</form:label>
				<form:input id="topicDescriptionAutoCompleter" path="topicDescription" class="input_25c" />
				<a class="topicDescription" id="refreshDescription" title="TOPIC DESCRIPTION" href="${ShowTopicDescription}"></a>
			</div>
			<div>
				<form:label id="placeDescriptionLabel" for="placeDescription" path="placeDescription" cssErrorClass="error">Topic Place:</form:label>
				<form:input id="placeDescriptionAutoCompleter" path="placeDescription" class="input_25c" />
			</div>
			
			<div>
				<input id="closeTopic" type="submit" value="Close" title="do not save changes" class="button" />
				<input id="save" type="submit" value="Save" class="button"/>
			</div>		
		</fieldset>	

		<form:hidden path="eplToId"/>
		<form:hidden path="topicId"/>
		<form:hidden path="placeId"/>
		<form:hidden path="entryId"/>
	</form:form>

	<c:url var="searchTopicLinkableToDocumentURL" value="/de/docbase/SearchTopicLinkableToDocument.json">
		<c:param name="entryId" value="${command.entryId}" />
	</c:url>

	<c:url var="searchPlaceLinkableToTopicDocumentURL" value="/de/docbase/SearchPlaceLinkableToTopicDocument.json">
		<c:param name="entryId" value="${command.entryId}" />
	</c:url>

	<script type="text/javascript">
		$j(document).ready(function() {
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
			    onSelect: function(value, data){ $j('#placeId').val(data); }
			  });

			$j('#closeTopic').click(function() { 
	            $j('#EditTopicDocumentDiv').block({ message: $j('#question') }); 
	            return false;
			});

			$j(".topicDescription").click(function() {
				Modalbox.show(this.href, {title: this.title, width: 750});
				return false;
			});

			$j("#EditTopicDocumentForm").submit(function (){
				$j.ajax({ type:"POST", url:$j(this).attr("action"), data:$j(this).serialize(), async:false, success:function(html) {
					$j("#EditTopicsDocumentDiv").load('${EditTopicsDocumentURL}');
				}})
				return false;
			});
		});
	</script>
	
	<div id="question" style="display:none; cursor: default"> 
		<h1>discard changes?</h1> 
		<input type="button" id="yes" value="Yes" /> 
		<input type="button" id="no" value="No" /> 
	</div>
	
	<script type="text/javascript">
	$j(document).ready(function() {
		$j('#no').click(function() { 
			$j.unblockUI();
			$j(".blockUI").fadeOut("slow");
			$j("#question").hide();
			$j("#EditTopicDocumentDiv").append($j("#question"));
			$j(".blockUI").remove();
			return false; 
		}); 
        
		$j('#yes').click(function() { 
			$j.ajax({ url: '${EditTopicsDocumentURL}', cache: false, success:function(html) { 
				$j("#EditTopicsDocumentDiv").html(html);
			}});
				
			return false; 
		}); 
     
	});
</script>
