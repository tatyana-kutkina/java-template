package edu.spbu.matrix;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class SparseMatrixTest {

    //2 маленьких спарс
    @Test
    public void mulSS1() {
        Matrix m1 = new SparseMatrix("sm1.txt");
        Matrix m2 = new SparseMatrix("sm2.txt");
        Matrix expected = new SparseMatrix("sresult.txt");
        assertEquals(expected, m1.mul(m2));
    }

    //большая матрица
    @Test
    public void mulSS2() {
        Matrix m1 = new SparseMatrix("M11.txt");
        Matrix m2 = new SparseMatrix("M22.txt");
        Matrix expected = new SparseMatrix("RESULT1.txt");
        assertEquals(expected, m1.mul(m2));
    }

    //маленькие спарс*дэнс
    @Test
    public void mulSD1() {
        Matrix m1 = new SparseMatrix("sm1.txt");
        Matrix m2 = new DenseMatrix("sm2.txt");
        Matrix expected = new DenseMatrix("sresult.txt");
        assertEquals(expected, m1.mul(m2));
    }

    //маленькие дэнс*спарс
    @Test
    public void mulDS1() {
        Matrix m1 = new DenseMatrix("sm1.txt");
        Matrix m2 = new SparseMatrix("sm2.txt");
        Matrix expected = new SparseMatrix("sresult.txt");
        assertEquals(expected, m1.mul(m2));
    }

    //в ожидаемом есть ключ, кт нет в реал. результате
    @Test
    public void mulSS4() {
        Matrix m1 = new SparseMatrix("sm1.txt");
        Matrix m2 = new SparseMatrix("sm2.txt");
        Matrix expected = new SparseMatrix("wrongKeySet1.txt");
        assertNotEquals(expected, m1.mul(m2));
    }

    //в реал. результате есть ключ, кт нет в ожидаемом результате
    @Test
    public void mulSS3() {
        Matrix m1 = new SparseMatrix("sm1.txt");
        Matrix m2 = new SparseMatrix("wrongKeySet2.txt");
        Matrix expected = new SparseMatrix("sresult.txt");
        assertNotEquals(expected, m1.mul(m2));
    }

}
