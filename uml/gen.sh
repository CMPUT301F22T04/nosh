if [ -f uml.png ]; then rm uml.png; fi
java -jar plantuml.jar -Playout=smetana uml.txt