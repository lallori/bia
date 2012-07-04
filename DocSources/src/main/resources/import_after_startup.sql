-- PLACES
-- added new field to manage creation date
update docsources.tblPlaces set dateCreated = dateEntered where dateCreated is null;
-- added new field to manage last update
update docsources.tblPlaces set lastUpdate = dateEntered where lastUpdate is null;


-- Document sortableDateInt: this is not for lucene indexing. This field is important for order documents by date.
update docsources.tblDocuments set sortableDateInt=concat(yearModern,SUBSTRING(concat('0',docMonthNum) FROM -2 FOR 2),SUBSTRING(concat('0',docDay) FROM -2 FOR 2)) where yearModern is not null and docMonthNum is not null and docDay is not null;
update docsources.tblDocuments set sortableDateInt=concat(yearModern,SUBSTRING(concat('0',docMonthNum) FROM -2 FOR 2),'00') where yearModern is not null and docMonthNum is not null and docDay is null;
update docsources.tblDocuments set sortableDateInt=concat(yearModern,'00',SUBSTRING(concat('0',docDay) FROM -2 FOR 2)) where yearModern is not null and docMonthNum is null and docDay is not null;
update docsources.tblDocuments set sortableDateInt=concat(yearModern,'00','00') where yearModern is not null and docMonthNum is null and docDay is null;
update docsources.tblDocuments set sortableDateInt=concat(docYear,SUBSTRING(concat('0',docMonthNum) FROM -2 FOR 2),SUBSTRING(concat('0',docDay) FROM -2 FOR 2)) where yearModern is null and docYear is not null and docMonthNum is not null and docDay is not null;
update docsources.tblDocuments set sortableDateInt=concat(docYear,SUBSTRING(concat('0',docMonthNum) FROM -2 FOR 2),'00') where yearModern is null and docYear is not null and docMonthNum is not null and docDay is null;
update docsources.tblDocuments set sortableDateInt=concat(docYear,'00',SUBSTRING(concat('0',docDay) FROM -2 FOR 2)) where yearModern is null and docYear is not null and docMonthNum is null and docDay is not null;
update docsources.tblDocuments set sortableDateInt=concat(docYear,'00','00') where yearModern is null and docYear is not null and docMonthNum is null and docDay is null;
update docsources.tblDocuments set sortableDateInt=concat('00',SUBSTRING(concat('0',docMonthNum) FROM -2 FOR 2),SUBSTRING(concat('0',docDay) FROM -2 FOR 2)) where yearModern is null and docYear is null and docMonthNum is not null and docDay is not null;
update docsources.tblDocuments set sortableDateInt=concat('00',SUBSTRING(concat('0',docMonthNum) FROM -2 FOR 2),'00') where yearModern is null and docYear is null and docMonthNum is not null and docDay is null;
update docsources.tblDocuments set sortableDateInt=concat('00','00',SUBSTRING(concat('0',docDay) FROM -2 FOR 2)) where yearModern is null and docYear is null and docMonthNum is null and docDay is not null;
update docsources.tblDocuments set sortableDateInt=concat('00','00','00') where yearModern is null and docYear is null and docMonthNum is null and docDay is null;

