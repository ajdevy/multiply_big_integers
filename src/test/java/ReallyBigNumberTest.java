import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ReallyBigNumberTest {

    @Test
    public void testMultiplyZeros() {
        ReallyBigNumber first = new ReallyBigNumber("0");
        ReallyBigNumber second = new ReallyBigNumber("0");

        assertEquals("0", first.multiply(second).toString());
    }

    @Test
    public void testMultiplyPositiveNumbers() {
        ReallyBigNumber first = new ReallyBigNumber("12");
        ReallyBigNumber second = new ReallyBigNumber("12");

        assertEquals("144", first.multiply(second).toString());
    }

    @Test
    public void testMultiplyNegativeToPositiveNumber() {
        ReallyBigNumber first = new ReallyBigNumber("10");
        ReallyBigNumber second = new ReallyBigNumber("-10");

        assertEquals("-100", first.multiply(second).toString());
    }

    @Test
    public void testMultiplyTwoNegativeNumbers() {
        ReallyBigNumber first = new ReallyBigNumber("-10");
        ReallyBigNumber second = new ReallyBigNumber("-10");

        assertEquals("100", first.multiply(second).toString());
    }
}
