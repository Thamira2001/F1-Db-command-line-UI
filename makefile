
build: Formula1Part2.class

Formula1Part2.class: Formula1Part2.java
	javac Formula1Part2.java

run: Formula1Part2.class
	java -cp .:mssql-jdbc-11.2.0.jre11.jar Formula1Part2

clean:
	rm Formula1Part2.class
