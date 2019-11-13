package edu.spbu.matrix;

import java.awt.*;
import java.io.*;
import java.nio.channels.ScatteringByteChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Разреженная матрица
 */
public class SparseMatrix implements Matrix {
  private Point key;
  int rows, columns;
  Map<Point, Double> val;

  //конструктор sparse матрицы
  public SparseMatrix(int rows, int columns) {
    this.rows = rows;
    this.columns = columns;
    val = new HashMap<>();
  }


  /**
   * загружает матрицу из файла
   *
   * @param fileName
   */
  public SparseMatrix(String fileName) {
    try (BufferedReader bufReader = new BufferedReader(new FileReader(fileName))) {

      String line;
      int count = 0;
      Double[] row;
      int height = 0;

      if ((line = bufReader.readLine()) != null) {
        String[] st = line.split(" ");
        count = st.length;
        val = new HashMap<>();
        row = new Double[count];
        for (int i = 0; i < count; i++) {
          row[i] = Double.parseDouble(st[i]);
          if (row[i] != 0)
            val.put(new Point(0, i), row[i]);
        }
        height++;
      }

      while ((line = bufReader.readLine()) != null) {
        String[] st = line.split(" ");

        if (st.length != count)
          throw new RuntimeException("Некорректно задана матрица: строки разных размеров");

        row = new Double[count];

        for (int i = 0; i < count; i++) {
          row[i] = Double.parseDouble(st[i]);
          if (row[i] != 0)
            val.put(new Point(height, i), row[i]);
        }
        height++;
      }
      rows = height;
      columns = count;
    } catch (IOException e) {
      System.out.println("Ошибка чтения файла.\n" + e.getMessage());
    }

  }

  /**
   * однопоточное умножение матриц
   * должно поддерживаться для всех 4-х вариантов
   *
   * @param o
   * @return
   */

  @Override
  public Matrix mul(Matrix o) {
    if (o instanceof SparseMatrix) {

      if (this.columns != ((SparseMatrix)o).rows) {
        throw new RuntimeException("Введена неправильных размеров матрица");
      }
      SparseMatrix result = new SparseMatrix(this.rows, ((SparseMatrix)o).columns);
      SparseMatrix sM = ((SparseMatrix) o).transp();

      for (Point key : val.keySet()) {
        for (int i = 0; i < sM.rows; i++) {
          Point p1 = new Point(i, key.y);
          if (sM.val.containsKey(p1)) {
            Point p2 = new Point(key.x, i);
            if (result.val.containsKey(p2)) {
              double t = result.val.get(p2) + val.get(key) * sM.val.get(p1);
              result.val.put(p2, t);
            } else {
              double t = val.get(key) * sM.val.get(p1);
              result.val.put(p2, t);
            }
          }
        }
      }
      return result;
    }

    if (o instanceof DenseMatrix) {

      if(this.columns!=((DenseMatrix)o).rows){
        throw new RuntimeException("Введена неправильных размеров матрица");
      }

      SparseMatrix result = new SparseMatrix(this.rows, ((DenseMatrix)o).columns);
      DenseMatrix dM = (DenseMatrix) o.transp();

      for (Point key : val.keySet()) {
        for (int i = 0; i < dM.rows; i++) {
          if (dM.deMatrix[i][key.y] != 0) {
            Point p = new Point(key.x, i);
            if (result.val.containsKey(p)) {
              double t = result.val.get(p) + val.get(key) * dM.deMatrix[i][key.y];
              result.val.put(p, t);
            } else {
              double t = val.get(key) * dM.deMatrix[i][key.y];
              result.val.put(p, t);
            }
          }
        }
      }
      return result;
    }

    return null;
  }

  /**
   * многопоточное умножение матриц
   *
   * @param o
   * @return
   */
  @Override
  public Matrix dmul(Matrix o) {
    return null;
  }

  @Override
  public int hashCode() {
    return Objects.hash(key, rows, columns, val);
  }

  /**
   * сравнивает с обоими вариантами
   *
   * @param o
   * @return
   */
  @Override
  public boolean equals(Object o) {

    if(this==o)
      return true;

    if(o instanceof SparseMatrix){

      if(this.hashCode()!=(o.hashCode())){
        System.out.printf("%d/n", this.hashCode());
        System.out.printf("%d/n", o.hashCode());
        return false;
      }

      SparseMatrix sM = (SparseMatrix) o;

      if (rows != sM.rows || columns != sM.columns)
        return false;
      if(val.size()==sM.val.size()){
        for(Point key: val.keySet()){
          if(!sM.val.containsKey(key))
            return false;
          if (Math.abs(val.get(key) - sM.val.get(key)) != 0){
            return false;
          }
        }
        return true;
      }
      return false;
    }

    if(o instanceof DenseMatrix){
      DenseMatrix dM = (DenseMatrix) o;

      if (rows != dM.rows || columns != dM.columns)
        return false;

      int count=0;
      for(int i=0;i<dM.rows;i++)
        for(int j=0;j<dM.columns;j++)
          if(dM.deMatrix[i][j]!=0)
            count++;
      if(count==val.size()){
        for(Point key: val.keySet()){
          if(dM.deMatrix[key.x][key.y]==0)
            return false;
          if(dM.deMatrix[key.x][key.y]!=val.get(key))
            return false;
        }
        return true;
      }
      return false;
    }

    return false;
  }

  //транспонирование матрицы
  @Override
  public SparseMatrix transp() {
    SparseMatrix res = new SparseMatrix(columns, rows);
    for (Point key : val.keySet()) {
      res.val.put(new Point(key.y, key.x), val.get(key));
    }
    return res;
  }

  @Override
  public String toString() {
    StringBuilder str = new StringBuilder();
    for (int i = 0; i < rows; i++) {
      str.append("\n");
      for (int j = 0; j < columns; j++) {
        Point key = new Point(i, j);
        if (val.containsKey(key)) {
          str.append(val.get(key));
          str.append(" ");
        } else {
          str.append(0);
          str.append(" ");
        }
      }

    }
    return (str.toString());
  }
  /*
  public static void main(String[] args) {
    Matrix m1 = new SparseMatrix("M1.txt");
    Matrix m2 = new DenseMatrix("M2.txt");
    //System.out.println(m1.mul(m2));
    try (FileWriter writer = new FileWriter("wrResult.txt")) {
      Matrix m3 = m1.mul(m2);
      m3.toString();
      writer.write(m3.toString());

    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }*/

}
