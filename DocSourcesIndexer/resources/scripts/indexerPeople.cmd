set JAVA_HOME="C:\Program Files\Java\jdk1.6.0_20"
set JAVA_OPTS="-Xms128m -Xmx1024"

%JAVA_HOME%\bin\java.exe -classpath ".\*;.\libs\*;" org.medici.docsources.indexer.DocSourcesIndexer people

