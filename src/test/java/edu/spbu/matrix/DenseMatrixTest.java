package edu.spbu.matrix;

import edu.spbu.MatrixGenerator;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class DenseMatrixTest
{
  /**
   * ожидается 4 таких теста
   */


  //все ок
  @Test
  public void mulDD1() {
    Matrix m1 = new DenseMatrix("BigDense1");
    Matrix m2 = new DenseMatrix("BigDense2");
    Matrix expected = new DenseMatrix("BigDenseResult");
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

  //4 потока
  @Test
  public void dmulDD1() {
    Matrix m1 = new DenseMatrix("BigDense1");
    Matrix m2 = new DenseMatrix("BigDense2");
    Matrix expected = new DenseMatrix("BigDenseResult");
    assertEquals(expected, m1.dmul(m2));
  }

  @Test
  public void mulDDEx0() {
    try{
      new MatrixGenerator(1, 1, "Generated1.txt", 500).generate();
      new MatrixGenerator(1, 1, "Generated2.txt", 500).generate();
    }
    catch(IOException e){
      e.printStackTrace();
    }

    Matrix m1 = new DenseMatrix("Generated1.txt");
    Matrix m2 = new DenseMatrix("Generated2.txt");

    long start=System.currentTimeMillis();
    Matrix M1=m1.mul(m2);
    long finish=System.currentTimeMillis();
    System.out.println(finish-start);

    start = System.currentTimeMillis();
    Matrix M2=m1.dmul(m2);
    finish = System.currentTimeMillis();
    System.out.println(finish-start);

    assertEquals(M1, M2);
  }

}
