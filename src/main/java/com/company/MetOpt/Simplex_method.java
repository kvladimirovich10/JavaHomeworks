package com.company.MetOpt;

public class Simplex_method {


    //ПРЯМАЯ ЗАДАЧА
    /*final static double[] vector_function = {0,0,0,0,0,0,0,0,0,0, 0,0,0, -1,-1, -1};    // {X1...XN* | U1...UM | W1...WM}
    final static double[] original_vector_function = {0.6, 1.2, 1.8, 2.4, 3, 1.5, 3, 2.5, 2.1, 2.7,0,0,0};        // {X1...XN | U1...UM }
    final static double[][] constraints_matrix = {{1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                                                  {0, 0, 0, 0, 0, 3, 6, -1, 3, 3},
                                                  {1, 2, 3, 4, 5, -2, -4, 0, -1, 0}};  //без U и W
    final static double[] constraints_matrix_addition = {0, 0, 0};    // U в каждой строке по добавке, если нет - 0
    final static double[] constraints_matrix_artificial = {1, 1, 1};     // W в каждой строке по добавке, если нет - 0
    final static double[] vector_right_part = {100, 0, 0};        //  правая часть*/

    //ДВОЙСТВЕННАЯ ЗАДАЧА
    final static double[] vector_function = {0,0,0,0,0,0,   0,0,0,0,0,0,0,0,0,0,  -1,-1,-1,-1,-1,-1,-1,-1,-1,-1 };              // {X1...XN | U1...UM | W1...WM}
    final static double[] original_vector_function = {-100,100,0,0,0,0,   0,0,0,0,0,0,0,0,0,0};    // {X1...XN | U1...UM }
    final static double[][] constraints_matrix = {{1,-1, 0, 0, 1, -1},
                                                  {1,-1, 0, 0, 2, -2},
                                                  {1,-1, 0, 0, 3, -3},
                                                  {1,-1, 0, 0, 4, -4},
                                                  {1,-1, 0, 0, 5, -5},
                                                  {1,-1, 3, -3, -2,2},
                                                  {1,-1, 6, -6, -4,4},
                                                  {1,-1, -1, 1, 0, 0},
                                                  {1,-1, 3, -3, -1,1},
                                                  {1,-1, 3, -3,0, 0}};  // без U и W1
    final static double[] constraints_matrix_addition =   {-1, -1, -1, -1,-1,-1,-1,-1,-1,-1};      // U в каждой строке по добавке, если нет - 0
    final static double[] constraints_matrix_artificial = { 1,  1,  1,  1, 1, 1, 1, 1, 1, 1};    // W в каждой строке по добавке, если нет - 0
    final static double[] vector_right_part = {0.6, 1.2, 1.8, 2.4, 3, 1.5, 3, 2.5, 2.1, 2.7};            //   правая часть*/


    public static void main(String[] args) {
        final String[] Args = Method.create_enum_like_array(constraints_matrix,
                constraints_matrix_addition,
                constraints_matrix_artificial);

        double[] result = Method.simplex_method(Args,
                vector_function,
                original_vector_function,
                constraints_matrix,
                constraints_matrix_addition,
                constraints_matrix_artificial,
                vector_right_part);

        System.out.print("(X1...Xn,Y) = ");
        Method.show_vector(result);
    }
}

class Method {

    static String[] create_enum_like_array(double[][] constraints_matrix,
                                           double[] constraints_matrix_addition,
                                           double[] constraints_matrix_artificial) {
        final String[] Args = new String[constraints_matrix[0].length + constraints_matrix_addition.length + constraints_matrix_artificial.length + 1];
        int delta = 0, u;

        for (int i = 0; i < constraints_matrix[0].length; i++) {
            Args[i] = "X" + (i + 1);
            delta++;
        }

        u = delta;
        for (int i = u; i < u + constraints_matrix_addition.length; i++) {
            Args[i] = "U" + (i + 1 - u);
            delta++;
        }
        u = delta;

        for (int i = u; i < u + constraints_matrix_artificial.length; i++) {
            Args[i] = "W" + (i + 1 - u);
            delta++;
        }
        u = delta;

        Args[u] = "B";

        return Args;
    }

