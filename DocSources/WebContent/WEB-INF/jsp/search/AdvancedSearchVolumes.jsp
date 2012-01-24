<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<div id="customSearchFilterDiv">
		<h1 class="advSearchTitle">Create your custom search filter</h1>
			<div id="accordion">
			<h1 id="volume"><a>Volume</a></h1>
			<div>
				<form id="volumeSearchForm" method="post" class="edit">
					<a class="helpIcon" title="This is the shelf number or call number assigned by the Archivio di Stato di Firenze to each volume of documents in the Medici Granducal Archive (Archivio Mediceo del Principato). This is the number that is used when ordering that volume for consultation in the Archivio and when citing it in publications.">?</a>
					<select id="volumeType" name="volumeType" class="selectform_long">
						<option value="Exactly" selected="selected">Exactly</option>
						<option value="Between">Between</option>
					</select>
					<input type="text" id="volume"  value="" class="input_5c" maxlength="5"/><!-- AUTOCOMPLETE -->
					<input id="betweenSearch" name="betweenSearch" class="input_5c" type="text" value="" maxlength="5" style="visibility:hidden"/>
					<input type="submit" id="addSearchFilter" value="Add" title="Add this to your search filter">
					<input type="hidden" id="category" value="Volume">
				</form>
				<hr />
				<form id="dateSearchForm" method="post" class="edit">
					<a class="helpIcon" title="When searching dates, you should enter the year according to modern (i.e. Roman) reckoning (with the new year beginning on 1 January), even when seeking documents dated according to Florentine reckoning (with the new year beginning on 25 March).">?</a>
					<select id="dateType" name="dateType" class="selectform_Llong">
						<option value="After">Written after</option>
						<option value="Before">Written before</option>
						<option value="Between">Written between</option>
					</select>
					<input type="text" id="dateYear" class="input_4c" maxlength="4" value="yyyy"/>
					<select id="dateMonth" name="dateMonth" class="selectform">
						<c:forEach items="${months}" var="month">
							<option value="${month.monthNum}" selected="selected">${month.monthName}</option>
						</c:forEach>
					</select>
					<input type="text" id="dateDay" name="dateDay" class="input_2c" maxlength="2" value="dd"/>
					<input type="submit" id="addSearchFilter" value="Add" title="Add this to your search filter" class="addDateRange">
					<input type="hidden" id="category" value="Date">
					<p class="invisible">and</p>
	                <input id="dateYearBetween" name="dateYearBetween" class="input_4c" type="text" value="yyyy" maxlength="4" style="visibility:hidden"/>
	                <select id="dateMonthBetween" name="dateMonthBetween" class="selectform" style="visibility:hidden">
						<c:forEach items="${months}" var="month">
							<option value="${month.monthNum}" selected="selected">${month.monthName}</option>
						</c:forEach>
	                </select>
	                <input id="dateDayBetween" name="dateDayBetween" class="input_2c" type="text" value="dd" maxlength="2" style="visibility:hidden"/>
	            </form>                	
			</div>
			
			<h1 id="volumeDesc"><a>Volume Description</a></h1>
			<div>
				<form id="digitizedSearchForm" method="post" class="edit">
					<a class="helpIcon" title="This is the shelf number or call number assigned by the Archivio di Stato di Firenze to each volume of documents in the Medici Granducal Archive (Archivio Mediceo del Principato). This is the number that is used when ordering that volume for consultation in the Archivio and when citing it in publications.">?</a>
					<label for="digitized" id="digitizedLabel">Digitized</label> 
                	<select id="digitized" name="digitized" class="selectform_short">
                    	<option value="" selected="selected"></option>
                   		<option value="Yes">Yes</option>
                    	<option value="No">No</option>
                	</select>
                	<input type="submit" id="addSearchFilter" value="Add" title="Add this date to your search filter">
                	<input type="hidden" id="category" value="Digitized">
            	</form>
            	<form id="languagesSearchForm" method="post" class="edit">
	                <a class="helpIcon" title="This is the shelf number or call number assigned by the Archivio di Stato di Firenze to each volume of documents in the Medici Granducal Archive (Archivio Mediceo del Principato). This is the number that is used when ordering that volume for consultation in the Archivio and when citing it in publications.">?</a>
	                <label for="languages" id="languagesLabel">Languages</label> 
	                <label for="italian" id="italianLabel"><i>Italian</i></label>
	                <input type="checkbox" name="italian" value="italian"\/>
	                <label for="french" id="frenchLabel"><i>French</i></label>
	                <input type="checkbox" name="french" value="french"\/>
	                <label for="german" id="germanLabel"><i>German</i></label>
	                <input type="checkbox" name="german" value="german"\/>
	                <label for="spanish" id="spanishLabel"><i>Spanish</i></label>
	                <input type="checkbox" name="spanish" value="spanish"\/>
	                <label for="latin" id="latinLabel"><i>Latin</i></label>
	                <input type="checkbox" name="latin" value="latin"\/>
	                <label for="english" id="englishLabel"><i>English</i></label>
	                <input type="checkbox" name="english" value="english"\/>
	                <input type="submit" id="addSearchFilter" value="Add" title="Add this date to your search filter">
	                <input type="hidden" id="category" value="Languages">
            	</form>
            	<form id="cipherSearchForm" method="post" class="edit">
	                <a class="helpIcon" title="This is the shelf number or call number assigned by the Archivio di Stato di Firenze to each volume of documents in the Medici Granducal Archive (Archivio Mediceo del Principato). This is the number that is used when ordering that volume for consultation in the Archivio and when citing it in publications.">?</a>
	                <label for="cipher" id="cipherLabel">Cipher</label> 
	                <select id="cipher" name="cipher" class="selectform_short">
	                    <option value="" selected="selected"></option>
	                    <option value="Yes">Yes</option>
	                    <option value="No">No</option>
	                </select>
	                <input type="submit" id="addSearchFilter" value="Add" title="Add this date to your search filter">
	                <input type="hidden" id="category" value="Cipher">
            	</form>
            	<form id="indexSearchForm" method="post" class="edit">
	                <a class="helpIcon" title="This is the shelf number or call number assigned by the Archivio di Stato di Firenze to each volume of documents in the Medici Granducal Archive (Archivio Mediceo del Principato). This is the number that is used when ordering that volume for consultation in the Archivio and when citing it in publications.">?</a>
	                <label for="index" id="indexLabel">Index of names</label> 
	                <select id="index" name="index" class="selectform_short">
	                    <option value="" selected="selected"></option>
	                    <option value="Yes">Yes</option>
	                    <option value="No">No</option>
	                </select>
	                <input type="submit" id="addSearchFilter" value="Add" title="Add this date to your search filter">
	                <input type="hidden" id="category" value="Index">
	            </form>
			</div>
			
			<h1 id="coorCont"><a>Coorespondents and Context</a></h1>
			<div>
				<form id="fromVolumeSearchForm" method="post" class="edit">
	                <a class="helpIcon" title="This is the shelf number or call number assigned by the Archivio di Stato di Firenze to each volume of documents in the Medici Granducal Archive (Archivio Mediceo del Principato). This is the number that is used when ordering that volume for consultation in the Archivio and when citing it in publications.">?</a>
	                <label for="fromVolume" id="fromLabel">From</label> 
	                <textarea id="fromVolume" name="fromVolume" class="txtarea_search"></textarea><!-- no autocompleter but word search -->
	                <input type="submit" id="addSearchFilter" value="Add" title="Add this date to your search filter">
	                <input type="hidden" id="category" value="From Volume">
	            </form>
	            <form id="toVolumeSearchForm" method="post" class="edit">
	                <a class="helpIcon" title="This is the shelf number or call number assigned by the Archivio di Stato di Firenze to each volume of documents in the Medici Granducal Archive (Archivio Mediceo del Principato). This is the number that is used when ordering that volume for consultation in the Archivio and when citing it in publications.">?</a>
	                <label for="toVolume" id="toLabel">To</label> 
	                <textarea id="toVolume" name="toVolume" class="txtarea_search"></textarea><!-- no autocompleter but word search -->
	                <input type="submit" id="addSearchFilter" value="Add" title="Add this date to your search filter">
	                <input type="hidden" id="category" value="To Volume">
	            </form>
	            <form id="contextSearchForm" method="post" class="edit">
	                <a class="helpIcon" title="This is the shelf number or call number assigned by the Archivio di Stato di Firenze to each volume of documents in the Medici Granducal Archive (Archivio Mediceo del Principato). This is the number that is used when ordering that volume for consultation in the Archivio and when citing it in publications.">?</a>
	                <label for="context" id="contextLabel">Context</label> 
	                <textarea id="context" name="context" class="txtarea_search"></textarea><!-- no autocompleter but word search -->
	                <input type="submit" id="addSearchFilter" value="Add" title="Add this date to your search filter">
	                <input type="hidden" id="category" value="Context">
	            </form>
	            <form id="inventarioSearchForm" method="post" class="edit">
	                <a class="helpIcon" title="This is the shelf number or call number assigned by the Archivio di Stato di Firenze to each volume of documents in the Medici Granducal Archive (Archivio Mediceo del Principato). This is the number that is used when ordering that volume for consultation in the Archivio and when citing it in publications.">?</a>
	                <label for="inventario" id="inventarioLabel">Inventario Sommario Description (Italian)</label> 
	                <textarea id="inventario" name="inventario" class="txtarea_search"></textarea><!-- no autocompleter but word search -->
	                <input type="submit" id="addSearchFilter" value="Add" title="Add this date to your search filter">
	                <input type="hidden" id="category" value="Inventario">
	            </form>
			</div>
			
			
				
	</div>
