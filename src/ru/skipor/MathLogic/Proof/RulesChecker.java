package ru.skipor.MathLogic.Proof;

import ru.skipor.MathLogic.Form.Form;
import ru.skipor.MathLogic.Form.Logical.BinaryNode;
import ru.skipor.MathLogic.Form.Logical.BinaryOperation;
import ru.skipor.MathLogic.Form.Logical.QuantifierNode;
import ru.skipor.MathLogic.Form.Logical.QuantifierOperation;

import java.util.*;

/**
 * User: Vladimir Skipor
 * Email: vladimirskipor@gmail.com
 * Date: 4/21/14
 * Time: 10:22 PM
 */
class RulesChecker {
        private String lastErrorMessage;

    public String getLastErrorMessage() {
        return lastErrorMessage;
    }

    private final Map<Form, List<Form>> mpMap;
    private final Set<Form> statemetnsSet;

    RulesChecker() {
        mpMap = new HashMap<>();
        statemetnsSet = new HashSet<>();
    }

    RulesChecker(int size) {
        mpMap = new HashMap<>(size);
        statemetnsSet = new HashSet<>();
    }

    public void add(Form statement) {
        statemetnsSet.add(statement);
        if (statement instanceof BinaryNode) {
            final Form ra = ((BinaryNode) statement).rightArgument;
            final Form la = ((BinaryNode) statement).leftArgument;
            if (!mpMap.containsKey(ra)) {

                final ArrayList<Form> list = new ArrayList<>();
                list.add(la);
                mpMap.put(ra, list);
            } else {
                mpMap.get(ra).add(la);
            }
        }

    }

    public void addAll(Collection<? extends Form> forms) {
        for (Form form : forms) {
            add(form);
        }
    }

    public boolean isModusPonens(Form statement) {
        List<Form> leftParts = mpMap.get(statement);
        if (leftParts != null) {
            for (ListIterator<Form> formListIterator = leftParts.listIterator(leftParts.size()); formListIterator.hasPrevious(); ) {
                final Form form = formListIterator.previous();
                if (statemetnsSet.contains(form)) {
                    return true;
                }
            }
        }

        return false;

    }

    public boolean isExistentialIntroduction(Form statement) {
        if (statement instanceof BinaryNode
                && ((BinaryNode) statement).operation.equals(BinaryOperation.ENTAILMENT)
                && ((BinaryNode) statement).leftArgument instanceof QuantifierNode
        && ((QuantifierNode) ((BinaryNode) statement).leftArgument).operation.equals(QuantifierOperation.EXISTENTIAL) ){
            final QuantifierNode leftArgument = (QuantifierNode) ((BinaryNode) statement).leftArgument;
            final Form rightArgument = ((BinaryNode) statement).rightArgument;
            final BinaryNode shouldExist = new BinaryNode(
                    leftArgument.argument
                    , rightArgument
                    , BinaryOperation.ENTAILMENT);
            if (statemetnsSet.contains(shouldExist)) {
                if (
                        !rightArgument.containsVariableAsFree(leftArgument.boundingVariable)) {

                    return true;
                } else {
                    lastErrorMessage = "переменная " + leftArgument.boundingVariable.toString() + " входит свободно в формулу " + rightArgument.toString() + ".";
                    return false;
                }
            } else {
                return false;
            }
        }
        return false;
    }

    public boolean isUniversalIntroduction(Form statement) {


        if (statement instanceof BinaryNode
                && ((BinaryNode) statement).operation.equals(BinaryOperation.ENTAILMENT)
                && ((BinaryNode) statement).rightArgument instanceof QuantifierNode
                && ((QuantifierNode) ((BinaryNode) statement).rightArgument).operation.equals(QuantifierOperation.UNIVERSAL) ){
            final QuantifierNode rightArgument = (QuantifierNode) ((BinaryNode) statement).rightArgument;
            final Form leftArgument = ((BinaryNode) statement).leftArgument;
            final BinaryNode shouldExist =  new BinaryNode(
                    leftArgument
                    , rightArgument.argument
                    , BinaryOperation.ENTAILMENT
            );
            if (statemetnsSet.contains(shouldExist)) {
                if (
                        !leftArgument.containsVariableAsFree(rightArgument.boundingVariable)) {

                    return true;
                } else {
                    lastErrorMessage = "переменная " + rightArgument.boundingVariable.toString() + " входит свободно в формулу " + leftArgument.toString() + ".";
                    return false;
                }
            } else {
                return false;
            }
        }
        return false;
    }

    public void resetErrorMessage() {
        lastErrorMessage = null;
    }


    public Form getModusPonensConditionalStatement(Form consequent) {
        List<Form> leftParts = mpMap.get(consequent);
        if (leftParts != null) {
            for (ListIterator<Form> formListIterator = leftParts.listIterator(leftParts.size()); formListIterator.hasPrevious(); ) {
                final Form form = formListIterator.previous();
                if (statemetnsSet.contains(form)) {
                    return form;
                }
            }
        }

        return null;

    }
}
