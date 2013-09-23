INSERT INTO user (Host, User, Password, Select_priv, Insert_priv, Update_priv, Delete_priv, Create_priv, Drop_priv, Reload_priv, Shutdown_priv, Process_priv, File_priv, Grant_priv, References_priv, Index_priv, Alter_priv, Show_db_priv, Super_priv, Create_tmp_table_priv, Lock_tables_priv, Execute_priv, Repl_slave_priv, Repl_client_priv, Create_view_priv, Show_view_priv, Create_routine_priv, Alter_routine_priv, Create_user_priv, Event_priv, Trigger_priv, Create_tablespace_priv,  max_questions, max_updates, max_connections, max_user_connections, plugin, authentication_string) VALUES ('localhost', 'bia', '*F3E3983672CB45FE600467C530BC0E0C9E033F07', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 0, 0, 0, 0, '', NULL);
INSERT INTO user (Host, User, Password, Select_priv, Insert_priv, Update_priv, Delete_priv, Create_priv, Drop_priv, Reload_priv, Shutdown_priv, Process_priv, File_priv, Grant_priv, References_priv, Index_priv, Alter_priv, Show_db_priv, Super_priv, Create_tmp_table_priv, Lock_tables_priv, Execute_priv, Repl_slave_priv, Repl_client_priv, Create_view_priv, Show_view_priv, Create_routine_priv, Alter_routine_priv, Create_user_priv, Event_priv, Trigger_priv, Create_tablespace_priv,  max_questions, max_updates, max_connections, max_user_connections, plugin, authentication_string) VALUES ('%', 'bia', '*F3E3983672CB45FE600467C530BC0E0C9E033F07', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 0, 0, 0, 0, '', NULL);
INSERT INTO user (Host, User, Password, Select_priv, Insert_priv, Update_priv, Delete_priv, Create_priv, Drop_priv, Reload_priv, Shutdown_priv, Process_priv, File_priv, Grant_priv, References_priv, Index_priv, Alter_priv, Show_db_priv, Super_priv, Create_tmp_table_priv, Lock_tables_priv, Execute_priv, Repl_slave_priv, Repl_client_priv, Create_view_priv, Show_view_priv, Create_routine_priv, Alter_routine_priv, Create_user_priv, Event_priv, Trigger_priv, Create_tablespace_priv,  max_questions, max_updates, max_connections, max_user_connections, plugin, authentication_string) VALUES ('localhost', 'biademo', '*F3E3983672CB45FE600467C530BC0E0C9E033F07', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 0, 0, 0, 0, '', NULL);
INSERT INTO user (Host, User, Password, Select_priv, Insert_priv, Update_priv, Delete_priv, Create_priv, Drop_priv, Reload_priv, Shutdown_priv, Process_priv, File_priv, Grant_priv, References_priv, Index_priv, Alter_priv, Show_db_priv, Super_priv, Create_tmp_table_priv, Lock_tables_priv, Execute_priv, Repl_slave_priv, Repl_client_priv, Create_view_priv, Show_view_priv, Create_routine_priv, Alter_routine_priv, Create_user_priv, Event_priv, Trigger_priv, Create_tablespace_priv,  max_questions, max_updates, max_connections, max_user_connections, plugin, authentication_string) VALUES ('%', 'biademo', '*F3E3983672CB45FE600467C530BC0E0C9E033F07', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 0, 0, 0, 0, '', NULL);

CREATE DATABASE bia /*!40100 DEFAULT CHARACTER SET latin1 */;

