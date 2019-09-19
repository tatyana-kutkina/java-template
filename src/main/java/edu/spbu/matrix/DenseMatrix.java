package edu.spbu.matrix;

import java.io.*;
import java.util.*;

/*
Object{
  string toString();
  long HashCode; // нужно использовать, чтобы отсеять те объекты, у кт разный хэшкод
  boolean equals(Object o);

}
s.equals(s2) // сравнение s и s2

 equals{
  if(HashCode != o.HashCode)
    return false;
  else {...}
 }
 */

/*
List <> l = new List <Integer> (10);
  l.append(10);

*/


/**
 * Плотная матрица
 */

public class DenseMatrix implements Matrix
{

  /* double[][] deMatrix;
  int rows;
  int columns;
  public DenseMatrix(int rows, int columns){
    this.rows = rows;
    this.columns = columns;
    deMatrix = new double [rows] [columns];
  }
*/


  /**
   * загружает матрицу из файла
   * @param fileName
   */
  public DenseMatrix(String fileName) {
    try{
      File file = new File (fileName);
      FileReader fileReader = new FileReader(file);
      BufferedReader bufferedReader = new BufferedReader(fileReader);

      String line ;
      while((line=bufferedReader.readLine()) != null){

      }

      bufferedReader.close();
    } catch(IOException ex){
      System.out.println(ex.getMessage());
    }



  }
  /**
   * однопоточное умнджение матриц
   * должно поддерживаться для всех 4-х вариантов
   *
   * @param o
   * @return
   */
  @Override public Matrix mul(Matrix o)
  {
    return null;
  }

  /**
   * многопоточное умножение матриц
   *
   * @param o
   * @return
   */
  @Override public Matrix dmul(Matrix o)
  {
    return null;
  }

  /**
   * спавнивает с обоими вариантами
   * @param o
   * @return
   */
  @Override public boolean equals(Object o) {

    return false;
  }

}
