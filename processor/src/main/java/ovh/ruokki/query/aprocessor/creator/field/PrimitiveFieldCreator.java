package ovh.ruokki.query.aprocessor.creator.field;

import com.github.javaparser.ast.body.FieldDeclaration;
import ovh.ruokki.query.aprocessor.ContextedClass;
import ovh.ruokki.query.aprocessor.TypeUtils;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeMirror;
import java.util.Collections;
import java.util.List;

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

        final TypeMirror typeMirror = fieldElement.asType();
        if (typeMirror.getKind().isPrimitive()) {
            typeElement = processingEnvironment.getTypeUtils().boxedClass((PrimitiveType) typeMirror);
        } else {
            typeElement = processingEnvironment.getElementUtils().getTypeElement(fieldElement.asType().toString());
        }

        final String fieldNameSingle = fieldElement.getSimpleName().toString();

        final FieldDeclaration fieldDeclaration = new MultiFieldDeclarator().makeFieldDecalaration(fieldNameSingle, contextedClass, typeElement);
        contextedClass.getGetMapMethodeDeclaration().getBody().orElseThrow(ProcessingCriteriaException::new)
                .addAndGetStatement("primitiveParam.put(\"" + fieldNameSingle + "\", this." + fieldNameSingle + "s)");

        return Collections.singletonList(fieldDeclaration);
    }


}
