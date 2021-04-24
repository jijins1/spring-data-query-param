package com.ruokki.query.aprocessor.creator.field;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.google.common.collect.Lists;
import com.ruokki.query.aprocessor.ContextedClass;
import com.ruokki.query.aprocessor.TypeUtils;
import com.ruokki.query.aprocessor.creator.method.MethodCreatorGetter;
import com.ruokki.query.aprocessor.creator.method.MethodCreatorSetter;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import java.util.Date;
import java.util.List;

public class DateFieldCreator implements FieldCreator {
    private final ProcessingEnvironment processingEnvironment;

    public DateFieldCreator(ProcessingEnvironment processingEnvironment) {
        this.processingEnvironment = processingEnvironment;
    }

    @Override
    public Boolean canCreateFieldFromElement(Element fieldElement) {
        final TypeUtils typeUtils = new TypeUtils(processingEnvironment);
        return typeUtils.isSubTypeOf(fieldElement, Date.class);
    }

    @Override
    public List<FieldDeclaration> createField(Element fieldElement, ContextedClass contextedClass) {
        CompilationUnit compilationUnit = contextedClass.getCompilationUnit();
        ClassOrInterfaceDeclaration classOrInterfaceDeclaration = contextedClass.getClassOrInterfaceDeclaration();

        TypeElement typeElement = processingEnvironment.getElementUtils().getTypeElement(fieldElement.asType().toString());

        compilationUnit.addImport(typeElement.getQualifiedName().toString());


        final Name typeSimpleName = typeElement.getSimpleName();
        final String fieldNameSingle = fieldElement.getSimpleName().toString();

        final FieldDeclaration fieldDeclarationStart = classOrInterfaceDeclaration.addField(typeSimpleName.toString(), fieldNameSingle + "Start", Modifier.Keyword.PRIVATE);
        final FieldDeclaration fieldDeclarationEnd = classOrInterfaceDeclaration.addField(typeSimpleName.toString(), fieldNameSingle + "End", Modifier.Keyword.PRIVATE);

        final MethodCreatorSetter methodCreatorSetter = new MethodCreatorSetter();
        methodCreatorSetter.addSetter(classOrInterfaceDeclaration, fieldDeclarationStart);
        methodCreatorSetter.addSetter(classOrInterfaceDeclaration, fieldDeclarationEnd);

        final MethodCreatorGetter methodCreatorGetter = new MethodCreatorGetter();
        methodCreatorGetter.addGetter(classOrInterfaceDeclaration, fieldDeclarationStart);
        methodCreatorGetter.addGetter(classOrInterfaceDeclaration, fieldDeclarationEnd);


        return Lists.newArrayList(fieldDeclarationStart, fieldDeclarationEnd);
    }
}