-- --------------------------------------------------------
-- Host                          :192.168.1.3
-- Versione server               :5.5.28-0ubuntu0.12.10.2-log - (Ubuntu)
-- S.O. server                   :debian-linux-gnu
-- HeidiSQL Versione             :7.0.0.4273
-- Creato                        :2013-01-06 19:54:53
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Dump della struttura di tabella bia.persistent_logins
CREATE TABLE IF NOT EXISTS `persistent_logins` (
  `series` varchar(255) NOT NULL,
  `last_used` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `token` varchar(255) NOT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`series`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dump della struttura di tabella bia.tblAccessLog
CREATE TABLE IF NOT EXISTS `tblAccessLog` (
  `idAccessLog` int(11) NOT NULL AUTO_INCREMENT,
  `action` varchar(1000) NOT NULL,
  `account` varchar(50) NOT NULL,
  `dateAndTime` datetime NOT NULL,
  `executionTime` bigint(20) NOT NULL,
  `authorities` varchar(500) DEFAULT NULL,
  `httpMethod` varchar(8) NOT NULL,
  `informations` longtext,
  `ipAddress` varchar(50) NOT NULL,
  `errors` varchar(3000) DEFAULT NULL,
  PRIMARY KEY (`idAccessLog`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dump della struttura di tabella bia.tblAccessLogStatistics
CREATE TABLE IF NOT EXISTS `tblAccessLogStatistics` (
  `accessLogStatisticsId` int(11) NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `action` varchar(2000) NOT NULL,
  `averageTime` double NOT NULL,
  `bestTime` bigint(20) NOT NULL,
  `worstTime` bigint(20) NOT NULL,
  `count` int(11) NOT NULL,
  `httpMethod` varchar(8) NOT NULL,
  PRIMARY KEY (`accessLogStatisticsId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dump della struttura di tabella bia.tblActivationUser
CREATE TABLE IF NOT EXISTS `tblActivationUser` (
  `Uuid` varchar(50) NOT NULL,
  `account` varchar(50) NOT NULL,
  `activationDate` datetime DEFAULT NULL,
  `activationIPAddress` varchar(50) DEFAULT NULL,
  `active` bit(1) NOT NULL,
  `mailSended` bit(1) NOT NULL,
  `mailSendedDate` datetime DEFAULT NULL,
  `requestDate` datetime NOT NULL,
  `requestIPAddress` varchar(50) NOT NULL,
  PRIMARY KEY (`Uuid`),
  KEY `FK43878D5FF42F8715` (`account`),
  CONSTRAINT `FK_tblActivationUser_tblUser` FOREIGN KEY (`account`) REFERENCES `tblUser` (`account`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dump della struttura di tabella bia.tblAltNames
CREATE TABLE IF NOT EXISTS `tblAltNames` (
  `NameID` int(10) NOT NULL AUTO_INCREMENT,
  `PersonID` int(10) DEFAULT NULL,
  `NamePrefix` varchar(50) DEFAULT NULL,
  `AltName` varchar(255) DEFAULT NULL,
  `NameType` varchar(50) DEFAULT NULL,
  `Notes` longtext,
  PRIMARY KEY (`NameID`),
  KEY `{870FEE28-ADD1-48B7-8BDE-4E5E79` (`PersonID`),
  KEY `PersonID` (`PersonID`),
  KEY `FK45594FD5317879C` (`PersonID`),
  KEY `FK45594FD4387D034` (`PersonID`),
  CONSTRAINT `FK45594FD4387D034` FOREIGN KEY (`PersonID`) REFERENCES `tblPeople` (`PERSONID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dump della struttura di tabella bia.tblAnnotations
CREATE TABLE IF NOT EXISTS `tblAnnotations` (
  `annotationId` int(11) NOT NULL AUTO_INCREMENT,
  `category` varchar(255) NOT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `h` double NOT NULL,
  `id` varchar(255) NOT NULL,
  `lastUpdate` datetime DEFAULT NULL,
  `text` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `type` varchar(10) NOT NULL,
  `w` double NOT NULL,
  `x` double NOT NULL,
  `y` double NOT NULL,
  `imageId` int(11) DEFAULT NULL,
  `account` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`annotationId`),
  KEY `K_Account` (`account`),
  CONSTRAINT `FK_tblAnnotations_tblUser` FOREIGN KEY (`account`) REFERENCES `tblUser` (`account`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dump della struttura di tabella bia.tblApplicationProperty
CREATE TABLE IF NOT EXISTS `tblApplicationProperty` (
  `id` varchar(100) NOT NULL,
  `help` varchar(1000) NOT NULL,
  `value` varchar(1000) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dump della struttura di tabella bia.tblApprovationUser
CREATE TABLE IF NOT EXISTS `tblApprovationUser` (
  `account` varchar(50) NOT NULL,
  `approved` bit(1) NOT NULL,
  `approvedDate` datetime DEFAULT NULL,
  `mailSended` bit(1) NOT NULL,
  `mailSendedDate` datetime DEFAULT NULL,
  `messageSended` bit(1) NOT NULL,
  `messageSendedDate` datetime DEFAULT NULL,
  PRIMARY KEY (`account`),
  KEY `K_Account` (`account`),
  CONSTRAINT `FK_tblApprovationUser_tblUser` FOREIGN KEY (`account`) REFERENCES `tblUser` (`account`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dump della struttura di tabella bia.tblBiblioT
CREATE TABLE IF NOT EXISTS `tblBiblioT` (
  `BiblioID` int(10) NOT NULL AUTO_INCREMENT,
  `AuthorEditor` varchar(255) DEFAULT NULL,
  `Title` varchar(255) DEFAULT NULL,
  `Type` varchar(255) DEFAULT NULL,
  `Periodical` varchar(255) DEFAULT NULL,
  `Publisher` varchar(255) DEFAULT NULL,
  `Dates` varchar(255) DEFAULT NULL,
  `Notes` varchar(255) DEFAULT NULL,
  `ShelfNum` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`BiblioID`),
  KEY `ShelfNum` (`ShelfNum`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dump della struttura di tabella bia.tblBioRefLink
CREATE TABLE IF NOT EXISTS `tblBioRefLink` (
  `RefID` int(10) NOT NULL AUTO_INCREMENT,
  `PersonID` int(10) DEFAULT NULL,
  `BiblioID` int(10) DEFAULT NULL,
  `Notes` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`RefID`),
  KEY `{65996A0C-3065-4039-B0EA-AF1B8E` (`PersonID`),
  KEY `{859FEC56-FBD3-4ADC-8A11-1D2243` (`BiblioID`),
  KEY `BiblioID` (`BiblioID`),
  KEY `PersonID` (`PersonID`),
  KEY `FK4F58C3635317879C` (`PersonID`),
  KEY `FK4F58C363AB0F6052` (`BiblioID`),
  KEY `FK4F58C3634387D034` (`PersonID`),
  KEY `FK4F58C363C8A82ABA` (`BiblioID`),
  CONSTRAINT `FK4F58C3634387D034` FOREIGN KEY (`PersonID`) REFERENCES `tblPeople` (`PERSONID`),
  CONSTRAINT `FK4F58C363C8A82ABA` FOREIGN KEY (`BiblioID`) REFERENCES `tblBiblioT` (`BiblioID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dump della struttura di tabella bia.tblCountries
CREATE TABLE IF NOT EXISTS `tblCountries` (
  `CODE` varchar(2) NOT NULL,
  `NAME` varchar(50) NOT NULL,
  PRIMARY KEY (`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dump della struttura di tabella bia.tblDigitization
CREATE TABLE IF NOT EXISTS `tblDigitization` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `active` tinyint(4) NOT NULL DEFAULT '-1',
  `volLetExt` varchar(1) DEFAULT NULL,
  `volNum` int(11) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dump della struttura di tabella bia.tblDocuments
CREATE TABLE IF NOT EXISTS `tblDocuments` (
  `ENTRYID` int(10) NOT NULL AUTO_INCREMENT,
  `SUMMARYID` int(10) DEFAULT NULL,
  `SUBVOL` varchar(50) DEFAULT NULL,
  `ResID` varchar(50) DEFAULT NULL,
  `DateCreated` datetime DEFAULT NULL,
  `LastUpdate` datetime DEFAULT NULL,
  `DOCTOBEVETTED` tinyint(1) NOT NULL,
  `DOCTOBEVETTEDDATE` datetime DEFAULT NULL,
  `DOCVETID` varchar(50) DEFAULT NULL,
  `DOCVETBEGINS` datetime DEFAULT NULL,
  `DOCVETTED` tinyint(1) NOT NULL,
  `DOCVETTEDDATE` datetime DEFAULT NULL,
  `DOCSTATBOX` varchar(50) DEFAULT NULL,
  `NEWENTRY` tinyint(1) NOT NULL,
  `INSERTNUM` varchar(50) DEFAULT NULL,
  `INSERTLET` varchar(15) DEFAULT NULL,
  `FOLIONUM` int(10) DEFAULT NULL,
  `FOLIOMOD` varchar(15) DEFAULT NULL,
  `TRANSCRIBEFOLIONUM` int(10) DEFAULT NULL,
  `TRANSCRIBEFOLIOMOD` varchar(15) DEFAULT NULL,
  `CONTDISC` tinyint(1) NOT NULL,
  `UNPAGED` tinyint(1) NOT NULL,
  `DOCYEAR` int(10) DEFAULT NULL,
  `DOCMONTHNUM` int(10) DEFAULT NULL,
  `DOCDAY` int(10) DEFAULT NULL,
  `DOCDATE` int(11) DEFAULT NULL,
  `SORTABLEDATE` varchar(50) DEFAULT NULL,
  `YEARMODERN` int(4) DEFAULT NULL,
  `RECKONING` tinyint(1) NOT NULL,
  `UNDATED` tinyint(1) NOT NULL,
  `DATEUNS` tinyint(1) NOT NULL,
  `DATEAPPROX` varchar(50) DEFAULT NULL,
  `DATENOTES` varchar(255) DEFAULT NULL,
  `SENDID` int(10) DEFAULT NULL,
  `SENDUNS` tinyint(1) NOT NULL,
  `SENDLOCPLALL` int(10) DEFAULT NULL,
  `SENDLOCUNS` tinyint(1) NOT NULL,
  `SENDNOTES` varchar(250) DEFAULT NULL,
  `RECIPID` int(10) DEFAULT NULL,
  `RECIPUNS` tinyint(1) NOT NULL,
  `RECIPLOCPLALL` int(10) DEFAULT NULL,
  `RECIPLOCUNS` tinyint(1) NOT NULL,
  `RECIPNOTES` varchar(250) DEFAULT NULL,
  `GRAPHIC` tinyint(1) NOT NULL,
  `DOCTYPOLOGY` varchar(250) DEFAULT NULL,
  `LOGICALDELETE` tinyint(4) NOT NULL DEFAULT '0',
  `SORTABLEDATEINT` int(11) DEFAULT NULL,
  PRIMARY KEY (`ENTRYID`),
  KEY `{0A228306-4768-4EBA-B54B-E5025E` (`SUMMARYID`),
  KEY `DOCMONTHNUM` (`DOCMONTHNUM`),
  KEY `DOCVETID` (`DOCVETID`),
  KEY `FOLIONUM` (`FOLIONUM`),
  KEY `INSERTNUM` (`INSERTNUM`),
  KEY `RECIPID` (`RECIPID`),
  KEY `ResID` (`ResID`),
  KEY `SENDID` (`SENDID`),
  KEY `SUMMARYID` (`SUMMARYID`),
  KEY `FK3AB6889AE9A60F21` (`DOCMONTHNUM`),
  KEY `FK3AB6889A2269D9F7` (`SENDLOCPLALL`),
  KEY `FK3AB6889A15239818` (`SUMMARYID`),
  KEY `FK3AB6889AD4078B0F` (`SENDID`),
  KEY `FK3AB6889A20A77D06` (`RECIPLOCPLALL`),
  KEY `FK3AB6889A7557595E` (`RECIPID`),
  KEY `FK3AB6889A4C3E5389` (`DOCMONTHNUM`),
  KEY `FK3AB6889A85021E5F` (`SENDLOCPLALL`),
  KEY `FK3AB6889A593E0B0` (`SUMMARYID`),
  KEY `FK3AB6889AC477D3A7` (`SENDID`),
  KEY `FK3AB6889A833FC16E` (`RECIPLOCPLALL`),
  KEY `FK3AB6889A65C7A1F6` (`RECIPID`),
  CONSTRAINT `FK3AB6889A4C3E5389` FOREIGN KEY (`DOCMONTHNUM`) REFERENCES `tblMonths` (`MONTHNUM`),
  CONSTRAINT `FK3AB6889A593E0B0` FOREIGN KEY (`SUMMARYID`) REFERENCES `tblVolumes` (`SUMMARYID`),
  CONSTRAINT `FK3AB6889A65C7A1F6` FOREIGN KEY (`RECIPID`) REFERENCES `tblPeople` (`PERSONID`),
  CONSTRAINT `FK3AB6889A833FC16E` FOREIGN KEY (`RECIPLOCPLALL`) REFERENCES `tblPlaces` (`PLACEALLID`),
  CONSTRAINT `FK3AB6889A85021E5F` FOREIGN KEY (`SENDLOCPLALL`) REFERENCES `tblPlaces` (`PLACEALLID`),
  CONSTRAINT `FK3AB6889AC477D3A7` FOREIGN KEY (`SENDID`) REFERENCES `tblPeople` (`PERSONID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dump della struttura di tabella bia.tblEpLink
CREATE TABLE IF NOT EXISTS `tblEpLink` (
  `EPLINKID` int(10) NOT NULL AUTO_INCREMENT,
  `ENTRYID` int(10) DEFAULT NULL,
  `PERSONID` int(10) DEFAULT NULL,
  `Portrait` tinyint(1) NOT NULL,
  `DateCreated` datetime DEFAULT NULL,
  `AssignUnsure` tinyint(1) NOT NULL,
  `DocRole` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`EPLINKID`),
  KEY `{3A4DAD64-7192-4240-9E8C-558569` (`PERSONID`),
  KEY `{F3BFC991-5FE8-4A2B-8784-A70788` (`ENTRYID`),
  KEY `ENTRYID` (`ENTRYID`),
  KEY `PERSONID` (`PERSONID`),
  KEY `Portrait` (`Portrait`),
  KEY `FKBEA058835317879C` (`PERSONID`),
  KEY `FKBEA05883901858A5` (`ENTRYID`),
  KEY `FKC06348A35317879C` (`PERSONID`),
  KEY `FKC06348A3901858A5` (`ENTRYID`),
  CONSTRAINT `FKC06348A35317879C` FOREIGN KEY (`PERSONID`) REFERENCES `tblPeople` (`PERSONID`),
  CONSTRAINT `FKC06348A3901858A5` FOREIGN KEY (`ENTRYID`) REFERENCES `tblDocuments` (`ENTRYID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dump della struttura di tabella bia.tblEplToLink
CREATE TABLE IF NOT EXISTS `tblEplToLink` (
  `EPLTOID` int(10) NOT NULL AUTO_INCREMENT,
  `ENTRYID` int(10) DEFAULT NULL,
  `TOPICID` int(10) DEFAULT NULL,
  `PLACESALLID` int(10) DEFAULT NULL,
  `DateCreated` datetime DEFAULT NULL,
  PRIMARY KEY (`EPLTOID`),
  KEY `{196E4CD8-B4C8-4D31-A266-21CD2B` (`TOPICID`),
  KEY `{407A034A-7F62-4C12-8EF8-79450A` (`ENTRYID`),
  KEY `{D1EA1212-02CB-41D5-9F2B-E35B73` (`PLACESALLID`),
  KEY `ENTRYID` (`ENTRYID`),
  KEY `PLACESALLID` (`PLACESALLID`),
  KEY `TOPICID` (`TOPICID`),
  KEY `FK4005175886E0B1DA` (`TOPICID`),
  KEY `FK40051758C6D6291A` (`PLACESALLID`),
  KEY `FK40051758901858A5` (`ENTRYID`),
  KEY `FKDAB50B7886E0B1DA` (`TOPICID`),
  KEY `FKDAB50B78C6D6291A` (`PLACESALLID`),
  KEY `FKDAB50B78901858A5` (`ENTRYID`),
  CONSTRAINT `FKDAB50B7886E0B1DA` FOREIGN KEY (`TOPICID`) REFERENCES `tblTopicsList` (`TOPICID`),
  CONSTRAINT `FKDAB50B78901858A5` FOREIGN KEY (`ENTRYID`) REFERENCES `tblDocuments` (`ENTRYID`),
  CONSTRAINT `FKDAB50B78C6D6291A` FOREIGN KEY (`PLACESALLID`) REFERENCES `tblPlaces` (`PLACEALLID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dump della struttura di tabella bia.tblFactChecks
CREATE TABLE IF NOT EXISTS `tblFactChecks` (
  `VetID` int(10) NOT NULL AUTO_INCREMENT,
  `EntryID` int(10) DEFAULT NULL,
  `Person` varchar(50) DEFAULT NULL,
  `Place` varchar(50) DEFAULT NULL,
  `DateInfo` varchar(50) DEFAULT NULL,
  `ADDLRES` longtext,
  `EDITCOMMENT` longtext,
  PRIMARY KEY (`VetID`),
  UNIQUE KEY `EntryID` (`EntryID`),
  KEY `{C59E46B8-FDA4-467E-B940-B8F653` (`EntryID`),
  KEY `VetID` (`VetID`),
  KEY `FK6C838455901858A5` (`EntryID`),
  KEY `FK6C8384552598DB3D` (`EntryID`),
  CONSTRAINT `FK6C8384552598DB3D` FOREIGN KEY (`EntryID`) REFERENCES `tblDocuments` (`ENTRYID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dump della struttura di tabella bia.tblForum
CREATE TABLE IF NOT EXISTS `tblForum` (
  `forumId` int(11) NOT NULL AUTO_INCREMENT,
  `dateCreated` datetime DEFAULT NULL,
  `description` varchar(2000) DEFAULT NULL,
  `dispositionOrder` tinyint(4) DEFAULT '0',
  `lastUpdate` datetime DEFAULT NULL,
  `postsNumber` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `subType` varchar(10) NOT NULL,
  `title` varchar(2000) NOT NULL,
  `topicsNumber` int(11) DEFAULT NULL,
  `type` varchar(10) NOT NULL,
  `entryId` int(11) DEFAULT NULL,
  `forumParent` int(11) DEFAULT NULL,
  `fullPath` varchar(50) NOT NULL,
  `hierarchyLevel` int(11) NOT NULL,
  `lastPost` int(11) DEFAULT NULL,
  `peopleId` int(11) DEFAULT NULL,
  `placeAllId` int(11) DEFAULT NULL,
  `summaryId` int(11) DEFAULT NULL,
  `imageId` int(11) DEFAULT NULL,
  `logicalDelete` tinyint(4) NOT NULL DEFAULT '0',
  `forumHelpText` longtext,
  `subForumsNumber` int(11) DEFAULT NULL,
  `totalViews` int(11) DEFAULT NULL,
  PRIMARY KEY (`forumId`),
  KEY `FKE850403AC159EBA` (`lastPost`),
  KEY `FKE850403E3C004CF` (`forumParent`),
  KEY `FKE8504031F274476` (`peopleId`),
  KEY `FKE850403654CC8E5` (`entryId`),
  KEY `FKE8504036F0B8458` (`summaryId`),
  KEY `FKE850403FF554D5F` (`placeAllId`),
  KEY `FKE85040346584937` (`forumParent`),
  KEY `FKE850403F978D0E` (`peopleId`),
  KEY `FKE850403FACD4B7D` (`entryId`),
  KEY `FKE8504035F7BCCF0` (`summaryId`),
  KEY `FKE85040361ED91C7` (`placeAllId`),
  KEY `FKE850403C6A56F22` (`lastPost`),
  CONSTRAINT `FKE85040346584937` FOREIGN KEY (`forumParent`) REFERENCES `tblForum` (`forumId`),
  CONSTRAINT `FKE8504035F7BCCF0` FOREIGN KEY (`summaryId`) REFERENCES `tblVolumes` (`SUMMARYID`),
  CONSTRAINT `FKE85040361ED91C7` FOREIGN KEY (`placeAllId`) REFERENCES `tblPlaces` (`PLACEALLID`),
  CONSTRAINT `FKE850403C6A56F22` FOREIGN KEY (`lastPost`) REFERENCES `tblForumPost` (`postId`),
  CONSTRAINT `FKE850403F978D0E` FOREIGN KEY (`peopleId`) REFERENCES `tblPeople` (`PERSONID`),
  CONSTRAINT `FKE850403FACD4B7D` FOREIGN KEY (`entryId`) REFERENCES `tblDocuments` (`ENTRYID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dump della struttura di tabella bia.tblForumOption
CREATE TABLE IF NOT EXISTS `tblForumOption` (
  `canDeletePosts` tinyint(4) NOT NULL DEFAULT '0',
  `canDeleteTopics` tinyint(4) NOT NULL DEFAULT '0',
  `canDownloadAttachments` tinyint(4) NOT NULL DEFAULT '0',
  `canEditPosts` tinyint(4) NOT NULL DEFAULT '0',
  `canHaveSubCategory` tinyint(4) NOT NULL DEFAULT '0',
  `canHaveSubForum` tinyint(4) NOT NULL DEFAULT '0',
  `canHaveTopics` tinyint(4) NOT NULL DEFAULT '0',
  `canOnlyViewOwnTopics` tinyint(4) NOT NULL DEFAULT '0',
  `canPostAttachments` tinyint(4) NOT NULL DEFAULT '0',
  `canPostReplys` tinyint(4) NOT NULL DEFAULT '0',
  `canRateTopics` tinyint(4) NOT NULL DEFAULT '0',
  `canView` tinyint(4) NOT NULL DEFAULT '0',
  `forumId` int(11) NOT NULL,
  `groupBySubForum` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`forumId`),
  KEY `FK1B942A58E7F171C0` (`forumId`),
  KEY `FK1B942A584A89B628` (`forumId`),
  CONSTRAINT `FK1B942A584A89B628` FOREIGN KEY (`forumId`) REFERENCES `tblForum` (`forumId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dump della struttura di tabella bia.tblForumPost
CREATE TABLE IF NOT EXISTS `tblForumPost` (
  `postId` int(11) NOT NULL AUTO_INCREMENT,
  `topicId` int(11) DEFAULT NULL,
  `parentPostId` int(11) DEFAULT NULL,
  `forumId` int(11) DEFAULT NULL,
  `account` varchar(50) DEFAULT NULL,
  `annotationId` int(11) DEFAULT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `lastUpdate` datetime DEFAULT NULL,
  `replyNumber` int(11) DEFAULT NULL,
  `subject` varchar(64) NOT NULL,
  `text` longtext,
  `topic` int(11) DEFAULT NULL,
  `ipAddress` varchar(50) NOT NULL,
  `logicalDelete` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`postId`),
  KEY `FK7CB357436642311A` (`topic`),
  KEY `FK7CB35743E7F171C0` (`forumId`),
  KEY `FK7CB357433D128189` (`parentPostId`),
  KEY `FK7CB357431BC3AA95` (`topicId`),
  KEY `FK7CB35743B9200951` (`account`),
  KEY `FK7CB3574344BAD736` (`annotationId`),
  KEY `FK7CB35743F42F8715` (`account`),
  KEY `FK7CB357439DAC6DB2` (`topic`),
  KEY `FK7CB357434A89B628` (`forumId`),
  KEY `FK7CB3574357A251F1` (`parentPostId`),
  KEY `FK7CB35743532DE72D` (`topicId`),
  KEY `FK7CB357437C2513CE` (`annotationId`),
  CONSTRAINT `FK_tblForumPost_tblUser` FOREIGN KEY (`account`) REFERENCES `tblUser` (`account`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK7CB3574344BAD736` FOREIGN KEY (`annotationId`) REFERENCES `docsources`.`tblAnnotation` (`annotationId`),
  CONSTRAINT `FK7CB357434A89B628` FOREIGN KEY (`forumId`) REFERENCES `tblForum` (`forumId`),
  CONSTRAINT `FK7CB35743532DE72D` FOREIGN KEY (`topicId`) REFERENCES `tblForumTopic` (`topicId`),
  CONSTRAINT `FK7CB3574357A251F1` FOREIGN KEY (`parentPostId`) REFERENCES `tblForumPost` (`postId`),
  CONSTRAINT `FK7CB357437C2513CE` FOREIGN KEY (`annotationId`) REFERENCES `tblAnnotations` (`annotationId`),
  CONSTRAINT `FK7CB357439DAC6DB2` FOREIGN KEY (`topic`) REFERENCES `tblForumTopic` (`topicId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dump della struttura di tabella bia.tblForumPostNotified
CREATE TABLE IF NOT EXISTS `tblForumPostNotified` (
  `postId` int(11) NOT NULL,
  `mailSended` bit(1) NOT NULL,
  `mailSendedDate` datetime DEFAULT NULL,
  PRIMARY KEY (`postId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dump della struttura di tabella bia.tblForumTopic
CREATE TABLE IF NOT EXISTS `tblForumTopic` (
  `topicId` int(11) NOT NULL AUTO_INCREMENT,
  `dateCreated` datetime DEFAULT NULL,
  `hasAttachment` bit(1) DEFAULT NULL,
  `ipAddress` varchar(50) NOT NULL,
  `lastUpdate` datetime DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `subject` varchar(255) DEFAULT NULL,
  `totalReplies` int(11) DEFAULT NULL,
  `totalViews` int(11) DEFAULT NULL,
  `topicType` int(11) DEFAULT NULL,
  `firstPost` int(11) DEFAULT NULL,
  `forumId` int(11) DEFAULT NULL,
  `lastPost` int(11) DEFAULT NULL,
  `account` varchar(50) DEFAULT NULL,
  `entryId` int(11) DEFAULT NULL,
  `imageId` int(11) DEFAULT NULL,
  `peopleId` int(11) DEFAULT NULL,
  `placeAllId` int(11) DEFAULT NULL,
  `summaryId` int(11) DEFAULT NULL,
  `annotationId` int(11) DEFAULT NULL,
  `logicalDelete` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`topicId`),
  KEY `FK19EFE2ECB0ECDB4` (`firstPost`),
  KEY `FK19EFE2ECE7F175A0` (`forumId`),
  KEY `FK19EFE2ECAC242A9A` (`lastPost`),
  KEY `FK19EFE2EC1F274476` (`peopleId`),
  KEY `FK19EFE2EC654CC8E5` (`entryId`),
  KEY `FK19EFE2EC6F0B8458` (`summaryId`),
  KEY `FK19EFE2ECFF554D5F` (`placeAllId`),
  KEY `FK19EFE2EC44BAD736` (`annotationId`),
  KEY `FK19EFE2EC259E9E1C` (`firstPost`),
  KEY `FK19EFE2EC4A89BA08` (`forumId`),
  KEY `FK19EFE2EC7C2513CE` (`annotationId`),
  KEY `FK19EFE2ECA4C915AD` (`account`),
  CONSTRAINT `FK_tblForumTopic_tblUser` FOREIGN KEY (`account`) REFERENCES `tblUser` (`account`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK19EFE2EC1F274476` FOREIGN KEY (`peopleId`) REFERENCES `tblPeople` (`PERSONID`),
  CONSTRAINT `FK19EFE2EC259E9E1C` FOREIGN KEY (`firstPost`) REFERENCES `tblForumPost` (`postId`),
  CONSTRAINT `FK19EFE2EC44BAD736` FOREIGN KEY (`annotationId`) REFERENCES `docsources`.`tblAnnotation` (`annotationId`),
  CONSTRAINT `FK19EFE2EC4A89BA08` FOREIGN KEY (`forumId`) REFERENCES `tblForum` (`forumId`),
  CONSTRAINT `FK19EFE2EC654CC8E5` FOREIGN KEY (`entryId`) REFERENCES `tblDocuments` (`ENTRYID`),
  CONSTRAINT `FK19EFE2EC6F0B8458` FOREIGN KEY (`summaryId`) REFERENCES `tblVolumes` (`SUMMARYID`),
  CONSTRAINT `FK19EFE2EC7C2513CE` FOREIGN KEY (`annotationId`) REFERENCES `tblAnnotations` (`annotationId`),
  CONSTRAINT `FK19EFE2ECFF554D5F` FOREIGN KEY (`placeAllId`) REFERENCES `tblPlaces` (`PLACEALLID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dump della struttura di tabella bia.tblForumTopicWatch
CREATE TABLE IF NOT EXISTS `tblForumTopicWatch` (
  `topicWwatchId` int(11) NOT NULL AUTO_INCREMENT,
  `topicId` int(11) DEFAULT NULL,
  `account` varchar(50) DEFAULT NULL,
  `topicWatchId` int(11) NOT NULL,
  PRIMARY KEY (`topicWwatchId`),
  KEY `FK25CE5431BC3AE75` (`topicId`),
  KEY `FK25CE543532DEB0D` (`topicId`),
  KEY `FK25CE543A4C915AD` (`account`),
  CONSTRAINT `FK_tblForumTopicWatch_tblUser` FOREIGN KEY (`account`) REFERENCES `tblUser` (`account`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK25CE543532DEB0D` FOREIGN KEY (`topicId`) REFERENCES `tblForumTopic` (`topicId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dump della struttura di tabella bia.tblImages
CREATE TABLE IF NOT EXISTS `tblImages` (
  `imageId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `volNum` int(10) unsigned NOT NULL,
  `volLetExt` varchar(1) DEFAULT NULL,
  `imageName` varchar(45) NOT NULL,
  `imageType` varchar(1) NOT NULL,
  `imageRectoVerso` varchar(1) NOT NULL,
  `missedNumbering` varchar(3) DEFAULT NULL,
  `imageOrder` int(5) DEFAULT NULL,
  `imageProgTypeNum` int(5) NOT NULL,
  `storagePath` int(11) NOT NULL,
  `dateCreated` datetime NOT NULL,
  PRIMARY KEY (`imageId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dump della struttura di tabella bia.tblMarriages
CREATE TABLE IF NOT EXISTS `tblMarriages` (
  `MARRIAGEID` int(10) NOT NULL AUTO_INCREMENT,
  `HUSBANDID` int(10) DEFAULT NULL,
  `WIFEID` int(10) DEFAULT NULL,
  `STARTDAY` int(10) DEFAULT NULL,
  `STARTMONTH` varchar(50) DEFAULT NULL,
  `STARTYEAR` int(10) DEFAULT NULL,
  `STARTUNS` tinyint(1) NOT NULL,
  `ENDDAY` int(10) DEFAULT NULL,
  `ENDMONTH` varchar(50) DEFAULT NULL,
  `ENDYEAR` int(10) DEFAULT NULL,
  `ENDUNS` tinyint(1) NOT NULL,
  `MARTERM` varchar(50) DEFAULT NULL,
  `REFID` int(10) DEFAULT NULL,
  `NOTES` longtext,
  `STARTMONTHNUM` int(10) DEFAULT NULL,
  `ENDMONTHNUM` int(10) DEFAULT NULL,
  `DateCreated` datetime DEFAULT NULL,
  PRIMARY KEY (`MARRIAGEID`),
  KEY `ENDMONTHNUM` (`ENDMONTHNUM`),
  KEY `HUSBANDID` (`HUSBANDID`),
  KEY `MARRIAGEID` (`MARRIAGEID`),
  KEY `REFID` (`REFID`),
  KEY `STARTMONTHNUM` (`STARTMONTHNUM`),
  KEY `WIFEID` (`WIFEID`),
  KEY `FKD6F29ECBA2A2A058` (`WIFEID`),
  KEY `FKD6F29ECBD60812E2` (`HUSBANDID`),
  KEY `FKD6F29ECB9312E8F0` (`WIFEID`),
  KEY `FKD6F29ECBC6785B7A` (`HUSBANDID`),
  CONSTRAINT `FKD6F29ECB9312E8F0` FOREIGN KEY (`WIFEID`) REFERENCES `tblPeople` (`PERSONID`),
  CONSTRAINT `FKD6F29ECBC6785B7A` FOREIGN KEY (`HUSBANDID`) REFERENCES `tblPeople` (`PERSONID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dump della struttura di tabella bia.tblMonths
CREATE TABLE IF NOT EXISTS `tblMonths` (
  `MONTHNUM` int(10) NOT NULL,
  `MONTHNAME` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`MONTHNUM`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dump della struttura di tabella bia.tblParents
CREATE TABLE IF NOT EXISTS `tblParents` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `parentId` int(10) NOT NULL,
  `childId` int(10) NOT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `lastUpdate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK7A67536B501303B1` (`parentId`),
  KEY `FK7A67536B36829BA3` (`childId`),
  KEY `FK7A67536B40834C49` (`parentId`),
  KEY `FK7A67536B26F2E43B` (`childId`),
  CONSTRAINT `FK7A67536B26F2E43B` FOREIGN KEY (`childId`) REFERENCES `tblPeople` (`PERSONID`),
  CONSTRAINT `FK7A67536B40834C49` FOREIGN KEY (`parentId`) REFERENCES `tblPeople` (`PERSONID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dump della struttura di tabella bia.tblPasswordChangeRequest
CREATE TABLE IF NOT EXISTS `tblPasswordChangeRequest` (
  `UUID` varchar(50) NOT NULL,
  `ACCOUNT` varchar(50) NOT NULL,
  `MAILSENDED` bit(1) NOT NULL,
  `MAILSENDEDDATE` datetime DEFAULT NULL,
  `REQUESTDATE` datetime NOT NULL,
  `REQUESTIPADDRESS` varchar(50) NOT NULL,
  `RESET` bit(1) NOT NULL,
  `RESETDATE` datetime DEFAULT NULL,
  `RESETIPADDRESS` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`UUID`),
  KEY `FK7F6E2CC6F42F8715` (`ACCOUNT`),
  CONSTRAINT `FK7F6E2CC6F42F8715` FOREIGN KEY (`ACCOUNT`) REFERENCES `tblUser` (`account`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dump della struttura di tabella bia.tblPeople
CREATE TABLE IF NOT EXISTS `tblPeople` (
  `PERSONID` int(10) NOT NULL AUTO_INCREMENT,
  `MAPNameLF` varchar(150) DEFAULT NULL,
  `GENDER` varchar(1) DEFAULT NULL,
  `ACTIVESTART` varchar(50) DEFAULT NULL,
  `ACTIVEEND` varchar(50) DEFAULT NULL,
  `BYEAR` double DEFAULT NULL,
  `BMONTHNUM` int(10) DEFAULT NULL,
  `BDAY` tinyint(3) unsigned DEFAULT NULL,
  `BORNDATE` int(11) DEFAULT NULL,
  `BPLACEID` int(10) DEFAULT NULL,
  `BPLACE` varchar(50) DEFAULT NULL,
  `DYEAR` double DEFAULT NULL,
  `DMONTHNUM` int(10) DEFAULT NULL,
  `DDAY` tinyint(3) unsigned DEFAULT NULL,
  `DEATHDATE` int(11) DEFAULT NULL,
  `DPLACEID` int(10) DEFAULT NULL,
  `DPLACE` varchar(50) DEFAULT NULL,
  `FIRST` varchar(50) DEFAULT NULL,
  `SUCNUM` varchar(6) DEFAULT NULL,
  `middle` varchar(50) DEFAULT NULL,
  `midprefix` varchar(50) DEFAULT NULL,
  `last` varchar(50) DEFAULT NULL,
  `lastprefix` varchar(50) DEFAULT NULL,
  `postlast` varchar(50) DEFAULT NULL,
  `postlastprefix` varchar(50) DEFAULT NULL,
  `BAPPROX` tinyint(1) NOT NULL,
  `BDATEBC` tinyint(1) NOT NULL,
  `BPLACEUNS` tinyint(1) NOT NULL,
  `DAPPROX` tinyint(1) NOT NULL,
  `DYEARBC` tinyint(1) NOT NULL,
  `DPLACEUNS` tinyint(1) NOT NULL,
  `STATUS` varchar(15) DEFAULT NULL,
  `BIONOTES` longtext,
  `STAFFNOTES` longtext,
  `PORTRAIT` tinyint(1) NOT NULL,
  `FATHERID` int(10) DEFAULT NULL,
  `MOTHERID` int(10) DEFAULT NULL,
  `RESID` varchar(50) DEFAULT NULL,
  `DateCreated` datetime DEFAULT NULL,
  `LastUpdate` datetime DEFAULT NULL,
  `LOGICALDELETE` tinyint(4) NOT NULL DEFAULT '0',
  `portraitImageName` varchar(100) DEFAULT NULL,
  `portraitAuthor` varchar(100) DEFAULT NULL,
  `portraitSubject` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`PERSONID`),
  KEY `BMONTHNUM` (`BMONTHNUM`),
  KEY `BPLACEID` (`BPLACEID`),
  KEY `DMONTHNUM` (`DMONTHNUM`),
  KEY `DPLACEID` (`DPLACEID`),
  KEY `FATHERID` (`FATHERID`),
  KEY `MOTHERID` (`MOTHERID`),
  KEY `RESID` (`RESID`),
  KEY `SUCNUM` (`SUCNUM`),
  KEY `FKD29D97ED8E811A4D` (`DMONTHNUM`),
  KEY `FKD29D97ED65F83C4B` (`BMONTHNUM`),
  KEY `FKD29D97EDB6002308` (`DPLACEID`),
  KEY `FKD29D97EDE63DC94A` (`BPLACEID`),
  KEY `FKD29D97EDF1195EB5` (`DMONTHNUM`),
  KEY `FKD29D97EDC89080B3` (`BMONTHNUM`),
  KEY `FKD29D97ED18986770` (`DPLACEID`),
  KEY `FKD29D97ED48D60DB2` (`BPLACEID`),
  CONSTRAINT `FKD29D97ED18986770` FOREIGN KEY (`DPLACEID`) REFERENCES `tblPlaces` (`PLACEALLID`),
  CONSTRAINT `FKD29D97ED48D60DB2` FOREIGN KEY (`BPLACEID`) REFERENCES `tblPlaces` (`PLACEALLID`),
  CONSTRAINT `FKD29D97EDC89080B3` FOREIGN KEY (`BMONTHNUM`) REFERENCES `tblMonths` (`MONTHNUM`),
  CONSTRAINT `FKD29D97EDF1195EB5` FOREIGN KEY (`DMONTHNUM`) REFERENCES `tblMonths` (`MONTHNUM`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dump della struttura di tabella bia.tblPlaceExternalLinks
CREATE TABLE IF NOT EXISTS `tblPlaceExternalLinks` (
  `PLACEEXTLINKSID` int(11) NOT NULL AUTO_INCREMENT,
  `DESCRIPTION` varchar(100) DEFAULT NULL,
  `EXTERNAL_LINK` varchar(300) DEFAULT NULL,
  `PLACEALLID` int(11) DEFAULT NULL,
  PRIMARY KEY (`PLACEEXTLINKSID`),
  KEY `FK1D5865451E022D5F` (`PLACEALLID`),
  KEY `FK1D586545809A71C7` (`PLACEALLID`),
  CONSTRAINT `FK1D586545809A71C7` FOREIGN KEY (`PLACEALLID`) REFERENCES `tblPlaces` (`PLACEALLID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dump della struttura di tabella bia.tblPlaceGeographicCoordinates
CREATE TABLE IF NOT EXISTS `tblPlaceGeographicCoordinates` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `DEGREE_LATITUDE` int(11) DEFAULT NULL,
  `DEGREE_LONGITUDE` int(11) DEFAULT NULL,
  `DIRECTION_LATITUDE` varchar(1) DEFAULT NULL,
  `DIRECTION_LONGITUDE` varchar(1) DEFAULT NULL,
  `MINUTE_LATITUDE` int(11) DEFAULT NULL,
  `MINUTE_LONGITUDE` int(11) DEFAULT NULL,
  `SECOND_LATITUDE` int(11) DEFAULT NULL,
  `SECOND_LONGITUDE` int(11) DEFAULT NULL,
  `PLACEALLID` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKEA85C69B1E022D5F` (`PLACEALLID`),
  KEY `FKEA85C69B809A71C7` (`PLACEALLID`),
  CONSTRAINT `FKEA85C69B809A71C7` FOREIGN KEY (`PLACEALLID`) REFERENCES `tblPlaces` (`PLACEALLID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dump della struttura di tabella bia.tblPlaces
CREATE TABLE IF NOT EXISTS `tblPlaces` (
  `PLACEALLID` int(10) NOT NULL AUTO_INCREMENT,
  `PLACENAMEID` int(10) DEFAULT NULL,
  `GEOGKEY` int(10) DEFAULT NULL,
  `PLACENAME` varchar(255) DEFAULT NULL,
  `PLACENAMEFULL` varchar(255) DEFAULT NULL,
  `PLNAMEFULL_PLTYPE` varchar(255) DEFAULT NULL,
  `PLTYPE` varchar(255) DEFAULT NULL,
  `PREFFLAG` varchar(5) DEFAULT NULL,
  `PLSOURCE` varchar(50) DEFAULT NULL,
  `PLPARENT` varchar(255) DEFAULT NULL,
  `PARENTTYPE` varchar(255) DEFAULT NULL,
  `PLPARENT_TERM_ID` int(10) DEFAULT NULL,
  `PLPARENT_SUBJECT_ID` int(10) DEFAULT NULL,
  `PLPARENT_PLACEALLID` int(10) DEFAULT NULL,
  `GPARENT` varchar(255) DEFAULT NULL,
  `GPTYPE` varchar(255) DEFAULT NULL,
  `GGP` varchar(255) DEFAULT NULL,
  `GGPTYPE` varchar(255) DEFAULT NULL,
  `GP2` varchar(255) DEFAULT NULL,
  `GP2TYPE` varchar(255) DEFAULT NULL,
  `RESID` varchar(50) DEFAULT NULL,
  `DATEENTERED` datetime DEFAULT NULL,
  `PLACESMEMO` longtext,
  `ADDLRES` tinyint(1) NOT NULL,
  `Term_Accent` varchar(50) DEFAULT NULL,
  `Language` int(10) DEFAULT NULL,
  `OTHER_FLAGS` varchar(50) DEFAULT NULL,
  `GEOGKEY_CHILDREN` longtext,
  `GEO_ID_ENCODING` varchar(10) DEFAULT NULL,
  `LOGICALDELETE` tinyint(4) NOT NULL DEFAULT '0',
  `DATECREATED` datetime DEFAULT NULL,
  `LASTUPDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`PLACEALLID`),
  KEY `GEOGKEY` (`GEOGKEY`),
  KEY `PLACENAMEID` (`PLACENAMEID`),
  KEY `PLPARENT_PLACEALLID` (`PLPARENT_PLACEALLID`),
  KEY `PLPARENT_SUBJECT_ID` (`PLPARENT_SUBJECT_ID`),
  KEY `PLPARENT_TERM_ID` (`PLPARENT_TERM_ID`),
  KEY `RESID` (`RESID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dump della struttura di tabella bia.tblPlaceType
CREATE TABLE IF NOT EXISTS `tblPlaceType` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dump della struttura di tabella bia.tblPoLink
CREATE TABLE IF NOT EXISTS `tblPoLink` (
  `PRLINKID` int(10) NOT NULL AUTO_INCREMENT,
  `PERSONID` int(10) DEFAULT NULL,
  `TitleOccID` int(10) DEFAULT NULL,
  `STARTYEAR` int(10) DEFAULT NULL,
  `STARTMONTH` varchar(50) DEFAULT NULL,
  `STARTDAY` int(10) DEFAULT NULL,
  `ENDYEAR` int(10) DEFAULT NULL,
  `ENDMONTH` varchar(50) DEFAULT NULL,
  `ENDDAY` int(10) DEFAULT NULL,
  `PRTAG` int(10) DEFAULT NULL,
  `PRLinkNotes` longtext,
  `STARTAPPROX` tinyint(1) NOT NULL,
  `STARTUNS` tinyint(1) NOT NULL,
  `ENDAPPROX` tinyint(1) NOT NULL,
  `ENDUNS` tinyint(1) NOT NULL,
  `STARTMONTHNUM` int(10) DEFAULT NULL,
  `ENDMONTHNUM` int(10) DEFAULT NULL,
  `DateCreated` datetime DEFAULT NULL,
  PRIMARY KEY (`PRLINKID`),
  KEY `{3FCD4DE6-DEE7-4A76-87FF-F32406` (`TitleOccID`),
  KEY `{4BCEF044-D25A-4C5D-B949-4B908F` (`PERSONID`),
  KEY `ENDMONTHNUM` (`ENDMONTHNUM`),
  KEY `PERSONID` (`PERSONID`),
  KEY `STARTMONTHNUM` (`STARTMONTHNUM`),
  KEY `TitleOccID` (`TitleOccID`),
  KEY `FKD1578FD7BB25084B` (`STARTMONTHNUM`),
  KEY `FKD1578FD71426990F` (`TitleOccID`),
  KEY `FKD1578FD75317879C` (`PERSONID`),
  KEY `FKD1578FD71E85BFC4` (`ENDMONTHNUM`),
  KEY `FKD31A7FF7BB25084B` (`STARTMONTHNUM`),
  KEY `FKD31A7FF71426990F` (`TitleOccID`),
  KEY `FKD31A7FF75317879C` (`PERSONID`),
  KEY `FKD31A7FF71E85BFC4` (`ENDMONTHNUM`),
  CONSTRAINT `FKD31A7FF71426990F` FOREIGN KEY (`TitleOccID`) REFERENCES `tblTitleOccsList` (`TitleOccID`),
  CONSTRAINT `FKD31A7FF71E85BFC4` FOREIGN KEY (`ENDMONTHNUM`) REFERENCES `tblMonths` (`MONTHNUM`),
  CONSTRAINT `FKD31A7FF75317879C` FOREIGN KEY (`PERSONID`) REFERENCES `tblPeople` (`PERSONID`),
  CONSTRAINT `FKD31A7FF7BB25084B` FOREIGN KEY (`STARTMONTHNUM`) REFERENCES `tblMonths` (`MONTHNUM`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dump della struttura di tabella bia.tblPrcLink
CREATE TABLE IF NOT EXISTS `tblPrcLink` (
  `PRCLinkID` int(10) NOT NULL AUTO_INCREMENT,
  `PersonID` int(10) DEFAULT NULL,
  `RoleCatID` int(10) DEFAULT NULL,
  `DateCreated` datetime DEFAULT NULL,
  PRIMARY KEY (`PRCLinkID`),
  KEY `PersonID` (`PersonID`),
  KEY `RoleCatID` (`RoleCatID`),
  KEY `FK968AE49D5317879C` (`PersonID`),
  KEY `FK968AE49D6BE301DE` (`RoleCatID`),
  KEY `FK968AE49D4387D034` (`PersonID`),
  KEY `FK968AE49D897BCC46` (`RoleCatID`),
  CONSTRAINT `FK968AE49D4387D034` FOREIGN KEY (`PersonID`) REFERENCES `tblPeople` (`PERSONID`),
  CONSTRAINT `FK968AE49D897BCC46` FOREIGN KEY (`RoleCatID`) REFERENCES `tblRoleCats` (`RoleCatID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dump della struttura di tabella bia.tblReportedForumPost
CREATE TABLE IF NOT EXISTS `tblReportedForumPost` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `messageSended` bit(1) NOT NULL,
  `messageSendedDate` datetime DEFAULT NULL,
  `postId` int(11) NOT NULL,
  `account` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK81A74DB0E31504A7` (`postId`),
  KEY `FK81A74DB0A4C915AD` (`account`),
  CONSTRAINT `FK81A74DB0A4C915AD` FOREIGN KEY (`account`) REFERENCES `tblUser` (`account`),
  CONSTRAINT `FK81A74DB0E31504A7` FOREIGN KEY (`postId`) REFERENCES `tblForumPost` (`postId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dump della struttura di tabella bia.tblResearchers
CREATE TABLE IF NOT EXISTS `tblResearchers` (
  `RESIDNo` int(10) DEFAULT NULL,
  `RESID` varchar(50) DEFAULT NULL,
  `RESFIRST` varchar(50) DEFAULT NULL,
  `RESLAST` varchar(50) DEFAULT NULL,
  `RESSTART` datetime DEFAULT NULL,
  `RESENDPROJ` datetime DEFAULT NULL,
  `RESENDACTUAL` datetime DEFAULT NULL,
  KEY `RESID` (`RESID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dump della struttura di tabella bia.tblRoleCats
CREATE TABLE IF NOT EXISTS `tblRoleCats` (
  `RoleCatID` int(10) NOT NULL AUTO_INCREMENT,
  `RoleCatMinor` varchar(255) DEFAULT NULL,
  `RoleCatMajor` varchar(255) DEFAULT NULL,
  `RoleCatMajorID` int(10) DEFAULT NULL,
  `SortGroups` int(10) DEFAULT NULL,
  `SORTGROUS` int(11) DEFAULT NULL,
  PRIMARY KEY (`RoleCatID`),
  KEY `RoleCatMajorID` (`RoleCatMajorID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dump della struttura di tabella bia.tblSchedone
CREATE TABLE IF NOT EXISTS `tblSchedone` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ATTIVO` bit(1) DEFAULT NULL,
  `CARTE_BIANCHE` longtext,
  `CARTE_MANCANTI` longtext,
  `CARTULAZIONE` varchar(1000) DEFAULT NULL,
  `COLORE_IMMAGINE` varchar(10) DEFAULT NULL,
  `COMPRESSIONE_JPEG` varchar(50) DEFAULT NULL,
  `COMPRESSIONE_PDF` varchar(50) DEFAULT NULL,
  `COMPRESSIONE_TIFF` varchar(50) DEFAULT NULL,
  `DATA_CREAZIONE` datetime DEFAULT NULL,
  `DATA_FINE_ANNO` int(11) DEFAULT NULL,
  `DATA_FINE_GIORNO` int(11) DEFAULT NULL,
  `DATA_INIZIO_ANNO` int(11) DEFAULT NULL,
  `DATA_INIZIO_GIORNO` int(11) DEFAULT NULL,
  `DATA_RIPRESA_ANNO` int(11) DEFAULT NULL,
  `DATA_RIPRESA_GIORNO` int(11) DEFAULT NULL,
  `DATA_ULTIMO_AGGIORNAMENTO` datetime DEFAULT NULL,
  `DESCRIZIONE_CONTENUTO` varchar(1000) DEFAULT NULL,
  `DESCRIZIONE_CONTENUTO_ENG` varchar(1000) DEFAULT NULL,
  `DIM_MEDIA_IMMAGINI_JPEG` bigint(20) DEFAULT NULL,
  `DIM_MEDIA_IMMAGINI_PDF` bigint(20) DEFAULT NULL,
  `DIM_MEDIA_IMMAGINI_TIFF` bigint(20) DEFAULT NULL,
  `DIM_TOTALE_IMMAGINI_JPEG` bigint(20) DEFAULT NULL,
  `DIM_TOTALE_IMMAGINI_PDF` bigint(20) DEFAULT NULL,
  `DIM_TOTALE_IMMAGINI_TIFF` bigint(20) DEFAULT NULL,
  `DIMENSIONI_ALTEZZA` int(11) DEFAULT NULL,
  `DIMENSIONI_BASE` varchar(50) DEFAULT NULL,
  `FONDO` varchar(50) DEFAULT NULL,
  `FORMATO_MEDIA_IMMAGINI_JPEG` varchar(2) DEFAULT NULL,
  `FORMATO_MEDIA_IMMAGINI_PDF` varchar(2) DEFAULT NULL,
  `FORMATO_MEDIA_IMMAGINI_TIFF` varchar(2) DEFAULT NULL,
  `FORMATO_TOTALE_IMMAGINI_JPEG` varchar(2) DEFAULT NULL,
  `FORMATO_TOTALE_IMMAGINI_PDF` varchar(2) DEFAULT NULL,
  `FORMATO_TOTALE_IMMAGINI_TIFF` varchar(2) DEFAULT NULL,
  `ISTITUTO` varchar(50) DEFAULT NULL,
  `LEGATURA` varchar(255) DEFAULT NULL,
  `NOME_FILES` varchar(1000) DEFAULT NULL,
  `NOTE_ALLA_CARTULAZIONE` longtext,
  `NOTE_ALLA_CARTULAZIONE_ENG` longtext,
  `NUMERO_TOTALE_IMMAGINI_JPEG` int(11) DEFAULT NULL,
  `NUMERO_TOTALE_IMMAGINI_PDF` int(11) DEFAULT NULL,
  `NUMERO_TOTALE_IMMAGINI_TIFF` int(11) DEFAULT NULL,
  `N_UNITA` int(11) DEFAULT NULL,
  `OPERATORE` varchar(50) DEFAULT NULL,
  `PROFONDITA_COLORE` varchar(50) DEFAULT NULL,
  `RESID` varchar(255) DEFAULT NULL,
  `RESPONSABILE_FOTORIPRODUZIONE` varchar(500) DEFAULT NULL,
  `RISOLUZIONE` varchar(50) DEFAULT NULL,
  `SISTEMA_DI_SCANSIONE` varchar(50) DEFAULT NULL,
  `SUPPORTO` varchar(50) DEFAULT NULL,
  `TIPO_RIPRESA` varchar(50) DEFAULT NULL,
  `TITOLO` varchar(50) DEFAULT NULL,
  `VOLLETEXT` varchar(1) DEFAULT NULL,
  `VOLNUM` int(11) DEFAULT NULL,
  `DATA_FINE_MESE` int(11) DEFAULT NULL,
  `DATA_INIZIO_MESE` int(11) DEFAULT NULL,
  `DATA_RIPRESA_MESE` int(11) DEFAULT NULL,
  `SERIESREFNUM` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK62DE022D444D0887` (`SERIESREFNUM`),
  KEY `FK62DE022D5DAB67FD` (`DATA_FINE_MESE`),
  KEY `FK62DE022D7764EA47` (`DATA_RIPRESA_MESE`),
  KEY `FK62DE022DA536AF7B` (`DATA_INIZIO_MESE`),
  CONSTRAINT `FK62DE022D444D0887` FOREIGN KEY (`SERIESREFNUM`) REFERENCES `tblSeriesList` (`SERIESREFNUM`),
  CONSTRAINT `FK62DE022D5DAB67FD` FOREIGN KEY (`DATA_FINE_MESE`) REFERENCES `tblMonths` (`MONTHNUM`),
  CONSTRAINT `FK62DE022D7764EA47` FOREIGN KEY (`DATA_RIPRESA_MESE`) REFERENCES `tblMonths` (`MONTHNUM`),
  CONSTRAINT `FK62DE022DA536AF7B` FOREIGN KEY (`DATA_INIZIO_MESE`) REFERENCES `tblMonths` (`MONTHNUM`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dump della struttura di tabella bia.tblSearchFilter
CREATE TABLE IF NOT EXISTS `tblSearchFilter` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dateCreated` datetime NOT NULL,
  `dateUpdated` datetime NOT NULL,
  `filterData` longblob,
  `filterName` varchar(50) NOT NULL,
  `searchType` varchar(15) DEFAULT NULL,
  `totalResult` bigint(20) NOT NULL,
  `account` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKC7C4A61EF42F8715` (`account`),
  CONSTRAINT `FKC7C4A61EF42F8715` FOREIGN KEY (`account`) REFERENCES `tblUser` (`account`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dump della struttura di tabella bia.tblSeriesList
CREATE TABLE IF NOT EXISTS `tblSeriesList` (
  `SERIESREFNUM` int(10) NOT NULL,
  `TITLE` varchar(100) DEFAULT NULL,
  `SUBTITLE1` varchar(100) DEFAULT NULL,
  `SUBTITLE2` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`SERIESREFNUM`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dump della struttura di tabella bia.tblSitemap
CREATE TABLE IF NOT EXISTS `tblSitemap` (
  `location` varchar(255) NOT NULL,
  `changeFrequency` varchar(10) DEFAULT NULL,
  `lastModification` date DEFAULT NULL,
  `priority` double DEFAULT NULL,
  `dateCreated` datetime DEFAULT NULL,
  PRIMARY KEY (`location`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dump della struttura di tabella bia.tblSitemapIndex
CREATE TABLE IF NOT EXISTS `tblSitemapIndex` (
  `id` int(11) NOT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `lastModification` date DEFAULT NULL,
  `location` varchar(255) NOT NULL,
  `xmlFile` longtext,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dump della struttura di tabella bia.tblSynExtracts
CREATE TABLE IF NOT EXISTS `tblSynExtracts` (
  `SynExtrID` int(10) NOT NULL AUTO_INCREMENT,
  `ENTRYID` int(10) DEFAULT NULL,
  `DOCEXTRACT` longtext,
  `SYNOPSIS` longtext,
  `LastUpdate` datetime DEFAULT NULL,
  `DateCreated` datetime DEFAULT NULL,
  PRIMARY KEY (`SynExtrID`),
  KEY `{83B2EA23-44E6-48F2-8897-F67473` (`ENTRYID`),
  KEY `ENTRYID` (`ENTRYID`),
  KEY `FKEFE0D45C901858A5` (`ENTRYID`),
  KEY `FKEFE0D45C2598DB3D` (`ENTRYID`),
  CONSTRAINT `FKEFE0D45C2598DB3D` FOREIGN KEY (`ENTRYID`) REFERENCES `tblDocuments` (`ENTRYID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dump della struttura di tabella bia.tblTitleOccsList
CREATE TABLE IF NOT EXISTS `tblTitleOccsList` (
  `TitleOccID` int(10) NOT NULL AUTO_INCREMENT,
  `TitleOcc` varchar(255) DEFAULT NULL,
  `RoleCatMinorID` int(10) DEFAULT NULL,
  `TitleVariants` longtext,
  `DATECREATED` datetime DEFAULT NULL,
  `LASTUPDATE` datetime DEFAULT NULL,
  `RESID` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`TitleOccID`),
  KEY `{3AA528B6-BDBA-47E4-8112-05329A` (`RoleCatMinorID`),
  KEY `RoleCatMinorID` (`RoleCatMinorID`),
  KEY `FK890DFB5C242BF133` (`RoleCatMinorID`),
  KEY `FK890DFB5C41C4BB9B` (`RoleCatMinorID`),
  CONSTRAINT `FK890DFB5C41C4BB9B` FOREIGN KEY (`RoleCatMinorID`) REFERENCES `tblRoleCats` (`RoleCatID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dump della struttura di tabella bia.tblTopicsList
CREATE TABLE IF NOT EXISTS `tblTopicsList` (
  `TOPICID` int(10) NOT NULL AUTO_INCREMENT,
  `TOPICTITLE` varchar(50) DEFAULT NULL,
  `DESCRIPTION` longtext,
  PRIMARY KEY (`TOPICID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dump della struttura di tabella bia.tblUser
CREATE TABLE IF NOT EXISTS `tblUser` (
  `account` varchar(50) NOT NULL,
  `activationDate` datetime DEFAULT NULL,
  `active` bit(1) NOT NULL DEFAULT b'0',
  `address` varchar(200) DEFAULT NULL,
  `approved` bit(1) NOT NULL DEFAULT b'0',
  `approvedBy` varchar(50) DEFAULT NULL,
  `badLogin` int(11) NOT NULL DEFAULT '0',
  `city` varchar(50) DEFAULT NULL,
  `country` varchar(3) NOT NULL,
  `currentLoginDate` datetime DEFAULT NULL,
  `expirationDate` datetime NOT NULL,
  `expirationPasswordDate` datetime NOT NULL,
  `firstName` varchar(50) NOT NULL,
  `forumJoinedDate` datetime DEFAULT NULL,
  `forumNumberOfPost` bigint(20) NOT NULL DEFAULT '0',
  `initials` varchar(5) NOT NULL,
  `interests` varchar(500) DEFAULT NULL,
  `lastActiveForumDate` datetime DEFAULT NULL,
  `lastForumPostDate` datetime DEFAULT NULL,
  `lastLoginDate` datetime DEFAULT NULL,
  `lastLogoutDate` datetime DEFAULT NULL,
  `lastName` varchar(50) NOT NULL,
  `lastPasswordChangeDate` datetime DEFAULT NULL,
  `locked` bit(1) NOT NULL DEFAULT b'0',
  ` mail` varchar(50) NOT NULL,
  `mailHide` bit(1) NOT NULL,
  `mailNotification` bit(1) NOT NULL,
  `middleName` varchar(20) NOT NULL,
  `organization` varchar(50) NOT NULL,
  `password` varchar(64) NOT NULL,
  `PORTRAIT` tinyint(4) NOT NULL,
  `portraitImageName` varchar(100) DEFAULT NULL,
  `photo` longblob,
  `registrationDate` datetime NOT NULL,
  `title` varchar(50) DEFAULT NULL,
  `forumTopicSubscription` bit(1) NOT NULL DEFAULT b'1',
  PRIMARY KEY (`account`),
  KEY `FKA5A810A91DDC28EE` (`approvedBy`),
  CONSTRAINT `FK_tblUser_tblUser` FOREIGN KEY (`approvedBy`) REFERENCES `tblUser` (`account`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dump della struttura di tabella bia.tblUserAuthority
CREATE TABLE IF NOT EXISTS `tblUserAuthority` (
  `authority` varchar(255) NOT NULL,
  `priority` int(11) NOT NULL,
  `description` varchar(255) NOT NULL,
  PRIMARY KEY (`authority`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dump della struttura di tabella bia.tblUserHistory
CREATE TABLE IF NOT EXISTS `tblUserHistory` (
  `idUserHistory` int(11) NOT NULL AUTO_INCREMENT,
  `action` varchar(10) DEFAULT NULL,
  `category` varchar(20) DEFAULT NULL,
  `dateAndTime` datetime NOT NULL,
  `description` varchar(100) NOT NULL,
  `searchData` longblob,
  `account` varchar(50) NOT NULL,
  `entryId` int(11) DEFAULT NULL,
  `forumId` int(11) DEFAULT NULL,
  `forumPostId` int(11) DEFAULT NULL,
  `forumTopicId` int(11) DEFAULT NULL,
  `peopleId` int(11) DEFAULT NULL,
  `placeAllId` int(11) DEFAULT NULL,
  `summaryId` int(11) DEFAULT NULL,
  `logicalDelete` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`idUserHistory`),
  KEY `FK6E450BABE5C8DE34` (`forumTopicId`),
  KEY `FK6E450BAB1F274476` (`peopleId`),
  KEY `FK6E450BABE7F171C0` (`forumId`),
  KEY `FK6E450BAB654CC8E5` (`entryId`),
  KEY `FK6E450BAB6F0B8458` (`summaryId`),
  KEY `FK6E450BABD74B90C0` (`forumPostId`),
  KEY `FK6E450BABFF554D5F` (`placeAllId`),
  KEY `FK6E450BABF42F8715` (`account`),
  KEY `FK6E450BAB1D331ACC` (`forumTopicId`),
  KEY `FK6E450BABF978D0E` (`peopleId`),
  KEY `FK6E450BAB4A89B628` (`forumId`),
  KEY `FK6E450BABFACD4B7D` (`entryId`),
  KEY `FK6E450BAB5F7BCCF0` (`summaryId`),
  KEY `FK6E450BAB61ED91C7` (`placeAllId`),
  CONSTRAINT `FK_tblUserHistory_tblUser` FOREIGN KEY (`account`) REFERENCES `tblUser` (`account`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK6E450BAB1D331ACC` FOREIGN KEY (`forumTopicId`) REFERENCES `tblForumTopic` (`topicId`),
  CONSTRAINT `FK6E450BAB4A89B628` FOREIGN KEY (`forumId`) REFERENCES `tblForum` (`forumId`),
  CONSTRAINT `FK6E450BAB5F7BCCF0` FOREIGN KEY (`summaryId`) REFERENCES `tblVolumes` (`SUMMARYID`),
  CONSTRAINT `FK6E450BAB61ED91C7` FOREIGN KEY (`placeAllId`) REFERENCES `tblPlaces` (`PLACEALLID`),
  CONSTRAINT `FK6E450BABF978D0E` FOREIGN KEY (`peopleId`) REFERENCES `tblPeople` (`PERSONID`),
  CONSTRAINT `FK6E450BABFACD4B7D` FOREIGN KEY (`entryId`) REFERENCES `tblDocuments` (`ENTRYID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dump della struttura di tabella bia.tblUserMarkedList
CREATE TABLE IF NOT EXISTS `tblUserMarkedList` (
  `idMarkedList` int(11) NOT NULL AUTO_INCREMENT,
  `dateCreated` datetime DEFAULT NULL,
  `dateLastUpdate` datetime DEFAULT NULL,
  `description` varchar(100) NOT NULL,
  `account` varchar(25) NOT NULL,
  PRIMARY KEY (`idMarkedList`),
  KEY `FK252B073F42F8715` (`account`),
  CONSTRAINT `FK_tblUserMarkedList_tblUser` FOREIGN KEY (`account`) REFERENCES `tblUser` (`account`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dump della struttura di tabella bia.tblUserMarkedListElement
CREATE TABLE IF NOT EXISTS `tblUserMarkedListElement` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dateCreated` datetime DEFAULT NULL,
  `entryId` int(11) DEFAULT NULL,
  `peopleId` int(11) DEFAULT NULL,
  `placeAllId` int(11) DEFAULT NULL,
  `idMarkedList` int(11) DEFAULT NULL,
  `summaryId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK968A2F09160EE057` (`idMarkedList`),
  KEY `FK968A2F091F274476` (`peopleId`),
  KEY `FK968A2F09654CC8E5` (`entryId`),
  KEY `FK968A2F096F0B8458` (`summaryId`),
  KEY `FK968A2F09FF554D5F` (`placeAllId`),
  CONSTRAINT `FK968A2F09160EE057` FOREIGN KEY (`idMarkedList`) REFERENCES `tblUserMarkedList` (`idMarkedList`),
  CONSTRAINT `FK968A2F091F274476` FOREIGN KEY (`peopleId`) REFERENCES `tblPeople` (`PERSONID`),
  CONSTRAINT `FK968A2F09654CC8E5` FOREIGN KEY (`entryId`) REFERENCES `tblDocuments` (`ENTRYID`),
  CONSTRAINT `FK968A2F096F0B8458` FOREIGN KEY (`summaryId`) REFERENCES `tblVolumes` (`SUMMARYID`),
  CONSTRAINT `FK968A2F09FF554D5F` FOREIGN KEY (`placeAllId`) REFERENCES `tblPlaces` (`PLACEALLID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dump della struttura di tabella bia.tblUserMessage
CREATE TABLE IF NOT EXISTS `tblUserMessage` (
  `messageId` int(11) NOT NULL AUTO_INCREMENT,
  `messageBody` varchar(4000) DEFAULT NULL,
  `messageReadedDate` datetime DEFAULT NULL,
  `recipient` varchar(100) DEFAULT NULL,
  `recipientStatus` int(11) DEFAULT NULL,
  `messageSendedDate` datetime DEFAULT NULL,
  `sender` varchar(100) DEFAULT NULL,
  `messageSubject` varchar(100) DEFAULT NULL,
  `parentId` int(11) DEFAULT NULL,
  `account` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`messageId`),
  KEY `FK6FF0271E27B05944` (`parentId`),
  KEY `FK6FF0271EDD8DAFAC` (`parentId`),
  KEY `FK6FF0271EA4C915AD` (`account`),
  CONSTRAINT `FK_tblUserMessage_tblUser` FOREIGN KEY (`account`) REFERENCES `tblUser` (`account`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK6FF0271EDD8DAFAC` FOREIGN KEY (`parentId`) REFERENCES `tblUserMessage` (`messageId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dump della struttura di tabella bia.tblUserPersonalNotes
CREATE TABLE IF NOT EXISTS `tblUserPersonalNotes` (
  `idPersonalNotes` int(11) NOT NULL AUTO_INCREMENT,
  `dateCreated` datetime DEFAULT NULL,
  `lastUpdate` datetime DEFAULT NULL,
  `personalNotes` longtext,
  `account` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`idPersonalNotes`),
  KEY `FK14AE5A38A4C915AD` (`account`),
  CONSTRAINT `FK_tblUserPersonalNotes_tblUser` FOREIGN KEY (`account`) REFERENCES `tblUser` (`account`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK14AE5A38A4C915AD` FOREIGN KEY (`account`) REFERENCES `tblUser` (`account`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dump della struttura di tabella bia.tblUserRole
CREATE TABLE IF NOT EXISTS `tblUserRole` (
  `userRoleId` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(50) DEFAULT NULL,
  `authority` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`userRoleId`),
  KEY `FK7C9488BF1148367E` (`authority`),
  KEY `FK7C9488BFF42F8715` (`account`),
  CONSTRAINT `FK_tblUserRole_tblUser` FOREIGN KEY (`account`) REFERENCES `tblUser` (`account`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK7C9488BF1148367E` FOREIGN KEY (`authority`) REFERENCES `tblUserAuthority` (`authority`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dump della struttura di tabella bia.tblVettingHistory
CREATE TABLE IF NOT EXISTS `tblVettingHistory` (
  `idVettingHistory` int(11) NOT NULL AUTO_INCREMENT,
  `dateAndTime` datetime NOT NULL,
  `account` varchar(50) DEFAULT NULL,
  `action` varchar(20) DEFAULT NULL,
  `category` varchar(20) DEFAULT NULL,
  `description` varchar(100) NOT NULL,
  `entryId` int(11) DEFAULT NULL,
  `forumId` int(11) DEFAULT NULL,
  `forumPostId` int(11) DEFAULT NULL,
  `forumTopicId` int(11) DEFAULT NULL,
  `peopleId` int(11) DEFAULT NULL,
  `placeAllId` int(11) DEFAULT NULL,
  `summaryId` int(11) DEFAULT NULL,
  PRIMARY KEY (`idVettingHistory`),
  KEY `FKB4C385F1D331ACC` (`forumTopicId`),
  KEY `FKB4C385FF978D0E` (`peopleId`),
  KEY `FKB4C385F4A89B628` (`forumId`),
  KEY `FKB4C385FFACD4B7D` (`entryId`),
  KEY `FKB4C385F5F7BCCF0` (`summaryId`),
  KEY `FKB4C385FF1DB6128` (`forumPostId`),
  KEY `FKB4C385FA4C915AD` (`account`),
  KEY `FKB4C385F61ED91C7` (`placeAllId`),
  CONSTRAINT `FK_tblVettingHistory_tblUser` FOREIGN KEY (`account`) REFERENCES `tblUser` (`account`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FKB4C385F1D331ACC` FOREIGN KEY (`forumTopicId`) REFERENCES `tblForumTopic` (`topicId`),
  CONSTRAINT `FKB4C385F4A89B628` FOREIGN KEY (`forumId`) REFERENCES `tblForum` (`forumId`),
  CONSTRAINT `FKB4C385F5F7BCCF0` FOREIGN KEY (`summaryId`) REFERENCES `tblVolumes` (`SUMMARYID`),
  CONSTRAINT `FKB4C385F61ED91C7` FOREIGN KEY (`placeAllId`) REFERENCES `tblPlaces` (`PLACEALLID`),
  CONSTRAINT `FKB4C385FF1DB6128` FOREIGN KEY (`forumPostId`) REFERENCES `tblForumPost` (`postId`),
  CONSTRAINT `FKB4C385FF978D0E` FOREIGN KEY (`peopleId`) REFERENCES `tblPeople` (`PERSONID`),
  CONSTRAINT `FKB4C385FFACD4B7D` FOREIGN KEY (`entryId`) REFERENCES `tblDocuments` (`ENTRYID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dump della struttura di tabella bia.tblVolumes
CREATE TABLE IF NOT EXISTS `tblVolumes` (
  `SUMMARYID` int(10) NOT NULL AUTO_INCREMENT,
  `SERIESREFNUM` int(10) DEFAULT NULL,
  `VOLNUM` int(10) DEFAULT NULL,
  `VOLLETEXT` varchar(1) DEFAULT NULL,
  `RESID` varchar(50) DEFAULT NULL,
  `DATECREATED` datetime DEFAULT NULL,
  `VOLTOBEVETTEDDATE` datetime DEFAULT NULL,
  `VOLTOBEVETTED` tinyint(1) NOT NULL,
  `VOLVETID` varchar(50) DEFAULT NULL,
  `VOLVETBEGINS` datetime DEFAULT NULL,
  `VOLVETTED` tinyint(1) NOT NULL,
  `VOLVETTEDDATE` datetime DEFAULT NULL,
  `STATBOX` varchar(50) DEFAULT NULL,
  `STARTYEAR` smallint(5) DEFAULT NULL,
  `STARTMONTH` varchar(50) DEFAULT NULL,
  `STARTMONTHNUM` int(10) DEFAULT NULL,
  `STARTDAY` tinyint(3) unsigned DEFAULT NULL,
  `STARTDATE` int(11) DEFAULT NULL,
  `ENDYEAR` smallint(5) DEFAULT NULL,
  `ENDMONTH` varchar(50) DEFAULT NULL,
  `ENDMONTHNUM` int(10) DEFAULT NULL,
  `ENDDAY` tinyint(3) unsigned DEFAULT NULL,
  `ENDDATE` int(11) DEFAULT NULL,
  `DATENOTES` longtext,
  `SENDERS` longtext,
  `RECIPS` longtext,
  `CCONTEXT` longtext,
  `FOLIOCOUNT` varchar(50) DEFAULT NULL,
  `BOUND` tinyint(1) NOT NULL,
  `FOLSNUMBRD` tinyint(1) NOT NULL,
  `OLDALPHAINDEX` tinyint(1) NOT NULL,
  `CCONDITION` longtext,
  `ITALIAN` tinyint(1) NOT NULL,
  `SPANISH` tinyint(1) NOT NULL,
  `ENGLISH` tinyint(1) NOT NULL,
  `LATIN` tinyint(1) NOT NULL,
  `GERMAN` tinyint(1) NOT NULL,
  `FRENCH` tinyint(1) NOT NULL,
  `OTHERLANG` varchar(50) DEFAULT NULL,
  `CIPHER` tinyint(1) NOT NULL,
  `CIPHERNOTES` varchar(255) DEFAULT NULL,
  `ORGNOTES` longtext,
  `STAFFMEMO` longtext,
  `PRINTEDDRAWINGS` tinyint(1) DEFAULT NULL,
  `PRINTEDMATERIAL` tinyint(1) DEFAULT NULL,
  `DIGITIZED` tinyint(4) NOT NULL DEFAULT '0',
  `LOGICALDELETE` tinyint(4) NOT NULL DEFAULT '0',
  `INVSOMDESC` longtext,
  `LASTUPDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`SUMMARYID`),
  KEY `{D3E3C3AA-55B8-4DDA-A19C-1AE6C7` (`SERIESREFNUM`),
  KEY `ENDMONTHNUM` (`ENDMONTHNUM`),
  KEY `RESID` (`RESID`),
  KEY `SERIESREFNUM` (`SERIESREFNUM`),
  KEY `STARTMONTHNUM` (`STARTMONTHNUM`),
  KEY `VOLNUM` (`VOLNUM`),
  KEY `VOLVETID` (`VOLVETID`),
  KEY `FKCF6342FBBB25084B` (`STARTMONTHNUM`),
  KEY `FKCF6342FB444D0887` (`SERIESREFNUM`),
  KEY `FKCF6342FB1E85BFC4` (`ENDMONTHNUM`),
  KEY `FKCF6342FB1DBD4CB3` (`STARTMONTHNUM`),
  KEY `FKCF6342FB5EDCD8EF` (`SERIESREFNUM`),
  KEY `FKCF6342FB811E042C` (`ENDMONTHNUM`),
  CONSTRAINT `FKCF6342FB1DBD4CB3` FOREIGN KEY (`STARTMONTHNUM`) REFERENCES `tblMonths` (`MONTHNUM`),
  CONSTRAINT `FKCF6342FB5EDCD8EF` FOREIGN KEY (`SERIESREFNUM`) REFERENCES `tblSeriesList` (`SERIESREFNUM`),
  CONSTRAINT `FKCF6342FB811E042C` FOREIGN KEY (`ENDMONTHNUM`) REFERENCES `tblMonths` (`MONTHNUM`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;


ALTER TABLE `docsources`.`persistent_logins` ENGINE = InnoDB ;
ALTER TABLE `docsources`.`tblAccessLog` ENGINE = InnoDB ;
ALTER TABLE `docsources`.`tblAccessLog` CHANGE COLUMN `informations` `informations` LONGTEXT NULL DEFAULT NULL AFTER `httpMethod`;
ALTER TABLE `docsources`.`tblPRCLink`RENAME TO `docsources`.`tblPrcLink`;

ALTER TABLE `docsources`.`tblEPLTOLink` CHANGE COLUMN `EPLTOID#` `EPLTOID` INT(10) NOT NULL AUTO_INCREMENT FIRST;
-- ALTER TABLE `docsources`.`tblEPLTOLink` DROP COLUMN `EPLTOID`;

-- MYSQL DATABASE STRUCTURE NORMALIZATION PATCH

-- DOCUMENTS
-- Doc Typology
ALTER TABLE `docsources`.`tblDocuments` ADD COLUMN `DOCTYPOLOGY` VARCHAR(250) AFTER `GRAPHIC`;
-- Folio transcribes Num
ALTER TABLE `docsources`.`tblDocuments` ADD COLUMN `TRANSCRIBEFOLIONUM` INT(10) AFTER `FOLIOMOD`;
-- Folio transcribes Mod
ALTER TABLE `docsources`.`tblDocuments` ADD COLUMN `TRANSCRIBEFOLIOMOD` VARCHAR(15) AFTER `TRANSCRIBEFOLIONUM`;
-- Change doc date fields order
ALTER TABLE `docsources`.`tblDocuments` CHANGE COLUMN `DOCYEAR` `DOCYEAR` INT(10) NULL DEFAULT NULL  AFTER `UNPAGED` , CHANGE COLUMN `DOCMONTHNUM` `DOCMONTHNUM` INT(10) NULL DEFAULT NULL  AFTER `DOCYEAR` , CHANGE COLUMN `DOCDAY` `DOCDAY` INT(10) NULL DEFAULT NULL  AFTER `DOCMONTHNUM` ;
-- Doc Date for Lucene indexing 
ALTER TABLE `docsources`.`tblDocuments` ADD COLUMN `DOCDATE` INT(11) AFTER `DOCDAY`;
-- Year Modern : type is int (4)
ALTER TABLE `docsources`.`tblDocuments` CHANGE COLUMN `YEARMODERN` `YEARMODERN` INT(4) NULL DEFAULT NULL  ;
-- Sortable date : type size decreased from 50 to 10
-- ALTER TABLE `docsources`.`tblDocuments` CHANGE COLUMN `SORTABLEDATE` `SORTABLEDATE` VARCHAR(10) NULL DEFAULT NULL  ;
-- Logical delete
ALTER TABLE `docsources`.`tblDocuments` ADD COLUMN `LOGICALDELETE` TINYINT(4) NOT NULL DEFAULT '0' AFTER `DOCTYPOLOGY`;

-- PEOPLE
-- Change born date fields and death date fields order
ALTER TABLE `docsources`.`tblPeople` CHANGE COLUMN `BYEAR` `BYEAR` DOUBLE NULL DEFAULT NULL  AFTER `ACTIVEEND` , CHANGE COLUMN `DYEAR` `DYEAR` DOUBLE NULL DEFAULT NULL  AFTER `BPLACE` ;
-- Born Date for Lucene indexing 
ALTER TABLE `docsources`.`tblPeople` ADD COLUMN `BORNDATE` INT(11) AFTER `BDAY`;
-- Death Date for Lucene indexing 
ALTER TABLE `docsources`.`tblPeople` ADD COLUMN `DEATHDATE` INT(11) AFTER `DDAY`;
-- Logical delete
ALTER TABLE `docsources`.`tblPeople` ADD COLUMN `LOGICALDELETE` TINYINT(4) NOT NULL DEFAULT '0' AFTER `LastUpdate`;

-- PLACES
-- GEOGKEY_CHILDREN
-- ALTER TABLE `docsources`.`tblPlaces` ADD COLUMN `GEOGKEY_CHILDREN` LONGTEXT DEFAULT NULL  AFTER `OTHER_FLAGS`;
-- GEO_ID_ENCODING
ALTER TABLE `docsources`.`tblPlaces` ADD COLUMN `GEO_ID_ENCODING` VARCHAR(10) DEFAULT NULL AFTER `GEOGKEY_CHILDREN`;
-- Logical delete
ALTER TABLE `docsources`.`tblPlaces` ADD COLUMN `LOGICALDELETE` TINYINT(4) NOT NULL DEFAULT '0' AFTER `GEO_ID_ENCODING`;

-- VOLUMES
-- Start Date for Lucene indexing 
ALTER TABLE `docsources`.`tblVolumes` ADD COLUMN `STARTDATE` INT(11) AFTER `STARTDAY`;
-- End Date for Lucene indexing 
ALTER TABLE `docsources`.`tblVolumes` ADD COLUMN `ENDDATE` INT(11) AFTER `ENDDAY`;
-- Context to CContext (context is a reserved key on Mysql)
ALTER TABLE `docsources`.`tblVolumes` CHANGE COLUMN `CONTEXT` `CCONTEXT` LONGTEXT CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL, CHANGE COLUMN `CONDITION` `CCONDITION` LONGTEXT CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL;
-- Printed Drawings
ALTER TABLE `docsources`.`tblVolumes` ADD COLUMN `PRINTEDDRAWINGS` TINYINT(1) AFTER `STAFFMEMO`;
-- Printed Material
ALTER TABLE `docsources`.`tblVolumes` ADD COLUMN `PRINTEDMATERIAL` TINYINT(1) AFTER `PRINTEDDRAWINGS`;
-- Digitized column.
ALTER TABLE `docsources`.`tblVolumes` ADD COLUMN `DIGITIZED` TINYINT(4) NOT NULL DEFAULT '0' AFTER `PRINTEDMATERIAL`;
-- Logical delete.
ALTER TABLE `docsources`.`tblVolumes` ADD COLUMN `LOGICALDELETE` TINYINT(4) NOT NULL DEFAULT '0' AFTER `DIGITIZED`;

-- FOREIGN KEYS BEGIN
ALTER TABLE tblActivationUser DROP FOREIGN KEY FK_tblActivationUser_tblUser;
ALTER TABLE tblActivationUser ADD CONSTRAINT FK_tblActivationUser_tblUser FOREIGN KEY (account) REFERENCES tblUser (account)  ON UPDATE CASCADE ON DELETE CASCADE;
ALTER TABLE tblAnnotations DROP FOREIGN KEY FK_tblAnnotations_tblUser;
ALTER TABLE tblAnnotations ADD CONSTRAINT FK_tblAnnotations_tblUser FOREIGN KEY (account) REFERENCES tblUser (account)  ON UPDATE CASCADE ON DELETE CASCADE;
ALTER TABLE tblApprovationUser DROP FOREIGN KEY FK_tblApprovationUser_tblUser;
ALTER TABLE tblApprovationUser ADD CONSTRAINT FK_tblApprovationUser_tblUser FOREIGN KEY (account) REFERENCES tblUser (account)  ON UPDATE CASCADE ON DELETE CASCADE;
ALTER TABLE tblForumPost DROP FOREIGN KEY FK_tblForumPost_tblUser;
ALTER TABLE tblForumPost ADD CONSTRAINT FK_tblForumPost_tblUser FOREIGN KEY (account) REFERENCES tblUser (account)  ON UPDATE CASCADE ON DELETE CASCADE;
ALTER TABLE tblForumTopic DROP FOREIGN KEY FK_tblForumTopic_tblUser;
ALTER TABLE tblForumTopic ADD CONSTRAINT FK_tblForumTopic_tblUser FOREIGN KEY (account) REFERENCES tblUser (account)  ON UPDATE CASCADE ON DELETE CASCADE;
ALTER TABLE tblForumTopicWatch DROP FOREIGN KEY FK_tblForumTopicWatch_tblUser;
ALTER TABLE tblForumTopicWatch ADD CONSTRAINT FK_tblForumTopicWatch_tblUser FOREIGN KEY (account) REFERENCES tblUser (account)  ON UPDATE CASCADE ON DELETE CASCADE;
ALTER TABLE tblUser DROP FOREIGN KEY FK_tblUser_tblUser;
ALTER TABLE tblUser	ADD CONSTRAINT FK_tblUser_tblUser FOREIGN KEY (approvedBy) REFERENCES tblUser (account)  ON UPDATE CASCADE ON DELETE CASCADE;
ALTER TABLE tblUserHistory DROP FOREIGN KEY FK_tblUserHistory_tblUser;
ALTER TABLE tblUserHistory	ADD CONSTRAINT FK_tblUserHistory_tblUser FOREIGN KEY (account) REFERENCES tblUser (account)  ON UPDATE CASCADE ON DELETE CASCADE;
ALTER TABLE tblUserMarkedList DROP FOREIGN KEY FK_tblUserMarkedList_tblUser;
ALTER TABLE tblUserMarkedList	ADD CONSTRAINT FK_tblUserMarkedList_tblUser FOREIGN KEY (account) REFERENCES tblUser (account)  ON UPDATE CASCADE ON DELETE CASCADE;
ALTER TABLE tblUserMessage DROP FOREIGN KEY FK_tblUserMessage_tblUser;
ALTER TABLE tblUserMessage	ADD CONSTRAINT FK_tblUserMessage_tblUser FOREIGN KEY (account) REFERENCES tblUser (account)  ON UPDATE CASCADE ON DELETE CASCADE;
ALTER TABLE tblUserPersonalNotes DROP FOREIGN KEY FK_tblUserPersonalNotes_tblUser;
ALTER TABLE tblUserPersonalNotes	ADD CONSTRAINT FK_tblUserPersonalNotes_tblUser FOREIGN KEY (account) REFERENCES tblUser (account)  ON UPDATE CASCADE ON DELETE CASCADE;
ALTER TABLE tblUserRole DROP FOREIGN KEY FK_tblUserRole_tblUser;
ALTER TABLE tblUserRole	ADD CONSTRAINT FK_tblUserRole_tblUser FOREIGN KEY (account) REFERENCES tblUser (account)  ON UPDATE CASCADE ON DELETE CASCADE;
ALTER TABLE tblVettingHistory DROP FOREIGN KEY FK_tblVettingHistory_tblUser;
ALTER TABLE tblVettingHistory	ADD CONSTRAINT FK_tblVettingHistory_tblUser FOREIGN KEY (account) REFERENCES tblUser (account)  ON UPDATE CASCADE ON DELETE CASCADE;
-- FOREIGN KEYS END


-- MYSQL DATABASE DATA NORMALIZATION PATCH

--Creation user
update tblDocuments  a set a.createdBy = (select b.account from tblUser b where b.initials = a.ResID);
update tblPeople  a set a.createdBy = (select b.account from tblUser b where b.initials = a.ResID);
update tblPlaces  a set a.createdBy = (select b.account from tblUser b where b.initials = a.ResID);
update tblVolumes  a set a.createdBy = (select b.account from tblUser b where b.initials = a.ResID);

--Last update user
update tblDocuments  a set a.lastUpdateBy = (select b.account from tblUser b where b.initials = a.ResID);
update tblPeople  a set a.lastUpdateBy = (select b.account from tblUser b where b.initials = a.ResID);
update tblPlaces  a set a.lastUpdateBy = (select b.account from tblUser b where b.initials = a.ResID);
update tblVolumes  a set a.lastUpdateBy = (select b.account from tblUser b where b.initials = a.ResID);

-- DOCUMENTS :
-- docMonthNum 0 or 13 must be setted to null  (Foreign Keys Checks)
update bia.tblDocuments set docMonthNum = null where docMonthNum = 0;
update bia.tblDocuments set docMonthNum = null where docMonthNum = 13;
-- Sender people linked to invalid people (Foreign Keys Checks)
update bia.tblDocuments set sendID = null where sendID not in (select personId from bia.tblPeople);
-- Recipient people linked to invalid people (Foreign Keys Checks)
update bia.tblDocuments set recipID = null where recipID not in (select personId from bia.tblPeople);
-- Sender place linked to invalid place (Foreign Keys Checks)
update bia.tblDocuments set sendLocplall = null where sendLocplall not in (select placeAllId from bia.tblPlaces);
-- Recipient place linked to invalid place (Foreign Keys Checks)
update bia.tblDocuments set recipLocplall = null where recipLocplall not in (select placeAllId from bia.tblPlaces);
-- FolioMod must be setted to null instead of empty string
update bia.tblDocuments set folioMod=null where folioMod='';

-- IMAGES
-- Folio type : this update sets the correct type by imageName field (example from filza n.7 : '0536_C_333_R.tif')
update bia.tblImages set imageType = substr(imageName, 6,1);
-- Recto Verso : 
update bia.tblImages set imageRectoVerso = substring(SUBSTRING_INDEX(imageName, '_', -1),1,1); 

-- PARENTS
-- Creating father records
insert into bia.tblParents select null, fatherId, personId, dateCreated, now() from bia.tblPeople where fatherId is not null;
-- Creating mother records
insert into bia.tblParents select null, motherId, personId, now(), now() from bia.tblPeople where motherId is not null;

-- PEOPLE
-- Gender types patch to allow correct use of numeration type Gender
update bia.tblPeople set gender = 'M' where gender = 'm';
update bia.tblPeople set gender = 'F' where gender = 'f';
update bia.tblPeople set gender = 'X' where gender = 'x';
update bia.tblPeople set gender = null where gender = '';
-- Born place linked to invalid place
update bia.tblPeople set bPlaceId = null where (bPlaceId not in (select placeAllId from bia.tblPlaces));
-- Death place linked to invalid place
update bia.tblPeople set dPlaceId = null where (dPlaceId not in (select placeAllId from bia.tblPlaces));

-- Marriages linked to invalid people
delete from bia.tblMarriages where (husbandId not in (select personId from bia.tblPeople) ) or ( wifeId not in (select personId from bia.tblPeople));
-- PrcLink linked to invalid RoleCat
delete from bia.tblPrcLink where RoleCatId not in (select roleCatID from bia.tblRoleCats);
-- Birth month num 0 or 13 must be setted to null
update bia.tblPeople set bMonthNum = null where bMonthNum = 0;
update bia.tblPeople set bMonthNum = null where bMonthNum = 13;
-- Death month num 0 or 13 must be setted to null
update bia.tblPeople set dMonthNum = null where dMonthNum = 0;
update bia.tblPeople set dMonthNum = null where dMonthNum = 13;
-- lastUpdate cannot be null, we update this to dateCreated
update bia.tblPeople set lastUpdate = dateCreated where lastUpdate is null;

-- linked alternative names is an enumeration with first letter in upper case
update bia.tblAltNames set nameType = 'Appellative' where lower(nameType) = 'appellative';
update bia.tblAltNames set nameType = 'Family' where lower(nameType) = 'family';
update bia.tblAltNames set nameType = 'Given' where lower(nameType) = 'given';
update bia.tblAltNames set nameType = 'Maiden' where lower(nameType) = 'maiden';
update bia.tblAltNames set nameType = 'Married' where lower(nameType) = 'married';
update bia.tblAltNames set nameType = 'Patronymic' where lower(nameType) = 'patronymic';
update bia.tblAltNames set nameType = 'SearchName' where lower(nameType) = 'searchname';

-- Preferred Role must be a boolean column (tinyint)
update bia.tblPOLink set prtag = 1 where prtag =-1;
-- MonthNum in linked TitleOrOccupation must be null if not present
update bia.tblPOLink set startMonthNum = null where startMonthNum = 0;
update bia.tblPOLink set endMonthNum = null where endMonthNum = 0;
-- Set startMonthNum from the startMonth field
update bia.tblPOLink set startMonthNum = null where startMonth is null;
update bia.tblPOLink set startMonthNum = 1 where startMonth like 'January';
update bia.tblPOLink set startMonthNum = 2 where startMonth like 'February';
update bia.tblPOLink set startMonthNum = 3 where startMonth like 'March';
update bia.tblPOLink set startMonthNum = 4 where startMonth like 'April';
update bia.tblPOLink set startMonthNum = 5 where startMonth like 'May';
update bia.tblPOLink set startMonthNum = 6 where startMonth like 'June';
update bia.tblPOLink set startMonthNum = 7 where startMonth like 'July';
update bia.tblPOLink set startMonthNum = 8 where startMonth like 'August';
update bia.tblPOLink set startMonthNum = 9 where startMonth like 'September';
update bia.tblPOLink set startMonthNum = 10 where startMonth like 'October';
update bia.tblPOLink set startMonthNum = 11 where startMonth like 'November';
update bia.tblPOLink set startMonthNum = 12 where startMonth like 'December';
-- Set endMonthNum from the endMonth field
update bia.tblPOLink set endMonthNum = null where endMonth is null;
update bia.tblPOLink set endMonthNum = 1 where endMonth like 'January';
update bia.tblPOLink set endMonthNum = 2 where endMonth like 'February';
update bia.tblPOLink set endMonthNum = 3 where endMonth like 'March';
update bia.tblPOLink set endMonthNum = 4 where endMonth like 'April';
update bia.tblPOLink set endMonthNum = 5 where endMonth like 'May';
update bia.tblPOLink set endMonthNum = 6 where endMonth like 'June';
update bia.tblPOLink set endMonthNum = 7 where endMonth like 'July';
update bia.tblPOLink set endMonthNum = 8 where endMonth like 'August';
update bia.tblPOLink set endMonthNum = 9 where endMonth like 'September';
update bia.tblPOLink set endMonthNum = 10 where endMonth like 'October';
update bia.tblPOLink set endMonthNum = 11 where endMonth like 'November';
update bia.tblPOLink set endMonthNum = 12 where endMonth like 'December';

-- VOLUMES 
-- start Document Month num 0 or 13 must be setted to null
update bia.tblVolumes set startMonthNum = null where startMonthNum = 0;
update bia.tblVolumes set startMonthNum = null where startMonthNum = 13;
-- end Document Month num 0 or 13 must be setted to null
update bia.tblVolumes set endMonthNum = null where endMonthNum = 0;
update bia.tblVolumes set endMonthNum = null where endMonthNum = 13;

-- lastUpdate cannot be null, we update this to dateCreated
update bia.tblVolumes set lastUpdate = dateCreated where lastUpdate is null;

-- PlaceType
delete from bia.tblPlaceType;
insert into bia.tblPlaceType (description) select distinct(plType) from bia.tblPlaces order by 1 asc;

-- Lucene Indexing (START) : Integer Date Fields Population  
-- Document docDate
update bia.tblDocuments set docDate=concat(docYear,SUBSTRING(concat('0',docMonthNum) FROM -2 FOR 2),SUBSTRING(concat('0',docDay) FROM -2 FOR 2)) where docYear is not null and docMonthNum is not null and docDay is not null;
update bia.tblDocuments set docDate=concat(docYear,SUBSTRING(concat('0',docMonthNum) FROM -2 FOR 2),'01') where docYear is not null and docMonthNum is not null and docDay is null;
update bia.tblDocuments set docDate=concat(docYear,'01',SUBSTRING(concat('0',docDay) FROM -2 FOR 2)) where docYear is not null and docMonthNum is null and docDay is not null;
update bia.tblDocuments set docDate=concat(docYear,'01','01') where docYear is not null and docMonthNum is null and docDay is null;
update bia.tblDocuments set docDate=concat('01',SUBSTRING(concat('0',docMonthNum) FROM -2 FOR 2),SUBSTRING(concat('0',docDay) FROM -2 FOR 2)) where docYear is null and docMonthNum is not null and docDay is not null;
update bia.tblDocuments set docDate=concat('01',SUBSTRING(concat('0',docMonthNum) FROM -2 FOR 2),'01') where docYear is null and docMonthNum is not null and docDay is null;
update bia.tblDocuments set docDate=concat('01','01',SUBSTRING(concat('0',docDay) FROM -2 FOR 2)) where docYear is null and docMonthNum is null and docDay is not null;
update bia.tblDocuments set docDate=concat('01','01','01') where docYear is null and docMonthNum is null and docDay is null;

-- Document sortableDateInt: this is not for lucene indexing. This field is important for order documents by date.
ALTER TABLE `docsources`.`tblDocuments` ADD COLUMN `SORTABLEDATEINT` INT(11) DEFAULT NULL AFTER `LOGICALDELETE`;
update bia.tblDocuments set sortableDateInt=concat(yearModern,SUBSTRING(concat('0',docMonthNum) FROM -2 FOR 2),SUBSTRING(concat('0',docDay) FROM -2 FOR 2)) where yearModern is not null and docMonthNum is not null and docDay is not null;
update bia.tblDocuments set sortableDateInt=concat(yearModern,SUBSTRING(concat('0',docMonthNum) FROM -2 FOR 2),'00') where yearModern is not null and docMonthNum is not null and docDay is null;
update bia.tblDocuments set sortableDateInt=concat(yearModern,'00',SUBSTRING(concat('0',docDay) FROM -2 FOR 2)) where yearModern is not null and docMonthNum is null and docDay is not null;
update bia.tblDocuments set sortableDateInt=concat(yearModern,'00','00') where yearModern is not null and docMonthNum is null and docDay is null;
update bia.tblDocuments set sortableDateInt=concat(docYear,SUBSTRING(concat('0',docMonthNum) FROM -2 FOR 2),SUBSTRING(concat('0',docDay) FROM -2 FOR 2)) where yearModern is null and docYear is not null and docMonthNum is not null and docDay is not null;
update bia.tblDocuments set sortableDateInt=concat(docYear,SUBSTRING(concat('0',docMonthNum) FROM -2 FOR 2),'00') where yearModern is null and docYear is not null and docMonthNum is not null and docDay is null;
update bia.tblDocuments set sortableDateInt=concat(docYear,'00',SUBSTRING(concat('0',docDay) FROM -2 FOR 2)) where yearModern is null and docYear is not null and docMonthNum is null and docDay is not null;
update bia.tblDocuments set sortableDateInt=concat(docYear,'00','00') where yearModern is null and docYear is not null and docMonthNum is null and docDay is null;
update bia.tblDocuments set sortableDateInt=concat('00',SUBSTRING(concat('0',docMonthNum) FROM -2 FOR 2),SUBSTRING(concat('0',docDay) FROM -2 FOR 2)) where yearModern is null and docYear is null and docMonthNum is not null and docDay is not null;
update bia.tblDocuments set sortableDateInt=concat('00',SUBSTRING(concat('0',docMonthNum) FROM -2 FOR 2),'00') where yearModern is null and docYear is null and docMonthNum is not null and docDay is null;
update bia.tblDocuments set sortableDateInt=concat('00','00',SUBSTRING(concat('0',docDay) FROM -2 FOR 2)) where yearModern is null and docYear is null and docMonthNum is null and docDay is not null;
update bia.tblDocuments set sortableDateInt=concat('00','00','00') where yearModern is null and docYear is null and docMonthNum is null and docDay is null;

-- People bornDate
update bia.tblPeople set bornDate=concat(bYear,SUBSTRING(concat('0',bMonthNum) FROM -2 FOR 2),SUBSTRING(concat('0',bDay) FROM -2 FOR 2)) where bYear is not null and bMonthNum is not null and bDay is not null;
update bia.tblPeople set bornDate=concat(bYear,SUBSTRING(concat('0',bMonthNum) FROM -2 FOR 2),'01') where bYear is not null and bMonthNum is not null and bDay is null;
update bia.tblPeople set bornDate=concat(bYear,'01',SUBSTRING(concat('0',bDay) FROM -2 FOR 2)) where bYear is not null and bMonthNum is null and bDay is not null;
update bia.tblPeople set bornDate=concat(bYear,'01','01') where bYear is not null and bMonthNum is null and bDay is null;
update bia.tblPeople set bornDate=concat('01',SUBSTRING(concat('0',bMonthNum) FROM -2 FOR 2),SUBSTRING(concat('0',bDay) FROM -2 FOR 2)) where bYear is null and bMonthNum is not null and bDay is not null;
update bia.tblPeople set bornDate=concat('01',SUBSTRING(concat('0',bMonthNum) FROM -2 FOR 2),'01') where bYear is null and bMonthNum is not null and bDay is null;
update bia.tblPeople set bornDate=concat('01','01',SUBSTRING(concat('0',bDay) FROM -2 FOR 2)) where bYear is null and bMonthNum is null and bDay is not null;
update bia.tblPeople set bornDate=concat('01','01','01') where bYear is null and bMonthNum is null and bDay is null;

-- People deathDate
update bia.tblPeople set deathDate=concat(dYear,SUBSTRING(concat('0',dMonthNum) FROM -2 FOR 2),SUBSTRING(concat('0',dDay) FROM -2 FOR 2)) where dYear is not null and dMonthNum is not null and dDay is not null;
update bia.tblPeople set deathDate=concat(dYear,SUBSTRING(concat('0',dMonthNum) FROM -2 FOR 2),'01') where dYear is not null and dMonthNum is not null and dDay is null;
update bia.tblPeople set deathDate=concat(dYear,'01',SUBSTRING(concat('0',dDay) FROM -2 FOR 2)) where dYear is not null and dMonthNum is null and dDay is not null;
update bia.tblPeople set deathDate=concat(dYear,'01','01') where dYear is not null and dMonthNum is null and dDay is null;
update bia.tblPeople set deathDate=concat('01',SUBSTRING(concat('0',dMonthNum) FROM -2 FOR 2),SUBSTRING(concat('0',dDay) FROM -2 FOR 2)) where dYear is null and dMonthNum is not null and dDay is not null;
update bia.tblPeople set deathDate=concat('01',SUBSTRING(concat('0',dMonthNum) FROM -2 FOR 2),'01') where dYear is null and dMonthNum is not null and dDay is null;
update bia.tblPeople set deathDate=concat('01','01',SUBSTRING(concat('0',dDay) FROM -2 FOR 2)) where dYear is null and dMonthNum is null and dDay is not null;
update bia.tblPeople set deathDate=concat('01','01','01') where dYear is null and dMonthNum is null and dDay is null;

-- Volume startDate
update bia.tblVolumes set startDate=concat(startYear,SUBSTRING(concat('0',startMonthNum) FROM -2 FOR 2),SUBSTRING(concat('0',startDay) FROM -2 FOR 2)) where startYear is not null and startMonthNum is not null and startDay is not null;
update bia.tblVolumes set startDate=concat(startYear,SUBSTRING(concat('0',startMonthNum) FROM -2 FOR 2),'01') where startYear is not null and startMonthNum is not null and startDay is null;
update bia.tblVolumes set startDate=concat(startYear,'01',SUBSTRING(concat('0',startDay) FROM -2 FOR 2)) where startYear is not null and startMonthNum is null and startDay is not null;
update bia.tblVolumes set startDate=concat(startYear,'01','01') where startYear is not null and startMonthNum is null and startDay is null;
update bia.tblVolumes set startDate=concat('01',SUBSTRING(concat('0',startMonthNum) FROM -2 FOR 2),SUBSTRING(concat('0',startDay) FROM -2 FOR 2)) where startYear is null and startMonthNum is not null and startDay is not null;
update bia.tblVolumes set startDate=concat('01',SUBSTRING(concat('0',startMonthNum) FROM -2 FOR 2),'01') where startYear is null and startMonthNum is not null and startDay is null;
update bia.tblVolumes set startDate=concat('01','01',SUBSTRING(concat('0',startDay) FROM -2 FOR 2)) where startYear is null and startMonthNum is null and startDay is not null;
update bia.tblVolumes set startDate=concat('01','01','01') where startYear is null and startMonthNum is null and startDay is null;

-- Volume endDate
update bia.tblVolumes set endDate=concat(endYear,SUBSTRING(concat('0',endMonthNum) FROM -2 FOR 2),SUBSTRING(concat('0',endDay) FROM -2 FOR 2)) where endYear is not null and endMonthNum is not null and endDay is not null;
update bia.tblVolumes set endDate=concat(endYear,SUBSTRING(concat('0',endMonthNum) FROM -2 FOR 2),'01') where endYear is not null and endMonthNum is not null and endDay is null;
update bia.tblVolumes set endDate=concat(endYear,'01',SUBSTRING(concat('0',endDay) FROM -2 FOR 2)) where endYear is not null and endMonthNum is null and endDay is not null;
update bia.tblVolumes set endDate=concat(endYear,'01','01') where endYear is not null and endMonthNum is null and endDay is null;
update bia.tblVolumes set endDate=concat('01',SUBSTRING(concat('0',endMonthNum) FROM -2 FOR 2),SUBSTRING(concat('0',endDay) FROM -2 FOR 2)) where endYear is null and endMonthNum is not null and endDay is not null;
update bia.tblVolumes set endDate=concat('01',SUBSTRING(concat('0',endMonthNum) FROM -2 FOR 2),'01') where endYear is null and endMonthNum is not null and endDay is null;
update bia.tblVolumes set endDate=concat('01','01',SUBSTRING(concat('0',endDay) FROM -2 FOR 2)) where endYear is null and endMonthNum is null and endDay is not null;
update bia.tblVolumes set endDate=concat('01','01','01') where endYear is null and endMonthNum is null and endDay is null;
-- Lucene Indexing (END) : Integer Date Fields Population  

-- Logical delete (New boolean field to manage logical delete)
update bia.tblDocuments set logicalDelete=0;
update bia.tblPeople set logicalDelete=0;
update bia.tblPlaces set logicalDelete=0;
update bia.tblVolumes set logicalDelete=0;

-- Set Digitized information on volumes to false
update bia.tblVolumes set digitized=0;
-- These 2 updates will set rights digitized. MySQL does not allow to UPDATE or DELETE a table's data if you're simultaneously reading that same data with a subquery, so we use a temporary c1 view 
update bia.tblVolumes set digitized=true where summaryId in (select summaryId from (select distinct(summaryId) from bia.tblImages a, bia.tblVolumes b where a.volNum = b.volnum and a.volLetExt is null) as c1);
update bia.tblVolumes set digitized=true where summaryId in (select summaryId from (select distinct(summaryId) from bia.tblImages a, bia.tblVolumes b where a.volNum = b.volnum and a.volLetExt = b.volLetExt) as c1);

-- Adjusting missedNumbering in table Images
update bia.tblImages set missedNumbering = substr(imageName,12,3) where length(imageName)>16;

-- Adjusting active fields in People to permit use of projection with Hibernate-Search
update bia.tblPeople set activeStart = null where activeStart = '';
update bia.tblPeople set activeEnd = null where activeEnd = '';

-- Adjusting dateCreated and lastUpdate in table tblTitleOccsList
UPDATE `tblTitleOccsList` SET `DATECREATED`='2012-01-01 00:00:00', `LASTUPDATE`='2012-01-01 00:00:00'

-- COUNTRY DATA ENTRY (Table schema is based on ISO standard 3166 code lists http://www.iso.org/iso/list-en1-semic-3.txt)
delete from bia.tblCountries;
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('AFGHANISTAN', 'AF');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('ALAND ISLANDS', 'AX');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('ALBANIA', 'AL');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('ALGERIA', 'DZ');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('AMERICAN SAMOA', 'AS');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('ANDORRA', 'AD');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('ANGOLA', 'AO');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('ANGUILLA', 'AI');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('ANTARCTICA', 'AQ');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('ANTIGUA AND BARBUDA', 'AG');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('ARGENTINA', 'AR');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('ARMENIA', 'AM');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('ARUBA', 'AW');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('AUSTRALIA', 'AU');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('AUSTRIA', 'AT');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('AZERBAIJAN', 'AZ');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('BAHAMAS', 'BS');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('BAHRAIN', 'BH');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('BANGLADESH', 'BD');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('BARBADOS', 'BB');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('BELARUS', 'BY');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('BELGIUM', 'BE');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('BELIZE', 'BZ');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('BENIN', 'BJ');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('BERMUDA', 'BM');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('BHUTAN', 'BT');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('BOLIVIA, PLURINATIONAL STATE OF', 'BO');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('BOSNIA AND HERZEGOVINA', 'BA');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('BOTSWANA', 'BW');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('BOUVET ISLAND', 'BV');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('BRAZIL', 'BR');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('BRITISH INDIAN OCEAN TERRITORY', 'IO');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('BRUNEI DARUSSALAM', 'BN');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('BULGARIA', 'BG');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('BURKINA FASO', 'BF');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('BURUNDI', 'BI');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('CAMBODIA', 'KH');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('CAMEROON', 'CM');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('CANADA', 'CA');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('CAPE VERDE', 'CV');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('CAYMAN ISLANDS', 'KY');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('CENTRAL AFRICAN REPUBLIC', 'CF');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('CHAD', 'TD');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('CHILE', 'CL');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('CHINA', 'CN');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('CHRISTMAS ISLAND', 'CX');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('COCOS (KEELING) ISLANDS', 'CC');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('COLOMBIA', 'CO');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('COMOROS', 'KM');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('CONGO', 'CG');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('CONGO, THE DEMOCRATIC REPUBLIC OF THE', 'CD');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('COOK ISLANDS', 'CK');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('COSTA RICA', 'CR');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('C�TE D''IVOIRE', 'CI');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('CROATIA', 'HR');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('CUBA', 'CU');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('CYPRUS', 'CY');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('CZECH REPUBLIC', 'CZ');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('DENMARK', 'DK');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('DJIBOUTI', 'DJ');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('DOMINICA', 'DM');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('DOMINICAN REPUBLIC', 'DO');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('ECUADOR', 'EC');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('EGYPT', 'EG');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('EL SALVADOR', 'SV');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('EQUATORIAL GUINEA', 'GQ');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('ERITREA', 'ER');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('ESTONIA', 'EE');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('ETHIOPIA', 'ET');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('FALKLAND ISLANDS (MALVINAS)', 'FK');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('FAROE ISLANDS', 'FO');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('FIJI', 'FJ');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('FINLAND', 'FI');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('FRANCE', 'FR');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('FRENCH GUIANA', 'GF');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('FRENCH POLYNESIA', 'PF');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('FRENCH SOUTHERN TERRITORIES', 'TF');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('GABON', 'GA');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('GAMBIA', 'GM');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('GEORGIA', 'GE');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('GERMANY', 'DE');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('GHANA', 'GH');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('GIBRALTAR', 'GI');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('GREECE', 'GR');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('GREENLAND', 'GL');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('GRENADA', 'GD');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('GUADELOUPE', 'GP');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('GUAM', 'GU');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('GUATEMALA', 'GT');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('GUERNSEY', 'GG');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('GUINEA', 'GN');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('GUINEA-BISSAU', 'GW');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('GUYANA', 'GY');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('HAITI', 'HT');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('HEARD ISLAND AND MCDONALD ISLANDS', 'HM');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('HOLY SEE (VATICAN CITY STATE)', 'VA');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('HONDURAS', 'HN');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('HONG KONG', 'HK');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('HUNGARY', 'HU');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('ICELAND', 'IS');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('INDIA', 'IN');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('INDONESIA', 'ID');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('IRAN, ISLAMIC REPUBLIC OF', 'IR');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('IRAQ', 'IQ');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('IRELAND', 'IE');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('ISLE OF MAN', 'IM');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('ISRAEL', 'IL');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('ITALY', 'IT');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('JAMAICA', 'JM');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('JAPAN', 'JP');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('JERSEY', 'JE');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('JORDAN', 'JO');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('KAZAKHSTAN', 'KZ');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('KENYA', 'KE');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('KIRIBATI', 'KI');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('KOREA, DEMOCRATIC PEOPLE''S REPUBLIC OF', 'KP');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('KOREA, REPUBLIC OF', 'KR');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('KUWAIT', 'KW');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('KYRGYZSTAN', 'KG');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('LAO PEOPLE''S DEMOCRATIC REPUBLIC', 'LA');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('LATVIA', 'LV');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('LEBANON', 'LB');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('LESOTHO', 'LS');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('LIBERIA', 'LR');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('LIBYAN ARAB JAMAHIRIYA', 'LY');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('LIECHTENSTEIN', 'LI');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('LITHUANIA', 'LT');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('LUXEMBOURG', 'LU');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('MACAO', 'MO');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('MACEDONIA, THE FORMER YUGOSLAV REPUBLIC OF', 'MK');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('MADAGASCAR', 'MG');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('MALAWI', 'MW');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('MALAYSIA', 'MY');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('MALDIVES', 'MV');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('MALI', 'ML');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('MALTA', 'MT');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('MARSHALL ISLANDS', 'MH');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('MARTINIQUE', 'MQ');

INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('MAURITANIA', 'MR');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('MAURITIUS', 'MU');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('MAYOTTE', 'YT');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('MEXICO', 'MX');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('MICRONESIA, FEDERATED STATES OF', 'FM');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('MOLDOVA, REPUBLIC OF', 'MD');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('MONACO', 'MC');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('MONGOLIA', 'MN');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('MONTENEGRO', 'ME');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('MONTSERRAT', 'MS');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('MOROCCO', 'MA');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('MOZAMBIQUE', 'MZ');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('MYANMAR', 'MM');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('NAMIBIA', 'NA');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('NAURU', 'NR');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('NEPAL', 'NP');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('NETHERLANDS', 'NL');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('NETHERLANDS ANTILLES', 'AN');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('NEW CALEDONIA', 'NC');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('NEW ZEALAND', 'NZ');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('NICARAGUA', 'NI');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('NIGER', 'NE');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('NIGERIA', 'NG');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('NIUE', 'NU');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('NORFOLK ISLAND', 'NF');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('NORTHERN MARIANA ISLANDS', 'MP');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('NORWAY', 'NO');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('OMAN', 'OM');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('PAKISTAN', 'PK');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('PALAU', 'PW');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('PALESTINIAN TERRITORY, OCCUPIED', 'PS');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('PANAMA', 'PA');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('PAPUA NEW GUINEA', 'PG');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('PARAGUAY', 'PY');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('PERU', 'PE');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('PHILIPPINES', 'PH');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('PITCAIRN', 'PN');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('POLAND', 'PL');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('PORTUGAL', 'PT');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('PUERTO RICO', 'PR');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('QATAR', 'QA');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('R�UNION', 'RE');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('ROMANIA', 'RO');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('RUSSIAN FEDERATION', 'RU');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('RWANDA', 'RW');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('SAINT BARTH�LEMY', 'BL');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('SAINT HELENA, ASCENSION AND TRISTAN DA CUNHA', 'SH');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('SAINT KITTS AND NEVIS', 'KN');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('SAINT LUCIA', 'LC');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('SAINT MARTIN', 'MF');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('SAINT PIERRE AND MIQUELON', 'PM');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('SAINT VINCENT AND THE GRENADINES', 'VC');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('SAMOA', 'WS');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('SAN MARINO', 'SM');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('SAO TOME AND PRINCIPE', 'ST');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('SAUDI ARABIA', 'SA');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('SENEGAL', 'SN');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('SERBIA', 'RS');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('SEYCHELLES', 'SC');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('SIERRA LEONE', 'SL');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('SINGAPORE', 'SG');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('SLOVAKIA', 'SK');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('SLOVENIA', 'SI');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('SOLOMON ISLANDS', 'SB');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('SOMALIA', 'SO');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('SOUTH AFRICA', 'ZA');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('SOUTH GEORGIA AND THE SOUTH SANDWICH ISLANDS', 'GS');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('SPAIN', 'ES');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('SRI LANKA', 'LK');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('SUDAN', 'SD');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('SURINAME', 'SR');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('SVALBARD AND JAN MAYEN', 'SJ');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('SWAZILAND', 'SZ');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('SWEDEN', 'SE');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('SWITZERLAND', 'CH');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('SYRIAN ARAB REPUBLIC', 'SY');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('TAIWAN, PROVINCE OF CHINA', 'TW');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('TAJIKISTAN', 'TJ');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('TANZANIA, UNITED REPUBLIC OF', 'TZ');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('THAILAND', 'TH');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('TIMOR-LESTE', 'TL');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('TOGO', 'TG');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('TOKELAU', 'TK');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('TONGA', 'TO');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('TRINIDAD AND TOBAGO', 'TT');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('TUNISIA', 'TN');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('TURKEY', 'TR');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('TURKMENISTAN', 'TM');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('TURKS AND CAICOS ISLANDS', 'TC');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('TUVALU', 'TV');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('UGANDA', 'UG');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('UKRAINE', 'UA');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('UNITED ARAB EMIRATES', 'AE');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('UNITED KINGDOM', 'GB');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('UNITED STATES', 'US');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('UNITED STATES MINOR OUTLYING ISLANDS', 'UM');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('URUGUAY', 'UY');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('UZBEKISTAN', 'UZ');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('VANUATU', 'VU');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('VENEZUELA, BOLIVARIAN REPUBLIC OF', 'VE');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('VIET NAM', 'VN');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('VIRGIN ISLANDS, BRITISH', 'VG');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('VIRGIN ISLANDS, U.S.', 'VI');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('WALLIS AND FUTUNA', 'WF');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('WESTERN SAHARA', 'EH');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('YEMEN', 'YE');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('ZAMBIA', 'ZM');
INSERT INTO bia.tblCountries (NAME, CODE) VALUES ('ZIMBABWE', 'ZW');


-- insert into bia.tblAccessLog select * from docsources_2011.tblAccessLog;
-- insert into bia.tblUserComment select * from docsources_2011.tblUserComment;
-- insert into bia.tblUserHistory select * from docsources_2011.tblUserHistory;
-- insert into bia.tblUserMessage select * from docsources_2011.tblUserMessage;

truncate table bia.tblApplicationProperty;
INSERT INTO bia.tblApplicationProperty (id, help, value) VALUES ('forum.identifier.document', 'Forum container for document', '5');
INSERT INTO bia.tblApplicationProperty (id, help, value) VALUES ('forum.identifier.people', 'Forum container for people', '6');
INSERT INTO bia.tblApplicationProperty (id, help, value) VALUES ('forum.identifier.place', 'Forum container for place', '7');
INSERT INTO bia.tblApplicationProperty (id, help, value) VALUES ('forum.identifier.volume', 'Forum container for volume', '8');
INSERT INTO bia.tblApplicationProperty (id, help, value) VALUES ('forum.identifier.general', 'Forum container for general questions', '9');
INSERT INTO bia.tblApplicationProperty (id, help, value) VALUES ('forum.identifier.paleography', 'Forum container for paleography', '10');


INSERT INTO bia.tblApplicationProperty (id, help, value) VALUES ('iipimage.image.path', 'Remote server image path', '/data/tiled_mdp/');
INSERT INTO bia.tblApplicationProperty (id, help, value) VALUES ('iipimage.image.notavailable', 'Tif image name for not available', 'img_notAvailable.tif');
INSERT INTO bia.tblApplicationProperty (id, help, value) VALUES ('iipimage.reverseproxy.fcgi.path', 'IIPImage fcgi-bin path', '/fcgi-bin/iipsrv.fcgi');
INSERT INTO bia.tblApplicationProperty (id, help, value) VALUES ('iipimage.reverseproxy.host', 'IIPImage host name', 'iipimage.medici.org');
INSERT INTO bia.tblApplicationProperty (id, help, value) VALUES ('iipimage.reverseproxy.port', 'IIPImage listening port', '80');
INSERT INTO bia.tblApplicationProperty (id, help, value) VALUES ('iipimage.reverseproxy.protocol', 'IIPImage supported protocl', 'http');
INSERT INTO bia.tblApplicationProperty (id, help, value) VALUES ('iipimage.reverseproxy.version', 'Property must be filled with 0.9.8 or 0.9.9', '0.9.9');
INSERT INTO bia.tblApplicationProperty (id, help, value) VALUES ('recaptcha.domainName', 'reCAPTCHA will only work on this domain and subdomains. If you have more than one domain (or a staging server), you can create a new set of keys.', 'localhost');
INSERT INTO bia.tblApplicationProperty (id, help, value) VALUES ('recaptcha.privateKey', 'Use this when communicating between your server and our server. Be sure to keep it a secret.', '6LcA-LsSAAAAAInVIlSHKjxqKKre-40BOpb3Abcs ');
INSERT INTO bia.tblApplicationProperty (id, help, value) VALUES ('recaptcha.publicKey', 'Use this in the JavaScript code that is served to your users', '	6LcA-LsSAAAAAKYVTuOi0KQWArgUQwPtMQFqwe_6 ');
INSERT INTO bia.tblApplicationProperty (id, help, value) VALUES ('recaptcha.server', 'Google recaptcha remote server. http://api.recaptcha.net or https://api-secure.recaptcha.net', 'https://api-secure.recaptcha.net');
INSERT INTO bia.tblApplicationProperty (id, help, value) VALUES ('recaptcha.siteId', 'Site identifier', '314849700');
INSERT INTO bia.tblApplicationProperty (id, help, value) VALUES ('user.expiration.password.months', 'Number of months to calculate user password from last change password', '6');
INSERT INTO bia.tblApplicationProperty (id, help, value) VALUES ('user.expiration.user.months', 'Number of months to calculate user expiration date from registration date', '6');
INSERT INTO bia.tblApplicationProperty (id, help, value) VALUES ('user.maxBadLogin', 'How many bad login user can have before user lock', '5');
INSERT INTO bia.tblApplicationProperty (id, help, value) VALUES ('website.domain', '', 'bia.medici.org');
INSERT INTO bia.tblApplicationProperty (id, help, value) VALUES ('website.protocol', '', 'http');
INSERT INTO bia.tblApplicationProperty (id, help, value) VALUES ('website.contextPath', '', '/DocSources/');

INSERT INTO bia.tblApplicationProperty (id, help, value) VALUES ('schedone.coloreImmagine', '', 'RGB');
INSERT INTO bia.tblApplicationProperty (id, help, value) VALUES ('schedone.nomeFiles', '', 'Quattro cifre (numero progressivo immagine) _ coperta/C (carta)/G(guardia)/A (allegato)/R(repertorio) _ tre cifre (numero della carta) _ tre lettere (bis ter qua) _ R/V');
INSERT INTO bia.tblApplicationProperty (id, help, value) VALUES ('schedone.fondo', '', 'Mediceo del Principato');
INSERT INTO bia.tblApplicationProperty (id, help, value) VALUES ('schedone.istituto', '', 'Archivio di Stato di Firenze');
INSERT INTO bia.tblApplicationProperty (id, help, value) VALUES ('schedone.legatura', '', 'Esempio (cambiare): Filza con coperta attribuita in fase di restauro (1974)');
INSERT INTO bia.tblApplicationProperty (id, help, value) VALUES ('schedone.responsabileFotoRiproduzione', '', 'dr. Francesca Klein');
INSERT INTO bia.tblApplicationProperty (id, help, value) VALUES ('schedone.supporto', '', 'Cartaceo');
INSERT INTO bia.tblApplicationProperty (id, help, value) VALUES ('schedone.tipoRipresa', '', 'Da originale');

INSERT INTO bia.tblApplicationProperty (id, help, value) VALUES ('mail.debug', 'Dump mail on application log', 'true');
INSERT INTO bia.tblApplicationProperty (id, help, value) VALUES ('mail.from', '', 'medici.archive@gmail.com');
INSERT INTO bia.tblApplicationProperty (id, help, value) VALUES ('mail.server.host', 'Host Server for sending email', 'smtp.gmail.com');
INSERT INTO bia.tblApplicationProperty (id, help, value) VALUES ('mail.server.password', 'Password to logon on smtp', 'cosimo1537.a');
INSERT INTO bia.tblApplicationProperty (id, help, value) VALUES ('mail.server.port', 'Port Server for sending email', '587');
INSERT INTO bia.tblApplicationProperty (id, help, value) VALUES ('mail.server.username', 'User to logon on smtp', 'medici.archive@gmail.com');
INSERT INTO bia.tblApplicationProperty (id, help, value) VALUES ('mail.smtp.auth', 'Enable smtp authentication', 'true');
INSERT INTO bia.tblApplicationProperty (id, help, value) VALUES ('mail.smtp.starttls.enable', 'Enable smtp starttls', 'true');
INSERT INTO bia.tblApplicationProperty (id, help, value) VALUES ('mail.transport.protocol', 'Mail Transport protocol', 'smtp');

INSERT INTO bia.tblApplicationProperty (id, help, value) VALUES ('mail.activationUser.subject', '', 'Action Required to Activate Membership for DocSources');
INSERT INTO bia.tblApplicationProperty (id, help, value) VALUES ('mail.activationUser.text', '', 'Dear {0},\r \r Thank you for registering at the BIA. Before we can activate your account one last step must be taken to complete your registration.\r Please note - you must complete this last step to become a registered member. You will only need to visit this url once to activate your account.\r To complete your registration, please visit this url:\r \r {3}://{4}{5}user/ActivateUser.do?uuid={2}\r \r Please be sure not to add extra spaces.\r If you are still having problems signing up please contact a member of our support staff.\r Your Account is: {1}\r Your Activation uuid is:{2}\r \r BIA Support Service');
INSERT INTO bia.tblApplicationProperty (id, help, value) VALUES ('mail.resetUserPassword.subject', '', 'Your login details for DocSources');
INSERT INTO bia.tblApplicationProperty (id, help, value) VALUES ('mail.resetUserPassword.text', '', 'Dear {0},\r\n\r\nYou have requested to reset your password on BIA because you have forgotten your password.\r\nTo reset your password, go to this page:\r\n\r\n{3}://{4}{5}user/ResetUserPassword.do?uuid={2}\r\n\r\nPlease be sure not to add extra spaces.\r\n\r\nIf you are still having problems signing up please contact a member of our support staff.\r\nYour Username is: {1}\r\nYour Reset Password uuid is:{2}\r\n\r\nAll the best,\r\nBIA Support Service\r\n');
INSERT INTO bia.tblApplicationProperty (id, help, value) VALUES ('mail.approvedUser.subject', '', 'Approved Membership for BIA');
INSERT INTO bia.tblApplicationProperty (id, help, value) VALUES ('mail.approvedUser.text', '', 'Dear {0},\r\n\r\nyour account has been approved.\r\n\r\n{1}://{2}{3}\r\n\r\nPlease be sure not to add extra spaces.\r\nIf you are still having problems signing up please contact a member of our support staff.\r\nYour Account is: {4}\r\n\r\nAll the best,\r\nBIA Support Service');
INSERT INTO bia.tblApplicationProperty (id, help, value) VALUES ('mail.forumPostReplyNotification.subject', '', 'bia.medici.org - {0} {1} replied to [{2}]');
INSERT INTO bia.tblApplicationProperty (id, help, value) VALUES ('mail.forumPostReplyNotification.text', '', 'The Medici Archive Project  - BIA\r\n\r\n{0} {1} replied to your post on topic {2} \r\n\r\nclick on the following link to see it:\r\n\r\n{3}://{4}{5}community/ShowTopicForum.do?forumId={6}&topicId={7}&completeDOM=true');
INSERT INTO bia.tblApplicationProperty (id, help, value) VALUES ('message.approvationUser.text', '', 'This user needs to be approved:\r\n\r\n${0}\r\n\r\nName: ${1} ${2} ${3}\r\nOrganization: ${4}\r\nEmail: ${5}\r\n\r\n<a href="${6}://${7}${8}user/ShowUser.do?account=${0}" class="lnkLeft">Show User ${0}</a>');
INSERT INTO bia.tblApplicationProperty (id, help, value) VALUES ('message.approvationUser.subject', '', 'User ${0} to be approved');

INSERT INTO bia.tblApplicationProperty (id, help, value) VALUES ('path.tmpdir', '', '/data/');

INSERT INTO `tblApplicationProperty` (`id`, `help`, `value`) VALUES ('portrait.person.path', '', '/data/portrait/person');
INSERT INTO `tblApplicationProperty` (`id`, `help`, `value`) VALUES ('portrait.person.path.tmp', '', '/data/portrait/person/tmp');
INSERT INTO `tblApplicationProperty` (`id`, `help`, `value`) VALUES ('portrait.person.default', '', '/data/portrait/person/img_user.jpg');
INSERT INTO `tblApplicationProperty` (`id`, `help`, `value`) VALUES ('portrait.user.path', '', '/data/portrait/user');
INSERT INTO `tblApplicationProperty` (`id`, `help`, `value`) VALUES ('portrait.user.path.tmp', '', '/data/portrait/user/tmp');
INSERT INTO `tblApplicationProperty` (`id`, `help`, `value`) VALUES ('portrait.user.default', '', '/data/portrait/user/img_user.jpg');

ALTER TABLE `tblDocuments` ALTER `DOCTOBEVETTED` DROP DEFAULT;
ALTER TABLE `tblDocuments` CHANGE COLUMN `DOCTOBEVETTED` `DOCTOBEVETTED` TINYINT(1) NULL AFTER `LastUpdate`;
ALTER TABLE `tblDocuments` ALTER `DOCVETTED` DROP DEFAULT;
ALTER TABLE `tblDocuments` CHANGE COLUMN `DOCVETTED` `DOCVETTED` TINYINT(1) NULL AFTER `DOCVETBEGINS`;

INSERT INTO bia.tblApplicationTemplate (`name`, `parentName`, `preparer`, `template`) VALUES ('menu/ShowLoginFirstModalWindow', 'template.partialDOM', NULL, NULL);
INSERT INTO bia.tblApplicationTemplateAttributes (`templateName`, `name`, `type`, `value`, `cascadeAttribute`, `parentAttribute`) VALUES ('menu/ShowLoginFirstModalWindow', 'main', NULL, '/WEB-INF/jsp/menu/ShowLoginFirstModalWindow.jsp', 0, NULL);

ALTER TABLE bia.tblImages ADD COLUMN `insertNum` VARCHAR(5) NULL DEFAULT NULL COMMENT 'Number of the Insert' AFTER `volLetExt`, ADD COLUMN `insertLet` VARCHAR(3) NULL DEFAULT NULL COMMENT 'Extension of the Insert' AFTER `insertNum`;

ALTER TABLE bia.tblForumpost ADD COLUMN `lastUpdateBy` VARCHAR(50) NULL DEFAULT NULL AFTER `account`, ADD CONSTRAINT `Fk_tblForumPost_tblUser_updater` FOREIGN KEY (`lastUpdateBy`) REFERENCES `tbluser` (`account`) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE bia.tblPlaceGeographicCoordinates CHANGE COLUMN `SECOND_LATITUDE` `SECOND_LATITUDE` FLOAT(11) NULL DEFAULT NULL AFTER `MINUTE_LONGITUDE`, CHANGE COLUMN `SECOND_LONGITUDE` `SECOND_LONGITUDE` FLOAT(11) NULL DEFAULT NULL AFTER `SECOND_LATITUDE`;

ALTER TABLE bia.tblDocuments ADD COLUMN `FOLIORV` VARCHAR(1) NULL DEFAULT NULL AFTER `FOLIOMOD`;

ALTER TABLE bia.tblDocuments ADD COLUMN `TRANSCRIBEFOLIORV` VARCHAR(1) NULL DEFAULT NULL AFTER `TRANSCRIBEFOLIOMOD`;