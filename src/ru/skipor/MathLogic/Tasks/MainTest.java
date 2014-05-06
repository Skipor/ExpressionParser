package ru.skipor.MathLogic.Tasks;

import ru.skipor.MathLogic.Form.Form;
import ru.skipor.MathLogic.Form.Parser.FormParser;
import ru.skipor.MathLogic.Proof.AxiomsSystems;

/**
 * User: Vladimir Skipor
 * Email: vladimirskipor@gmail.com
 * Date: 9/24/13
 * Time: 1:27 PM
 */
public class MainTest {


    public static void main(String[] args) throws Exception {

//        System.out.println(AxiomsSystems.isAxiom(Form.create("B->?x(B)")));
        final Form form = FormParser.parse("@t(P(z))->?x@tP(x)");
        System.out.println(AxiomsSystems.isAxiom(form));
//        Form statement = form;
//        final Form la = ((BinaryNode) statement).leftArgument;
//        final Form ra = ((BinaryNode) statement).rightArgument;
//        Form noSub, withSub;
//        Variable variable;
//        boolean flag;
//        if (la instanceof QuantifierNode && ((QuantifierNode) la).operation.equals(QuantifierOperation.UNIVERSAL)) {
//            variable = ((QuantifierNode) la).boundingVariable;
//            noSub = ((QuantifierNode) la).argument;
//            withSub = ra;
//        } else if (ra instanceof QuantifierNode && ((QuantifierNode) ra).operation.equals(QuantifierOperation.EXISTENTIAL)) {
//            variable = ((QuantifierNode) ra).boundingVariable;
//            noSub = ((QuantifierNode) ra).argument;
//            withSub = la;
//        } else {
//            flag = false;
//            System.out.println("erororo");
//
//            return;
//        }
//        if(!noSub.containsVariableAsFree(variable)){
//            flag = true;
//        }
//        Term sub = noSub.getOnlySubstitution(withSub, variable);
//        System.out.println(sub); // todo remove
////        final Form form = FormParser.parse("@xP(x)->P(x)");
////        final Form form = FormParser.parse("(x+0)");
////        Term term = FormParser.parseTerm("c(a, b,c)");
//        System.out.println(form);
//        System.out.println(AxiomsSystems.isAxiom(form));
//        System.out.println(form.getOnlySubstitution(form, new Variable("x")));
//        Form p1 = new Predicate("P", Arrays.asList((Term) new Variable("x")));
//        System.out.println(p1.equalsButSubstitutions(p1, new Substitutions()));
//        System.out.println(new Variable("x").equalsButSubstitutions(new Variable("x"), new Substitutions()));
//        System.out.println(term);
//        System.out.println(term.getVariables());
//        System.out.println(new Variable("a").containsVariable(new Variable("b")));
//        System.out.println(form.containsVariableAsFree(new Variable("c")));
//        Term a = FormParser.parseTerm("c(b1(a ,c), b ,c)");
//        Term b = FormParser.parseTerm("c(b1(a ,c(b1(a ,c), b ,c)), b ,c(b1(a ,c), b ,c))");
//        Substitutions substitutions = new Substitutions();
//        System.out.println(a.equalsButSubstitutions(b, substitutions));
//        System.out.println(substitutions.getVariableSubsitutions());
//
//        substitutions = new Substitutions();
//        Form c = FormParser.parse("a = b");
//        Form d = FormParser.parse("c(b1, b2, b3) = g");
//        System.out.println(c.equalsButSubstitutions(d, substitutions));
//        System.out.println(substitutions.getVariableSubsitutions());
//        System.out.println(substitutions.getPredicateSubstitutions());
//        List<Form> axi = FormParser.parseForms("A(0, y)&(@x(A(x,y) -> A(x', y))) -> A(x,y), A->B");
//        System.out.println(axi);
//        System.out.println(Proof.createProof("testProofs/formalMath0.txt").check());
//        System.out.println(Proof.getLastErrorMessage());
//        System.out.println(Form.create("?z(A(y))").isFreeToSubstituteFor(new Variable("z"), new Variable("y")));
//        final Form logicalOperation = FormParser.parse("!!A->A");
//        System.out.println()


//        Proof proof = Proof.createProof("resourceProofs/work");
//        if (proof.check() != 0) {
//            System.out.println(Proof.getLastErrorMessage());
//        }
//        Deduction deduction = new Deduction(proof);
//        if(!deduction.apply()) {
//            System.out.println(deduction.getErrorMessage());
//        }
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter("resourceProofs/work.out"))) {
//            writer.write(deduction.getProof().toString());
//        }


    }



}

