<%@ page import="java.lang.management.*"%>
<%@ page import="java.util.*"%>

	<div id="MemoryMxBeanDiv" class="background">
		<div class="title">
			<h5><fmt:message key="adm.showMemoryUsage.mEmoryMxbean"/></h5>
		</div>
		<h5 class="subtitle"><fmt:message key="adm.showMemoryUsage.heapMemoryUsage"/></h5>
    <div class="listMemory">
		<div class="row">
			<div class="item"><fmt:message key="adm.showMemoryUsage.init"/></div>
			<div class="value5"><%=ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getInit()%></div>
		</div>
		<div class="row">
			<div class="item"><fmt:message key="adm.showMemoryUsage.used"/></div>
			<div class="value5"><%=ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getUsed()%></div>
		</div>
        <div class="row">
			<div class="item"><fmt:message key="adm.showMemoryUsage.committed"/></div>
			<div class="value5"><%=ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getCommitted()%></div>
		</div>
        <div class="row">
			<div class="item"><fmt:message key="adm.showMemoryUsage.max"/></div>
			<div class="value5"><%=ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getMax()%></div>
		</div>
	</div>
	<br />
    <h5 class="subtitle"><fmt:message key="adm.showMemoryUsage.nonHeapMemoryUsage"/></h5>
    <div class="listMemory">
		<div class="row">
			<div class="item"><fmt:message key="adm.showMemoryUsage.init"/></div>
			<div class="value5"><%=ManagementFactory.getMemoryMXBean().getNonHeapMemoryUsage().getInit()%></div>
		</div>
        <div class="row">
			<div class="item"><fmt:message key="adm.showMemoryUsage.used"/></div>
			<div class="value5"><%=ManagementFactory.getMemoryMXBean().getNonHeapMemoryUsage().getUsed()%></div>
		</div>
        <div class="row">
			<div class="item"><fmt:message key="adm.showMemoryUsage.committed"/></div>
			<div class="value5"><%=ManagementFactory.getMemoryMXBean().getNonHeapMemoryUsage().getCommitted()%></div>
		</div>
        <div class="row">
			<div class="item"><fmt:message key="adm.showMemoryUsage.max"/></div>
			<div class="value5"><%=ManagementFactory.getMemoryMXBean().getNonHeapMemoryUsage().getMax()%></div>
		</div>
	</div>
</div>

<br />

<div id="MemoryPoolMxBeanDiv" class="background">
	<div class="title">
		<h5><fmt:message key="adm.showMemoryUsage.mEmoryMemoryUsage"/></h5>
	</div>
	<%
    	Iterator iter = ManagementFactory.getMemoryPoolMXBeans().iterator();
        while (iter.hasNext()) {
            MemoryPoolMXBean item = (MemoryPoolMXBean) iter.next(); 
	%>
	<h5 class="title"><%= item.getName() %></h5>
	<h5 class="subtitle"><fmt:message key="adm.showMemoryUsage.type"/></h5>
    <div class="listMemory">
		<div class="row">
			<div class="item"><%= item.getType() %></div>
			<div class="value5"></div>
		</div>
	</div>	
	<h5 class="subtitle"><fmt:message key="adm.showMemoryUsage.usage"/></h5>
    <div class="listMemory">
		<div class="row">
			<div class="item"><fmt:message key="adm.showMemoryUsage.init"/></div>
			<div class="value5"><%= item.getUsage().getInit() %></div>
		</div>
        <div class="row">
			<div class="item"><fmt:message key="adm.showMemoryUsage.used"/></div>
			<div class="value5"><%= item.getUsage().getUsed() %></div>
		</div>
        <div class="row">
			<div class="item"><fmt:message key="adm.showMemoryUsage.committed"/></div>
			<div class="value5"><%= item.getUsage().getCommitted() %></div>
		</div>
        <div class="row">
			<div class="item"><fmt:message key="adm.showMemoryUsage.max"/></div>
			<div class="value5"><%= item.getUsage().getMax() %></div>
		</div>
	</div>
	<br>
    <h5 class="subtitle"><fmt:message key="adm.showMemoryUsage.peakUsage"/></h5>
    <div class="listMemory">
		<div class="row">
			<div class="item"><fmt:message key="adm.showMemoryUsage.init"/></div>
			<div class="value5"><%= item.getPeakUsage().getInit() %></div>
		</div>
        <div class="row">
			<div class="item"><fmt:message key="adm.showMemoryUsage.used"/></div>
			<div class="value5"><%= item.getPeakUsage().getUsed() %></div>
		</div>
        <div class="row">
			<div class="item"><fmt:message key="adm.showMemoryUsage.committed"/></div>
			<div class="value5"><%= item.getPeakUsage().getCommitted() %></div>
		</div>
        <div class="row">
			<div class="item"><fmt:message key="adm.showMemoryUsage.max"/></div>
			<div class="value5"><%= item.getPeakUsage().getMax() %></div>
		</div>
	</div>
	<h5 class="subtitle"><fmt:message key="adm.showMemoryUsage.collectionUsage"/></h5>
    <div class="listMemory">
		<div class="row">
			<div class="item"><fmt:message key="adm.showMemoryUsage.init"/></div>
			<div class="value5">
			<% 
				String output;
				if(item.getCollectionUsage() != null)
					output = String.valueOf(item.getCollectionUsage().getInit());
				else
					output = "null";
			%>
			<%= output %>
			</div>
		</div>
        <div class="row">
			<div class="item"><fmt:message key="adm.showMemoryUsage.used"/></div>
			<div class="value5">
			<%
				if(item.getCollectionUsage() != null)
					output = String.valueOf(item.getCollectionUsage().getUsed());
				else
					output = "null"; 
			%>
			<%= output %>
			</div>
		</div>
        <div class="row">
			<div class="item"><fmt:message key="adm.showMemoryUsage.committed"/></div>
			<div class="value5">
			<% 
				if(item.getCollectionUsage() != null)
					output = String.valueOf(item.getCollectionUsage().getCommitted());
				else
					output = "null"; 
			%>
			<%= output %>
			</div>
		</div>
        <div class="row">
			<div class="item"><fmt:message key="adm.showMemoryUsage.max"/></div>
			<div class="value5">
			<% 
				if(item.getCollectionUsage() != null)
					output = String.valueOf(item.getCollectionUsage().getMax());
				else
					output = "null"; 
			%>
			<%= output %>
			</div>
		</div>
	</div>
	<% } %>
</div>

