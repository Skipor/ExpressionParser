package ru.skipor.MathLogic.Tasks;

import ru.skipor.MathLogic.Form.Form;
import ru.skipor.MathLogic.Form.Parser.FormParser;
import ru.skipor.MathLogic.Proof.Proof;
import ru.skipor.MathLogic.Proof.ProofGenerator;

import java.io.FileWriter;

/**
 * User: Vladimir Skipor
 * Email: vladimirskipor@gmail.com
 * Date: 9/24/13
 * Time: 1:27 PM
 */
public class MainTest {


    public static void main(String[] args) throws Exception {

        final Form form = FormParser.parse("A&!A->(B|C)&!A");
//        final Form form = FormParser.parse("!!A->A");
        Proof proof = ProofGenerator.generate(form);
        assert (proof.check() == 0);
        try (FileWriter fileWriter = new FileWriter("resourceProofs/work.out")) {
            fileWriter.write(proof.toString(true));
        }


////        final Form form = FormParser.parse("(!B->!A)->(A->B)");
////        final Form form = FormParser.parse("((C->(!B->(!A->(!((A&!A)->((B|C)&!A))->(!((A&!A)->((B|C)&!A))->!((A&!A)->((B|C)&!A)))))))->(C->((!B->(!A->(!((A&!A)->((B|C)&!A))->(!((A&!A)->((B|C)&!A))->!((A&!A)->((B|C)&!A))))))->((!B->((!A->(!((A&!A)->((B|C)&!A))->(!((A&!A)->((B|C)&!A))->!((A&!A)->((B|C)&!A)))))->((!A->((!((A&!A)->((B|C)&!A))->(!((A&!A)->((B|C)&!A))->!((A&!A)->((B|C)&!A))))->((!((A&!A)->((B|C)&!A))->((!((A&!A)->((B|C)&!A))->!((A&!A)->((B|C)&!A)))->!((A&!A)->((B|C)&!A))))->(!((A&!A)->((B|C)&!A))->!((A&!A)->((B|C)&!A))))))->(!A->((!((A&!A)->((B|C)&!A))->((!((A&!A)->((B|C)&!A))->!((A&!A)->((B|C)&!A)))->!((A&!A)->((B|C)&!A))))->(!((A&!A)->((B|C)&!A))->!((A&!A)->((B|C)&!A))))))))->(!B->((!A->((!((A&!A)->((B|C)&!A))->(!((A&!A)->((B|C)&!A))->!((A&!A)->((B|C)&!A))))->((!((A&!A)->((B|C)&!A))->((!((A&!A)->((B|C)&!A))->!((A&!A)->((B|C)&!A)))->!((A&!A)->((B|C)&!A))))->(!((A&!A)->((B|C)&!A))->!((A&!A)->((B|C)&!A))))))->(!A->((!((A&!A)->((B|C)&!A))->((!((A&!A)->((B|C)&!A))->!((A&!A)->((B|C)&!A)))->!((A&!A)->((B|C)&!A))))->(!((A&!A)->((B|C)&!A))->!((A&!A)->((B|C)&!A))))))))))->(C->((!B->((!A->(!((A&!A)->((B|C)&!A))->(!((A&!A)->((B|C)&!A))->!((A&!A)->((B|C)&!A)))))->((!A->((!((A&!A)->((B|C)&!A))->(!((A&!A)->((B|C)&!A))->!((A&!A)->((B|C)&!A))))->((!((A&!A)->((B|C)&!A))->((!((A&!A)->((B|C)&!A))->!((A&!A)->((B|C)&!A)))->!((A&!A)->((B|C)&!A))))->(!((A&!A)->((B|C)&!A))->!((A&!A)->((B|C)&!A))))))->(!A->((!((A&!A)->((B|C)&!A))->((!((A&!A)->((B|C)&!A))->!((A&!A)->((B|C)&!A)))->!((A&!A)->((B|C)&!A))))->(!((A&!A)->((B|C)&!A))->!((A&!A)->((B|C)&!A))))))))->(!B->((!A->((!((A&!A)->((B|C)&!A))->(!((A&!A)->((B|C)&!A))->!((A&!A)->((B|C)&!A))))->((!((A&!A)->((B|C)&!A))->((!((A&!A)->((B|C)&!A))->!((A&!A)->((B|C)&!A)))->!((A&!A)->((B|C)&!A))))->(!((A&!A)->((B|C)&!A))->!((A&!A)->((B|C)&!A))))))->(!A->((!((A&!A)->((B|C)&!A))->((!((A&!A)->((B|C)&!A))->!((A&!A)->((B|C)&!A)))->!((A&!A)->((B|C)&!A))))->(!((A&!A)->((B|C)&!A))->!((A&!A)->((B|C)&!A))))))))))");
//        final Form form = FormParser.parse("(A->!B)->(!B->!A)");
//
//        List<Variable> variableList = CounterexampleGenerator.generate(form);
//        if (variableList != null) {
//            System.out.println(form.evaluate(null));
//            for (Variable variable : variableList) {
//                System.out.println(variable.name + " " + variable.getValue());
//            }
//        } else {
//            System.out.println("Always true");
//        }
//            int incorrectStatement = Proof.checkFile("input.txt");
//            System.out.println(incorrectStatement);

//        Deduction deduction = new Deduction(Proof.createProof("resourceProofs/work"));
////deduction.apply();
//        Proof proof = deduction.getProof();
//        proof.removeCopies();
//        assert (proof.check() == 0);
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter("resourceProofs/work.out"))) {
//
//            StringBuilder builder = new StringBuilder();
//            Form f = FormParser.parse("f");
//            Form fOrNotf = FormParser.parse("f|!f");
//
//            builder.append("f->f|!f").append("\n");
//            builder.append(ProofBank.getProofByName("(f->o)->(!o->!f)", f, fOrNotf)).append("\n");
//            builder.append("!(f|!f)->!f").append("\n");
//
//            Form notf = FormParser.parse("!f");
//            builder.append("!f->f|!f").append("\n");
//            builder.append(ProofBank.getProofByName("(f->o)->(!o->!f)", notf, fOrNotf)).append("\n");
//            builder.append("!(f|!f)->!!f").append("\n");
//            builder.append(FormHelper.insert(AxiomsSystems.formsOfSystemsOfAxioms[8], FormParser.parse("!(f|!f)"), FormParser.parse("!f")));
//            writer.write(builder.toString());
//            writer.write(proof.toString());
    }

