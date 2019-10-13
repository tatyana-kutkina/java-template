package edu.spbu.matrix;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class MatrixTest
{
  /**
   * ожидается 4 таких теста
   */
  @Test
  public void mulDD1() {
    Matrix m1 = new DenseMatrix("m1.txt");
    Matrix m2 = new DenseMatrix("m2.txt");
    Matrix expected = new DenseMatrix("result.txt");
    assertEquals(expected, m1.mul(m2));
  }

  @Test
  public void mulDD2() {
    Matrix m1 = new DenseMatrix("m1.txt");
    Matrix m2 = new DenseMatrix("m2.txt");
    Matrix expected = new DenseMatrix("wrongResultSize.txt");
    assertNotEquals(expected, m1.mul(m2));
  }

  @Test
  public void mulDD3() {
    Matrix m1 = new DenseMatrix("m1.txt");
    Matrix m2 = new DenseMatrix("m2.txt");
    Matrix expected = new DenseMatrix("wrongResultVal.txt");
    assertNotEquals(expected, m1.mul(m2));
  }

  @Test(expected=RuntimeException.class)
  public void mulDD4() {
    Matrix m1 = new DenseMatrix("m1.txt");
    Matrix m2 = new DenseMatrix("wrongSize.txt");
    Matrix expected = new DenseMatrix("result.txt");
    assertEquals(expected, m1.mul(m2));
  }

  @Test
  public void mulDD5() {
    Matrix m1 = new DenseMatrix("m11.txt");
    Matrix m2 = new DenseMatrix("m22.txt");
    Matrix expected = new DenseMatrix("result2.txt");
    assertEquals(expected, m1.mul(m2));
    //System.out.println(m1.mul(m2));
  }

}
