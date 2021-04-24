package com.ruokki.query.aprocessor;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.google.common.collect.Lists;
import com.ruokki.query.annotation.CommonCriteria;
import com.ruokki.query.aprocessor.creator.field.DateFieldCreator;
import com.ruokki.query.aprocessor.creator.field.FieldCreator;
import com.ruokki.query.aprocessor.creator.field.PrimitiveFieldCreator;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Set;


@SupportedAnnotationTypes("com.ruokki.query.annotation.Criteria")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class CriteriaProcessor extends AbstractProcessor {

    List<FieldCreator> fieldCreators;

    public CriteriaProcessor() {
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        fieldCreators = Lists.newArrayList(new PrimitiveFieldCreator(processingEnv), new DateFieldCreator(processingEnv));

        for (TypeElement annotation : annotations) {
            for (Element classElement : roundEnv.getElementsAnnotatedWith(annotation)) {


                final ContextedClass contextedClass = this.initClass(classElement);

                classElement.getEnclosedElements().stream()
                        .filter(element -> element.getKind().isField())
                        .forEach(fieldElement -> {
                            try {
                                this.addFieldFromCriteria(fieldElement, contextedClass);
                            } catch (Exception e) {
                                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, e.getMessage());
                            }
                        });

                this.addAbstractMethod(contextedClass);
                this.writeClass(classElement, contextedClass);
            }
        }
        return true;
    }

    private void addAbstractMethod(ContextedClass contextedClass) {
    }

    private void writeClass(Element classElement, ContextedClass contextedClass) {
        CompilationUnit compilationUnit = contextedClass.getCompilationUnit();
        ClassOrInterfaceDeclaration classOrInterfaceDeclaration = contextedClass.getClassOrInterfaceDeclaration();


        try {
            final String fullyQualifiedName = classOrInterfaceDeclaration.getFullyQualifiedName().get();
            JavaFileObject builderFile = processingEnv.getFiler().createSourceFile(fullyQualifiedName);
            try (PrintWriter out = new PrintWriter(builderFile.openWriter())) {


                out.println(compilationUnit.toString());
            }
        } catch (IOException ioException) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, ioException.getMessage());

        }
    }

    private ContextedClass initClass(Element classElement) {
        var className = classElement.getSimpleName().toString();
        final String fullClassName = classElement.toString();

        var packageName = getPackageName(className, fullClassName);


        final CompilationUnit compilationUnit = new CompilationUnit();
        compilationUnit.setPackageDeclaration(packageName + ".criteria");


        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Class  " + classElement);

        final TypeElement fromClass = processingEnv.getElementUtils().getTypeElement(classElement.asType().toString());

        final ClassOrInterfaceDeclaration myClass = compilationUnit.addClass(classElement.getSimpleName() + "Criteria")
                .addModifier(Modifier.Keyword.PUBLIC);


        compilationUnit.addImport(CommonCriteria.class);
        myClass.addExtendedType("CommonCriteria<" + fromClass.getSimpleName() + ">");


        compilationUnit.addImport(fromClass.getQualifiedName().toString());

        final ConstructorDeclaration constructorDeclaration = myClass.addConstructor(Modifier.Keyword.PUBLIC);
        final BlockStmt constructorBody = constructorDeclaration.getBody();
        constructorBody.addAndGetStatement("super(" + fromClass.getSimpleName() + ".class)");


        final ContextedClass contextedClass = new ContextedClass().setClassOrInterfaceDeclaration(myClass)
                .setCompilationUnit(compilationUnit);


        return contextedClass;
    }

    private String getPackageName(String className, String fullClassName) {
        return fullClassName.substring(0, fullClassName.length() - className.length() - 1);
    }

    private void addFieldFromCriteria(Element fieldElement, ContextedClass contextedClass) {
        fieldCreators.stream().filter(fieldCreator -> fieldCreator.canCreateFieldFromElement(fieldElement))
                .forEach(fieldCreator -> fieldCreator.createField(fieldElement, contextedClass));

    }


}