</div>

<script type="text/javascript">
	$j(document).ready(function() {
		$j("#dateMonth option:eq(0)").text("mm");
		$j("#dateMonth option:eq(0)").attr('selected', 'selected');
		$j("#dateMonthBetween option:eq(0)").text("mm");
		$j("#dateMonthBetween option:eq(0)").attr('selected', 'selected');

		$j("#volumeType").change(function(){
			if(this.options[1].selected) 
				$j('#betweenSearch').css('visibility','visible'); 
		    else 
				$j('#betweenSearch').css('visibility','hidden');
		});	

		$j("#dateType").change(function(){
			if(this.options[2].selected) { 
				$j("#dateYearBetween").css('visibility','visible');
				$j("#dateMonthBetween").css('visibility','visible');
				$j("#dateDayBetween").css('visibility','visible');
				$j('.invisible').css('visibility','visible');
				$j('.visible').css('visibility','hidden');
				$j('.addDateRange').css('margin-top','53px');
		   } else { 
				$j('#dateYearBetween').css('visibility','hidden');
				$j('#dateMonthBetween').css('visibility','hidden');
				$j('#dateDayBetween').css('visibility','hidden');
				$j('.invisible').css('visibility','hidden');
				$j('.visible').css('visibility','visible');
				$j('.addDateRange').css('margin-top','5px');
			}
		});
		
		$j('#dateYear').focus(function() {
			if(this.value=='yyyy') {
				this.value='';
			}
		});
		$j('#dateYearBetween').focus(function() {
			if(this.value=='yyyy') {
				this.value='';
			}
		});
		$j('#dateDay').focus(function() {
			if(this.value=='dd') {
				this.value='';
			}
		});
		$j('#dateDayBetween').focus(function() {
			if(this.value=='dd') {
				this.value='';
			}
		});

		$j("#volumeSearchForm").advancedSearchForm();
		$j("#dateSearchForm").advancedSearchForm();
		$j("#digitizedSearchForm").advancedSearchForm();
		$j("#languagesSearchForm").advancedSearchForm();
		$j("#cipherSearchForm").advancedSearchForm();
		$j("#indexSearchForm").advancedSearchForm();
		$j("#fromVolumeSearchForm").advancedSearchForm();
		$j("#toVolumeSearchForm").advancedSearchForm();
		$j("#contextSearchForm").advancedSearchForm();
		$j("#inventarioSearchForm").advancedSearchForm();
		$j('#accordion').accordion({
			active: false, 
			autoHeight: false
			});
		$j('#volume').click(function(){
			$j.scrollTo({top:'0px',left:'0px'}, 800 );
			$j("#yourSearchFilterDiv").animate({"top": "0px"}, "slow");
			return false;
		});
		$j('#volumeDesc').click(function(){
			$j.scrollTo({top:'113px',left:'0px'}, 800 );
			$j("#yourSearchFilterDiv").animate({"top": "70px"}, "slow");
			return false;
		});
		$j('#coorCont').click(function(){
			$j.scrollTo({top:'140px',left:'0px'}, 800 );
			$j("#yourSearchFilterDiv").animate({"top": "100px"}, "slow");
			return false;
		});
	});
</script>