    static double[] simplex_method(String[] Args,
                                   double[] vector_function,
                                   double[] original_vector_function,
                                   double[][] constraints_matrix,
                                   double[] constraints_matrix_addition,
                                   double[] constraints_matrix_artificial,
                                   double[] vector_right_part) {


        boolean[] basis = new boolean[vector_function.length + 1];
        int[] index_out = new int[constraints_matrix_artificial.length];
        String swapArgs;
        double swapDoubles;
        int j = 0;


        int index, line;
        double[][] simplex_matrix = Optimizer.create_simplexmatrix(vector_function,
                constraints_matrix,
                vector_right_part,
                constraints_matrix_addition,
                constraints_matrix_artificial);
        show_matrix(simplex_matrix);

        int count = Optimizer.create_boolean(basis,
                constraints_matrix_artificial,
                constraints_matrix_addition,
                constraints_matrix);


        double[] vert = Optimizer.create_vert(count,
                constraints_matrix_artificial);

        double[] hor = Optimizer.create_hor(count,
                constraints_matrix_addition,
                constraints_matrix_artificial,
                constraints_matrix);

        String[] vArgs = Optimizer.create_vert_args(count,
                Args,
                constraints_matrix_addition,
                constraints_matrix_artificial,
                constraints_matrix);

        String[] hArgs = Optimizer.create_hor_args(basis,
                Args,
                count,
                constraints_matrix_addition,
                constraints_matrix_artificial,
                constraints_matrix);


        double[][] simplex_matrix_basis = Optimizer.create_basis_simplexmatrix(
                hor,
                vert,
                basis,
                simplex_matrix,
                vector_function,
                constraints_matrix,
                vector_right_part,
                constraints_matrix_addition,
                constraints_matrix_artificial);

        shower(hArgs, hor, simplex_matrix_basis, vArgs, vert);


        while (Optimizer.find_minimum(simplex_matrix_basis) != -1) {
            index = Optimizer.find_minimum(simplex_matrix_basis);
            line = Optimizer.find_minimum_devision(index, simplex_matrix_basis);

            System.out.println("min = " + simplex_matrix[line][index]);

            simplex_matrix_basis = Optimizer.reconstruct_simplexmatix_basis(simplex_matrix_basis, index, line);

            swapArgs = vArgs[line];
            vArgs[line] = hArgs[index];
            hArgs[index] = swapArgs;

            swapDoubles = vert[line];
            vert[line] = hor[index];
            hor[index] = swapDoubles;
            shower(hArgs, hor, simplex_matrix_basis, vArgs, vert);

        }


        for (int i = 0; i < hArgs.length; i++) {

            int element = find_position(i, Args, hArgs);
            if (element > original_vector_function.length - 1 && element != original_vector_function.length + constraints_matrix_artificial.length) {
                index_out[j] = i;
                j++;
            }
        }

        for (int i = 0; i < index_out.length; i++) {
            System.out.println(index_out[i]);
        }


        String[] posthorArgs = Optimizer.create_posthorArgs(hArgs,
                index_out);
        double[] posthor = Optimizer.create_posthor(Args,
                hor,
                index_out,
                original_vector_function,
                posthorArgs);

        vert = Optimizer.create_postvert(Args, vert, original_vector_function, vArgs);

        double[][] postsimplex_matrix_basis = Optimizer.create_postsimplex_matrix(simplex_matrix_basis,
                index_out,
                vert,
                posthor);

        shower(posthorArgs, posthor, postsimplex_matrix_basis, vArgs, vert);

        while (Optimizer.find_minimum(postsimplex_matrix_basis) != -1) {
            index = Optimizer.find_minimum(postsimplex_matrix_basis);
            line = Optimizer.find_minimum_devision(index, postsimplex_matrix_basis);
            System.out.println(index);
            System.out.println(line);

            postsimplex_matrix_basis = Optimizer.reconstruct_simplexmatix_basis(postsimplex_matrix_basis, index, line);

            swapArgs = vArgs[line];
            vArgs[line] = posthorArgs[index];
            posthorArgs[index] = swapArgs;

            swapDoubles = vert[line];
            vert[line] = posthor[index];
            posthor[index] = swapDoubles;

            shower(posthorArgs, posthor, postsimplex_matrix_basis, vArgs, vert);

        }


        //ДЛЯ ПРЯМОЙ ЗАДАЧИ
        double[] result = new double[constraints_matrix[0].length + 1];

        for (int i = 0; i < posthor.length; i++) {
            int element = Method.find_position(i, Args, posthorArgs);
            if (element < constraints_matrix[0].length)
                result[element] = 0;
        }
        for (int i = 0; i < vert.length; i++) {
            int element = Method.find_position(i, Args, vArgs);
            if (element < constraints_matrix[0].length)
                result[element] = postsimplex_matrix_basis[i][postsimplex_matrix_basis[0].length - 1];
        }
        result[result.length - 1] = postsimplex_matrix_basis[postsimplex_matrix_basis.length - 1][postsimplex_matrix_basis[0].length - 1];


        //ДЛЯ ДВОЙСТВЕННОЙ
        /*double[] result = new double[constraints_matrix_addition.length + 1];

        for (int i = 0 ; i < posthor.length ; i++) {
            int element = Method.find_position(i, Args, posthorArgs);
            if (element >= constraints_matrix[0].length && element < constraints_matrix[0].length + constraints_matrix_addition.length)
                result[element - constraints_matrix[0].length] = postsimplex_matrix_basis[postsimplex_matrix_basis.length - 1][i];
        }
        for (int i = 0 ; i < vert.length ; i++) {
            int element = Method.find_position(i, Args, vArgs);
            if (element >= constraints_matrix[0].length && element < constraints_matrix[0].length + constraints_matrix_addition.length)
                result[element - constraints_matrix[0].length] = 0;
        }
        result[result.length - 1] = postsimplex_matrix_basis[postsimplex_matrix_basis.length - 1][postsimplex_matrix_basis[0].length - 1];*/
        return result;

    }

