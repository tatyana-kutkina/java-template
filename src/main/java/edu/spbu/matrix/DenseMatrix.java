package edu.spbu.matrix;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Плотная матрица
 */
public class DenseMatrix implements Matrix
{

  public static final int ARRAY_SIZE = 2000;
  double[][] deMatrix;
  int rows;
  int columns;

  /**
   * загружает матрицу из файла
   * @param fileName
   */
  public DenseMatrix(String fileName) {
    try(BufferedReader bufReader = new BufferedReader(new FileReader(fileName))) {

      String line;
      ArrayList<Double[]> list = new ArrayList<>();
      Double[] row;
      int count=0;

      if((line=bufReader.readLine())!=null){
        String[] st = line.split(" ");
        count = st.length;
        row = new Double[count];
        for(int i=0;i<st.length;i++)
          row[i]=Double.parseDouble(st[i]);
        list.add(row);
      }

      while((line=bufReader.readLine())!=null){
        String[] st = line.split(" ");

        if(st.length!=count){
          throw new RuntimeException("Некорректно задана матрица: строки разных размеров");
        }

        row = new Double[st.length];
        for(int i=0;i<st.length;i++)
          row[i]=Double.parseDouble(st[i]);
        list.add(row);

      }
      deMatrix=new double[list.size()][count];
      columns=count;
      rows=list.size();

      for(int i=0;i<rows;i++)
        for(int j=0;j<columns;j++)
          deMatrix[i][j]=list.get(i)[j];
    }

    catch(IOException e) {
      System.out.println("Ошибка чтения файла.\n" + e.getMessage());
    }

  }

    public DenseMatrix(int rows, int columns){
        this.rows = rows;
        this.columns = columns;
        deMatrix = new double [rows] [columns];
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
    if(o instanceof DenseMatrix){
      if(this.columns!=((DenseMatrix)o).rows){
        throw new RuntimeException("Введена неправильных размеров матрица");
      }
      DenseMatrix result = new DenseMatrix(this.rows, ((DenseMatrix)o).columns);
      DenseMatrix o1 = ((DenseMatrix)o).transp();
      for(int i=0;i<this.rows;i++) {
          for (int j = 0; j < result.columns; j++) {
              for (int k = 0; k < this.columns; k++) {
                  result.deMatrix[i][j] += this.deMatrix[i][k] * o1.deMatrix[j][k];
              }
          }
      }
      return (result);
    }
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

  public int hashCode(){
     return Arrays.hashCode(deMatrix);
  }

  /**
   * спавнивает с обоими вариантами
   * @param o
   * @return
   */

  @Override public boolean equals(Object o) {

    if(this==o)
      return true;
    /*
    if(this.hashCode()!=(o.hashCode())){
      System.out.printf("%d %d ", this.hashCode(), o.hashCode());
      return false;
    }
    */
    if(o instanceof DenseMatrix) {
      DenseMatrix dM = (DenseMatrix) o;

      if (rows != dM.rows || columns != dM.columns)
        return false;
      else {
        for (int i = 0; i < rows; i++)
          for (int j = 0; j < columns; j++) {
            if (deMatrix[i][j] != dM.deMatrix[i][j])
              return false;
          }
        return true; // не найдено неравных элементов
      }
    }
      else return false;
  }

  @Override
  public String toString() {
    StringBuilder str = new StringBuilder();
    for(int i=0; i<rows;i++) {
      for (int j = 0; j < columns; j++) {
        str.append(deMatrix[i][j]);
        str.append(" ");
      }
      str.append("\n");
    }
    return (str.toString());
  }

  //транспонирование матрицы
  private DenseMatrix transp(){
    DenseMatrix res = new DenseMatrix(columns,rows);
    for(int i=0;i<rows;i++)
      for(int j=0;j<columns;j++)
      res.deMatrix[j][i]=deMatrix[i][j];
      return res;
  }

  /*public static void main (String[] args){
    Matrix m1 = new DenseMatrix("m1.txt");
    Matrix m2 = new DenseMatrix("wrongSize.txt");
    System.out.println(m1.mul(m2));
  }*/

}
