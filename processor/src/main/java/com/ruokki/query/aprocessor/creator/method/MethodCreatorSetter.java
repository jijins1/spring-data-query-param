package com.ruokki.query.aprocessor.creator.method;

import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.stmt.BlockStmt;

import static com.ruokki.query.aprocessor.StringUtils.capitalize;

public class MethodCreatorSetter {
    public void addSetter(ClassOrInterfaceDeclaration classDeclaration, FieldDeclaration fieldDeclaration) {

        final VariableDeclarator fieldName = fieldDeclaration.getVariable(0);

        final MethodDeclaration methodDeclaration = classDeclaration.addMethod("set" + capitalize(fieldName.toString()), Modifier.Keyword.PUBLIC);
        methodDeclaration.setType(classDeclaration.getName().asString());


        methodDeclaration.addAndGetParameter(fieldDeclaration.getCommonType(), fieldName.getName().asString());

        final BlockStmt body = new BlockStmt();
        body.addAndGetStatement("this." + fieldName + " = " + fieldName);
        body.addAndGetStatement("return this");

        methodDeclaration.setBody(body);

    }

}
