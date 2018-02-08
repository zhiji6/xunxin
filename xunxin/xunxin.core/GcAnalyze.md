JVM GC 参数详解
CommandLine flags: 
-XX:InitialHeapSize=268435456 
-XX:MaxHeapSize=1073741824 
-XX:+PrintGC 
-XX:+PrintGCDateStamps 
-XX:+PrintGCDetails 
-XX:+PrintGCTimeStamps 
-XX:+UseCompressedClassPointers 
-XX:+UseCompressedOops 
-XX:+UseG1GC 
-XX:-UseLargePagesIndividualAllocation 
-XX:+UseStringDeduplication 

2018-01-09T13:26:38.576+0800: 0.683: [GC concurrent-string-deduplication, 34.2K  ->27.7K  (6680.0B), avg 19.1%, 0.0001059 secs]

2018-01-09T13:26:38.838+0800: 0.945: [GC concurrent-string-deduplication, 2295.1K->1870.9K(424.2K) , avg 18.5%, 0.0093370 secs]




