<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
	
	<c:url var="ShowPortraitUserURL" value="/user/ShowPortraitUser.do">
		<c:param name="account" value="${command.account}" />
		<c:param name="time" value="${time}" />
	</c:url>

	<c:url var="CropPortraitUserURL" value="/user/CropPortraitUser.do">
		<c:param name="account" value="${command.account}" />
	</c:url>
	
	<c:url var="ShowUserProfileURL" value="/user/ShowUserProfile.do"/>
	
	<form id="cropPortraitUserForm" action="${CropPortraitUserURL}" method="post" class="edit" enctype="multipart/form-data">
		<div>
<!-- 			<input id="save" type="submit" value="Save" /> -->
		<input id="crop" type="submit" value="Crop" />
		</div>
		<input type="hidden" id="x" name="x" value"0">
		<input type="hidden" id="x2" name="x2" value"0">
		<input type="hidden" id="y" name="y" value"0">
		<input type="hidden" id="y2" name="y2" value"0">
		<input type="hidden" id="w" name="w" value"0">
		<input type="hidden" id="h" name="h" value"0">
	</form>
	
	<div style="width:auto;height:${portraitHeight}px;overflow:hidden;margin-left:5px;">
		<img id="jcrop_target" src="${ShowPortraitUserURL}" />
	</div>

	<script type="text/javascript">
		$j(document).ready(function() {

			$j('#jcrop_target').Jcrop({
				onChange: fillForm,
				onSelect: fillForm,
				aspectRatio: 111 / 145
			});

			function fillForm(c) {
				$j('#x').val(c.x);
				$j('#y').val(c.y);
				$j('#x2').val(c.x2);
				$j('#y2').val(c.y2);
				$j('#w').val(c.w);
				$j('#h').val(c.h);
			};
			
				
			$j("#cropPortraitUserForm").submit(function (){
				$j.ajax({ type:"POST", url:$j(this).attr("action"), data:$j(this).serialize(), async:false, success:function(html) {
					$j("#uploadPortraitUserWindow").dialog("close");
					Modalbox.show('${ShowUserProfileURL}', {title: "USER PREFERENCES", width: 760, height: 470});
				}});
				return false;
			});
		});
	</script>