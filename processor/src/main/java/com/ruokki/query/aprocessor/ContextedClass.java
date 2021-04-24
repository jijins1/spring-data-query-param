package com.ruokki.query.aprocessor;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


@Getter
@Setter
@Accessors(chain = true)
public class ContextedClass {
    private ClassOrInterfaceDeclaration classOrInterfaceDeclaration;
    private CompilationUnit compilationUnit;
    private MethodDeclaration getMapMethodeDeclaration;

}
