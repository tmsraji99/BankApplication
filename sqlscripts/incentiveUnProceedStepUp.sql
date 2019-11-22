CREATE DEFINER=`root`@`localhost` PROCEDURE `incentiveUnProceedStepUp`(in userId Long)
BEGIN
declare done long default 0; 
declare recid Long default 0;
declare v_canid Long default 0;
declare new_count Long default 1;
declare v_incentive_amount double default 1;
declare v_condition varchar(45) ;
declare v_target int;
declare v_valueType varchar(45) ;
declare v_cnt int;
declare total_amount double default 0;
declare v_can_id Long default 0;
declare v_am_percentage varchar(45);
declare v_bdm_percentage varchar(45);

declare v_lead_percentage varchar(45);

declare v_am_amount int;
declare v_am_cal_amount double;

declare v_bdm_amount int;
declare v_bdm_cal_amount double;

declare v_lead_amount int;
declare v_lead_cal_amount double;

declare v_primaryContact_id int;
declare v_accountManger_id int;
declare v_secondaryContact_id int;

declare v_lead_id int;
declare flag int default 0;
declare v_date date;
declare v_onBoardedDate date;

declare cur1 cursor for 
         SELECT mappedUser_id, id,onBoardedDate ,candidate_id FROM testing.candidatemapping where onBoardedStatus='on Boarded'
and onBoardedDate<=(CURDATE()) and mappedUser_id= userId and incetiveProcessed =0  and offeredCtc>= (SELECT min(min_ctc) FROM testing.incentive_configuration where is_countable=1 and role='recruiter' group by is_countable ) order by onBoardedDate desc; 
declare continue handler for not found set done=1;
SELECT 
    amount, valueType
INTO v_bdm_amount , v_bdm_percentage FROM
    testing.incentive_configuration
WHERE
    role = 'BDM';

SELECT 
    amount, valueType
INTO v_am_amount , v_am_percentage FROM
    testing.incentive_configuration
WHERE
    role = 'AM';

SELECT 
    amount, valueType
INTO v_lead_amount , v_lead_percentage FROM
    testing.incentive_configuration
WHERE
    role = 'Lead';



    set done = 0;
    open cur1;
    igmLoop: loop
        fetch cur1 into recid,v_canid,v_onBoardedDate,v_can_id;
        if done = 1 then leave igmLoop;
        end if;
  
SELECT 
    MAX(newCount) + 1, COUNT(*)
INTO new_count , v_cnt FROM
    testing.incentivenew
WHERE
    iscountable = 1
        AND YEAR(onBoraderd_date) = (SELECT 
            YEAR(onBoardedDate)
        FROM
            candidatemapping
        WHERE
            id = v_canid)
        AND MONTH(date) = (SELECT 
            MONTH(onBoardedDate)
        FROM
            candidatemapping
        WHERE
            id = v_canid)
        AND recid = recid
        AND isAmountDebited = 0;
 
if(new_count is null) then
set new_count:=1;
end if;

SELECT 
    amount, rule, target, valueType, COUNT(*)
INTO v_incentive_amount , v_condition , v_target , v_valueType , v_cnt FROM
    testing.incentive_configuration
WHERE
    role = 'recruiter' AND is_countable = 1
        AND target = new_count;



if(v_date is null) then
 select v_onBoardedDate  into v_date ;
 end if;
CASE 
WHEN new_count=v_target THEN 
     INSERT INTO `testing`.`incentivenew`
(
`date`,
`recId`,
`canId`,
`cr_Amount`,`onBoraderd_date`,`newCount` ,iscountable)
VALUES
(
 v_date,
recid,
v_can_id,
v_incentive_amount,v_onBoardedDate,new_count,1);

   
    ELSE
  
    SELECT amount,rule,target,valueType into v_incentive_amount,v_condition,v_target,v_valueType  FROM testing.incentive_configuration where role='recruiter' and target =(select max(target) from testing.incentive_configuration where role='recruiter');
  
   INSERT INTO `testing`.`incentivenew`
(
`date`,
`recId`,
`canId`,
`cr_Amount`,`onBoraderd_date`,`newCount`,iscountable)
VALUES
(
 v_date,
recid,
v_can_id,
v_incentive_amount,v_onBoardedDate,new_count,1);
 
END case;
 
SELECT 
    primaryContact_id,
    accountManger_id,
    secondaryContact_id,
    lead_id,
    COUNT(*)
INTO v_primaryContact_id , v_accountManger_id , v_secondaryContact_id , v_cnt , v_lead_id FROM
    testing.candidatemapping canmap,
    client cids,
    testing.bdmreq bdmreq
WHERE
    canmap.id = v_canid
        AND canmap.bdmReq_id = bdmreq.id
        AND cids.id = bdmreq.client_id; 

    if(v_am_percentage='%') then
    
    
    set v_am_cal_amount = (v_incentive_amount*v_am_amount)/100;
    else
   set v_am_cal_amount =v_am_amount;
    end if ;
    
    if(v_bdm_percentage='%') then
      set v_bdm_cal_amount = (v_incentive_amount*v_bdm_amount)/100;
  
    else
   set v_bdm_cal_amount =v_bdm_amount;
    end if ;
    
     if(v_lead_percentage='%') then
       set v_lead_cal_amount =  (v_incentive_amount*v_lead_amount)/100 ;
  
    else
   set v_lead_cal_amount =v_lead_amount;
    end if ;
  if(v_am_cal_amount!=0.00) then   
INSERT INTO `testing`.`incentivenew`
(
`date`,
`recId`,
`canId`,
`cr_Amount`,`onBoraderd_date`)
VALUES
(
v_date,
v_accountManger_id,
v_can_id,
v_am_cal_amount,v_onBoardedDate);
end if;
 if(v_bdm_cal_amount!=0.00) then       
    INSERT INTO `testing`.`incentivenew`
(
`date`,
`recId`,
`canId`,
`cr_Amount`,`onBoraderd_date`)
VALUES
(
 v_date,
v_primaryContact_id,
v_can_id,
v_bdm_cal_amount,v_onBoardedDate);
end if;
   if(v_lead_cal_amount!=0.00) then  
     INSERT INTO `testing`.`incentivenew`
(
`date`,
`recId`,
`canId`,
`cr_Amount`,`onBoraderd_date`)
VALUES
(
 v_date,
v_lead_id,
v_can_id,
v_lead_cal_amount,v_onBoardedDate);
   end if;
UPDATE testing.candidatemapping 
SET 
    incetiveProcessed = 1
WHERE
    id = v_canid;
   
    end loop igmLoop;
 
 
    close cur1;
END