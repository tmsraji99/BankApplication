CREATE DEFINER=`root`@`localhost` PROCEDURE `CalIncentiveForDebitStepDown`(in userId Long)
BEGIN
declare done long default 0; 
declare recid Long default 0;
declare recid1 Long default 0;
declare v_canid Long default 0;
declare new_count Long default 1;
declare v_onBoardedDate date;
declare v_absconded_date date;
declare v_caldate date;
declare v_dbamount double default 0;
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
declare v_cnt int;
declare v_cnts int;
declare v_inv_id int;
declare cur1 cursor for 
         SELECT mappedUser_id, id ,onBoardedDate, absconded_date,candidate_id FROM testing.candidatemapping where onBoardedStatus!='on Boarded' and absconded_date is not null and DATEDIFF(onBoardedDate,absconded_date) <=90
and  mappedUser_id= userId and  isAmountDebited =0 and incetiveProcessed in(1,2)  order by onBoardedDate desc;
declare continue handler for not found set done=1;
SELECT amount,valueType into v_bdm_amount, v_bdm_percentage FROM testing.incentive_configuration where role='BDM' ;

SELECT amount,valueType into v_am_amount,v_am_percentage FROM testing.incentive_configuration where role='AM' ;

SELECT amount,valueType into v_lead_amount,v_lead_percentage
FROM testing.incentive_configuration where role='Lead' ;

    set done = 0;
    open cur1;
     igmLoop: loop
        fetch cur1 into recid,v_canid,v_onBoardedDate ,v_absconded_date,v_can_id;
        if done = 1 then leave igmLoop;
        end if;
        
        
    
        
        select date  into v_caldate   from incentivenew where recId =userId and canId=v_can_id  and iscountable=1
         and cr_amount is not null  order by id desc  limit 1;
        
-- select v_inv_id;
        
         select newCount,id into v_cnts,v_inv_id from incentivenew where iscountable=1 and date=v_caldate and recId =userId and isAmountDebited=0 and newCount is not null order by  id desc limit 1;
         select v_cnts ,v_inv_id;
     
	  
        select cr_amount 
         into v_dbamount from  
            incentivenew where id=v_inv_id and isAmountDebited =0;
           INSERT INTO `testing`.`incentivenew`
(
`date`,
`recId`,
`canId`,
`dr_Amount`,`abscondDate`)
VALUES
(
 now(),
recid,
v_can_id,
v_dbamount,v_absconded_date);  

        update testing.incentivenew set isAmountDebited =1 where id =v_inv_id;
        update testing.candidatemapping set isAmountDebited =1 where id =v_canid;
            SELECT primaryContact_id,accountManger_id,secondaryContact_id,count(*),lead_id into v_primaryContact_id,v_accountManger_id, v_secondaryContact_id , v_cnt,v_lead_id FROM testing.candidatemapping canmap ,
  client cids, testing.bdmreq bdmreq where canmap.id=v_canid and canmap.bdmReq_id = bdmreq.id and cids.id=bdmreq.client_id; 
    if(v_am_percentage='%') then
    
    
    set v_am_cal_amount = (v_dbamount*v_am_amount)/100;
    else
   set v_am_cal_amount =v_am_amount;
    end if ;
    
    if(v_bdm_percentage='%') then
      set v_bdm_cal_amount = (v_dbamount*v_bdm_amount)/100;
  
    else
   set v_bdm_cal_amount =v_bdm_amount;
    end if ;
    
     if(v_lead_percentage='%') then
   
       set v_lead_cal_amount =  (v_dbamount*v_lead_amount)/100 ;
  
    else
   set v_lead_cal_amount =v_lead_amount;
    end if ;
    
INSERT INTO `testing`.`incentivenew`
(
`date`,
`recId`,
`canId`,
`dr_Amount`,`abscondDate`)
VALUES
(
 now(),
v_accountManger_id,
v_can_id,
v_am_cal_amount,v_absconded_date);
    
    INSERT INTO `testing`.`incentivenew`
(
`date`,
`recId`,
`canId`,
`dr_Amount`,`abscondDate`)
VALUES
(
 now(),
v_primaryContact_id,
v_can_id,
v_bdm_cal_amount,v_absconded_date);
    
     INSERT INTO `testing`.`incentivenew`
(
`date`,
`recId`,
`canId`,
`dr_Amount`,`abscondDate`)
VALUES
(
 now(),
v_lead_id,
v_can_id,
v_lead_cal_amount,v_absconded_date);
      
           
        
    end loop igmLoop;
  

  
    
    close cur1;
END