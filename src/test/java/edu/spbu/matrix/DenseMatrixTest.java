package edu.spbu.matrix;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class DenseMatrixTest
{
  /**
   * ожидается 4 таких теста
   */
/*
  //все ок
  @Test
  public void mulDD1() {
    Matrix m1 = new DenseMatrix("m1.txt");
    Matrix m2 = new DenseMatrix("m2.txt");
    Matrix expected = new DenseMatrix("result.txt");
    assertEquals(expected, m1.mul(m2));
  }

  //у результата не такой же размер
  @Test
  public void mulDD2() {
    Matrix m1 = new DenseMatrix("m1.txt");
    Matrix m2 = new DenseMatrix("m2.txt");
    Matrix expected = new DenseMatrix("wrongResultSize.txt");
    assertNotEquals(expected, m1.mul(m2));
  }

  //у результата другое значение
  @Test
  public void mulDD3() {
    Matrix m1 = new DenseMatrix("m1.txt");
    Matrix m2 = new DenseMatrix("m2.txt");
    Matrix expected = new DenseMatrix("wrongResultVal.txt");
    assertNotEquals(expected, m1.mul(m2));
  }
  //одна из матриц неподходящего размера
  @Test(expected=RuntimeException.class)
  public void mulDD4() {
    Matrix m1 = new DenseMatrix("m1.txt");
    Matrix m2 = new DenseMatrix("wrongSize.txt");
    Matrix expected = new DenseMatrix("result.txt");
    assertEquals(expected, m1.mul(m2));
  }
*/
  @Test
  public void mulDD5() {
    Matrix m1 = new DenseMatrix("M11.txt");
    Matrix m2 = new DenseMatrix("M22.txt");
    Matrix m3 = new SparseMatrix("M11.txt");
    Matrix m4 = new SparseMatrix("M22.txt");
    Matrix m5 = m1.mul(m2);
    Matrix m6 = m3.mul(m4);
    assertEquals(m5,m6);
  }

}
