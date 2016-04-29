---
layout: post
title: "sample code of shell"
description: ""
category: "tech"
tags: [shell/bash]
date: "2016-04-28"

---
{% include JB/setup %}

dbmonitor.sh:监控分区表

```bash
#!/bin/bash
# create new partition of table eben_history
# delete old partition of table eben_history

host=xx
dbuser=xx
passwd=xx

ts1=$(($(date +%s)-(10*24*60*60))) #10 days ago
oldpart=$(date +p%Y%m%d -d "1970-01-01 UTC $ts1 seconds")

ts2=$(($(date +%s)+(3*24*60*60))) #3 days after
newpart=$(date +p%Y%m%d -d "1970-01-01 UTC $ts2 seconds")
ts2=$(($ts2*1000+24*60*60*1000)) #4 days after of milliseconds

echo "creating ${newpart} and deleting ${oldpart}"
mysql -h$host -u$dbuser -p$passwd << EOF
use funambol;
alter table eben_history reorganize partition pmax into
        (partition $newpart values less than ($ts2),
                partition pmax values less than maxvalue);
        exit
        EOF

# seperate two task to avoid impact each other
mysql -h$host -u$dbuser -p$passwd << EOF
use funambol;
alter table eben_history drop partition $oldpart;
exit
EOF
```


