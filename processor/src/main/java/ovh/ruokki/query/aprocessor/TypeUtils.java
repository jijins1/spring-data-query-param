package ovh.ruokki.query.aprocessor;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

public class TypeUtils {

    private ProcessingEnvironment processingEnvironment;

    public TypeUtils(ProcessingEnvironment processingEnvironment) {
        this.processingEnvironment = processingEnvironment;
    }

    public  Boolean isSubTypeOf(Element fieldElement, Class<?> numberClass) {
        final Types typeUtils = processingEnvironment.getTypeUtils();
        final Elements elementUtils = processingEnvironment.getElementUtils();
        final TypeMirror numberTypeMirror = elementUtils.getTypeElement(numberClass.getName()).asType();

        return typeUtils.isSubtype(fieldElement.asType(), numberTypeMirror);
    }

}
