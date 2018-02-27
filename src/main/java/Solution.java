import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class Solution {

    public static void main(String[] args) {
        ReallyBigNumber first = new ReallyBigNumber("1200");
        ReallyBigNumber second = new ReallyBigNumber("-1200");

        ReallyBigNumber result = first.multiply(second);

        System.out.println("result = " + result);
    }
}

class ReallyBigNumber {

    private boolean isNegative = false;

    private List<Integer> digits = new ArrayList<>();

    public ReallyBigNumber(final String numberString) {
        final String digitsString;
        if (numberString.startsWith("-")) {
            isNegative = true;
            digitsString = numberString.substring(1, numberString.length());
        } else {
            digitsString = numberString;
        }

        final List<Integer> newDigits =
                digitsString.chars()
                        .mapToObj(character -> Character.digit(character, Character.MAX_RADIX))
                        .collect(Collectors.toList());

        digits.addAll(newDigits);
    }

    public ReallyBigNumber multiply(ReallyBigNumber anotherNumber) {
        final ReallyBigNumber result = new ReallyBigNumber("0");

        //for every current digit from the end
        for (int i = anotherNumber.digits.size() - 1; i >= 0; i--) {
            final Integer anotherDigit = anotherNumber.digits.get(i);

            final Integer anotherDigitPowerOfTen = anotherNumber.digits.size() - 1 - i;

            //for every current digit from the end
            for (int j = digits.size() - 1; j >= 0; j--) {
                //get the current digit
                final Integer currentDigit = digits.get(j);
                final Integer currentDigitPowerOfTen = digits.size() - 1 - j;

                //multiply the current with another digit
                final Integer digitResult = anotherDigit * currentDigit;

                //assuming the max digit multiplication would be only out of two digits
                if (digitResult >= 10) {
                    //two digits in number
                    final Integer firstNewDigit = Character.getNumericValue(digitResult.toString().charAt(0));

                    final Integer secondNewDigit = Character.getNumericValue(digitResult.toString().charAt(1));

                    final int secondDigitPowerOfTen = currentDigitPowerOfTen + anotherDigitPowerOfTen;
                    //first add the second digit
                    result.addToDigit(secondDigitPowerOfTen, secondNewDigit);

                    final int firstDigitPowerOfTen = secondDigitPowerOfTen + 1;
                    //then add the first digit
                    result.addToDigit(firstDigitPowerOfTen, firstNewDigit);


                } else {
                    //only one digit in number
                    Integer firstDigit = Character.getNumericValue(digitResult.toString().charAt(0));
                    final int powerOfTen = currentDigitPowerOfTen + anotherDigitPowerOfTen;

                    result.addToDigit(powerOfTen, firstDigit);
                }
            }
        }

        //if both numbers are negative, the result is positive
        if (isNegative && anotherNumber.isNegative) {
            result.isNegative = false;
            //or if one of the numbers is negative, the result is negative
        } else if (isNegative || anotherNumber.isNegative) {
            result.isNegative = true;
        }
        return result;
    }

    private void addToDigit(Integer powerOfTen, Integer newOneDigitInt) {
        //if new digit to add is 0, no need to add it
        final Integer digitAtIndex;
        if (digits.size() > powerOfTen) {
            digitAtIndex = digits.get(digits.size() - 1 - powerOfTen);
        } else {
            digitAtIndex = 0;
        }

        final Integer result = digitAtIndex + newOneDigitInt;

        final int firstDigitIndexToSet = digits.size() - 1 - powerOfTen;

        //if has more than two digits, assuming the multiplication result of two single digits is < 100
        if (result >= 10) {
            final Integer firstDigit = Character.getNumericValue(result.toString().charAt(0));

            //set the first digit
            digits.set(firstDigitIndexToSet, firstDigit);

            //add the
            final Integer secondDigit = Character.getNumericValue(
                    result.toString().charAt(0));

            // put the first digit of the current number
            // into the next place to left after the second digit
            addToDigit(powerOfTen + 1, secondDigit);

        } else {
            //only one digit
            final Integer firstDigit = result;

            if (digits.size() > powerOfTen) {
                digits.set(firstDigitIndexToSet, firstDigit);
            } else {
                final List<Integer> newDigitList = new ArrayList<>();
                newDigitList.add(firstDigit);
                newDigitList.addAll(digits);
                digits = newDigitList;
            }
        }
    }

    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder();

        for (Integer digit : digits) {
            result.append(digit.toString());
        }
        final String negativitySign = isNegative ? "-" : "";
        return negativitySign + result.toString();
    }
}