    private static void shower(String[] horArgs, double[] hor, double[][] matrix, String[] vertArgs, double[] vert) {
        show_vector_Args(horArgs);
        show_vector(hor);
        show_matrix(matrix);
        show_vector_Args(vertArgs);
        show_vector(vert);
        System.out.println();
    }

    static void show_vector(double[] vector) {
        for (int i = 0; i < vector.length; i++) {
            System.out.print(vector[i] + " ");
        }
        System.out.println();
    }

    static void show_matrix(double[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(String.format("%.2f ", matrix[i][j]));
            }
            System.out.println();
        }
        System.out.println();
    }

    static void show_vector_Args(String[] vector) {
        for (int i = 0; i < vector.length; i++) {
            System.out.print(vector[i] + " ");
        }
        System.out.println();
    }

    static int find_position(int i, String[] Args, String[] hArgs) {
        int element = 0;
        for (int k = 0; k < Args.length; k++) {
            if (hArgs[i] == Args[k])
                break;
            element++;
        }
        return element;
    }


}

class Optimizer {

    static double[][] create_simplexmatrix(double[] function,
                                           double[][] constraints,
                                           double[] right_part,
                                           double[] constraints_addition,
                                           double[] constraints_Artificial) {
        double[][] simplex_matrix = new double[constraints.length + 1][constraints[0].length + constraints_addition.length + constraints_Artificial.length + 2];

        for (int i = 0; i < simplex_matrix.length; i++) {

            if (i == simplex_matrix.length - 1) {
                for (int j = 0; j < function.length; j++) {
                    simplex_matrix[i][j] = function[j];
                }

                simplex_matrix[i][simplex_matrix[i].length - 2] = 1.0;
                simplex_matrix[i][simplex_matrix[i].length - 1] = 0.0;
                continue;
            }

            for (int j = 0; j < constraints[0].length; j++) {
                simplex_matrix[i][j] = constraints[i][j];
            }

            for (int k = constraints[0].length; k < constraints[0].length + constraints.length; k++) {
                if (i == (k - constraints[0].length))
                    simplex_matrix[i][k] = constraints_addition[i];
                else
                    simplex_matrix[i][k] = 0.0;
            }

            for (int k = constraints[0].length + constraints.length; k < constraints[0].length + constraints.length + constraints_Artificial.length; k++) {
                if (i == (k - (constraints[0].length + constraints.length)))
                    simplex_matrix[i][k] = constraints_Artificial[i];
                else
                    simplex_matrix[i][k] = 0.0;
            }

            simplex_matrix[i][simplex_matrix[i].length - 2] = 0.0;
            simplex_matrix[i][simplex_matrix[i].length - 1] = right_part[i];


        }

        return simplex_matrix;
    }

