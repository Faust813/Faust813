mkdir workAP
javac -classpath commons-cli-1.3.1.jar -sourcepath work -d workAP work/*.java
jar -cfe GlobalProject.jar work.Main -C workAP .