package example;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.MultiMap;
import org.apache.commons.collections4.map.MultiValueMap;
import org.apache.commons.io.FileUtils;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

public class Parser {

	// To count import statements and variables declared
	public static int count;

	// Method to reset count
	public void counter() {
		count = 0;
	}

	// Lists to store method return type, name and parameters
	List<String> method_return = new ArrayList<String>();
	List<String> methods = new ArrayList<String>();
	List<String> method_parameters = new ArrayList<String>();

	// Lists to store line number and method called
	List<Integer> method_linenum = new ArrayList<Integer>();
	List<String> method_call = new ArrayList<String>();

	// MultiMap to store 'key' for methods called and 'value' for variables
	MultiMap<String, String> map = new MultiValueMap<String, String>();

	public void run() throws IOException {

		// To read the content of the file
		String content = FileUtils.readFileToString(
				new File("C:\\Users\\Admin\\eclipse-workspace\\Assignment1SE\\src\\example\\JFrameExample.java"));

		// To create a parser object
		ASTParser parser = ASTParser.newParser(AST.JLS8);

		// To give the content to the parser
		parser.setSource(content.toCharArray());
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		final CompilationUnit cu = (CompilationUnit) parser.createAST(null);

		// Print the import statements of the file:
		System.out.println("List of import statements: ");

		cu.accept(new ASTVisitor() {

			@Override
			public boolean visit(ImportDeclaration id) {

				// Increment counter
				count++;

				// Syntax for import statements
				String import_name = id.getName().getFullyQualifiedName();

				// Printing the import statements for first part
				System.out.println("[" + count + "] " + import_name);

				return true;
			}

			@Override
			public boolean visit(MethodDeclaration md) {

				// Syntax for method declared return type and name
				String return_type = md.getReturnType2().toString();
				String method_name = md.getName().getIdentifier();

				// Syntax for method declared line number
				int method_line = cu.getLineNumber(md.getStartPosition());

				System.out.println();

				// Adding the method return type, name and parameters to the lists
				method_return.add(return_type);
				methods.add(method_name);
				method_parameters.add(md.parameters().toString());

				// Printing the Method Signature
				System.out.println("Line Number: " + method_line + " Method Signature: " + return_type + " "
						+ method_name + " " + md.parameters().toString());

				// Calling counter method
				counter();

				// Printing variables on the method
				System.out.println("Variables declared on the method body: ");

				md.accept(new ASTVisitor() {

					@Override
					public boolean visit(VariableDeclarationFragment vdf) {

						// Syntax for variable name
						String variable_name = vdf.getName().getIdentifier();

						// Syntax for line number
						int vline_number = cu.getLineNumber(vdf.getStartPosition());

						// Increment counter
						count++;

						// Printing the list of variables for second part
						System.out.println(
								"[" + count + "] Line Number: " + vline_number + " Variable Name: " + variable_name);

						return true;
					}
				});

				System.out.println();

				// Printing the Method Signature
				System.out.println("Line Number: " + method_line + " Method Signature: " + return_type + " "
						+ method_name + " " + md.parameters().toString());

				return true;
			}

			@Override
			public boolean visit(MethodInvocation mi) {

				// Adding method call to list
				method_call.add(mi.getName().getIdentifier());

				// Adding method call line number to list
				method_linenum.add(cu.getLineNumber(mi.getStartPosition()));

				// Checking if expression is not null and is of SimpleName
				if (mi.getExpression() != null && mi.getExpression() instanceof SimpleName) {

					// Syntax for method invoked expression and name
					String exp = mi.getExpression().toString();
					String name = mi.getName().getIdentifier();

					// Adding method invoked method called and variables to MultiMap
					map.put(exp, name);
				}
				return true;
			}

			@Override
			public boolean visit(VariableDeclarationFragment vdf1) {

				vdf1.accept(new ASTVisitor() {

					@Override
					public boolean visit(ClassInstanceCreation cic) {

						if (cic.getType().isSimpleType()) {

							// Syntax for constructor name and type
							String exp = vdf1.getName().getIdentifier();
							String name = cic.getType().toString();

							// Adding constructor name and type to MultiMap
							map.put(exp, name);
						}
						return true;
					}
				});
				return true;
			}
		});

		// Printing the methods called for third part
		for (String name : map.keySet()) {
			System.out.println("Methods called on " + name + ": " + map.get(name));
		}
		
		 // Printing the method call and Method Signature for fourth part
		
		System.out.println();

		for (int i = 0; i < method_call.size(); i++) {

			// Syntax to get each method call from the list
			String name = method_call.get(i);

			/*
			 * Comparing method invoked with method declared list to find similar method
			 * names
			 */
			if (methods.contains(name)) {

				// Getting the index of the method to call the Method Signature
				int j = methods.indexOf(name);

				/*
				 * Printing line number, method called with Method Signature if similar else
				 * printing unknown if no match found in the class
				 */
				System.out.println("Line Number: " + method_linenum.get(i) + " Method Call: " + method_call.get(i)
						+ " Method Signature: " + method_return.get(j) + " " + methods.get(j) + " "
						+ method_parameters.get(j));
			} else {
				System.out.println("Line Number: " + method_linenum.get(i) + " Method Call: " + method_call.get(i)
						+ " Method Signature: Unknown");
			}
		}
	}

	public static void main(String[] args) {

		// Creating instance of class to run the parse
		Parser assignment = new Parser();
		
		try {
			assignment.run();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
