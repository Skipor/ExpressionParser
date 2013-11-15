//package ru.skipor.MathLogicParser.Proof;
//
//import ru.skipor.MathLogicParser.Form.Form;
//
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
///**
// * User: Vladimir Skipor
// * Email: vladimirskipor@gmail.com
// * Date: 9/15/13
// * Time: 11:27 PM
// */
//public class Deduction {
//    private List<Form> statementsSystem;
//    private List<Form> statements;
//    private static ProofBank proofBank = new ProofBank();
//
//    public Deduction(Proof proof) {
//        this.statementsSystem = proof.statementsSystem;
//        this.statements = proof.statements;
//    }
//
//
//
//    public void apply() {
//        if (!ready()) {
//
//            final int alphaIndex = statementsSystem.size() - 1;
//            Form alpha = statementsSystem.get(alphaIndex);
//            statementsSystem.remove(alphaIndex);
//            Set<Form> statementsSystemSet = new HashSet<>(statementsSystem);
//            List<Form> futureStatements = new ArrayList<>(statementsSystem);
//            for (Form curentStatement : statements) {
//                if (statementsSystemSet.contains(curentStatement)) {
//                    continue;
//                } else if (AxiomsSystems.isAxiom(curentStatement)) {
//                    futureStatements.add(curentStatement);
//                } else if (curentStatement.equals(alpha)) {
//                    futureStatements.addAll(proofBank.getProofByName("f->f", alpha).statements);
//                } else if (Proof.isModusPonensOf(curentStatement,futureStatements))
//            }
//
//
//
//
//        }
//
//        }
//
//    public  boolean ready() {
//        return (statementsSystem == null || statementsSystem.isEmpty());
//    }
//
//
//    public Proof getProof() {
//        return new Proof(statements,statementsSystem);
//    }
//
//    public Proof getFinal() {
//        while (!ready()) {
//            apply();
//        }
//        return new Proof(statements, statementsSystem);
//    }
//}
