<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<c:url var="LoadingImageURL" value="/images/loading_autocomplete.gif"/>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FELLOWS">
		<c:url var="EditTitlesOrOccupationsPersonURL" value="/de/peoplebase/EditTitlesOrOccupationsPerson.do">
			<c:param name="personId"   value="${command.personId}" />
		</c:url>
		<c:url var="ShowPersonURL" value="/src/peoplebase/ShowPerson.do">
			<c:param name="personId"   value="${command.personId}" />
		</c:url>
		<c:url var="CreateNewTitleOrOccupationPersonURL" value="/de/peoplebase/CreateNewTitleOrOccupationPerson.do" />
	</security:authorize>
	<br>
	<form:form id="EditTitleOrOccupationPersonForm" method="post" cssClass="edit">
	<!--- Loading div when saving the form -->
	<div id="loadingDiv"></div>
		<fieldset>
			<legend>
			<c:if test="${empty command.prfLinkId}"> 
				<b>ADD NEW TITLE</b>
			</c:if>
			<c:if test="${command.prfLinkId > 0}">
				<b>Edit TITLE</b>
			</c:if>
			</legend>
			
			<div class="listForm">
				<div class="row">
					<div class="col_l"><a class="helpIcon" title="<fmt:message key="peoplebase.editTitleOrOccupationPerson.help.edit"></fmt:message>">?</a><form:label id="titleOrOccupationDescriptionLabel" for="titleOrOccupationDescription" path="titleOrOccupationDescription" cssErrorClass="error">Add Title &amp; Occ:</form:label></div>
					<div class="col_r"><form:input id="titleAutocomplete" path="titleOrOccupationDescription" cssClass="input_29c"/></div>
				</div>
				<div class="row">
					<div class="col_l"></div>
					<div class="col_r">
						<a class="helpIcon" title="<fmt:message key="peoplebase.editTitleOrOccupationPerson.help.preferredrol"></fmt:message>">?</a>
						<form:label id="preferredRoleLabel" for="preferredRole" path="preferredRole" cssErrorClass="error">Preferred role</form:label>
						<form:checkbox id="preferredRole1" path="preferredRole" />
					</div>
				</div>
			</div>
			
			<hr />
			
			<div><b>Start:</b></div>
			<div class="listForm">
				<div class="row">
					<div class="col_r"><form:label id="startYearLabel" for="startYear" path="startYear" cssErrorClass="error">Year</form:label></div>
					<div class="col_l"><form:input path="startYear" cssClass="input_4c" maxlength="4" id="startYear"/></div>
					<div class="col_r"><form:label id="startMonthNumLabel" for="startMonthNum" path="startMonthNum" cssErrorClass="error">Month</form:label></div>
					<div class="col_l"><form:select id="startMonthNum" path="startMonthNum" cssClass="selectform_long" items="${months}" itemValue="monthNum" itemLabel="monthName"/></div>
					<div class="col_r"><form:label id="startDayLabel" for="startDay" path="startDay" cssErrorClass="error">Day</form:label></div>
					<div class="col_l"><form:input path="startDay" cssClass="input_2c" maxlength="2"/></div>
					<div class="col_r">
						<form:label id="startApproxLabel" for="startApprox" path="startApprox" cssErrorClass="error">Approx.</form:label>
						<form:checkbox path="startApprox" id="startApprox1"/>
					</div>
					<div class="col_r">
						<form:label id="startUnsLabel" for="startUns" path="startUns" cssErrorClass="error">Uns.</form:label>
						<form:checkbox path="startUns" id="startUns1"/>
					</div>
				</div>
			</div>
				
			<hr />
			
			<div><b>End:</b></div>
			<div class="listForm">
				<div class="row">
					<div class="col_r"><form:label id="endYearLabel" for="endYear" path="endYear" cssErrorClass="error">Year</form:label></div>
					<div class="col_l"><form:input path="endYear" cssClass="input_4c" maxlength="4" id="endYear"/></div>
					<div class="col_r"><form:label id="endMonthNumLabel" for="endMonthNum" path="endMonthNum" cssErrorClass="error">Month</form:label></div>
					<div class="col_l"><form:select id="endMonthNum" path="endMonthNum" cssClass="selectform_long" items="${months}" itemValue="monthNum" itemLabel="monthName"/></div>
					<div class="col_r"><form:label id="endDayLabel" for="endDay" path="endDay" cssErrorClass="error">Day</form:label></div>
					<div class="col_l"><form:input path="endDay" cssClass="input_2c" maxlength="2"/></div>
					<div class="col_r">
						<form:label id="endApproxLabel" for="endApprox" path="endApprox" cssErrorClass="error">Approx</form:label>
						<form:checkbox path="endApprox" id="endApprox1"/>
					</div>
					<div class="col_r">
						<form:label id="endUnsLabel" for="endtUns" path="endUns" cssErrorClass="error">Uns</form:label>
						<form:checkbox path="endUns" id="endUns1"/>
					</div>
				</div>
			</div>
			
			<form:hidden path="titleOccIdNew" />
			<form:hidden path="personId" />
			<form:hidden path="prfLinkId" />
			
			<form:errors path="startYear" cssClass="inputerrors" htmlEscape="false" />
			<form:errors path="startDay" cssClass="inputerrors" htmlEscape="false" />
			<form:errors path="endYear" cssClass="inputerrors" htmlEscape="false" />
			<form:errors path="endDay" cssClass="inputerrors" htmlEscape="false" />
			
			<div>
				<input id="closeTitle" class="button_small fl" type="submit" value="Close" title="do not save changes" />
				<input type="submit" value="Save" id="save" class="button_small fr" />
			</div>
			
			<input type="hidden" value="" id="modify" />
			
		</fieldset>	
	</form:form>
	
	<c:url var="SearchTitleOrOccupationURL" value="/de/peoplebase/SearchTitleOrOccupation.json">
		<c:param name="personId" value="${command.personId}"></c:param>
	</c:url>

	<script type="text/javascript">
		$j(document).ready(function() {
			
			$j("#EditTitleOrOccupationPersonForm :input").change(function(){
				$j("#modify").val(1); <%-- //set the hidden field if an element is modified --%>
				return false;
			});
			
				$j("#save").click(function(){
	        		$j("#loadingDiv").css('height', $j("#loadingDiv").parent().height());
	       			$j("#loadingDiv").css('visibility', 'visible');
	        	});
			
			var titleOrOccupationDescription = $j('#titleAutocomplete').AutocompleteTitle({ 
			    serviceUrl:'${SearchTitleOrOccupationURL}',
			    loadingImageUrl:'${LoadingImageURL}',
			    minChars:3, 
			    delimiter: /(,|;)\s*/, // regex or character
			    maxHeight:400,
			    width:600,
			    zIndex: 9999,
			    deferRequestBy: 0, //miliseconds
			    noCache: true, //default is false, set to true to disable caching
			    onSelect: function(value, data){ $j('#titleOccIdNew').val(data); }
			});

			$j('#closeTitle').click(function() {
				titleOrOccupationDescription.killSuggestions();
				if($j("#modify").val() == 1){
					$j("#EditTitleOrOccupationPersonDiv").block({ message: $j("#question"),
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
					$j.ajax({ url: '${EditTitlesOrOccupationsPersonURL}', cache: false, success:function(html) { 
						$j("#EditTitlesOrOccupationsPersonDiv").html(html);
					}});
						
					return false;
				}
			});

			$j("#EditTitleOrOccupationPersonForm").submit(function (){
				$j.ajax({ type:"POST", url:$j(this).attr("action"), data:$j(this).serialize(), async:false, success:function(html) {
					if($j(html).find('.inputerrors').length > 0){
						$j("#EditTitleOrOccupationPersonDiv").html(html);
					}else{
						$j("#EditTitlesOrOccupationsPersonDiv").load('${EditTitlesOrOccupationsPersonURL}');
					}
				}})
				return false;
			});
			
			var delay = (function(){
				  var timer = 0;
				  return function(callback, ms){
				    clearTimeout (timer);
				    timer = setTimeout(callback, ms);
				  };
				})();

			
			
			$j('#titleAutocomplete').keyup(function(){
				delay(function(){
					$j(".autocomplete").each(function(){
// 					if($j(this).text() == "No Title found."){
						$j(this).append("<tr><td><a id='createNew' href='#'><b>Create new Title / Occupation</b></a></td><td></td><td></td></tr>");
// 						$j("#createNew").click(function(){
// 							titleOrOccupationDescription.killSuggestions();
// 							$j("#titleAutocomplete").val("");
// 							$dialogNewTitleOccupation.dialog("open");
// 							return false;
// 						});
// 					}
				});}, 1000 );				
			});
			
			$j('#createNew').die();
			
			$j("#createNew").live('click',function(){
				titleOrOccupationDescription.killSuggestions();
				$j("#titleAutocomplete").val("");
				if($j("#DialogNewTitleOccupation").length == 0){
					var $dialogNewTitleOccupation = $j('<div id="DialogNewTitleOccupation"></div>').dialog({
						resizable: false,
						width: 550,
						height: 200, 
						modal: true,
						autoOpen : false,
						zIndex: 3999,
						open: function(event, ui) { 
		            		$j(this).load('${CreateNewTitleOrOccupationPersonURL}');
		           		},
						overlay: {
							backgroundColor: '#000',
							opacity: 0.5
						},
						title: 'CREATE NEW TITLE / OCCUPATION'
					});
					$dialogNewTitleOccupation.dialog("open");
				}else
					$j("#DialogNewTitleOccupation").dialog("open");
				return false;
			});
			
			
		});
	</script>
	
<div id="question" style="display:none; cursor: default"> 
	<h1>Discard changes?</h1> 
	<input type="button" id="yes" value="Yes" /> 
	<input type="button" id="no" value="No" /> 
</div>

<script type="text/javascript">
	$j(document).ready(function() {
		
		$j('.helpIcon').tooltip({ 
			track: true, 
			fade: 350 
		});
		
		$j('#no').click(function() { 
			$j.unblockUI();
			$j(".blockUI").fadeOut("slow");
			$j("#question").hide();
			$j("#EditTitleOrOccupationPersonDiv").append($j("#question"));
			$j(".blockUI").remove();
			return false; 
		}); 
        
		$j('#yes').click(function() { 
			$j.ajax({ url: '${EditTitlesOrOccupationsPersonURL}', cache: false, success:function(html) { 
				$j("#EditTitlesOrOccupationsPersonDiv").html(html);
			}});
				
			return false; 
		}); 
     
	});
</script>
