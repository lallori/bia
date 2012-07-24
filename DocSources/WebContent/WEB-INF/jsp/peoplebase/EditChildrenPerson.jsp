<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="EditChildrenPersonURL" value="/de/peoplebase/EditChildrenPerson.do">
			<c:param name="personId"   value="${command.personId}" />
		</c:url>
		<c:url var="AddChildPersonURL" value="/de/peoplebase/EditChildPerson.do">
			<c:param name="id"  value="0" />
			<c:param name="parentId"  value="${command.personId}" />
			<c:param name="childId"  value="0" />
		</c:url>
		<c:url var="ShowPersonURL" value="/src/peoplebase/ShowPerson.do">
			<c:param name="personId"   value="${command.personId}" />
		</c:url>
	</security:authorize>

	<form:form id="EditChildrenPersonForm" method="post" cssClass="edit">
		<fieldset>
		<legend><b>CHILDREN</b></legend>
		<c:forEach items="${children}" var="currentChild">
			<c:url var="EditChildPersonURL" value="/de/peoplebase/EditChildPerson.do">
				<c:param name="id" value="${currentChild.id}" />
				<c:param name="parentId" value="${command.personId}" />
				<c:param name="childId" value="${currentChild.child.personId}" />
			</c:url>

			<c:url var="DeleteChildPersonURL" value="/de/peoplebase/DeleteChildPerson.do" >
				<c:param name="id" value="${currentChild.id}" />
				<c:param name="parentId" value="${command.personId}" />
				<c:param name="childId" value="${currentChild.child.personId}" />
			</c:url>
			
			<c:url var="ComparePersonURL" value="/src/peoplebase/ComparePerson.do">
					<c:param name="personId"   value="${currentChild.child.personId}" />
				</c:url>
			
			<div class="listForm">
				<div class="row">
					<div class="col_l"><input id="child_${currentChild.id}" name="child_${currentChild.id}" class="input_40c_disabled" type="text" value="${currentChild.child.mapNameLf}" disabled="disabled" /></div>
					<div class="col_r"><a class="deleteIcon" title="Delete this entry" href="${DeleteChildPersonURL}"></a></div>
					<div class="col_r"><a class="editValue" class="editValue" href="${EditChildPersonURL}" title="Edit this entry"></a></div>
					<div class="col_r"><a href="${ComparePersonURL }" class="personIcon" title="Show this person record"></a></div>
				</div>
			</div>

		</c:forEach>
			
			<div>
				<input id="close" type="submit" value="Close" title="do not save changes" class="button" />
				<input id="AddNewValue" type="submit" value="Add" title="Add new Child" />
			</div>
			
		</fieldset>	
		
		<div id="EditChildPersonDiv"></div>
	
	</form:form>

	<script type="text/javascript">
		$j(document).ready(function() {
			$j.scrollTo("#EditChildrenPersonForm");
			
			$j("#EditDetailsPerson").css('visibility', 'hidden');
			$j("#EditNamesPerson").css('visibility', 'hidden');
	        $j("#EditTitlesOrOccupationsPerson").css('visibility', 'hidden'); 
			$j("#EditParentsPerson").css('visibility', 'hidden');
			$j("#EditSpousesPerson").css('visibility', 'hidden');
	        $j("#EditResearchNotesPerson").css('visibility', 'hidden'); 
	        
	        $j('#close').click(function() {
				$j.ajax({ url: '${ShowPersonURL}', cache: false, success:function(html) { 
					$j("#body_left").html(html);
				}});

				return false;
			});

	        /*$j(".deleteIcon").click(function() {
				$j.get(this.href, function(data) {
					if(data.match(/KO/g)){
			            var resp = $j('<div></div>').append(data); // wrap response
					} else {
						$j("#EditChildrenPersonDiv").load('${EditChildrenPersonURL}');
					}
		        });
				return false;
			});*/

			$j(".deleteIcon").click(function() {
				var temp = $j(this);
				$j("#EditChildrenPersonDiv").block({ message: $j(".question"),
					css: { 
						border: 'none', 
						padding: '5px',
						boxShadow: '1px 1px 10px #666',
						'-webkit-box-shadow': '1px 1px 10px #666'
						} ,
						overlayCSS: { backgroundColor: '#999' }	
				});

				$j('.no').click(function() {
					$j.unblockUI();
					$j(".blockUI").fadeOut("slow");
					$j(".question").hide();
					$j("#EditChildrenPersonDiv").append($j(".question"));
					$j(".blockUI").remove();
					$j("#EditChildrenPersonDiv").load('${EditChildrenPersonURL}');
					return false; 
				}); 

				$j('.yes').click(function() { 
					$j.get(temp.attr("href"), function(data) {
					if(data.match(/KO/g)){
			            var resp = $j('<div></div>').append(data); // wrap response
					} else {
						$j("#EditChildrenPersonDiv").load('${EditChildrenPersonURL}');
						return false;
					}
					return false; 
				}); 	
					     
				});
				return false;
			});

			$j(".editValue").click(function() {
				$j(".deleteIcon").css('visibility', 'hidden');
				$j("#EditChildPersonDiv").load($j(this).attr("href"));
				return false;
			});

			$j("#AddNewValue").click(function(){
				$j("#EditChildPersonDiv").load("${AddChildPersonURL}");
				return false;
			});

			$j(".personIcon").click(function(){
				var tabName = $j(this).parent();
				tabName = $j(tabName).find('.input_40c_disabled');
				tabName = $j(tabName).val();
				var numTab = 0;
				
				if(tabName.length > 20){
					tabName = tabName.substring(0,17) + "...";
				}
				
				//Check if already exist a tab with this person
				var tabExist = false;
				$j("#tabs ul li a").each(function(){
					if(!tabExist){
						if(this.text != ""){
							numTab++;
						}
					}
					if(this.text == tabName){
						tabExist = true;
					}
				});
				
				if(!tabExist){
					$j( "#tabs" ).tabs( "add" , $j(this).attr("href"), tabName + "</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
					$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
					return false;
				}else{
					$j("#tabs").tabs("select", numTab);
					return false;
				}
			});
		});
	</script>
	
	<div class="question" style="display:none; cursor: default"> 
		<h1>Delete this Child entry?</h1> 
		<input type="button" class="yes" value="Yes" /> 
		<input type="button" class="no" value="No" /> 
	</div>
	
