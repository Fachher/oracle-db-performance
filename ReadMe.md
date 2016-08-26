



-- Testing

select s.prev_exec_start
from v$session s 
where 1=1 
and username = 'DGVS_LOCAL'
and s.program = 'JDBC Thin Client';