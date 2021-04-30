package com.ruokki.query.aprocessor;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


/**
 * The type Contexted class.
 */
@Getter
@Setter
@Accessors(chain = true)
public class ContextedClass {
    /**
     * The Class or interface declaration to populate during processor annotation build
     */
    private ClassOrInterfaceDeclaration classOrInterfaceDeclaration;
    /**
     * The Compilation unit. Who contains the class
     */
    private CompilationUnit compilationUnit;
    /**
     * The Get map methode declaration.
     */
    private MethodDeclaration getMapMethodeDeclaration;


}
