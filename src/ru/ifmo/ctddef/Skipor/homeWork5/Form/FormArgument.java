package ru.ifmo.ctddef.Skipor.homeWork5.Form;

/**
 * Created with IntelliJ IDEA.
 * User: vladimirskipor
 * Date: 26.05.13
 * Time: 14:56
 * To change this template use File | Settings | File Templates.
 */
public interface FormArgument {
    FormArgument plus(FormArgument rightArgument);

    FormArgument minus(FormArgument rightArgument);

    FormArgument times(FormArgument rightArgument);

    FormArgument divide(FormArgument rightArgument);

    FormArgument mod(FormArgument rightArgument);

    FormArgument pow(FormArgument rightArgument);

    FormArgument parse(String value);

    String toString();

}

//class BigInt implements FormArgument {
//    private static BigInteger value;
//
//}
