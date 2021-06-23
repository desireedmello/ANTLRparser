# ANTLRparser
ANTLR parser used to scan JAVA source code for different brances of the tree node using AST view.

## Output Generated from Source Code:

1. Prints the import statements of the file.

2. Prints the variables declared in the method body along with the line number.

3. Prints the methods called on the variables.
 
4. For each method call, prints the method signature declared on the same class. If the method is not declared on the same class, prints UNKNOWN.

## Instructions to run the code and generate the output:

1. Coding is done in Eclipse JDT with the ASTParser to parse the sample test code.

2. This Eclipse project is a plug-in project.

3. For the build path please add the below jar files from the ANTLRparser project- lib folder to the Libraries- Classpath
   * commons-collections4-4.1.jar
   * commons-io-2.8.0.jar 

4. In the ANTLRparser project- META-INF folder- MANIFEST.MF file add the following to the Dependencies:

   * org.eclipse.jdt (3.18.700)
   * org.eclipse.jdt.core (3.25.0)
   * org.eclipse.core.resources (3.14.0)
   * org.eclipse.core.runtime (3.20.100)

5. Codes are in the ANTLRparser project- src folder- example package-
    * ArrangeSample.java (Sample test code) (2021 BeginnersBook)
    * JFrameExample.java (Sample test code)
    * Parser.java (Source code)

6. Please change the file path if using another source code in String content [line 48-49] in the Parser.java class.

7. Both Sample Test Codes and Source Code is in JAVA language.

8. ASTParser is for Java Version JLS8, if using a higher version of JAVA please change it in AstParser parser [line 52] in the Parser.java class

9. Used ASTView from eclipse Marketplace in the help tab to map the Sample Test Codes.

10. To run the code in eclipse please run it from the Parser.java class and check the output in the eclipse console.

### References

2021 BeginnersBook. Java Program to Sort Strings in an Alphabetical Order.
Retrieved May 21, 2021, https://beginnersbook.com/2018/10/java-program-to-sort-strings-in-an-alphabetical-order/