-- Populting tblForum
INSERT INTO `docsources`.`tblForum` (`forumId`, `description`, `title`, `type`, `subType`, `dispositionOrder`, `lastUpdate`, `dateCreated`, `postsNumber`, `status`, `topicsNumber`, `forumParent`, `lastPost`, `entryId`, `peopleId`, `placeAllId`, `summaryId`) VALUES (1, 'Board Index forum', 'Board Index', 'CATEGORY', 'GENERIC', 1, '2012-06-02 08:47:00', '2012-03-02 08:46:47', 0, 1, 0, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `docsources`.`tblForum` (`forumId`, `description`, `title`, `type`, `subType`, `dispositionOrder`, `lastUpdate`, `dateCreated`, `postsNumber`, `status`, `topicsNumber`, `forumParent`, `lastPost`, `entryId`, `peopleId`, `placeAllId`, `summaryId`) VALUES (2, 'Scholarly discussion forums', 'Scholarly discussion forums', 'CATEGORY', 'GENERIC', 1, '2011-04-16 08:13:07', '2012-04-16 08:13:07', 0, 1, 0, 1, NULL, NULL, NULL, NULL, NULL);
IblNSERT INTO `docsources`.`tblForum` (`forumId`, `description`, `title`, `type`, `subType`, `dispositionOrder`, `lastUpdate`, `dateCreated`, `postsNumber`, `status`, `topicsNumber`, `forumParent`, `lastPost`, `entryId`, `peopleId`, `placeAllId`, `summaryId`) VALUES (3, 'Learning Forums', 'Learning Forums', 'CATEGORY', 'GENERIC', 2, '2012-04-16 11:13:07', '2012-04-16 08:12:07', 0, 1, 0, 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `docsources`.`tblForum` (`forumId`, `description`, `title`, `type`, `subType`, `dispositionOrder`, `lastUpdate`, `dateCreated`, `postsNumber`, `status`, `topicsNumber`, `forumParent`, `lastPost`, `entryId`, `peopleId`, `placeAllId`, `summaryId`) VALUES (4, 'Technical Forums', 'Technical Forums', 'CATEGORY', 'GENERIC', 3, '2012-04-20 21:13:07', '2012-04-17 08:13:07', 0, 1, 0, 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `docsources`.`tblForum` (`forumId`, `description`, `title`, `type`, `subType`, `dispositionOrder`, `lastUpdate`, `dateCreated`, `postsNumber`, `status`, `topicsNumber`, `forumParent`, `lastPost`, `entryId`, `peopleId`, `placeAllId`, `summaryId`) VALUES (5, 'Documents forum', 'Documents', 'FORUM', 'DOCUMENT', 1, '2012-04-22 18:41:29', '2012-04-22 18:41:29', 0, 1, 0, 2, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `docsources`.`tblForum` (`forumId`, `description`, `title`, `type`, `subType`, `dispositionOrder`, `lastUpdate`, `dateCreated`, `postsNumber`, `status`, `topicsNumber`, `forumParent`, `lastPost`, `entryId`, `peopleId`, `placeAllId`, `summaryId`) VALUES (6, 'People forum', 'People', 'FORUM', 'PEOPLE', 2, '2012-04-23 08:42:05', '2012-04-23 08:42:05', 0, 1, 0, 2, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `docsources`.`tblForum` (`forumId`, `description`, `title`, `type`, `subType`, `dispositionOrder`, `lastUpdate`, `dateCreated`, `postsNumber`, `status`, `topicsNumber`, `forumParent`, `lastPost`, `entryId`, `peopleId`, `placeAllId`, `summaryId`) VALUES (7, 'Places forum', 'Places', 'FORUM', 'PLACE', 3, '2012-04-23 18:27:05', '2012-04-23 18:27:05', 0, 1, 0, 2, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `docsources`.`tblForum` (`forumId`, `description`, `title`, `type`, `subType`, `dispositionOrder`, `lastUpdate`, `dateCreated`, `postsNumber`, `status`, `topicsNumber`, `forumParent`, `lastPost`, `entryId`, `peopleId`, `placeAllId`, `summaryId`) VALUES (8, 'Volumes forum', 'Volumes', 'FORUM', 'VOLUME', 4, '2012-05-26 08:44:01', '2012-05-26 08:43:59', 0, 1, 0, 2, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `docsources`.`tblForum` (`forumId`, `description`, `title`, `type`, `subType`, `dispositionOrder`, `lastUpdate`, `dateCreated`, `postsNumber`, `status`, `topicsNumber`, `forumParent`, `lastPost`, `entryId`, `peopleId`, `placeAllId`, `summaryId`) VALUES (9, 'General questions forum', 'General Questions', 'FORUM', 'GENERIC', 1, '2012-03-26 14:54:01', '2012-03-26 11:00:01', 0, 1, 0, 3, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `docsources`.`tblForum` (`forumId`, `description`, `title`, `type`, `subType`, `dispositionOrder`, `lastUpdate`, `dateCreated`, `postsNumber`, `status`, `topicsNumber`, `forumParent`, `lastPost`, `entryId`, `peopleId`, `placeAllId`, `summaryId`) VALUES (10, 'Paleography forum', 'Paleography', 'FORUM', 'GENERIC', 2, '2012-03-26 13:11:32', '2012-03-26 10:12:01', 0, 1, 0, 3, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `docsources`.`tblForum` (`forumId`, `description`, `title`, `type`, `subType`, `dispositionOrder`, `lastUpdate`, `dateCreated`, `postsNumber`, `status`, `topicsNumber`, `forumParent`, `lastPost`, `entryId`, `peopleId`, `placeAllId`, `summaryId`) VALUES (11, 'How to use BIA forum', 'How to use BIA', 'FORUM', 'GENERIC', 3, '2012-05-27 14:44:01', '2012-03-27 14:44:01', 0, 1, 0, 4, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `docsources`.`tblForum` (`forumId`, `description`, `title`, `type`, `subType`, `dispositionOrder`, `lastUpdate`, `dateCreated`, `postsNumber`, `status`, `topicsNumber`, `forumParent`, `lastPost`, `entryId`, `peopleId`, `placeAllId`, `summaryId`) VALUES (12, 'Problems with BIA forum', 'Problems with BIA', 'FORUM', 'GENERIC', 1, '2012-05-10 12:44:01', '2012-04-10 22:44:01', 0, 1, 0, 4, NULL, NULL, NULL, NULL, NULL);

INSERT INTO `docsources`.`tblForumOption` (`forumId`, `canHaveSubCategory`, `canHaveSubForum`, `canHaveTopics`, `canDeletePosts`, `canDeleteTopics`, `canDownloadAttachments`, `canEditPosts`, `canOnlyViewOwnTopics`, `canPostAttachments`, `canPostReplys`, `canRateTopics`, `canView`) VALUES (1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1);
INSERT INTO `docsources`.`tblForumOption` (`forumId`, `canHaveSubCategory`, `canHaveSubForum`, `canHaveTopics`, `canDeletePosts`, `canDeleteTopics`, `canDownloadAttachments`, `canEditPosts`, `canOnlyViewOwnTopics`, `canPostAttachments`, `canPostReplys`, `canRateTopics`, `canView`) VALUES (2, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1);
INSERT INTO `docsources`.`tblForumOption` (`forumId`, `canHaveSubCategory`, `canHaveSubForum`, `canHaveTopics`, `canDeletePosts`, `canDeleteTopics`, `canDownloadAttachments`, `canEditPosts`, `canOnlyViewOwnTopics`, `canPostAttachments`, `canPostReplys`, `canRateTopics`, `canView`) VALUES (3, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1);
INSERT INTO `docsources`.`tblForumOption` (`forumId`, `canHaveSubCategory`, `canHaveSubForum`, `canHaveTopics`, `canDeletePosts`, `canDeleteTopics`, `canDownloadAttachments`, `canEditPosts`, `canOnlyViewOwnTopics`, `canPostAttachments`, `canPostReplys`, `canRateTopics`, `canView`) VALUES (4, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1);
INSERT INTO `docsources`.`tblForumOption` (`forumId`, `canHaveSubCategory`, `canHaveSubForum`, `canHaveTopics`, `canDeletePosts`, `canDeleteTopics`, `canDownloadAttachments`, `canEditPosts`, `canOnlyViewOwnTopics`, `canPostAttachments`, `canPostReplys`, `canRateTopics`, `canView`) VALUES (5, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1);
INSERT INTO `docsources`.`tblForumOption` (`forumId`, `canHaveSubCategory`, `canHaveSubForum`, `canHaveTopics`, `canDeletePosts`, `canDeleteTopics`, `canDownloadAttachments`, `canEditPosts`, `canOnlyViewOwnTopics`, `canPostAttachments`, `canPostReplys`, `canRateTopics`, `canView`) VALUES (6, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1);
INSERT INTO `docsources`.`tblForumOption` (`forumId`, `canHaveSubCategory`, `canHaveSubForum`, `canHaveTopics`, `canDeletePosts`, `canDeleteTopics`, `canDownloadAttachments`, `canEditPosts`, `canOnlyViewOwnTopics`, `canPostAttachments`, `canPostReplys`, `canRateTopics`, `canView`) VALUES (7, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1);
INSERT INTO `docsources`.`tblForumOption` (`forumId`, `canHaveSubCategory`, `canHaveSubForum`, `canHaveTopics`, `canDeletePosts`, `canDeleteTopics`, `canDownloadAttachments`, `canEditPosts`, `canOnlyViewOwnTopics`, `canPostAttachments`, `canPostReplys`, `canRateTopics`, `canView`) VALUES (8, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1);
INSERT INTO `docsources`.`tblForumOption` (`forumId`, `canHaveSubCategory`, `canHaveSubForum`, `canHaveTopics`, `canDeletePosts`, `canDeleteTopics`, `canDownloadAttachments`, `canEditPosts`, `canOnlyViewOwnTopics`, `canPostAttachments`, `canPostReplys`, `canRateTopics`, `canView`) VALUES (9, 0, 0, 1, 1, 1, 0, 1, 0, 0, 1, 0, 1);
INSERT INTO `docsources`.`tblForumOption` (`forumId`, `canHaveSubCategory`, `canHaveSubForum`, `canHaveTopics`, `canDeletePosts`, `canDeleteTopics`, `canDownloadAttachments`, `canEditPosts`, `canOnlyViewOwnTopics`, `canPostAttachments`, `canPostReplys`, `canRateTopics`, `canView`) VALUES (10, 0, 0, 1, 1, 1, 0, 1, 0, 0, 1, 0, 1);
INSERT INTO `docsources`.`tblForumOption` (`forumId`, `canHaveSubCategory`, `canHaveSubForum`, `canHaveTopics`, `canDeletePosts`, `canDeleteTopics`, `canDownloadAttachments`, `canEditPosts`, `canOnlyViewOwnTopics`, `canPostAttachments`, `canPostReplys`, `canRateTopics`, `canView`) VALUES (11, 0, 0, 1, 1, 1, 0, 1, 0, 0, 1, 0, 1);
INSERT INTO `docsources`.`tblForumOption` (`forumId`, `canHaveSubCategory`, `canHaveSubForum`, `canHaveTopics`, `canDeletePosts`, `canDeleteTopics`, `canDownloadAttachments`, `canEditPosts`, `canOnlyViewOwnTopics`, `canPostAttachments`, `canPostReplys`, `canRateTopics`, `canView`) VALUES (12, 0, 0, 1, 1, 1, 0, 1, 0, 0, 1, 0, 1);

