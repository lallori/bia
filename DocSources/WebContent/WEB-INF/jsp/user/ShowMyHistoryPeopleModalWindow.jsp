<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<div id="MyHistoryDiv">
	<div id="DocsHistory">
		<div class="title">
        	<h5>DOCUMENTS</h5>
        </div>
        
        <div class="historyList">
        	<c:forEach items="${historyReport['Document']}" var="currentHistory" varStatus="status">
            	${status.count}
            <div class="historyRowColor">
                <div class="historyDate"><a href="#"><fmt:formatDate pattern="MM/dd/yyyy" value="${currentHistory.dateAndTime}" /></a></div> 
                <div class="historyAction" title="Deleted">${currentHistory.action}</div>
                <div class="historyItem"><a href="#">${currentHistory.document.MDPAndFolio}</a></div>
			</div>
        	</c:forEach>
		</div>
        
        <a href="/DocSources/cm/HistoryDocs.html" id="moreDocs">More<span class="arrow"></span></a>
    </div>

    <div id="VolumesHistory">
		<div class="title">
        	<h5>VOLUME</h5>
        </div>
        
        <div class="historyList">
        	<c:forEach items="${historyReport['Document']}" var="currentHistory" varStatus="status">
            <div class="historyRowColor">
                <div class="historyDate"><a href="#"><fmt:formatDate pattern="MM/dd/yyyy" value="${currentHistory.dateAndTime}" /></a></div> 
                <div class="historyAction" title="Deleted">${currentHistory.action}</div>
                <div class="historyItem"><a href="#">${currentHistory.action}</a></div>
			</div>
        	</c:forEach>
		</div>
        
        <a href="/DocSources/cm/HistoryVol.html" id="moreVolumes">More<span class="arrow"></span></a>
    </div>
    
    <div id="PlaceHistory">
		<div class="title">
        	<h5>PLACE</h5>
        </div>
        
        <div class="historyList">
            <div class="historyRowColor">
                <div class="historyDate"><a href="#">11/30/2012</a></div>
                <div class="historyAction" title="Deleted"><a href="#">D</a></div>  
                <div class="historyItem" title="A Coruña/Galicia/España"><a href="#">A Coruña/Galicia/...</a></div><!-- Massimo 17 caratteri! Se è più lungo vengono i 3 puntini alla fine e nell tag title del div si scrive intero così che l'utente quando fa rollover riesce a leggerlo tutto --> 
			</div>
            
            <div class="historyRow">
                <div class="historyDate"><a href="#">$Data</a></div>
                <div class="historyAction" title="Modified">M</div>  
                <div class="historyItem"><a href="#">$Place Name</a></div> 
			</div>
            
			<div class="historyRowColor">
                <div class="historyDate"><a href="#">$Data</a></div>
                <div class="historyAction" title="Viewed">V</div>   
                <div class="historyItem"><a href="#">$Place Name</a></div> 
			</div>
            
            <div class="historyRow">
                <div class="historyDate"><a href="#">$Data</a></div>
                <div class="historyAction" title="Deleted">D</div>   
                <div class="historyItem"><a href="#">$Place Name</a></div> 
			</div>
            
            <div class="historyRowColor">
                <div class="historyDate"><a href="#">$Data</a></div>
                <div class="historyAction" title="Modified">M</div>   
                <div class="historyItem"><a href="#">$Place Name</a></div> 
			</div>        
		</div>
        
        <a href="/DocSources/cm/HistoryPla.html" id="morePlaces">More<span class="arrow"></span></a>
    </div>
    
    <div id="PeopleHistory">
		<div class="title">
        	<h5>PEOPLE</h5>
        </div>
        
        <div class="historyList">
            <div class="historyRowColor">
                <div class="historyDate"><a href="#">11/30/2012</a></div>
                <div class="historyAction" title="Deleted"><a href="#">D</a></div>   
                <div class="historyPersonValue" title="Latini, Cosimo di Filippo"><a href="#">Latini, Cosimo di...</a></div><!-- Massimo 17 caratteri! Se è più lungo vengono i 3 puntini alla fine e nell tag title del div si scrive intero così che l'utente quando fa rollover riesce a leggerlo tutto --> 
			</div>
            
            <div class="historyRow">
                <div class="historyDate"><a href="#">$Data</a></div>
                <div class="historyAction" title="Modified">M</div>   
                <div class="historyItem"><a href="#">$Name $Suffix</a></div> 
			</div>
            
			<div class="historyRowColor">
                <div class="historyDate"><a href="#">$Data</a></div>
                <div class="historyAction" title="Viewed">V</div>     
                <div class="historyItem"><a href="#">$Name $Suffix</a></div> 
			</div>
            
            <div class="historyRow">
                <div class="historyDate"><a href="#">$Data</a></div>
                <div class="historyAction" title="Deleted">D</div> 
                <div class="historyItem"><a href="#">$Name $Suffix</a></div> 
			</div>
            
            <div class="historyRowColor">
                <div class="historyDate"><a href="#">$Data</a></div>
                <div class="historyAction" title="Modified">M</div>  
                <div class="historyItem"><a href="#">$Name $Suffix</a></div> 
			</div>        
		</div>
        
        <a href="/DocSources/cm/HistoryPeop.html" id="morePeople">More<span class="arrow"></span></a>
    </div>
	<input id="close" type="submit" title="Close Chronology window" value="Close"/>
</div>


<script type="text/javascript">
	$j(document).ready(function() {
		$j("#moreDocs").click(
			function() {															
				Modalbox.show($j(this).attr("href"), {title: "MY DOCUMENTS HISTORY ", width: 750});return false;}
				);	
		$j("#morePeople").click(
			function() {															
				Modalbox.show($j(this).attr("href"), {title: "MY PEOPLE HISTORY", width: 750});return false;}
				);	
									   
		$j("#moreVolumes").click(
			function() {															
				Modalbox.show($j(this).attr("href"), {title: "MY VOLUMES HISTORY", width: 750});return false;}
				);							   	
		$j("#morePlaces").click(
			function() {															
				Modalbox.show($j(this).attr("href"), {title: "MY PLACE HISTORY", width: 750});return false;}
				);
		$j("#close").click(
			function(){
				Modalbox.hide(); return false;}
				);
	});
</script>