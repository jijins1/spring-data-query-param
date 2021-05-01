package com.ruokki.query.aprocessor.creator.field;

import com.github.javaparser.ast.body.FieldDeclaration;
import com.google.common.collect.Lists;
import com.ruokki.query.aprocessor.ContextedClass;
import com.ruokki.query.aprocessor.CriteriaProcessor;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.tools.Diagnostic;
import java.util.List;

/**
 * The type Field creator who will create criteria field for subentity.
 */
public class SubEntityFieldCreator implements FieldCreator {
    private final ProcessingEnvironment processingEnvironment;

    public SubEntityFieldCreator(ProcessingEnvironment processingEnvironment) {
        this.processingEnvironment = processingEnvironment;
    }

    @Override
    public Boolean canCreateFieldFromElement(Element fieldElement) {
        final OneToOne oneToOne = fieldElement.getAnnotation(OneToOne.class);
        final ManyToOne manyToOne = fieldElement.getAnnotation(ManyToOne.class);
        final OneToMany oneToMany = fieldElement.getAnnotation(OneToMany.class);
        final ManyToMany manyToMany = fieldElement.getAnnotation(ManyToMany.class);
        return oneToOne != null || manyToOne != null || oneToMany != null || manyToMany != null;
    }

    @Override
    public List<FieldDeclaration> createField(Element fieldElement, ContextedClass contextedClass) {
        final String fieldNameSingle = fieldElement.getSimpleName().toString() + "Id";

        final FieldDeclaration fieldDeclaration = new MultiFieldDeclarator().makeFieldDecalaration(fieldNameSingle, contextedClass, processingEnvironment.getElementUtils().getTypeElement(Long.class.getName()));
        contextedClass.getGetMapMethodeDeclaration().getBody().orElseThrow(ProcessingCriteriaException::new)
                .addAndGetStatement(CriteriaProcessor.SUB_ENTITY_PARAM + ".put(\"" + fieldNameSingle + "\", this." + fieldNameSingle + "s)");

        return Lists.newArrayList(fieldDeclaration);
    }
}