    static double[][] create_basis_simplexmatrix(double[] horizontal,
                                                 double[] vertical,
                                                 boolean[] bool,
                                                 double[][] prepare_matrix,
                                                 double[] function,
                                                 double[][] constraints,
                                                 double[] right_part,
                                                 double[] constraints_addition,
                                                 double[] constraints_Artificial) {

        int k;

        int count = create_boolean(bool, constraints_Artificial, constraints_addition, constraints);

        double[][] simplex_matrix = new double[count + 1][constraints[0].length + constraints_addition.length + constraints_Artificial.length - count + 1];


        for (int i = 0; i < simplex_matrix.length - 1; i++) {
            k = 0;
            for (int j = 0; j < function.length; j++) {
                if (!bool[j]) {
                    simplex_matrix[i][k] = prepare_matrix[i][j];
                    k++;
                }
            }
            simplex_matrix[i][simplex_matrix[0].length - 1] = right_part[i];

        }

        for (int j = 0; j < simplex_matrix[0].length; j++) {
            simplex_matrix[simplex_matrix.length - 1][j] = scalar_multiplication(simplex_matrix, horizontal, vertical, j);
        }

        return simplex_matrix;
    }

    static int create_boolean(boolean[] basis,
                              double[] constraints_Artificial,
                              double[] constraints_addition,
                              double[][] constraints) {

        int count = 0;
        for (int i = 0; i < constraints_addition.length; i++) {
            if (constraints_addition[i] == 1) {
                basis[constraints[0].length + i] = true;
                count++;
            }
            if (constraints_Artificial[i] == 1) {
                basis[constraints[0].length + constraints_addition.length + i] = true;
                count++;
            }
        }
        return count;
    }

    static double[] create_vert(int count,
                                double[] constraints_Artificial) {

        double[] vertical = new double[count];

        for (int i = 0; i < vertical.length; i++) {
            if (constraints_Artificial[i] == 1.0)
                vertical[i] = -1.0;
            else
                vertical[i] = 0.0;
        }
        return vertical;
    }

    static double[] create_hor(int count,
                               double[] constraints_addition,
                               double[] constraints_Artificial,
                               double[][] constraints) {

        double[] horizontal = new double[constraints[0].length + constraints_addition.length + constraints_Artificial.length - count + 1];


        for (int i = 0; i < horizontal.length; i++) {
            horizontal[i] = 0.0;
        }
        return horizontal;

    }

    static String[] create_vert_args(int count,
                                     String[] Args,
                                     double[] constraints_addition,
                                     double[] constraints_Artificial,
                                     double[][] constraints) {

        int k = 0;
        String[] verticalArgs = new String[count];

        for (int i = 0; i < constraints_addition.length; i++) {
            if (constraints_addition[i] == 1) {
                verticalArgs[k] = Args[constraints[0].length + i];
                k++;
            }
            if (constraints_Artificial[i] == 1) {
                verticalArgs[k] = Args[constraints[0].length + constraints_addition.length + i];
                k++;
            }
        }

        return verticalArgs;
    }

    static String[] create_hor_args(boolean[] bool,
                                    String[] Args,
                                    int count,
                                    double[] constraints_addition,
                                    double[] constraints_Artificial,
                                    double[][] constraints) {
        int l = 0;

        String[] horizontalArgs = new String[constraints[0].length + constraints_addition.length + constraints_Artificial.length - count + 1];

        for (int i = 0; i < bool.length; i++) {
            if (!bool[i]) {
                horizontalArgs[l] = Args[i];
                l++;
            }
        }
        return horizontalArgs;
    }


    static double scalar_multiplication(double[][] matrix,
                                        double[] hor,
                                        double[] vert,
                                        int index) {
        double result = 0;

        for (int j = 0; j < matrix.length - 1; j++) {
            result += vert[j] * matrix[j][index];
        }

        return (result - hor[index]);
    }

    static int find_minimum(double[][] matrix) {
        int index = 0;
        double eps = -0.00001;
        double A = eps;
        for (int i = 0; i < matrix[0].length - 1; i++) {
            if (A > matrix[matrix.length - 1][i]) {
                index = i;
                A = matrix[matrix.length - 1][i];
            }
        }

        System.out.println("A = " + A);
        System.out.println("index = " + index);
        if (A == eps && index == 0) {
            //System.out.println("STOP " + matrix[matrix.length - 1][matrix[0].length - 1]);
            return -1;
        }
        return index;
    }

