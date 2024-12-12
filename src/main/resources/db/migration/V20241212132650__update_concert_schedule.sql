UPDATE concert_schedule
SET start_time = DATE_SUB(start_time, INTERVAL 6 MONTH)
WHERE concert_id = 3;