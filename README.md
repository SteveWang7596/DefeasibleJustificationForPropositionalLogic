# DefeasibleJustificationForPropositionalLogic

DefeasibleJustificationForPropositionalLogic (DJPL) is a tool that computes justification for a defeasible entailment. 

## Limitations
DJPL depends on the Tweety Project (https://tweetyproject.org/) for models and operations for Propositional Logic (PL). The Tweety Project is based on Java 15. Therefore, DJPL can only compile and run with Java 15. 

## Compile and Run
Maven manages DJPL build. The pom.xml is configured to compile DJPL and create a fat jar. The fat jar includes DJPL along with its dependencies. DJPL can be compiled with the following Maven command.
```
mvn clean package
```
The outputs of the compilation are in the "target" folder. The fat jar is named "DefeasibleJustificationForPropositionalLogic-1.0-SNAPSHOT-jar-with-dependencies" by default. To run the fat jar, use the following Java command. 
```
java -jar DefeasibleJustificationForPropositionalLogic-1.0-SNAPSHOT-jar-with-dependencies.jar
```

## Input
DJPL presents a graphical user interface (GUI) which allows users to input a defeasible knowledge base and a query. The defeasible knowledge base is in the form of a text file, see the following example: 
```
Penguin => Bird
Robin=>Bird
SpecialPenguin=>Penguin
Bird~>Fly
Bird~>Wings
Penguin~>!Fly
SpecialPenguin~>Fly
```
