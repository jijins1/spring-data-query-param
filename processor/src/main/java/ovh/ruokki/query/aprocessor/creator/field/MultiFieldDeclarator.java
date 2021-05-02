package ovh.ruokki.query.aprocessor.creator.field;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.google.common.collect.Lists;
import ovh.ruokki.query.aprocessor.ContextedClass;
import ovh.ruokki.query.aprocessor.creator.method.MethodCreatorGetter;
import ovh.ruokki.query.aprocessor.creator.method.MethodCreatorSetter;
import ovh.ruokki.query.aprocessor.StringUtils;

import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import java.util.List;

public class MultiFieldDeclarator {
    public FieldDeclaration makeFieldDecalaration(String fieldNameSingle,
                                                  ContextedClass contextedClass,
                                                  TypeElement typeElement) {
        final CompilationUnit compilationUnit = contextedClass.getCompilationUnit();
        compilationUnit.addImport(typeElement.getQualifiedName().toString());
        compilationUnit.addImport(List.class);
        compilationUnit.addImport(Lists.class);

        final ClassOrInterfaceDeclaration classOrInterfaceDeclaration = contextedClass.getClassOrInterfaceDeclaration();

        final Name typeSimpleName = typeElement.getSimpleName();

        final String fieldNamePluriel = fieldNameSingle + "s";
        final FieldDeclaration fieldDeclaration = classOrInterfaceDeclaration.addField("List<" + typeSimpleName + ">", fieldNamePluriel, Modifier.Keyword.PRIVATE);

        new MethodCreatorGetter().addGetter(classOrInterfaceDeclaration, fieldDeclaration);
        new MethodCreatorSetter().addSetter(classOrInterfaceDeclaration, fieldDeclaration);
        this.addSetterArray(classOrInterfaceDeclaration, fieldDeclaration, typeSimpleName.toString(), fieldNameSingle);
        this.addSetterOne(classOrInterfaceDeclaration, fieldDeclaration, typeSimpleName.toString(), fieldNameSingle);

        return fieldDeclaration;
    }


    private void addSetterOne(ClassOrInterfaceDeclaration classDeclaration, FieldDeclaration fieldDeclaration, String typeFieldName, String fieldName) {

        final MethodDeclaration methodDeclaration = classDeclaration.addMethod("set" + StringUtils.capitalize(fieldName), Modifier.Keyword.PUBLIC);
        methodDeclaration.setType(classDeclaration.getName().asString());


        methodDeclaration.addAndGetParameter(typeFieldName, fieldName);

        final BlockStmt body = new BlockStmt();
        body.addAndGetStatement("this." + fieldDeclaration.getVariable(0) + " =  Lists.newArrayList(" + fieldName + ")");
        body.addAndGetStatement("return this");

        methodDeclaration.setBody(body);

    }

    private void addSetterArray(ClassOrInterfaceDeclaration classDeclaration, FieldDeclaration fieldDeclaration, String typeFieldName, String fieldName) {

        final MethodDeclaration methodDeclaration = classDeclaration.addMethod("set" + StringUtils.capitalize(fieldDeclaration.getVariable(0).getNameAsString()), Modifier.Keyword.PUBLIC);
        methodDeclaration.setType(classDeclaration.getName().asString());


        methodDeclaration.addAndGetParameter(typeFieldName, fieldName + "[]");

        final BlockStmt body = new BlockStmt();
        body.addAndGetStatement("this." + fieldDeclaration.getVariable(0) + " =  Lists.newArrayList(" + fieldName + ")");
        body.addAndGetStatement("return this");

        methodDeclaration.setBody(body);

    }
}
