DELIMITER $$
CREATE TRIGGER trg_booking_insert_check BEFORE
INSERT ON booking FOR EACH ROW BEGIN DECLARE v_count INT;
SELECT COUNT(1) INTO v_count
FROM booking
WHERE roomid = NEW.roomid AND DATE = NEW.date AND (
 (NEW.begintime BETWEEN begintime AND finishtime) OR (NEW.finishtime BETWEEN begintime AND finishtime) OR (begintime BETWEEN NEW.begintime AND NEW.finishtime) OR (finishtime BETWEEN NEW.begintime AND NEW.finishtime)
);
 
 IF v_count > 0 THEN SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = '会议室在同一天内已有重合时间段的预订！'; END IF; END$$

DELIMITER ; 
