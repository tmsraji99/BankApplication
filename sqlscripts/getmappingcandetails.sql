CREATE 
    ALGORITHM = UNDEFINED 
    DEFINER = `root`@`localhost` 
    SQL SECURITY DEFINER
VIEW `getmappingcandetails` AS
    SELECT 
        `candmap`.`candidate_id` AS `candidateid`,
        `candmap`.`bdmReq_id` AS `bdmReqId`,
        `c`.`clientName` AS `clientName`,
        `candmap`.`submissionDate` AS `candidateSubmitionDate`,
        `candmap`.`candidateStatus` AS `candidateStatus`,
        CONCAT(`can`.`firstName`, `can`.`lastName`) AS `candidateName`,
        `req`.`nameOfRequirement` AS `nameOfTheReq`,
        `u`.`name` AS `nameOftheRecruiter`,
        `candmap`.`offereStatus` AS `offerStatus`,
        CONCAT(`u`.`firstName`, `u`.`lastName`) AS `recruiterName`,
        `candmap`.`mappedUser_id` AS `mappedUser_id`,
        `candmap`.`lastUpdatedDate` AS `lastUpdatedDate`,
        `c`.`primaryContact_id` AS `primaryContact_id`,
        `c`.`secondaryContact_id` AS `secondaryContact_id`,
        `c`.`lead_id` AS `lead_id`,
        `c`.`accountManger_id` AS `accountManger_id`,
        `req`.`typeOfHiring` AS `typeOfHiring`,
        `candmap`.`offeredCtc` AS `CTC`,
        `candmap`.`onBoardedStatus` AS `onBoardedStatus`
    FROM
        ((((`candidatemapping` `candmap`
        JOIN `bdmreq` `req`)
        JOIN `client` `c`)
        JOIN `candidate` `can`)
        JOIN `user` `u`)
    WHERE
        ((`candmap`.`bdmReq_id` = `req`.`id`)
            AND (`req`.`client_id` = `c`.`id`)
            AND (`candmap`.`candidate_id` = `can`.`id`)
            AND (`u`.`id` = `candmap`.`mappedUser_id`)
            AND (`candmap`.`mappedUser_id` = `can`.`user_id`))