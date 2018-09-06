package fish.payara.maventest;

import org.junit.Assert;
import org.junit.Test;

public class SomeClassTest {
    
    @Test
    public void testDoSomething() {
        SomeClass sc = new SomeClass();
        Assert.assertEquals("Hello World!", sc.saySomething());
    }
}
