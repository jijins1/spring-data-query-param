package com.ruokki.query.aprocessor.creator.field;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.google.common.collect.Lists;
import com.ruokki.query.aprocessor.ContextedClass;
import com.ruokki.query.aprocessor.TypeUtils;
import com.ruokki.query.aprocessor.creator.method.MethodCreatorGetter;
import com.ruokki.query.aprocessor.creator.method.MethodCreatorSetter;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeMirror;
import java.util.Collections;
import java.util.List;

import static com.ruokki.query.aprocessor.StringUtils.capitalize;

public class PrimitiveFieldCreator implements FieldCreator {
    private ProcessingEnvironment processingEnvironment;

    public PrimitiveFieldCreator(ProcessingEnvironment processingEnvironment) {
        this.processingEnvironment = processingEnvironment;
    }

    @Override
    public Boolean canCreateFieldFromElement(Element fieldElement) {
        final TypeUtils typeUtils = new TypeUtils(processingEnvironment);
        final Boolean subTypeOfNumber = typeUtils.isSubTypeOf(fieldElement, Number.class);
        final Boolean subTypeOfBoolean = typeUtils.isSubTypeOf(fieldElement, Boolean.class);
        final Boolean subTypeOfString = typeUtils.isSubTypeOf(fieldElement, String.class);
        return fieldElement.asType().getKind().isPrimitive() || subTypeOfBoolean || subTypeOfNumber || subTypeOfString;
    }


    @Override
    public List<FieldDeclaration> createField(Element fieldElement, ContextedClass contextedClass) {
        TypeElement typeElement;

        CompilationUnit compilationUnit = contextedClass.getCompilationUnit();
        ClassOrInterfaceDeclaration classOrInterfaceDeclaration = contextedClass.getClassOrInterfaceDeclaration();

        final TypeMirror typeMirror = fieldElement.asType();
        if (typeMirror.getKind().isPrimitive()) {
            typeElement = processingEnvironment.getTypeUtils().boxedClass((PrimitiveType) typeMirror);
        } else {
            typeElement = processingEnvironment.getElementUtils().getTypeElement(fieldElement.asType().toString());
        }
        compilationUnit.addImport(typeElement.getQualifiedName().toString());
        compilationUnit.addImport(List.class);
        compilationUnit.addImport(Lists.class);

        final Name typeSimpleName = typeElement.getSimpleName();
        final String fieldNameSingle = fieldElement.getSimpleName().toString();

        final FieldDeclaration fieldDeclaration = classOrInterfaceDeclaration.addField("List<" + typeSimpleName + ">", fieldNameSingle + "s", Modifier.Keyword.PRIVATE);

        new MethodCreatorGetter().addGetter(classOrInterfaceDeclaration, fieldDeclaration);
        new MethodCreatorSetter().addSetter(classOrInterfaceDeclaration, fieldDeclaration);
        this.addSetterArray(classOrInterfaceDeclaration, fieldDeclaration, typeSimpleName.toString(), fieldNameSingle);
        this.addSetterOne(classOrInterfaceDeclaration, fieldDeclaration, typeSimpleName.toString(), fieldNameSingle);
        return Collections.singletonList(fieldDeclaration);
    }

    private void addSetterOne(ClassOrInterfaceDeclaration classDeclaration, FieldDeclaration fieldDeclaration, String typeFieldName, String fieldName) {

        final MethodDeclaration methodDeclaration = classDeclaration.addMethod("set" + capitalize(fieldName), Modifier.Keyword.PUBLIC);
        methodDeclaration.setType(classDeclaration.getName().asString());


        methodDeclaration.addAndGetParameter(typeFieldName, fieldName);

        final BlockStmt body = new BlockStmt();
        body.addAndGetStatement("this." + fieldDeclaration.getVariable(0) + " =  Lists.newArrayList(" + fieldName + ")");
        body.addAndGetStatement("return this");

        methodDeclaration.setBody(body);

    }

    private void addSetterArray(ClassOrInterfaceDeclaration classDeclaration, FieldDeclaration fieldDeclaration, String typeFieldName, String fieldName) {

        final MethodDeclaration methodDeclaration = classDeclaration.addMethod("set" + capitalize(fieldDeclaration.getVariable(0).getNameAsString()), Modifier.Keyword.PUBLIC);
        methodDeclaration.setType(classDeclaration.getName().asString());


        methodDeclaration.addAndGetParameter(typeFieldName, fieldName + "[]");

        final BlockStmt body = new BlockStmt();
        body.addAndGetStatement("this." + fieldDeclaration.getVariable(0) + " =  Lists.newArrayList(" + fieldName + ")");
        body.addAndGetStatement("return this");

        methodDeclaration.setBody(body);

    }


}
