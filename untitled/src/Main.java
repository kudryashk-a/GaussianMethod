import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader("src/input.txt"));

        //Считываем размер матрицы
        String[] size = reader.readLine().split(" ");
        int m = Integer.parseInt(size[0]);
        int n = Integer.parseInt(size[1]);

        //Считываем элементы матрицы
        double[][] matrix = new double[m][n];
        for (int i = 0; i < m; i++) {
            String[] row = reader.readLine().split(" ");
            for (int j = 0; j < n; j++) {
                matrix[i][j] = Integer.parseInt(row[j]);
            }
        }

        //Считываем индексы столбцов минора
        String[] indices = reader.readLine().split(" ");
        int[] ind = new int[indices.length];
        for (int i = 0; i < indices.length; i++) {
            ind[i] = Integer.parseInt(indices[i]);
        }

        reader.close();

        //Проходимся по строкам и столбцам минора
        for (int i = 0, k = 0; (i < m) || (k < ind.length); i++, k++) {

            //Если коэффициент нулевой, то прибавляем к этой строке другую с ненулевым коэффициентом
            if (matrix[i][ind[k] - 1] == 0) {
                for (int l = 0; l < m; l++) {
                    if (matrix[l][ind[k] - 1] != 0) {
                        for (int x = 0; x < n; x++) {
                            matrix[i][x] += matrix[l][x];
                        }
                        break;
                    }
                }
            }

            //В столбцах минора под единицами делаем нули
            for (int y = 0; y < k; y++) {
                double mn = matrix[i][ind[y] - 1];
                for (int z = 0; z < n; z++) {
                    matrix[i][z] -= matrix[y][z] * mn;
                }
            }

            //В столбцах минора по диагонали делаем единицы
            double del = matrix[i][ind[k] - 1];
            for (int j = 0; j < n; j++) {
                matrix[i][j] /= del;
            }
        }

        //В столбцах минора над единицами делаем нули
       for (int k = ind.length - 1; k >=0; k--) {
            for (int i = k - 1; i >= 0; i--) {
                for (int j = 0; j < n; j++) {
                    matrix[i][j] -= matrix[k][j] * matrix[i][ind[k] - 1];
                }
            }
        }

       FileWriter writer = new FileWriter("src/output.txt");

       //Записываем получившуюся матрицу
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                writer.write(String.valueOf(matrix[i][j])+" ");
            }
            writer.write("\n");
        }

        writer.close();

    }
}