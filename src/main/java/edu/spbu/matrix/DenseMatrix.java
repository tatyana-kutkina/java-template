package edu.spbu.matrix;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

class DMuller implements Runnable{

  int rows,columns;
  int end, start;
  DenseMatrix right, left,res;

  DMuller(int indexS, int indexEn,int columns, DenseMatrix right, DenseMatrix left, DenseMatrix res ){
    this.end = indexEn;
    this.start = indexS;
    this.columns = columns;
    this.right = right;
    this.left = left;
    this.res = res;
    this.rows = indexEn-indexS;
  }

  @Override
  public void run(){
    for(int i=start;i<end;i++) {
      for (int j = 0; j < res.columns; j++) {
        for (int k = 0; k < this.columns; k++) {
          res.deMatrix[i][j] += left.deMatrix[i][k] * right.deMatrix[j][k];
        }
      }
    }
  }

}


/**
 * Плотная матрица
 */
public class DenseMatrix implements Matrix
{
  double[][] deMatrix;
  int rows;
  int columns;

  //конструктор dense матрицы
  public DenseMatrix(int rows, int columns){
    this.rows = rows;
    this.columns = columns;
    deMatrix = new double [rows] [columns];
  }

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


  /**
   * однопоточное умножение матриц
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

    if(o instanceof SparseMatrix){

      if(this.columns!=((SparseMatrix)o).rows){
        throw new RuntimeException("Введена неправильных размеров матрица");
      }
      SparseMatrix result = new SparseMatrix(this.rows, ((SparseMatrix)o).columns);
      SparseMatrix o1 = ((SparseMatrix)o).transp();

      for( Point key: o1.val.keySet()) {
        for (int i = 0; i < this.rows; i++) {
          if(deMatrix[i][key.y]!=0){
            Point p = new Point(i,key.x);
            if (result.val.containsKey(p)){
              double t = result.val.get(p) + deMatrix[i][key.y]*o1.val.get(key);
              result.val.put(p,t);
            }
            else {
              double t = deMatrix[i][key.y] * o1.val.get(key);
              result.val.put(p, t);
            }
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
  @Override public Matrix dmul(Matrix o) {

    if(o instanceof DenseMatrix){

      if(this.columns!=((DenseMatrix)o).rows){
        throw new RuntimeException("Введена неправильных размеров матрица");
      }

      DenseMatrix result = new DenseMatrix(this.rows, ((DenseMatrix)o).columns);
      DenseMatrix dM = ((DenseMatrix)o).transp();


      Thread[] threads = new Thread[4];
      int ost = rows%4;
      int j=0;
      for(int i=0;i<4;i++){
        j=rows/4;
        DMuller muller;
        if(i==ost&&i!=0) {
          muller = new DMuller(i * j, j * (i + 1) + ost, columns, dM,this, result);
        }
        else {
          muller = new DMuller(i * j, j * (i + 1), columns, dM,this, result);
        }
        threads[i]= new Thread(muller);
        threads[i].start();
      }
      for(int i=0;i<4;i++){
        try {
          threads[i].join();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }

      return(result);
    }

     return null;
  }

    @Override
    public int hashCode () {

      int result = Objects.hash(rows, columns);
      result = 31 * result + Arrays.deepHashCode(deMatrix);
      return result;
    }

    /**
     * спавнивает с обоими вариантами
     * @param o
     * @return
     */


    @Override public boolean equals (Object o){

      if (this == o)
        return true;

      if (o instanceof DenseMatrix) {

        //проверка на хэшкод
        if (this.hashCode() != (o.hashCode())) {
          return false;
        }

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

      if (o instanceof SparseMatrix) {
        SparseMatrix sM = (SparseMatrix) o;
        if (rows != sM.rows || columns != sM.columns)
          return false;

        for (Point key : sM.val.keySet()) {
          if (deMatrix[key.x][key.y] == 0)
            return false;
          if (deMatrix[key.x][key.y] != sM.val.get(key))
            return false;
        }
        return true;
      }

      return false;
    }

    @Override
    public String toString () {
      StringBuilder str = new StringBuilder();
      for (int i = 0; i < rows; i++) {
        for (int j = 0; j < columns; j++) {
          str.append(deMatrix[i][j]);
          str.append(" ");
        }
        str.append("\n");
      }
      return (str.toString());
    }

    //транспонирование матрицы
    @Override
    public DenseMatrix transp () {
      DenseMatrix res = new DenseMatrix(columns, rows);
      for (int i = 0; i < rows; i++)
        for (int j = 0; j < columns; j++)
          res.deMatrix[j][i] = deMatrix[i][j];
      return res;
    }
/*
  public static void main(String[] args){
    DenseMatrix m1 = new DenseMatrix("BigDense1");
    DenseMatrix m2 = new DenseMatrix("BigDense2");
    Matrix m3 = (m1.dmul(m2));
    System.out.println(m3);
  }*/

}
