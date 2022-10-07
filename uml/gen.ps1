if (Test-Path -path uml.png) {
    rm uml.png
}

if (Test-Path -path plantuml.jar) {
    java -jar plantuml.jar -Playout=smetana uml.txt
}