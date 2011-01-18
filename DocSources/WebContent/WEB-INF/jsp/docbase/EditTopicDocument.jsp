<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="EditTopicsDocument" value="/de/docbase/EditTopicsDocument.do">
			<c:param name="entryId" value="${command.entryId}" />
		</c:url>
	</security:authorize>

	<form:form id="EditTopicDocumentForm" cssClass="edit">
		<fieldset>
			<legend><b>ADD NEW TOPIC</b></legend>
			<div>
				<form:label id="topicDescriptionLabel" for="topicDescription" path="topicDescription" cssErrorClass="error">Topic:</form:label>
				<form:input id="topicDescriptionAutoCompleter" path="topicDescription" class="input_25c" />
			</div>
			<div>
				<form:label id="placeDescriptionLabel" for="placeDescription" path="placeDescription" cssErrorClass="error">Topic Place:</form:label>
				<form:input id="placeDescriptionAutoCompleter" path="placeDescription" class="input_25c" />
			</div>
			
			<div>
				<input id="close" type="submit" value="Close" title="do not save changes" class="button" />
				<input id="save" type="submit" value="Save" class="button"/>
			</div>		
		</fieldset>	

		<form:hidden path="eplToId"/>
		<form:hidden path="topicId"/>
		<form:hidden path="placeId"/>
		<form:hidden path="entryId"/>
	</form:form>

	<c:url var="searchTopicLinkableToDocumentUrl" value="/de/docbase/SearchTopicLinkableToDocument.json">
		<c:param name="entryId" value="${command.entryId}" />
	</c:url>

	<c:url var="searchPlaceLinkableToTopicDocumentUrl" value="/de/docbase/SearchPlaceLinkableToTopicDocument.json">
		<c:param name="entryId" value="${command.entryId}" />
	</c:url>

	<script type="text/javascript">
		$(document).ready(function() {
			var topicDescription = $('#topicDescriptionAutoCompleter').autocomplete({ 
			    serviceUrl:'${searchTopicLinkableToDocumentUrl}',
			    minChars:1, 
			    delimiter: /(,|;)\s*/, // regex or character
			    maxHeight:400,
			    width:250,
			    zIndex: 9999,
			    deferRequestBy: 0, //miliseconds
			    noCache: true, //default is false, set to true to disable caching
			    onSelect: function(value, data){ $('#topicId').val(data); }
			  });

			var placeDescription = $('#placeDescriptionAutoCompleter').autocomplete({ 
			    serviceUrl:'${searchPlaceLinkableToTopicDocumentUrl}',
			    minChars:3, 
			    delimiter: /(,|;)\s*/, // regex or character
			    maxHeight:400,
			    width:600,
			    zIndex: 9999,
			    deferRequestBy: 0, //miliseconds
			    noCache: true, //default is false, set to true to disable caching
			    onSelect: function(value, data){ $('#placeId').val(data); }
			  });

			$('#close').click(function() { 
	            $('#EditTopicDocumentDiv').block({ 
	                message: '<h1>Discard changes and close window?</h1>', 
	                css: { border: '3px solid #a00' } 
	            })
			});

			$("#EditTopicDocumentForm").submit(function (){
				$.ajax({ type:"POST", url:$(this).attr("action"), data:$(this).serialize(), async:false, success:function(html) { 
						if(html.match(/inputerrors/g)){
							$("#EditTopicsDocumentDiv").load('${EditTopicsDocument}');
						} else {
							$("#EditTopicDocumentDiv").html(html);
						}
					} 
				});
				return false;
			});
		});
	</script>