    static int find_minimum_devision(int index,
                                     double[][] matrix) {
        int line = 0;
        double A = Double.MAX_VALUE;

        for (int i = 0; i < matrix.length - 1; i++) {
            if (matrix[i][index] > 0) {
                if (A > matrix[i][matrix[0].length - 1] / matrix[i][index]) {
                    line = i;
                    A = matrix[i][matrix[0].length - 1] / matrix[i][index];
                }
            }
        }

        //System.out.println("line = "+line+" A = "+A);
        return line;
    }


    static double[][] reconstruct_simplexmatix_basis(double[][] matrix,
                                                     int index,
                                                     int line) {

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (i != line && j != index)
                    matrix[i][j] = recalculate_elements(matrix, index, line, i, j);
            }
        }

        matrix[line][index] = 1 / matrix[line][index];
        for (int i = 0; i < matrix[0].length; i++) {
            if (i != index)
                matrix[line][i] = matrix[line][i] * matrix[line][index];
        }

        for (int i = 0; i < matrix.length; i++) {
            if (i != line) {
                matrix[i][index] = -matrix[i][index];
                matrix[i][index] = matrix[i][index] * matrix[line][index];
            }
        }


        return matrix;
    }

    static double[] create_posthor(String[] Args,
                                   double[] hor,
                                   int[] index_out,
                                   double[] originalVectorFunction,
                                   String[] postHorArgs) {

        double[] posthor = new double[hor.length - index_out.length];

        int l = 0;

        for (int j = 0; j < hor.length; j++) {
            int d = 0;
            for (int i = 0; i < index_out.length; i++) {
                if (j == index_out[i])
                    d++;
            }
            if (d == 0) {
                posthor[l] = hor[j];
                l++;
            }
        }


        for (int i = 0; i < posthor.length - 1; i++) {
            int element = Method.find_position(i, Args, postHorArgs);
            posthor[i] = originalVectorFunction[element];
        }

        return posthor;
    }

    static double[] create_postvert(String[] Args,
                                    double[] postvert,
                                    double[] originalVectorFunction,
                                    String[] vertArgs) {
        for (int i = 0; i < postvert.length; i++) {
            int element = Method.find_position(i, Args, vertArgs);
            postvert[i] = originalVectorFunction[element];
        }

        return postvert;
    }

    static String[] create_posthorArgs(String[] horArgs,
                                       int[] index_out) {

        String[] posthorArgs = new String[horArgs.length - index_out.length];

        int l = 0;

        for (int j = 0; j < horArgs.length; j++) {
            int d = 0;
            for (int i = 0; i < index_out.length; i++) {
                if (j == index_out[i])
                    d++;
            }
            if (d == 0) {
                posthorArgs[l] = horArgs[j];
                l++;
            }
        }

        return posthorArgs;
    }


    static double[][] create_postsimplex_matrix(double[][] matrix,
                                                int[] index_out,
                                                double[] vert,
                                                double[] hor) {

        double[][] post_simplex_matrix = new double[matrix.length][matrix[0].length - index_out.length];


        int q = 0, l;
        for (int i = 0; i < matrix.length - 1; i++) {
            l = 0;
            for (int j = 0; j < matrix[0].length; j++) {
                int d = 0;
                for (int k = 0; k < index_out.length; k++) {
                    if (j == index_out[k])
                        d++;
                }
                if (d == 0) {
                    post_simplex_matrix[q][l] = matrix[i][j];
                    l++;
                }
            }
            q++;
        }

        for (int j = 0; j < post_simplex_matrix[0].length; j++) {
            post_simplex_matrix[post_simplex_matrix.length - 1][j] = scalar_multiplication(post_simplex_matrix, hor, vert, j);
        }

        return post_simplex_matrix;

    }


    private static double recalculate_elements(double[][] matrix,
                                               int index,
                                               int line,
                                               int cer_i,
                                               int cur_j) {
        return (matrix[cer_i][cur_j] * matrix[line][index] - matrix[line][cur_j] * matrix[cer_i][index]) / matrix[line][index];
    }

}