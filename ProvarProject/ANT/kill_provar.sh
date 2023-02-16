
#!/bin/sh

pidof chromedriver > /home/chromedriver_pids.txt
pidof java > /home/java_pids.txt

grep -Fxvf /home/chromedriver.txt /home/chromedriver_pids.txt > /home/c_pids.txt
grep -Fxvf /home/java.txt /home/java_pids.txt > /home/j_pids.txt

while IFS="" read -r p|| [ -n "$p" ]
do
	echo chromedriver: ${p}
	kill -SIGKILL ${p}
done < /home/c_pids.txt

while IFS="" read -r p|| [ -n "$p" ]
do
	echo java: ${p}
	kill -SIGKILL ${p}
done < /home/j_pids.txt

rm /home/chromedriver.txt
rm /home/chromedriver_pids.txt
rm /home/c_pids.txt
rm /home/java.txt
rm /home/java_pids.txt
rm /home/j_pids.txt
