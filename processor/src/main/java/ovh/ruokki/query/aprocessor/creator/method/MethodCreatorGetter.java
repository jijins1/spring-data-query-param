package ovh.ruokki.query.aprocessor.creator.method;

import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.stmt.BlockStmt;
import ovh.ruokki.query.aprocessor.StringUtils;

public class MethodCreatorGetter {

    /**
     * Add a getter into class who return value from field
     *
     * @param classDeclaration
     * @param fieldDeclaration
     */
    public void addGetter(ClassOrInterfaceDeclaration classDeclaration, FieldDeclaration fieldDeclaration) {
        final VariableDeclarator fieldName = fieldDeclaration.getVariable(0);
        final MethodDeclaration methodDeclaration = classDeclaration.addMethod("get" + StringUtils.capitalize(fieldName.toString()), Modifier.Keyword.PUBLIC);
        methodDeclaration.setType(fieldDeclaration.getCommonType());
        final BlockStmt body = new BlockStmt();
        body.addAndGetStatement("return this." + fieldName);

        methodDeclaration.setBody(body);
    }

}