//        Proof proof = new Proof("resourceProofs/cpded.txt");
//        System.out.println(proof.removeCopies());
//
//        assert (proof.check() == 0);
//
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter("resourceProofs/cp.out.txt"))) {
//            writer.write(proof.toString());
//        }

//            ProofBank proofBank = new ProofBank();
//            Set<Form> assumptionSet = new HashSet<>();
//            Form alpha = FormParser.parse("A&!A"), currentStatement = FormParser.parse("")
//
//            if (assumptionSet.contains(currentStatement) || AxiomsSystems.isAxiom(currentStatement)) {
//                futureStatements.addAll(proofBank.getProofByName("o->f", currentStatement, alpha).statements);
//            } else if (currentStatement.equals(alpha)) {
//                futureStatements.addAll(proofBank.getProofByName("f->f", alpha).statements);
//            } else {
//                Form antecedent = Proof.getModusPonensConditionalStatement(currentStatement,
//                        statements);
//                if (antecedent != null) {
//
//                    futureStatements.addAll(proofBank.getProofByName("f->p", alpha, antecedent, currentStatement).statements);
//                } else {
//                    System.out.println(statements.indexOf(currentStatement));
//                    for (Form st : futureStatements) {
//                        System.out.println(st);
//                    }
//
//                    throw new IllegalStateException(currentStatement.toString());
//
////                        return false;
//                }


//            }

}

