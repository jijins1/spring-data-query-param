package com.ruokki.query.aprocessor.creator.field;

import com.github.javaparser.ast.body.FieldDeclaration;
import com.ruokki.query.aprocessor.ContextedClass;

import javax.lang.model.element.Element;
import java.util.List;

public interface FieldCreator {


    /**
     * Verifie si le field creator peut creer un/des fields pour le fieldElement En Parametre
     *
     * @param fieldElement
     * @return
     */
    Boolean canCreateFieldFromElement(Element fieldElement);

    /**
     * Creates fields in classOrInterfaceDeclaration
     *
     * @param fieldElement
     * @param contextedClass
     * @return created and added field
     */
    List<FieldDeclaration> createField(Element fieldElement, ContextedClass contextedClass);
}
