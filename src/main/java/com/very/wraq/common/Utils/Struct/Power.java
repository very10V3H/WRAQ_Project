package com.very.wraq.common.Utils.Struct;

public class Power {
    private int Power1;
    private int Power2;
    private int Power3;
    private int Power4;

    public Power(int power1, int power2, int power3, int power4) {
        this.Power1 = power1;
        this.Power2 = power2;
        this.Power3 = power3;
        this.Power4 = power4;
    }

    public void setPower1(int power1) {
        Power1 = power1;
    }

    public void setPower2(int power2) {
        Power2 = power2;
    }

    public void setPower3(int power3) {
        Power3 = power3;
    }

    public void setPower4(int power4) {
        Power4 = power4;
    }

    public int getPower1() {
        return Power1;
    }

    public int getPower2() {
        return Power2;
    }

    public int getPower3() {
        return Power3;
    }

    public int getPower4() {
        return Power4;
    }

    public int get1Count() {
        int count = 0;
        int num = 1;
        if (Power1 == num) count++;
        if (Power2 == num) count++;
        if (Power3 == num) count++;
        if (Power4 == num) count++;
        return count;
    }

    public int get2Count() {
        int count = 0;
        int num = 2;
        if (Power1 == num) count++;
        if (Power2 == num) count++;
        if (Power3 == num) count++;
        if (Power4 == num) count++;
        return count;
    }

    public int get3Count() {
        int count = 0;
        int num = 3;
        if (Power1 == num) count++;
        if (Power2 == num) count++;
        if (Power3 == num) count++;
        if (Power4 == num) count++;
        return count;
    }

    public int get4Count() {
        int count = 0;
        int num = 4;
        if (Power1 == num) count++;
        if (Power2 == num) count++;
        if (Power3 == num) count++;
        if (Power4 == num) count++;
        return count;
    }

    public int get5Count() {
        int count = 0;
        int num = 5;
        if (Power1 == num) count++;
        if (Power2 == num) count++;
        if (Power3 == num) count++;
        if (Power4 == num) count++;
        return count;
    }

    public int get6Count() {
        int count = 0;
        int num = 6;
        if (Power1 == num) count++;
        if (Power2 == num) count++;
        if (Power3 == num) count++;
        if (Power4 == num) count++;
        return count;
    }

    public int get7Count() {
        int count = 0;
        int num = 7;
        if (Power1 == num) count++;
        if (Power2 == num) count++;
        if (Power3 == num) count++;
        if (Power4 == num) count++;
        return count;
    }

    public int get8Count() {
        int count = 0;
        int num = 8;
        if (Power1 == num) count++;
        if (Power2 == num) count++;
        if (Power3 == num) count++;
        if (Power4 == num) count++;
        return count;
    }
}
