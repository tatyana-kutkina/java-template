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
        Matrix m1 = new SparseMatrix("M1.txt");
        Matrix m2 = new SparseMatrix("M2.txt");
        Matrix expected = new SparseMatrix("RESULT.txt");
        assertEquals(expected, m1.mul(m2));
    }

    //маленькие спарс*дэнс
    @Test
    public void mulSD1() {
        Matrix m1 = new SparseMatrix("sm1.txt");
        Matrix m2 = new DenseMatrix("sm2.txt");
        Matrix expected = new SparseMatrix("sresult.txt");
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
}
