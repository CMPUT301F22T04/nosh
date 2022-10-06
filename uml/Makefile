

run: clean uml

uml: plantuml.jar
	java -jar plantuml.jar -Playout=smetana uml.txt

clean:
	if [ -f uml.png ]; then rm uml.png; fi
