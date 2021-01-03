/*
   Copyright 2021 Martin Heywang

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package io.github.martinheywang.products.api.model.exception;

import java.math.BigInteger;
import java.text.MessageFormat;

/**
 * A MoneyException is an exception that occurs when an action, from the user or
 * the program wants the money amount. If it goes under 0 or any defined value
 * in the program, this exception occurs.
 */
public final class MoneyException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 5737118311047978775L;

    /**
     * Creates a new MoneyException with a default message.
     */
    public MoneyException() {
        super("Vous n'avez pas assez d'argent !");
    }

    /**
     * Creates a MoneyException with a specific message using the given
     * informations.
     * 
     * @param beforeTransaction the amount of money before the transaction.
     * @param toSubtract        the amount of money that wanted to be subtracted.
     */
    public MoneyException(BigInteger beforeTransaction, BigInteger toSubtract) {
        super(MessageFormat.format("Vous n''avez pas assez d''argent ! ({0} - {1} < 0)", beforeTransaction, toSubtract.negate()));
    }

}
