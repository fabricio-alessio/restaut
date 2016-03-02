export PATH=/usr/local/jdk1.8.0_20/bin:$PATH
export JAVA_HOME=/usr/local/jdk1.8.0_20

nohup java -jar restaut.jar mongodb://10.111.4.44 8082 &
