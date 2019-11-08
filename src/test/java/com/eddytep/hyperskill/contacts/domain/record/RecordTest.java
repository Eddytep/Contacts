package com.eddytep.hyperskill.contacts.domain.record;

import org.junit.Test;

import static org.junit.Assert.*;

public class RecordTest {

    @Test
    public void setPhoneNumber() {

        String[] phones1 = {"+(1) 123 265Fa8", "+(1)-123-31651-65", "+0 (123) 513-651-5132", "123 456 789",
                "+1-(123)-321513-65165-AJNDcdsc", "+1 512 65 65 6546842", "+1 512", "123-(456)", "9",
                "+0 (123) 456-789-ABcd", "(123) 234 345-456", "2565282946845", "+987351351351"};
        for (String phone : phones1) {
            assertEquals(new OrganizationRecord("","", phone).getPhoneNumber(), phone);
        }
        String[] phones2 = {"+(1) 1 265Fa8", "+(1)-123-31651-6", "+0*(123)*513*651*5132",
                "-1-(123)-321513-65165-AJNDcdsc", "+1 512 65 65 654фыва2",
                "+0 (123) 456/789/ABcd", "(123)=234 345-456", "+7(321)351638435",};
        for (String phone : phones2) {
            assertNotEquals(new OrganizationRecord("","", phone).getPhoneNumber(), phone);
        }
    }
}